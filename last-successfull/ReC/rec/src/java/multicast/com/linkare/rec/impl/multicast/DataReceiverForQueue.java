/*
 
 * DataReceiverForQueue.java
 
 *
 
 * Created on 13 de Agosto de 2002, 16:15
 
 */

package com.linkare.rec.impl.multicast;

import java.util.logging.Level;
import java.util.logging.Logger;

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
	private final static Logger LOGGER = Logger.getLogger(DataReceiverForQueue.class.getName());

	private DataReceiverWrapper drw = null;

	private IDataReceiverForQueueListener dataReceiverForQueueListener = null;

	public EventQueue messageQueue = null;

	public DataReceiverForQueue(final DataReceiver dr, final IDataReceiverForQueueListener dataReceiverForQueueListener)
			throws NotAuthorized

	{

		setDataReceiverForQueueListener(dataReceiverForQueueListener);

		drw = new DataReceiverWrapper(dr);

		if (!drw.isConnected())

		{

			LOGGER.log(Level.SEVERE,
					"Error connection to DataReceiver in DataReceiverForQueue - Throwing not authorized...");

			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_CONNECTION_FAILED);

		}

		messageQueue = new EventQueue(new DataReceiverQueueDispatcher(), this.getClass().getSimpleName());

	}

	public boolean isEmpty() {
		return !messageQueue.hasEvents();
	}

	@Override
	public String toString() {
		return "Proxy Receiver " + drw;
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof DataReceiverForQueue)) {
			return false;
		}

		final DataReceiverForQueue other = (DataReceiverForQueue) obj;

		if (other.drw != null && other.drw.isSameDelegate(drw)) {
			return true;
		}

		return false;
	}

	public boolean isConnected() {
		return drw.isConnected();
	}

	public void shutdownAsSoonAsPossible() {
		if (shutdown) {
			return;
		}

		(new Thread() {
			@Override
			public void run() {
				setName(getName() + " - DataReceiverForQueue - shutdown");
				shutdown();
			}
		}).start();
	}

	private boolean shutdown = false;

	public synchronized void shutdown() {
		if (shutdown) {
			return;
		}

		shutdown = true;

		LOGGER.log(Level.FINEST, "receiver " + drw + " - Shutting down!");

		LOGGER.log(Level.FINEST, "receiver " + drw + " - shutting down message queue!");

		messageQueue.shutdown();

		LOGGER.log(Level.FINEST, "receiver " + drw + " - message queue is shut down!");

		LOGGER.log(Level.FINEST, "receiver " + drw + " - informing dataReceiverForQueueListener that I'm gone!");

		if (getDataReceiverForQueueListener() != null) {
			getDataReceiverForQueueListener().dataReceiverForQueueIsGone(this);
		}

		LOGGER.log(Level.FINEST, "receiver " + drw + " is shut down!");
	}

	public boolean isShutdown() {
		return shutdown;
	}

	public void stateChanged(final DataProducerStateChangeEvent event) {
		messageQueue.addEvent(event);
	}

	public void newSamples(final NewSamplesEvent event) {
		messageQueue.addEvent(event);
	}

	public DataReceiverWrapper getDataReceiver() {
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

	public IDataReceiverForQueueListener getDataReceiverForQueueListener() {
		return dataReceiverForQueueListener;
	}

	/**
	 * Setter for property dataReceiverForQueueListener.
	 * 
	 * @param dataReceiverForQueueListener New value of property
	 *            dataReceiverForQueueListener.
	 * 
	 */

	public void setDataReceiverForQueueListener(final IDataReceiverForQueueListener dataReceiverForQueueListener) {
		this.dataReceiverForQueueListener = dataReceiverForQueueListener;
	}

	private class DataReceiverQueueDispatcher implements EventQueueDispatcher {

		@Override
		public void dispatchEvent(final Object o) {

			if (!drw.isConnected()) {
				shutdownAsSoonAsPossible();
				return;
			}

			try {
				if (o instanceof NewSamplesEvent) {

					final NewSamplesEvent evt = (NewSamplesEvent) o;

					LOGGER.log(Level.INFO, "DataReceiverForQueue - dispatching new samples message event " + evt);

					drw.newSamples(evt.getLargestNumPacket());

					// verificar se e' um evento de paragem da thread
					if (evt.isPoisoned()) {
						LOGGER.log(Level.FINE,
								"received a poison sample with largest num packet = " + evt.getLargestNumPacket());
						shutdownAsSoonAsPossible();
					}

				} else if (o instanceof DataProducerStateChangeEvent) {
					final DataProducerStateChangeEvent evt = (DataProducerStateChangeEvent) o;
					drw.stateChanged(evt.getDataProducerState());
				}
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Oooppss.. receiver gone? - Error dispatching event to receiver! Why? Gone?",
						e);

				if (!isConnected()) {
					shutdownAsSoonAsPossible();
					return;
				}

			}

		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}
	}
}
