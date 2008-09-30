/*
 * MaximumClientsReachedConstants.java
 *
 * Created on 24 de Janeiro de 2003, 11:57
 */

package com.linkare.rec.impl.exceptions;

import com.linkare.rec.acquisition.MaximumClientsReached;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class MaximumClientsReachedConstants
{
	private static final java.util.ResourceBundle maximumClientsReachedRB = java.util.ResourceBundle.getBundle("com/linkare/rec/impl/exceptions/resources/MaximumClientsReached");

	public static final int MAXIMUM_CLIENTS_REACHED_IN_MAIN_QUEUE=0;
    public static final String MAXIMUM_CLIENTS_REACHED_IN_MAIN_QUEUE_MSG=maximumClientsReachedRB.getString("maximum_clients_reached_in_multicastcontroller");

	public static final int MAXIMUM_CLIENTS_REACHED_IN_HARDWARE_QUEUE=1;
    public static final String MAXIMUM_CLIENTS_REACHED_IN_HARDWARE_QUEUE_MSG=maximumClientsReachedRB.getString("maximum_clients_reached_in_multicasthardware");

	public static final int MAXIMUM_RECEIVERS_REACHED_IN_DATA_PRODUCER=2;
    public static final String MAXIMUM_RECEIVERS_REACHED_IN_DATA_PRODUCER_MSG=maximumClientsReachedRB.getString("maximum_data_receivers_reached_proxy_dataproducer");


	public static String getTranslatedMessage(MaximumClientsReached e)
	{
		switch(e.errorCode)
		{
			case MAXIMUM_CLIENTS_REACHED_IN_HARDWARE_QUEUE:
				return MAXIMUM_CLIENTS_REACHED_IN_HARDWARE_QUEUE_MSG;
			case MAXIMUM_CLIENTS_REACHED_IN_MAIN_QUEUE:
				return MAXIMUM_CLIENTS_REACHED_IN_MAIN_QUEUE_MSG;
			case MAXIMUM_RECEIVERS_REACHED_IN_DATA_PRODUCER:
				return MAXIMUM_RECEIVERS_REACHED_IN_DATA_PRODUCER_MSG;
		}

		return e.getMessage();
	}


}
