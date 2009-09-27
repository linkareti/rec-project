/*
 * AbstractExpDataModel.java
 *
 * Created on August 5, 2004, 5:51 PM
 */

package com.linkare.rec.impl.client.experiment;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.DataReceiverHelper;
import com.linkare.rec.acquisition.DataReceiverOperations;
import com.linkare.rec.acquisition.MaxPacketNumUnknown;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.data.SamplesPacketSourceDepacketizer;
import com.linkare.rec.impl.data.SamplesSourceEvent;
import com.linkare.rec.impl.data.SamplesSourceEventListener;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.DataCollector;
import com.linkare.rec.impl.utils.DataCollectorState;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public abstract class AbstractExpDataModel extends DataCollector implements ExpDataModel, DataReceiverOperations {
	private static String DATA_RECEIVER_LOGGER = "DataReceiver.Logger";

	private HardwareAcquisitionConfig acqHeader = null;
	private String apparatusName = null;

	private transient DataReceiver _this = null;
	private transient ObjectID oid = null;

	private int largestnumsample = MaxPacketNumUnknown.value;

	private boolean running = false;
	private boolean runnedOnce = false;

	private SamplesPacketSourceDepacketizer depacketizer = null;

	/** Holds value of property remoteDataProducer. */
	private com.linkare.rec.impl.wrappers.DataProducerWrapper remoteDataProducer;

	private DataProducerRunningCheck dataProducerRunningCheck = null;

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	static {
		Logger l = LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DATA_RECEIVER_LOGGER));
		}
	}

	private DataReceiver _thisDataReceiver = null;

	public AbstractExpDataModel() {
		super();
		depacketizer = new SamplesPacketSourceDepacketizer();
		depacketizer.addSamplesSourceEventListener(new SamplesDepacketizingAdapter());
		depacketizer.setSamplesPacketSource(getSamplesPacketSource());
	}

	private DataReceiver getDataReceiver() {
		if (_thisDataReceiver != null)
			return _thisDataReceiver;

		try {
			return (_thisDataReceiver = DataReceiverHelper.narrow(ORBBean.getORBBean().getAutoIdRootPOA()
					.servant_to_reference(
							ORBBean.getORBBean().registerAutoIdRootPOAServant(DataReceiver.class, this,
									(oid = new ObjectID())))));

		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.logThrowable("DataReceiver not registered with ORB...", e, LogManager.getLogManager().getLogger(
					DATA_RECEIVER_LOGGER));
			return null;
		}

	}

	/**
	 * Getter for property remoteDataProducer.
	 * 
	 * @return Value of property remoteDataProducer.
	 */
	public com.linkare.rec.impl.wrappers.DataProducerWrapper getDpwDataSource() {
		return this.remoteDataProducer;
	}

	/**
	 * Setter for property remoteDataProducer.
	 * 
	 * @param remoteDataProducer New value of property remoteDataProducer.
	 */
	public void setDpwDataSource(DataProducerWrapper remoteDataProducer) throws MaximumClientsReached {
		if (!remoteDataProducer.isConnected()) {
			Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.SEVERE,
					"DataProducer is disconnected! Check it out, please...");
		}

		this.remoteDataProducer = remoteDataProducer;
		dataProducerRunningCheck = new DataProducerRunningCheck();
		dataProducerRunningCheck.startCheck();

		if (getDataReceiver() != null) {
			try {
				this.remoteDataProducer.registerDataReceiver(_thisDataReceiver);

			} catch (MaximumClientsReached e) {
				// System.out.println("NOT Registered with dataProducer");
				LoggerUtil.logThrowable(null, e, LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
				throw e;
			}

		}
	}

	public HardwareAcquisitionConfig getAcquisitionConfig() {
		if (acqHeader != null)
			return acqHeader;
		else {
			try {
				acqHeader = remoteDataProducer.getAcquisitionHeader();

			} catch (NotAvailableException nae) {
				LoggerUtil.logThrowable("Couldn't get Acquisition Header!", nae, LogManager.getLogManager().getLogger(
						DATA_RECEIVER_LOGGER));
			}
		}

		return acqHeader;
	}

	public String getApparatusName() {
		if (apparatusName != null)
			return apparatusName;

		try {
			apparatusName = getAcquisitionConfig().getFamiliarName();
			return apparatusName;
		} catch (NullPointerException npe) {
			LoggerUtil.logThrowable("Couldn't get Apparatus Name from Acquisition Header!", npe, LogManager
					.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
		}

		return "Unknown";
	}

	/**
	 * Registers ExpDataModelListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public void addExpDataModelListener(com.linkare.rec.impl.client.experiment.ExpDataModelListener listener) {
		if (listener == null)
			return;
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.client.experiment.ExpDataModelListener.class, listener);

		if (getDataCollectorState().equals(DataCollectorState.DP_ENDED))
			fireExpDataModelListenerDataModelEnded();
		else if (getDataCollectorState().equals(DataCollectorState.DP_ERROR))
			fireExpDataModelListenerDataModelError();
		else if (getDataCollectorState().equals(DataCollectorState.DP_STARTED))
			fireExpDataModelListenerDataModelStarted();
		else if (getDataCollectorState().equals(DataCollectorState.DP_STARTED_NODATA))
			fireExpDataModelListenerDataModelStartedNoData();
		else if (getDataCollectorState().equals(DataCollectorState.DP_STOPED))
			fireExpDataModelListenerDataModelStoped();
		else if (getDataCollectorState().equals(DataCollectorState.DP_WAITING))
			fireExpDataModelListenerDataModelWaiting();
	}

	/**
	 * Removes ExpDataModelListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public void removeExpDataModelListener(com.linkare.rec.impl.client.experiment.ExpDataModelListener listener) {
		if (listener != null && listenerList != null)
			listenerList.remove(com.linkare.rec.impl.client.experiment.ExpDataModelListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelStoped() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).dataModelStoped();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelStarted() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).dataModelStarted();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelStartedNoData() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1])
						.dataModelStartedNoData();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelError() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).dataModelError();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelEnded() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).dataModelEnded();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelWaiting() {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).dataModelWaiting();
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireExpDataModelListenerNewSamples(NewExpDataEvent event) {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).newSamples(event);
			}
		}
	}

	public void stateChanged(com.linkare.rec.acquisition.DataProducerState newState) {
		setRemoteDataProducerState(newState);
	}

	public void fireStateChanged() {
		System.out.println("State CHANGED!");
		if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_ENDED)) {
			fireExpDataModelListenerDataModelEnded();
			if (dataProducerRunningCheck != null) {
				// long millis=System.currentTimeMillis();
				dataProducerRunningCheck.shutdown();
				// System.out.println("Took me "+(System.currentTimeMillis()-millis)/1000
				// +
				// " s to shutdown the Thread checking if remote data producer is alive...");
			}
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STOPED)) {
			fireExpDataModelListenerDataModelStoped();
			if (dataProducerRunningCheck != null) {
				// long millis=System.currentTimeMillis();
				dataProducerRunningCheck.shutdown();
				// System.out.println("Took me "+(System.currentTimeMillis()-millis)/1000
				// +
				// " s to shutdown the Thread checking if remote data producer is alive...");
			}
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_ERROR)) {
			fireExpDataModelListenerDataModelError();
			if (dataProducerRunningCheck != null) {
				// long millis=System.currentTimeMillis();
				dataProducerRunningCheck.shutdown();
				// System.out.println("Took me "+(System.currentTimeMillis()-millis)/1000
				// +
				// " s to shutdown the Thread checking if remote data producer is alive...");
			}
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STARTED)) {
			System.out.println("Data Model Started!");
			fireExpDataModelListenerDataModelStarted();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STARTED_NODATA)) {
			System.out.println("Data Model Started no data!");
			fireExpDataModelListenerDataModelStartedNoData();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_WAITING)) {
			fireExpDataModelListenerDataModelWaiting();
		}
	}

	public void registerDataReceiver(com.linkare.rec.acquisition.DataReceiver data_receiver)
			throws com.linkare.rec.acquisition.MaximumClientsReached {
		try {
			this.remoteDataProducer.registerDataReceiver(data_receiver);
			newSamples(this.remoteDataProducer.getMaxPacketNum());
		} catch (com.linkare.rec.acquisition.MaximumClientsReached e) {
			LoggerUtil.logThrowable(null, e, LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
			throw e;
		}
	}

	private boolean remoteDataProducerEnded = false;

	public void producerIsStoped() {
		largestnumsample = remoteDataProducer.getMaxPacketNum();

		remoteDataProducerEnded = true;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	// TODO CHECK WITH JP
	public int getTotalSamples() {
		return depacketizer.getLastSampleNum();
		// return getAcquisitionConfig().getTotalSamples();
		/*
		 * if(samples!=null) { synchronized(samples) { return samples.size(); }
		 * } else return 0;
		 */
	}

	private void deactivateDataReceiver() {
		try {
			ORBBean.getORBBean().deactivateServant(oid.getOid());
		} catch (Exception e) {
			e.printStackTrace();
		}

		_thisDataReceiver = null;
	}

	public void remoteDataProducerGone() {
		deactivateDataReceiver();
	}

	/*
	 * public com.linkare.rec.data.config.HardwareAcquisitionConfig
	 * getAcquisitionHeader() throws
	 * com.linkare.rec.acquisition.NotAvailableException { if(acqHeader!=null)
	 * return acqHeader; else { try {
	 * acqHeader=remoteDataProducer.getAcquisitionHeader();
	 * 
	 * }catch(NotAvailableException nae) {
	 * LoggerUtil.logThrowable("Couldn't get Acquisition Header!"
	 * ,nae,LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER)); } }
	 * 
	 * return acqHeader; }
	 */

	public String getDataProducerName() {
		return remoteDataProducer.getDataProducerName();
	}

	public DataProducerWrapper getRemoteDataProducer() {
		return remoteDataProducer;
	}

	public void log(Level debugLevel, String message) {
	}

	public void logThrowable(String message, Throwable t) {
	}

	public void pause() {
		setPause(true);
	}

	public void play() {
		setPause(false);
	}

	public void stopNow() {
		producerIsStoped();

		deactivateDataReceiver();
		setExit(true);
	}

	/**
	 * Getter for property dataAvailable.
	 * 
	 * @return Value of property dataAvailable.
	 */
	public boolean isDataAvailable() {
		return (acqHeader != null);
	}

	public SamplesPacket[] getSamplesPackets(int packetStartIndex, int packetEndIndex)
			throws SamplesPacketReadException {
		try {
			return remoteDataProducer.getSamples(packetStartIndex, packetEndIndex);
		} catch (Exception e) {
			LoggerUtil.logThrowable(e.getMessage(), e, LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
			return null;
		}
	}

	/**
	 * Registers SamplesPacketSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addSamplesPacketSourceEventListener(
			com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Removes SamplesPacketSourceEventListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeSamplesPacketSourceEventListener(
			com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener) {
		listenerList.remove(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireSamplesPacketSourceEventListenerNewSamplesPackets(
			com.linkare.rec.impl.data.SamplesPacketSourceEvent event) {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class) {
				((com.linkare.rec.impl.data.SamplesPacketSourceEventListener) listeners[i + 1])
						.newSamplesPackets(event);
			}
		}
	}

	public boolean isRunning() {
		boolean run = false;
		if (!isPause() && !isExit())
			run = true;
		return run;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */

	public com.linkare.rec.data.acquisition.PhysicsValue getValueAt(int sampleIndex, int channelIndex) {
		try {
			return depacketizer.getSamples(sampleIndex, sampleIndex)[0][channelIndex];
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.logThrowable("Exception when getValueAt() from AbstractDataModel", e, Logger
					.getLogger(DATA_RECEIVER_LOGGER));
			return null;
		}
	}

	public void newSamples(int largestNumPacket) {
		setLargestPacketKnown(largestNumPacket);
	}

	private int lastsample = 0;

	/*********************************************************************************/
	private class SamplesDepacketizingAdapter implements SamplesSourceEventListener {
		public void newSamples(SamplesSourceEvent evt) {
			try {
				largestnumsample = evt.getSampleLargestIndex();
				fireExpDataModelListenerNewSamples(new NewExpDataEvent(this, lastsample, largestnumsample));
				lastsample = largestnumsample;
			} catch (Exception e) {
				LoggerUtil.logThrowable("Exception when newSamples(SamplesSourceEvent evt) from AbstractDataModel", e,
						Logger.getLogger(DATA_RECEIVER_LOGGER));
			}
		}
	}

	/*********************************************************************************/
	private class DataProducerRunningCheck extends Thread {
		private long millisChecked = System.currentTimeMillis();
		private Thread currentThread = null;
		private boolean shutdown = false;

		public void startCheck() {
			setPriority(Thread.NORM_PRIORITY - 2);

			synchronized (this) {
				start();
				try {
					this.wait();
				} catch (Exception ignored) {
				}
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				this.notifyAll();
			}
			try {
				currentThread.join(10000);
			} catch (Exception ignored) {
			}
		}

		public void checked() {
			millisChecked = System.currentTimeMillis();
			synchronized (this) {
				this.notifyAll();
			}
		}

		public void run() {
			currentThread = currentThread();
			synchronized (this) {
				this.notifyAll();
			}
			while (!shutdown) {
				synchronized (this) {
					try {
						this.wait(5000);
					} catch (Exception ignored) {
					}
				}

				if (millisChecked + 5000 < System.currentTimeMillis()) {
					try {
						if (!remoteDataProducer.isConnected()) {
							Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.WARNING,
									"remote Data Producer is gone... tested connected and it returned false...");
							remoteDataProducerGone();
							return;
						} else
							checked();

					} catch (Exception e) {
						LoggerUtil
								.logThrowable(
										"remote Data Producer is gone... tested connected and it has blown with an exception...",
										e, LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
						remoteDataProducerGone();
						return;
					}
				}
			}
			currentThread = null;
		}
	}
}
