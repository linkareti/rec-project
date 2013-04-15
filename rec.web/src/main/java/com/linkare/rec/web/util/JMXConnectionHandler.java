package com.linkare.rec.web.util;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.management.InstanceNotFoundException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.web.mbean.MBeanConnectionResourcesUtilities;

public final class JMXConnectionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(JMXConnectionHandler.class);

    private final String jmxURL;
    private final String jmxUser;
    private final String jmxPass;

    private volatile JMXConnector jmxConnector;
    private volatile MBeanNotificationListenerInfo listener;
    private NotificationListener connectionListener;

    private final AtomicInteger numberOfRetries;

    private final Lock mainLock;

    public JMXConnectionHandler(final String jmxURL, final String jmxUser, final String jmxPass) {

	if (jmxURL == null || jmxUser == null || jmxPass == null) {
	    throw new NullPointerException("jmxURL, user or pwd cannot be null");
	}

	this.jmxURL = jmxURL;
	this.jmxUser = jmxUser;
	this.jmxPass = jmxPass;

	this.jmxConnector = null;
	this.numberOfRetries = new AtomicInteger(0);
	this.listener = null;

	mainLock = new ReentrantLock();
    }

    public boolean initJMXConnectorIfNotAlreadyRegistered(final MBeanNotificationListenerInfo notif) {
	boolean result = false;
	if (isAvailable()) {
	    if (!isConnected()) {

		mainLock.lock();
		try {

		    jmxConnector = MBeanConnectionResourcesUtilities.getJMXConnector(jmxURL, jmxUser, jmxPass);

		    LOGGER.info("jmxconnector created for: {} ", jmxURL);

		    jmxConnector.addConnectionNotificationListener(getConnectionNotificationListener(), null, null);

		    registerNotifListener(notif);

		    result = true;
		} catch (Exception e) {
		    numberOfRetries.incrementAndGet();
		    LOGGER.error(String.format("Error creating JMXCConnector to url: %s", jmxURL));
		    LOGGER.debug(e.getMessage(),e);
		    closeJMXConnector();
		} finally {
		    mainLock.unlock();
		}
	    }
	}
	return result;
    }

    private void registerNotifListener(final MBeanNotificationListenerInfo notificationListener) throws InstanceNotFoundException, IOException {

	listener = notificationListener;

	MBeanConnectionResourcesUtilities.addNotificationListener(jmxConnector, notificationListener.getObjectName(), listener.getListener(),
								  notificationListener.getFilter(), null);

	LOGGER.info("notificationListener added for: {}", jmxURL);
    }

    private NotificationListener getConnectionNotificationListener() {
	if (connectionListener == null) {

	    connectionListener = new NotificationListener() {

		public void handleNotification(Notification notification, Object handback) {

		    if (notification instanceof JMXConnectionNotification) {

			try {
			    final JMXConnectionNotification connectionNotif = (JMXConnectionNotification) notification;

			    final String type = connectionNotif.getType();

			    LOGGER.info("Receiving JMXConnectionNotification. Type:  " + type);

			    if (JMXConnectionNotification.CLOSED.equals(type) || JMXConnectionNotification.FAILED.equals(type)) {
				LOGGER.warn("Received JMXConnectionNotification CLOSED or FAILED. We will try to close jmxconnector associated with it");
				closeJMXConnector();
			    }
			    //			    } else if (JMXConnectionNotification.NOTIFS_LOST.equals(type)) {
			    //				//				LOG.warn("JMXConnectionNotification.NOTIFS_LOST");
			    //			    } else if (JMXConnectionNotification.OPENED.equals(type)) {
			    //				//				LOG.warn("Received JMXConnectionNotification.OPENED");
			    //			    }
			} catch (Throwable e) {
			    LOGGER.info("Error handling JMXConnectionNotification.", e);
			}
		    }
		}
	    };
	}
	return connectionListener;
    }

    public void closeJMXConnector() {
	mainLock.lock();
	try {
	    if (jmxConnector != null) {
		removeNotificationListener();
		removeConnectionNotificationListener();
		jmxConnector.close();
	    }
	} catch (Exception e) {
	    LOGGER.error("Error closing JMXConnector.", e);
	} finally {
	    jmxConnector = null;
	    mainLock.unlock();
	}
    }

    private void removeConnectionNotificationListener() {
	try {

	    if (jmxConnector != null && connectionListener != null) {
		jmxConnector.removeConnectionNotificationListener(connectionListener);
	    }
	} catch (Exception e) {
	    LOGGER.error("Error removing Connection NotificationListener.", e);
	} finally {
	    connectionListener = null;
	}

    }

    private void removeNotificationListener() {
	try {

	    if (jmxConnector != null && listener != null) {
		MBeanConnectionResourcesUtilities.removeNotificationListener(jmxConnector, listener.getObjectName(), listener.getListener());
	    }
	} catch (Exception e) {
	    LOGGER.error(e.getMessage(), e);
	} finally {
	    listener = null;
	}
    }

    private boolean isAvailable() {
	return (numberOfRetries.get() < Integer.MAX_VALUE);
    }

    public boolean isConnected() {
	return jmxConnector != null;
    }

    public <T> T getMbeanProxy(final ObjectName objectName, final Class<T> mbeanInterface) {
	try {
	    return isConnected() ? MBeanConnectionResourcesUtilities.getMBeanClientProxy(jmxConnector, objectName, mbeanInterface, true) : null;
	} catch (IOException e) {
	    LOGGER.error("Error creating jmx client proxy", e);
	    closeJMXConnector();
	    return null;
	}
    }

    public String getJmxURL() {
	return jmxURL;
    }

    public String getJmxUser() {
	return jmxUser;
    }

    public String getJmxPass() {
	return jmxPass;
    }

}
