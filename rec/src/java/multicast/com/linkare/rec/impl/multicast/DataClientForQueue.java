/*
 * DataClientForQueue.java
 *
 * Created on 13 de Agosto de 2002, 16:15
 */

package com.linkare.rec.impl.multicast;

import java.util.logging.Level;

import com.linkare.rec.acquisition.DataClient;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.acquisition.PROPKEY_USERINFO_CERTIFICATE;
import com.linkare.rec.acquisition.PROPKEY_USERINFO_PASS;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.events.HardwareChangeEvent;
import com.linkare.rec.impl.events.HardwareLockEvent;
import com.linkare.rec.impl.events.HardwareStateChangeEvent;
import com.linkare.rec.impl.exceptions.NotAuthorizedConstants;
import com.linkare.rec.impl.multicast.security.DefaultUser;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.SecurityManagerFactory;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.impl.wrappers.DataClientWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */

public class DataClientForQueue {

	private DataClientWrapper dcw = null;
	private IDataClientForQueueListener dataClientForQueueListener = null;
	public EventQueue messageQueue = null;
	private UserInfo userInfo = null;
	private UserInfo userInfoCleaned = null;
	private DefaultUser defaultUser = null;

	public DataClientForQueue(IResource resource, DataClient dc, IDataClientForQueueListener dataClientForQueueListener)
			throws NotAuthorized {
		setDataClientForQueueListener(dataClientForQueueListener);
		this.dcw = new DataClientWrapper(dc);

		if (!dcw.isConnected()) {
			getDataClientForQueueListener().log(Level.SEVERE,
					"Error getting username in DataClientForQueue - Throwing not authorized...");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE);
		}

		setUserInfo(dcw.getUserInfo());

		if (getUserInfo() == null || getUserInfo().getUserName() == null) {
			getDataClientForQueueListener().log(Level.SEVERE, "Username is null - Throwing not authorized...");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NULL);
		}

		if (getUserInfo().getUserName().equals("")) {
			getDataClientForQueueListener().log(Level.SEVERE, "Username is empty - Throwing not authorized...");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_EMPTY);
		}

		if (!SecurityManagerFactory.authenticate(resource, getAsDefaultUser())) {
			getDataClientForQueueListener().log(Level.SEVERE, "SecurityManager didn't authenticate you");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_SECURITY_MANAGER);
		}

		messageQueue = new EventQueue(new DataClientQueueDispatcher());
	}

	public String getUserName() {
		return getUserInfo().getUserName();
	}

	public String toString() {
		return "Proxy Client " + getUserName();
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof DataClientForQueue))
			return false;

		DataClientForQueue other = (DataClientForQueue) obj;

		if (other.dcw != null && other.dcw.isSameDelegate(dcw))
			return true;

		return false;
	}

	public boolean isConnected() {
		return dcw.isConnected();
	}

	public void shutdownAsSoonAsPossible() {
		// Just creating a Thread so that we don't call shutdown on the
		// messageQueue from its own Thread...
		// otherwise we would block because the queue is waiting for a join to
		// it's dispatching Thread...
		(new Thread() {
			public void run() {
				shutdown();
			}
		}).start();
	}

	private boolean shutDown = false;

	public synchronized void shutdown() {
		if (shutDown)
			return;
		shutDown = true;
		getDataClientForQueueListener().log(Level.INFO, "client " + getUserName() + " - Shutting down!");
		getDataClientForQueueListener().log(Level.INFO, "client " + getUserName() + " - shutting down message queue!");
		messageQueue.shutdown();
		getDataClientForQueueListener().log(Level.INFO, "client " + getUserName() + " - message queue is shut down!");
		;
		getDataClientForQueueListener().log(Level.INFO,
				"client " + getUserName() + " - informing dataClientForQueueListener that I'm gone!");
		getDataClientForQueueListener().dataClientForQueueIsGone(this);
		getDataClientForQueueListener().log(Level.INFO, "client " + getUserName() + " is shut down!");
		shutDown = false;
	}

	// I want to now if the user is shutting down TODO CHECK WITH JP
	public boolean isShuttingDown() {
		return shutDown;
	}

	public boolean sameUser(DataClientForQueue dcfqother) {
		return sameUser(dcfqother.getUserInfo());
	}

	public boolean sameUser(UserInfo userInfo) {
		if (userInfo != null && userInfo.equals(getUserInfo()))
			return true;

		return false;
	}

	public String getPassword() {
		return getUserInfo().getPassword();
	}

	public byte[] getCertificate() {
		return getUserInfo().getCertificate();
	}

	public void hardwareChange() {
		if (!dcw.isConnected()) {
			shutdown();
			return;
		}
		messageQueue.addEvent(new HardwareChangeEvent());
	}

	public void hardwareLockable(HardwareLockEvent evt) {
		if (!dcw.isConnected()) {
			shutdown();
			return;
		}
		messageQueue.addEvent(evt);
	}

	public void hardwareStateChange(HardwareStateChangeEvent evt) {
		if (!dcw.isConnected()) {
			shutdown();
			return;
		}
		messageQueue.addEvent(evt);
	}

	public void receiveMessage(ChatMessageEvent evt) {
		if (!dcw.isConnected()) {
			shutdown();
			return;
		}
		/*
		 * if(evt.getUserTo()==null) return;
		 */

		if (evt.getUserTo() == null || evt.getUserTo().getUserName() == null
				|| evt.getUserTo().getUserName().equals(getUserName()))
			messageQueue.addEvent(evt);
	}

	public DataClientWrapper getDataClient() {
		if (!dcw.isConnected()) {
			shutdown();
			return null;
		}

		return dcw;
	}

	/**
	 * Getter for property userInfo.
	 * 
	 * @return Value of property userInfo.
	 * 
	 */
	public UserInfo getUserInfo() {

		return userInfo;
	}

	/**
	 * Getter for property userInfo.
	 * 
	 * @return Value of property userInfo.
	 * 
	 */
	public UserInfo getUserInfoCleaned() {
		if (userInfoCleaned == null) {
			userInfoCleaned = new UserInfo(userInfo);
			userInfoCleaned.removeUserProp(PROPKEY_USERINFO_CERTIFICATE.value);
			userInfoCleaned.removeUserProp(PROPKEY_USERINFO_PASS.value);
		}

		return userInfoCleaned;
	}

	public DefaultUser getAsDefaultUser() {
		if (defaultUser == null)
			defaultUser = new DefaultUser(userInfo);

		return defaultUser;
	}

	/**
	 * Setter for property userInfo.
	 * 
	 * @param userInfo New value of property userInfo.
	 * 
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/**
	 * Getter for property dataClientForQueueListener.
	 * 
	 * @return Value of property dataClientForQueueListener.
	 * 
	 */
	public com.linkare.rec.impl.multicast.IDataClientForQueueListener getDataClientForQueueListener() {
		return dataClientForQueueListener;
	}

	/**
	 * Setter for property dataClientForQueueListener.
	 * 
	 * @param dataClientForQueueListener New value of property
	 *            dataClientForQueueListener.
	 * 
	 */
	public void setDataClientForQueueListener(
			com.linkare.rec.impl.multicast.IDataClientForQueueListener dataClientForQueueListener) {
		this.dataClientForQueueListener = dataClientForQueueListener;
	}

	private class DataClientQueueDispatcher implements EventQueueDispatcher {
		public void dispatchEvent(Object o) {
			if (!dcw.isConnected()) {
				shutdownAsSoonAsPossible();
				return;
			}
			try {
				if (o instanceof HardwareStateChangeEvent) {
					HardwareStateChangeEvent evt = (HardwareStateChangeEvent) o;
					dcw.hardwareStateChange(evt.getNewState());
				}
				if (o instanceof HardwareChangeEvent) {
					dcw.hardwareChange();
				}
				if (o instanceof ChatMessageEvent) {
					ChatMessageEvent evt = (ChatMessageEvent) o;
					if (evt.getUserFrom() == null || evt.getUserTo() == null)
						return;

					dcw
							.receiveMessage(evt.getUserFrom().getUserName(), evt.getUserTo().getUserName(), evt
									.getMessage());
				}
				if (o instanceof HardwareLockEvent) {
					HardwareLockEvent evt = (HardwareLockEvent) o;
					// aqui o evento de HardwareLock que esta na Queue e
					// desmultiplicado
					// para true CORBA way
					dcw.hardwareLockable(evt.getMilliseconds_to_lock_success());
					// para alem disso, o proprio evento tem consigo
					// um Thread para fazer o countDown...
					evt.startCountDown();
				}

			} catch (Exception e) {
				getDataClientForQueueListener().logThrowable(
						"Oooppss.. client gone? - Error dispatching event to client! Why? Gone?", e);
				if (!isConnected()) {
					shutdownAsSoonAsPossible();
					return;
				}
			}
		}

		public int getPriority() {
			return Thread.NORM_PRIORITY;
		}

	}

}
