/*
 * ReCMultiCastDataProducer.java
 *
 * Created on 5 de Novembro de 2002, 12:54
 */

package com.linkare.rec.impl.multicast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;

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
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.SamplesPacketMatrix;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.exceptions.NotAnAvailableSamplesPacketExceptionConstants;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.ResourceType;
import com.linkare.rec.impl.utils.DataCollector;
import com.linkare.rec.impl.utils.DataCollectorState;
import com.linkare.rec.impl.utils.Deactivatable;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;

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

	private static final long GET_SAMPLES_IDLE_TIME = Defaults.defaultIfEmpty(
			System.getProperty("ReC.MultiCastDataProducer.GET_SAMPLES_IDLE_TIME"), 60) * 1000;

	private transient DataProducerWrapper remoteDataProducer = null;
	private transient DataProducer _this = null;
	private transient DataReceiverQueue dataReceiversQueue = null;
	private transient DataReceiverQueueAdapter dataReceiverQueueAdapter = null;
	private HardwareAcquisitionConfig cachedAcqHeader = null;
//	private String cachedDataProducerName = null;
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
		// TODO pq inicializar as queues? lanca threads q n morrem
		// initInternalQueue();
	}

	public ReCMultiCastDataProducer(final IResource resource, final int maximum_receivers, final String oid,
			final String user) {
		super();
		this.oid = oid;

		this.resource = resource;
		this.maximum_receivers = maximum_receivers;
		initInternalQueue();
		dataReceiver = new ReCMultiCastDataProducerDataReceiver();
		this.user = user;
	}

	public ReCMultiCastDataProducer(final HardwareAcquisitionConfig header, final String dataProducerName,
			final String oid, final DataCollectorState dataCollectorState, final SamplesPacketMatrix packetMatrix,
			final String user) {
		super(dataCollectorState, packetMatrix);
		this.cachedAcqHeader = header;
		//this.cachedDataProducerName = dataProducerName;
		this.oid = oid;
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

	public void setRemoteDataProducer(final DataProducer remoteDataProducer) {
		this.remoteDataProducer = new DataProducerWrapper(remoteDataProducer);
		setPriority(Thread.MAX_PRIORITY - 1);
		try {
			setTotalSamples(getAcquisitionHeader().getTotalSamples());
			setFrequency((long) getAcquisitionHeader().getSelectedFrequency().getFrequency());
		} catch (final NotAvailableException e) {
			logThrowable("Error getting AcquisitionHeader info", e);
		}
		try {
			setLargestPacketKnown(getRemoteDataProducer().getMaxPacketNum());
		} catch (final Exception e) {
			logThrowable("Error getting Remote Data Producer Max Packet Num", e);
		}
		try {
			setRemoteDataProducerState(getRemoteDataProducer().getDataProducerState());
		} catch (final Exception e) {
			logThrowable("Error getting Remote Data Producer Max Packet Num", e);
		}
		// first caching of dataproducername
		getDataProducerName();
	}

	public void setOID(final String oid) {
		this.oid = oid;
		if (resource != null) {
			final java.util.Map<String, String> props = resource.getProperties();
			props.put(ResourceType.DATAPRODUCER.getPropertyKey(), oid);
			((DefaultResource) resource).setProperties(props);
		}
	}

	public DataProducer _this() {
		if (_this != null) {
			return _this;
		}

//		if (oid == null) {
//			setOID(getFileName());
//		}

		try {
			log(Level.FINEST, "Trying to create DataProducer CORBA Object... " + getOID());
			final Servant servant = ORBBean.getORBBean().registerDataProducerPOAServant(DataProducer.class, this,
					ORBBean.StrToOid(getOID()), ReCMultiCastController.DP_DEACTIVATOR);
			log(Level.FINEST, "Registered with the POA... " + getOID());
			return (_this = DataProducerHelper.narrow(ORBBean.getORBBean()
					.getDataProducerPOA(ReCMultiCastController.DP_DEACTIVATOR).servant_to_reference(servant)));
		} catch (final Exception e) {
			logThrowable("Couldn't register this DataProducer with the ORB!", e);
		}
		return _this;
	}

	@Override
	public HardwareAcquisitionConfig getAcquisitionHeader() throws NotAvailableException {
		if (cachedAcqHeader == null && remoteDataProducer != null) {
			try {
				cachedAcqHeader = remoteDataProducer.getAcquisitionHeader();
			} catch (final NotAvailableException e) {
				logThrowable("Couldn't get Acquisition Header! - Rethrowing exception...", e);
				throw e;
			} catch (final Exception e) {
				logThrowable("Other reason - Couldn't get Acquisition Header! Throwing not available exception...", e);
				throw new NotAvailableException();
			}
		}

		return cachedAcqHeader;
	}

	/**
	 * Operation getDataProducerName
	 */
	@Override
	public String getDataProducerName() {
		// if (cachedDataProducerName == null && remoteDataProducer != null) {
		// try {
		// cachedDataProducerName = remoteDataProducer.getDataProducerName();
		// } catch (final Exception e) {
		// logThrowable("Other reason - Couldn't get DataProducerName!", e);
		// }
		// }
		// return cachedDataProducerName;
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
				&& dataReceiversQueue.isShutdown() && elapsedTime) {
			log(Level.FINE, getOID() + " is now deactivatable!");
			log(Level.FINE, getOID() + " Data Producer State is " + getDataProducerState()
					+ " and Data Receivers Queue Shutdown is " + dataReceiversQueue.isShutdown()
					+ " DataCollector exit is " + isExit() + " and elapsed time is " + elapsedTime);
			return true;
		} else {
			log(Level.FINE, getOID() + " is not deactivatable! Data Producer State is " + getDataProducerState()
					+ " and Data Receivers Queue Shutdown is " + dataReceiversQueue.isShutdown()
					+ " DataCollector exit is " + isExit() + " and elapsed time is " + elapsedTime);
			return false;
		}
	}

//	private String fileName = null;
//
//	public String getFileName() {
//		return fileName;
//	}

	@Override
	public void registerDataReceiver(final DataReceiver data_receiver) throws MaximumClientsReached {
		log(Level.INFO, "Received request to register a new DataReceiver in ReCMultiCastDataProducer " + getOID());
		try {
			dataReceiversQueue.add(data_receiver, resource, getDataProducerState());
		} catch (final NotAuthorized e) {
			logThrowable("Couldn't register data receiver: " + data_receiver, e);
		} catch (final MaximumClientsReached mcr) {
			logThrowable("Couldn't register data receiver: " + data_receiver, mcr);
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

	/* Proxy Logging methods */
	@Override
	public void log(final Level debugLevel, final String message) {
		if (getReCMultiCastDataProducerListener() != null) {
			getReCMultiCastDataProducerListener().log(debugLevel, "DataProducer " + getOID() + " - " + message);
		}
	}

	@Override
	public void logThrowable(final String message, final Throwable t) {
		if (getReCMultiCastDataProducerListener() != null) {
			getReCMultiCastDataProducerListener().logThrowable("DataProducer " + getOID() + " - " + message, t);
		}
	}

	public void fireOneDataReceiverGone() {
		if (getReCMultiCastDataProducerListener() != null) {
			getReCMultiCastDataProducerListener().oneDataReceiverGone();
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

	@Override
	public DataProducerWrapper getRemoteDataProducer() {
		return remoteDataProducer;
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
		log(Level.FINEST, "Finishing data collector run - Creating poison event to terminate queues");
		dataReceiversQueue.newPoisonSamples(getLargestPacketKnown());
	}

	@Override
	public SamplesPacket[] getSamples(final int packetIndexStart, final int packetIndexEnd)
			throws NotAnAvailableSamplesPacketException {
		try {
			lastGetSamplesTimestamp = System.currentTimeMillis();
			return getSamplesPacketSource().getSamplesPackets(packetIndexStart, packetIndexEnd);
		} catch (final SamplesPacketReadException e) {
			logThrowable("Error getting samples packet " + e.getErrorPacketNumber()
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
				logThrowable("Couldn't register DataReceiver with the ORB!", e);
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

		public void shutdown() {
			try {
				ORBBean.getORBBean().getAutoIdRootPOA().deactivate_object(objectID.getOid());
			} catch (final Exception e) {
				logThrowable(getClass().getName() + " Error deactivating object " + objectID, e);
			}
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
		public void log(final Level debugLevel, final String message) {
			ReCMultiCastDataProducer.this.log(debugLevel, message);
		}

		@Override
		public void logThrowable(final String message, final Throwable t) {
			ReCMultiCastDataProducer.this.logThrowable(message, t);
		}

		@Override
		public void oneDataReceiverForQueueIsGone() {
			ReCMultiCastDataProducer.this.fireOneDataReceiverGone();
		}

	}

}// ReCMultiCastDataProducer
