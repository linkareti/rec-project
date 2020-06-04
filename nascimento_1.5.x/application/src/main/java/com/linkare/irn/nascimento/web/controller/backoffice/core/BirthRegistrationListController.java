package com.linkare.irn.nascimento.web.controller.backoffice.core;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.dao.core.BirthRegistrationDAO;
import com.linkare.irn.nascimento.exception.organization.OrganizationNotFoundException;
import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.core.BirthRegistrationStatus;
import com.linkare.irn.nascimento.model.ext.search.core.BirthRegistrationSearch;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.OrganizationType;
import com.linkare.irn.nascimento.model.security.RoleType;
import com.linkare.irn.nascimento.service.core.BirthRegistrationService;
import com.linkare.irn.nascimento.service.organization.OrganizationService;
import com.linkare.irn.nascimento.util.SelectionRange;
import com.linkare.irn.nascimento.web.auth.LoginBean;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;
import com.linkare.irn.nascimento.web.controller.ApplicationListProtectedBean;
import com.linkare.irn.nascimento.web.datamodel.core.BirthRegistrationLazyDataModel;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Named
@ViewScoped
public class BirthRegistrationListController
		extends ApplicationListProtectedBean<BirthRegistrationLazyDataModel, BirthRegistrationDAO, BirthRegistration> {

	private static final Logger LOG = LoggerFactory.getLogger(BirthRegistrationListController.class);

	private static final int NUMBER_OF_MONTHS_TO_SEARCH_FOR = 6;

	private static final long serialVersionUID = -6454088446543836978L;

	private int numberOfOtherHospitalUnitsToShow = 100;

	@Inject
	private BirthRegistrationService birthRegistrationService;

	@Inject
	private OrganizationService organizationService;

	private BirthRegistrationSearch example = new BirthRegistrationSearch();

	@Override
	public void checkAccess() throws UnauthorizedException {
		if (!getLoginBean().getHasHospitalUnitRole() && !getLoginBean().getHasCivilRegistrationOfficeRole()
				&& !getLoginBean().getHasManagerRole()) {
			throw new UnauthorizedException(
					"User " + getLoginBean().getUsername() + " has no access to the birth registrations");
		}
	}

	@PostConstruct
	@Override
	public void init() {
		super.init();
		initBirthRegistrationFilter();
	}

	protected void initBirthRegistrationFilter() {
		this.example.clear();
		setImplicitFilters();
	}

	private void setImplicitFilters() {
		if (this.example.getStart() == null && this.example.getEnd() == null) {
			final LocalDate now = new org.joda.time.LocalDate();
			final LocalDate monthInThePast = now.minusMonths(NUMBER_OF_MONTHS_TO_SEARCH_FOR);

			this.example.setStart(monthInThePast.toDateTimeAtCurrentTime().toDate());
			this.example.setEnd(now.toDateTimeAtCurrentTime().toDate());
		}

		final LoginBean loginBean = getLoginBean();
		final RoleType roleType = loginBean.getRoleType();
		switch (roleType) {
		case HOSPITAL_UNIT:
			this.example.setStatus(BirthRegistrationStatus.SUBMITTED);
			// if we have no organizationId, we set it to 0, so that we do not return any
			// results
			this.example.setHospitalUnitId(
					loginBean.getOrganization().getId() == null ? null : loginBean.getOrganization().getId());
			break;
		case CIVIL_REGISTRATION_OFFICE:
			this.example.setStatus(BirthRegistrationStatus.ELABORATING);

			try {
				Organization org = organizationService.findActivesByTypeAndEmail(OrganizationType.HOSPITAL_UNIT,
						loginBean.getOrganization().getEmail()).get(0);

				this.example.setHospitalUnitId(org.getId());
			} catch (OrganizationNotFoundException e) {
				LOG.warn("No hospital unit found for conservatory " + loginBean.getOrganization().getName());
			}

			break;
		default:
			break;
		}
	}

	public boolean isOtherHospitalUnit() {

		if (example.getHospitalUnitId() != null) {
			this.example.setIsOtherHospital(
					organizationService.find(example.getHospitalUnitId()).getOrganizationType().isOtherHospitalUnit());
			return this.example.getIsOtherHospital();
		}

		return false;
	}

	@Override
	protected BirthRegistrationLazyDataModel initDataModel() {
		final BirthRegistrationLazyDataModel lazyDataModel = new BirthRegistrationLazyDataModel(
				birthRegistrationService.getDAO());
		lazyDataModel.addPageFilter(BirthRegistrationLazyDataModel.EXAMPLE, getExample());
		return lazyDataModel;
	}

	public List<String> completeOtherHospitals(final String name) {
		return birthRegistrationService.findOtherHospitalUnits(name,
				new SelectionRange(0, getNumberOfOtherHospitalUnitsToShow()));
	}

	@Override
	protected void addFilters() {
		setImplicitFilters();
		getDataModel().addPageFilter(BirthRegistrationLazyDataModel.EXAMPLE, getExample());
	}

	public String search() {
		resetPagination();
		return null;
	}

	@Override
	protected String getUIDataComponentId() {
		return "birthRegistrations";
	}

	@Override
	protected BirthRegistrationDAO initDAO() {
		return birthRegistrationService.getDAO();
	}

	/**
	 * @return the example
	 */
	public BirthRegistrationSearch getExample() {
		return example;
	}

	@Override
	public void clear() {
		this.initBirthRegistrationFilter();
	}

	public int getNumberOfOtherHospitalUnitsToShow() {
		return numberOfOtherHospitalUnitsToShow;
	}

	public void setNumberOfOtherHospitalUnitsToShow(int numberOfOtherHospitalUnitsToShow) {
		this.numberOfOtherHospitalUnitsToShow = numberOfOtherHospitalUnitsToShow;
	}
}