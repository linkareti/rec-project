/*
 
 * DataReceiverForQueue.java
 
 *
 
 * Created on 13 de Agosto de 2002, 16:15
 
 */

package com.linkare.rec.impl.multicast;

import java.util.logging.Level;

import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.impl.events.DataProducerStateChangeEvent;
import com.linkare.rec.impl.events.NewSamplesEvent;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.impl.wrappers.DataReceiverWrapper;

/**
 * 
 * 
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */

public class DataReceiverForQueue

{

	private DataReceiverWrapper drw = null;

	private IDataReceiverForQueueListener dataReceiverForQueueListener = null;

	public EventQueue messageQueue = null;

	public DataReceiverForQueue(DataReceiver dr, IDataReceiverForQueueListener dataReceiverForQueueListener)
			throws NotAuthorized

	{

		setDataReceiverForQueueListener(dataReceiverForQueueListener);

		this.drw = new DataReceiverWrapper(dr);

		if (!drw.isConnected())

		{

			log(Level.SEVERE, "Error connection to DataReceiver in DataReceiverForQueue - Throwing not authorized...");

			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_CONNECTION_FAILED);

		}

		messageQueue = new EventQueue(new DataReceiverQueueDispatcher());

	}

	/* Proxy Logging methods */
	public void log(Level debugLevel, String message) {
		if (getDataReceiverForQueueListener() != null)
			getDataReceiverForQueueListener().log(debugLevel, message);
	}

	public void logThrowable(String message, Throwable t) {
		if (getDataReceiverForQueueListener() != null)
			getDataReceiverForQueueListener().logThrowable(message, t);
	}

	public String toString()

	{

		return "Proxy Receiver " + drw.getDelegate();

	}

	public boolean equals(Object obj)

	{

		if (!(obj instanceof DataReceiverForQueue))

			return false;

		DataReceiverForQueue other = (DataReceiverForQueue) obj;

		if (other.drw != null && other.drw.isSameDelegate(drw))

			return true;

		return false;

	}

	public boolean isConnected()

	{

		return drw.isConnected();

	}

	public void shutdownAsSoonAsPossible() {
		if (shutdown)
			return;

		(new Thread() {
			public void run() {
				shutdown();
			}
		}).start();
	}

	private boolean shutdown = false;

	public synchronized void shutdown()

	{
		if (shutdown)
			return;

		shutdown = true;

		log(Level.INFO, "receiver " + drw.getDelegate() + " - Shutting down!");

		log(Level.INFO, "receiver " + drw.getDelegate() + " - shutting down message queue!");

		messageQueue.shutdown();

		log(Level.INFO, "receiver " + drw.getDelegate() + " - message queue is shut down!");
		;

		log(Level.INFO, "receiver " + drw.getDelegate() + " - informing dataReceiverForQueueListener that I'm gone!");

		if (getDataReceiverForQueueListener() != null)
			getDataReceiverForQueueListener().dataReceiverForQueueIsGone(this);

		log(Level.INFO, "receiver " + drw.getDelegate() + " is shut down!");
	}

	public void stateChanged(DataProducerStateChangeEvent event)

	{

		messageQueue.addEvent(event);

	}

	public void newSamples(NewSamplesEvent event)

	{

		messageQueue.addEvent(event);

	}

	public DataReceiverWrapper getDataReceiver()

	{

		return drw;

	}

	/**
	 * Getter for property dataReceiverForQueueListener.
	 * 
	 * @return Value of property dataReceiverForQueueListener.
	 * 
	 * 
	 * 
	 */

	public IDataReceiverForQueueListener getDataReceiverForQueueListener()

	{

		return dataReceiverForQueueListener;

	}

	/**
	 * Setter for property dataReceiverForQueueListener.
	 * 
	 * @param dataReceiverForQueueListener New value of property
	 *            dataReceiverForQueueListener.
	 * 
	 * 
	 * 
	 */

	public void setDataReceiverForQueueListener(IDataReceiverForQueueListener dataReceiverForQueueListener)

	{

		this.dataReceiverForQueueListener = dataReceiverForQueueListener;

	}

	private class DataReceiverQueueDispatcher implements EventQueueDispatcher

	{

		public void dispatchEvent(Object o)

		{

			if (!drw.isConnected())

			{

				shutdownAsSoonAsPossible();

				return;

			}

			try

			{

				if (o instanceof NewSamplesEvent)

				{

					NewSamplesEvent evt = (NewSamplesEvent) o;

					drw.newSamples(evt.getLargestNumPacket());

				}

				if (o instanceof DataProducerStateChangeEvent)

				{

					DataProducerStateChangeEvent evt = (DataProducerStateChangeEvent) o;

					drw.stateChanged(evt.getDataProducerState());

				}

			}

			catch (Exception e)

			{

				logThrowable("Oooppss.. receiver gone? - Error dispatching event to receiver! Why? Gone?", e);

				if (!isConnected())

				{

					shutdownAsSoonAsPossible();

					return;

				}

			}

		}

		public int getPriority()

		{

			return Thread.NORM_PRIORITY + 2;

		}

	}

}
