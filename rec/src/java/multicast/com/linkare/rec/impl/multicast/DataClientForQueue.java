/*
 * DataClientForQueue.java
 *
 * Created on 13 de Agosto de 2002, 16:15
 */

package com.linkare.rec.impl.multicast;

import java.util.logging.Level;
import java.util.logging.Logger;

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

	private static final Logger LOGGER = Logger.getLogger(DataClientForQueue.class.getName());

	private DataClientWrapper dcw = null;
	private IDataClientForQueueListener dataClientForQueueListener = null;
	public EventQueue messageQueue = null;
	private UserInfo userInfo = null;
	private UserInfo userInfoCleaned = null;
	private DefaultUser defaultUser = null;
	private IResource resource = null;

	public DataClientForQueue(final IResource resource, final DataClient dc,
			final IDataClientForQueueListener dataClientForQueueListener) throws NotAuthorized {
		this.resource = resource;

		setDataClientForQueueListener(dataClientForQueueListener);
		dcw = new DataClientWrapper(dc);

		if (!dcw.isConnected()) {
			LOGGER.log(Level.SEVERE, "Error getting username in DataClientForQueue - Throwing not authorized...");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NOT_AVAILABLE);
		}

		setUserInfo(dcw.getUserInfo());

		if (getUserInfo() == null || getUserInfo().getUserName() == null) {
			LOGGER.log(Level.SEVERE, "Username is null - Throwing not authorized...");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_NULL);
		}

		if (getUserInfo().getUserName().equals("")) {
			LOGGER.log(Level.SEVERE, "Username is empty - Throwing not authorized...");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_USERNAME_EMPTY);
		}

		if (!SecurityManagerFactory.authenticate(this.resource, getAsDefaultUser())) {
			LOGGER.log(Level.SEVERE, "SecurityManager didn't authenticate you");
			throw new NotAuthorized(NotAuthorizedConstants.NOT_AUTHORIZED_SECURITY_MANAGER);
		}

		messageQueue = new EventQueue(new DataClientQueueDispatcher(), "DataClientForQueue - " + userInfo.getUserName());
	}

	public String getUserName() {
		return getUserInfo().getUserName();
	}

	@Override
	public String toString() {
		return "Proxy Client " + getUserName();
	}

	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof DataClientForQueue)) {
			return false;
		}

		final DataClientForQueue other = (DataClientForQueue) obj;

		if (other.dcw != null && other.dcw.isSameDelegate(dcw)) {
			return true;
		}

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
			@Override
			public void run() {
				setName(getName() + " - DataClientForQueue - shutdown");
				shutdown();
			}
		}).start();
	}

	private boolean shutDown = false;

	public synchronized void shutdown() {
		if (shutDown) {
			return;
		}
		shutDown = true;
		LOGGER.log(Level.INFO, "client " + getUserName() + " - Shutting down!");
		LOGGER.log(Level.INFO, "client " + getUserName() + " - shutting down message queue!");
		messageQueue.shutdown();
		LOGGER.log(Level.INFO, "client " + getUserName() + " - message queue is shut down!");
		LOGGER.log(Level.INFO, "client " + getUserName() + " - informing dataClientForQueueListener that I'm gone!");
		getDataClientForQueueListener().dataClientForQueueIsGone(this);
		LOGGER.log(Level.INFO, "client " + getUserName() + " is shut down!");
		shutDown = false;
	}

	public boolean isShuttingDown() {
		return shutDown;
	}

	public boolean sameUser(final DataClientForQueue dcfqother) {
		return sameUser(dcfqother.getUserInfo());
	}

	public boolean sameUser(final UserInfo userInfo) {
		if (userInfo != null && userInfo.equals(getUserInfo())) {
			return true;
		}

		return false;
	}

	public String getPassword() {
		return getUserInfo().getPassword();
	}

	public byte[] getCertificate() {
		return getUserInfo().getCertificate();
	}

	public void hardwareChange() {
		messageQueue.addEvent(new HardwareChangeEvent());
	}

	public void hardwareLockable(final HardwareLockEvent evt) {
		messageQueue.addEvent(evt);
	}

	public void hardwareStateChange(final HardwareStateChangeEvent evt) {
		messageQueue.addEvent(evt);
	}

	public void receiveMessage(final ChatMessageEvent evt) {
		if (evt.getUserTo() == null || evt.getUserTo().getUserName() == null
				|| evt.getUserTo().getUserName().equals(getUserName())) {
			messageQueue.addEvent(evt);
		}
	}

	public DataClientWrapper getDataClient() {
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
		if (defaultUser == null) {
			defaultUser = new DefaultUser(userInfo);
		}

		return defaultUser;
	}

	/**
	 * Setter for property userInfo.
	 * 
	 * @param userInfo New value of property userInfo.
	 * 
	 */
	public void setUserInfo(final UserInfo userInfo) {
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
			final com.linkare.rec.impl.multicast.IDataClientForQueueListener dataClientForQueueListener) {
		this.dataClientForQueueListener = dataClientForQueueListener;
	}

	private class DataClientQueueDispatcher implements EventQueueDispatcher {

		@Override
		public void dispatchEvent(final Object o) {
			// Connection check
			if (!dcw.isConnected()) {
				shutdownAsSoonAsPossible();
				return;
			}

			try {
				if (o instanceof HardwareStateChangeEvent) {
					final HardwareStateChangeEvent evt = (HardwareStateChangeEvent) o;
					LOGGER.log(Level.FINE, "Dispatching hardware state [" + evt.getNewState() + "] + Client : "+DataClientForQueue.this.getUserName());
					dcw.hardwareStateChange(evt.getNewState());
					LOGGER.log(Level.FINE, "Dispatched hardware state [" + evt.getNewState() + "] + Client : "+DataClientForQueue.this.getUserName());
				}
				if (o instanceof HardwareChangeEvent) {
					//HardwareChangeEvent evt=(HardwareChangeEvent) o;
					LOGGER.log(Level.FINE, "Dispatching hardware change + Client : "+DataClientForQueue.this.getUserName());
					dcw.hardwareChange();
					LOGGER.log(Level.FINE, "Dispatched hardware change + Client : "+DataClientForQueue.this.getUserName());
				}
				if (o instanceof ChatMessageEvent) {
					final ChatMessageEvent evt = (ChatMessageEvent) o;
					if (evt.getUserFrom() == null || evt.getUserTo() == null) {
						return;
					}

					LOGGER.log(Level.FINE, "Dispatching chat message + Client : "+DataClientForQueue.this.getUserName());
					dcw.receiveMessage(evt.getUserFrom().getUserName(), evt.getUserTo().getUserName(), evt.getMessage());
					LOGGER.log(Level.FINE, "Dispatched chat message + Client : "+DataClientForQueue.this.getUserName());
				}
				if (o instanceof HardwareLockEvent) {
					final HardwareLockEvent evt = (HardwareLockEvent) o;
					// aqui o evento de HardwareLock que esta na Queue e
					// desmultiplicado
					// para true CORBA way
					LOGGER.log(Level.FINE, "Dispatching lock to + Client : "+DataClientForQueue.this.getUserName());
					dcw.hardwareLockable(evt.getMillisecondsToLockSuccess());
					LOGGER.log(Level.FINE, "Dispatched lock to + Client : "+DataClientForQueue.this.getUserName());
					// para alem disso, o proprio evento tem consigo
					// um Thread para fazer o countDown...
					evt.startCountDown();
				}

			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Oooppss.. client gone? - Error dispatching event to client! Why? Gone?", e);
				if (!isConnected()) {
					shutdownAsSoonAsPossible();
					return;
				}
			}
		}

		@Override
		public int getPriority() {
			return Thread.NORM_PRIORITY;
		}

	}

	/**
	 * @return the resource
	 */
	public IResource getResource() {
		return resource;
	}

}
