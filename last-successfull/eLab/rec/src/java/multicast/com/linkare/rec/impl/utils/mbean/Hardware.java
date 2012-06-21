/* 
 * Hardware.java created on Jun 11, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.mbean.IHardwareMXBean;
import com.linkare.rec.am.mbean.NotificationTypeEnum;
import com.linkare.rec.impl.multicast.ReCMultiCastHardware;
import com.linkare.rec.impl.utils.mapping.DTOMapperUtils;
import com.linkare.rec.impl.wrappers.HardwareWrapper;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class Hardware implements IHardwareMXBean, NotificationEmitter {

	public static final String HARDWARE_LOGGER = "Hardware.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(Hardware.HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(Hardware.HARDWARE_LOGGER));
		}
	}

	private static final AtomicLong sequenceNumber = new AtomicLong(0L);

	private final ReCMultiCastHardware reCMultiCastHardware;

	private final HardwareWrapper hardware;


	public Hardware(final ReCMultiCastHardware reCMultiCastHardware) {
		this.reCMultiCastHardware = reCMultiCastHardware;
		hardware = this.reCMultiCastHardware.getHardware();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HardwareInfoDTO getHardwareInfo() {
		return DTOMapperUtils.mapToHardwareInfoDTO(hardware.getHardwareInfo());

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getState() {
		return hardware.getHardwareState().toString();
	}

	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		return NotificationTypeEnum.getMBeanNotificationInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws IllegalArgumentException {
		reCMultiCastHardware.addNotificationListener(listener, filter, handback);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
		reCMultiCastHardware.removeNotificationListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws ListenerNotFoundException {
		reCMultiCastHardware.removeNotificationListener(listener, filter, handback);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getClientList() {
		return reCMultiCastHardware.getClientUsernames();
	}

}
