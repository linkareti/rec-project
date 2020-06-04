package com.linkare.irn.nascimento.model.core;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import com.linkare.irn.nascimento.model.VersionedDomainObject;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.identification.Address;
import com.linkare.irn.nascimento.model.identification.Gender;
import com.linkare.irn.nascimento.model.identification.Naturality;
import com.linkare.irn.nascimento.model.identification.NaturalityType;
import com.linkare.irn.nascimento.model.identification.Parent;
import com.linkare.irn.nascimento.model.organization.Organization;
import com.linkare.irn.nascimento.model.survey.BirthFiliation;
import com.linkare.irn.nascimento.model.survey.BirthLocation;
import com.linkare.irn.nascimento.model.survey.BirthNature;
import com.linkare.irn.nascimento.model.survey.BirthTwin;
import com.linkare.irn.nascimento.model.survey.MidwifeType;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationRegistrationValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationSurveyValidation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Audited
@Table(name = "birth_registration")
@NamedQueries({

		@NamedQuery(name = BirthRegistration.QUERY_NAME_FIND_REGISTRATIONS_TO_EXPIRE, query = "select b from BirthRegistration b where "
				+ "b.creationDate < :" + BirthRegistration.QUERY_PARAM_CREATION_DATE
				+ " and b.birthRegistrationStatus = com.linkare.irn.nascimento.model.core.BirthRegistrationStatus.REQUIRES_2ND_FOREFATHER_CONFIRMATION"),

		@NamedQuery(name = BirthRegistration.FIND_OTHER_HOSPITAL_UNITS, query = "select b.otherHospitalUnit from BirthRegistration b where b.otherHospitalUnit is not null and lower(b.otherHospitalUnit) like :"
				+ BirthRegistration.PARAM_OTHER_HOSPITAL_UNIT)

})
public class BirthRegistration extends VersionedDomainObject {

	private static final long serialVersionUID = -5177032826129307329L;

	public static final String QUERY_NAME_FIND_REGISTRATIONS_TO_EXPIRE = "BirthRegistration.findRegistrationsToExpire";

	public static final String FIND_OTHER_HOSPITAL_UNITS = "BirthRegistration.findOtherHospitalUnits";

	public static final String PARAM_OTHER_HOSPITAL_UNIT = "otherHospitalUnit";

	@Column(name = "given_name", length = 150)
	@Size(max = 150)
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	@NotAudited
	private String givenName;

	@Column(name = "family_name", length = 150)
	@Size(max = 150)
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	@NotAudited
	private String familyName;

	@Column(name = "date_of_birth")
	@Temporal(TemporalType.TIMESTAMP)
	@Past(groups = BirthRegistrationRegistrationValidation.class)
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	@NotAudited
	private Date dateOfBirth;

	@Column(name = "gender", length = 75)
	@Enumerated(EnumType.STRING)
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	@NotAudited
	private Gender gender;

	@Column(name = "deceased")
	@NotAudited
	private Boolean deceased;

	@Column(name = "deceased_date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotAudited
	private Date deceasedDate;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "key_naturality")
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	@NotAudited
	private Naturality naturality;

	@Column(name = "birth_registration_status")
	@Enumerated(EnumType.STRING)
	private BirthRegistrationStatus birthRegistrationStatus;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "key_first_parent")
	@NotAudited
	private Parent firstParent;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "key_second_parent")
	@NotAudited
	private Parent secondParent;

	@ManyToOne
	@JoinColumn(name = "key_nationality")
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	@NotAudited
	private Country nationality;

	@Embedded
	@NotAudited
	private Address address;

	@Column(name = "rejection_reason")
	@Size(max = 250)
	private String rejectionReason;

	@Column(name = "comment")
	@NotAudited
	@Size(max = 250)
	private String comment;

	@Column(name = "observation")
	@Size(max = 250)
	private String observation;

	@Column(name = "registration_number")
	@Size(max = 20)
	@NotNull
	@NotAudited
	private String registrationNumber;

	@ManyToOne
	@JoinColumn(name = "key_hospital_unit")
	@NotAudited
	private Organization hospitalUnit;

	@Column(name = "other_hospital")
	@Size(max = 250)
	@NotAudited
	private String otherHospitalUnit;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "key_document")
	@NotAudited
	private BirthRegistrationDocument document;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "key_baby_photo")
	@NotAudited
	private BirthRegistrationBabyPhoto babyPhoto;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "key_receipt")
	@NotAudited
	private BirthRegistrationDocument receipt;

	@Transient
	@NotNull(groups = BirthRegistrationRegistrationValidation.class)
	private NaturalityType naturalityType;

	@Column(name = "last_birth_registration_status_date")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastBirthRegistrarionDate;

	// Survey
	// Filiação
	@Column(name = "birth_filiation", length = 75)
	@Enumerated(EnumType.STRING)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private BirthFiliation birthFiliation;

	// Data do Casamento
	@Column(name = "marriage_date")
	@Temporal(TemporalType.DATE)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Date marriageDate;

	// Filhos anteriores pertencentes a este casamento - nados-vivos
	@Column(name = "marriage_live_birth")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer marriageLiveBirth;

	// Filhos anteriores pertencentes a este casamento - fetos-mortos
	@Column(name = "marriage_stillborn")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer marriageStillborn;

	// Peso à nascença
	@Column(name = "weight")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 100, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 8500, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer weight;

	// Local
	@Column(name = "birth_local", length = 75)
	@Enumerated(EnumType.STRING)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private BirthLocation birthLocation;

	// Natureza
	@Column(name = "birth_nature", length = 75)
	@Enumerated(EnumType.STRING)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private BirthNature birthNature;

	// Se resultante de Parto gemelar
	@Column(name = "birth_twins", length = 75)
	@Enumerated(EnumType.STRING)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private BirthTwin birthTwins;

	// Número de nados-vivos
	@Column(name = "birth_twins_number_of_live_birth")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 1, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 9, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer birthTwinsNumberOfLiveBirth;

	// Número de fetos-mortos
	@Column(name = "birth_twins_number_of_stillbirth")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 8, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer birthTwinsNumberOfStillBirth;

	// Número de gémeos
	@Column(name = "birth_twins_number_of_twins")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 2, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 9, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer birthTwinsNumberOfTwins;

	// Assistência
	@Column(name = "midwife_type", length = 75)
	@Enumerated(EnumType.STRING)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private MidwifeType midwifeType;

	// Duração da gravidez
	@Column(name = "duration_of_pregnancy")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 44, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer durationOfPregnancy;

	// Número de filhos anteriores comuns - nados-vivos
	@Column(name = "number_of_common_live_birth_children")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer numberOfCommonLiveBirthChildren;

	// Número de filhos anteriores comuns - fetos-mortos
	@Column(name = "number_of_common_stillborn_children")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer numberOfCommonStillbornChildren;

	// Número de partos anteriores
	@Column(name = "number_of_previous_birth")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer numberOfPreviousBirth;

	// Número de nados-vivos
	@Column(name = "number_of_live_birth")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer numberOfLiveBirth;

	// Número de fetos-mortos 22+ semanas
	@Column(name = "number_of_stillborn_22_or_more_weeks")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer numberOfStillborn22OrMoreWeeks;

	// Número de fetos-mortos 22- semanas
	@Column(name = "number_of_stillborn_less_than_22_weeks")
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
	@Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Integer numberOfStillbornLessThan22Weeks;

	// Data do termo da gravidez imediatamente anterior
	@Column(name = "previous_birth_date")
	@Temporal(TemporalType.DATE)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Date previousBirthDate;

	// Data do nascimento do último nado-vivo
	@Column(name = "previous_live_birth_date")
	@Temporal(TemporalType.DATE)
	@NotNull(groups = BirthRegistrationSurveyValidation.class)
	@NotAudited
	private Date previousLiveBirthDate;

	@Column(name = "survey_observations")
	@Size(max = 500)
	@NotAudited
	private String surveyObservations;

	/**
	 * @return the name
	 */
	public String getName() {
		return String.format("%s %s", givenName, familyName);
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the familyName
	 */
	public String getFamilyName() {
		return familyName;
	}

	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the deceased
	 */
	public Boolean getDeceased() {
		return deceased;
	}

	/**
	 * @param deceased the deceased to set
	 */
	public void setDeceased(Boolean deceased) {
		this.deceased = deceased;
	}

	/**
	 * @return the deceasedDate
	 */
	public Date getDeceasedDate() {
		return deceasedDate;
	}

	/**
	 * @param deceasedDate the deceasedDate to set
	 */
	public void setDeceasedDate(Date deceasedDate) {
		this.deceasedDate = deceasedDate;
	}

	/**
	 * @return the naturality
	 */
	public Naturality getNaturality() {
		return naturality;
	}

	/**
	 * @param naturality the naturality to set
	 */
	public void setNaturality(Naturality naturality) {
		this.naturality = naturality;
	}

	/**
	 * @return the birthRegistrationStatus
	 */
	public BirthRegistrationStatus getBirthRegistrationStatus() {
		return birthRegistrationStatus;
	}

	/**
	 * @param birthRegistrationStatus the birthRegistrationStatus to set
	 */
	public void setBirthRegistrationStatus(BirthRegistrationStatus birthRegistrationStatus) {
		this.birthRegistrationStatus = birthRegistrationStatus;
	}

	/**
	 * @return the firstParent
	 */
	public Parent getFirstParent() {
		if (firstParent == null) {
			firstParent = new Parent();
		}
		return firstParent;
	}

	/**
	 * @param firstParent the firstParent to set
	 */
	public void setFirstParent(Parent firstParent) {
		this.firstParent = firstParent;
	}

	/**
	 * @return the secondParent
	 */
	public Parent getSecondParent() {
		return secondParent;
	}

	/**
	 * @param secondParent the secondParent to set
	 */
	public void setSecondParent(Parent secondParent) {
		this.secondParent = secondParent;
	}

	/**
	 * @return the nationality
	 */
	public Country getNationality() {
		return nationality;
	}

	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * @return the rejectionReason
	 */
	public String getRejectionReason() {
		return rejectionReason;
	}

	/**
	 * @param rejectionReason the rejectionReason to set
	 */
	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	/**
	 * @return the observation
	 */
	public String getObservation() {
		return observation;
	}

	/**
	 * @param observation the observation to set
	 */
	public void setObservation(String observation) {
		this.observation = observation;
	}

	/**
	 * @return the registrationNumber
	 */
	public String getRegistrationNumber() {
		return registrationNumber;
	}

	/**
	 * @param registrationNumber the registrationNumber to set
	 */
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	/**
	 * @return the hospitalUnit
	 */
	public Organization getHospitalUnit() {
		return hospitalUnit;
	}

	/**
	 * @param hospitalUnit the hospitalUnit to set
	 */
	public void setHospitalUnit(Organization hospitalUnit) {
		this.hospitalUnit = hospitalUnit;
	}

	/**
	 * @return the document
	 */
	public BirthRegistrationDocument getDocument() {
		if (document == null) {
			document = new BirthRegistrationDocument();
		}
		return document;
	}

	/**
	 * @param document the document to set
	 */
	public void setDocument(BirthRegistrationDocument document) {
		this.document = document;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return getDocument().getFilename();
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		getDocument().setFilename(filename);
	}

	public String getFileIcon() {
		return getFileIcon(getFileContentType());
	}

	public String getFileIcon(final String fileContentType) {
		if (StringUtils.isBlank(fileContentType)) {
			return null;
		}
		if (fileContentType.contains("pdf")) {
			return "fa fa-file-pdf-o";
		}
		return "fa fa-file-image-o";
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return getDocument().getFileContentType();
	}

	/**
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		getDocument().setFileContentType(fileContentType);
	}

	/**
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return getDocument().getFileSize();
	}

	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		getDocument().setFileSize(fileSize);
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return getDocument().getFileContent();
	}

	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		getDocument().setFileContent(fileContent);
	}

	/**
	 * @return the receipt
	 */
	public BirthRegistrationDocument getReceipt() {
		if (receipt == null) {
			receipt = new BirthRegistrationDocument();
		}
		return receipt;
	}

	/**
	 * @param receipt the receipt to set
	 */
	public void setReceipt(BirthRegistrationDocument receipt) {
		this.receipt = receipt;
	}

	/**
	 * @return the naturalityType
	 */
	public NaturalityType getNaturalityType() {
		return naturalityType;
	}

	/**
	 * @param naturalityType the naturalityType to set
	 */
	public void setNaturalityType(NaturalityType naturalityType) {
		this.naturalityType = naturalityType;
	}

	/**
	 * @return the lastBirthRegistrarionDate
	 */
	public Date getLastBirthRegistrarionDate() {
		return lastBirthRegistrarionDate;
	}

	/**
	 * @param lastBirthRegistrarionDate the lastBirthRegistrarionDate to set
	 */
	public void setLastBirthRegistrarionDate(Date lastBirthRegistrarionDate) {
		this.lastBirthRegistrarionDate = lastBirthRegistrarionDate;
	}

	/**
	 * @return
	 */
	public BirthFiliation getBirthFiliation() {
		return birthFiliation;
	}

	/**
	 * @param birthFiliation
	 */
	public void setBirthFiliation(BirthFiliation birthFiliation) {
		this.birthFiliation = birthFiliation;
	}

	/**
	 * @return
	 */
	public Date getMarriageDate() {
		return marriageDate;
	}

	/**
	 * @param marriageDate
	 */
	public void setMarriageDate(Date marriageDate) {
		this.marriageDate = marriageDate;
	}

	/**
	 * @return
	 */
	public Integer getMarriageLiveBirth() {
		return marriageLiveBirth;
	}

	/**
	 * @param marriageLiveBirth
	 */
	public void setMarriageLiveBirth(Integer marriageLiveBirth) {
		this.marriageLiveBirth = marriageLiveBirth;
	}

	/**
	 * @return
	 */
	public Integer getMarriageStillborn() {
		return marriageStillborn;
	}

	/**
	 * @param marriageStillborn
	 */
	public void setMarriageStillborn(Integer marriageStillborn) {
		this.marriageStillborn = marriageStillborn;
	}

	/**
	 * @return
	 */
	public Integer getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 */
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/**
	 * @return
	 */
	public BirthLocation getBirthLocation() {
		return birthLocation;
	}

	/**
	 * @param birthLocation
	 */
	public void setBirthLocation(BirthLocation birthLocation) {
		this.birthLocation = birthLocation;
	}

	/**
	 * @return
	 */
	public BirthNature getBirthNature() {
		return birthNature;
	}

	/**
	 * @param birthNature
	 */
	public void setBirthNature(BirthNature birthNature) {
		this.birthNature = birthNature;
	}

	/**
	 * @return
	 */
	public BirthTwin getBirthTwins() {
		return birthTwins;
	}

	/**
	 * @param birthTwins
	 */
	public void setBirthTwins(BirthTwin birthTwins) {
		this.birthTwins = birthTwins;
	}

	/**
	 * @return
	 */
	public Integer getBirthTwinsNumberOfLiveBirth() {
		return birthTwinsNumberOfLiveBirth;
	}

	/**
	 * @param birthTwinsNumberOfLiveBirth
	 */
	public void setBirthTwinsNumberOfLiveBirth(Integer birthTwinsNumberOfLiveBirth) {
		this.birthTwinsNumberOfLiveBirth = birthTwinsNumberOfLiveBirth;
	}

	/**
	 * @return
	 */
	public Integer getBirthTwinsNumberOfStillBirth() {
		return birthTwinsNumberOfStillBirth;
	}

	/**
	 * @param birthTwinsNumberOfStillBirth
	 */
	public void setBirthTwinsNumberOfStillBirth(Integer birthTwinsNumberOfStillBirth) {
		this.birthTwinsNumberOfStillBirth = birthTwinsNumberOfStillBirth;
	}

	/**
	 * @return
	 */
	public Integer getBirthTwinsNumberOfTwins() {
		return birthTwinsNumberOfTwins;
	}

	/**
	 * @param birthTwinsNumberOfTwins
	 */
	public void setBirthTwinsNumberOfTwins(Integer birthTwinsNumberOfTwins) {
		this.birthTwinsNumberOfTwins = birthTwinsNumberOfTwins;
	}

	/**
	 * @return
	 */
	public MidwifeType getMidwifeType() {
		return midwifeType;
	}

	/**
	 * @param midwifeType
	 */
	public void setMidwifeType(MidwifeType midwifeType) {
		this.midwifeType = midwifeType;
	}

	/**
	 * @return
	 */
	public Integer getDurationOfPregnancy() {
		return durationOfPregnancy;
	}

	/**
	 * @param durationOfPregnancy
	 */
	public void setDurationOfPregnancy(Integer durationOfPregnancy) {
		this.durationOfPregnancy = durationOfPregnancy;
	}

	/**
	 * @return the numberOfCommonLiveBirthChildren
	 */
	public Integer getNumberOfCommonLiveBirthChildren() {
		return numberOfCommonLiveBirthChildren;
	}

	/**
	 * @param numberOfCommonLiveBirthChildren the numberOfCommonLiveBirthChildren to
	 *                                        set
	 */
	public void setNumberOfCommonLiveBirthChildren(Integer numberOfCommonLiveBirthChildren) {
		this.numberOfCommonLiveBirthChildren = numberOfCommonLiveBirthChildren;
	}

	/**
	 * @return the numberOfCommonStillbornChildren
	 */
	public Integer getNumberOfCommonStillbornChildren() {
		return numberOfCommonStillbornChildren;
	}

	/**
	 * @param numberOfCommonStillbornChildren the numberOfCommonStillbornChildren to
	 *                                        set
	 */
	public void setNumberOfCommonStillbornChildren(Integer numberOfCommonStillbornChildren) {
		this.numberOfCommonStillbornChildren = numberOfCommonStillbornChildren;
	}

	/**
	 * @return the numberOfPreviousBirth
	 */
	public Integer getNumberOfPreviousBirth() {
		return numberOfPreviousBirth;
	}

	/**
	 * @param numberOfPreviousBirth the numberOfPreviousBirth to set
	 */
	public void setNumberOfPreviousBirth(Integer numberOfPreviousBirth) {
		this.numberOfPreviousBirth = numberOfPreviousBirth;
	}

	/**
	 * @return the numberOfLiveBirth
	 */
	public Integer getNumberOfLiveBirth() {
		return numberOfLiveBirth;
	}

	/**
	 * @param numberOfLiveBirth the numberOfLiveBirth to set
	 */
	public void setNumberOfLiveBirth(Integer numberOfLiveBirth) {
		this.numberOfLiveBirth = numberOfLiveBirth;
	}

	/**
	 * @return the numberOfStillborn22OrMoreWeeks
	 */
	public Integer getNumberOfStillborn22OrMoreWeeks() {
		return numberOfStillborn22OrMoreWeeks;
	}

	/**
	 * @param numberOfStillborn22OrMoreWeeks the numberOfStillborn22OrMoreWeeks to
	 *                                       set
	 */
	public void setNumberOfStillborn22OrMoreWeeks(Integer numberOfStillborn22OrMoreWeeks) {
		this.numberOfStillborn22OrMoreWeeks = numberOfStillborn22OrMoreWeeks;
	}

	/**
	 * @return the numberOfStillbornLessThan22Weeks
	 */
	public Integer getNumberOfStillbornLessThan22Weeks() {
		return numberOfStillbornLessThan22Weeks;
	}

	/**
	 * @param numberOfStillbornLessThan22Weeks the numberOfStillbornLessThan22Weeks
	 *                                         to set
	 */
	public void setNumberOfStillbornLessThan22Weeks(Integer numberOfStillbornLessThan22Weeks) {
		this.numberOfStillbornLessThan22Weeks = numberOfStillbornLessThan22Weeks;
	}

	/**
	 * @return the previousBirthDate
	 */
	public Date getPreviousBirthDate() {
		return previousBirthDate;
	}

	/**
	 * @param previousBirthDate the previousBirthDate to set
	 */
	public void setPreviousBirthDate(Date previousBirthDate) {
		this.previousBirthDate = previousBirthDate;
	}

	/**
	 * @return the previousLiveBirthDate
	 */
	public Date getPreviousLiveBirthDate() {
		return previousLiveBirthDate;
	}

	/**
	 * @param previousLiveBirthDate the previousLiveBirthDate to set
	 */
	public void setPreviousLiveBirthDate(Date previousLiveBirthDate) {
		this.previousLiveBirthDate = previousLiveBirthDate;
	}

	/**
	 * @return the surveyObservations
	 */
	public String getSurveyObservations() {
		return surveyObservations;
	}

	/**
	 * @param surveyObservations the surveyObservations to set
	 */
	public void setSurveyObservations(String surveyObservations) {
		this.surveyObservations = surveyObservations;
	}

	@PostLoad
	@Override
	public void postLoad() {
		super.postLoad();
		if (this.naturality != null) {
			this.naturalityType = this.naturality.getNaturalityType();
		}
	}

	public BirthRegistrationBabyPhoto getBabyPhoto() {

		if (babyPhoto == null) {
			this.babyPhoto = new BirthRegistrationBabyPhoto();
		}

		return babyPhoto;
	}

	public void setBabyPhoto(BirthRegistrationBabyPhoto babyPhoto) {
		this.babyPhoto = babyPhoto;
	}

	public String getOtherHospitalUnit() {
		return otherHospitalUnit;
	}

	public void setOtherHospitalUnit(String otherHospitalUnit) {
		this.otherHospitalUnit = otherHospitalUnit;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}