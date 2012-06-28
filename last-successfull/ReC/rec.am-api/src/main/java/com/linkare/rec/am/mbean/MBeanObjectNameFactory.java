/* 
 * MBeanObjectNameFactory.java created on Mar 22, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.am.mbean;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class MBeanObjectNameFactory {

    private enum DomainsEnum {
	DEFAULT_DOMAIN("com.linkare.rec");

	private final String domain;

	private DomainsEnum(final String domain) {
	    this.domain = domain;
	}

	public String getDomain() {
	    return domain + ":";
	}
    }

    /**
     * Single point to define MBean names
     * 
     * @author Artur Correia - Linkare TI
     */
    private enum MBeansNameEnum {
	THREADPOOLEXECUTOR_STATISTICS("ThreadPoolExecutorStatistics"), HARDWARE("Hardware"), MULTICAST_CONTROLLER("RecMultiCastController");

	private static final String NAME_ATTRIBUTE = "name";

	private final String mbeanName;

	private MBeansNameEnum(final String mbeanName) {
	    this.mbeanName = new StringBuilder(MBeansNameEnum.NAME_ATTRIBUTE).append(EQUAL).append(mbeanName).toString();
	}

	public String getMBeanName() {
	    return mbeanName;
	}
    }

    private static final String ID = "id";
    private static final String COMMA_SEPARATOR = ",";
    private static final String EQUAL = "=";

    private static final ObjectName THREADPOOLEXECUTOR_STATISTICS_OBJECT_NAME;
    private static final ObjectName MULTICAST_CONTROLLER_OBJECT_NAME;

    private static final String HARDWARE_BASE_NAME;

    static {
	try {
	    THREADPOOLEXECUTOR_STATISTICS_OBJECT_NAME = new ObjectName(DomainsEnum.DEFAULT_DOMAIN.getDomain()
		    + MBeansNameEnum.THREADPOOLEXECUTOR_STATISTICS.getMBeanName());

	    MULTICAST_CONTROLLER_OBJECT_NAME = new ObjectName(DomainsEnum.DEFAULT_DOMAIN.getDomain()
		    + MBeansNameEnum.MULTICAST_CONTROLLER.getMBeanName());

	} catch (MalformedObjectNameException e) {
	    throw new RuntimeException(e);
	}

	HARDWARE_BASE_NAME = new StringBuilder(DomainsEnum.DEFAULT_DOMAIN.getDomain()).append(MBeansNameEnum.HARDWARE.getMBeanName()).append(COMMA_SEPARATOR)
										      .append(ID).append(EQUAL).toString();
    }

    public static ObjectName getThreadPoolExecutorStatisticsObjectName() {
	return THREADPOOLEXECUTOR_STATISTICS_OBJECT_NAME;

    }

    public static ObjectName getMultiCastControllerObjectName() {
	return MULTICAST_CONTROLLER_OBJECT_NAME;
    }

    public static ObjectName getHardwareObjectName(final String hardwareUniqueID) {
	try {
	    return new ObjectName(HARDWARE_BASE_NAME + hardwareUniqueID);
	} catch (final MalformedObjectNameException e) {
	    throw new RuntimeException(e);
	}
    }

}
