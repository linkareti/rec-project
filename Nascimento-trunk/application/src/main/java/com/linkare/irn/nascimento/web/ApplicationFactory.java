package com.linkare.irn.nascimento.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.inject.Named;

import com.linkare.irn.nascimento.model.core.BirthRegistrationStatus;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.identification.AcademicDegree;
import com.linkare.irn.nascimento.model.identification.AddressType;
import com.linkare.irn.nascimento.model.identification.Gender;
import com.linkare.irn.nascimento.model.identification.Job;
import com.linkare.irn.nascimento.model.identification.JobCondition;
import com.linkare.irn.nascimento.model.identification.JobStatus;
import com.linkare.irn.nascimento.model.identification.LatitudeCoordinatePosition;
import com.linkare.irn.nascimento.model.identification.LongitudeCoordinatePosition;
import com.linkare.irn.nascimento.model.identification.MaritalStatus;
import com.linkare.irn.nascimento.model.identification.NaturalityType;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.organization.OrganizationType;
import com.linkare.irn.nascimento.model.survey.BirthFiliation;
import com.linkare.irn.nascimento.model.survey.BirthLocation;
import com.linkare.irn.nascimento.model.survey.BirthNature;
import com.linkare.irn.nascimento.model.survey.BirthTwin;
import com.linkare.irn.nascimento.model.survey.MidwifeType;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.geographic.CountryEvent;
import com.linkare.irn.nascimento.service.cdi.organization.OrganizationEvent;
import com.linkare.irn.nascimento.service.geographic.CountryService;
import com.linkare.irn.nascimento.service.organization.OrganizationService;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@ApplicationScoped
@Singleton
@Startup
@Named
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Lock(LockType.READ)
public class ApplicationFactory {

	private static final String PORTUGAL_COUNTRY_CODE = "PRT";

	private static final List<Gender> GENDERS = Arrays.asList(Gender.values());

	private static final List<MaritalStatus> MARITAL_STATUSES = Arrays.asList(MaritalStatus.values());

	private static final List<NaturalityType> NATURALITY_TYPES = Arrays.asList(NaturalityType.values());

	private static final List<AddressType> ADDRESS_TYPES = Arrays.asList(AddressType.values());

	private static final List<Job> JOBS = Arrays.asList(Job.values());

	private static final List<JobStatus> JOB_STATUSES = Arrays.asList(JobStatus.values());

	private static final List<JobCondition> JOB_CONDITIONS = Arrays.asList(JobCondition.values());

	private static final List<AcademicDegree> ACADEMIC_DEGREES = Arrays.asList(AcademicDegree.values());

	private static final List<BirthRegistrationStatus> BIRTH_REGISTRATION_STATUSES;

	private static final List<LatitudeCoordinatePosition> LATITUDE_COORDINATE_POSITIONS = Arrays
			.asList(LatitudeCoordinatePosition.values());

	private static final List<LongitudeCoordinatePosition> LONGITUDE_COORDINATE_POSITIONS = Arrays
			.asList(LongitudeCoordinatePosition.values());

	private static final List<BirthFiliation> BIRTH_FILIATIONS = Arrays.asList(BirthFiliation.values());

	private static final List<BirthLocation> BIRTH_LOCATIONS = Arrays.asList(BirthLocation.values());

	private static final List<BirthNature> BIRTH_NATURES = Arrays.asList(BirthNature.values());

	private static final List<BirthTwin> BIRTH_TWINS = Arrays.asList(BirthTwin.values());

	private static final List<MidwifeType> MIDWIFE_TYPES = Arrays.asList(MidwifeType.values());

	private List<Country> countries;

	private List<Country> nonPortugueseCountries;

	private Country portugal;

	private List<Organization> hospitalUnits;

	static {

		BIRTH_REGISTRATION_STATUSES = new ArrayList<>();

		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.REQUIRES_2ND_FOREFATHER_CONFIRMATION);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.CANCELLED_BY_SECOND_FOREFATHER);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.EXPIRED);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.ELABORATING);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.ACCEPTED);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.CONCLUDED);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.REJECTED);
		BIRTH_REGISTRATION_STATUSES.add(BirthRegistrationStatus.DELETED);

	}

	@Inject
	private CountryService countryService;

	@Inject
	private OrganizationService organizationService;

	/**
	 * @return the genders
	 */
	public List<Gender> getGenders() {
		return GENDERS;
	}

	/**
	 * @return the maritalStatuses
	 */
	public List<MaritalStatus> getMaritalStatuses() {
		return MARITAL_STATUSES;
	}

	/**
	 * @return the naturalityTypes
	 */
	public List<NaturalityType> getNaturalityTypes() {
		return NATURALITY_TYPES;
	}

	/**
	 * @return the addressTypes
	 */
	public List<AddressType> getAddressTypes() {
		return ADDRESS_TYPES;
	}

	/**
	 * @return the jobs
	 */
	public List<Job> getJobs() {
		return JOBS;
	}

	/**
	 * @return the jobStatuses
	 */
	public List<JobStatus> getJobStatuses() {
		return JOB_STATUSES;
	}

	/**
	 * @return the jobConditions
	 */
	public List<JobCondition> getJobConditions() {
		return JOB_CONDITIONS;
	}

	/**
	 * @return the academicDegrees
	 */
	public List<AcademicDegree> getAcademicDegrees() {
		return ACADEMIC_DEGREES;
	}

	/**
	 * @return the birthRegistrationStatus
	 */
	public List<BirthRegistrationStatus> getBirthRegistrationStatuses() {
		return BIRTH_REGISTRATION_STATUSES;
	}

	/**
	 * @return the latitude coordinate positions
	 */
	public List<LatitudeCoordinatePosition> getLatitudeCoordinatePositions() {
		return LATITUDE_COORDINATE_POSITIONS;
	}

	/**
	 * @return the longitude coordinate positions
	 */
	public List<LongitudeCoordinatePosition> getLongitudeCoordinatePositions() {
		return LONGITUDE_COORDINATE_POSITIONS;
	}

	/**
	 * @return the birthFiliations
	 */
	public List<BirthFiliation> getBirthFiliations() {
		return BIRTH_FILIATIONS;
	}

	/**
	 * @return the birthLocations
	 */
	public List<BirthLocation> getBirthLocations() {
		return BIRTH_LOCATIONS;
	}

	/**
	 * @return the birthNatures
	 */
	public List<BirthNature> getBirthNatures() {
		return BIRTH_NATURES;
	}

	/**
	 * @return the birthTwins
	 */
	public List<BirthTwin> getBirthTwins() {
		return BIRTH_TWINS;
	}

	/**
	 * @return the midwifeTypes
	 */
	public List<MidwifeType> getMidwifeTypes() {
		return MIDWIFE_TYPES;
	}

	@PostConstruct
	public void init() {
		initCountries();
		initHospitalUnits();
	}

	private void initCountries() {
		this.countries = countryService.findAll();
		this.nonPortugueseCountries = new ArrayList<>(this.countries);
		if (!this.countries.isEmpty()) {
			this.portugal = this.countryService.findByCode(PORTUGAL_COUNTRY_CODE);
			if (this.portugal != null) {
				this.nonPortugueseCountries.remove(this.portugal);
			}
		}
	}

	@Lock(LockType.WRITE)
	public void onCountryEvent(@Observes(during = TransactionPhase.AFTER_COMPLETION) final CountryEvent event) {
		final EventOperation operation = event.getOperation();
		final Country payload = event.getPayload();

		if (payload == null) {
			throw new IllegalArgumentException("Payload not included in event " + event);
		}
		switch (operation) {
		case CREATE:
			this.countries.add(payload);
			if (PORTUGAL_COUNTRY_CODE.equals(payload.getCode())) {
				this.portugal = payload;
			} else {
				this.nonPortugueseCountries.add(payload);
			}
			break;
		case UPDATE:
			replaceCountry(payload);
			replaceNonPortugueseCountry(payload);
			break;
		default:
			throw new IllegalArgumentException("Unexpected operation type " + operation);
		}
	}

	private void replaceCountry(final Country payload) {
		int index = 0;
		for (final Country current : countries) {
			if (Objects.equals(current, payload)) {
				countries.set(index, payload);
				break;
			}
			index++;
		}
	}

	private void replaceNonPortugueseCountry(final Country payload) {
		int index = 0;
		for (final Country current : nonPortugueseCountries) {
			if (Objects.equals(current, payload)) {
				nonPortugueseCountries.set(index, payload);
				break;
			}
			index++;
		}
	}

	private void initHospitalUnits() {
		this.hospitalUnits = organizationService.findActiveByType(OrganizationType.HOSPITAL_UNIT);
		this.hospitalUnits.addAll(organizationService.findActiveByType(OrganizationType.OTHER_HOSPITAL_UNIT));
	}

	@Lock(LockType.WRITE)
	public void onOrganizationEvent(
			@Observes(during = TransactionPhase.AFTER_COMPLETION) final OrganizationEvent event) {
		final EventOperation operation = event.getOperation();
		final Organization payload = event.getPayload();

		if (payload == null) {
			throw new IllegalArgumentException("Payload not included in event " + event);
		}
		if (payload.getOrganizationType() == OrganizationType.HOSPITAL_UNIT) {
			switch (operation) {
			case CREATE:
				this.hospitalUnits.add(payload);
				break;
			case UPDATE:
				replaceHospitalUnit(payload);
				break;
			default:
				throw new IllegalArgumentException("Unexpected operation type " + operation);
			}
		}
	}

	private void replaceHospitalUnit(final Organization payload) {
		int index = 0;
		if (payload.getActive()) {
			if (hospitalUnits.contains(payload)) {
				for (final Organization current : hospitalUnits) {
					if (Objects.equals(current, payload)) {
						hospitalUnits.set(index, payload);
						break;
					}
					index++;
				}
			} else {
				hospitalUnits.add(payload);
			}
		} else {
			hospitalUnits.remove(payload);
		}
	}

	/**
	 * @return the countries
	 */
	public List<Country> getCountries() {
		return countries;
	}

	/**
	 * @return the nonPortugueseCountries
	 */
	public List<Country> getNonPortugueseCountries() {
		return nonPortugueseCountries;
	}

	/**
	 * @return the portugal
	 */
	public Country getPortugal() {
		return portugal;
	}

	/**
	 * @return the hospitalUnits
	 */
	public List<Organization> getHospitalUnits() {
		return hospitalUnits;
	}
}