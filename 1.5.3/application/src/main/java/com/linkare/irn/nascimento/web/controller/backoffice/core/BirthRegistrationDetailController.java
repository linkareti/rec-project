package com.linkare.irn.nascimento.web.controller.backoffice.core;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;

import org.primefaces.model.StreamedContent;

import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.service.core.BirthRegistrationService;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.web.auth.UnauthorizedException;
import com.linkare.irn.nascimento.web.controller.BaseProtectedController;
import com.linkare.irn.nascimento.web.controller.shared.core.BirthRegistrationControllerAware;
import com.linkare.irn.nascimento.web.controller.shared.core.BirthRegistrationOperation;
import com.linkare.irn.nascimento.web.controller.shared.core.DocumentHelper;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@ViewScoped
@Named
public class BirthRegistrationDetailController extends BaseProtectedController
		implements BirthRegistrationControllerAware {

	private static final long serialVersionUID = -6454088446543836978L;

	private Long id;

	private BirthRegistration birthRegistration;

	@Inject
	private BirthRegistrationService birthRegistrationService;

	private DocumentHelper<BirthRegistrationDetailController> documentHelper;

	private boolean showRejectionReasonGroup;

	@Override
	public void checkAccess() throws UnauthorizedException {
		if (!getLoginBean().getHasHospitalUnitRole() && !getLoginBean().getHasCivilRegistrationOfficeRole()
				&& !getLoginBean().getHasManagerRole()) {
			throw new UnauthorizedException(
					"User " + getLoginBean().getUsername() + " has no access to the birth registrations");
		}

		if (id != null) {
			this.birthRegistration = birthRegistrationService.find(id);
		}

		if (birthRegistration == null) {
			throw new EntityNotFoundException("Birth registration with id " + id + " not found");
		}

		if (getLoginBean().getHasHospitalUnitRole() && (birthRegistration.getHospitalUnit() == null
				|| !Objects.equals(getLoginBean().getOrganization(), birthRegistration.getHospitalUnit()))) {
			throw new UnauthorizedException(
					"User " + getLoginBean().getUsername() + " has no access to the birth registration with id " + id);
		}

		this.documentHelper = new DocumentHelper<BirthRegistrationDetailController>(true, this,
				birthRegistrationService);
	}

	@PostConstruct
	@Override
	public void init() {
		super.init();
	}

	public String hospitalUnitAcceptSubmission() {
		try {
			birthRegistrationService.hospitalUnitAcceptBirthRegistration(getBirthRegistration());
			JsfUtils.addGlobalInfoMessage(
					getMessage(BirthRegistrationOperation.HOSPITAL_UNIT_ACCEPTANCE.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(
					getMessage(BirthRegistrationOperation.HOSPITAL_UNIT_ACCEPTANCE.getErrorKey()));
		}
		return null;
	}

	public String hospitalUnitRejectSubmission() {
		try {
			birthRegistrationService.hospitalUnitRejectBirthRegistration(getBirthRegistration());
			JsfUtils.addGlobalInfoMessage(
					getMessage(BirthRegistrationOperation.HOSPITAL_UNIT_REJECTION.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(
					getMessage(BirthRegistrationOperation.HOSPITAL_UNIT_REJECTION.getErrorKey()));
		}
		return null;
	}

	public String civilRegistrationOfficeAcceptSubmission() {
		try {
			birthRegistrationService.civilRegistrationOfficeAcceptBirthRegistration(birthRegistration);
			JsfUtils.addGlobalInfoMessage(
					getMessage(BirthRegistrationOperation.CIVIL_REGISTRATION_OFFICE_ACCEPTANCE.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(
					getMessage(BirthRegistrationOperation.CIVIL_REGISTRATION_OFFICE_ACCEPTANCE.getErrorKey()));
		}
		return null;
	}

	public String civilRegistrationOfficeEffectuateSubmission() {
		try {
			birthRegistrationService.civilRegistrationOfficeEffectuateBirthRegistration(birthRegistration);
			JsfUtils.addGlobalInfoMessage(
					getMessage(BirthRegistrationOperation.CIVIL_REGISTRATION_OFFICE_EFFECTUATE.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(
					getMessage(BirthRegistrationOperation.CIVIL_REGISTRATION_OFFICE_EFFECTUATE.getErrorKey()));
		}
		return null;
	}

	public String civilRegistrationOfficeRejectSubmission() {
		try {
			birthRegistrationService.civilRegistrationOfficeRejectBirthRegistration(birthRegistration);
			JsfUtils.addGlobalInfoMessage(
					getMessage(BirthRegistrationOperation.CIVIL_REGISTRATION_OFFICE_REJECTION.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(
					getMessage(BirthRegistrationOperation.CIVIL_REGISTRATION_OFFICE_REJECTION.getErrorKey()));
		}
		return null;
	}

	public String recoverBirthRegistration() {
		try {
			birthRegistrationService.recoverBirthRegistration(birthRegistration);
			JsfUtils.addGlobalInfoMessage(getMessage(BirthRegistrationOperation.RECOVER.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(getMessage(BirthRegistrationOperation.RECOVER.getErrorKey()));
		}
		return null;
	}

	public String assignToDeletedBirthRegistration() {

		try {
			birthRegistrationService.assignToDeletedBirthRegistration(birthRegistration);
			JsfUtils.addGlobalInfoMessage(getMessage(BirthRegistrationOperation.ASSIGN_TO_DELETED.getMessageKey()));
			return "birth_registrations?faces-redirect=true";
		} catch (OptimisticLockException e) {
			JsfUtils.addGlobalErrorMessage(getMessage("message.error.optimisticLockException"));
		} catch (final Exception e) {
			logError(e);
			JsfUtils.addGlobalErrorMessage(getMessage(BirthRegistrationOperation.ASSIGN_TO_DELETED.getErrorKey()));
		}
		return null;
	}

	public boolean hasManagerRole() {
		return getLoginBean().getHasManagerRole();
	}

	/**
	 * @return the showRejectionReasonGroup
	 */
	public boolean isShowRejectionReasonGroup() {
		return showRejectionReasonGroup;
	}

	/**
	 * @param showRejectionReasonGroup the showRejectionReasonGroup to set
	 */
	public void setShowRejectionReasonGroup(boolean showRejectionReasonGroup) {
		this.showRejectionReasonGroup = showRejectionReasonGroup;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the birthRegistration
	 */
	@Override
	public BirthRegistration getBirthRegistration() {
		return birthRegistration;
	}

	/**
	 * @param birthRegistration the birthRegistration to set
	 */
	public void setBirthRegistration(BirthRegistration birthRegistration) {
		this.birthRegistration = birthRegistration;
	}

	/**
	 * @return the documentHelper
	 */
	public DocumentHelper<BirthRegistrationDetailController> getDocumentHelper() {
		return documentHelper;
	}

	public StreamedContent getFile() {
		return getDocumentHelper().getFile();
	}

}