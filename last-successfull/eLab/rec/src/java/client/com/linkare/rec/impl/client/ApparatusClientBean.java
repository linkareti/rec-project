/*
 * ApparatusClientBean.java
 *
 * Created on 13 de Maio de 2003, 20:15
 */

package com.linkare.rec.impl.client;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.omg.PortableServer.Servant;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.DataClientHelper;
import com.linkare.rec.acquisition.DataClientOperations;
import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.acquisition.IncorrectStateException;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.NotOwnerException;
import com.linkare.rec.acquisition.NotRegistered;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.apparatus.Apparatus;
import com.linkare.rec.impl.client.apparatus.ApparatusConnector;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorEvent;
import com.linkare.rec.impl.client.apparatus.ApparatusConnectorListener;
import com.linkare.rec.impl.client.chat.ChatConnectionEvent;
import com.linkare.rec.impl.client.chat.ChatRoomEvent;
import com.linkare.rec.impl.client.chat.IChatMessageListener;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener;
import com.linkare.rec.impl.client.experiment.ExpUsersListEvent;
import com.linkare.rec.impl.client.experiment.ExpUsersListSource;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;
import com.linkare.rec.impl.wrappers.MultiCastHardwareWrapper;

/**
 * This class implements a client for a lab apparatus... It hides the sordid
 * CORBA details and implements a JavaBeans style interface to the backend
 * system. It is not a user interface component, but rather enables faster
 * integration between the backend model and the UI via events
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ApparatusClientBean implements DataClientOperations, ExpUsersListSource, IChatServer, ApparatusConnector {

	private static final Logger LOGGER = Logger.getLogger(ApparatusClientBean.class.getName());

	private transient DataProducerWrapper dataProducerInEffect = null;
	private transient MultiCastHardwareWrapper mchw = null;
	private transient DataClient _this = null;
	private transient ObjectID oid = null;
	/**
	 * An internal management boolean which keeps the connected state
	 */
	public boolean connectedBefore = false;
	private UserInfo userInfo = null;

	/** Creates a new instance of ApparatusClientBean */
	public ApparatusClientBean() {

	}

	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList = null;

	// private helper methods
	private DataClient _this() {
		if (_this != null) {
			return _this;
		}

		try {
			oid = new ObjectID();
			Servant registeredServant = ORBBean.getORBBean().registerAutoIdRootPOAServant(DataClient.class, this, oid);
			org.omg.CORBA.Object thisReference = ORBBean.getORBBean().getAutoIdRootPOA()
					.servant_to_reference(registeredServant);
			_this = DataClientHelper.narrow(thisReference);
			return _this;
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Couldn't register this DataClient with ORB", e);
			return null;
		}

	}

	/**
	 * Determines if the client is currently connected to an apparatus server
	 * 
	 * @return The current connection state (True if this bean is connected to
	 *         an end system)
	 */
	public boolean isConnected() {
		boolean connected = false;
		try {
			connected = !mchw.getDelegate()._non_existent();
		} catch (final Exception e) {
			connected = false;
		}

		if (!connected) {
			disconnect();
		}

		return connected;
	}

	/**
	 * This sets the user information by which the user identifies itself
	 * 
	 * @param userInfo The information by which the server uniquely identifies
	 *            the client. It may depend on the authentication plugin of the
	 *            server, but usually it will be a username and password or just
	 *            a username. IF using the default SecurityManager at the server
	 *            (open access)
	 */
	@Override
	public void setUserInfo(final UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Returns the user identification currently being used by this apparatus
	 * client
	 * 
	 * @return The UserInfo Object currently being used to identify the client
	 *         with the apparatus server
	 */
	@Override
	public com.linkare.rec.acquisition.UserInfo getUserInfo() {
		// userInfo = _this().getUserInfo();
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		return userInfo;
	}

	/**
	 * Implementation of Operations interface for CORBA comm
	 */
	@Override
	public void hardwareChange() {
		// silent noop - MCHardwares don't send HardwareChanges
	}

	/**
	 * Implementation of Operations interface for CORBA comm
	 * 
	 * @param millisecs_to_lock_success The number of milliseconds until
	 *            possibility of lock is lost
	 */
	@Override
	public void hardwareLockable(final long millisecs_to_lock_success) {
		fireApparatusConnectorListenerApparatusLockable(millisecs_to_lock_success);
		// lets make sure we are the first in the table since we have lock...
		// refreshUsersList();
	}

	private HardwareState state = HardwareState.UNKNOWN;

	@Override
	public void hardwareStateChange(final HardwareState newState) {
		state = newState;
		LOGGER.log(Level.INFO, "Received new State info:" + newState);
		processState();
	}

	private void processState() {
		try {
			if (state.equals(HardwareState.UNKNOWN)) {
				fireApparatusConnectorListenerApparatusStateUnknow(null);
			} else if (state.equals(HardwareState.CONFIGURING)) {
				fireApparatusConnectorListenerApparatusStateConfiguring(null);
			} else if (state.equals(HardwareState.CONFIGURED)) {
				fireApparatusConnectorListenerApparatusStateConfigured(null);
			} else if (state.equals(HardwareState.STARTING)) {
				fireApparatusConnectorListenerApparatusStateStarting(null);
			} else if (state.equals(HardwareState.STARTED)) {
				fireApparatusConnectorListenerApparatusStateStarted(dataProducerInEffect);
			} else if (state.equals(HardwareState.STOPING)) {
				fireApparatusConnectorListenerApparatusStateStoping(null);
				dataProducerInEffect = null;
				// locked = false;
			} else if (state.equals(HardwareState.STOPED)) {
				fireApparatusConnectorListenerApparatusStateStoped(null);
				dataProducerInEffect = null;
				// locked = false;
			} else if (state.equals(HardwareState.RESETING)) {
				fireApparatusConnectorListenerApparatusStateReseting(null);
				dataProducerInEffect = null;
				// locked = false;
			} else if (state.equals(HardwareState.RESETED)) {
				fireApparatusConnectorListenerApparatusStateReseted(null);
				dataProducerInEffect = null;
				// locked = false;
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Error processing state", e);
		}
	}

	@Override
	public void receiveMessage(final String clientFrom, final String clientTo, final String message) {
		fireIChatMessageListenerNewChatMessage(new ChatMessageEvent(this, new UserInfo(clientFrom), new UserInfo(
				clientTo == ChatMessageEvent.EVERYONE_USER ? ChatMessageEvent.EVERYONE_USER_ALIAS : clientTo), message));
	}

	/**** End DataClient Operations ****/

	/**
	 * Registers ApparatusConnectorListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addApparatusConnectorListener(final ApparatusConnectorListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(ApparatusConnectorListener.class, listener);
	}

	/**
	 * Removes ApparatusConnectorListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeApparatusConnectorListener(final ApparatusConnectorListener listener) {
		listenerList.remove(ApparatusConnectorListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusConnecting(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusConnecting(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusConnected(final String message) {
		/*
		 * usersListRefreshThread = new UsersListRefresher();
		 * 
		 * usersListRefreshThread.start();
		 */

		startAutoRefresh(getUsersListRefreshPeriod());

		fireIChatMessageListenerConnectionChanged(true);

		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusConnected(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusDisconnecting(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusDisconnecting(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusDisconnected(final String message) {
		if (usersListRefreshThread != null) {
			// fireIChatMessageListenerUsersListChanged(null);
			fireExpUsersListChangeListenerUsersListChanged(null);
			usersListRefreshThread.stopNow();
			usersListRefreshThread = null;
		}

		fireIChatMessageListenerConnectionChanged(false);

		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusDisconnected(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusUnreachable(final String message) {
		disconnect();
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusUnreachable(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusNotAuthorized(final String message) {
		disconnect();
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusNotAuthorized(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusMaxUsers(final String message) {
		disconnect();
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusMaxUsers(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusNotRegistered(final String message) {
		disconnect();
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusNotRegistered(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateUnknow(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateUnknow(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateConfiguring(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateConfiguring(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateConfigured(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateConfigured(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateStarting(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateStarting(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateStarted(DataProducerWrapper dataSource) {
		if (dataSource == null) {
			try {
				dataSource = new DataProducerWrapper(apparatus.getMultiCastHardware().getDataProducer(getUserInfo()));
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Data Source is null... unable to fetch it from MCHardware...", e);
			}
		}
		if (dataSource == null) {
			return;
		}

		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateStarted(new ApparatusConnectorEvent(this,
						dataSource));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateStoping(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateStoping(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateStoped(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateStoped(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateReseting(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateReseting(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateReseted(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateReseted(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusLockable(final long millis_to_lock_success) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusLockable(new ApparatusConnectorEvent(this,
						millis_to_lock_success));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusIncorrectState(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusIncorrectState(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusLocked(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusLocked(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusNotOwner(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusNotOwner(new ApparatusConnectorEvent(this,
						message));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param param1 Parameter #1 of the <CODE>EventObject<CODE> constructor.
	 */
	private void fireApparatusConnectorListenerApparatusStateConfigError(final String message) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ApparatusConnectorListener.class) {
				((ApparatusConnectorListener) listeners[i + 1]).apparatusStateConfigError(new ApparatusConnectorEvent(
						this, message));
			}
		}
	}

	@Override
	public void stopAutoRefresh() {
		if (usersListRefreshThread != null) {
			usersListRefreshThread.stopNow();
		}

	}

	@Override
	public void startAutoRefresh(final long delayMillis) {
		usersListRefreshPeriod = delayMillis;
		if (usersListRefreshThread != null) {
			usersListRefreshThread.stopNow();
			while (usersListRefreshThread != null && usersListRefreshThread.isAlive()) {
				Thread.yield();
			}

			if (usersListRefreshThread == null) {
				return;
			}
		}
		usersListRefreshThread = new UsersListRefresher();
		usersListRefreshThread.start();
	}

	/**
	 * Registers ExpUsersListChangeListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addExpUsersListChangeListener(
			final com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener.class, listener);
	}

	/**
	 * Removes ExpUsersListChangeListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeExpUsersListChangeListener(
			final com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener listener) {
		listenerList.remove(com.linkare.rec.impl.client.experiment.ExpUsersListChangeListener.class, listener);
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
			if (listeners[i] == ExpUsersListChangeListener.class) {
				((ExpUsersListChangeListener) listeners[i + 1]).usersListChanged(new ExpUsersListEvent(this, event));
			}
			// Se isto existir... vamos ter problemas mais tarde...pois o chat
			// só vai ficar com os utilizadores ligados no apparatus em
			// questão...
			/*
			 * else if (listeners[i]==IChatMessageListener.class) {
			 * ((IChatMessageListener)listeners[i+1]).userListChanged(event); }
			 */
		}
	}

	private final Vector<UserInfo> usersList = new Vector<UserInfo>();
	// private Vector<UserInfo> expUsersList = new Vector<UserInfo>();

	/** Holds value of property usersListRefreshPeriod. */
	private long usersListRefreshPeriod = 20000;

	private String roomName = null;

	@Override
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(final String roomName) {
		this.roomName = roomName;
		fireIChatMessageListenerRoomChanged(roomName);
	}

	private void refreshUsersList() {
		UserInfo[] users = null;

		try {
			users = mchw.getClientList(getUserInfo());
		} catch (final NotRegistered e) {
			usersListRefreshThread.stopNow();
			usersListRefreshThread = null;
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
		} catch (final NotAuthorized na) {
			LOGGER.log(Level.SEVERE, na.getMessage(), na);
			fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
		}

		if (users == null) {
			return;
		}

		synchronized (usersList) {
			usersList.clear();
			for (final UserInfo user : users) {
				usersList.add(user);
			}
			// fireIChatMessageListenerUsersListChanged(getUsers());
		}
		fireExpUsersListChangeListenerUsersListChanged(getUsers());
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
			for (int i = 0; i < usersObj.length; i++) {
				users[i + 1] = usersObj[i];
			}
		}
		return users;
	}

	@Override
	public void sendMessage(final ChatMessageEvent evt) {
		if (mchw != null) {
			try {
				mchw.sendMessage(getUserInfo(), evt.getUserTo().getUserName(), evt.getMessage());
			} catch (final NotRegistered e) {
				fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			} catch (final NotAuthorized na) {
				LOGGER.log(Level.SEVERE, na.getMessage(), na);
				fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
			}
		}
	}

	/**
	 * Registers IChatMessageListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	@Override
	public synchronized void addChatMessageListener(final IChatMessageListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(IChatMessageListener.class, listener);
	}

	/**
	 * Removes IChatMessageListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	@Override
	public synchronized void removeChatMessageListener(final IChatMessageListener listener) {
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
				((IChatMessageListener) listeners[i + 1]).connectionChanged(new ChatConnectionEvent(this, isConnected));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void fireIChatMessageListenerRoomChanged(final String newRoomName) {
		if (listenerList == null) {
			return;
		}
		final Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == IChatMessageListener.class) {
				((IChatMessageListener) listeners[i + 1]).roomChanged(new ChatRoomEvent(this, newRoomName));
			}
		}
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	// Now who wants to listen must implement ExpUsersListChangeListener...
	/*
	 * private void fireIChatMessageListenerUsersListChanged(UserInfo[]
	 * newUsersList) { if (listenerList == null) return; Object[] listeners =
	 * listenerList.getListenerList(); for (int i = listeners.length-2; i>=0;
	 * i-=2) { if (listeners[i]==IChatMessageListener.class) {
	 * ((IChatMessageListener)listeners[i+1]).userListChanged(newUsersList); } }
	 * }
	 */

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

	/**
	 * Getter for property apparatus.
	 * 
	 * @return Value of property apparatus.
	 */
	public Apparatus getApparatus() {
		return apparatus;
	}

	/**
	 * Setter for property apparatus.
	 * 
	 * @param apparatus New value of property apparatus.
	 */
	public void setApparatus(final Apparatus apparatus) {
		this.apparatus = apparatus;
		if (apparatus != null) {
			mchw = apparatus.getMultiCastHardware();
		}
	}

	@Override
	public void connect() {
		if (apparatus == null || apparatus.getMultiCastHardware() == null) {
			fireApparatusConnectorListenerApparatusDisconnected(null);
			return;
		}
		fireApparatusConnectorListenerApparatusConnecting(null);
		mchw = apparatus.getMultiCastHardware();

		try {
			if (mchw.isConnected()) {
				try {
					mchw.registerDataClient(_this());
					refreshUsersList();
					setRoomName(mchw.getHardwareInfo(getUserInfo()).getFamiliarName());
					connectedBefore = true;
					fireApparatusConnectorListenerApparatusConnected(null);
				} catch (final MaximumClientsReached e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					fireApparatusConnectorListenerApparatusMaxUsers(e.getMessage());
				} catch (final NotAuthorized e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					fireApparatusConnectorListenerApparatusNotAuthorized(e.getMessage());
				} catch (final NotAvailableException e) {
					LOGGER.log(Level.SEVERE, e.getMessage(), e);
					fireApparatusConnectorListenerApparatusDisconnected(e.getMessage());
				}

			} else {
				fireApparatusConnectorListenerApparatusDisconnected(null);
			}
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			e.printStackTrace();
			fireApparatusConnectorListenerApparatusUnreachable(e.getMessage());
		}
	}

	@Override
	public void disconnect() {
		if (connectedBefore) {
			try {
				fireApparatusConnectorListenerApparatusDisconnecting(null);
				// System.out.println("Trying to deactivate object with oid = "
				// + oid.getOid());
				ORBBean.getORBBean().deactivateServant(oid.getOid());
				_this = null;
				// System.out.println("Deactivated object with oid = " +
				// oid.getOid());
				fireApparatusConnectorListenerApparatusDisconnected(null);
				connectedBefore = false;
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				e.printStackTrace();
			}
		}
	}

	// private void reconnect() {
	// disconnect();
	// connect();
	// }
	//
	// private boolean locked = false;

	@Override
	public void lock() {
		if (mchw != null) {
			try {
				mchw.requireLock(getUserInfo());
				// locked = true;
				fireApparatusConnectorListenerApparatusLocked(null);
			} catch (final IncorrectStateException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusIncorrectState(e.getMessage());
			} catch (final NotAvailableException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusDisconnecting(e.getMessage());
			} catch (final NotRegistered e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
			} catch (final NotOwnerException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotOwner(e.getMessage());
			} catch (final NotAuthorized na) {
				LOGGER.log(Level.SEVERE, na.getMessage(), na);
				fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
			}
		} else {
			fireApparatusConnectorListenerApparatusDisconnected(null);
		}

	}

	@Override
	public void configure(final HardwareAcquisitionConfig config) {
		if (mchw != null) {
			try {
				mchw.configure(getUserInfo(), config);

			} catch (final IncorrectStateException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusIncorrectState(e.getMessage());
			} catch (final NotAvailableException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusDisconnecting(e.getMessage());
			} catch (final WrongConfigurationException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusStateConfigError(e.getMessage());
			} catch (final NotRegistered e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
			} catch (final NotOwnerException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotOwner(e.getMessage());
			} catch (final NotAuthorized na) {
				LOGGER.log(Level.SEVERE, na.getMessage(), na);
				fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
			}
		} else {
			fireApparatusConnectorListenerApparatusDisconnected(null);
		}
	}

	@Override
	public void reset() {
		if (mchw != null) {
			try {
				mchw.reset(getUserInfo());
				dataProducerInEffect = null;
			} catch (final IncorrectStateException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusIncorrectState(e.getMessage());
			} catch (final NotAvailableException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusDisconnecting(e.getMessage());
			} catch (final NotRegistered e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
			} catch (final NotOwnerException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotOwner(e.getMessage());
			} catch (final NotAuthorized na) {
				LOGGER.log(Level.SEVERE, na.getMessage(), na);
				fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
			}
		} else {
			fireApparatusConnectorListenerApparatusDisconnected(null);
		}
	}

	@Override
	public void start() {
		if (mchw != null) {
			try {
				final DataProducer dataProducer = mchw.start(getUserInfo());
				dataProducerInEffect = new DataProducerWrapper(dataProducer);

			} catch (final IncorrectStateException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusIncorrectState(e.getMessage());
			} catch (final NotAvailableException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusDisconnecting(e.getMessage());
			} catch (final NotRegistered e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
			} catch (final NotOwnerException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotOwner(e.getMessage());
			} catch (final NotAuthorized na) {
				LOGGER.log(Level.SEVERE, na.getMessage(), na);
				fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
			}
		} else {
			fireApparatusConnectorListenerApparatusDisconnected(null);
		}
	}

	@Override
	public void stop() {
		if (mchw != null) {
			try {
				mchw.stop(getUserInfo());
				dataProducerInEffect = null;

			} catch (final IncorrectStateException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusIncorrectState(e.getMessage());
			} catch (final NotAvailableException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusDisconnecting(e.getMessage());
			} catch (final NotRegistered e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotRegistered(e.getMessage());
			} catch (final NotOwnerException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
				fireApparatusConnectorListenerApparatusNotOwner(e.getMessage());
			} catch (final NotAuthorized na) {
				LOGGER.log(Level.SEVERE, na.getMessage(), na);
				fireApparatusConnectorListenerApparatusNotAuthorized(na.getMessage());
			}
		} else {
			fireApparatusConnectorListenerApparatusDisconnected(null);
		}
	}

	private UsersListRefresher usersListRefreshThread = null;// new
	// UsersListRefresher();

	/** Holds value of property apparatus. */
	private Apparatus apparatus;

	public class UsersListRefresher extends Thread {
		private volatile boolean stop = false;

		public UsersListRefresher() {
			setName(getName() + " - UsersListRefresher");
		}

		public void stopNow() {
			stop = true;
		}

		@Override
		public void run() {
			while (!stop) {
				while ((mchw == null || !mchw.isConnected()) && !stop) {
					try {
						Thread.sleep(usersListRefreshPeriod);
					} catch (final Exception e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
						return;
					}
				}

				while (!stop && mchw != null && mchw.isConnected()) {
					try {
						Thread.sleep(usersListRefreshPeriod);
						if (!stop) {
							refreshUsersList();
						}
					} catch (final Exception e) {
						LOGGER.log(Level.SEVERE, e.getMessage(), e);
						return;
					}
				}
			}
		}
	}
}
