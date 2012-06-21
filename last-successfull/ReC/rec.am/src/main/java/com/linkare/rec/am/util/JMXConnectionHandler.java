package com.linkare.rec.am.util;

import java.io.IOException;

import javax.management.InstanceNotFoundException;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectionNotification;
import javax.management.remote.JMXConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.rec.am.mbean.MBeanConnectionResourcesUtilities;
import com.linkare.rec.am.model.Laboratory;

public final class JMXConnectionHandler {

    private final static Logger LOG = LoggerFactory.getLogger(JMXConnectionHandler.class);

    //TODO: system property?
    private static final int MAX_NUMBER_OF_RETRIES = 50;

    private final String jmxIP;
    private final int jmxPort;
    private final String jmxUser;
    private final String jmxPass;

    private JMXConnector jmxConnector;
    private NotificationListener listener;
    private NotificationListener connectionListener;
    private ObjectName objName;

    private int numberOfRetries;

    private boolean isConnected;

    public JMXConnectionHandler(final Laboratory lab) {

	this.jmxIP = lab.getJmxIP();
	this.jmxPort = lab.getJmxPort();
	this.jmxUser = lab.getJmxUser();
	this.jmxPass = lab.getJmxPass();

	this.jmxConnector = null;
	this.numberOfRetries = 0;
	this.listener = null;
	this.objName = null;
	this.isConnected = false;
    }

    public boolean initJMXConnectorIfPossible() {
	try {

	    if (isAvailable()) {

		if (jmxConnector == null) {

		    jmxConnector = MBeanConnectionResourcesUtilities.getJMXConnector(jmxIP, jmxPort, jmxUser, jmxPass);

		    LOG.info("jmxconnector created for: {}:{} ", new Object[] { jmxIP, jmxPort });

		    jmxConnector.addConnectionNotificationListener(getConnectionNotificationListener(), null, null);

		    isConnected = true;
		}
	    }

	} catch (Exception e) {
	    this.numberOfRetries++;
	    LOG.error("Error creating JMXCConnector to ip: {}:{}", new Object[] { jmxIP, jmxPort }, e);
	}
	return isConnected;
    }

    public void registerNotificationListener(final NotificationListener lst, final ObjectName objectName, final NotificationFilter filter, final Object handback) {
	try {
	    if (listener == null) {
		listener = lst;
		objName = objectName;

		MBeanConnectionResourcesUtilities.addNotificationListener(jmxConnector, objectName, listener, filter, handback);

		LOG.info("notificationListener added for: {}:{} ", new Object[] { jmxIP, jmxPort });
	    }
	} catch (IOException e) {
	    LOG.error("jmx connection problems. next iteration will try to create a new jmxconnector", e);
	    closeJMXConnector();
	} catch (InstanceNotFoundException e) {
	    LOG.info(e.getMessage(), e);
	}
    }

    private NotificationListener getConnectionNotificationListener() {
	if (connectionListener == null) {

	    connectionListener = new NotificationListener() {

		public void handleNotification(Notification notification, Object handback) {

		    if (notification instanceof JMXConnectionNotification) {

			try {
			    final JMXConnectionNotification connectionNotif = (JMXConnectionNotification) notification;

			    final String type = connectionNotif.getType();

			    LOG.info("Receiving JMXConnectionNotification. Type:  " + type);

			    if (JMXConnectionNotification.CLOSED.equals(type)) {
				//				Events.instance().raiseEvent(EventTypeEnum.JMXCONNECTOR_CLOSED.name(), LaboratoryJMXConnectionHandler.this.laboratory);
			    } else if (JMXConnectionNotification.FAILED.equals(type)) {
				//				Events.instance().raiseEvent(EventTypeEnum.JMXCONNECTOR_FAILED.name(), LaboratoryJMXConnectionHandler.this.laboratory);
			    }
			    // } else if
			    // (JMXConnectionNotification.NOTIFS_LOST.equals(type))
			    // {
			    // // do nothing
			    // } else if
			    // (JMXConnectionNotification.OPENED.equals(type)) {
			    // // do nothing
			    // }
			} catch (Throwable e) {
			    LOG.info("Error handling JMXConnectionNotification.", e);
			}
		    }
		}
	    };
	}
	return connectionListener;
    }

    public void closeJMXConnector() {
	try {
	    if (jmxConnector != null) {
		removeNotificationListener();
		removeConnectionNotificationListener();
		jmxConnector.close();
		isConnected = false;
	    }
	} catch (IOException e) {
	    LOG.error("Error closing JMXConnector.", e);
	} finally {
	    jmxConnector = null;
	}
    }

    private void removeConnectionNotificationListener() {
	try {

	    if (jmxConnector != null && connectionListener != null) {
		jmxConnector.removeConnectionNotificationListener(connectionListener);
	    }
	    connectionListener = null;
	} catch (Exception e) {
	    LOG.error("Error removing Connection NotificationListener.", e);
	}

    }

    private void removeNotificationListener() {
	try {

	    if (jmxConnector != null && objName != null && listener != null) {
		MBeanConnectionResourcesUtilities.removeNotificationListener(jmxConnector, objName, listener);
	    }
	    listener = null;
	    objName = null;

	} catch (Exception e) {
	    LOG.error(e.getMessage(), e);
	}
    }

    private boolean isAvailable() {
	return (numberOfRetries < MAX_NUMBER_OF_RETRIES);
    }

    public boolean isConnected() {
	return isConnected;
    }

    public <T> T getMbeanProxy(final ObjectName objectName, final Class<T> mbeanInterface) {
	try {
	    return isConnected ? MBeanConnectionResourcesUtilities.getMBeanClientProxy(jmxConnector, objectName, mbeanInterface) : null;
	} catch (IOException e) {
	    LOG.error("Error creating jmx client proxy", e);
	    return null;
	}
    }

}
