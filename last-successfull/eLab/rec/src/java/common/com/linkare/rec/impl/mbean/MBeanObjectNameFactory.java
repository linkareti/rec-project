/* 
 * MBeanObjectNameFactory.java created on Mar 22, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.mbean;

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
			return this.domain + ":";
		}
	}

	/**
	 * Single point to define MBean names
	 * 
	 * @author Artur Correia - Linkare TI
	 */
	private enum MBeansNameEnum {
		THREADPOOLEXECUTORSTATISTICS("ThreadPoolExecutorStatistics");

		private static final String NAME_ATTRIBUTE = "name";

		private final String mbeanName;

		private MBeansNameEnum(final String mbeanName) {
			this.mbeanName = new StringBuilder(NAME_ATTRIBUTE).append("=").append(mbeanName).toString();
		}

		public String getMBeanName() {
			return this.mbeanName;
		}
	}

	public static ObjectName getThreadPoolExecutorStatisticsObjectName() {
		try {
			return new ObjectName(DomainsEnum.DEFAULT_DOMAIN.getDomain()
					+ MBeansNameEnum.THREADPOOLEXECUTORSTATISTICS.getMBeanName());
		} catch (MalformedObjectNameException e) {
			throw new RuntimeException(e);
		}
	}

}
