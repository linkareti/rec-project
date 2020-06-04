package com.linkare.irn.nascimento.service.core;

import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.model.ext.mapping.statistic.StatisticDTO;
import com.linkare.irn.nascimento.service.message.EmailMessageService;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class BirthRegistrationStatisticSchedulerService {

	private static final Logger LOG = LoggerFactory.getLogger(BirthRegistrationStatisticSchedulerService.class);

	@Inject
	private BirthRegistrationService birthRegistrationService;

	@Inject
	private EmailMessageService emailMessageService;

	@Schedule(minute = "59", hour = "23")
	public void sendStatistic() {

		LOG.info("Executing BirthRegistrationStatisticSchedulerService...");

		StatisticDTO statistics = birthRegistrationService.findYesterdayStatistics();

		if (statistics != null) {

			emailMessageService.sendStatistics(statistics);
		}

	}

}
