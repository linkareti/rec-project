package com.linkare.irn.nascimento.web.controller.frontoffice.core;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.extensions.cdi.core.api.scope.conversation.ViewAccessScoped;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.linkare.irn.nascimento.model.core.BirthRegistration;
import com.linkare.irn.nascimento.model.core.BirthRegistrationDocument;
import com.linkare.irn.nascimento.model.core.BirthRegistrationStatus;
import com.linkare.irn.nascimento.model.geographic.Country;
import com.linkare.irn.nascimento.model.geographic.Parish;
import com.linkare.irn.nascimento.model.identification.Address;
import com.linkare.irn.nascimento.model.identification.AddressType;
import com.linkare.irn.nascimento.model.identification.ForeignNaturality;
import com.linkare.irn.nascimento.model.identification.Gender;
import com.linkare.irn.nascimento.model.identification.IdentificationCardType;
import com.linkare.irn.nascimento.model.identification.MaritalStatus;
import com.linkare.irn.nascimento.model.identification.Naturality;
import com.linkare.irn.nascimento.model.identification.NaturalityType;
import com.linkare.irn.nascimento.model.identification.OnBoardNaturality;
import com.linkare.irn.nascimento.model.identification.Parent;
import com.linkare.irn.nascimento.model.identification.PortugueseNaturality;
import com.linkare.irn.nascimento.model.survey.BirthLocation;
import com.linkare.irn.nascimento.service.cdi.EventOperation;
import com.linkare.irn.nascimento.service.cdi.core.BirthRegistrationEvent;
import com.linkare.irn.nascimento.service.core.BirthRegistrationService;
import com.linkare.irn.nascimento.service.geographic.CountryService;
import com.linkare.irn.nascimento.service.geographic.ParishService;
import com.linkare.irn.nascimento.service.message.EmailMessageService;
import com.linkare.irn.nascimento.util.JsfUtils;
import com.linkare.irn.nascimento.util.SelectionRange;
import com.linkare.irn.nascimento.web.auth.AuthenticationBean;
import com.linkare.irn.nascimento.web.auth.LoginBean;
import com.linkare.irn.nascimento.web.controller.BaseController;
import com.linkare.irn.nascimento.web.controller.shared.core.BirthRegistrationControllerAware;
import com.linkare.irn.nascimento.web.controller.shared.core.BirthRegistrationOperation;
import com.linkare.irn.nascimento.web.controller.shared.core.DocumentHelper;
import com.lowagie.text.DocumentException;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateful
@LocalBean
@ViewAccessScoped
@Named
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BirthRegistrationController extends BaseController
		implements SessionSynchronization, BirthRegistrationControllerAware {

	private static final long serialVersionUID = 3639669604954264657L;

	private static final Logger LOG = LoggerFactory.getLogger(BirthRegistrationController.class);

	private static final String FIRST_PARENT_ACCESS_DENIED_DECEASED = "birthRegistration.firstParent.accessDenied.deceasedMessage";

	private static final String FIRST_PARENT_ACCESS_DENIED_TOO_YOUND = "birthRegistration.firstParent.accessDenied.tooYoungAndMaleMessage";

	private static final String ACCESS_DENIED_ID_NUMBER_MISMATCH = "birthRegistration.accessDenied.idNumberMismatchMessage";

	private final static String PDF_CONTENT_TYPE = "application/pdf";

	private final static String REPORT_PATH = "/public/birth_registration/birth_registration_report.xhtml";

	private String uuid;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Inject
	private Event<BirthRegistrationEvent> birthRegistrationEvent;

	@Inject
	private BirthRegistrationService birthRegistrationService;

	@Inject
	private EmailMessageService emailMessageService;

	@Inject
	private CountryService countryService;

	@Inject
	private ParishService parishService;

	@Inject
	private AuthenticationBean authenticationBean;

	private BirthRegistration birthRegistration;

	private BirthRegistrationStep currentStep;

	private String identificationCardNumber;

	private int numberOfParishesToShow = 300;

	private BirthRegistrationOperation operation;

	private String errorKey;

	private boolean refreshRequired;

	private Boolean editMode;

	private Boolean parentsMarriedWithEachOther;

	private Boolean parentsWereMarriedWithEachOther;

	private Boolean parentsKnowEachOther;

	private String accessDeniedMessage;

	private DocumentHelper<BirthRegistrationController> documentHelper;

	private ServiceProviderHelper serviceProviderHelper;

	private boolean completed;

	@Inject
	private LoginBean loginBean;

	private boolean isFirstParent;

	private boolean isRequiredBabyPhoto;

	private StreamedContent receipt;

	@PostConstruct
	@Override
	public void init() {
		super.init();
		this.completed = false;
		this.editMode = Boolean.TRUE;
		this.isFirstParent = true;

		moveToIdentificationSection();
	}

	public boolean isSecondParent() {
		return BirthRegistrationStatus.ELABORATING.equals(birthRegistration.getBirthRegistrationStatus());
	}

	public void load() {
		loadBirthRegistration();
		this.serviceProviderHelper = new ServiceProviderHelper(this);
		if (this.loginBean.isLoggedIn() && loginBean.getHasCitizenRole()) {
			moveToFirstParentSection();
		} else if (!serviceProviderHelper.isDummyMode()) {
			serviceProviderHelper.prepareAuthnRequest();
		}
		this.documentHelper = new DocumentHelper<BirthRegistrationController>(true, this, birthRegistrationService);
	}

	private void loadBirthRegistration() {
		if (StringUtils.isNotBlank(uuid)) {
			this.birthRegistration = birthRegistrationService.findByUUID(uuid);
			if (this.birthRegistration != null) {
				this.editMode = Boolean.FALSE;
			}
		}
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the birthRegistration
	 */
	@Override
	public BirthRegistration getBirthRegistration() {
		if (birthRegistration == null) {
			birthRegistration = new BirthRegistration();
		}
		return birthRegistration;
	}

	/**
	 * @return the isFirstParent
	 */
	public boolean isFirstParent() {
		return isFirstParent;
	}

	public boolean isShowIdentification() {
		return this.currentStep == BirthRegistrationStep.IDENTIFICATION;
	}

	public boolean isShowFirstParent() {
		return this.currentStep == BirthRegistrationStep.FIRST_PARENT;
	}

	public boolean isShowSecondParent() {
		return this.currentStep == BirthRegistrationStep.SECOND_PARENT;
	}

	public boolean isShowRegistration() {
		return this.currentStep == BirthRegistrationStep.REGISTRATION;
	}

	public boolean isShowSurvey() {
		return this.currentStep == BirthRegistrationStep.SURVEY;
	}

	public boolean isShowSummary() {
		return this.currentStep == BirthRegistrationStep.SUMMARY;
	}

	public boolean isShowAccessDenied() {
		return this.currentStep == BirthRegistrationStep.ACCESS_DENIED;
	}

	/**
	 * @return the currentStep
	 */
	public BirthRegistrationStep getCurrentStep() {
		return currentStep;
	}

	public void moveToStep(final BirthRegistrationStep step) {
		if (step == null) {
			moveToIdentificationSection();
		} else {
			switch (step) {
			case FIRST_PARENT:
				moveToFirstParentSection();
				break;
			case SECOND_PARENT:
				moveToSecondParentSection();
				break;
			case REGISTRATION:
				moveToRegistrationSection();
				break;
			case SURVEY:
				moveToSurveySection();
				break;
			case SUMMARY:
				moveToSummarySection();
				break;
			default:
				moveToIdentificationSection();
				break;
			}
		}
	}

	public void moveToIdentificationSection() {
		this.currentStep = BirthRegistrationStep.IDENTIFICATION;
	}

	public void dummyAuthenticate() {
		serviceProviderHelper.dummyInit();
		moveToFirstParentSection();
	}

	public void moveToFirstParentSection() {
		final Parent firstParent = getBirthRegistration().getFirstParent();
		final Parent secondParent = getBirthRegistration().getSecondParent();
		this.currentStep = BirthRegistrationStep.FIRST_PARENT;

		if (!getBirthRegistration().isPersistent()) {
			if (firstParent.getDeceased() == Boolean.TRUE) {
				this.currentStep = BirthRegistrationStep.ACCESS_DENIED;
				this.accessDeniedMessage = getMessage(FIRST_PARENT_ACCESS_DENIED_DECEASED,
						getIdentificationCardNumber());
				return;
			} else if (firstParent.getGender() == Gender.MALE && firstParent.isBelow16Years()) {
				this.currentStep = BirthRegistrationStep.ACCESS_DENIED;
				this.accessDeniedMessage = getMessage(FIRST_PARENT_ACCESS_DENIED_TOO_YOUND);
				return;
			}
		} else {
			if (loginBean.getHasCitizenRole()) {
				if (Objects.equals(firstParent.getIdentificationCardNumber(),
						loginBean.getIdentificationCardNumber())) {
					isFirstParent = true;
				} else if (secondParent != null && Objects.equals(secondParent.getIdentificationCardNumber(),
						loginBean.getIdentificationCardNumber())) {
					isFirstParent = false;
				} else {
					this.currentStep = BirthRegistrationStep.ACCESS_DENIED;
					this.accessDeniedMessage = getMessage(ACCESS_DENIED_ID_NUMBER_MISMATCH,
							getIdentificationCardNumber());
				}
			}
		}
	}

	public void moveToSecondParentSection() {
		this.currentStep = BirthRegistrationStep.SECOND_PARENT;
	}

	public void moveToRegistrationSection() {
		if (getBirthRegistration().getNationality() == null) {
			final PortugueseNaturality naturality = new PortugueseNaturality();
			naturality.setCountry(getApplicationFactory().getPortugal());
			getBirthRegistration().setNaturality(naturality);
			getBirthRegistration().setNaturalityType(NaturalityType.PORTUGUESE);

			getBirthRegistration().setNationality(getApplicationFactory().getPortugal());
		}

		this.currentStep = BirthRegistrationStep.REGISTRATION;
	}

	public void moveToSurveySection() {
		this.currentStep = BirthRegistrationStep.SURVEY;

		getBirthRegistration().setBirthLocation(BirthLocation.HOSPITAL);
	}

	public void moveToSummarySection() {
		this.currentStep = BirthRegistrationStep.SUMMARY;
	}

	/**
	 * @return the identificationCardNumber
	 */
	public String getIdentificationCardNumber() {
		return identificationCardNumber;
	}

	/**
	 * @param identificationCardNumber the identificationCardNumber to set
	 */
	public void setIdentificationCardNumber(String identificationCardNumber) {
		this.identificationCardNumber = identificationCardNumber;
	}

	/**
	 * @return the numberOfParishesToShow
	 */
	public int getNumberOfParishesToShow() {
		return numberOfParishesToShow;
	}

	/**
	 * @param numberOfParishesToShow the numberOfParishesToShow to set
	 */
	public void setNumberOfParishesToShow(int numberOfParishesToShow) {
		this.numberOfParishesToShow = numberOfParishesToShow;
	}

	public void onFirstParentMaritalStatusChange() {
		this.parentsMarriedWithEachOther = null;
		this.parentsWereMarriedWithEachOther = null;
		this.parentsKnowEachOther = null;
		getBirthRegistration().setSecondParent(null);
	}

	public void onBirthFiliationChange() {
		getBirthRegistration().setMarriageDate(null);
		getBirthRegistration().setMarriageLiveBirth(null);
		getBirthRegistration().setMarriageStillborn(null);
	}

	public void onBirthNatureChange() {
		getBirthRegistration().setBirthTwins(null);
		getBirthRegistration().setBirthTwinsNumberOfLiveBirth(null);
		getBirthRegistration().setBirthTwinsNumberOfStillBirth(null);
		getBirthRegistration().setBirthTwinsNumberOfTwins(null);

	}

	public void onNaturalityTypeChange(final Parent parent) {
		final NaturalityType naturalityType = parent.getNaturalityType();
		Naturality naturality = null;
		if (naturalityType != null) {
			switch (naturalityType) {
			case PORTUGUESE:
				naturality = new PortugueseNaturality();
				naturality.setCountry(getApplicationFactory().getPortugal());
				break;
			case FOREIGN:
				naturality = new ForeignNaturality();
				break;
			case ON_BOARD:
				naturality = new OnBoardNaturality();
				naturality.setCountry(getApplicationFactory().getPortugal());
				break;
			default:
				break;
			}
		}

		parent.setNaturality(naturality);
	}

	public void onNaturalityTypeChange() {
		final NaturalityType naturalityType = getBirthRegistration().getNaturalityType();
		Naturality naturality = null;
		if (naturalityType != null) {
			switch (naturalityType) {
			case PORTUGUESE:
				naturality = new PortugueseNaturality();
				naturality.setCountry(getApplicationFactory().getPortugal());
				break;
			case FOREIGN:
				naturality = new ForeignNaturality();
				break;
			case ON_BOARD:
				naturality = new OnBoardNaturality();
				naturality.setCountry(getApplicationFactory().getPortugal());
				break;
			default:
				break;
			}
		}

		getBirthRegistration().setNaturality(naturality);
	}

	public void onFirstParentAddressTypeChange() {
		onAddressTypeChange(getBirthRegistration().getFirstParent(), true);
	}

	public void onSecondParentAddressTypeChange() {
		onAddressTypeChange(getBirthRegistration().getSecondParent(), false);
	}

	public void onAddressTypeChange(final Parent parent, final boolean firstParent) {
		final Address address = parent.getAddress();

		address.setStreet(null);
		address.setZipCode(null);
		address.setLocation(null);
		address.setParish(null);
		address.setCountry(null);

		final AddressType addressType = address.getAddressType();
		if (addressType != null) {
			switch (addressType) {
			case PORTUGUESE:
				address.setCountry(getApplicationFactory().getPortugal());
				if (firstParent) {
					getServiceProviderHelper().initFirstParentAddressFromResponseData(parent);
				}
				break;
			case FOREIGN:
				break;
			default:
				break;
			}
		}
	}

	public void onParentsMarriedWithEachOtherChange() {
		this.parentsKnowEachOther = null;
		getBirthRegistration().setSecondParent(null);
		if (this.parentsMarriedWithEachOther == Boolean.TRUE) {
			initSecondParent(getApplicationFactory().getPortugal(), null, IdentificationCardType.CITIZEN_CARD,
					MaritalStatus.MARRIED);
		}
	}

	public void onParentsWereMarriedWithEachOtherChange() {
		this.parentsKnowEachOther = null;
		getBirthRegistration().setSecondParent(null);
		if (this.parentsWereMarriedWithEachOther == Boolean.TRUE) {
			initSecondParent(getApplicationFactory().getPortugal(), null, IdentificationCardType.CITIZEN_CARD, null);
		}
	}

	public void onParentsKnowEachOtherChange() {
		getBirthRegistration().setSecondParent(null);
		if (this.parentsKnowEachOther == Boolean.TRUE) {
			initSecondParent(getApplicationFactory().getPortugal(), null, IdentificationCardType.CITIZEN_CARD, null);
		}
	}

	public void onSecondParentDeceasedChange(final Parent parent) {
		final Boolean deceased = parent.getDeceased();
		// reset second parent
		initSecondParent(null, deceased, null, null);

		if (deceased == Boolean.FALSE) {
			getBirthRegistration().getSecondParent().setNationality(getApplicationFactory().getPortugal());
			getBirthRegistration().getSecondParent().setIdentificationCardType(IdentificationCardType.CITIZEN_CARD);
		}
	}

	private void initSecondParent(final Country nationality, final Boolean deceased,
			final IdentificationCardType identificationCardType, final MaritalStatus maritalStatus) {
		final Parent secondParent = new Parent();
		secondParent.setNationality(nationality);
		secondParent.setDeceased(deceased);
		secondParent.setIdentificationCardType(identificationCardType);
		secondParent.setMaritalStatus(maritalStatus);
		getBirthRegistration().setSecondParent(secondParent);
	}

	public List<Parish> completeActiveParish(final String name) {
		return parishService.findActiveWithNameLikeInFullPath(name, new SelectionRange(0, getNumberOfParishesToShow()));
	}

	public List<Parish> completeAnyParish(final String name) {
		return parishService.findAllWithNameLikeInFullPath(name, new SelectionRange(0, getNumberOfParishesToShow()));
	}

	/**
	 * @return the editMode
	 */
	public Boolean getEditMode() {
		return editMode;
	}

	/**
	 * @param editMode the editMode to set
	 */
	public void setEditMode(Boolean editMode) {
		this.editMode = editMode;
	}

	/**
	 * @return the parentsMarriedWithEachOther
	 */
	public Boolean getParentsMarriedWithEachOther() {
		return parentsMarriedWithEachOther;
	}

	/**
	 * @param parentsMarriedWithEachOther the parentsMarriedWithEachOther to set
	 */
	public void setParentsMarriedWithEachOther(Boolean parentsMarriedWithEachOther) {
		this.parentsMarriedWithEachOther = parentsMarriedWithEachOther;
	}

	/**
	 * @return the parentsWereMarriedWithEachOther
	 */
	public Boolean getParentsWereMarriedWithEachOther() {
		return parentsWereMarriedWithEachOther;
	}

	/**
	 * @param parentsWereMarriedWithEachOther the parentsWereMarriedWithEachOther to
	 *                                        set
	 */
	public void setParentsWereMarriedWithEachOther(Boolean parentsWereMarriedWithEachOther) {
		this.parentsWereMarriedWithEachOther = parentsWereMarriedWithEachOther;
	}

	/**
	 * @return the parentsKnowEachOther
	 */
	public Boolean getParentsKnowEachOther() {
		return parentsKnowEachOther;
	}

	/**
	 * @param parentsKnowEachOther the parentsKnowEachOther to set
	 */
	public void setParentsKnowEachOther(Boolean parentsKnowEachOther) {
		this.parentsKnowEachOther = parentsKnowEachOther;
	}

	/**
	 * @return the accessDeniedMessage
	 */
	public String getAccessDeniedMessage() {
		return accessDeniedMessage;
	}

	/**
	 * @return the refreshRequired
	 */
	public boolean isRefreshRequired() {
		return refreshRequired;
	}

	/**
	 * @param refreshRequired the refreshRequired to set
	 */
	public void setRefreshRequired(boolean refreshRequired) {
		this.refreshRequired = refreshRequired;
	}

	/**
	 * @return the completed
	 */
	public boolean isCompleted() {
		return completed;
	}

	/**
	 * @return the documentHelper
	 */
	public DocumentHelper<BirthRegistrationController> getDocumentHelper() {
		return documentHelper;
	}

	/**
	 * @return the serviceProviderHelper
	 */
	public ServiceProviderHelper getServiceProviderHelper() {
		return serviceProviderHelper;
	}

	/**
	 * @return the authenticationBean
	 */
	AuthenticationBean getAuthenticationBean() {
		return authenticationBean;
	}

	private String save() {
		try {
			if (getBirthRegistration().isPersistent()) {
				birthRegistrationService.update(getBirthRegistration());
				entityManager.flush();
			} else {
				birthRegistrationService.create(getBirthRegistration());
				entityManager.flush();

				if (birthRegistration.getBirthRegistrationStatus() == BirthRegistrationStatus.ELABORATING) {
					emailMessageService.registerNotificationForFirstParent(birthRegistration);
					emailMessageService
							.registerNewBirthRegistrationNotificationForCivilRegistrationOffice(birthRegistration);
					// registerNewBirthRegistrationNotificationForHospitalUnit(birthRegistration);
				} else {
					emailMessageService.registerNotificationsWhenSecondParentConfirmationIsRequired(birthRegistration);
				}
			}

			return "/public/birth_registration/completed.xhtml?faces-redirect=true&uuid="
					+ getBirthRegistration().getUuid();
		} catch (OptimisticLockException e) {
			errorKey = "message.error.optimisticLockException";
			refreshRequired = true;
		} catch (final Exception e) {
			logError(e);
			// let the sessionSynchronization do its job but simply do not let the Exception
			// go up...
		}
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String submit() {
		this.operation = BirthRegistrationOperation.FIRST_PARENT_SUBMISSION;
		final Parent secondParent = getBirthRegistration().getSecondParent();

		if (mayBeSubmittedDirectly(secondParent)) {
			getBirthRegistration().setBirthRegistrationStatus(BirthRegistrationStatus.ELABORATING);
		} else {
			getBirthRegistration()
					.setBirthRegistrationStatus(BirthRegistrationStatus.REQUIRES_2ND_FOREFATHER_CONFIRMATION);
		}
		getBirthRegistration().setLastBirthRegistrarionDate(Calendar.getInstance().getTime());

		final String result = save();

		birthRegistrationEvent.fire(new BirthRegistrationEvent(EventOperation.CREATE, getBirthRegistration()));

		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void storeReceipt(@Observes(during = TransactionPhase.AFTER_SUCCESS) final BirthRegistrationEvent event) {
		if (event.getOperation() == EventOperation.CREATE) {
			final String url = getContextPathUrl() + REPORT_PATH + "?uuid=" + getBirthRegistration().getUuid();

			try {
				final ITextRenderer renderer = new ITextRenderer();
				renderer.setDocument(url);
				renderer.layout();

				final BirthRegistrationDocument receipt = new BirthRegistrationDocument();
				try (final ByteArrayOutputStream responseOutputStream = new ByteArrayOutputStream()) {
					renderer.createPDF(responseOutputStream);

					receipt.setFileContentType(PDF_CONTENT_TYPE);
					receipt.setFilename(
							"declaracao-nascimento-" + getBirthRegistration().getRegistrationNumber() + ".pdf");
					receipt.setFileSize((long) responseOutputStream.size());
					receipt.setFileContent(responseOutputStream.toByteArray());
				}
				getBirthRegistration().setReceipt(receipt);
				birthRegistration = birthRegistrationService.update(getBirthRegistration());
			} catch (IOException | DocumentException ex) {
				LOG.error("Errors building PDF", ex);
			}
		}
	}

	private boolean mayBeSubmittedDirectly(final Parent secondParent) {
		return parentsKnowEachOther == Boolean.FALSE || parentsMarriedWithEachOther == Boolean.TRUE
				|| (secondParent != null && secondParent.getDeceased() == Boolean.TRUE);
	}

	// Begin second parent operations
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String acceptSubmission() {
		this.operation = BirthRegistrationOperation.SECOND_PARENT_CONFIRMATION_ACCEPTANCE;
		birthRegistrationService.acceptSubmission(getBirthRegistration());
		return save();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String rejectSubmission() {
		this.operation = BirthRegistrationOperation.SECOND_PARENT_CONFIRMATION_REJECTION;
		birthRegistrationService.rejectSubmission(getBirthRegistration());
		return save();
	}
	// End second parent operations

	@Override
	public void afterBegin() throws EJBException, RemoteException {
		// do nothing
	}

	@Override
	public void beforeCompletion() throws EJBException, RemoteException {
		// do nothing
	}

	@Override
	public void afterCompletion(boolean committed) throws EJBException, RemoteException {
		if (committed) {
			if (operation != null) {
				JsfUtils.addGlobalInfoMessage(getMessage(operation.getMessageKey()));
			}
		} else {
			if (StringUtils.isNotBlank(errorKey)) {
				JsfUtils.addGlobalErrorMessage(getMessage(errorKey));
			} else {
				JsfUtils.addGlobalErrorMessage(getMessage(operation.getErrorKey()));
			}
		}

		this.errorKey = null;
		operation = null;
	}

	public StreamedContent getFile() {
		return getDocumentHelper().getFile();
	}

	/**
	 * @return the countryService
	 */
	protected CountryService getCountryService() {
		return countryService;
	}

	/**
	 * @return the parishService
	 */
	protected ParishService getParishService() {
		return parishService;
	}

	public String getContextPathUrl() {
		final FacesContext facesContext = FacesContext.getCurrentInstance();
		final ExternalContext externalContext = facesContext.getExternalContext();

		// final String serverName = externalContext.getRequestServerName();
		// final String port = String.valueOf(externalContext.getRequestServerPort());

		// To run this application on docker
		final String serverName = "localhost";
		final String port = "8080";

		final String contextPath = externalContext.getRequestContextPath();
		final String protocol = externalContext.getRequestScheme();

		return protocol + "://" + serverName + ":" + port + contextPath;
	}

	public StreamedContent getReceipt() {
		if (receipt == null) {
			BirthRegistrationDocument receiptDocument = getBirthRegistration().getReceipt();
			this.receipt = new DefaultStreamedContent(new ByteArrayInputStream(receiptDocument.getFileContent()),
					receiptDocument.getFileContentType(), receiptDocument.getFilename());
		}
		return receipt;
	}

	public boolean isLoggedIn() {
		return loginBean.isLoggedIn();
	}

	public boolean isRequiredBabyPhoto() {
		return isRequiredBabyPhoto;
	}

	public void setRequiredBabyPhoto(boolean isRequiredBabyPhoto) {
		this.isRequiredBabyPhoto = isRequiredBabyPhoto;
	}

}