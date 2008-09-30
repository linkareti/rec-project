/*
 * ELABMultiCastController.java Created on 30 de Outubro de 2002, 10:30
 */

package com.linkare.rec.impl.multicast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.linkare.rec.impl.events.HardwareAddEvt;
import com.linkare.rec.impl.events.HardwareChangeEvent;
import com.linkare.rec.impl.events.HardwareChangeListener;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.multicast.security.DefaultOperation;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.multicast.security.DefaultUser;
import com.linkare.rec.impl.multicast.security.IOperation;
import com.linkare.rec.impl.multicast.security.IUser;
import com.linkare.rec.impl.multicast.security.SecurityManagerFactory;
import com.linkare.rec.impl.utils.Defaults;

/**
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCMultiCastController implements MultiCastControllerOperations {

	public static String MCCONTROLLER_LOGGER = "MultiCastController.Logger";

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
	 * An internal Thread checking for hardware connections Checks mostly if they are alive
	 */
	private HardwareConnectionCheck hardwareConnectionChecker = null;

	/*
	 * The maximum number of apparatus available on this lab
	 */
	private static final int MAXIMUM_HARDWARES = 40;

	/*
	 * The maximum number of clients to register with each hardware
	 */
	private static final int MAXIMUM_CLIENTS_PER_HARDWARE = Defaults.defaultIfEmpty(System
			.getProperty("ReC.MultiCastController.MAX_CLIENTS_PER_HARDWARE"), 20);

	/*
	 * The maximum number of clients to register with this lab
	 */
	private static final int MAXIMUM_CLIENTS = MAXIMUM_CLIENTS_PER_HARDWARE * MAXIMUM_HARDWARES;

	/**
	 * Creates a new instance of ReCMultiCastController
	 */
	public ReCMultiCastController() {
		// log the number of clients available to the hardware
		log(Level.INFO, "MAXIMUM_CLIENTS_PER_HARDWARE = " + MAXIMUM_CLIENTS_PER_HARDWARE + " MAXIMUM_CLIENTS="
				+ MAXIMUM_CLIENTS);

		resource = new DefaultResource();

		// Try to determine the MultiCastController address
		String multiCastLocation = "";
		try {
			multiCastLocation = InetAddress.getLocalHost().getCanonicalHostName();
		}
		catch (Exception e) {
			logThrowable("Error determining MultiCastController Location", e);
		}

		try {
			// Create a client queue adapter
			clientQueueAdapter = new ClientQueueAdapter();
			// Create a hardware change adapter
			hardwareChangeAdapter = new HardwareChangeAdapter();

			resource.getProperties().put(resource.getResourceType().getPropertyKey(), multiCastLocation);

			// Create a client queue with a max of MAXIMUM_CLIENTS
			clientQueue = new ClientQueue(clientQueueAdapter, MAXIMUM_CLIENTS);
		}
		catch (Exception e) {
			// REMOVE THIS TRY CATCH BLOCK AFTER FINISHING THE TEST PHASE...
			logThrowable("Error initializing the ReCMultiCastController", e);
		}

		// Create a hardware connection checker
		hardwareConnectionChecker = new HardwareConnectionCheck();
		// Start it up
		hardwareConnectionChecker.start();
	}

	/* my Remote Interface Implementation* */

	public void registerDataClient(DataClient data_client) throws NotAuthorized, MaximumClientsReached {
		clientQueue.add(data_client, resource);
	}

	public MultiCastHardware[] enumerateHardware(UserInfo user) throws NotRegistered, NotAuthorized {
		IOperation op1 = new DefaultOperation(IOperation.OP_ENUM_HARDWARES);
		DefaultUser userOp = new DefaultUser(user);

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

	/**
	 * @see com.linkare.rec.acquisition.MultiCastControllerOperations#getClientList(com.linkare.rec.acquisition.UserInfo)
	 */
	public UserInfo[] getClientList(UserInfo user) throws NotRegistered, NotAuthorized {
		UserInfo[] retVal = clientQueue.getUsers(user, resource);
		return retVal;
	}

	/**
	 * @see com.linkare.rec.acquisition.MultiCastControllerOperations#sendMessage(com.linkare.rec.acquisition.UserInfo,
	 *      java.lang.String, java.lang.String)
	 */
	public void sendMessage(UserInfo user, String clientTo, String message) throws NotRegistered, NotAuthorized {
		clientQueue.sendChatMessage(user, clientTo, message, resource);
	}

	/*
	 * @see com.linkare.rec.acquisition.MultiCastControllerOperations#registerHardware(com.linkare.rec.acquisition.Hardware)
	 */
	public void registerHardware(Hardware hardware) {
		HardwareAddEvt evt = new HardwareAddEvt(hardware);
		hardwareChangeAdapter.hardwareAdded(evt);
	}

	/**
	 * This method shuts down the ReCMultiCastController It destroys the internal hardwareConnectionChecker, the
	 * hardwareChangeAdapter and the ClientQueueAdapter
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
	protected void finalize() throws Throwable {
		shutdown();
	}

	/*
	 * Internal method to log messages
	 */
	private void log(Level l, String message) {
		Logger.getLogger(MCCONTROLLER_LOGGER).log(l, message);
	}

	/*
	 * Internal method to log exceptions
	 */
	private void logThrowable(String message, Throwable t) {
		Logger.getLogger(MCCONTROLLER_LOGGER).log(Level.ALL, message, t);
	}

	/* Inner Class - Hardware Connection Checker */
	private class HardwareConnectionCheck extends Thread {
		private boolean shutdown = false;

		public void shutdown() {
			shutdown = true;
			try {
				synchronized (this) {
					this.join();
				}
			}
			catch (Exception e) {
			}
		}

		public void run() {
			while (!shutdown) {
				synchronized (this) {
					try {
						this.wait(5000);
					}
					catch (Exception ignored) {
					}
				}
				synchronized (multiCastHardwares) {
					Iterator iterHardwares = ((ArrayList) multiCastHardwares.clone()).iterator();
					while (iterHardwares.hasNext()) {
						try {
							ReCMultiCastHardware rmch = (ReCMultiCastHardware) iterHardwares.next();
							if (!rmch.getHardware().isConnected()) {
								rmch.shutdown();
								multiCastHardwares.remove(rmch);
								clientQueue.hardwareChanged(new HardwareChangeEvent());
							}
						}
						catch (Exception e) {
							logThrowable("MultiCastController - Error cheking hardware connection status!", e);
						}
					}
				}

			}
		}
	}

	/* End Inner Class - Hardware Connection Checker */

	private ArrayList<ReCMultiCastHardware> multiCastHardwares = null;

	/* Inner class - HardwareChangeListener implementation */
	// Also contains the list of hardwares... in House Management
	private class HardwareChangeAdapter implements HardwareChangeListener {

		public HardwareChangeAdapter() {
			multiCastHardwares = new ArrayList<ReCMultiCastHardware>(MAXIMUM_HARDWARES);
		}

		public void hardwareAdded(HardwareAddEvt evt) {
			boolean changed = false;
			synchronized (multiCastHardwares) {

				log(Level.INFO, "Trying to add an Hardware");
				String hardwareId = null;

				try {
					hardwareId = evt.getHardware().getHardwareInfo().getHardwareUniqueID();
				}
				catch (Exception e) {
					logThrowable("Error Trying to add an Hardware - Couldn't get it's ID", e);
				}

				Iterator iter = multiCastHardwares.iterator();
				while (iter.hasNext()) {// trying to find if hardware exists
					ReCMultiCastHardware hardware = (ReCMultiCastHardware) iter.next();
					if (hardware.getHardwareUniqueId().equals(hardwareId)) {// oppps,
						// allready
						// exists
						if (hardware.getHardware().isSameDelegate(evt.getHardware())) {// same id, same
							// delegate... just
							// return and do nothing
							log(Level.INFO, "Hardware with ID " + hardwareId
									+ " is refreshing registration at the MultiCastController!");
							return;
						}
						else {// same id, not same delegate... say it and
							// replace delegate...
							log(Level.INFO,
									"There seems to be two hardwares with the same ID registering at this MultiCastController. The ID in question is "
											+ hardwareId + ". Checking to see if the old one is still connected...");
							if (!hardware.getHardware().isConnected()) {
								log(Level.INFO, "The old hardware with " + hardwareId
										+ " has gonne bananas... Shut it down and replace it by a new one...");
								hardware.shutdown();
								multiCastHardwares.remove(hardware);
								break;
							}
							else {
								log(Level.INFO, "The old hardware with " + hardwareId
										+ " is still alive... Please give the new hardware a different ID...");
								return;
							}
						}
					}
				}
				// if we got here means hardware was not found... Yuppi! A new
				// Hardware!
				if (multiCastHardwares.size() >= MAXIMUM_HARDWARES) {// Shaaamme,,,,
					// I'm
					// full
					log(Level.INFO, "Didn't register Harware with id " + hardwareId
							+ " because I don't have any more slots available. My Hardwares Limit is "
							+ MAXIMUM_HARDWARES);
					return;
				}

				// PLUS, not full yet... Yuppi!
				ReCMultiCastHardware addedHardware = null;
				try {
					DefaultResource hardwareResource = resource.createChildResource();
					addedHardware = new ReCMultiCastHardware(hardwareResource, evt.getHardware(),
							MAXIMUM_CLIENTS_PER_HARDWARE, clientQueue);
				}
				catch (Exception e) {
					logThrowable("Couldn't create a ReCMultiCastHardware for a Hardware that is registering!", e);
					return;
				}

				log(Level.INFO, "ReCMultiCastHardware " + hardwareId + " was created!");

				multiCastHardwares.add(addedHardware);
				changed = true;
			}
			if (changed) clientQueue.hardwareChanged(new HardwareChangeEvent());
		}

		public MultiCastHardware[] enumerateHardwares(IUser userOp) {
			ArrayList<MultiCastHardware> multicastHardwareArrayList = new ArrayList<MultiCastHardware>(multiCastHardwares.size());
			IOperation op = new DefaultOperation(IOperation.OP_LIST_HARDWARE);

			synchronized (multiCastHardwares) {
				Iterator iter = multiCastHardwares.iterator();

				while (iter.hasNext()) {
					ReCMultiCastHardware mchardware = (ReCMultiCastHardware) iter.next();
					op.getProperties().put(IOperation.PROPKEY_HARDWARE_ID, mchardware.getHardwareUniqueId());
					if (SecurityManagerFactory.authorize(mchardware.getResource(), userOp, op))
						multicastHardwareArrayList.add(mchardware._this());
				}
			}
			MultiCastHardware[] retVal = new MultiCastHardware[multicastHardwareArrayList.size()];
			System.arraycopy(multicastHardwareArrayList.toArray(), 0, retVal, 0, retVal.length);
			return retVal;
		}

		public void shutdown() {
			synchronized (multiCastHardwares) {
				log(Level.INFO, "Shutting down HardwareChangeAdapter and all the Hardwares!");

				Iterator iter = multiCastHardwares.iterator();

				while (iter.hasNext())
					((ReCMultiCastHardware) iter.next()).shutdown();
			}
		}

	}

	/* Inner class - ClientQueueListener implementation */
	private class ClientQueueAdapter implements IClientQueueListener {

		public void dataClientForQueueIsGone(com.linkare.rec.impl.multicast.DataClientForQueue dcfq) {
			// BIG Silent noop...
		}

		public void log(Level debugLevel, String message) {
			ReCMultiCastController.this.log(debugLevel, message);
		}

		public void logThrowable(String message, Throwable t) {
			ReCMultiCastController.this.logThrowable(message, t);
		}

	}

}
