package com.linkare.rec.am.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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

import com.linkare.rec.am.model.ErrorMessage;
import com.linkare.rec.am.model.FailedMailMessage;
import com.linkare.rec.am.model.MessageCategory;
import com.linkare.rec.am.model.util.Attachment;
import com.linkare.rec.am.model.util.BusinessException;
import com.linkare.rec.am.model.util.MailFormatEnum;
import com.linkare.rec.am.model.util.MailMessageRequest;
import com.linkare.rec.am.model.util.MimePart;
import com.linkare.rec.am.model.util.NoValidRecipientsFoundForMessage;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 * 
 */
@Stateless(name = "mailService")
@Remote(value = MailServiceRemote.class)
@Local(value = MailServiceLocal.class)
public class MailServiceBean implements MailServiceRemote, MailServiceLocal {

    private static final String ERROR_MESSAGES_FILE = "errorMessages";

    @Resource(mappedName = "mail/mailSession")
    private javax.mail.Session mailSession;

    @Resource(mappedName = "mailConnectionFactory")
    private ConnectionFactory mailConnectionFactory;

    @Resource(mappedName = "jms/MailMessages")
    private Destination queue;

    //    @Inject
    //    private Logger log;

    @Resource(mappedName = "recipientsPerBlockMaxSize")
    private int recipientsPerBlockMaxSize;

    @Resource(mappedName = "maxFailCount")
    private int maxFailCount;

    @Resource(mappedName = "sendMailRetryMillis")
    private int sendMailRetryMillis;

    @Resource
    private TimerService timerService;

    @EJB(beanInterface = ErrorMessageServiceLocal.class)
    private ErrorMessageServiceLocal errorMessageService;

    //TODO inject the resource bundle
    //    @Inject
    //    private transient ResourceBundle errorMessages;
    //
    //    @Produces
    public ResourceBundle getResouceBundle() {
	return ResourceBundle.getBundle(ERROR_MESSAGES_FILE);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Override
    public void sendMessages(MailMessageRequest mailMessageRequest, int failCount) {

	Message message = new MimeMessage(mailSession);
	prepareMessageToSend(message, mailMessageRequest);

	try {

	    Transport.send(message);

	    //TODO change to injected log.
	    System.out.println("Message sent to mailserver");

	} catch (MessagingException e) {
	    reportMessageError(getResouceBundle().getString("mail.server.failed"), mailMessageRequest.getSubject(),
			       Arrays.toString(mailMessageRequest.getRecipients()), ++failCount);
	    if (failCount < maxFailCount) {
		sendToTimer(mailMessageRequest, failCount);
	    }
	}
    }

    private void prepareMessageToSend(final Message message, final MailMessageRequest mailMessageRequest) {

	try {

	    prepareMessageContent(message, mailMessageRequest);

	    InternetAddress fromAddress = new InternetAddress(mailMessageRequest.getFrom());
	    message.setFrom(fromAddress);
	    message.setReplyTo(new InternetAddress[] { fromAddress });

	    Set<InternetAddress> recipients = new HashSet<InternetAddress>();
	    if (mailMessageRequest.getRecipients() != null) {
		for (String mailTo : mailMessageRequest.getRecipients()) {
		    recipients.add(new InternetAddress(mailTo));
		}
	    }
	    if (mailMessageRequest.getTo() != null && mailMessageRequest.getTo().trim().length() > 0) {
		recipients.add(new InternetAddress(mailMessageRequest.getTo()));
	    }
	    if (mailMessageRequest.getRecipients() != null && mailMessageRequest.getRecipients().length > 0) {
		message.setRecipients(RecipientType.BCC, recipients.toArray(new InternetAddress[0]));
	    }

	} catch (AddressException e) {
	    reportMessageError(getResouceBundle().getString("mail.parsing.valid.email"), mailMessageRequest.getSubject(), mailMessageRequest.getFrom(),
			       mailMessageRequest.getTo(), Arrays.toString(mailMessageRequest.getRecipients()));
	    throw new BusinessException(e);
	} catch (MessagingException e) {
	    reportMessageError(getResouceBundle().getString("mail.properties.set"), mailMessageRequest.toShortString());
	    throw new BusinessException(e);
	}
    }

    /**
     * Saves on the database an error message of type {@link MessageCategory#MAIL}.
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
     * Creates a single action timer to execute {@link #sendMailRetryMillis} * {@link #maxFailCount} milliseconds after now. If it's not possible to send the
     * e-mail, the next time we'll wait a little more before retrying.
     * 
     * @param failedMessage
     *            The message to send to the timer service.
     * @param failCount
     *            Number of times the message failed to be sent.
     */
    private void sendToTimer(MailMessageRequest failedMessage, int failCount) {
	TimerConfig config = new TimerConfig();
	config.setInfo(new FailedMailMessage(failedMessage, failCount));
	timerService.createSingleActionTimer(sendMailRetryMillis * failCount, config);
    }

    private void prepareMessageContent(Message message, MailMessageRequest mailMessageRequest) throws MessagingException {

	message.setSubject(mailMessageRequest.getSubject());

	MimeMultipart multipart = new MimeMultipart();

	if (mailMessageRequest.getMailFormat() == MailFormatEnum.PLAIN_TEXT) {
	    MimeBodyPart textContent = new MimeBodyPart();
	    textContent.setText(mailMessageRequest.getContent());
	    multipart.addBodyPart(textContent);
	} else {
	    MimeBodyPart messageBodyPart = new MimeBodyPart();
	    messageBodyPart.setContent(mailMessageRequest.getContent(), "text/html");

	    multipart.addBodyPart(messageBodyPart);

	    if (mailMessageRequest.getMimeParts() != null) {
		for (MimePart mimePart : mailMessageRequest.getMimeParts()) {
		    ByteArrayDataSource dataSource = new ByteArrayDataSource(mimePart.getPartContent(), mimePart.getMimeType());
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
		ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getFileContent(), attachment.getMimeType());
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
    public void queueMessage(MailMessageRequest request) throws NoValidRecipientsFoundForMessage {

	Connection connection = null;
	Session queueSession = null;
	MessageProducer sender = null;

	try {

	    connection = mailConnectionFactory.createConnection();
	    queueSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	    sender = queueSession.createProducer(queue);

	    List<MailMessageRequest> messageRequests = splitMessageInBlocks(request, this.recipientsPerBlockMaxSize);

	    ObjectMessage objectMessage = null;
	    for (MailMessageRequest mailMessageRequest : messageRequests) {
		objectMessage = queueSession.createObjectMessage();
		objectMessage.setObject(mailMessageRequest);
		sender.send(objectMessage);
	    }

	} catch (JMSException e) {
	    throw new BusinessException(e);
	} finally {
	    closeResources(connection, queueSession, sender);
	}
    }

    private List<MailMessageRequest> splitMessageInBlocks(MailMessageRequest request, int blockSizeMax) throws NoValidRecipientsFoundForMessage {
	List<MailMessageRequest> requests = new ArrayList<MailMessageRequest>();
	Set<String> mergedValidatedRecipients = mergeAndValidateAllRecipients(request);

	if (mergedValidatedRecipients.size() <= 0) {
	    String msgPattern = getResouceBundle().getString("mail.no.valid.recipients");
	    reportMessageError(msgPattern, request.getSubject());
	    throw new NoValidRecipientsFoundForMessage(MessageFormat.format(msgPattern, request.getSubject()));
	}

	Set<Set<String>> recipientsPerBlock = splitRecipientsPerBlocks(mergedValidatedRecipients, blockSizeMax);

	final String[] convertionType = new String[0];

	if (recipientsPerBlock.size() > 0) {
	    for (Set<String> blockOfRecipients : recipientsPerBlock) {
		if (blockOfRecipients.size() == 0) {
		    continue;
		}
		requests.add(new MailMessageRequest(request, request.getTo(), blockOfRecipients.toArray(convertionType)));
	    }
	} else {
	    requests.add(new MailMessageRequest(request, request.getTo(), null));
	}

	return requests;
    }

    private Set<Set<String>> splitRecipientsPerBlocks(Set<String> mergedValidatedRecipients, int blockSizeMax) {
	Set<Set<String>> recipientsPerBlock = new HashSet<Set<String>>(mergedValidatedRecipients != null ? Math.max(mergedValidatedRecipients.size()
		/ blockSizeMax, 1) : 0);

	if (mergedValidatedRecipients != null && mergedValidatedRecipients.size() > 0) {
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
	HashSet<String> mergedRecipients = new HashSet<String>(request.getRecipients() != null ? request.getRecipients().length : 0);

	Set<String> invalidMails = new HashSet<String>();
	if (!isValidMail(request.getTo())) {
	    invalidMails.add(request.getTo());
	} else {
	    mergedRecipients.add(request.getTo());
	}
	if (request.getRecipients() != null && request.getRecipients().length > 0) {
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
		    System.out.println("Unable to convert recipient address '" + recipient + "' to a valid internet address because: " + e.getMessage()
			    + "! Ignoring it!");
		    e.printStackTrace();
		}
	    }
	}
	if (invalidMails.size() > 0) {
	    reportMessageError(getResouceBundle().getString("mail.invalid.email"), invalidMails.toString(), request.getSubject());
	}
	return mergedRecipients;
    }

    private void closeResources(Connection queueConnection, javax.jms.Session queueSession, MessageProducer sender) {
	if (sender != null) {
	    try {
		sender.close();
	    } catch (JMSException e) {
		e.printStackTrace();
	    }
	}
	if (queueSession != null) {
	    try {
		queueSession.close();
	    } catch (JMSException e) {
		e.printStackTrace();
	    }
	}
	if (queueConnection != null) {
	    try {
		queueConnection.close();
	    } catch (JMSException e) {
		e.printStackTrace();
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
}