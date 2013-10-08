package com.linkare.rec.impl.client.experiment;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import org.omg.PortableServer.Servant;

import com.linkare.rec.acquisition.DataProducer;
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
import com.linkare.rec.impl.utils.DataCollector;
import com.linkare.rec.impl.utils.DataCollectorState;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

/**
 * Abstract implementation of the {@link ExpDataModel} wich is also a
 * {@link DataCollector} and implements the delegate for the
 * {@link DataReceiver} CORBA interface through the
 * {@link DataReceiverOperations} CORBA business interface
 * 
 * @author André Neto - LEFT - IST
 */
public abstract class AbstractExpDataModel extends DataCollector implements ExpDataModel, DataReceiverOperations {

	private static final Logger LOGGER = Logger.getLogger(AbstractExpDataModel.class.getName());

	/** Gnerated UID */
	private static final long serialVersionUID = 5721300601918680941L;

	/**
	 * Cache of the acquisition configuration
	 */
	private HardwareAcquisitionConfig acqHeader = null;

	/**
	 * Cache of the apparatus familiar name (hardware familiar name)
	 */
	private String apparatusName = null;

	// private transient DataReceiver _this = null;
	private transient ObjectID oid = null;

	/**
	 * The currently highest sample index
	 */
	private int largestnumsample = MaxPacketNumUnknown.value;

	// private boolean running = false;
	// private boolean runnedOnce = false;

	/**
	 * Auxiliary object to help in "depacketizing" the samples packets
	 */
	private SamplesPacketSourceDepacketizer depacketizer = null;

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = new EventListenerList();

	/**
	 * The CORBA registered reference
	 */
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
			LOGGER.log(Level.SEVERE, "DataReceiver not registered with ORB...", e);
			return null;
		}

	}

	/**
	 * Setter for property remoteDataProducer.
	 * 
	 * @param remoteDataProducer New value of property remoteDataProducer.
	 */
	@Override
	public void setDpwDataSource(final DataProducer remoteDataProducer) throws MaximumClientsReached {
		
		super.setRemoteDataProducer(remoteDataProducer);

		if (getDataReceiver() != null) {
			try {
				this.remoteDataProducer.registerDataReceiver(_thisDataReceiver);

			} catch (final MaximumClientsReached e) {
				// System.out.println("NOT Registered with dataProducer");
				LOGGER.log(Level.SEVERE, null, e);
				throw e;
			}
		}

		if (getAcquisitionConfig() != null) {
			setTotalSamples(getAcquisitionConfig().getTotalSamples());
			setFrequency((long) getAcquisitionConfig().getSelectedFrequency().getFrequency());

		}
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionConfig() {
		if (acqHeader != null) {
			return acqHeader;
		} else {
			try {
				acqHeader = remoteDataProducer.getAcquisitionHeader();

			} catch (final NotAvailableException nae) {
				LOGGER.log(Level.SEVERE, "Couldn't get Acquisition Header!", nae);
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
			LOGGER.log(Level.SEVERE, "Couldn't get Apparatus Name from Acquisition Header!", npe);
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

		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.finest("Registering listener " + listener + " on AbstractExpDataModel...");
		}

		listenerList.add(com.linkare.rec.impl.client.experiment.ExpDataModelListener.class, listener);

		if (getDataCollectorState().equals(DataCollectorState.DP_ENDED)) {
			listener.dataModelEnded();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_ERROR)) {
			listener.dataModelError();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_STARTED)) {
			listener.dataModelStarted();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_STARTED_NODATA)) {
			listener.dataModelStartedNoData();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_STOPED)) {
			listener.dataModelStoped();
		} else if (getDataCollectorState().equals(DataCollectorState.DP_WAITING)) {
			listener.dataModelWaiting();
		}

		if (lastsample > 0) {
			listener.newSamples(new NewExpDataEvent(this, 0, lastsample));
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
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.log(Level.FINEST, "****** No listeners for AbstractExpDataModel????");
			}
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.log(Level.FINEST, "Informing " + listeners.length + " listeners of NewSamples available in range ["
					+ event.getSamplesStartIndex() + ":" + event.getSamplesEndIndex() + "]");
		}
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.experiment.ExpDataModelListener.class) {
				if (LOGGER.isLoggable(Level.FINEST)) {
					LOGGER.log(Level.FINEST, "Informing " + listeners[i + 1]
							+ " listener of NewSamples available in range [" + event.getSamplesStartIndex() + ":"
							+ event.getSamplesEndIndex() + "]");
				}
				((com.linkare.rec.impl.client.experiment.ExpDataModelListener) listeners[i + 1]).newSamples(event);
			}
		}
	}

	@Override
	public void stateChanged(final com.linkare.rec.acquisition.DataProducerState newState) {
		if (LOGGER.isLoggable(Level.FINEST)) {
			LOGGER.finest("Received Remote State from DataProducer:" + newState.toString());
		}
		setRemoteDataProducerState(newState);
	}

	@Override
	public void fireStateChanged() {
		LOGGER.fine("State CHANGED!");
		if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_ENDED)) {
			fireExpDataModelListenerDataModelEnded();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STOPED)) {
			fireExpDataModelListenerDataModelStoped();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_ERROR)) {
			fireExpDataModelListenerDataModelError();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STARTED)) {
			LOGGER.fine("Data Model Started!");
			fireExpDataModelListenerDataModelStarted();
		} else if (getDataCollectorState().equals(com.linkare.rec.impl.utils.DataCollectorState.DP_STARTED_NODATA)) {
			LOGGER.fine("Data Model Started no data!");
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
			LOGGER.log(Level.SEVERE, null, e);
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

	public String getDataProducerName() {
		return remoteDataProducer.getDataProducerName();
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
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
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
			LOGGER.log(Level.SEVERE, "Exception when getValueAt() from AbstractDataModel", e);
			return null;
		}
	}

	@Override
	public void newSamples(final int largestNumPacket) {
		setLargestPacketKnown(largestNumPacket);
	}

	private int lastsample = 0;

	/*********************************************************************************/
	private class SamplesDepacketizingAdapter implements SamplesSourceEventListener {
		@Override
		public void newSamples(final SamplesSourceEvent evt) {
			try {
				fireExpDataModelListenerNewSamples(evt.getSampleLargestIndex());
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Exception when newSamples(SamplesSourceEvent evt) from AbstractDataModel", e);
			}
		}
	}

	protected void fireExpDataModelListenerNewSamples(final int sampleLargestIndex) {
		if (sampleLargestIndex > largestnumsample) {
			largestnumsample = sampleLargestIndex;
			fireExpDataModelListenerNewSamples(new NewExpDataEvent(this, lastsample, largestnumsample));
			lastsample = largestnumsample;
		} else {
			if (LOGGER.isLoggable(Level.FINEST)) {
				LOGGER.log(Level.FINEST,
						"*********** NOT INFORMING OF NEW EXPERIMENT DATA BECAUSE largestnumsample is higher then sampleLargestIndex : "
								+ largestnumsample + ">" + sampleLargestIndex);
			}
		}
	}

}
