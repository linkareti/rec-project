/* 
 * HardwareMXBean.java created on Jun 11, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.am.mbean;

import java.util.List;
import java.util.Set;

import javax.management.MXBean;

import com.linkare.rec.am.ClientInfoDTO;
import com.linkare.rec.am.HardwareInfoDTO;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@MXBean
public interface IHardwareMXBean {

    HardwareInfoDTO getHardwareInfo();

    String getState();

    List<ClientInfoDTO> getClientList();

    void kickUsers(final Set<String> usersToKick);

}
