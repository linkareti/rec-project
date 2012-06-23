/* 
 * MultiCastController.java created on Jun 12, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

import java.util.List;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.RegisteredHardwareDTO;
import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.impl.multicast.ReCMultiCastController;
import com.linkare.rec.impl.utils.mapping.DTOMapperUtils;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class MultiCastController implements IMultiCastControllerMXBean, NotificationEmitter {

	private final ReCMultiCastController reCMultiCastController;

	private final NotificationManager notificationManager;

	public MultiCastController(final ReCMultiCastController reCMultiCastController) {
		this.reCMultiCastController = reCMultiCastController;
		notificationManager = NotificationManager.getInstance();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<HardwareInfoDTO> getHardwares() {
		return DTOMapperUtils.mapToHardwareInfoDTOList(reCMultiCastController.unsecureEnumerateHardware());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ClientInfoDTO> getClients() {
		return DTOMapperUtils.mapToClientInfoDTOList(reCMultiCastController.getClientList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<RegisteredHardwareDTO> getRegisteredHardwaresInfo() {
		return DTOMapperUtils.mapToRegisteredHardwareInfoDTOList(reCMultiCastController.getRegisteredHardwareInfo());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws IllegalArgumentException {
		notificationManager.addNotificationListener(listener, filter, handback);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
		notificationManager.removeNotificationListener(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MBeanNotificationInfo[] getNotificationInfo() {
		return notificationManager.getNotificationInfo();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback)
			throws ListenerNotFoundException {
		notificationManager.removeNotificationListener(listener, filter, handback);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public long getUpTimeInMillis() {
		return notificationManager.getUptime();
	}

}
