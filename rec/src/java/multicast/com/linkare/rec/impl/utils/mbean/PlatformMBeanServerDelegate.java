/* 
 * PlatformMBeanServerDelegate.java created on Jun 11, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.NotCompliantMBeanException;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;
import javax.management.ObjectInstance;
import javax.management.ObjectName;

import com.linkare.rec.impl.mbean.ThreadPoolExecutorStatistics;
import com.linkare.rec.impl.multicast.ReCMultiCastController;
import com.linkare.rec.impl.multicast.ReCMultiCastHardware;
import com.linkare.rec.web.mbean.MBeanObjectNameFactory;

/**
 * 
 * A Delegate to the Platform MbeanServer
 * 
 * @author Artur Correia - Linkare TI
 */
public final class PlatformMBeanServerDelegate {

	private static final Logger LOGGER = Logger.getLogger(PlatformMBeanServerDelegate.class.getName());

	private PlatformMBeanServerDelegate() {
		throw new UnsupportedOperationException();
	}

	private static ObjectInstance registerMBean(final Object instance, final ObjectName objectName)
			throws ManagementException {

		if (instance == null) {
			throw new NullPointerException("MBean instance cannot be null");
		}

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Register MBEAN: " + objectName.getCanonicalName());
		}

		try {
			return ManagementFactory.getPlatformMBeanServer().registerMBean(instance, objectName);
		} catch (InstanceAlreadyExistsException e) {
			throw new ManagementException(e);
		} catch (MBeanRegistrationException e) {
			throw new ManagementException(e);
		} catch (NotCompliantMBeanException e) {
			throw new ManagementException(e);
		}

	}

	public static ObjectInstance registerThreadPoolExecutorStatisticsMXBean() throws ManagementException {
		return registerMBean(new ThreadPoolExecutorStatistics(),
				MBeanObjectNameFactory.getThreadPoolExecutorStatisticsObjectName());
	}

	public static ObjectInstance registerMultiCastControllerMXBean(final ReCMultiCastController reCMultiCastController)
			throws ManagementException {

		final MultiCastController multiCastController = new MultiCastController(reCMultiCastController);

		return registerMBean(multiCastController, MBeanObjectNameFactory.getMultiCastControllerObjectName());
	}

	public static ObjectInstance registerHardwareMXBean(final ReCMultiCastHardware reCMultiCastHardware)
			throws ManagementException {
		return registerMBean(new Hardware(reCMultiCastHardware),
				MBeanObjectNameFactory.getHardwareObjectName(reCMultiCastHardware.getHardwareUniqueId()));
	}

	private static void unRegister(final ObjectName objectName) throws ManagementException {

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("UnRegister MBEAN: " + objectName.getCanonicalName());
		}

		try {
			ManagementFactory.getPlatformMBeanServer().unregisterMBean(objectName);
		} catch (InstanceNotFoundException e) {
			if (LOGGER.isLoggable(Level.INFO)) {
				LOGGER.info(String.format("MBean with object name: %s not found for unregister",
						objectName.getCanonicalName()));
			}

		} catch (MBeanRegistrationException e) {
			throw new ManagementException(e);
		}
	}

	public static void addHardwareNotificationListener(final String hardwareUniqueID,
			final NotificationListener listener, final NotificationFilter filter, final Object handback)
			throws ManagementException {

		if (hardwareUniqueID == null || listener == null) {
			throw new NullPointerException("hardwareUniqueID or listener cannot be null");
		}

		final ObjectName obName = MBeanObjectNameFactory.getHardwareObjectName(hardwareUniqueID);

		addNotificationListener(obName, listener, filter, handback);

	}

	private static void addNotificationListener(final ObjectName obName, final NotificationListener listener,
			final NotificationFilter filter, final Object handback) throws ManagementException {

		if (LOGGER.isLoggable(Level.INFO)) {
			LOGGER.info("Register Notification Listener for MBean: " + obName.getCanonicalName());
		}

		try {
			ManagementFactory.getPlatformMBeanServer().addNotificationListener(obName, listener, filter, handback);
		} catch (InstanceNotFoundException e) {
			throw new ManagementException(e);
		}
	}

	public static void unregisterHardware(final String hardwareUniqueID) throws ManagementException {
		unRegister(MBeanObjectNameFactory.getHardwareObjectName(hardwareUniqueID));
	}
}
