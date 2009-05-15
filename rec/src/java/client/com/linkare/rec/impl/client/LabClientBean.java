/*
 * LabClientBean.java
 *
 * Created on 13 de Maio de 2003, 18:03
 */

package com.linkare.rec.impl.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientHelper;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerHelper;
import com.linkare.rec.acquisition.MultiCastHardware;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.client.apparatus.Apparatus;
import com.linkare.rec.impl.client.apparatus.ApparatusListChangeEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusListSource;
import com.linkare.rec.impl.client.chat.ChatConnectionEvent;
import com.linkare.rec.impl.client.chat.ChatRoomEvent;
import com.linkare.rec.impl.client.chat.IChatMessageListener;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener;
import com.linkare.rec.impl.client.experiment.ExpUsersListEvent;
import com.linkare.rec.impl.client.experiment.ExpUsersListSource;
import com.linkare.rec.impl.client.lab.LabConnector;
import com.linkare.rec.impl.client.lab.LabConnectorEvent;
import com.linkare.rec.impl.client.lab.LabConnectorListener;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.logging.LoggerUtil;
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
         * Define the Logger name for the lab
         */
    private static String LAB_CLIENT_LOGGER = "ReC.LaboratoryClientBean";
    /*
         * Staticaly initialize the logger for the Lab client
         */
    static {
	Logger l = LogManager.getLogManager().getLogger(LAB_CLIENT_LOGGER);
	if (l == null) {
	    LogManager.getLogManager().addLogger(Logger.getLogger(LAB_CLIENT_LOGGER));
	}
    }

    /*
         * The Executor to use when doing parallel tasks
         */
    private transient Executor asyncExecutor = null;

    /*
         * The multicast controller client to use
         */
    private transient MultiCastControllerWrapper mcw = null;

    private transient DataClient _this = null;

    private transient ObjectID oid = null;

    public boolean connectedBefore = false;

    private Collection<Apparatus> apparatusList = new ArrayList<Apparatus>();

    private UserInfo userInfo = null;

    /** Utility field used by event firing mechanism. */
    private javax.swing.event.EventListenerList listenerList = null;

    // private helper methods
    private DataClient _this() {
	if (_this != null)
	    return _this;

	try {
	    /*
                 * org.omg.PortableServer.Servant servant =
                 * ORBBean.getORBBean().registerAutoIdRootPOAServant(DataClient.class,this,(oid=new
                 * ObjectID())); if(servant != null) System.out.println("Servant
                 * is ok!");
                 * 
                 * org.omg.CORBA.Object object =
                 * ORBBean.getORBBean().getAutoIdRootPOA().servant_to_reference(servant);
                 * 
                 * if(object != null) System.out.println("Object is ok!");
                 * 
                 * DataClient client = DataClientHelper.narrow(object);
                 * 
                 * if(client != null) System.out.println("Client is ok!");
                 * 
                 * return null;
                 */
	    return (_this = DataClientHelper.narrow(ORBBean.getORBBean().getAutoIdRootPOA().servant_to_reference(
		    ORBBean.getORBBean().registerAutoIdRootPOAServant(DataClient.class, this, (oid = new ObjectID())))));

	} catch (Exception e) {
	    LoggerUtil.logThrowable("Couldn't register this DataClient with ORB", e, Logger.getLogger(LAB_CLIENT_LOGGER));
	    return null;
	}

    }

    /** Creates a new instance of LabClientBean */
    public LabClientBean() {

    }

    /** ** DataClient Operations *** */

    public UserInfo getUserInfo() {
	// TODO check with JP
	// userInfo = _this().getUserInfo();
	if (userInfo == null)
	    userInfo = new UserInfo();
	return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
	this.userInfo = userInfo;
    }

    public void hardwareChange() {
	Logger.getLogger(LAB_CLIENT_LOGGER).log(Level.INFO, "Hardware Change information received...");

	(new Thread() {
	    public void run() {
		refreshHardwares();
	    }
	}).start();

    }

    private void refreshHardwares() {
	try {
	    MultiCastHardware[] hardwares = mcw.enumerateHardware(getUserInfo());

	    if (hardwares == null)
		return;

	    ArrayList<Apparatus> apparatusListTemp = new ArrayList<Apparatus>(hardwares == null ? 0 : hardwares.length);

	    if (hardwares != null) {
		for (int i = 0; i < hardwares.length; i++) {
		    MultiCastHardwareWrapper mchw = new MultiCastHardwareWrapper(hardwares[i]);

		    if (mchw.getHardwareInfo(getUserInfo()) == null) {
			continue;
		    }

		    Apparatus app = new Apparatus(mchw, mchw.getHardwareInfo(getUserInfo()));

		    apparatusListTemp.add(app);
		}
	    }
	    Apparatus[] newApparatusList = new Apparatus[apparatusListTemp.size()];

	    ((ArrayList) apparatusList).trimToSize();
	    for (int i = 0; i < apparatusListTemp.size(); i++) {
		newApparatusList[i] = (Apparatus) apparatusListTemp.get(i);
	    }

	    apparatusList = apparatusListTemp;
	    fireApparatusListSourceListenerApparatusChanged(new ApparatusListChangeEvent(this, newApparatusList));

	} catch (NotRegistered e) {
	    e.printStackTrace();
	    LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
	    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_REGISTERED);
	} catch (NotAuthorized na) {
	    na.printStackTrace();
	    LoggerUtil.logThrowable(null, na, Logger.getLogger(LAB_CLIENT_LOGGER));
	    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
	}
    }

    public void hardwareLockable(long millisecs_to_lock_success) {
	// silent noop
    }

    public void hardwareStateChange(com.linkare.rec.acquisition.HardwareState newState) {
	// silent noop
    }

    public void receiveMessage(String clientFrom, String clientTo, String message) {
	fireIChatMessageListenerNewChatMessage(new ChatMessageEvent(this, new UserInfo(clientFrom), new UserInfo(
		clientTo == ChatMessageEvent.EVERYONE_USER ? ChatMessageEvent.EVERYONE_USER_ALIAS : clientTo), message));
    }

    /** ** End DataClient Operations *** */

    /** ** LabConnector *** */
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

    private Object obj = new Object();

    public void connect(String MCobjAddress) {
	final String adress = MCobjAddress;
	stop = true;
	synchronized (obj) {
	    try {
		while (connecting) {
		    wait();
		}
	    } catch (InterruptedException ignored) {
	    }
	}
	disconnect();

	stop = false;

	try {
	    connectThread = new Thread() {
		public void run() {
		    doConnect(adress);
		}
	    };
	    connectThread.start();
	    // connectThread.join();
	} catch (Exception e) {
	    LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
	}
    }

    private void doConnect(String MCobjAddress) {
	/*
         * String MCobjAddress="com/linkare/rec/MultiCastController";
         * if(System.getProperty("Rec.MultiCastController.Address")!=null)
         * MCobjAddress=System.getProperty("Rec.MultiCastController.Address");
         */
	stop = false;
	connecting = true;

	try {
	    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_CONNECTING);
	    org.omg.CORBA.Object o = ORBBean.getORBBean().getRemoteCORBALocObject(MCobjAddress);

	    if (stop) {
		synchronized (obj) {
		    connecting = false;
		    obj.notifyAll();
		}
		return;
	    }

	    try {
		MultiCastController mcc = MultiCastControllerHelper.narrow(o);
		if (mcc != null) {
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
			    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_CONNECTED);
			    fireIChatMessageListenerConnectionChanged(true);

			    refreshHardwares();

			    if (timerCheckConnectionMultiCastController != null) {
				timerCheckConnectionMultiCastController.cancel();
				timerCheckConnectionMultiCastController = null;
			    }

			    timerCheckConnectionMultiCastController = new Timer(true);

			    timerCheckConnectionMultiCastController.schedule(new TimerTask() {
				public void run() {
				    if (!mcw.isConnected()) {
					fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_DISCONNECTED);
					cancel();
				    }
				}
			    }, 5000, 5000);

			    connectedBefore = true;
			} catch (MaximumClientsReached e) {
			    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_MAX_USERS);
			    fireIChatMessageListenerConnectionChanged(false);
			} catch (NotAuthorized e) {
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

	    } catch (Exception e) {
		e.printStackTrace();
		fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_UNREACHABLE);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
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
         * @param listener
         *                The listener to register.
         */
    public synchronized void addLabConnectorListener(LabConnectorListener listener) {
	if (listenerList == null) {
	    listenerList = new javax.swing.event.EventListenerList();
	}
	listenerList.add(LabConnectorListener.class, listener);
    }

    /**
         * Removes LabConnectorListener from the list of listeners.
         * 
         * @param listener
         *                The listener to remove.
         */
    public synchronized void removeLabConnectorListener(LabConnectorListener listener) {
	listenerList.remove(LabConnectorListener.class, listener);
    }

    /**
         * Notifies all registered listeners about the event.
         * 
         * @param event
         *                The event to be fired
         */
    private void fireLabConnectorListenerLabStatusChanged(int statusCode) {
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

	if (listenerList == null)
	    return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == LabConnectorListener.class) {
		((LabConnectorListener) listeners[i + 1]).labStatusChanged(new LabConnectorEvent(this, statusCode));
	    }
	}
    }

    /** ** End LabConnector *** */

    private void reconnect(String MCobjAddress) {
	disconnect();
	connect(MCobjAddress);
    }

    /** ** ApparatusListSource *** */

    /**
         * Registers ApparatusListSourceListener to receive events.
         * 
         * @param listener
         *                The listener to register.
         */
    public synchronized void addApparatusListSourceListener(
	    com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener listener) {
	if (listenerList == null) {
	    listenerList = new javax.swing.event.EventListenerList();
	}
	listenerList.add(com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener.class, listener);
    }

    /**
         * Removes ApparatusListSourceListener from the list of listeners.
         * 
         * @param listener
         *                The listener to remove.
         */
    public synchronized void removeApparatusListSourceListener(
	    com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener listener) {
	listenerList.remove(com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener.class, listener);
    }

    /**
         * Notifies all registered listeners about the event.
         * 
         * @param event
         *                The event to be fired
         */
    private void fireApparatusListSourceListenerApparatusChanged(ApparatusListChangeEvent newApparatusList) {
	if (listenerList == null)
	    return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener.class) {
		((com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener) listeners[i + 1])
			.apparatusListChanged(newApparatusList);
	    }
	}
    }

    public void sendMessage(ChatMessageEvent evt) {
	if (mcw != null) {
	    try {
		mcw.sendMessage(getUserInfo(), evt.getUserTo().getUserName(), evt.getMessage());
	    } catch (NotRegistered e) {
		fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_REGISTERED);
		LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
	    } catch (NotAuthorized na) {
		LoggerUtil.logThrowable(null, na, Logger.getLogger(LAB_CLIENT_LOGGER));
		fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
	    }
	}
    }

    private Vector<UserInfo> usersList = new Vector<UserInfo>();

    /** Holds value of property usersListRefreshPeriod. */
    private long usersListRefreshPeriod = 20000;

    private void refreshUsersList() {
	UserInfo[] users = null;

	try {
	    users = mcw.getClientList(getUserInfo());
	} catch (NotRegistered e) {
	    LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
	    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_REGISTERED);
	    fireIChatMessageListenerConnectionChanged(false);
	} catch (NotAuthorized na) {
	    LoggerUtil.logThrowable(null, na, Logger.getLogger(LAB_CLIENT_LOGGER));
	    fireLabConnectorListenerLabStatusChanged(LabConnectorEvent.STATUS_NOT_AUTHORIZED);
	}

	if (users == null)
	    return;

	synchronized (usersList) {
	    usersList.clear();
	    for (int i = 0; i < users.length; i++) {
		usersList.add(users[i]);
	    }
	    fireExpUsersListChangeListenerUsersListChanged(getUsers());
	}
    }

    public UserInfo[] getUsers() {
	UserInfo[] usersObj = null;

	synchronized (usersList) {
	    usersObj = (UserInfo[]) usersList.toArray(new UserInfo[0]);
	}

	UserInfo[] users = null;

	if (usersObj != null)
	    users = new UserInfo[usersObj.length + 1];
	else
	    users = new UserInfo[1];

	users[0] = new UserInfo(ChatMessageEvent.EVERYONE_USER_ALIAS);

	if (usersObj != null)
	    for (int i = 0; i < usersObj.length; i++)
		users[i + 1] = usersObj[i];
	return users;
    }

    public String getRoomName() {
	return "Laboratory Main Hall";
    }

    /**
         * Registers IChatMessageListener to receive events.
         * 
         * @param listener
         *                The listener to register.
         */
    public synchronized void addChatMessageListener(com.linkare.rec.impl.client.chat.IChatMessageListener listener) {
	if (listenerList == null) {
	    listenerList = new javax.swing.event.EventListenerList();
	}

	listenerList.add(com.linkare.rec.impl.client.chat.IChatMessageListener.class, listener);
    }

    /**
         * Removes IChatMessageListener from the list of listeners.
         * 
         * @param listener
         *                The listener to remove.
         */
    public synchronized void removeChatMessageListener(com.linkare.rec.impl.client.chat.IChatMessageListener listener) {
	listenerList.remove(IChatMessageListener.class, listener);
    }

    /**
         * Notifies all registered listeners about the event.
         * 
         * @param event
         *                The event to be fired
         */
    private void fireIChatMessageListenerConnectionChanged(boolean isConnected) {
	if (listenerList == null)
	    return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == IChatMessageListener.class) {
		((IChatMessageListener) listeners[i + 1]).connectionChanged(new ChatConnectionEvent(this, isConnected));
		if (isConnected)
		    refreshUsersList();
	    }
	}
    }

    /**
         * Notifies all registered listeners about the event.
         * 
         * @param event
         *                The event to be fired
         */
    private void fireIChatMessageListenerRoomChanged(String newRoomName) {
	if (listenerList == null)
	    return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == IChatMessageListener.class) {
		((IChatMessageListener) listeners[i + 1]).roomChanged(new ChatRoomEvent(this, newRoomName));
	    }
	}
    }

    /**
         * Notifies all registered listeners about the event.
         * 
         * @param event
         *                The event to be fired
         */
    private void fireExpUsersListChangeListenerUsersListChanged(UserInfo[] event) {
	if (listenerList == null)
	    return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == ExpUsersListChangeListener.class) {
		((ExpUsersListChangeListener) listeners[i + 1]).usersListChanged(new ExpUsersListEvent(this, event));
	    } else if (listeners[i] == IChatMessageListener.class) {
		((IChatMessageListener) listeners[i + 1]).userListChanged(event);
	    }
	}
    }

    /**
         * Notifies all registered listeners about the event.
         * 
         * @param event
         *                The event to be fired
         */
    private void fireIChatMessageListenerNewChatMessage(ChatMessageEvent evt) {
	if (listenerList == null)
	    return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == IChatMessageListener.class) {
		((IChatMessageListener) listeners[i + 1]).newChatMessage(evt);
	    }
	}
    }

    /**
         * Getter for property usersListRefreshPeriod.
         * 
         * @return Value of property usersListRefreshPeriod.
         */
    public long getUsersListRefreshPeriod() {
	return this.usersListRefreshPeriod;
    }

    /**
         * Setter for property usersListRefreshPeriod.
         * 
         * @param usersListRefreshPeriod
         *                New value of property usersListRefreshPeriod.
         */
    public void setUsersListRefreshPeriod(long usersListRefreshPeriod) {
	this.usersListRefreshPeriod = usersListRefreshPeriod;
    }

    public void startAutoRefresh(long delayMillis) {
	usersListRefreshPeriod = delayMillis;
	if (usersListRefreshThread != null) {
	    usersListRefreshThread.stopNow();
	    while (usersListRefreshThread.isAlive()) {
		Thread.currentThread().yield();
	    }
	}
	usersListRefreshThread = new UsersListRefresher();
	usersListRefreshThread.start();
    }

    public void removeExpUsersListChangeListener(ExpUsersListChangeListener listener) {
	listenerList.remove(ExpUsersListChangeListener.class, listener);
    }

    public void addExpUsersListChangeListener(ExpUsersListChangeListener listener) {
	if (listenerList == null) {
	    listenerList = new javax.swing.event.EventListenerList();
	}

	listenerList.add(com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener.class, listener);
    }

    public void stopAutoRefresh() {
	usersListRefreshThread.stopNow();
    }

    public Apparatus getApparatusByID(String uniqueID) {
	Apparatus[] appList = (Apparatus[]) (apparatusList.toArray(new Apparatus[0]));
	for (int i = 0; i < appList.length; i++) {
	    if (appList[i].getHardwareInfo().getHardwareUniqueID().equals(uniqueID)) {
		return appList[i];
	    }
	}
	return null;
    }

    private UsersListRefresher usersListRefreshThread = null;// new
                                                                // UsersListRefresher();

    public class UsersListRefresher extends Thread {
	private volatile boolean stop = false;

	public void stopNow() {
	    stop = true;
	}

	public void run() {
	    while (!stop) {
		while ((mcw == null || !mcw.isConnected()) && !stop) {
		    try {
			sleep(usersListRefreshPeriod);
		    } catch (Exception e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
			return;
		    }
		}

		while ((mcw != null && mcw.isConnected()) && !stop) {
		    try {
			sleep(usersListRefreshPeriod);
			if (!stop) {
			    refreshUsersList();
			    // refreshHardwares();
			}
		    } catch (Exception e) {
			LoggerUtil.logThrowable(null, e, Logger.getLogger(LAB_CLIENT_LOGGER));
			return;
		    }
		}
	    }
	}
    }
}
