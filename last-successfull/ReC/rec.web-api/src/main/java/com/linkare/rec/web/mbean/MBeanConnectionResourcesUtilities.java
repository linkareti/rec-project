package com.linkare.rec.web.mbean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.ListenerNotFoundException;
import javax.management.MBeanServerConnection;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

public final class MBeanConnectionResourcesUtilities {

    private MBeanConnectionResourcesUtilities() {
	throw new UnsupportedOperationException();
    }

    private static Map<String, String[]> getAuthenticationMap(final String user, final String pwd) {
	final Map<String, String[]> authenticationMap = new HashMap<String, String[]>();
	final String[] credentials = new String[] { user, pwd };
	authenticationMap.put(JMXConnector.CREDENTIALS, credentials);
	return authenticationMap;
    }

    public static JMXConnector getJMXConnector(final String jmxURL, final String user, final String pwd) throws IOException {

	if (jmxURL == null || user == null || pwd == null) {
	    throw new NullPointerException("jmxURL, user or pwd cannot be null");
	}

	final JMXServiceURL url;
	try {
	    url = new JMXServiceURL(jmxURL);
	} catch (MalformedURLException e) {
	    throw new IllegalArgumentException(e);
	}

	return JMXConnectorFactory.connect(url, getAuthenticationMap(user, pwd));

    }

    public static <T> T getMBeanClientProxy(final JMXConnector jmxConnector, final ObjectName objectName, final Class<T> mbeanInterface, final boolean isMXBean)
	    throws IOException {

	if (objectName == null || mbeanInterface == null) {
	    throw new NullPointerException("jmxConnector, objectName or remoteInterface cannot be null");
	}

	final MBeanServerConnection mBeanServerConnection = jmxConnector != null ? jmxConnector.getMBeanServerConnection()
		: ManagementFactory.getPlatformMBeanServer();

	return isMXBean ? JMX.newMXBeanProxy(mBeanServerConnection, objectName, mbeanInterface) : JMX.newMBeanProxy(mBeanServerConnection, objectName,
														    mbeanInterface);

    }

    public static void addNotificationListener(final JMXConnector jmxConnector, final ObjectName objectName, final NotificationListener listener,
	    final NotificationFilter filter, final Object handback) throws IOException, InstanceNotFoundException {

	if (jmxConnector == null || objectName == null || listener == null) {
	    throw new NullPointerException("jmxConnector, objectName or listener cannot be null");
	}

	jmxConnector.getMBeanServerConnection().addNotificationListener(objectName, listener, filter, handback);
    }

    public static void removeNotificationListener(final JMXConnector jmxConnector, final ObjectName objectName, final NotificationListener listener)
	    throws IOException, InstanceNotFoundException, ListenerNotFoundException {

	if (jmxConnector == null || objectName == null || listener == null) {
	    throw new NullPointerException("jmxConnector, objectName or listener cannot be null");
	}

	jmxConnector.getMBeanServerConnection().removeNotificationListener(objectName, listener);

    }

}
