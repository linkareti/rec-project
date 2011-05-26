/*
 * NotAvailableExceptionConstants.java
 *
 * Created on 14 de Agosto de 2002, 15:28
 */

package com.linkare.rec.impl.exceptions;

import com.linkare.rec.acquisition.WrongConfigurationException;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class NotAvailableExceptionConstants {
	private static final java.util.ResourceBundle notAvailableExceptionRB = java.util.ResourceBundle
			.getBundle("com/linkare/rec/impl/exceptions/resources/NotAvailableException");

	public static final int HARDWARE_DOENST_EXIST_IN_MULTICASTCONTROLLER = 0;
	public static final String HARDWARE_DOENST_EXIST_IN_MULTICASTCONTROLLER_MSG = NotAvailableExceptionConstants.notAvailableExceptionRB
			.getString("hardware_not_referenced_by_multicastcontroller");
	public static final int NO_DATA_PRODUCER_AT_THIS_MOMENT = 1;
	public static final String NO_DATA_PRODUCER_AT_THIS_MOMENT_MSG = NotAvailableExceptionConstants.notAvailableExceptionRB
			.getString("no_dataproducer_active_in_hardware");
	public static final int COULDNT_GET_ALL_SAMPLES = 2;
	public static final String COULDNT_GET_ALL_SAMPLES_MSG = NotAvailableExceptionConstants.notAvailableExceptionRB
			.getString("not_all_samples_available_yet");
	public static final int HARDWARE_SHUTTING_DOWN = 3;
	public static final String HARDWARE_SHUTTING_DOWN_MSG = NotAvailableExceptionConstants.notAvailableExceptionRB
			.getString("hardware_shutting_down");
	public static final int NO_ACQ_HEADER = 4;
	public static final String NO_ACQ_HEADER_MSG = NotAvailableExceptionConstants.notAvailableExceptionRB
			.getString("acquisition_header_not_available");

	public static String getTranslatedMessage(final WrongConfigurationException e) {
		switch (e.errorCode) {
		case COULDNT_GET_ALL_SAMPLES:
			return NotAvailableExceptionConstants.COULDNT_GET_ALL_SAMPLES_MSG;
		case HARDWARE_DOENST_EXIST_IN_MULTICASTCONTROLLER:
			return NotAvailableExceptionConstants.HARDWARE_DOENST_EXIST_IN_MULTICASTCONTROLLER_MSG;
		case HARDWARE_SHUTTING_DOWN:
			return NotAvailableExceptionConstants.HARDWARE_SHUTTING_DOWN_MSG;
		case NO_DATA_PRODUCER_AT_THIS_MOMENT:
			return NotAvailableExceptionConstants.NO_DATA_PRODUCER_AT_THIS_MOMENT_MSG;
		case NO_ACQ_HEADER:
			return NotAvailableExceptionConstants.NO_ACQ_HEADER_MSG;
		}

		return e.getMessage();
	}

}
