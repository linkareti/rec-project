/* 
 * MultiCastController.java created on Jun 12, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils.mbean;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.ListenerNotFoundException;
import javax.management.MBeanNotificationInfo;
import javax.management.NotificationEmitter;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.RegisteredHardwareDTO;
import com.linkare.rec.am.mbean.IHardwareMXBean;
import com.linkare.rec.am.mbean.IMultiCastControllerMXBean;
import com.linkare.rec.am.mbean.MBeanConnectionResourcesUtilities;
import com.linkare.rec.am.mbean.MBeanObjectNameFactory;
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
	public Map<String, RegisteredHardwareDTO> getRegisteredHardwaresInfo(final List<String> hardwareUniqueIDs) {

		Map<String, RegisteredHardwareDTO> result = Collections.emptyMap();

		final Map<String, RegisteredHardwareDTO> mapFromList = getMapFromList();
		if (hardwareUniqueIDs == null) {
			result = mapFromList;
		} else {
			result = new HashMap<String, RegisteredHardwareDTO>(hardwareUniqueIDs.size());
			for (final RegisteredHardwareDTO registeredHardware : mapFromList.values()) {
				result.put(registeredHardware.getHardwareUniqueID(), registeredHardware);
			}
		}
		return result;
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

	private Map<String, RegisteredHardwareDTO> getMapFromList() {

		Map<String, RegisteredHardwareDTO> result = Collections.emptyMap();

		final List<RegisteredHardwareDTO> mapToRegisteredHardwareInfoDTOList = DTOMapperUtils
				.mapToRegisteredHardwareInfoDTOList(reCMultiCastController.getRegisteredHardwareInfo());
		result = new HashMap<String, RegisteredHardwareDTO>(mapToRegisteredHardwareInfoDTOList.size());

		for (final RegisteredHardwareDTO registeredHardwareDTO : mapToRegisteredHardwareInfoDTOList) {
			result.put(registeredHardwareDTO.getHardwareUniqueID(), registeredHardwareDTO);
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void kickUsers(final Set<String> usersToKick, final String hardwareUniqueID) {
		try {
			final IHardwareMXBean mBeanClientProxy = MBeanConnectionResourcesUtilities.getMBeanClientProxy(null,
					MBeanObjectNameFactory.getHardwareObjectName(hardwareUniqueID), IHardwareMXBean.class, true);
			mBeanClientProxy.kickUsers(usersToKick);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


}
