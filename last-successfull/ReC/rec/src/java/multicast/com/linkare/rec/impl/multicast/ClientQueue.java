/*
 * ClientQueue.java
 *
 * Created on 13 de Agosto de 2002, 0:35
 */

package com.linkare.rec.impl.multicast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.events.HardwareChangeEvent;
import com.linkare.rec.impl.events.HardwareLockEvent;
import com.linkare.rec.impl.events.HardwareStateChangeEvent;
import com.linkare.rec.impl.exceptions.MaximumClientsReachedConstants;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.multicast.security.DefaultOperation;
import com.linkare.rec.impl.multicast.security.DefaultUser;
import com.linkare.rec.impl.multicast.security.IOperation;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.SecurityManagerFactory;
import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ClientQueue {

	private boolean cyclingQueue = false;

	private boolean shutDown = false;

	private int maximumClients = 1;

	private IClientQueueListener clientQueueListener = null;

	// private internal state variables
	private List<DataClientForQueue> queueOrg = new LinkedList<DataClientForQueue>();

	private EventQueue messageQueue = new EventQueue(new ClientQueueDispatcher());

	private ClientsConnectionCheck clientsConnectionChecker = new ClientsConnectionCheck();

	private IDataClientForQueueListener dataClientForQueueAdapter = new DataClientForQueueAdapter();

	/**
	 * Creates the <code>ClientQueue</code>.
	 * 
	 * @param clientQueueListener - The listener party
	 * @param maximumClients - Maximum number of clients to be registered in the
	 *            queue
	 */
	public ClientQueue(IClientQueueListener clientQueueListener, int maximumClients) {
		setClientQueueListener(clientQueueListener);
		setMaximumClients(maximumClients);
		ExecutorScheduler.scheduleAtFixedRate(clientsConnectionChecker, 0, 1000, TimeUnit.MILLISECONDS);
	}

	/* Delegate Implementations for MCHardware & MCController */
	public UserInfo[] getUsers(UserInfo user, IResource resource) throws NotRegistered, NotAuthorized {
		IOperation op1 = new DefaultOperation(IOperation.OP_ENUM_USERS);
		DefaultOperation opList = new DefaultOperation(IOperation.OP_LIST_USER);
		DefaultUser securityUser = new DefaultUser(user);

		if (!contains(user)) {
			getClientQueueListener().log(
					Level.INFO,
					"ClientQueue - Client " + user.getUserName()
							+ " doesn't exist here! - Going to send a NotRegistered Exception!");
			throw new NotRegistered();
		}

		if (!SecurityManagerFactory.authorize(resource, securityUser, op1)) {
			getClientQueueListener().log(
					Level.INFO,
					"ClientQueue - Client " + user.getUserName()
							+ " is not authorized to make this operation! - Going to send a NotAuthorized Exception!");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
		}

		Iterator<DataClientForQueue> iter = iterator();

		ArrayList<UserInfo> userInfosOut = new ArrayList<UserInfo>(queueOrg.size());
		while (iter.hasNext()) {
			DataClientForQueue otherUser = iter.next();
			opList.getProperties().put(IOperation.PROPKEY_USERID_OTHER, otherUser.getAsDefaultUser());
			if (SecurityManagerFactory.authorize(resource, securityUser, opList)) {
				userInfosOut.add(otherUser.getUserInfoCleaned());
			}
		}

		/*
		 * UserInfo[] retVal=new UserInfo[userInfosOut.size()];
		 * System.arraycopy(userInfosOut,0, retVal, 0, retVal.length); return
		 * retVal;
		 */
		UserInfo[] retVal = userInfosOut.toArray(new UserInfo[0]);
		return retVal;
	}

	// message queue dispatchers
	public void hardwareStateChange(HardwareState newState) {
		messageQueue.addEvent(new HardwareStateChangeEvent(newState));
	}

	public void hardwareLockable(HardwareLockEvent evt) {
		messageQueue.addEvent(evt);
	}

	public void hardwareChanged(HardwareChangeEvent evt) {
		messageQueue.addEvent(evt);
	}

	public void sendChatMessage(UserInfo user, String clientTo, String message, IResource resource)
			throws NotRegistered, NotAuthorized {
		synchronized (queueOrg) {
			if (!contains(user)) {
				getClientQueueListener().log(
						Level.INFO,
						"ClientQueue -  The user " + user.getUserName()
								+ " is not registered! - Going to send a NotRegistered Exception");
				throw new NotRegistered();
			}

			UserInfo otherUser = userNameToUserInfo(clientTo);

			if (otherUser == null || otherUser.getUserName().equals(ChatMessageEvent.EVERYONE_USER_ALIAS))
				otherUser = new UserInfo(ChatMessageEvent.EVERYONE_USER);

			DefaultOperation op = new DefaultOperation(IOperation.OP_SEND_MESSAGE);
			op.getProperties().put(IOperation.PROPKEY_USERID_OTHER, otherUser);

			if (!SecurityManagerFactory.authorize(resource, new DefaultUser(user), op)) {
				getClientQueueListener().log(
						Level.INFO,
						"ClientQueue - The user "
								+ user.getUserName()
								+ " is not authorized to send messages to the user "
								+ (clientTo == ChatMessageEvent.EVERYONE_USER ? ChatMessageEvent.EVERYONE_USER_ALIAS
										: clientTo) + " ! - Going to send a NotAuthorized Exception!");
				throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_OPERATION);
			}

			messageQueue.addEvent(new ChatMessageEvent(this, user, otherUser, message));
		}
	}

	// Helper function for chat messages...
	private UserInfo userNameToUserInfo(String userName) {
		Iterator<DataClientForQueue> iter = iterator();
		while (iter.hasNext()) {
			DataClientForQueue dcfq = iter.next();
			if (dcfq.getUserName().equals(userName)) {
				return dcfq.getUserInfo();
			}
		}

		return null;
	}

	public void shutdown() {
		if (shutDown)
			return;
		shutDown = true;
		getClientQueueListener().log(Level.INFO, "ClientQueue - shut down process initiated!");
		getClientQueueListener().log(Level.INFO, "ClientQueue - shutting down message queue!");
		messageQueue.shutdown();
		getClientQueueListener().log(Level.INFO, "ClientQueue - message queue is shut down!");

		getClientQueueListener().log(Level.INFO, "ClientQueue - shutting down clients connection checker!");
		clientsConnectionChecker.shutdown();
		getClientQueueListener().log(Level.INFO, "ClientQueue - clients connection checker is shut down!");

		getClientQueueListener().log(Level.INFO, "ClientQueue - shutting down clients!");
		Iterator<DataClientForQueue> iter = iterator();
		while (iter.hasNext()) {
			DataClientForQueue dcfq = iter.next();
			getClientQueueListener().log(Level.INFO, "ClientQueue - shutting down client " + dcfq.getUserName());
			dcfq.shutdown();
			getClientQueueListener().log(Level.INFO, "ClientQueue - client " + dcfq.getUserName() + " is shut down!");
		}

		getClientQueueListener().log(Level.INFO, "ClientQueue - shut down completed!");
	}

	public boolean add(DataClient dc, IResource resource) throws MaximumClientsReached, NotAuthorized {
		getClientQueueListener().log(Level.INFO, "ClientQueue - trying to register new client!");
		boolean retVal = false;

		synchronized (queueOrg) {
			getClientQueueListener().log(Level.INFO, "CHECKING IF THERE IS SPACE AVAILABLE...");
			getClientQueueListener().log(Level.INFO, "Number of registered users = " + queueOrg.size());
			if (queueOrg.size() >= getMaximumClients()) {
				getClientQueueListener()
						.log(Level.INFO,
								"ClientQueue - Maximum capacity reached... Going to deny client's request for registration! Maybe try later?");
				throw new MaximumClientsReached(
						MaximumClientsReachedConstants.MAXIMUM_CLIENTS_REACHED_IN_HARDWARE_QUEUE, getMaximumClients());
			} else {
				getClientQueueListener().log(Level.INFO, queueOrg.size() + "<" + maximumClients + " ok to add user!");
			}

		}

		DataClientForQueue dcfq = new DataClientForQueue(resource, dc, dataClientForQueueAdapter);

		if (!SecurityManagerFactory.authenticate(resource, dcfq.getAsDefaultUser()))
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_PASSWORD_NOT_MATCH);

		getClientQueueListener().log(Level.INFO,
				"ClientQueue : checking to see if DataClient " + dcfq.getUserName() + " is allready registered!");

		if (contains(dcfq.getUserInfo())) {
			DataClientForQueue otherDcfq = getWrapperForUser(dcfq.getUserInfo());
			if (otherDcfq.isShuttingDown() || !otherDcfq.isConnected()) {
				if(!otherDcfq.isShuttingDown())
				{
					otherDcfq.shutdown();
				}
				synchronized (this) {
					try {
						while (otherDcfq.isShuttingDown()) {
							// Waiting for other to finish...please be
							// patient...at least 20 seconds of the messageQueue
							// shutdown...
							wait(1000);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (!otherDcfq.getDataClient().isSameDelegate(dcfq.getDataClient())) {
				getClientQueueListener().log(
						Level.INFO,
						"ClientQueue : There is allready a user with that name : " + otherDcfq.getUserName()
								+ " - Sending a NotAuthorized Exception!");
				throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_REPEATED);
			} else {
				// user is allready registered... just ignore it...
				return true;
			}
		}

		synchronized (queueOrg) {
			getClientQueueListener().log(Level.INFO,
					"ClientQueue - Going to register client " + dcfq.getUserName() + " !");
			retVal = queueOrg.add(dcfq);
			getClientQueueListener().log(Level.INFO,
					"ClientQueue - registered client " + dcfq.getUserName() + (retVal ? "successfully!" : "failed!"));
		}

		return retVal;
	}

	
	public DataClientForQueue first() {
		synchronized (queueOrg) {
			try {
				return queueOrg.get(0);
			} catch (Exception e) {
				getClientQueueListener().log(Level.INFO, "ClientQueue - No client at first position!");
				return null;
			}
		}
	}

	public boolean isEmpty() {
		synchronized (queueOrg) {
			return queueOrg.isEmpty();
		}
	}

	public Iterator<DataClientForQueue> iterator() {
		synchronized (queueOrg) {
			return Collections.unmodifiableList(new LinkedList<DataClientForQueue>(queueOrg)).iterator();
		}
	}

	public boolean remove(DataClientForQueue dcfq) {
		boolean returnVal = false;

		synchronized (queueOrg) {
			try {
				if (queueOrg.contains(dcfq)) {
					returnVal = queueOrg.remove(dcfq);

					if (!cyclingQueue) {
						getClientQueueListener().log(
								Level.INFO,
								"ClientQueue - " + (returnVal ? "Removed" : "Failed to remove") + " client "
										+ dcfq.getUserName() + "!");
						if (returnVal) {
							getClientQueueListener().log(Level.INFO,
									"ClientQueue - informing client is gone " + dcfq.getUserName() + "!");
							clientQueueListener.dataClientForQueueIsGone(dcfq);
						}
					}
				} else {
					getClientQueueListener().log(Level.INFO,
							"ClientQueue - client " + dcfq.getUserName() + " isn't registered here!");
				}

			} catch (Exception e) {
				getClientQueueListener().logThrowable(
						"ClientQueue - Error " + (cyclingQueue ? "cycling" : "removing") + " client "
								+ dcfq.getUserName() + "!", e);
			}
		}

		return returnVal;
	}

	public void moveFirstToLast() {
		synchronized (queueOrg) {
			cyclingQueue = true;
			DataClientForQueue first = first();

			if (remove(first))
				queueOrg.add(first);
			else
				getClientQueueListener().log(Level.INFO, "ClientQueue - Unable to cycle users in Queue!");

			cyclingQueue = false;
		}
	}

	public boolean contains(UserInfo user) {
		Iterator<DataClientForQueue> iter = iterator();
		getClientQueueListener().log(Level.FINEST, "ClientQueue - users size is " + Collections.unmodifiableList(queueOrg).size());
		getClientQueueListener().log(Level.FINEST, "ClientQueue - username passed in is " + (user == null ? "(user is null)" : user.getUserName()));
		while (iter.hasNext()) {
			DataClientForQueue dcfq = iter.next();
			getClientQueueListener().log(Level.FINEST, "ClientQueue - dcfq.getUserName() " + dcfq.getUserName());
			if (dcfq.getUserName().equals(user.getUserName())) {
				return true;
			}

		}
		return false;
	}

	private DataClientForQueue getWrapperForUser(UserInfo user) {
		Iterator<DataClientForQueue> iter = iterator();
		while (iter.hasNext()) {
			DataClientForQueue dcfq = (DataClientForQueue) iter.next();
			if (dcfq.getUserName().equals(user.getUserName())) {
				return dcfq;
			}
		}
		return null;
	}

	public String toString() {
		StringBuffer returnClients = new StringBuffer("");

		returnClients.append("******************************************" + "\r\n");
		returnClients.append("Client Queue Contents: \r\n");
		Iterator<DataClientForQueue> iter = iterator();
		while (iter.hasNext()) {
			returnClients.append(iter.next() + "\r\n");
		}
		returnClients.append("******************************************" + "\r\n");
		return returnClients.toString();
	}

	/**
	 * Getter for property maximumClients.
	 * 
	 * @return Value of property maximumClients.
	 * 
	 */
	public int getMaximumClients() {
		return maximumClients;
	}

	/**
	 * Setter for property maximumClients.
	 * 
	 * @param maximumClients New value of property maximumClients.
	 * 
	 */
	public void setMaximumClients(int maximumClients) {
		this.maximumClients = maximumClients;
	}

	/**
	 * Getter for property clientQueueListener.
	 * 
	 * @return Value of property clientQueueListener.
	 * 
	 */
	public com.linkare.rec.impl.multicast.IClientQueueListener getClientQueueListener() {
		return clientQueueListener;
	}

	/**
	 * Setter for property clientQueueListener.
	 * 
	 * @param clientQueueListener New value of property clientQueueListener.
	 * 
	 */
	public void setClientQueueListener(com.linkare.rec.impl.multicast.IClientQueueListener clientQueueListener) {
		this.clientQueueListener = clientQueueListener;
	}

	/* Inner Class - Queue Dispatcher */
	private class ClientQueueDispatcher implements EventQueueDispatcher {
		private HardwareState cachedHardwareState = null;

		public void dispatchEvent(Object o) {
			if (o instanceof HardwareStateChangeEvent) {
				if (cachedHardwareState == null
						|| !cachedHardwareState.equals(((HardwareStateChangeEvent) o).getNewState())) {
					cachedHardwareState = ((HardwareStateChangeEvent) o).getNewState();

					getClientQueueListener().log(
							Level.INFO,
							"ClientQueue - dispatching Hardware State change event. New State is: "
									+ ((HardwareStateChangeEvent) o).getNewState());

					Iterator iter = iterator();
					while (iter.hasNext()) {
						try {
							((DataClientForQueue) iter.next()).hardwareStateChange((HardwareStateChangeEvent) o);
						} catch (Exception e) {
							getClientQueueListener().logThrowable(
									"ClientQueue - Error dispatching Hardware State change events to clients!", e);
						}
					}

					return;
				}
			} else if (o instanceof ChatMessageEvent) {
				getClientQueueListener().log(Level.INFO, "ClientQueue - dispatching chat message event.");

				Iterator iter = iterator();
				while (iter.hasNext()) {
					try {
						((DataClientForQueue) iter.next()).receiveMessage((ChatMessageEvent) o);
					} catch (Exception e) {
						getClientQueueListener().logThrowable("ClientQueue - Error dispatching chat message event!", e);
					}
				}
			} else if (o instanceof HardwareLockEvent) {
				getClientQueueListener().log(Level.INFO,
						"The current free memory is: " + Runtime.getRuntime().freeMemory());

				getClientQueueListener().log(
						Level.INFO,
						"ClientQueue - dispatching Hardware Lock event to client "
								+ ((HardwareLockEvent) o).getLockerClient().getUserName());

				try {
					((HardwareLockEvent) o).getLockerClient().hardwareLockable((HardwareLockEvent) o);

				} catch (Exception e) {
					getClientQueueListener().logThrowable(
							"ClientQueue - Error dispatching Hardware Lock event to client "
									+ ((HardwareLockEvent) o).getLockerClient().getUserName(), e);
				}
			} else if (o instanceof HardwareChangeEvent) {
				getClientQueueListener().log(Level.INFO, "ClientQueue - dispatching Hardware Change event!");

				Iterator<DataClientForQueue> iter = iterator();
				while (iter.hasNext()) {
					try {
						iter.next().hardwareChange();
					} catch (Exception e) {
						getClientQueueListener().logThrowable("ClientQueue - Error dispatching Hardware Change event!",
								e);
					}
				}
			}

		}

		public int getPriority() {
			return Thread.NORM_PRIORITY + 2;
		}

	}

	/* End Inner Class - Queue Dispatcher */

	/* Inner Class - Clients Connection Checker */
	private class ClientsConnectionCheck extends ScheduledWorkUnit {
		private boolean shutdown = false;

		public void run() {
			Iterator<DataClientForQueue> iterClients = iterator();
			while (iterClients.hasNext()) {
				try {
					DataClientForQueue dcfq = iterClients.next();
					if (!dcfq.isConnected()) {
						dcfq.shutdown();
					}
				} catch (Exception e) {
					getClientQueueListener().logThrowable("ClientQueue - Error cheking client connection status!", e);
				}
			}
		}
	}

	/* End Inner Class - Clients Connection Checker */

	/* Inner Class - Clients callbacks */
	private class DataClientForQueueAdapter implements IDataClientForQueueListener {
		public void dataClientForQueueIsGone(DataClientForQueue dcfq) {
			remove(dcfq);
		}

		/* Proxy Logging methods for DataClientForQueue */
		public void log(Level debugLevel, String message) {
			getClientQueueListener().log(debugLevel, "ClientQueue - " + message);
		}

		public void logThrowable(String message, Throwable t) {
			getClientQueueListener().logThrowable("ClientQueue - " + message, t);
		}

	}
	/* End Inner Class - Clients callbacks */
}
