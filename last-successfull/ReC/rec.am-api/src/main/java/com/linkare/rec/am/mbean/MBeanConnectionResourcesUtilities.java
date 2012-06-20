package com.linkare.rec.am.mbean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.management.InstanceNotFoundException;
import javax.management.JMX;
import javax.management.ListenerNotFoundException;
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

    public static JMXConnector getJMXConnector(final String ip, final int port, final String user,
	    final String pwd) throws IOException {

	if (ip == null || user == null || pwd == null) {
	    throw new NullPointerException("ip, user or pwd cannot be null");
	}

	final JMXServiceURL url;
	try {
	    url = new JMXServiceURL(new StringBuilder("service:jmx:rmi:///jndi/rmi://").append(ip)
		    .append(":").append(port).append("/jmxrmi").toString());
	} catch (MalformedURLException e) {
	    throw new IllegalArgumentException(e);
	}

	Map<String, String[]> authenticationMap = new HashMap<String, String[]>();
	String[] credentials = new String[] { user, pwd };
	authenticationMap.put(JMXConnector.CREDENTIALS, credentials);

	return JMXConnectorFactory.connect(url, authenticationMap);

    }

    public static <T> T getMBeanClientProxy(final JMXConnector jmxConnector,
	    final ObjectName objectName, final Class<T> mbeanInterface) throws IOException {

	if (objectName == null || mbeanInterface == null) {
	    throw new NullPointerException(
		    "jmxConnector, objectName or remoteInterface cannot be null");
	}

	return jmxConnector != null ? JMX.newMBeanProxy(jmxConnector.getMBeanServerConnection(), objectName, mbeanInterface)
		: JMX.newMBeanProxy(ManagementFactory.getPlatformMBeanServer(), objectName, mbeanInterface);

    }

    public static void addNotificationListener(final JMXConnector jmxConnector,
	    final ObjectName objectName, final NotificationListener listener,
	    final NotificationFilter filter, final Object handback) throws IOException,
	    InstanceNotFoundException {

	if (jmxConnector == null || objectName == null || listener == null) {
	    throw new NullPointerException("jmxConnector, objectName or listener cannot be null");
	}

	jmxConnector.getMBeanServerConnection().addNotificationListener(objectName, listener,
		filter, handback);
    }

    public static void removeNotificationListener(final JMXConnector jmxConnector,
	    final ObjectName objectName, final NotificationListener listener) throws IOException,
	    InstanceNotFoundException, ListenerNotFoundException {

	if (jmxConnector == null || objectName == null || listener == null) {
	    throw new NullPointerException("jmxConnector, objectName or listener cannot be null");
	}

	jmxConnector.getMBeanServerConnection().removeNotificationListener(objectName, listener);

    }

}
