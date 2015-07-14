package com.linkare.rec.web.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jws.WebService;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.linkare.rec.web.RepositoryFacade;
import com.linkare.rec.web.mail.Attachment;
import com.linkare.rec.web.mail.MailFormatEnum;
import com.linkare.rec.web.mail.MailMessageRequest;
import com.linkare.rec.web.mail.MailServiceRemote;
import com.linkare.rec.web.mail.MimePart;
import com.linkare.rec.web.mail.NoValidRecipientsFoundForMessage;
import com.linkare.rec.web.model.ErrorMessage;
import com.linkare.rec.web.model.FailedMailMessage;
import com.linkare.rec.web.model.MessageCategory;
import com.linkare.rec.web.model.util.BusinessException;
import com.linkare.rec.web.model.util.ExperimentDataXSVConverter;
import com.linkare.rec.web.repository.ByteArrayValueDTO;
import com.linkare.rec.web.repository.DataProducerDTO;
import com.linkare.rec.web.repository.DateTimeDTO;
import com.linkare.rec.web.repository.SamplesPacketDTO;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@Stateless(name = "mailService")
@Remote(value = MailServiceRemote.class)
@Local(value = MailServiceLocal.class)
@WebService(endpointInterface = "com.linkare.rec.web.mail.MailServiceRemote", name = "MailServiceWS", serviceName = "rec-services", portName = "mail", targetNamespace = "http://webservices.linkare.com/rec")
public class MailServiceBean implements MailServiceRemote, MailServiceLocal {

	private final static Logger LOGGER = java.util.logging.Logger
			.getLogger(MailServiceBean.class.getName());

	private static final String ERROR_MESSAGES_FILE = "errorMessages";
	private static final String MAIL_MESSAGES_FILE = "mailMessages";

	@Resource(mappedName = "mail/mailSession")
	private javax.mail.Session mailSession;

	@Resource(mappedName = "mailConnectionFactory")
	private ConnectionFactory mailConnectionFactory;

	@Resource(mappedName = "jms/MailMessages")
	private Destination queue;

	// @Resource(name = "recipientsPerBlockMaxSize")
	// private int recipientsPerBlockMaxSize;

	// @Resource(name = "maxFailCount")
	// private Integer maxFailCount;

	// @Resource(mappedName = "sendMailRetryMillis")
	// private int sendMailRetryMillis;

	@Resource
	private TimerService timerService;

	@EJB(beanInterface = ErrorMessageServiceLocal.class)
	private ErrorMessageServiceLocal errorMessageService;

	@EJB(beanInterface = RepositoryFacade.class)
	private RepositoryFacade repository;

	/**
	 * Defines the {@link Locale} of the client that is currently using this
	 * instance of the EJB. This must be used only inside a transaction, to
	 * avoid sending the Locale as parameter in every method call made within a
	 * transaction, but when used, it should be nulled at the end of the method.
	 */
	private Locale currentClientLocale;

	public ResourceBundle getErrorMessagesResouceBundle() {
		return ResourceBundle.getBundle(ERROR_MESSAGES_FILE);
	}

	public ResourceBundle getMailMessagesResouceBundle() {
		return ResourceBundle.getBundle(
				MAIL_MESSAGES_FILE,
				currentClientLocale != null ? currentClientLocale : Locale
						.getDefault(), Control
						.getNoFallbackControl(Control.FORMAT_PROPERTIES));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void sendMessages(MailMessageRequest mailMessageRequest,
			int numberOfTries) {

		currentClientLocale = mailMessageRequest.getClientLocale();

		final String experimentConfiguration = mailMessageRequest.getContent();

		try {

			numberOfTries++;

			DataProducerDTO experienceData = repository
					.getExperimentResultByOID(mailMessageRequest.getSubject());
			if (experienceData == null) {
				if (numberOfTries < mailMessageRequest.getMaxFailCount()) {
					System.out
							.println("The experiment data is still not available. A retry will me made in "
									+ (numberOfTries
											* mailMessageRequest
													.getSendMailRetryMillis() / 1000)
									+ " seconds.");
					sendToTimer(mailMessageRequest, numberOfTries);
				} else {
					reportMessageError(getErrorMessagesResouceBundle()
							.getString("mail.experience.unavailable"),
							mailMessageRequest.getSubject(), numberOfTries);
				}
				return;
			}

			String bodyMessage = generateBodyMessage(experienceData);
			mailMessageRequest.setContent(bodyMessage);

			List<Attachment> attachments = generateExperimentMailAttachments(
					experienceData, experimentConfiguration);
			mailMessageRequest.setAttachments(attachments);

			String subject = generateSubject(experienceData);
			mailMessageRequest.setSubject(subject);

			Message message = new MimeMessage(mailSession);
			prepareMessageToSend(message, mailMessageRequest);
			Transport.send(message);

			LOGGER.log(Level.FINEST, "Message sent to mailserver");

		} catch (MessagingException e) {
			reportMessageError(
					getErrorMessagesResouceBundle().getString(
							"mail.server.failed"),
					mailMessageRequest.getSubject(),
					Arrays.toString(mailMessageRequest.getRecipients()),
					numberOfTries);
			if (numberOfTries < mailMessageRequest.getMaxFailCount()) {
				sendToTimer(mailMessageRequest, numberOfTries);
			}
		} catch (RemoteException e) {
			reportMessageError(
					getErrorMessagesResouceBundle().getString(
							"mail.invalid.oid"),
					mailMessageRequest.getSubject());
			if (numberOfTries < mailMessageRequest.getMaxFailCount()) {
				sendToTimer(mailMessageRequest, numberOfTries);
			}
		} catch (IOException e) {
			reportMessageError(
					getErrorMessagesResouceBundle().getString(
							"mail.create.attachment"),
					mailMessageRequest.getSubject());
			if (numberOfTries < mailMessageRequest.getMaxFailCount()) {
				sendToTimer(mailMessageRequest, numberOfTries);
			}
		} finally {
			// Clear the Locale before putting the EJB back in the pool.
			currentClientLocale = null;
		}
	}

	private String generateSubject(DataProducerDTO experienceData) {

		Date experimentDate = getExperienceStart(experienceData.getAcqHeader()
				.getTimeStart());

		String dayOfYear = DateFormat.getDateInstance(DateFormat.SHORT,
				currentClientLocale).format(experimentDate);
		String hour = DateFormat.getTimeInstance(DateFormat.MEDIUM,
				currentClientLocale).format(experimentDate);
		String timezone = new SimpleDateFormat("z").format(experimentDate);

		String subjectPattern = getMailMessagesResouceBundle().getString(
				"mail.subject.text");
		return MessageFormat.format(subjectPattern, experienceData
				.getAcqHeader().getFamiliarName(), dayOfYear, hour, timezone);
	}

	private String generateBodyMessage(DataProducerDTO experienceData) {

		String bodyMessage = getMailMessagesResouceBundle().getString(
				"mail.body.text");
		String experimentName = experienceData.getAcqHeader().getFamiliarName();
		char decimalSeparator = DecimalFormatSymbols.getInstance(
				currentClientLocale).getDecimalSeparator();
		char groupingSeparator = DecimalFormatSymbols.getInstance(
				currentClientLocale).getGroupingSeparator();

		StringBuilder localeString = new StringBuilder(
				currentClientLocale.getDisplayLanguage(currentClientLocale));
		if (currentClientLocale.getCountry() != null
				&& !currentClientLocale.getCountry().equals("")) {
			localeString
					.append(" (")
					.append(currentClientLocale
							.getDisplayCountry(currentClientLocale))
					.append(")");
		}

		return MessageFormat.format(bodyMessage, experimentName,
				experienceData.getUser(), localeString.toString(),
				decimalSeparator, groupingSeparator, decimalSeparator,
				groupingSeparator);
	}

	/**
	 * Generates all the attachments for the e-mail with the experiment results.
	 * 
	 * @param experimentData
	 *            Must contain the experiment data serialized and also the
	 *            information about the channels.
	 * @param experienceConfiguration
	 *            A string representing the configuration for the experiment.
	 * @return
	 * @throws IOException
	 */
	private List<Attachment> generateExperimentMailAttachments(
			DataProducerDTO experimentData, String experienceConfiguration)
			throws IOException {

		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.log(Level.FINEST, experimentData.toString());
		}

		List<Attachment> listOfAttachments = new ArrayList<Attachment>();
		List<SamplesPacketDTO> samples = getSamplePacketList(experimentData);
		if (samples == null) {
			return listOfAttachments;
		}
		experimentData.setSamplesPacketMatrix(samples);

		// Attachment configurationFile =
		// generateExperimentConfigurationAttachment(experienceData.getAcqHeader().getHardwareParameters());
		Attachment configurationFile = generateExperimentConfigurationAttachment(experienceConfiguration);
		listOfAttachments.add(configurationFile);

		String binaryFilename = getMailMessagesResouceBundle().getString(
				"attachment.binary.filename.prefix");
		ExperimentDataXSVConverter converter = new ExperimentDataXSVConverter(
				'\t', binaryFilename, currentClientLocale);
		byte[] tsvFileByteArray = converter.toByteArray(experimentData);
		Map<String, ByteArrayValueDTO> otherAttachmentsMap = converter
				.getReferencedBinaries();

		String tsvFilename = getMailMessagesResouceBundle().getString(
				"attachment.data.filename");
		Attachment tsvFile = new Attachment();
		tsvFile.setMimeType("text/tab-separated-values");
		tsvFile.setFileName(tsvFilename + ".csv");
		tsvFile.setFileContent(tsvFileByteArray);

		listOfAttachments.add(tsvFile);
		if (otherAttachmentsMap != null) {
			listOfAttachments
					.addAll(generateExperimentMailAdditionalAttachments(otherAttachmentsMap));
		}

		String executionDate = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss_z",
				currentClientLocale).format(getExperienceStart(experimentData
				.getAcqHeader().getTimeStart()));
		int nameSeparatorCharIndex = experimentData.getOid().indexOf("/");
		String fileName = experimentData.getOid().substring(0,
				nameSeparatorCharIndex);
		StringBuilder buildFileName = new StringBuilder(fileName).append("_")
				.append(executionDate).append(".zip");
		Attachment zipFile = zipAttachments(buildFileName.toString(),
				listOfAttachments);

		listOfAttachments = new ArrayList<Attachment>();
		listOfAttachments.add(zipFile);

		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.log(Level.FINEST, listOfAttachments.toString());
		}

		return listOfAttachments;
	}

	private Date getExperienceStart(DateTimeDTO dto) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dto.getDate());
		calendar.set(Calendar.HOUR_OF_DAY, dto.getHours());
		calendar.set(Calendar.MINUTE, dto.getMinutes());
		calendar.set(Calendar.SECOND, dto.getSeconds());

		return calendar.getTime();
	}

	private Attachment zipAttachments(String filename,
			List<Attachment> listOfAttachments) {

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ZipOutputStream zipOutput = new ZipOutputStream(byteStream);

		try {
			for (Attachment attachment : listOfAttachments) {
				ZipEntry entry = new ZipEntry(attachment.getFileName());
				zipOutput.putNextEntry(entry);
				zipOutput.write(attachment.getFileContent());
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		} finally {
			try {
				zipOutput.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Attachment attachment = new Attachment();
		attachment.setFileContent(byteStream.toByteArray());
		attachment.setMimeType("application/zip");
		attachment.setFileName(filename);
		return attachment;
	}

	private Attachment generateExperimentConfigurationAttachment(
			String configuration) {

		String configurationFilename = getMailMessagesResouceBundle()
				.getString("attachment.configuration.filename");
		Attachment configurationFile = new Attachment();
		configurationFile.setMimeType("text/plain");
		configurationFile.setFileName(configurationFilename + ".txt");
		configurationFile.setFileContent(configuration.getBytes());

		return configurationFile;
	}

	/**
	 * Generates all the attachments "referenced" in cells of the tsv file that
	 * represent byte[] data. Each file maps to a value in a tsv cell with the
	 * same value as the file name (without file extension).
	 * 
	 * @param otherAttachmentsMap
	 * @return
	 */
	private List<? extends Attachment> generateExperimentMailAdditionalAttachments(
			Map<String, ByteArrayValueDTO> otherAttachmentsMap) {

		List<Attachment> attachments = new ArrayList<Attachment>();

		for (String key : otherAttachmentsMap.keySet()) {

			ByteArrayValueDTO byteArrayValue = otherAttachmentsMap.get(key);

			String mimeType = byteArrayValue.getMimeType();
			int mimeTypeSeparatorIndex = mimeType.indexOf("/");

			String fileExtension = mimeTypeSeparatorIndex != -1 ? mimeType
					.substring(mimeTypeSeparatorIndex) : "bin";
			Attachment attachment = new Attachment(key + "." + fileExtension,
					byteArrayValue.getData(), mimeType);

			attachments.add(attachment);
		}

		return attachments;
	}

	private static List<SamplesPacketDTO> getSamplePacketList(
			final DataProducerDTO experienceData) {
		List<SamplesPacketDTO> samples = null;
		try {
			samples = getListFromByteArray(experienceData
					.getSamplesPacketMatrixSerialized());
		} catch (IOException e) {
			LOGGER.log(
					Level.FINEST,
					"Unable to read the experience data object from the stream.",
					e);
		} catch (ClassNotFoundException e) {
			LOGGER.log(Level.FINEST,
					"The class " + SamplesPacketDTO.class.getName()
							+ " is unknown.", e);
		}
		return samples;
	}

	@SuppressWarnings("unchecked")
	private static List<SamplesPacketDTO> getListFromByteArray(
			final byte[] samples) throws IOException, ClassNotFoundException {
		List<SamplesPacketDTO> result = Collections.emptyList();
		if (samples != null) {
			final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
					samples);
			final ObjectInputStream objectInputStream = new ObjectInputStream(
					byteArrayInputStream);
			result = (List<SamplesPacketDTO>) objectInputStream.readObject();
			objectInputStream.close();
		}
		return result;
	}

	private void prepareMessageToSend(final Message message,
			final MailMessageRequest mailMessageRequest) {

		try {

			prepareMessageContent(message, mailMessageRequest);

			// InternetAddress fromAddress = new
			// InternetAddress(mailMessageRequest.getFrom());
			// message.setFrom(fromAddress);
			// message.setReplyTo(new InternetAddress[] { fromAddress });

			Set<InternetAddress> recipients = new HashSet<InternetAddress>();
			if (mailMessageRequest.getRecipients() != null) {
				for (String mailTo : mailMessageRequest.getRecipients()) {
					recipients.add(new InternetAddress(mailTo));
				}
			}
			if (mailMessageRequest.getTo() != null
					&& mailMessageRequest.getTo().trim().length() > 0) {
				recipients.add(new InternetAddress(mailMessageRequest.getTo()));
			}
			if (mailMessageRequest.getRecipients() != null
					&& mailMessageRequest.getRecipients().length > 0) {
				message.setRecipients(RecipientType.BCC,
						recipients.toArray(new InternetAddress[0]));
			}

		} catch (AddressException e) {
			reportMessageError(
					getErrorMessagesResouceBundle().getString(
							"mail.parsing.valid.email"),
					mailMessageRequest.getSubject(),
					mailMessageRequest.getFrom(), mailMessageRequest.getTo(),
					Arrays.toString(mailMessageRequest.getRecipients()));
			throw new BusinessException(e);
		} catch (MessagingException e) {
			reportMessageError(
					getErrorMessagesResouceBundle().getString(
							"mail.properties.set"),
					mailMessageRequest.toShortString());
			throw new BusinessException(e);
		}
	}

	/**
	 * Saves on the database an error message of type
	 * {@link MessageCategory#MAIL}.
	 * 
	 * @param message
	 * @param params
	 */
	private void reportMessageError(String message, Object... params) {
		ErrorMessage error = new ErrorMessage();
		error.setMessage(MessageFormat.format(message, params));
		error.setCategory(MessageCategory.MAIL);
		error.setDateError(new Date());
		errorMessageService.create(error);
	}

	/**
	 * Creates a single action timer to execute {@link #sendMailRetryMillis} *
	 * {@link #maxFailCount} milliseconds after now. If it's not possible to
	 * send the e-mail, the next time we'll wait a little more before retrying.
	 * 
	 * @param failedMessage
	 *            The message to send to the timer service.
	 * @param failCount
	 *            Number of times the message failed to be sent.
	 */
	private void sendToTimer(MailMessageRequest failedMessage, int failCount) {
		TimerConfig config = new TimerConfig();
		config.setInfo(new FailedMailMessage(failedMessage, failCount));
		timerService.createSingleActionTimer(
				failedMessage.getSendMailRetryMillis() * failCount, config);
	}

	private void prepareMessageContent(Message message,
			MailMessageRequest mailMessageRequest) throws MessagingException {

		message.setSubject(mailMessageRequest.getSubject());

		MimeMultipart multipart = new MimeMultipart();

		if (mailMessageRequest.getMailFormat() == MailFormatEnum.PLAIN_TEXT) {
			MimeBodyPart textContent = new MimeBodyPart();
			textContent.setText(mailMessageRequest.getContent());
			multipart.addBodyPart(textContent);
		} else {
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(mailMessageRequest.getContent(),
					"text/html");

			multipart.addBodyPart(messageBodyPart);

			if (mailMessageRequest.getMimeParts() != null) {
				for (MimePart mimePart : mailMessageRequest.getMimeParts()) {
					ByteArrayDataSource dataSource = new ByteArrayDataSource(
							mimePart.getPartContent(), mimePart.getMimeType());
					DataHandler dataHandler = new DataHandler(dataSource);

					MimeBodyPart inlinePart = new MimeBodyPart();
					inlinePart.setDataHandler(dataHandler);
					inlinePart.setFileName(mimePart.getPartName());
					inlinePart.setContentID("<" + mimePart.getCid() + ">");

					multipart.addBodyPart(inlinePart);
				}
			}

		}

		if (mailMessageRequest.getAttachments() != null) {
			for (Attachment attachment : mailMessageRequest.getAttachments()) {
				ByteArrayDataSource dataSource = new ByteArrayDataSource(
						attachment.getFileContent(), attachment.getMimeType());
				DataHandler dataHandler = new DataHandler(dataSource);

				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.setDataHandler(dataHandler);
				attachmentPart.setFileName(attachment.getFileName());
				attachmentPart.setDisposition(Part.ATTACHMENT);

				multipart.addBodyPart(attachmentPart);
			}
		}

		message.setContent(multipart);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void queueMessage(MailMessageRequest request)
			throws NoValidRecipientsFoundForMessage {

		// Just to make sure the client programmer always sends the client
		// locale.
		if (request.getClientLocale() == null) {
			throw new IllegalArgumentException(
					"The Client Locale is a mandatory field");
		}

		Connection connection = null;
		Session queueSession = null;
		MessageProducer sender = null;

		try {

			connection = mailConnectionFactory.createConnection();
			queueSession = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);
			sender = queueSession.createProducer(queue);

			lookupProperties(request);

			List<MailMessageRequest> messageRequests = splitMessageInBlocks(
					request, request.getRecipientsPerBlockMaxSize());

			ObjectMessage objectMessage = null;
			for (MailMessageRequest mailMessageRequest : messageRequests) {
				objectMessage = queueSession.createObjectMessage();
				objectMessage.setObject(mailMessageRequest);
				sender.send(objectMessage);
			}

		} catch (JMSException e) {
			throw new BusinessException(e);
		} catch (NamingException e) {
			throw new BusinessException(e);
		} finally {
			closeResources(connection, queueSession, sender);
		}
	}

	private List<MailMessageRequest> splitMessageInBlocks(
			MailMessageRequest request, int blockSizeMax)
			throws NoValidRecipientsFoundForMessage {
		List<MailMessageRequest> requests = new ArrayList<MailMessageRequest>();
		Set<String> mergedValidatedRecipients = mergeAndValidateAllRecipients(request);

		// If "to" was present, it is already in the mergedValidatedRecipients,
		// so it can be removed from the MailMessageRequest, to avoid sending
		// the e-mail duplicated.
		request.setTo(null);
		if (mergedValidatedRecipients.size() <= 0) {
			String msgPattern = getErrorMessagesResouceBundle().getString(
					"mail.no.valid.recipients");
			reportMessageError(msgPattern, request.getSubject());
			throw new NoValidRecipientsFoundForMessage(MessageFormat.format(
					msgPattern, request.getSubject()));
		}

		Set<Set<String>> recipientsPerBlock = splitRecipientsPerBlocks(
				mergedValidatedRecipients, blockSizeMax);

		final String[] convertionType = new String[0];

		if (recipientsPerBlock.size() > 0) {
			for (Set<String> blockOfRecipients : recipientsPerBlock) {
				if (blockOfRecipients.size() == 0) {
					continue;
				}
				requests.add(new MailMessageRequest(request, request.getTo(),
						blockOfRecipients.toArray(convertionType)));
			}
		} else {
			requests.add(new MailMessageRequest(request, request.getTo(), null));
		}

		return requests;
	}

	private Set<Set<String>> splitRecipientsPerBlocks(
			Set<String> mergedValidatedRecipients, int blockSizeMax) {
		Set<Set<String>> recipientsPerBlock = new HashSet<Set<String>>(
				mergedValidatedRecipients != null ? Math.max(
						mergedValidatedRecipients.size() / blockSizeMax, 1) : 0);

		if (mergedValidatedRecipients != null
				&& mergedValidatedRecipients.size() > 0) {
			Set<String> currentSetRecipients = new HashSet<String>(blockSizeMax);
			recipientsPerBlock.add(currentSetRecipients);
			int countRecipients = 0;
			for (String recipient : mergedValidatedRecipients) {
				currentSetRecipients.add(recipient);
				++countRecipients;
				if (countRecipients == blockSizeMax) {
					currentSetRecipients = new HashSet<String>(blockSizeMax);
					recipientsPerBlock.add(currentSetRecipients);
					countRecipients = 0;
				}
			}
		}

		return recipientsPerBlock;
	}

	/**
	 * Checks if a mail string matches a valid e-mail pattern.
	 * 
	 * @param mail
	 * @return
	 */
	private boolean isValidMail(String mail) {
		if (mail.matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			return true;
		}
		return false;
	}

	private Set<String> mergeAndValidateAllRecipients(MailMessageRequest request) {
		HashSet<String> mergedRecipients = new HashSet<String>(
				request.getRecipients() != null ? request.getRecipients().length
						: 0);

		Set<String> invalidMails = new HashSet<String>();
		if (request.getTo() != null) {
			if (!isValidMail(request.getTo())) {
				invalidMails.add(request.getTo());
			} else {
				mergedRecipients.add(request.getTo());
			}
		}
		if (request.getRecipients() != null
				&& request.getRecipients().length > 0) {
			for (int i = 0; i < request.getRecipients().length; i++) {
				String recipient = request.getRecipients()[i];
				if (!isValidMail(recipient)) {
					invalidMails.add(recipient);
					continue;
				}
				try {
					// Just to make sure it is parseable
					new InternetAddress(recipient);
					mergedRecipients.add(recipient);
				} catch (AddressException e) {
					LOGGER.log(Level.FINEST,
							"Unable to convert recipient address '" + recipient
									+ "' to a valid internet address because: "
									+ e.getMessage() + "! Ignoring it!", e);
				}
			}
		}
		if (invalidMails.size() > 0) {
			reportMessageError(
					getErrorMessagesResouceBundle().getString(
							"mail.invalid.email"), invalidMails.toString(),
					request.getSubject());
		}
		return mergedRecipients;
	}

	private void closeResources(Connection queueConnection,
			javax.jms.Session queueSession, MessageProducer sender) {
		if (sender != null) {
			try {
				sender.close();
			} catch (JMSException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		if (queueSession != null) {
			try {
				queueSession.close();
			} catch (JMSException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		if (queueConnection != null) {
			try {
				queueConnection.close();
			} catch (JMSException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	/**
	 * Processes the timer service event, by retrying to send the message again.
	 * 
	 * @param timer
	 * @throws NoValidRecipientsFoundForMessage
	 */
	@Timeout
	public void processTimer(final Timer timer) {
		FailedMailMessage mailMessage = (FailedMailMessage) timer.getInfo();
		sendMessages(mailMessage.getMailRequest(), mailMessage.getFailCount());
	}

	/**
	 * Looks up the jndi for the custom jndi names
	 * <code>recipientsPerBlockMaxSize</code>, <code>maxFailCount</code> and
	 * <code>sendMailRetryMillis</code> and sets the <code>request</code> with
	 * these properties, if they exist.
	 * 
	 * @param request
	 * @throws NamingException
	 *             If it's not possible to get the InitialContext or one of the
	 *             properties.
	 */
	private void lookupProperties(final MailMessageRequest request)
			throws NamingException {
		InitialContext ic = new InitialContext();
		Integer recipientsPerBlockMaxSize = (Integer) ic
				.lookup("recipientsPerBlockMaxSize");
		Integer maxFailCount = (Integer) ic.lookup("maxFailCount");
		Integer sendMailRetryMillis = (Integer) ic
				.lookup("sendMailRetryMillis");
		request.setMailProperties(recipientsPerBlockMaxSize, maxFailCount,
				sendMailRetryMillis);
	}
}