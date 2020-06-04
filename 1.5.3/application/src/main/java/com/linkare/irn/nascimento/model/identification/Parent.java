package com.linkare.irn.nascimento.model.identification;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

import org.hibernate.validator.constraints.Email;
import org.joda.time.LocalDate;
import org.joda.time.Years;

import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationFirstParentValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationSecondParentValidation;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationSurveyValidation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "parent")
public class Parent extends DomainObject {

    private static final long serialVersionUID = 541450644360237111L;

    @Column(name = "deceased")
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private Boolean deceased;

    @Column(name = "given_name", length = 150)
    @Size(max = 150)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private String givenName;

    @Column(name = "family_name", length = 150)
    @Size(max = 150)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private String familyName;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    @Past(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private Date dateOfBirth;

    @Column(name = "father_name", length = 255)
    @Size(max = 255)
    private String fatherName;

    @Column(name = "mother_name", length = 255)
    @Size(max = 255)
    private String motherName;

    @Column(name = "email", length = 150)
    @Size(max = 150)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    @Email(regexp = ".+@.+\\..+", groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private String email;

    @Column(name = "telephone", length = 50)
    @Size(max = 50)
    private String telephone;

    @Column(name = "identification_card_number", length = 75)
    @Size(max = 75)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private String identificationCardNumber;

    @Column(name = "identification_card_type", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private IdentificationCardType identificationCardType;

    @Column(name = "gender", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private Gender gender;

    @Column(name = "marital_status", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private MaritalStatus maritalStatus;

    // Survey
    // Grau de instrução completo
    @Column(name = "academic_degree", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = BirthRegistrationSurveyValidation.class)
    private AcademicDegree academicDegree;

    // Condição perante o trabalho
    @Column(name = "job_condition", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = BirthRegistrationSurveyValidation.class)
    private JobCondition jobCondition;

    // Profissão
    @Column(name = "job", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = BirthRegistrationSurveyValidation.class)
    private Job job;

    @Column(name = "job_status", length = 75)
    @Enumerated(EnumType.STRING)
    @NotNull(groups = BirthRegistrationSurveyValidation.class)
    private JobStatus jobStatus;

    // Número de filhos vivos não comuns
    @Column(name = "number_of_children_non_common")
    @NotNull(groups = BirthRegistrationSurveyValidation.class)
    @Min(value = 0, groups = BirthRegistrationSurveyValidation.class)
    @Max(value = 20, groups = BirthRegistrationSurveyValidation.class)
    private Integer numberOfChildrenNonCommon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "key_naturality")
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private Naturality naturality;

    @Embedded
    private Address address;

    @ManyToOne
    @JoinColumn(name = "key_nationality")
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private Country nationality;

    @Transient
    @NotNull(groups = { BirthRegistrationFirstParentValidation.class, BirthRegistrationSecondParentValidation.class })
    private NaturalityType naturalityType;

    /**
     * @return the deceased
     */
    public Boolean getDeceased() {
	return deceased;
    }

    /**
     * @param deceased
     *            the deceased to set
     */
    public void setDeceased(Boolean deceased) {
	this.deceased = deceased;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
	return givenName;
    }

    /**
     * @param givenName
     *            the givenName to set
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
     * @param familyName
     *            the familyName to set
     */
    public void setFamilyName(String familyName) {
	this.familyName = familyName;
    }

    /**
     * @return the name
     */
    public String getName() {
	return String.format("%s %s", givenName, familyName);
    }

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth() {
	return dateOfBirth;
    }

    /**
     * @param dateOfBirth
     *            the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth) {
	this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the fatherName
     */
    public String getFatherName() {
	return fatherName;
    }

    /**
     * @param fatherName
     *            the fatherName to set
     */
    public void setFatherName(String fatherName) {
	this.fatherName = fatherName;
    }

    /**
     * @return the motherName
     */
    public String getMotherName() {
	return motherName;
    }

    /**
     * @param motherName
     *            the motherName to set
     */
    public void setMotherName(String motherName) {
	this.motherName = motherName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
	return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * @return the telephone
     */
    public String getTelephone() {
	return telephone;
    }

    /**
     * @param telephone
     *            the telephone to set
     */
    public void setTelephone(String telephone) {
	this.telephone = telephone;
    }

    /**
     * @return the identificationCardNumber
     */
    public String getIdentificationCardNumber() {
	return identificationCardNumber;
    }

    /**
     * @param identificationCardNumber
     *            the identificationCardNumber to set
     */
    public void setIdentificationCardNumber(String identificationCardNumber) {
	this.identificationCardNumber = identificationCardNumber;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
	return gender;
    }

    /**
     * @param gender
     *            the gender to set
     */
    public void setGender(Gender gender) {
	this.gender = gender;
    }

    /**
     * @return the maritalStatus
     */
    public MaritalStatus getMaritalStatus() {
	return maritalStatus;
    }

    /**
     * @param maritalStatus
     *            the maritalStatus to set
     */
    public void setMaritalStatus(MaritalStatus maritalStatus) {
	this.maritalStatus = maritalStatus;
    }

    /**
     * @return the identificationCardType
     */
    public IdentificationCardType getIdentificationCardType() {
	return identificationCardType;
    }

    /**
     * @param identificationCardType
     *            the identificationCardType to set
     */
    public void setIdentificationCardType(IdentificationCardType identificationCardType) {
	this.identificationCardType = identificationCardType;
    }

    /**
     * @return the academicDegree
     */
    public AcademicDegree getAcademicDegree() {
	return academicDegree;
    }

    /**
     * @param academicDegree
     *            the academicDegree to set
     */
    public void setAcademicDegree(AcademicDegree academicDegree) {
	this.academicDegree = academicDegree;
    }

    /**
     * @return the jobCondition
     */
    public JobCondition getJobCondition() {
	return jobCondition;
    }

    /**
     * @param jobCondition
     *            the jobCondition to set
     */
    public void setJobCondition(JobCondition jobCondition) {
	this.jobCondition = jobCondition;
    }

    /**
     * @return the job
     */
    public Job getJob() {
	return job;
    }

    /**
     * @param job
     *            the job to set
     */
    public void setJob(Job job) {
	this.job = job;
    }

    /**
     * @return the jobStatus
     */
    public JobStatus getJobStatus() {
	return jobStatus;
    }

    /**
     * @param jobStatus
     *            the jobStatus to set
     */
    public void setJobStatus(JobStatus jobStatus) {
	this.jobStatus = jobStatus;
    }

    /**
     * @return the numberOfChildrenNonCommon
     */
    public Integer getNumberOfChildrenNonCommon() {
	return numberOfChildrenNonCommon;
    }

    /**
     * @param numberOfChildrenNonCommon
     *            the numberOfChildrenNonCommon to set
     */
    public void setNumberOfChildrenNonCommon(Integer numberOfChildrenNonCommon) {
	this.numberOfChildrenNonCommon = numberOfChildrenNonCommon;
    }

    /**
     * @return the naturality
     */
    public Naturality getNaturality() {
	return naturality;
    }

    /**
     * @param naturality
     *            the naturality to set
     */
    public void setNaturality(Naturality naturality) {
	this.naturality = naturality;
    }

    /**
     * @return the address
     */
    public Address getAddress() {
	if (address == null) {
	    address = new Address();
	}
	return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(Address address) {
	this.address = address;
    }

    /**
     * @return the nationality
     */
    public Country getNationality() {
	return nationality;
    }

    /**
     * @param nationality
     *            the nationality to set
     */
    public void setNationality(Country nationality) {
	this.nationality = nationality;
    }

    /**
     * @return the naturalityType
     */
    public NaturalityType getNaturalityType() {
	return naturalityType;
    }

    /**
     * @param naturalityType
     *            the naturalityType to set
     */
    public void setNaturalityType(NaturalityType naturalityType) {
	this.naturalityType = naturalityType;
    }

    @PostLoad
    @Override
    public void postLoad() {
	super.postLoad();
	if (this.naturality != null) {
	    this.naturalityType = this.naturality.getNaturalityType();
	}
    }

    /**
     * @return
     */
    public boolean isBelow16Years() {
	if (dateOfBirth != null) {
	    final LocalDate birthdate = new LocalDate(dateOfBirth);
	    final LocalDate now = new LocalDate();
	    Years age = Years.yearsBetween(birthdate, now);
	    return age.getYears() < 16;
	}
	return false;
    }
}