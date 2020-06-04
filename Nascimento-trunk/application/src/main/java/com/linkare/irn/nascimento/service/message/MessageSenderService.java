package com.linkare.irn.nascimento.service.message;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.enterprise.event.TransactionPhase;
import javax.inject.Inject;
import javax.mail.internet.InternetAddress;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.model.message.EmailMessage;
import com.linkare.irn.nascimento.model.message.MessageStatus;
import com.linkare.irn.nascimento.service.cdi.message.EmailMessageEvent;
import com.linkare.irn.nascimento.web.ConfigurationManager;
import com.linkare.irn.nascimento.wsclient.ArgMCEnvioEmail;
import com.linkare.irn.nascimento.wsclient.CabecalhoTipo;
import com.linkare.irn.nascimento.wsclient.DeTipo;
import com.linkare.irn.nascimento.wsclient.IpTipo;
import com.linkare.irn.nascimento.wsclient.MCEnvioEmailBndQSService;
import com.linkare.irn.nascimento.wsclient.MCEnvioEmailFault;
import com.linkare.irn.nascimento.wsclient.MCEnvioEmailPortType;
import com.linkare.irn.nascimento.wsclient.MensagemTipo;
import com.linkare.irn.nascimento.wsclient.ObjectFactory;
import com.linkare.irn.nascimento.wsclient.UtilizadorTipo;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class MessageSenderService {

	// private static final String EMAIL_CONTENT_TYPE = "text/html; charset=utf-8";

	private static final Logger LOG = LoggerFactory.getLogger(MessageSenderService.class);

	@Inject
	private EmailMessageService emailMessageService;

	@Inject
	private ConfigurationManager configurationManager;

	@Asynchronous
	public void sendEmail(final EmailMessage message) {
		dispatchEmailAndUpdateStatus(message);
	}

	public void sendEmail(@Observes(during = TransactionPhase.AFTER_SUCCESS) final EmailMessageEvent event) {
		dispatchEmailAndUpdateStatus(event.getPayload());
	}

	/**
	 * @param message
	 */
	private void dispatchEmailAndUpdateStatus(final EmailMessage message) {
		if (message.getStatus() == MessageStatus.PENDING) {
			if (sendEmail(message.getUuid(), message.getOrigin(), message.getDestinations(), message.getSubject(),
					message.getContent(), message.getUsername(), message.getDisplayName())) {
				message.setStatus(MessageStatus.SENT);
				emailMessageService.update(message);
			} else {
				message.setAttempt(message.getAttempt() == null ? 0 : message.getAttempt() + 1);
				if (message.getAttempt() == 10) {
					message.setStatus(MessageStatus.ERROR);
				}
				emailMessageService.update(message);
			}
		}
	}

	private boolean sendEmail(final String uuid, final InternetAddress from, final List<InternetAddress> destinations,
			final String subject, final String content, final String username, final String displayName) {

		try {
			MCEnvioEmailBndQSService service = new MCEnvioEmailBndQSService();
			service.setHandlerResolver(new WsMailSenderSecurityHeadesHandlerResolver());

			MCEnvioEmailPortType mcEnvioEmailPort = service.getMCEnvioEmailBndQSPort();
			BindingProvider bp = (BindingProvider) mcEnvioEmailPort;

			bp.getRequestContext().put("ws-security.username",
					configurationManager.getConfiguration().getWsSendEmailUsername());
			bp.getRequestContext().put("ws-security.password",
					configurationManager.getConfiguration().getWsSendEmailPassword());
			bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
					configurationManager.getConfiguration().getWsSendEmailEndpoint());

			CabecalhoTipo cabecalho = createCabecalho(username, displayName);
			ArgMCEnvioEmail mcEnvioEmail = createArgMCEnvioEmail(from, destinations, subject, content);

			MensagemTipo mcEnvioEmailResponse = mcEnvioEmailPort.mcEnvioEmail(cabecalho, mcEnvioEmail);

			return mcEnvioEmailResponse.getCodigo() == 0 && "OK".equals(mcEnvioEmailResponse.getDescricao());

		} catch (MCEnvioEmailFault |

				WebServiceException e) {
			LOG.error("Could not send email with UUID " + uuid, e);
			return false;
		}
	}

	private CabecalhoTipo createCabecalho(final String username, final String displayName) {
		ObjectFactory factory = new ObjectFactory();

		// Utilizador
		UtilizadorTipo utilizador = factory.createUtilizadorTipo();
		utilizador.setUtilizador(username);
		utilizador.setNome(displayName);

		// ip
		String ipV4 = "127.0.0.1";
		try {
			ipV4 = Inet4Address.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		IpTipo ip = factory.createIpTipo();
		ip.setIpv4(ipV4);

		// de
		DeTipo de = factory.createDeTipo();
		de.setAplicacao(configurationManager.getConfiguration().getWsSendEmailApplicationCode());
		de.setServico(configurationManager.getConfiguration().getWsSendEmailServiceCode());
		de.setDepartamento(configurationManager.getConfiguration().getWsSendEmailDepartmentCode());
		de.setIp(ip);

		CabecalhoTipo cabecalho = factory.createCabecalhoTipo();
		cabecalho.setUtilizador(utilizador);
		cabecalho.setDe(de);

		return cabecalho;
	}

	private ArgMCEnvioEmail createArgMCEnvioEmail(final InternetAddress from, final List<InternetAddress> destinations,
			final String subject, final String content) {
		ObjectFactory factory = new ObjectFactory();

		ArgMCEnvioEmail mcEnvioEmail = factory.createArgMCEnvioEmail();
		mcEnvioEmail.setFrom(internetAddressToString(from));

		String[] toDestination = new String[destinations.size()];
		for (int i = 0; i < destinations.size(); i++) {
			toDestination[i] = internetAddressToString(destinations.get(i));
		}

		mcEnvioEmail.setTo(String.join(",", toDestination));
		mcEnvioEmail.setSubject(subject);
		mcEnvioEmail.setBody(content);

		return mcEnvioEmail;
	}

	private String internetAddressToString(final InternetAddress internetAddress) {
		return internetAddress.getPersonal() + " <" + internetAddress.getAddress() + ">";
	}
}