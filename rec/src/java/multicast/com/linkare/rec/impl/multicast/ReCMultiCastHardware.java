package com.linkare.rec.impl.multicast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientHelper;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.MultiCastHardwareHelper;
import com.linkare.rec.acquisition.MultiCastHardwareOperations;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.NotOwnerException;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.events.HardwareLockEvent;
import com.linkare.rec.impl.events.LockCountDown;
import com.linkare.rec.impl.exceptions.IncorrectStateExceptionConstants;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.exceptions.NotAvailableExceptionConstants;
import com.linkare.rec.impl.exceptions.NotOwnerExceptionConstants;
import com.linkare.rec.impl.multicast.security.AllocationManagerSecurityManager;
import com.linkare.rec.impl.multicast.security.DefaultOperation;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.multicast.security.DefaultUser;
import com.linkare.rec.impl.multicast.security.IOperation;
import com.linkare.rec.impl.multicast.security.OperationType;
import com.linkare.rec.impl.multicast.security.ResourceType;
import com.linkare.rec.impl.multicast.security.SecurityManagerFactory;
import com.linkare.rec.impl.threading.AbstractConditionDecisor;
import com.linkare.rec.impl.threading.ConditionChecker;
import com.linkare.rec.impl.utils.MultiCastExperimentStats;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.utils.RegisteredHardwareInfo;
import com.linkare.rec.impl.utils.mapping.DTOMapperUtils;
import com.linkare.rec.impl.utils.mbean.ManagementException;
import com.linkare.rec.impl.utils.mbean.PlatformMBeanServerDelegate;
import com.linkare.rec.impl.wrappers.HardwareWrapper;
import com.linkare.rec.web.ClientInfoDTO;
import com.linkare.rec.web.HardwareStateChangeDTO;
import com.linkare.rec.web.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.web.RecChatMessageDTO;
import com.linkare.rec.web.mbean.MBeanObjectNameFactory;
import com.linkare.rec.web.mbean.NotificationTypeEnum;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCMultiCastHardware implements MultiCastHardwareOperations, NotificationEmitter {

	private static final Logger LOGGER = Logger.getLogger(ReCMultiCastHardware.class.getName());

	private static final long LOCK_PERIOD = Long.parseLong(ReCSystemProperty.MULTICAST_HARDWARE_LOCK_PERIOD.getValue()) * 1000;

	private ClientQueue clientQueue = null;

	private HardwareWrapper hardware = null;

	// is it free (locking=false && ownerDataClient=null), lock available
	// (locking=true && locked=false)
	// locking (locking=true & locked=true) or allready locked (locking=false &
	// locked=true)
	private volatile boolean locking = false;

	private volatile boolean locked = false;

	private volatile boolean startCalled = false;

	private MultiCastExperimentStats experimentStats;

	// ... and by whom?
	private DataClientForQueue ownerDataClient = null;

	// if the poor guy just had a chance to stop others experiments give'm the
	// lock again! Just inform him!
	private static final int OWNER_CHANGE = 0;

	private static final int OWNER_REMOVED_GIVE_STOP_LOCK = 1;

	private static final int OWNER_ONLY_HAD_STOP_LOCK = 2;

	private int ChangeOwnerInNextLockCycle = ReCMultiCastHardware.OWNER_CHANGE;

	private volatile boolean shuttingDown = false;

	private LockCycler currentLocker = null;

	private ConditionChecker checkerStart = null;

	/* Proxy State Information */
	private HardwareState proxyHardwareState = HardwareState.UNKNOWN;

	private HardwareInfo proxyHardwareInfo = null;

	private ReCMultiCastDataProducer recMultiCastDataProducer = null;

	private String proxyHardwareUniqueId = null;

	/** Remote references for myself */
	private transient MultiCastHardware _this = null;

	private ObjectID objectID = null;

	private DefaultResource resource = null;

	private MultiCastHardwareDataClient dataClient = null;

	private ClientQueue mainQueue = null;

	private int maximumClients = 1;

	// to be able to send jmx notifications
	private final NotificationBroadcasterSupport notificationSupport;

	/**
	 * Creates a new instance of MultiCastHardwareImpl
	 * 
	 * @param resource
	 * @param hardware
	 * @param maximumClients
	 * @param mainQueue
	 * @throws Exception
	 */
	public ReCMultiCastHardware(final DefaultResource resource, final Hardware hardware, final int maximumClients,
			final ClientQueue mainQueue) throws Exception {
		this.mainQueue = mainQueue;
		setResource(resource);
		dataClient = new MultiCastHardwareDataClient();

		this.maximumClients = maximumClients;

		clientQueue = new ClientQueue(new ClientQueueAdapter(), maximumClients);

		notificationSupport = new NotificationBroadcasterSupport();

		setHardware(hardware);

		currentLocker = new LockCycler();

		try {
			this.hardware.registerDataClient(dataClient._this());
		} catch (final NotAuthorized e) {
			LOGGER.log(Level.SEVERE,
					"Couldn't register this MultiCastHardware as a DataClient for the Hardware - NotAuthorized! Rethrowing Exception...",
					e);
			throw e;
		}

		// Initializing CORBA Object Implementations
		_this();

	}

	public MultiCastHardware _this() {
		if (_this != null) {
			return _this;
		}

		try {
			return (_this = MultiCastHardwareHelper.narrow(ORBBean
					.getORBBean()
					.getAutoIdRootPOA()
					.servant_to_reference(
							ORBBean.getORBBean().registerAutoIdRootPOAServant(MultiCastHardware.class, this,
									(objectID = new ObjectID())))));
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,"Couldn't register as a MultiCastHardware in the ORB!", e);
			return null;
		}

	}

	// Remote Methods implementation

	@Override
	public void requireLock(final UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotOwnerException, NotAuthorized {
		final DefaultOperation op = new DefaultOperation(OperationType.OP_LOCK);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		synchronized (this) {
			verifyOwnership(user);
			// Se rebentar na linha anterior - bye bye!
			verifyShuttingDown();
			// Se rebentar na linha anterior - sorry! Going down! check with
			// System Admin!
			locked = true;
			currentLocker.stopCountDown();
		}

		// if we got here, then the ownerDataClient!=null &&
		// ownerDataClient=currentDataClient
		synchronized (ownerDataClient) {
			// just verify state
			if (!proxyHardwareState.equals(HardwareState.UNKNOWN)) {
				if (ChangeOwnerInNextLockCycle == ReCMultiCastHardware.OWNER_REMOVED_GIVE_STOP_LOCK) {
					ChangeOwnerInNextLockCycle = ReCMultiCastHardware.OWNER_ONLY_HAD_STOP_LOCK;
				}

				startCalled = false;

				locking = false;
				locked = true;
				cancelTimeoutCycleLockChecker();

				ownerDataClient.getUserInfo().setLockedTime(new DateTime());

				checkerStart = new ConditionChecker(ReCMultiCastHardware.LOCK_PERIOD * 10,
						ReCMultiCastHardware.LOCK_PERIOD, new AbstractConditionDecisor() {
							@Override
							public ConditionResult getConditionResult() {
								if (startCalled) {
									checkerStart = null;
									return ConditionResult.CONDITION_MET_TRUE;
								}
								return ConditionResult.CONDITION_NOT_MET;
							}

							@Override
							public void onConditionTimeOut() {
								LOGGER.log(Level.INFO, "DataClient has Locked the Hardware but has not started it in "
										+ ReCMultiCastHardware.LOCK_PERIOD * 10 / 1000
										+ " s, so I'm cycling the Queue...");
								checkerStart = null;
								dataClientGone(ownerDataClient);
							}
						});
			} else {
				locked = false;
				throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
						proxyHardwareState, HardwareState.STOPED);
			}
		}

	}

	@Override
	public void configure(final UserInfo user, final HardwareAcquisitionConfig configuration)
			throws IncorrectStateException, NotAvailableException, WrongConfigurationException, NotOwnerException,
			NotAuthorized {
		LOGGER.log(Level.INFO, "Received the configuration [" + configuration + "] from user [" + user
				+ "] and the hardware state is [" + getHardwareState() + "]");

		final DefaultOperation op = new DefaultOperation(OperationType.OP_CONFIG);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		op.getProperties().put(IOperation.PROPKEY_ACQ_CONFIG, configuration);
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		verifyShuttingDown();
		verifyOwnership(user);
		// se nao rebentar na linha anteriro podemos dar lock se o estado for ok
		// e nao estivermos a meio de um shutDown...

		// verify state
		if (proxyHardwareState.equals(HardwareState.STOPED) || proxyHardwareState.equals(HardwareState.RESETED)) {
			hardware.configure(configuration);
		} else {
			LOGGER.log(Level.INFO, "Multicast: Configure Request Received.... State is wrong for request: "
					+ proxyHardwareState);
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					proxyHardwareState, HardwareState.CONFIGURING);
		}

	}

	@Override
	public DataProducer start(final UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotOwnerException, NotAuthorized {

		final DefaultOperation op = new DefaultOperation(OperationType.OP_START);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		synchronized (this) {
			if (startCalled) {
				throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION_MSG,
						NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
			}
			startCalled = true;
		}
		verifyShuttingDown();
		verifyOwnership(user);
		// se nao rebentar na linha anterior podemos dar lock se o estado for ok
		// e nao estivermos a meio de um shutDown...

		if (proxyHardwareState.equals(HardwareState.STOPED) || proxyHardwareState.equals(HardwareState.CONFIGURED)) {
			// jobEnded(proxyDataProducer);
			final DefaultResource dataProducerResource = getResource().createChildResource();
			recMultiCastDataProducer = ReCMultiCastDataProducerFactory.createReCMultiCastDataProducer(
					dataProducerResource, new DataProducerAdapter(), getHardwareUniqueId(), maximumClients,
					user.getUserName());

			// registar o objecto corba
			final DataProducer dataProducer = recMultiCastDataProducer._this();
			LOGGER.log(Level.INFO, "Going to start the hardware");
			// iniciar o driver
			recMultiCastDataProducer.setRemoteDataProducer(hardware.start(recMultiCastDataProducer.getDataReceiver()));
			// iniciar a aquisicao de dados do driver
			// recMultiCastDataProducer.initAcquisitionThread();

			experimentStats.startExperimentStats();

			return dataProducer;
		} else {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					proxyHardwareState, HardwareState.CONFIGURING);
		}
	}

	@Override
	public DataProducer startOutput(final UserInfo user, final DataProducer data_source)
			throws IncorrectStateException, NotAvailableException, NotOwnerException, NotAuthorized {
		final DefaultOperation op = new DefaultOperation(OperationType.OP_START_OUTPUT);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		op.getProperties().put(IOperation.PROPKEY_REMOTE_DATAPRODUCER, data_source);
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		synchronized (this) {
			if (startCalled) {
				throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION_MSG,
						NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
			}
			startCalled = true;
		}
		verifyShuttingDown();
		verifyOwnership(user);
		// se nao rebentar na linha anteriro podemos dar lock se o estado for ok
		// e nao estivermos a meio de um shutDown...

		if (proxyHardwareState.equals(HardwareState.STOPED) || proxyHardwareState.equals(HardwareState.CONFIGURED)) {
			// jobEnded(proxyDataProducer);

			final DefaultResource dataProducerResource = getResource().createChildResource();
			recMultiCastDataProducer = ReCMultiCastDataProducerFactory.createReCMultiCastDataProducer(
					dataProducerResource, new DataProducerAdapter(), getHardwareUniqueId(), maximumClients,
					user.getUserName());

			final DataProducer dataProducer = recMultiCastDataProducer._this(); // registar
			// o
			// objecto
			// corba
			LOGGER.log(Level.INFO, "Going to start output the hardware");
			recMultiCastDataProducer.setRemoteDataProducer(hardware.startOutput(
					recMultiCastDataProducer.getDataReceiver(), data_source)); // iniciar
																				// o
																				// driver
			// recMultiCastDataProducer.initAcquisitionThread(); // iniciar a
			// aquisicao de
			// dados do
			// driver

			return dataProducer;
		} else {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					proxyHardwareState, HardwareState.CONFIGURING);
		}

	}

	@Override
	public void stop(final UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotAuthorized {

		final DefaultOperation op = new DefaultOperation(OperationType.OP_STOP);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		verifyOwnership(user);
		verifyShuttingDown();

		// se nao rebentar nas linha anteriro podemos dar lock a outros!
		if (!proxyHardwareState.equals(HardwareState.STARTED)) {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					proxyHardwareState, HardwareState.CONFIGURING);
		}

		hardware.stop();
	}

	@Override
	public void reset(final UserInfo user) throws IncorrectStateException, NotAvailableException, NotOwnerException,
			NotAuthorized {
		final DefaultOperation op = new DefaultOperation(OperationType.OP_RESET);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		verifyOwnership(user);
		verifyShuttingDown();
		// se nao rebentar nas linhas anteriores podemos dar lock a outro...

		// verify state
		if (proxyHardwareState.equals(HardwareState.STARTED) || proxyHardwareState.equals(HardwareState.STOPED)
				|| proxyHardwareState.equals(HardwareState.CONFIGURED)) {
			hardware.reset();
		} else {
			throw new IncorrectStateException(IncorrectStateExceptionConstants.WRONG_HARDWARE_STATE,
					proxyHardwareState, HardwareState.CONFIGURING);
		}

	}

	@Override
	public DataProducer getDataProducer(final UserInfo user) throws IncorrectStateException, NotAvailableException,
			NotAuthorized {

		verifyShuttingDown();

		final ReCMultiCastDataProducer producer = recMultiCastDataProducer;
		if (producer == null) {
			throw new NotAvailableException(NotAvailableExceptionConstants.NO_DATA_PRODUCER_AT_THIS_MOMENT);
		}

		final DefaultOperation op = new DefaultOperation(OperationType.OP_GET_DATAPRODUCER);
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(producer.getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		final DataProducer dataProducer = recMultiCastDataProducer._this();
		// recMultiCastDataProducer.initAcquisitionThread();
		return dataProducer;
	}

	@Override
	public void sendMessage(final UserInfo user, final String clientTo, final String message) throws NotRegistered,
			NotAuthorized {
		clientQueue.sendChatMessage(user, clientTo, message, resource);
		sendNewChatMessageNotification(new RecChatMessageDTO(user.getUserName() + " : " + message,
				new Date().getTime(), null), NotificationTypeEnum.NEW_CHAT_MESSAGE_BY_APPARATUS, user.getUserName());
	}

	@Override
	public HardwareInfo getHardwareInfo(final UserInfo user) throws NotAuthorized, NotRegistered {
		LOGGER.log(Level.FINEST, "Fetching hardware info by user " + user.getUserName());

		if (!mainQueue.contains(user)) {
			LOGGER.log(Level.FINEST, "User " + user.getUserName()
					+ " is not on the main queue... throwing a NotRegistered exception from hardware "
					+ getHardwareUniqueId());
			throw new NotRegistered();
		}
		final DefaultOperation op = new DefaultOperation(OperationType.OP_GET_HARDWAREINFO);
		final HardwareInfo hardwareInfo = getHardwareInfo();
		op.getProperties().put(IOperation.PROPKEY_HARDWAREINFO, hardwareInfo);
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			LOGGER.log(Level.FINEST, "User " + user.getUserName()
					+ " is not authorized to access the hardware info on hardware " + getHardwareUniqueId());
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		LOGGER.log(Level.FINEST, "User " + user.getUserName() + " on hardware " + getHardwareUniqueId()
				+ " will receive a hardware info that is " + (hardwareInfo == null ? "null" : "not null") + "!");
		return hardwareInfo;
	}

	@Override
	public HardwareState getHardwareState(final UserInfo user) throws NotAuthorized, NotRegistered {
		if (!mainQueue.contains(user)) {
			throw new NotRegistered();
		}

		final DefaultOperation op = new DefaultOperation(OperationType.OP_GET_HARDWARESTATE);
		op.getProperties().put(IOperation.PROPKEY_HARDWARESTATE, getHardwareState());
		final DefaultUser securityUser = new DefaultUser(user);

		if (!SecurityManagerFactory.authorize(getResource(), securityUser, op)) {
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		return getHardwareState();
	}

	// end remote methods implementation

	private void setHardware(final Hardware hardware) throws Exception {
		if (hardware == null) {
			this.hardware = null;
			dataClient.hardwareStateChange(HardwareState.UNKNOWN);
			return;
		}

		if (this.hardware != null && this.hardware.isSameDelegate(hardware)) {
			return;
		}

		this.hardware = new HardwareWrapper(hardware);
		try {
			proxyHardwareInfo = this.hardware.getHardwareInfo();
			proxyHardwareUniqueId = proxyHardwareInfo.getHardwareUniqueID();

			experimentStats = MultiCastExperimentStats.getInstance(proxyHardwareInfo, ReCMultiCastHardware.LOCK_PERIOD);

			getResource().getProperties().put(ResourceType.MCHARDWARE.getPropertyKey(), proxyHardwareUniqueId);

			dataClient.hardwareStateChange(this.hardware.getHardwareState());
			LOGGER.log(Level.INFO, "Hardware State is now " + proxyHardwareState);
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,"MultiCastHardware " + getHardwareUniqueId()
					+ ": Couldn't get Remote Hardware properties! Rethrowing exception...", e);
			throw e;
		}
	}

	public String getHardwareUniqueId() {
		if (proxyHardwareUniqueId != null) {
			return proxyHardwareUniqueId;
		}

		return "[unknow HardwareUniqueId]";
	}

	private void verifyOwnership(final UserInfo user) throws NotOwnerException {
		if (ownerDataClient == null || !ownerDataClient.getUserInfo().getUserName().equals(user.getUserName())) {
			if (locked && !locking) {
				throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_LOCKED_TO_ANOTHER,
						ownerDataClient.getUserName());
			} else if (locked && locking) {
				throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_IN_LOCK_PROCESS_TO_ANOTHER,
						ownerDataClient.getUserName());
			} else if (!locked && locking) {
				throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_LOCK_AVAILABLE_TO_ANOTHER,
						ownerDataClient.getUserName());
			} else if (!locked && !locking) {
				throw new NotOwnerException(NotOwnerExceptionConstants.HARDWARE_NOT_LOCKABLE_TO_ANYONE,
						ownerDataClient.getUserName());
			}
		}
	}

	private void verifyShuttingDown() throws NotAvailableException {
		if (shuttingDown) {
			throw new NotAvailableException(NotAvailableExceptionConstants.HARDWARE_SHUTTING_DOWN);
		}
	}

	public void shutdown() {
		if (shuttingDown) {
			return;
		}

		LOGGER.log(Level.INFO, "Shutting down!");

		shuttingDown = true;

		try {
			PlatformMBeanServerDelegate.unregisterHardware(getHardwareUniqueId());
		} catch (ManagementException e) {
			LOGGER.log(Level.SEVERE,"Error unregister mbean...", e);
		}

		if (checkerStart != null) {
			checkerStart.cancelCheck();
		}

		clientQueue.shutdown();

		if (recMultiCastDataProducer != null) {
			recMultiCastDataProducer.shutdown();
		}

		if (currentLocker != null) {
			currentLocker.exit();
			currentLocker = null;
		}

		experimentStats.shutdown();

		LOGGER.log(Level.INFO, "Shut down completed!");

		try {
			ORBBean.getORBBean().deactivateServant(objectID.getOid());
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,"Error deactivating in POA...", e);
		}

		dataClient.shutdown();

		shuttingDown = false;
	}

	/** Utility field holding the MCHardwareChangeListener. */
	// private transient MCHardwareChangeListener
	// listenerMCHardwareChangeListener = null;

	long lastSentHardwareLockableTimestamp = 0;
	ConditionChecker timeoutCycleLockChecker = null;

	private void cancelTimeoutCycleLockChecker() {
		if (timeoutCycleLockChecker != null) {
			timeoutCycleLockChecker.cancelCheck();
		}
	}

	private void cycleLockHardware() {
		try {
			synchronized (this) {
				if (locking && locked) {
					return;// In locking process
				} else if (!locking && locked) {
					return;// Allready Locked
				}

				if (!locked) {
					if (clientQueue.first() == null) {
						return; // the queue was empty
					}

					final DataClientForQueue oldOwnerDataClient = ownerDataClient;

					if (ChangeOwnerInNextLockCycle == ReCMultiCastHardware.OWNER_CHANGE) {
						LOGGER.log(Level.FINEST, "Rotating DataClient Queue for lock...");
						clientQueue.moveFirstToLast();
					}

					if (ChangeOwnerInNextLockCycle == ReCMultiCastHardware.OWNER_ONLY_HAD_STOP_LOCK) {
						LOGGER.log(Level.INFO,
								"Current owner had a stop lock! Now give him the lock, next time give it to another!");
						ChangeOwnerInNextLockCycle = ReCMultiCastHardware.OWNER_CHANGE;
					}

					ownerDataClient = clientQueue.first();
					locking = true;

					if (oldOwnerDataClient != ownerDataClient) {
						experimentStats.lockEventSent();
					}

					if (ownerDataClient != null) {
						lastSentHardwareLockableTimestamp = System.currentTimeMillis();
						clientQueue.hardwareLockable(new HardwareLockEvent(currentLocker,
								ReCMultiCastHardware.LOCK_PERIOD, ownerDataClient));

						timeoutCycleLockChecker = new ConditionChecker(ReCMultiCastHardware.LOCK_PERIOD * 2,
								ReCMultiCastHardware.LOCK_PERIOD, new AbstractConditionDecisor() {
									long sentHardwareLockableTimestamp = lastSentHardwareLockableTimestamp;
									DataClientForQueue owner = ownerDataClient;

									@Override
									public ConditionResult getConditionResult() {
										if (isLockCycleOk()) {
											timeoutCycleLockChecker = null;
											return ConditionResult.CONDITION_MET_TRUE;
										}
										return ConditionResult.CONDITION_NOT_MET;
									}

									@Override
									public void onConditionTimeOut() {
										if (!isLockCycleOk()) {
											LOGGER.log(Level.INFO, "Hardware lock was sent to owner " + owner.getUserName()
													+ " but hasn't locked it, so I'm considering it is gone!");
											timeoutCycleLockChecker = null;
											owner.shutdown();
										}
									}

									private boolean isLockCycleOk() {
										return sentHardwareLockableTimestamp != lastSentHardwareLockableTimestamp
												|| !locking && locked;
									}
								});
					}

					if (ownerDataClient != null && ownerDataClient.getUserInfo().getLockedTime() != null
							&& ownerDataClient.getUserInfo().getLockedTime().getMilliSeconds() != 0) {
						timeStartMin = ownerDataClient.getUserInfo().getLockedTime();
					} else {
						timeStartMin = new DateTime();
					}

				}
			}
		} catch (Throwable t) {
			// Lock Cycler should never exit in any case...
			LOGGER.log(Level.SEVERE, "Problem cycling lock:" + t.getMessage(), t);
		}

	}

	private void dataClientGone(final DataClientForQueue dcfq) {
		if (dcfq == null || ownerDataClient == null) {
			return;
		}

		if (dcfq.equals(ownerDataClient)) {
			locked = false;
			locking = false;

			ownerDataClient = null;

			currentLocker.stopCountDown();

			experimentStats.stopExperimentStats();

			// ChangeOwnerInNextLockCycle=OWNER_REMOVED_GIVE_STOP_LOCK;
			if (clientQueue.isEmpty()) {
				locked = false;
				locking = false;
				currentLocker.stopCountDown();
			} else {
				cycleLockHardware();
			}
		}

	}

	/**
	 * Getter for the usernames of this hardware clients.
	 * 
	 * @return Usernames list.
	 */
	public List<String> getClientUsernames() {
		final List<String> usernames = new ArrayList<String>();

		final Iterator<DataClientForQueue> iterator = clientQueue.iterator();
		while (iterator.hasNext()) {
			final DataClientForQueue dcfq = iterator.next();
			usernames.add(dcfq.getUserName());
		}

		return usernames;
	}

	DateTime timeStartMin = null;

	@Override
	public UserInfo[] getClientList(final UserInfo user) throws NotRegistered, NotAuthorized {
		final UserInfo[] retVal = clientQueue.getUsers(user, resource);
		
		if (retVal != null) {
			final DateTime initTime = new DateTime(timeStartMin);
			final DateTime endTime = new DateTime(initTime);

			if (retVal.length > 0) {
				retVal[0].setNextLockTime(new DateTime(initTime), new DateTime(initTime));
			}

			// Fixed values version
			// long experimentMaxTimeShift =
			// FrequencyUtil.getMaximumExperimentTime(getHardwareInfo());
			// for (int i = 1; i < retVal.length; i++) {
			// initTime.addMillis(LOCK_PERIOD);
			// endTime.addMillis(experimentMaxTimeShift + LOCK_PERIOD);
			// retVal[i].setNextLockTime(new DateTime(initTime), new
			// DateTime(endTime));
			// }

			// Average values version
			final long averageExecutionTime = experimentStats.calcAverageExecutionTime();
			for (int i = 1; i < retVal.length; i++) {
				initTime.addMillis(ReCMultiCastHardware.LOCK_PERIOD);
				endTime.addMillis(averageExecutionTime);
				retVal[i].setNextLockTime(new DateTime(initTime), new DateTime(endTime));
			}
		}
		return retVal;
	}

	public List<UserInfo> getClientList() {
		return clientQueue.getUsers();
	}

	@Override
	public void registerDataClient(final DataClient data_client) throws NotAuthorized, NotAvailableException,
			MaximumClientsReached {
		LOGGER.log(Level.FINEST, "Hardware " + getHardwareUniqueId() + " : registering DataClient!");
		verifyShuttingDown();
		LOGGER.log(Level.FINEST, "Hardware " + getHardwareUniqueId() + " : Verified Shutting down!");
		final boolean startupLockCycler = clientQueue.isEmpty();
		LOGGER.log(Level.FINEST, "clientQueue is empty? " + startupLockCycler);

		// clientQueue.add(mainQueue.getWrapperForDataClient(data_client),resource);
		UserInfo userInfo = null;
		try {
			userInfo = data_client.getUserInfo();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,"Hardware " + getHardwareUniqueId()
					+ " : Got an exception trying to read UserInfo for DataClient... I'm not registering him!", e);
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE);
		}

		if (mainQueue.contains(userInfo)) {
			final boolean addok = clientQueue.add(data_client, resource);

			if (addok) {// A unica maneira que consegui arranjar de registar na
				// mainqueue o hardware connected to
				try {
					final UserInfo[] users = mainQueue.getUsers(userInfo, resource);
					if (users != null) {
						for (final UserInfo user : users) {
							if (user != null && user.getUserName() != null && userInfo != null
									&& userInfo.getUserName() != null) {
								if (user.getUserName().equals(userInfo.getUserName())) {
									user.setHardwaresConnectedTo(getHardwareUniqueId());
								}
							}
							/*
							 * else if(users[i] != null)
							 * users[i].setHardwaresConnectedTo(null);
							 */

						}
					}
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE,"Hardware " + getHardwareUniqueId() + " : Error setting hardware connected to ", e);
				}
			}
		} else {
			LOGGER.log(Level.SEVERE, "Hardware " + getHardwareUniqueId() + " : Data Client: "
					+ data_client.getUserInfo().getUserName()
					+ " is not registered to MultiCastController... How did he ever get to me? Sending NotAuthorized!");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_REGISTER_AT_PARENT_RESOURCE_FIRST);
		}
		sendClientNotification(NotificationTypeEnum.REGISTER_NEW_CLIENT_HARDWARE, data_client.getUserInfo());

		LOGGER.log(Level.FINEST, "Hardware " + getHardwareUniqueId() + " : Added a Data Client: "
				+ data_client.getUserInfo().getUserName() + " to clienQueue!");

		if (startupLockCycler) {
			cycleLockHardware();
		}
	}

	/**
	 * Getter for property resource.
	 * 
	 * @return Value of property resource.
	 * 
	 */
	public DefaultResource getResource() {
		return resource;
	}

	/**
	 * Setter for property resource.
	 * 
	 * @param resource New value of property resource.
	 * 
	 */
	public void setResource(final DefaultResource resource) {
		this.resource = resource;
	}

	private HardwareInfo getHardwareInfo() {
		return proxyHardwareInfo;
	}

	private HardwareState getHardwareState() {
		return proxyHardwareState;
	}

	RegisteredHardwareInfo getRegisteredHardwareInfo() {
		return new RegisteredHardwareInfo(getHardwareUniqueId(), getHardwareState().getValue(), getClientUsernames());
	}

	private void sendNotif(final NotificationTypeEnum notificationType, final MultiCastControllerNotifInfoDTO userData) {
		final Notification notif = notificationType.createNotif(MBeanObjectNameFactory
				.getHardwareObjectName(getHardwareUniqueId()));

		// FIXME: convert to compositedata
		notif.setUserData(userData);

		notificationSupport.sendNotification(notif);
	}

	private void sendStateChangeNotification(final byte oldStateCode, final byte newStateCode) {

		final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO = new MultiCastControllerNotifInfoDTO(
				AllocationManagerSecurityManager.getLaboratoryID(),
				NotificationTypeEnum.HARDWARE_STATE_CHANGE.getType());

		final HardwareStateChangeDTO hardwareStateDTO = new HardwareStateChangeDTO(getHardwareUniqueId(), newStateCode,
				oldStateCode);
		multiCastControllerNotifInfoDTO.setHardwareStateChangeDTO(hardwareStateDTO);

		sendNotif(NotificationTypeEnum.HARDWARE_STATE_CHANGE, multiCastControllerNotifInfoDTO);

	}

	private void sendClientNotification(final NotificationTypeEnum notificationType, final UserInfo client) {

		if (client == null) {
			return;
		}

		final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO = new MultiCastControllerNotifInfoDTO(
				AllocationManagerSecurityManager.getLaboratoryID(), notificationType.getType());

		final ClientInfoDTO clientInfoDTO = new ClientInfoDTO(client.getUserName());
		multiCastControllerNotifInfoDTO.setClientInfoDTO(clientInfoDTO);

		multiCastControllerNotifInfoDTO.setRegisteredHardwareDTO(DTOMapperUtils
				.mapToRegisteredHardwareInfoDTO(getRegisteredHardwareInfo()));

		sendNotif(notificationType, multiCastControllerNotifInfoDTO);

	}

	private void sendNewChatMessageNotification(final RecChatMessageDTO chatMessageDTO,
			final NotificationTypeEnum notificationType, final String userName) {
		final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO = new MultiCastControllerNotifInfoDTO(
				AllocationManagerSecurityManager.getLaboratoryID(), notificationType.getType());

		final ClientInfoDTO clientInfoDTO = new ClientInfoDTO(userName);
		multiCastControllerNotifInfoDTO.setClientInfoDTO(clientInfoDTO);
		multiCastControllerNotifInfoDTO.setRecChatMessageDTO(chatMessageDTO);
		multiCastControllerNotifInfoDTO.setRegisteredHardwareDTO(DTOMapperUtils
				.mapToRegisteredHardwareInfoDTO(getRegisteredHardwareInfo()));

		sendNotif(notificationType, multiCastControllerNotifInfoDTO);

	}

	/**
	 * Getter for property hardware.
	 * 
	 * @return Value of property hardware.
	 * 
	 */
	public HardwareWrapper getHardware() {
		return hardware;
	}

	/* Inner class - ClientQueueListener implementation */
	private class ClientQueueAdapter implements IClientQueueListener {

		@Override
		public void dataClientForQueueIsGone(final DataClientForQueue dcfq) {
			sendClientNotification(NotificationTypeEnum.UNREGISTER_CLIENT_HARDWARE, dcfq.getUserInfo());
			dataClientGone(dcfq);
		}

	}

	/* Inner class - ClientQueueListener implementation */

	/* Inner Class - DataClient Implementation */
	private class MultiCastHardwareDataClient implements DataClientOperations {
		private transient DataClient _this = null;

		private ObjectID objectID = null;

		private final UserInfo userInfo = new UserInfo("MultiCastHardwareUser");

		public DataClient _this() {
			if (_this != null) {
				return _this;
			}

			try {
				return (_this = DataClientHelper.narrow(ORBBean
						.getORBBean()
						.getAutoIdRootPOA()
						.servant_to_reference(
								ORBBean.getORBBean().registerAutoIdRootPOAServant(DataClient.class, this,
										(objectID = new ObjectID())))));
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE,"Couldn't register as a DataClient in the ORB!", e);
				return null;
			}

		}

		@Override
		public UserInfo getUserInfo() {
			return userInfo;
		}

		@Override
		public void hardwareStateChange(final HardwareState newState) {
			if (newState.equals(proxyHardwareState)) {
				return;
			}

			final byte oldState = proxyHardwareState.getValue();

			proxyHardwareState = newState;

			sendStateChangeNotification(proxyHardwareState.getValue(), oldState);

			clientQueue.hardwareStateChange(proxyHardwareState);

			try {
				if (proxyHardwareState.equals(HardwareState.STOPED) || proxyHardwareState.equals(HardwareState.RESETED)) {
					locked = false;
					locking = false;
					if (ownerDataClient != null) {
						// Since passing null DateTime is such a pain in the
						// ass, because of the way the Date and Time
						// helpers(write and read) are implemented, I'll
						// consider null = 0 milliseconds
						ownerDataClient.getUserInfo().setLockedTime(new DateTime(0));
					}
					ownerDataClient = null;

					if (currentLocker != null) {
						currentLocker.stopCountDown();
					}
					experimentStats.stopExperimentStats();
					cycleLockHardware();
					recMultiCastDataProducer = null;
				}
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE,"Error cycling lock...", e);
			}

		}

		/*** BIG SILENT NOOPS ***/

		@Override
		public void hardwareChange() {
			// NOOP - Hardwares won't call this method
		}

		@Override
		public void hardwareLockable(final long millisecs_to_lock_success) {
			// NOOP - Hardwares won't call this method
		}

		@Override
		public void receiveMessage(final String clientFrom, final String clientTo, final String message) {
			// NOOP - Hardwares won't call this method
		}

		/*** END BIG SILENT NOOPS ***/

		public void shutdown() {
			try {
				ORBBean.getORBBean().deactivateServant(objectID.getOid());
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE,"Error deactivating DataClient in POA...", e);
			}
		}

	}

	/* End Inner Class - DataClient Implementation */

	/* Inner Class - ProxyDataProducerCallBack Implementation */
	private class DataProducerAdapter implements ReCMultiCastDataProducerListener {

		@Override
		public void onDataReceiverGone() {
			try {
				if (ownerDataClient == null) {
					return;
				}
				// test if DataClientOwner is still there...
				if (!ownerDataClient.getDataClient().isConnected()) {
					LOGGER.log(Level.INFO, "Current owner is gone!");
					dataClientGone(ownerDataClient);
				}
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE,"Error checking connection to owner client ", e);
			}
		}

	}

	/* End Inner Class - ProxyDataProducerCallBack Implementation */

	/* Inner Class - LockCycler - manages the cycling of locks */
	private class LockCycler extends Thread implements LockCountDown {
		private long endTime = 0;

		private boolean exit = false;

		private Thread currentThread = null;

		private final Object synch = new Object();

		private boolean starting = false;

		public LockCycler() {
			super("Lock Cycler Thread for Hardware " + getHardwareUniqueId());
			synchronized (this) {
				start();
				try {
					wait();
				} catch (final Exception e) {
				}
			}
		}

		public void stopCountDown() {

			synchronized (synch) {
				endTime = 0;
				synch.notifyAll();
				// wait for pause entered...
				try {
					synch.wait();
				} catch (final Exception e) {
				}

			}
		}

		@Override
		public void startCountDown(final long milliseconds_to_lock_success) {
			synchronized (synch) {
				endTime = System.currentTimeMillis() + milliseconds_to_lock_success;
				starting = true;
				synch.notifyAll();
				// wait for counting entered...
				try {
					synch.wait();
				} catch (final Exception e) {
				}
			}
		}

		@Override
		public void run() {
			currentThread = Thread.currentThread();

			synchronized (this) {
				notifyAll();// notify that I got a signal - entering pause
			}
			synchronized (synch) {
				while (!exit) {
					synch.notifyAll();
					try {
						synch.wait(Math.max(endTime - System.currentTimeMillis(), 0));
					} catch (final Exception ignored) {
					}

					if (exit || endTime == 0 || locked) {
						continue;
					}

					if (starting) {
						starting = false;
						continue;
					}

					endTime = 0;
					cycleLockHardware();
				}
			}

		}

		public void exit() {
			endTime = 0;
			exit = true;
			synchronized (synch) {
				synch.notifyAll();
			}
			if (currentThread != null) {
				try {

					currentThread.join();
				} catch (final Exception ignored) {
				}
				currentThread = null;
			}
		}
	}

	/* End Inner Class - LockCycler - manages the cycling of locks */

	/**
	 * Shuts down the users that are connected to this hardware and contained in
	 * the parameter.
	 * 
	 * @param usernamesToKick
	 */
	public void kickUsers(final Set<String> usernamesToKick) {
		final Iterator<DataClientForQueue> iterator = clientQueue.iterator();
		while (iterator.hasNext()) {
			final DataClientForQueue dcfq = iterator.next();
			if (usernamesToKick.contains(dcfq.getUserName())) {
				LOGGER.log(Level.INFO, "Shuting down user [" + dcfq.getUserName() + "] in the hardware ["
						+ getHardwareUniqueId() + "]");
				dcfq.shutdownAsSoonAsPossible();
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws IllegalArgumentException {
		notificationSupport.addNotificationListener(listener, filter, handback);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
		notificationSupport.removeNotificationListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		// FIXME: should only refer to the
		// NotificationType.HARDWARE_STATE_CHANGE
		return NotificationTypeEnum.getMBeanNotificationInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws ListenerNotFoundException {
		notificationSupport.removeNotificationListener(listener, filter, handback);
	}

}