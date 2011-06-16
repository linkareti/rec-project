/*
 * AbstractExpDataModel.java
 *
 * Created on August 5, 2004, 5:51 PM
 */

package com.linkare.rec.impl.client.experiment;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.omg.PortableServer.Servant;

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

	/** Gnerated UID */
	private static final long serialVersionUID = 5721300601918680941L;

	private static String DATA_RECEIVER_LOGGER = "DataReceiver.Logger";

	private HardwareAcquisitionConfig acqHeader = null;
	private String apparatusName = null;

	// private transient DataReceiver _this = null;
	private transient ObjectID oid = null;

	private int largestnumsample = MaxPacketNumUnknown.value;

	// private boolean running = false;
	// private boolean runnedOnce = false;

	private SamplesPacketSourceDepacketizer depacketizer = null;

	/** Holds value of property remoteDataProducer. */
	private com.linkare.rec.impl.wrappers.DataProducerWrapper remoteDataProducer;

	private DataProducerRunningCheck dataProducerRunningCheck = null;

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	static {
		final Logger l = LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
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
		if (_thisDataReceiver != null) {
			return _thisDataReceiver;
		}

		try {
			oid = new ObjectID();
			Servant registeredServant = ORBBean.getORBBean()
					.registerAutoIdRootPOAServant(DataReceiver.class, this, oid);
			org.omg.CORBA.Object thisReference = ORBBean.getORBBean().getAutoIdRootPOA()
					.servant_to_reference(registeredServant);
			_thisDataReceiver = DataReceiverHelper.narrow(thisReference);
			return _thisDataReceiver;

		} catch (final Exception e) {
			e.printStackTrace();
			LoggerUtil.logThrowable("DataReceiver not registered with ORB...", e,
					LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
			return null;
		}

	}

	/**
	 * Getter for property remoteDataProducer.
	 * 
	 * @return Value of property remoteDataProducer.
	 */
	public com.linkare.rec.impl.wrappers.DataProducerWrapper getDpwDataSource() {
		return remoteDataProducer;
	}

	/**
	 * Setter for property remoteDataProducer.
	 * 
	 * @param remoteDataProducer New value of property remoteDataProducer.
	 */
	@Override
	public void setDpwDataSource(final DataProducerWrapper remoteDataProducer) throws MaximumClientsReached {
		if (!remoteDataProducer.isConnected()) {
			Logger.getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER).log(Level.SEVERE,
					"DataProducer is disconnected! Check it out, please...");
		}
		this.remoteDataProducer = remoteDataProducer;

		if (getDataReceiver() != null) {
			try {
				this.remoteDataProducer.registerDataReceiver(_thisDataReceiver);

			} catch (final MaximumClientsReached e) {
				// System.out.println("NOT Registered with dataProducer");
				LoggerUtil.logThrowable(null, e,
						LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
				throw e;
			}
		}

		setTotalSamples(getAcquisitionConfig().getTotalSamples());
		setFrequency((long) getAcquisitionConfig().getSelectedFrequency().getFrequency());

		dataProducerRunningCheck = new DataProducerRunningCheck();
		dataProducerRunningCheck.startCheck();
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionConfig() {
		if (acqHeader != null) {
			return acqHeader;
		} else {
			try {
				acqHeader = remoteDataProducer.getAcquisitionHeader();

			} catch (final NotAvailableException nae) {
				LoggerUtil.logThrowable("Couldn't get Acquisition Header!", nae,
						LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
			}
		}

		return acqHeader;
	}

	@Override
	public String getApparatusName() {
		if (apparatusName != null) {
			return apparatusName;
		}

		try {
			apparatusName = getAcquisitionConfig().getFamiliarName();
			return apparatusName;
		} catch (final NullPointerException npe) {
			LoggerUtil.logThrowable("Couldn't get Apparatus Name from Acquisition Header!", npe, LogManager
					.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
		}

		return "Unknown";
	}

	/**
	 * Registers ExpDataModelListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public void addExpDataModelListener(final com.linkare.rec.impl.client.experiment.ExpDataModelListener listener) {
		if (listener == null) {
			return;
		}
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.client.experiment.ExpDataModelListener.class, listener);

		if (getDataCollectorState().equals(DataCollectorState.DP_ENDED)) {
			fireExpDataModelListenerDataModelEnded();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_ERROR)) {
			fireExpDataModelListenerDataModelError();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_STARTED)) {
			fireExpDataModelListenerDataModelStarted();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_STARTED_NODATA)) {
			fireExpDataModelListenerDataModelStartedNoData();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_STOPED)) {
			fireExpDataModelListenerDataModelStoped();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_WAITING)) {
			fireExpDataModelListenerDataModelWaiting();
		}
	}

	/**
	 * Removes ExpDataModelListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public void removeExpDataModelListener(final com.linkare.rec.impl.client.experiment.ExpDataModelListener listener) {
		if (listener != null && listenerList != null) {
			listenerList.remove(com.linkare.rec.impl.client.experiment.ExpDataModelListener.class, listener);
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * 
	 */
	private void fireExpDataModelListenerDataModelStoped() {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
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
	private void fireExpDataModelListenerNewSamples(final NewExpDataEvent event) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).newSamples(event);
			}
		}
	}

	@Override
	public void stateChanged(final com.linkare.rec.acquisition.DataProducerState newState) {
		setRemoteDataProducerState(newState);
	}

	@Override
	public void fireStateChanged() {
		System.out.println("State CHANGED!");
		if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_ENDED)) {
			fireExpDataModelListenerDataModelEnded();
			shutdownDataProducerRunningCheck();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STOPED)) {
			fireExpDataModelListenerDataModelStoped();
			shutdownDataProducerRunningCheck();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_ERROR)) {
			fireExpDataModelListenerDataModelError();
			shutdownDataProducerRunningCheck();
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

	public void registerDataReceiver(final com.linkare.rec.acquisition.DataReceiver data_receiver)
			throws com.linkare.rec.acquisition.MaximumClientsReached {
		try {
			remoteDataProducer.registerDataReceiver(data_receiver);
			newSamples(remoteDataProducer.getMaxPacketNum());
		} catch (final com.linkare.rec.acquisition.MaximumClientsReached e) {
			LoggerUtil.logThrowable(null, e,
					LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
			throw e;
		}
	}

	// private boolean remoteDataProducerEnded = false;

	public void producerIsStoped() {
		largestnumsample = remoteDataProducer.getMaxPacketNum();

		// remoteDataProducerEnded = true;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	@Override
	public int getTotalSamples() {
		// o lastSampleNum por omissao tem -1 pois o primeiro index e' o 0
		// (zero)
		return depacketizer.getLastSampleNum() + 1;
	}

	private void deactivateDataReceiver() {
		try {
			ORBBean.getORBBean().deactivateServant(oid.getOid());
		} catch (final Exception e) {
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

	@Override
	public DataProducerWrapper getRemoteDataProducer() {
		return remoteDataProducer;
	}

	@Override
	public void log(final Level debugLevel, final String message) {
		Logger.getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER).log(debugLevel, message);
	}

	@Override
	public void logThrowable(final String message, final Throwable t) {
		LoggerUtil.logThrowable(t.getMessage(), t,
				LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
	}

	@Override
	public void pause() {
		setPause(true);
	}

	@Override
	public void play() {
		setPause(false);
	}

	@Override
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
	@Override
	public boolean isDataAvailable() {
		return (acqHeader != null);
	}

	public SamplesPacket[] getSamplesPackets(final int packetStartIndex, final int packetEndIndex)
			throws SamplesPacketReadException {
		try {
			return remoteDataProducer.getSamples(packetStartIndex, packetEndIndex);
		} catch (final Exception e) {
			LoggerUtil.logThrowable(e.getMessage(), e,
					LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
			return null;
		}
	}

	/**
	 * Registers SamplesPacketSourceEventListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addSamplesPacketSourceEventListener(
			final com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener) {
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
			final com.linkare.rec.impl.data.SamplesPacketSourceEventListener listener) {
		listenerList.remove(com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	// private void fireSamplesPacketSourceEventListenerNewSamplesPackets(
	// com.linkare.rec.impl.data.SamplesPacketSourceEvent event) {
	// if (listenerList == null)
	// return;
	// Object[] listeners = listenerList.getListenerList();
	// for (int i = listeners.length - 2; i >= 0; i -= 2) {
	// if (listeners[i] ==
	// com.linkare.rec.impl.data.SamplesPacketSourceEventListener.class) {
	// ((com.linkare.rec.impl.data.SamplesPacketSourceEventListener) listeners[i
	// + 1])
	// .newSamplesPackets(event);
	// }
	// }
	// }

	@Override
	public boolean isRunning() {
		boolean run = false;
		if (!isPause() && !isExit()) {
			run = true;
		}
		return run;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */

	@Override
	public com.linkare.rec.data.acquisition.PhysicsValue getValueAt(final int sampleIndex, final int channelIndex) {
		try {
			return depacketizer.getSamples(sampleIndex, sampleIndex)[0][channelIndex];
		} catch (final Exception e) {
			e.printStackTrace();
			LoggerUtil.logThrowable("Exception when getValueAt() from AbstractDataModel", e,
					Logger.getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
			return null;
		}
	}

	@Override
	public void newSamples(final int largestNumPacket) {
		setLargestPacketKnown(largestNumPacket);
	}

	/**
	 * Shutdown the RunningCheck thread if alive
	 */
	private void shutdownDataProducerRunningCheck() {
		if (dataProducerRunningCheck != null) {
			dataProducerRunningCheck.shutdown();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finishDataCollectorRun() {
		super.finishDataCollectorRun();
		shutdownDataProducerRunningCheck();
	}

	private int lastsample = 0;

	/*********************************************************************************/
	private class SamplesDepacketizingAdapter implements SamplesSourceEventListener {
		@Override
		public void newSamples(final SamplesSourceEvent evt) {
			try {
				fireExpDataModelListenerNewSamples(evt.getSampleLargestIndex());
			} catch (final Exception e) {
				LoggerUtil.logThrowable("Exception when newSamples(SamplesSourceEvent evt) from AbstractDataModel", e,
						Logger.getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
			}
		}
	}

	protected void fireExpDataModelListenerNewSamples(final int sampleLargestIndex) {
		if (sampleLargestIndex > largestnumsample) {
			largestnumsample = sampleLargestIndex;
			fireExpDataModelListenerNewSamples(new NewExpDataEvent(this, lastsample, largestnumsample));
			lastsample = largestnumsample;
		}
	}

	/*********************************************************************************/
	private class DataProducerRunningCheck extends Thread {
		private long millisChecked = System.currentTimeMillis();
		private Thread currentThread = null;
		private boolean shutdown = false;

		/**
		 * Creates the
		 * <code>AbstractExpDataModel.DataProducerRunningCheck</code>.
		 */
		public DataProducerRunningCheck() {
			super();
			setName(getName() + " - AbstractExpDataModel - DataProducerRunningCheck");
		}

		public void startCheck() {
			setPriority(Thread.NORM_PRIORITY - 2);

			synchronized (this) {
				start();
				try {
					this.wait();
				} catch (final Exception ignored) {
				}
			}
		}

		public void shutdown() {
			shutdown = true;
			synchronized (this) {
				notifyAll();
			}
			try {
				currentThread.join(10000);
			} catch (final Exception ignored) {
			}
		}

		public void checked() {
			millisChecked = System.currentTimeMillis();
			synchronized (this) {
				notifyAll();
			}
		}

		@Override
		public void run() {
			currentThread = Thread.currentThread();
			synchronized (this) {
				notifyAll();
			}
			while (!shutdown) {
				synchronized (this) {
					try {
						this.wait(5000);
					} catch (final Exception ignored) {
					}
				}

				if (millisChecked + 5000 < System.currentTimeMillis()) {
					try {
						if (!remoteDataProducer.isConnected()) {
							Logger.getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER).log(Level.WARNING,
									"remote Data Producer is gone... tested connected and it returned false...");
							remoteDataProducerGone();
							return;
						} else {
							checked();
						}

					} catch (final Exception e) {
						LoggerUtil
								.logThrowable(
										"remote Data Producer is gone... tested connected and it has blown with an exception...",
										e,
										LogManager.getLogManager().getLogger(AbstractExpDataModel.DATA_RECEIVER_LOGGER));
						remoteDataProducerGone();
						return;
					}
				}
			}
			currentThread = null;
		}
	}
}
