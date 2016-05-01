/*
 * LabClientBean.java
 *
 * Created on 13 de Maio de 2003, 18:03
 */

package com.linkare.rec.impl.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.DataClientPOATie;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerHelper;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.client.apparatus.Apparatus;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListSource;
import com.linkare.rec.impl.client.chat.ChatConnectionEvent;
import com.linkare.rec.impl.client.chat.IChatMessageListener;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener;
import com.linkare.rec.impl.client.experiment.ExpUsersListEvent;
import com.linkare.rec.impl.client.experiment.ExpUsersListSource;
import com.linkare.rec.impl.client.lab.LabConnector;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.client.lab.LabConnectorListener;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.MultiCastControllerWrapper;
import com.linkare.rec.impl.wrappers.MultiCastHardwareWrapper;

/**
 * This class implements a client for a laboratory This is not a user interface
 * component but rather a wrapper to abstract remote calls to a lab.
 * 
 * 
 * @author José Pedro Pereira - Linkare T.I.
 * 
 */
public class LabClientBean implements DataClientOperations, LabConnector, ApparatusListSource, IChatServer,
		ExpUsersListSource {

	/*
	 * Staticaly initialize the logger for the Lab client
	 */
	private static final Logger LOGGER = Logger.getLogger(LabClientBean.class.getName());

	/*
	 * The Executor to use when doing parallel tasks
	 */
	// private transient Executor asyncExecutor = null;

	/*
	 * The multicast controller client to use
	 */
	private transient MultiCastControllerWrapper mcw = null;

	private transient DataClient _this = null;

	private transient ObjectID oid = null;

	private boolean connectedBefore = false;

	private Collection<Apparatus> apparatusList = new ArrayList<Apparatus>();

	private UserInfo userInfo = null;

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	// private helper methods
	private DataClient _this() {
		if (_this != null) {
			return _this;
		}

		try {
			oid = new ObjectID();
			DataClientPOATie servant = (DataClientPOATie)ORBBean.getORBBean().registerAutoIdRootPOAServant(DataClient.class, this, oid);
			_this = servant._this();
			return _this;

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't register this DataClient with ORB", e);
			return null;
		}

	}

	/** Creates a new instance of LabClientBean */
	public LabClientBean() {

	}

	/** ** DataClient Operations *** */

	@Override
	public UserInfo getUserInfo() {
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		return userInfo;
	}

	@Override
	public void setUserInfo(final UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public void hardwareChange() {
		LOGGER.log(Level.INFO, "Hardware Change information received...");

		(new Thread() {
			@Override
			public void run() {
				setName(getName() + " - LabClientBean - refreshHardwares");
				refreshHardwares();
			}
		}).start();

	}

	private void refreshHardwares() {
		try {
			final MultiCastHardware[] hardwares = mcw.enumerateHardware(getUserInfo());

			if (hardwares == null) {
				LOGGER.log(Level.WARNING,
						"There are no hardwares available at the Laboratory that this user may access");
				return;
			}
			LOGGER.log(Level.FINE, "There are " + hardwares.length
					+ " hardwares available at the Laboratory that this user may access");

			final ArrayList<Apparatus> apparatusListTemp = new ArrayList<Apparatus>(hardwares == null ? 0
					: hardwares.length);

			if (hardwares != null) {
				for (int i = 0; i < hardwares.length; i++) {
					final MultiCastHardwareWrapper mchw = new MultiCastHardwareWrapper(hardwares[i]);

					final HardwareInfo hardwareInfo = mchw.getHardwareInfo(getUserInfo());

					if (hardwareInfo == null) {
						LOGGER.log(Level.INFO, "Can't get HardwareInfo for hardware index " + i + " of max index "
								+ hardwares.length);
						continue;
					}

					final Apparatus app = new Apparatus(mchw, hardwareInfo);
					apparatusListTemp.add(app);
				}
			}
			final Apparatus[] newApparatusList = new Apparatus[apparatusListTemp.size()];

			for (int i = 0; i < apparatusListTemp.size(); i++) {
				newApparatusList[i] = apparatusListTemp.get(i);
			}

			LOGGER.log(Level.INFO,
					"Hardwares received from Multicast. New Aparatus list: " + Arrays.deepToString(newApparatusList));

			apparatusList = apparatusListTemp;
			fireApparatusListSourceListenerApparatusChanged(new ApparatusListChangeEvent(this, newApparatusList));

		} catch (final NotRegistered e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_REGISTERED);
		} catch (final NotAuthorized e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
		}
	}

	@Override
	public void hardwareLockable(final long milliSecondsToLockSuccess) {
		// silent noop
	}

	@Override
	public void hardwareStateChange(final com.linkare.rec.acquisition.HardwareState newState) {
		// silent noop
	}

	@Override
	public void receiveMessage(final String clientFrom, final String clientTo, final String message) {
		LOGGER.log(Level.INFO, "Received a remote message: " + message + ", coming from " + clientFrom + " to->"
				+ clientTo);
		fireIChatMessageListenerNewChatMessage(new ChatMessageEvent(this, new UserInfo(clientFrom), new UserInfo(
				clientTo == ChatMessageEvent.EVERYONE_USER ? ChatMessageEvent.EVERYONE_USER_ALIAS : clientTo), message));
	}

	/** ** End DataClient Operations *** */

	/** ** LabConnector *** */
	@Override
	public void disconnect() {
		if (connectedBefore) {
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_DISCONNECTING);

			ORBBean.getORBBean().deactivateServant(oid.getOid());
			_this = null;

			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_DISCONNECTED);

			apparatusList = new ArrayList<Apparatus>();
			connectedBefore = false;
		}
	}

	private Timer timerCheckConnectionMultiCastController = null;

	private Thread connectThread = null;

	private boolean connecting = false;

	private boolean stop = false;

	private final Object obj = new Object();

	@Override
	public void connect(final String MCobjAddress) {
		final String adress = MCobjAddress;
		stop = true;
		synchronized (obj) {
			try {
				while (connecting) {
					wait();
				}
			} catch (final InterruptedException ignored) {
			}
		}
		disconnect();

		stop = false;

		try {
			connectThread = new Thread() {
				@Override
				public void run() {
					setName(getName() + " - LabClientBean - doConnect");
					doConnect(adress);
				}
			};
			connectThread.start();
			// connectThread.join();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	private void doConnect(final String mccontrollerCORBALocAddress) {

		stop = false;
		connecting = true;

		try {
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_CONNECTING);
			final org.omg.CORBA.Object o = ORBBean.getORBBean().getRemoteCORBALocObject(mccontrollerCORBALocAddress);

			if (stop) {
				synchronized (obj) {
					connecting = false;
					obj.notifyAll();
				}
				return;
			}

			try {
				final MultiCastController mcc = MultiCastControllerHelper.narrow(o);
				if (mcc != null) {
					LOGGER.info("mcw is being build with " + mcc);
					mcw = new MultiCastControllerWrapper(mcc);
					if (mcw.isConnected()) {
						try {
							if (stop) {
								synchronized (obj) {
									connecting = false;
									obj.notifyAll();
								}
								return;
							}
							mcw.registerDataClient(_this());
							LOGGER.info("Registered data client on mcw");
							fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_CONNECTED);
							fireIChatMessageListenerConnectionChanged(true);

							refreshHardwares();

							if (timerCheckConnectionMultiCastController != null) {
								timerCheckConnectionMultiCastController.cancel();
								timerCheckConnectionMultiCastController = null;
							}

							timerCheckConnectionMultiCastController = new Timer(true);

							timerCheckConnectionMultiCastController.schedule(new TimerTask() {
								@Override
								public void run() {
									if (!mcw.isConnected()) {
										fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_DISCONNECTED);
										cancel();
									}
								}
							}, 5000, 5000);

							connectedBefore = true;
						} catch (final MaximumClientsReached e) {
							LOGGER.log(Level.WARNING, "Error registring data client. Server maximum capacity reached.",
									e);
							fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_MAX_USERS);
							fireIChatMessageListenerConnectionChanged(false);
						} catch (final NotAuthorized e) {
							LOGGER.log(Level.WARNING, "Error registring data client. User not authorized.", e);
							fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
							fireIChatMessageListenerConnectionChanged(false);
						}

					} else {
						fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_DISCONNECTED);
						fireIChatMessageListenerConnectionChanged(false);
					}
				} else {
					fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_UNREACHABLE);
					fireIChatMessageListenerConnectionChanged(false);
				}

			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_UNREACHABLE);
			}

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_UNREACHABLE);
			fireIChatMessageListenerConnectionChanged(false);
		}

		connecting = false;
		synchronized (obj) {
			obj.notifyAll();
		}
	}

	/**
	 * Registers LabConnectorListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addLabConnectorListener(final LabConnectorListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(LabConnectorListener.class, listener);
	}

	/**
	 * Removes LabConnectorListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeLabConnectorListener(final LabConnectorListener listener) {
		listenerList.remove(LabConnectorListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireLabConnectorListenerLabStatusChanged(final int statusCode) {
		if (statusCode == LabConnectorEvent.STATUS_DISCONNECTED) {
			if (usersListRefreshThread != null) {
				usersListRefreshThread.stopNow();
				usersListRefreshThread = null;
			}
			fireIChatMessageListenerConnectionChanged(false);
			fireApparatusListSourceListenerApparatusChanged(null);
		} else if (statusCode == LabConnectorEvent.STATUS_CONNECTED) {
			usersListRefreshThread = new UsersListRefresher();
			usersListRefreshThread.start();
		}

		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == LabConnectorListener.class) {
				try {
					((LabConnectorListener) listeners[i + 1]).labStatusChanged(new LabConnectorEvent(this, statusCode));
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to send lab status change information to listener", e);
				}
			}
		}
	}

	/** ** End LabConnector *** */

	// private void reconnect(String MCobjAddress) {
	// disconnect();
	// connect(MCobjAddress);
	// }

	/** ** ApparatusListSource *** */

	/**
	 * Registers ApparatusListSourceListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addApparatusListSourceListener(
			final com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener.class, listener);
	}

	/**
	 * Removes ApparatusListSourceListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeApparatusListSourceListener(
			final com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener listener) {
		listenerList.remove(com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireApparatusListSourceListenerApparatusChanged(final ApparatusListChangeEvent newApparatusList) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener.class) {
				try {
					((com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener) listeners[i + 1])
							.apparatusListChanged(newApparatusList);
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to inform listener of apparatus list changes!", e);
				}
			}
		}
	}

	@Override
	public void sendMessage(final ChatMessageEvent evt) {
		if (mcw != null) {
			try {
				LOGGER.fine("MCW is " + mcw);
				LOGGER.fine("getUserInfo() " + getUserInfo());
				LOGGER.fine("evt.getUserTo() " + evt.getUserTo());
				if (evt.getUserTo() != null) {
					LOGGER.fine("evt.getUserTo().getUserName()" + evt.getUserTo().getUserName());
				}
				LOGGER.fine("evt.getMessage() " + evt.getMessage());
				mcw.sendMessage(getUserInfo(), evt.getUserTo().getUserName(), evt.getMessage());
			} catch (final NotRegistered e) {
				LOGGER.log(Level.WARNING, e.getMessage(), e);
				fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_REGISTERED);
			} catch (final NotAuthorized e) {
				LOGGER.log(Level.WARNING, e.getMessage(), e);
				fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
			}
		}
	}

	private final Vector<UserInfo> usersList = new Vector<UserInfo>();

	/** Holds value of property usersListRefreshPeriod. */
	private long usersListRefreshPeriod = 20000;

	private void refreshUsersList() {
		UserInfo[] users = null;

		try {
			users = mcw.getClientList(getUserInfo());
		} catch (final NotRegistered e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_REGISTERED);
			fireIChatMessageListenerConnectionChanged(false);
		} catch (final NotAuthorized e) {
			LOGGER.log(Level.WARNING, e.getMessage(), e);
			fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
		}

		if (users == null) {
			return;
		}

		synchronized (usersList) {
			usersList.clear();
			for (final UserInfo user : users) {
				usersList.add(user);
			}
			fireExpUsersListChangeListenerUsersListChanged(getUsers());
		}
	}

	@Override
	public UserInfo[] getUsers() {
		UserInfo[] usersObj = null;

		synchronized (usersList) {
			usersObj = usersList.toArray(new UserInfo[0]);
		}

		UserInfo[] users = null;

		if (usersObj != null) {
			users = new UserInfo[usersObj.length + 1];
		} else {
			users = new UserInfo[1];
		}

		users[0] = new UserInfo(ChatMessageEvent.EVERYONE_USER_ALIAS);

		if (usersObj != null) {
			System.arraycopy(usersObj, 0, users, 1, usersObj.length);
		}
		return users;
	}

	@Override
	public String getRoomName() {
		return "Laboratory Main Hall";
	}

	/**
	 * Registers IChatMessageListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addChatMessageListener(final com.linkare.rec.impl.client.chat.IChatMessageListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}

		listenerList.add(com.linkare.rec.impl.client.chat.IChatMessageListener.class, listener);
	}

	/**
	 * Removes IChatMessageListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeChatMessageListener(
			final com.linkare.rec.impl.client.chat.IChatMessageListener listener) {
		listenerList.remove(IChatMessageListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireIChatMessageListenerConnectionChanged(final boolean isConnected) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChatMessageListener.class) {
				try {
					((IChatMessageListener) listeners[i + 1]).connectionChanged(new ChatConnectionEvent(this,
							isConnected));
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to deliver connection change event to listener", e);
				}

			}
		}
		if (isConnected) {
			refreshUsersList();
		}

	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireExpUsersListChangeListenerUsersListChanged(final UserInfo[] event) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			try {
				if (listeners[i] == ExpUsersListChangeListener.class) {
					((ExpUsersListChangeListener) listeners[i + 1])
							.usersListChanged(new ExpUsersListEvent(this, event));
				} else if (listeners[i] == IChatMessageListener.class) {
					((IChatMessageListener) listeners[i + 1]).userListChanged(event);
				}
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to deliver users list change event to listener", e);
			}

		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireIChatMessageListenerNewChatMessage(final ChatMessageEvent evt) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChatMessageListener.class) {
				try {
					((IChatMessageListener) listeners[i + 1]).newChatMessage(evt);
				} catch (final Exception e) {
					LOGGER.log(Level.SEVERE, "Unable to deliver chat message to listener", e);
				}
			}
		}
	}

	/**
	 * Getter for property usersListRefreshPeriod.
	 * 
	 * @return Value of property usersListRefreshPeriod.
	 */
	public long getUsersListRefreshPeriod() {
		return usersListRefreshPeriod;
	}

	/**
	 * Setter for property usersListRefreshPeriod.
	 * 
	 * @param usersListRefreshPeriod New value of property
	 *            usersListRefreshPeriod.
	 */
	public void setUsersListRefreshPeriod(final long usersListRefreshPeriod) {
		this.usersListRefreshPeriod = usersListRefreshPeriod;
	}

	@Override
	public void startAutoRefresh(final long delayMillis) {
		usersListRefreshPeriod = delayMillis;
		if (usersListRefreshThread != null) {
			usersListRefreshThread.stopNow();
			while (usersListRefreshThread.isAlive()) {
				Thread.yield();
			}
		}
		usersListRefreshThread = new UsersListRefresher();
		usersListRefreshThread.start();
	}

	@Override
	public void removeExpUsersListChangeListener(final ExpUsersListChangeListener listener) {
		listenerList.remove(ExpUsersListChangeListener.class, listener);
	}

	@Override
	public void addExpUsersListChangeListener(final ExpUsersListChangeListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}

		listenerList.add(com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener.class, listener);
	}

	@Override
	public void stopAutoRefresh() {
		usersListRefreshThread.stopNow();
	}

	public Apparatus getApparatusByID(final String uniqueID) {
		for (final Apparatus app : apparatusList) {
			if (app.getHardwareInfo().getHardwareUniqueID().equals(uniqueID)) {
				return app;
			}
		}
		return null;
	}

	private UsersListRefresher usersListRefreshThread = null;// new

	// UsersListRefresher();

	public class UsersListRefresher extends Thread {
		private volatile boolean stop = false;

		/**
		 * Creates the <code>LabClientBean.UsersListRefresher</code>.
		 */
		public UsersListRefresher() {
			super();
			setName(getName() + " - LabClientBean - UsersListRefresher");
		}

		public void stopNow() {
			stop = true;
		}

		@Override
		public void run() {
			while (!stop) {
				while ((mcw == null || !mcw.isConnected()) && !stop) {
					try {
						Thread.sleep(usersListRefreshPeriod);
					} catch (final Exception e) {
						LOGGER.log(Level.WARNING, e.getMessage(), e);
						return;
					}
				}

				while ((mcw != null && mcw.isConnected()) && !stop) {
					try {
						// refresh ASAP when it is possible and then wait for
						// next refresh

						refreshUsersList();
						// refreshHardwares();
						Thread.sleep(usersListRefreshPeriod);
					} catch (final Exception e) {
						LOGGER.log(Level.WARNING, e.getMessage(), e);
						return;
					}
				}
			}
		}
	}
}
