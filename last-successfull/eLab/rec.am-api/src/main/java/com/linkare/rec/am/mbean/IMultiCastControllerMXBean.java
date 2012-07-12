/* 
 * IMultiCastControllerMXBean.java created on Jun 12, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.am.mbean;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MXBean;

import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;
import com.linkare.rec.am.RegisteredHardwareDTO;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@MXBean
public interface IMultiCastControllerMXBean {

    List<HardwareInfoDTO> getHardwares();

    List<ClientInfoDTO> getClients();

    Map<String, RegisteredHardwareDTO> getRegisteredHardwaresInfo(final List<String> hardwareUniqueIDs);

    long getUpTimeInMillis();

    void kickUsers(final Set<String> usersToKick, final String hardwareUniqueID);


}
