package com.linkare.rec.impl.multicast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerHelper;
import com.linkare.rec.acquisition.DataProducerOperations;
import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.DataReceiverHelper;
import com.linkare.rec.acquisition.DataReceiverOperations;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.data.SamplesPacketMatrix;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.events.DataProducerStateChangeEvent;
import com.linkare.rec.impl.events.NewPoisonSamplesEvent;
import com.linkare.rec.impl.events.NewSamplesEvent;
import com.linkare.rec.impl.exceptions.NotAnAvailableSamplesPacketExceptionConstants;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.utils.DataCollector;
import com.linkare.rec.impl.utils.DataCollectorState;
import com.linkare.rec.impl.utils.Deactivatable;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCMultiCastDataProducer extends DataCollector implements DataProducerOperations, Deactivatable,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5596097800609305018L;

	private static final long GET_SAMPLES_IDLE_TIME = Integer
			.parseInt(ReCSystemProperty.MULTICAST_GET_SAMPLES_IDLE_TIME.getValue()) * 1000;

	private static final Logger LOGGER = Logger.getLogger(ReCMultiCastDataProducer.class.getName());

	private transient DataProducer _this = null;
	private transient DataReceiverQueue dataReceiversQueue = null;
	private transient DataReceiverQueueAdapter dataReceiverQueueAdapter = null;
	// private String cachedDataProducerName = null;
	private String oid = null;
	private transient ReCMultiCastDataProducerListener reCMultiCastDataProducerListener = null;
	private IResource resource = null;
	private transient ReCMultiCastDataProducerDataReceiver dataReceiver = null;
	private String user = null;

	private volatile boolean alreadySavedOnRepository = false;
	private int maximum_receivers = 1;

	private long lastGetSamplesTimestamp = System.currentTimeMillis();

	private void writeObject(final ObjectOutputStream stream) throws IOException {
		stream.defaultWriteObject();
	}

	private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
		stream.defaultReadObject();
		setOID(oid);
		initInternalQueue();
	}

	public ReCMultiCastDataProducer(final IResource resource, final int maximum_receivers, final String oid,
			final String user) {
		super();
		this.resource = resource;
		this.setOID(oid);
		this.maximum_receivers = maximum_receivers;
		initInternalQueue();
		dataReceiver = new ReCMultiCastDataProducerDataReceiver();
		this.user = user;
	}

	public ReCMultiCastDataProducer(final HardwareAcquisitionConfig header, final String dataProducerName,
			final String oid, final DataCollectorState dataCollectorState, final SamplesPacketMatrix packetMatrix,
			final String user) {
		super(dataCollectorState, packetMatrix);

		// Try to determine the MultiCastController address
		String multiCastLocation = "";
		try {
			multiCastLocation = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Error determining MultiCastController Location", e);
		}

		DefaultResource parentMCControllerResource = new DefaultResource();
		parentMCControllerResource.getProperties().put(parentMCControllerResource.getResourceType().getPropertyKey(),
				multiCastLocation);

		DefaultResource parentHardwareResource = new DefaultResource(parentMCControllerResource);
		parentHardwareResource.getProperties().put(parentHardwareResource.getResourceType().getPropertyKey(),
				header.getHardwareUniqueID());

		DefaultResource dataProducerResource = new DefaultResource(parentHardwareResource);
		this.resource = dataProducerResource;

		this.cachedAcqHeader = header;
		// this.cachedDataProducerName = dataProducerName;
		this.setOID(oid);
		this.user = user;
		initInternalQueue();
	}

	private void initInternalQueue() {
		dataReceiverQueueAdapter = new DataReceiverQueueAdapter();
		dataReceiversQueue = new DataReceiverQueue(dataReceiverQueueAdapter, maximum_receivers);
	}

	// just for serialize
	public ReCMultiCastDataProducer() {
		// do not call super() - it should release the acquisition Thread...
		// but if we were serialized there's no need in doing it...
	}

	public DataReceiver getDataReceiver() {
		return dataReceiver._this();
	}

	public void setOID(final String oid) {
		this.oid = oid;
		final java.util.Map<String, String> props = resource.getProperties();
		props.put(resource.getResourceType().getPropertyKey(), oid);
	}

	public DataProducer _this() {
		if (_this != null) {
			return _this;
		}

		try {
			LOGGER.log(Level.FINEST, "Trying to create DataProducer CORBA Object... " + getOID());
			final Servant servant = ORBBean.getORBBean().registerDataProducerPOAServant(DataProducer.class, this,
					ORBBean.StrToOid(getOID()), ReCMultiCastController.DP_DEACTIVATOR);
			LOGGER.log(Level.FINEST, "Registered with the POA... " + getOID());
			return (_this = DataProducerHelper.narrow(ORBBean.getORBBean()
					.getDataProducerPOA(ReCMultiCastController.DP_DEACTIVATOR).servant_to_reference(servant)));
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't register this DataProducer with the ORB!", e);
		}
		return _this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setRemoteDataProducer(DataProducer remoteDataProducer) {
		super.setRemoteDataProducer(remoteDataProducer);
		getDataProducerName();
	}

	/**
	 * Operation getDataProducerName
	 */
	@Override
	public String getDataProducerName() {
		return getOID();
	}

	@Override
	public String getOID() {
		return oid;
	}

	@Override
	public POA getPOA() {
		try {
			return ORBBean.getORBBean().getDataProducerPOA(ReCMultiCastController.DP_DEACTIVATOR);
		} catch (final Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isDeactivationPossible() {
		final boolean elapsedTime = lastGetSamplesTimestamp < System.currentTimeMillis()
				- ReCMultiCastDataProducer.GET_SAMPLES_IDLE_TIME;
		if ((!getDataProducerState().equals(DataProducerState.DP_WAITING)) && isExit()
				&& (dataReceiversQueue == null || dataReceiversQueue.isShutdown() || alreadySavedOnRepository) && elapsedTime) {
			LOGGER.log(Level.FINE, getOID() + " is now deactivatable!");
			LOGGER.log(Level.FINE, getOID() + " Data Producer State is " + getDataProducerState()
					+ " and Data Receivers Queue Shutdown is " + dataReceiversQueue.isShutdown()
					+ " DataCollector exit is " + isExit() + " and elapsed time is " + elapsedTime);
			return true;
		} else {
			LOGGER.log(Level.FINE, getOID() + " is not deactivatable! Data Producer State is " + getDataProducerState()
					+ " and Data Receivers Queue Shutdown is " + dataReceiversQueue.isShutdown()
					+ " DataCollector exit is " + isExit() + " and elapsed time is " + elapsedTime);
			return false;
		}
	}


	@Override
	public void registerDataReceiver(final DataReceiver data_receiver) throws MaximumClientsReached {
		LOGGER.log(Level.INFO, "Received request to register a new DataReceiver in ReCMultiCastDataProducer "
				+ getOID());
		try {
			DataProducerState currentDataProducerState = getDataProducerState();
			DataReceiverForQueue drfq = dataReceiversQueue.add(data_receiver, resource, currentDataProducerState);
			LOGGER.log(Level.FINEST,
					"DataReceiverQueue - Informing dataReceiver of current State "+currentDataProducerState+" - just to get him goin'!");
			DataProducerStateChangeEvent currentDataProducerStateChangeEvent = new DataProducerStateChangeEvent(currentDataProducerState);
			if(alreadySavedOnRepository) {
				drfq.stateChanged(new DataProducerStateChangeEvent(DataProducerState.DP_STARTED_NODATA));
				drfq.stateChanged(new DataProducerStateChangeEvent(DataProducerState.DP_STARTED));
			}
			drfq.stateChanged(currentDataProducerStateChangeEvent);
			if(!currentDataProducerState.equals(DataProducerState.DP_WAITING) || !currentDataProducerState.equals(DataProducerState.DP_STARTED_NODATA)) {
				NewSamplesEvent event = this.alreadySavedOnRepository?new NewPoisonSamplesEvent(getLargestPacketKnown()):new NewSamplesEvent(getLargestPacketKnown());
				drfq.newSamples(event);
			}
		} catch (final NotAuthorized e) {
			LOGGER.log(Level.SEVERE, "Couldn't register data receiver: " + data_receiver, e);
		} catch (final MaximumClientsReached mcr) {
			LOGGER.log(Level.SEVERE, "Couldn't register data receiver: " + data_receiver, mcr);
			throw mcr;
		}
	}

	@Override
	public void fireNewSamples() {
		final int maxPacketNum = getMaxPacketNum();
		if ((getTotalSamples() - 1 == maxPacketNum)
				|| (maxPacketNum == -1 && getDataCollectorState().equals(
						com.linkare.rec.impl.utils.DataCollectorState.DP_ENDED))) {
			// condicao de te'rmino da recepcao de dados
			dataReceiversQueue.newPoisonSamples(maxPacketNum);
		} else {
			dataReceiversQueue.newSamples(maxPacketNum);
		}
	}

	@Override
	public void fireStateChanged() {
		dataReceiversQueue.stateChanged(getDataProducerState());
	}

	public void fireOnDataReceiverGone() {
		if (getReCMultiCastDataProducerListener() != null) {
			getReCMultiCastDataProducerListener().onDataReceiverGone();
		}
	}

	/**
	 * Getter for property reCMultiCastDataProducerListener.
	 * 
	 * @return Value of property reCMultiCastDataProducerListener.
	 * 
	 */
	public ReCMultiCastDataProducerListener getReCMultiCastDataProducerListener() {
		return reCMultiCastDataProducerListener;
	}

	/**
	 * Setter for property reCMultiCastDataProducerListener.
	 * 
	 * @param reCMultiCastDataProducerListener New value of property
	 *            reCMultiCastDataProducerListener.
	 * 
	 */
	public void setReCMultiCastDataProducerListener(
			final ReCMultiCastDataProducerListener reCMultiCastDataProducerListener) {
		this.reCMultiCastDataProducerListener = reCMultiCastDataProducerListener;
	}


	/**
	 * Getter for property resource.
	 * 
	 * @return Value of property resource.
	 * 
	 */
	public IResource getResource() {
		return resource;
	}

	public void shutdown() {
		setRemoteDataProducerState(DataProducerState.DP_ERROR);
		dataReceiversQueue.shutdown();
		shutdownThread();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finishDataCollectorRun() {
		super.finishDataCollectorRun();
		// condicao de te'rmino das queues
		LOGGER.log(Level.FINEST, "Finishing data collector run - Creating poison event to terminate queues");
		dataReceiversQueue.newPoisonSamples(getLargestPacketKnown());
	}

	@Override
	public SamplesPacket[] getSamples(final int packetIndexStart, final int packetIndexEnd)
			throws NotAnAvailableSamplesPacketException {
		try {
			lastGetSamplesTimestamp = System.currentTimeMillis();
			return getSamplesPacketSource().getSamplesPackets(packetIndexStart, packetIndexEnd);
		} catch (final SamplesPacketReadException e) {
			LOGGER.log(Level.SEVERE, "Error getting samples packet " + e.getErrorPacketNumber()
					+ " - Throwing NotAnAvailableSamplesPacketException...", e);
			throw new NotAnAvailableSamplesPacketException(
					NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, e.getErrorPacketNumber());
		}
	}

	@Override
	public int getMaxPacketNum() {
		final int maxpacket = getSamplesPacketSource().getLargestNumPacket();
		return maxpacket;
	}

	@Override
	public DataProducerState getDataProducerState() {
		return getDataCollectorState().toDataProducerState();
	}

	public String getUser() {
		return user;
	}

	public boolean alreadySavedOnRepository() {
		return alreadySavedOnRepository;
	}

	public void setAlreadySavedOnRepository() {
		this.alreadySavedOnRepository = true;
	}

	/* Inner class - DataReceiver implementation */
	private class ReCMultiCastDataProducerDataReceiver implements DataReceiverOperations {
		private transient DataReceiver _this = null;
		private transient ObjectID objectID = new ObjectID();

		public DataReceiver _this() {
			if (_this != null) {
				return _this;
			}

			try {
				return (_this = DataReceiverHelper.narrow(ORBBean
						.getORBBean()
						.getAutoIdRootPOA()
						.servant_to_reference(
								ORBBean.getORBBean().registerAutoIdRootPOAServant(DataReceiver.class, this, objectID))));
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Couldn't register DataReceiver with the ORB!", e);
			}

			return _this;
		}

		@Override
		public void newSamples(final int maxpacketNum) {
			ReCMultiCastDataProducer.this.setLargestPacketKnown(maxpacketNum);
		}

		@Override
		public void stateChanged(final DataProducerState newState) {
			ReCMultiCastDataProducer.this.setRemoteDataProducerState(newState);
		}

		@Override
		public void clientsListChanged() {
			// BIG SILENT NOOP - Hardwares Won't call this method
		}

	}

	/* Inner class - DataReceiver implementation */

	/* Inner classs IDataReceiverQueueListener implementation */
	public class DataReceiverQueueAdapter implements IDataReceiverQueueListener {

		@Override
		public void oneDataReceiverForQueueIsGone() {
			ReCMultiCastDataProducer.this.fireOnDataReceiverGone();
		}

	}

}// ReCMultiCastDataProducer
