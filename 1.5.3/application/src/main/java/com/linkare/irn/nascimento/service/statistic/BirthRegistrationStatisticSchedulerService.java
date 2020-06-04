package com.linkare.irn.nascimento.service.statistic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.model.config.Configuration;
import com.linkare.irn.nascimento.model.ext.mapping.statistic.BirthRegistrationAuditStatisticDTO;
import com.linkare.irn.nascimento.model.ext.mapping.statistic.StatisticDTO;
import com.linkare.irn.nascimento.service.core.BirthRegistrationService;
import com.linkare.irn.nascimento.service.message.EmailMessageService;
import com.linkare.irn.nascimento.web.ConfigurationManager;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BirthRegistrationStatisticSchedulerService {

	private static final Logger LOG = LoggerFactory.getLogger(BirthRegistrationStatisticSchedulerService.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Inject
	private BirthRegistrationService birthRegistrationService;

	@Inject
	private EmailMessageService emailMessageService;

	@Inject
	private ConfigurationManager configurationManager;

	private Map<Integer, StringBuilder> table;

	@Schedule(dayOfWeek = "1", hour = "0", minute = "1")
	public void sendStatistic() {

		LOG.info("Executing BirthRegistrationStatisticSchedulerService...");

		Date previousModay = getPreviousMonday();
		Date today = getYesterday();

		List<BirthRegistrationAuditStatisticDTO> audits = birthRegistrationService
				.findYesterdayStatistics(previousModay, today);

		table = new HashMap<>();

		setHeader(previousModay, today);

		Map<Date, StatisticDTO> statistics = new TreeMap<>();
		mapStatistics(audits, statistics);

		setRowsDescription();
		populateTable(statistics);
		sendEmails();

	}

	private void sendEmails() {
		Configuration configuration = configurationManager.getConfiguration();

		String[] emails = configuration.getStatisticsEmail().split(";");

		for (String email : emails) {
			emailMessageService.sendStatistics(addToTable(getMergedContent()), email);
		}
	}

	private String addToTable(String content) {
		return new StringBuilder().append("<table>").append(content).append("</table>").toString();
	}

	private String getMergedContent() {

		StringBuilder content = new StringBuilder();

		for (StringBuilder sb : table.values()) {
			content.append(sb.toString());
		}

		return content.toString();
	}

	private void populateTable(Map<Date, StatisticDTO> statistics) {
		int column = 0;
		for (StatisticDTO statistic : statistics.values()) {
			int line = 1;
			column++;
			boolean lastColumn = (column == statistics.size());

			addToFieldToRow(line++, statistic.getSubmitted().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getPendingForefather().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getElaboratingWithoutForefather().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getElaboratingByForefather().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getRejectedByForefather().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getExpired().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getAccepted().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getConcluded().toString(), lastColumn);
			addToFieldToRow(line++, statistic.getRejected().toString(), lastColumn);
			addToFieldToRow(line, statistic.getDeleted().toString(), lastColumn);

		}
	}

	private void setRowsDescription() {

		int line = 1;
		for (BirthRegistrationAuditStatisticsFields field : BirthRegistrationAuditStatisticsFields.values()) {
			addToFieldToRow(line++, field.getPortgueseTranslation(), false);
		}
	}

	private void close(final Integer line) {
		table.put(line, table.get(line).append("</tr>"));
	}

	private void addToFieldToRow(final Integer line, final String content, final boolean closed) {
		table.put(line, table.getOrDefault(line, new StringBuilder().append("<tr>")).append("<td>").append(content)
				.append("</td>"));

		if (closed) {
			close(line);
		}
	}

	private void mapStatistics(List<BirthRegistrationAuditStatisticDTO> audits, Map<Date, StatisticDTO> statistics) {

		for (BirthRegistrationAuditStatisticDTO audit : audits) {

			StatisticDTO statistic = statistics.getOrDefault(audit.getModifiedDate(), new StatisticDTO());
			statistic.processAudit(audit);
			statistics.put(audit.getModifiedDate(), statistic);
		}

	}

	private void setHeader(final Date previousModay, final Date today) {

		addHeader(String.format("Período de %s a %s", dateFormat.format(previousModay), dateFormat.format(today)),
				false);

		int totalDaysOfWeeks = DaysOfWeek.values().length;
		for (DaysOfWeek dayOfWeek : DaysOfWeek.values()) {
			totalDaysOfWeeks--;
			addHeader(dayOfWeek.getPortgueseTranslation(), (totalDaysOfWeeks == 0));
		}

	}

	private void addHeader(final String content, final boolean closed) {
		table.put(0, table.getOrDefault(0, new StringBuilder().append("<tr>")).append("<th>").append(content)
				.append("</th>"));

		if (closed) {
			close(0);
		}
	}

	private Date getPreviousMonday() {

		DateTime today = new DateTime();
		DateTime sameDayLastWeek = today.minusWeeks(1);

		Calendar monday = Calendar.getInstance();

		monday.setTime(sameDayLastWeek.withDayOfWeek(DateTimeConstants.MONDAY).toDate());

		monday.set(Calendar.HOUR_OF_DAY, 0);
		monday.set(Calendar.MINUTE, 0);
		monday.set(Calendar.SECOND, 0);

		return monday.getTime();
	}

	private Date getYesterday() {

		DateTime today = new DateTime();
		DateTime sameDayLastWeek = today.minusWeeks(1);

		Calendar sunday = Calendar.getInstance();

		sunday.setTime(sameDayLastWeek.withDayOfWeek(DateTimeConstants.SUNDAY).toDate());

		sunday.set(Calendar.HOUR_OF_DAY, 23);
		sunday.set(Calendar.MINUTE, 59);
		sunday.set(Calendar.SECOND, 59);

		return sunday.getTime();
	}

}
