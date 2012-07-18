package com.linkare.rec.impl.multicast;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastControllerOperations;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.events.HardwareAddEvt;
import com.linkare.rec.impl.events.HardwareChangeEvent;
import com.linkare.rec.impl.events.HardwareChangeListener;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.multicast.security.AllocationManagerSecurityManager;
import com.linkare.rec.impl.multicast.security.DefaultOperation;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.multicast.security.DefaultUser;
import com.linkare.rec.impl.multicast.security.IOperation;
import com.linkare.rec.impl.multicast.security.ISecurityCommunicator;
import com.linkare.rec.impl.multicast.security.IUser;
import com.linkare.rec.impl.multicast.security.OperationType;
import com.linkare.rec.impl.multicast.security.SecurityManagerFactory;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.DataProducerActivator;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.utils.RegisteredHardwareInfo;
import com.linkare.rec.impl.utils.mapping.DTOMapperUtils;
import com.linkare.rec.impl.utils.mbean.ManagementException;
import com.linkare.rec.impl.utils.mbean.NotificationManager;
import com.linkare.rec.impl.utils.mbean.PlatformMBeanServerDelegate;
import com.linkare.rec.web.MultiCastControllerNotifInfoDTO;
import com.linkare.rec.web.RecChatMessageDTO;
import com.linkare.rec.web.mbean.NotificationTypeEnum;

/**
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCMultiCastController implements MultiCastControllerOperations, ISecurityCommunicator {

	public static final DataProducerActivator DP_DEACTIVATOR = new DataProducerActivator();
	private static final Logger LOGGER = Logger.getLogger(ReCMultiCastController.class.getName());
	public static final String SYSPROP_MULTICAST_INIT_REF = "rec.multicastcontroller.initref";
	public static final String MULTICAST_INIT_REF = Defaults.defaultIfEmpty(
			System.getProperty(ReCMultiCastController.SYSPROP_MULTICAST_INIT_REF), "MultiCastController");
	public static final String SYSPROP_MULTICAST_BIND_NAME = "rec.multicastcontroller.bindname";
	public static final String MULTICAST_BIND_NAME = Defaults.defaultIfEmpty(
			System.getProperty(ReCMultiCastController.SYSPROP_MULTICAST_BIND_NAME), "MultiCastController");

	/*
	 * i18n support
	 */
	private DefaultResource resource = null;

	/*
	 * The default client queue
	 */
	private ClientQueue clientQueue = null;

	/*
	 * An internal class to adapt to client queues
	 */
	private ClientQueueAdapter clientQueueAdapter = null;

	/*
	 * An internal class to adapt to hardware changes
	 */
	private HardwareChangeAdapter hardwareChangeAdapter = null;

	/*
	 * An internal Thread checking for hardware connections Checks mostly if
	 * they are alive
	 */
	private HardwareConnectionCheck hardwareConnectionChecker = null;

	/*
	 * The maximum number of apparatus available on this lab
	 */
	private static final int MAXIMUM_HARDWARES = Defaults.defaultIfEmpty(
			System.getProperty("rec.multicastcontroller.max.hardwares"), 40);

	/*
	 * The maximum number of clients to register with each hardware
	 */
	private static final int MAXIMUM_CLIENTS_PER_HARDWARE = Defaults.defaultIfEmpty(
			System.getProperty("rec.multicastcontroller.maxclients.per.hardware"), 20);

	/*
	 * The maximum number of clients to register with this lab
	 */
	private static final int MAXIMUM_CLIENTS = ReCMultiCastController.MAXIMUM_CLIENTS_PER_HARDWARE
			* ReCMultiCastController.MAXIMUM_HARDWARES;

	/**
	 * Creates a new instance of ReCMultiCastController
	 */
	public ReCMultiCastController() {
		// log the number of clients available to the hardware
		log(Level.INFO, "MAXIMUM_CLIENTS_PER_HARDWARE = " + ReCMultiCastController.MAXIMUM_CLIENTS_PER_HARDWARE
				+ " MAXIMUM_CLIENTS=" + ReCMultiCastController.MAXIMUM_CLIENTS);

		resource = new DefaultResource();

		// Try to determine the MultiCastController address
		String multiCastLocation = "";
		try {
			multiCastLocation = InetAddress.getLocalHost().getCanonicalHostName();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Error determining MultiCastController Location", e);
		}

		// Create a client queue adapter
		clientQueueAdapter = new ClientQueueAdapter();
		// Create a hardware change adapter
		hardwareChangeAdapter = new HardwareChangeAdapter();

		resource.getProperties().put(resource.getResourceType().getPropertyKey(), multiCastLocation);

		// Create a client queue with a max of MAXIMUM_CLIENTS
		clientQueue = new ClientQueue(clientQueueAdapter, ReCMultiCastController.MAXIMUM_CLIENTS);

		// register mbean to expose some statistics from processingmanager

		try {
			PlatformMBeanServerDelegate.registerThreadPoolExecutorStatisticsMXBean();
			PlatformMBeanServerDelegate.registerMultiCastControllerMXBean(this);
		} catch (ManagementException e) {
			throw new RuntimeException(e);
		}

		// Initialize Security Manager
		SecurityManagerFactory.getSecurityManager().registerMultiCastHardware(multiCastHardwares);

		// TODO register security communicator
		SecurityManagerFactory.getSecurityManager().registerSecurityCommunicator(this);

		// Create a hardware connection checker
		hardwareConnectionChecker = new HardwareConnectionCheck();
		// Start it up
		// hardwareConnectionChecker.start();
		log(Level.INFO, "Started ReCMulticastController OK.");
	}

	/* my Remote Interface Implementation* */
	@Override
	public void registerDataClient(final DataClient data_client) throws NotAuthorized, MaximumClientsReached {
		clientQueue.add(data_client, resource);
		sendClientNotification(data_client.getUserInfo(), NotificationTypeEnum.REGISTER_NEW_CLIENT_MC);
	}

	@Override
	public MultiCastHardware[] enumerateHardware(final UserInfo user) throws NotRegistered, NotAuthorized {
		final IOperation op1 = new DefaultOperation(OperationType.OP_ENUM_HARDWARES);
		final DefaultUser userOp = new DefaultUser(user);

		if (!clientQueue.contains(user)) {
			log(Level.INFO, "Client " + user.getUserName()
					+ " doesn't exist here! - Going to send a NotRegistered Exception!");
			throw new NotRegistered();
		}

		if (!SecurityManagerFactory.authorize(resource, userOp, op1)) {
			log(Level.INFO, "Client " + user.getUserName()
					+ " is not authorized to make this operation! - Going to send a NotAuthorized Exception!");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		return hardwareChangeAdapter.enumerateHardwares(userOp);
	}

	public List<HardwareInfo> unsecureEnumerateHardware() {
		List<HardwareInfo> result = Collections.emptyList();

		final List<ReCMultiCastHardware> unsecureEnumerateHardwares = hardwareChangeAdapter
				.unsecureEnumerateHardwares();
		if (!unsecureEnumerateHardwares.isEmpty()) {
			result = new ArrayList<HardwareInfo>(unsecureEnumerateHardwares.size());
			for (final ReCMultiCastHardware reCMultiCastHardware : unsecureEnumerateHardwares) {
				result.add(reCMultiCastHardware.getHardware().getHardwareInfo());
			}
		}
		return result;
	}

	public List<RegisteredHardwareInfo> getRegisteredHardwareInfo() {

		List<RegisteredHardwareInfo> result = Collections.emptyList();

		final List<ReCMultiCastHardware> unsecureEnumerateHardwares = hardwareChangeAdapter
				.unsecureEnumerateHardwares();

		if (!unsecureEnumerateHardwares.isEmpty()) {

			result = new ArrayList<RegisteredHardwareInfo>(unsecureEnumerateHardwares.size());

			for (final ReCMultiCastHardware reCMultiCastHardware : unsecureEnumerateHardwares) {
				result.add(reCMultiCastHardware.getRegisteredHardwareInfo());
			}
		}
		return result;
	}

	/**
	 * @see com.linkare.rec.acquisition.MultiCastControllerOperations#getClientList(com.linkare.rec.acquisition.UserInfo)
	 */
	@Override
	public UserInfo[] getClientList(final UserInfo user) throws NotRegistered, NotAuthorized {
		log(Level.FINEST,
				"Controller - Getting the client list for user "
						+ (user == null ? "(user is null)" : user.getUserName()));
		final UserInfo[] retVal = clientQueue.getUsers(user, resource);
		log(Level.FINEST, "Controller - Got as retVal " + retVal);
		return retVal;
	}

	public List<UserInfo> getClientList() {
		return clientQueue.getUsers();
	}

	/**
	 * @see com.linkare.rec.acquisition.MultiCastControllerOperations#sendMessage(com.linkare.rec.acquisition.UserInfo,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void sendMessage(final UserInfo user, final String clientTo, final String message) throws NotRegistered,
			NotAuthorized {
		clientQueue.sendChatMessage(user, clientTo, message, resource);
		if (!clientQueue.isJmxAdmin(user)) {
			sendNewChatMessageNotification(
					new RecChatMessageDTO(user.getUserName() + " : " + message, new Date().getTime(), null),
					NotificationTypeEnum.NEW_CHAT_MESSAGE_BY_APPARATUS, user.getUserName());
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendMulticastMessage(final String clientTo, final String message) {
		clientQueue.sendMulticastChatMessage(clientTo, message);
	}

	/*
	 * @see
	 * com.linkare.rec.acquisition.MultiCastControllerOperations#registerHardware
	 * (com.linkare.rec.acquisition.Hardware)
	 */
	@Override
	public void registerHardware(final Hardware hardware) {
		final HardwareAddEvt evt = new HardwareAddEvt(hardware);
		hardwareChangeAdapter.hardwareAdded(evt);
	}

	/**
	 * This method shuts down the ReCMultiCastController It destroys the
	 * internal hardwareConnectionChecker, the hardwareChangeAdapter and the
	 * ClientQueueAdapter
	 */
	public void shutdown() {
		log(Level.INFO, "Shutting down MultiCastController");
		hardwareConnectionChecker.shutdown();
		hardwareChangeAdapter.shutdown();
		clientQueue.shutdown();
	}

	/**
	 * Call shutdown from finalizer to free resources
	 */
	@Override
	protected void finalize() throws Throwable {
		shutdown();
	}

	/*
	 * Internal method to log messages
	 */
	private void log(final Level l, final String message) {
		LOGGER.log(l, message);
	}

	/* Inner Class - Hardware Connection Checker */
	private class HardwareConnectionCheck extends ScheduledWorkUnit {
		// private boolean shutdown = false;

		HardwareConnectionCheck() {
			ExecutorScheduler.scheduleAtFixedRate(this, 1, 60, SECONDS);
		}

		// public void shutdown() {
		// // shutdown = true;
		// // try {
		// // synchronized (this) {
		// // this.join();
		// // }
		// // }
		// // catch (Exception e) {
		// // }
		// }
		@Override
		public void run() {
			// while (!shutdown) {
			// synchronized (this) {
			// try {
			// log(Level.FINE, "Hardware connector waiting...");
			// this.wait(5000);
			// }
			// catch (Exception ignored) {
			// }
			// }
			synchronized (multiCastHardwares) {
				for (final Iterator<ReCMultiCastHardware> it = multiCastHardwares.iterator(); it.hasNext();) {
					final ReCMultiCastHardware rmch = it.next();
					try {
						if (!rmch.getHardware().isConnected()) {
							log(Level.FINEST, "Hardware " + rmch.getHardwareUniqueId() + " is gone! Shutting it down!");
							rmch.shutdown();
							log(Level.FINEST, "Hardware " + rmch.getHardwareUniqueId()
									+ " was shutdown successfully - Now removing it from MCController list!");
							it.remove();
							log(Level.FINEST, "Hardware " + rmch.getHardwareUniqueId()
									+ " removed from MCCContoller sucessfully. Now tell clients it is gone!");

							sendHardwareNotification(rmch, NotificationTypeEnum.UNREGISTER_HARDWARE);

							clientQueue.hardwareChanged(new HardwareChangeEvent());
							log(Level.FINEST, "Clients were told that hardware " + rmch.getHardwareUniqueId()
									+ " is gone!");
						}
					} catch (final Exception e) {
						logThrowable("MultiCastController - Error cheking hardware connection status!", e);
					}
				}
				log(Level.FINE, "Hardware connector waiting... " + Thread.currentThread().getName());
			}
			// }
		}
	}

	/* End Inner Class - Hardware Connection Checker */
	private List<ReCMultiCastHardware> multiCastHardwares = null;

	/* Inner class - HardwareChangeListener implementation */
	// Also contains the list of hardwares... in House Management
	private class HardwareChangeAdapter implements HardwareChangeListener {

		public HardwareChangeAdapter() {
			multiCastHardwares = new LinkedList<ReCMultiCastHardware>();
		}

		@Override
		public void hardwareAdded(final HardwareAddEvt evt) {
			boolean changed = false;
			ReCMultiCastHardware addedHardware = null;
			synchronized (multiCastHardwares) {

				log(Level.INFO, "Trying to add an Hardware");
				String hardwareId = null;

				try {
					if (evt == null || evt.getHardware() == null || evt.getHardware().getHardwareInfo() == null
							|| evt.getHardware().getHardwareInfo().getHardwareUniqueID() == null) {
						log(Level.WARNING, "Error Trying to add an Hardware - Couldn't get it's ID");
						return;
					}
				} catch (final Exception e) {
					log(Level.WARNING, "Exception occurred while access hardware info. " + e.getMessage());
					return;
				}

				hardwareId = evt.getHardware().getHardwareInfo().getHardwareUniqueID();

				final Iterator<ReCMultiCastHardware> iter = multiCastHardwares.iterator();
				while (iter.hasNext()) {// trying to find if hardware exists
					final ReCMultiCastHardware hardware = iter.next();
					if (hardware.getHardwareUniqueId().equals(hardwareId)) {// oppps,
						// allready
						// exists
						if (hardware.getHardware().isSameDelegate(evt.getHardware())) {// same
							// id,
							// same
							// delegate... just
							// return and do nothing
							log(Level.INFO, "Hardware with ID " + hardwareId
									+ " is refreshing registration at the MultiCastController!");
							return;
						} else {// same id, not same delegate... say it and
							// replace delegate...
							log(Level.INFO,
									"There seems to be two hardwares with the same ID registering at this MultiCastController. The ID in question is "
											+ hardwareId + ". Checking to see if the old one is still connected...");
							if (!hardware.getHardware().isConnected()) {
								log(Level.INFO, "The old hardware with " + hardwareId
										+ " has gonne bananas... Shut it down and replace it by a new one...");
								hardware.shutdown();
								iter.remove();
								break;
							} else {
								log(Level.INFO, "The old hardware with " + hardwareId
										+ " is still alive... Please give the new hardware a different ID...");
								return;
							}
						}
					}
				}
				// if we got here means hardware was not found... Yuppi! A new
				// Hardware!
				if (multiCastHardwares.size() >= ReCMultiCastController.MAXIMUM_HARDWARES) {// Shaaamme,,,,
					// I'm
					// full
					log(Level.INFO, "Didn't register Harware with id " + hardwareId
							+ " because I don't have any more slots available. My Hardwares Limit is "
							+ ReCMultiCastController.MAXIMUM_HARDWARES);
					return;
				}

				// PLUS, not full yet... Yuppi!
				try {
					final DefaultResource hardwareResource = resource.createChildResource();
					addedHardware = new ReCMultiCastHardware(hardwareResource, evt.getHardware(),
							ReCMultiCastController.MAXIMUM_CLIENTS_PER_HARDWARE, clientQueue);

				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE,
							"Couldn't create a ReCMultiCastHardware for a Hardware that is registering!", e);
					return;
				}

				log(Level.INFO, "ReCMultiCastHardware " + hardwareId + " was created!");

				multiCastHardwares.add(addedHardware);

				registerMXBeanAndNotificationListener(addedHardware);

				changed = true;
			}
			if (changed) {
				sendHardwareNotification(addedHardware, NotificationTypeEnum.REGISTER_NEW_HARDWARE);
				clientQueue.hardwareChanged(new HardwareChangeEvent());
			}
		}

		public MultiCastHardware[] enumerateHardwares(final IUser userOp) {

			final IOperation op = new DefaultOperation(OperationType.OP_LIST_HARDWARE);

			final ArrayList<MultiCastHardware> multicastHardwareArrayList;
			synchronized (multiCastHardwares) {

				multicastHardwareArrayList = new ArrayList<MultiCastHardware>(multiCastHardwares.size());

				final Iterator<ReCMultiCastHardware> iter = multiCastHardwares.iterator();

				while (iter.hasNext()) {
					final ReCMultiCastHardware mchardware = iter.next();
					op.getProperties().put(IOperation.PROPKEY_HARDWARE_ID, mchardware.getHardwareUniqueId());
					if (SecurityManagerFactory.authorize(mchardware.getResource(), userOp, op)) {
						multicastHardwareArrayList.add(mchardware._this());
					}
				}
			}
			final MultiCastHardware[] retVal = new MultiCastHardware[multicastHardwareArrayList.size()];
			System.arraycopy(multicastHardwareArrayList.toArray(), 0, retVal, 0, retVal.length);
			return retVal;
		}

		public List<ReCMultiCastHardware> unsecureEnumerateHardwares() {

			synchronized (multiCastHardwares) {
				final List<ReCMultiCastHardware> result = new ArrayList<ReCMultiCastHardware>(multiCastHardwares);
				return result;
			}
		}

		// public List<HardwareInfo> unsecureEnumerateHardwares() {
		//
		// synchronized (multiCastHardwares) {
		// final List<HardwareInfo> result = new
		// ArrayList<HardwareInfo>(multiCastHardwares.size());
		//
		// for (final ReCMultiCastHardware hardwareWrapper : multiCastHardwares)
		// {
		// result.add(hardwareWrapper.getHardware().getHardwareInfo());
		// }
		// return result;
		// }
		// }
		public void shutdown() {
			synchronized (multiCastHardwares) {
				log(Level.INFO, "Shutting down HardwareChangeAdapter and all the Hardwares!");

				final Iterator<ReCMultiCastHardware> iter = multiCastHardwares.iterator();

				while (iter.hasNext()) {
					(iter.next()).shutdown();
				}
			}
		}
	}

	/* Inner class - ClientQueueListener implementation */
	private class ClientQueueAdapter implements IClientQueueListener {

		@Override
		public void dataClientForQueueIsGone(final com.linkare.rec.impl.multicast.DataClientForQueue dcfq) {
			sendClientNotification(dcfq.getUserInfo(), NotificationTypeEnum.UNREGISTER_CLIENT_MC);
		}

		@Override
		public void log(final Level debugLevel, final String message) {
			ReCMultiCastController.this.log(debugLevel, message);
		}

		@Override
		public void logThrowable(final String message, final Throwable t) {
			LOGGER.log(Level.SEVERE, message, t);
		}
	}

	private boolean registerMXBeanAndNotificationListener(final ReCMultiCastHardware hardware) {
		boolean result = false;
		try {
			PlatformMBeanServerDelegate.registerHardwareMXBean(hardware);

			PlatformMBeanServerDelegate.addHardwareNotificationListener(hardware.getHardwareUniqueId(),
					NotificationManager.getInstance().getNotificationListener(), null, null);
			result = true;
		} catch (ManagementException e) {
			LOGGER.log(Level.SEVERE, "Error registing MXBean", e);
		}
		return result;
	}

	private void sendNewChatMessageNotification(final RecChatMessageDTO chatMessageDTO,
			final NotificationTypeEnum notificationType, final String userName) {

		final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO = new MultiCastControllerNotifInfoDTO(
				AllocationManagerSecurityManager.getLaboratoryID(), notificationType.getType());

		multiCastControllerNotifInfoDTO.setRecChatMessageDTO(chatMessageDTO);

		NotificationManager.getInstance().sendAsynNotification(notificationType, multiCastControllerNotifInfoDTO);

	}

	private void sendHardwareNotification(final ReCMultiCastHardware multiCastHardware,
			final NotificationTypeEnum notificationType) {

		final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO = new MultiCastControllerNotifInfoDTO(
				AllocationManagerSecurityManager.getLaboratoryID(), notificationType.getType());

		multiCastControllerNotifInfoDTO.setRegisteredHardwareDTO(DTOMapperUtils
				.mapToRegisteredHardwareInfoDTO(multiCastHardware.getRegisteredHardwareInfo()));

		NotificationManager.getInstance().sendAsynNotification(notificationType, multiCastControllerNotifInfoDTO);

	}

	private void sendClientNotification(final UserInfo userInfo, final NotificationTypeEnum notificationType) {

		final MultiCastControllerNotifInfoDTO multiCastControllerNotifInfoDTO = new MultiCastControllerNotifInfoDTO(
				AllocationManagerSecurityManager.getLaboratoryID(), notificationType.getType());
		multiCastControllerNotifInfoDTO.setClientInfoDTO(DTOMapperUtils.mapToClientInfoDTO(userInfo));

		NotificationManager.getInstance().sendAsynNotification(notificationType, multiCastControllerNotifInfoDTO);
	}
}
