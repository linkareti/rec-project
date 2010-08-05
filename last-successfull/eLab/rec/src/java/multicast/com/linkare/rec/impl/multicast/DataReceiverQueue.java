/*
 * DataReceiverQueue.java
 *
 * Created on 13 de Agosto de 2002, 0:35
 */

package com.linkare.rec.impl.multicast;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.impl.events.DataProducerStateChangeEvent;
import com.linkare.rec.impl.events.NewPoisonSamplesEvent;
import com.linkare.rec.impl.events.NewSamplesEvent;
import com.linkare.rec.impl.exceptions.MaximumClientsReachedConstants;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */

// TODO -> TESTING implements java.io.Serializable
public class DataReceiverQueue implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1810449051214754798L;

	private boolean shutDown = false;

	private int maximumDataReceivers = 1;

	private IDataReceiverQueueListener dataReceiverQueueListener = null;

	// private internal state variables
	private List<DataReceiverForQueue> queueOrg = new LinkedList<DataReceiverForQueue>();

	private EventQueue messageQueue = new EventQueue(new DataReceiverQueueDispatcher(), this.getClass().getSimpleName());

	private DataReceiversConnectionCheck dataReceiversConnectionChecker = new DataReceiversConnectionCheck();

	private IDataReceiverForQueueListener dataReceiverForQueueAdapter = new DataReceiverForQueueAdapter();

	public static String DATARECEIVERQUEUE_LOGGER = "DataReceiverQueue.Logger";

	/**
	 * Creates a new instance of HardwareDataReceiverQueue
	 * 
	 * @param dataReceiverQueueListener
	 * @param maximumDataReceivers
	 */
	public DataReceiverQueue(IDataReceiverQueueListener dataReceiverQueueListener, int maximumDataReceivers) {
		setDataReceiverQueueListener(dataReceiverQueueListener);
		setMaximumDataReceivers(maximumDataReceivers);
		// dataReceiversConnectionChecker.start();
	}

	/* Delegate Implementations for MCDataProducer */
	// message queue dispatchers
	public void stateChanged(DataProducerState newState) {
		messageQueue.addEvent(new DataProducerStateChangeEvent(newState));
	}

	public void newSamples(int largestPacketNum) {
		messageQueue.addEvent(new NewSamplesEvent(largestPacketNum));
	}

	public void newPoisonSamples(int largestPacketNum) {
		if (!messageQueue.isStopdispatching()) {
			messageQueue.addEvent(new NewPoisonSamplesEvent(largestPacketNum));
		}
	}

	// Helper function for chat messages...

	public void shutdown() {
		if (shutDown)
			return;
		shutDown = true;
		log(Level.INFO, "DataReceiverQueue - shut down process initiated!");
		log(Level.INFO, "DataReceiverQueue - shutting down message queue!");
		messageQueue.shutdown();
		log(Level.INFO, "DataReceiverQueue - message queue is shut down!");

		log(Level.INFO, "DataReceiverQueue - shutting down dataReceivers connection checker!");
		dataReceiversConnectionChecker.shutdown();
		log(Level.INFO, "DataReceiverQueue - dataReceivers connection checker is shut down!");

		log(Level.INFO, "DataReceiverQueue - shutting down dataReceivers!");
		Iterator<DataReceiverForQueue> iter = iterator();
		while (iter.hasNext()) {
			DataReceiverForQueue drfq = (DataReceiverForQueue) iter.next();
			log(Level.INFO, "DataReceiverQueue - shutting down dataReceiver " + drfq.getDataReceiver().getDelegate());
			drfq.shutdown();
			log(Level.INFO, "DataReceiverQueue - dataReceiver " + drfq.getDataReceiver().getDelegate()
					+ " is shut down!");
		}

		log(Level.INFO, "DataReceiverQueue - shut down completed!");
	}

	public boolean add(DataReceiver dr, IResource resource, DataProducerState currentState)
			throws MaximumClientsReached, NotAuthorized {
		log(Level.INFO, "DataReceiverQueue - trying to register new dataReceiver!");
		boolean retVal = false;
		DataReceiverForQueue drfq = new DataReceiverForQueue(dr, dataReceiverForQueueAdapter);
		
		synchronized (queueOrg) {
			if (queueOrg.size() >= getMaximumDataReceivers()) {
				log(
						Level.INFO,
						"DataReceiverQueue - Maximum capacity reached... Going to deny dataReceiver's request for registration! Maybe try later?");
				throw new MaximumClientsReached(
						MaximumClientsReachedConstants.MAXIMUM_RECEIVERS_REACHED_IN_DATA_PRODUCER,
						getMaximumDataReceivers());
			}

			log(Level.INFO, "DataReceiverQueue : checking to see if DataReceiver "
					+ drfq.getDataReceiver().getDelegate() + " is allready registered!");

			if (contains(drfq)) {
				if (!drfq.isConnected()) {
					log(Level.INFO, "DataReceiverQueue : dataReceiver " + drfq.getDataReceiver().getDelegate()
							+ " is not connected - shutting it down!");
					drfq.shutdown();
				} else {
					// datareceiver is allready registered... just ignore it...
					return true;
				}
			}

			log(Level.INFO, "DataReceiverQueue - Going to register dataReceiver "
					+ drfq.getDataReceiver().getDelegate() + " !");
			retVal = queueOrg.add(drfq);
			log(Level.INFO, "DataReceiverQueue - registered dataReceiver " + drfq.getDataReceiver().getDelegate()
					+ (retVal ? "successfully!" : "failed!"));
			log(Level.INFO, "DataReceiverQueue - Informing dataReceiver " + drfq.getDataReceiver().getDelegate()
					+ " of current State... just to get him goin'!");
			drfq.stateChanged(new DataProducerStateChangeEvent(currentState));

		}

		return retVal;
	}

	public Iterator<DataReceiverForQueue> iterator() {
		synchronized (queueOrg) {
			return Collections.unmodifiableList(new LinkedList<DataReceiverForQueue>(queueOrg)).iterator();
		}
	}

	public boolean remove(DataReceiverForQueue drfq) {
		boolean returnVal = false;

		if (queueOrg == null || drfq == null)
			return true;

		synchronized (queueOrg) {
			try {
				if (queueOrg.contains(drfq)) {
					returnVal = queueOrg.remove(drfq);

					log(Level.INFO, "DataReceiverQueue - " + (returnVal ? "Removed" : "Failed to remove")
							+ " dataReceiver " + drfq.getDataReceiver().getDelegate() + "!");
					if (returnVal) {
						log(Level.INFO, "DataReceiverQueue - informing dataReceiver is gone "
								+ drfq.getDataReceiver().getDelegate() + "!");

						if (dataReceiverQueueListener != null)
							dataReceiverQueueListener.oneDataReceiverForQueueIsGone();
					}

				} else
					log(Level.INFO, "DataReceiverQueue - dataReceiver " + drfq.getDataReceiver().getDelegate()
							+ " isn't registered here!");

			} catch (Exception e) {
				logThrowable("DataReceiverQueue - Error removing dataReceiver " + drfq.getDataReceiver().getDelegate()
						+ "!", e);
			}
		}

		return returnVal;
	}

	public boolean contains(DataReceiverForQueue drfq) {
		return queueOrg.contains(drfq);
	}
	
	public boolean isShutdown() {
		return messageQueue.isStopdispatching() && isDispatcherQueueShutdown();
	}
	
	private boolean isDispatcherQueueShutdown() {
		boolean ret = true;
		Iterator<DataReceiverForQueue> queue = iterator();
		while (queue.hasNext()) {
			ret = ret && queue.next().isShutdown();
		}
		return ret;
	}
	
	public boolean isEmpty() {
		return messageQueue.isEmpty() && isDispatcherQueueEmpty();
	}
	
	private boolean isDispatcherQueueEmpty() {
		boolean ret = true;
		Iterator<DataReceiverForQueue> queue = iterator();
		while (queue.hasNext()) {
			ret = ret && queue.next().isEmpty();
		}
		return ret;
	}

	public String toString() {
		StringBuffer returnDataReceivers = new StringBuffer("");

		returnDataReceivers.append("******************************************" + "\r\n");
		returnDataReceivers.append("DataReceiver Queue Contents: \r\n");
		Iterator<DataReceiverForQueue> iter = iterator();
		while (iter.hasNext())
			returnDataReceivers.append(iter.next() + "\r\n");
		returnDataReceivers.append("******************************************" + "\r\n");
		return returnDataReceivers.toString();
	}

	/**
	 * Getter for property maximumDataReceivers.
	 * 
	 * @return Value of property maximumDataReceivers.
	 * 
	 */
	public int getMaximumDataReceivers() {
		return maximumDataReceivers;
	}

	/**
	 * Setter for property maximumDataReceivers.
	 * 
	 * @param maximumDataReceivers New value of property maximumDataReceivers.
	 * 
	 */
	public void setMaximumDataReceivers(int maximumDataReceivers) {
		this.maximumDataReceivers = maximumDataReceivers;
	}

	/**
	 * Getter for property dataReceiverQueueListener.
	 * 
	 * @return Value of property dataReceiverQueueListener.
	 * 
	 */
	public com.linkare.rec.impl.multicast.IDataReceiverQueueListener getDataReceiverQueueListener() {
		return dataReceiverQueueListener;
	}

	/**
	 * Setter for property dataReceiverQueueListener.
	 * 
	 * @param dataReceiverQueueListener New value of property
	 *            dataReceiverQueueListener.
	 * 
	 */
	public void setDataReceiverQueueListener(
			com.linkare.rec.impl.multicast.IDataReceiverQueueListener dataReceiverQueueListener) {
		this.dataReceiverQueueListener = dataReceiverQueueListener;
	}

	/* Proxy Logging methods */
	public void log(Level debugLevel, String message) {
		if (getDataReceiverQueueListener() != null)
			getDataReceiverQueueListener().log(debugLevel, message);
	}

	public void logThrowable(String message, Throwable t) {
		if (getDataReceiverQueueListener() != null)
			getDataReceiverQueueListener().logThrowable(message, t);
	}

	/* Inner Class - Queue Dispatcher */
	private class DataReceiverQueueDispatcher implements EventQueueDispatcher {
		private DataProducerState cachedDataProducerState = null;

		public void dispatchEvent(Object o) {
			if (o instanceof DataProducerStateChangeEvent) {
				DataProducerStateChangeEvent evt = (DataProducerStateChangeEvent) o;
				if (cachedDataProducerState == null || !cachedDataProducerState.equals(evt.getDataProducerState())) {
					cachedDataProducerState = evt.getDataProducerState();

					log(Level.INFO, "DataReceiverQueue - dispatching DataProducer State change event. New State is: "
							+ evt.getDataProducerState());

					Iterator<DataReceiverForQueue> iter = iterator();
					while (iter.hasNext()) {
						try {
							((DataReceiverForQueue) iter.next()).stateChanged(evt);
						} catch (Exception e) {
							logThrowable(
									"DataReceiverQueue - Error dispatching DataProducer State change events to dataReceivers!",
									e);
						}
					}

					return;
				}
			} else if (o instanceof NewSamplesEvent) {
				NewSamplesEvent evt = (NewSamplesEvent) o;
				log(Level.INFO, "DataReceiverQueue - dispatching new samples message event " + evt);

				Iterator<DataReceiverForQueue> iter = iterator();
				while (iter.hasNext()) {
					try {
						((DataReceiverForQueue) iter.next()).newSamples(evt);
					} catch (Exception e) {
						logThrowable("DataReceiverQueue - Error dispatching new samples message event!", e);
					}
				}
				
				// verificar se e' um evento de paragem da thread
				if (evt.isPoisoned()) {
					log(Level.INFO, "DataReceiverQueue - shutting down message queue!");
					messageQueue.shutdown();
					log(Level.INFO, "DataReceiverQueue - shutting down dataReceivers connection checker!");
					dataReceiversConnectionChecker.shutdown();
				}
			}
		}

		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}

	}

	/* End Inner Class - Queue Dispatcher */

	/* Inner Class - DataReceivers Connection Checker */
	private class DataReceiversConnectionCheck extends ScheduledWorkUnit {
		private boolean shutdown = false;

		DataReceiversConnectionCheck() {
			ExecutorScheduler.scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
		}

		public void run() {

			Iterator<DataReceiverForQueue> iterDataReceivers = iterator();
			while (iterDataReceivers.hasNext() /* && !shutdown */) {
				try {
					DataReceiverForQueue drfq = (DataReceiverForQueue) iterDataReceivers.next();
					if (!drfq.isConnected() && !shutdown) {
						drfq.shutdownAsSoonAsPossible();
					}
				} catch (Exception e) {
					logThrowable("DataReceiverQueue - Error cheking dataReceiver connection status!", e);
				}
			}

		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void logThrowable(String message, Throwable throwable) {
			DataReceiverQueue.this.logThrowable(message, throwable);
		}
	}

	/* End Inner Class - DataReceivers Connection Checker */

	/* Inner Class - DataReceivers callbacks */
	private class DataReceiverForQueueAdapter implements IDataReceiverForQueueListener {
		public void dataReceiverForQueueIsGone(DataReceiverForQueue drfq) {
			remove(drfq);
		}

		/* Proxy Logging methods for DataReceiverForQueue */
		public void log(Level debugLevel, String message) {
			Logger.getLogger(DATARECEIVERQUEUE_LOGGER).log(debugLevel, "DataReceiverQueue - " + message);
		}

		public void logThrowable(String message, Throwable t) {
			LoggerUtil.logThrowable("DataReceiverQueue - " + message, t, Logger.getLogger(DATARECEIVERQUEUE_LOGGER));
		}
	}
	/* End Inner Class - DataReceivers callbacks */
}
