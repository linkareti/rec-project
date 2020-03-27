/* 
 * FullServerMain.java created on Jul 23, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.driver;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.config.ReCSystemPropertyLocation;
import com.linkare.rec.impl.threading.ProcessingManager;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.SystemExitSecurityManager;
import com.linkare.rec.utils.ClassUtils;

/**
 * 
 * @author Gedsimon Pereira - Linkare TI
 */
public class FullServerMain {

	private static final Logger LOGGER = Logger.getLogger(FullServerMain.class.getName());

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setSecurityManager(new SystemExitSecurityManager());
		try {
			Set<ReCSystemProperty> listRequiredNotDefined = ReCSystemProperty
					.listRequiredNotDefined(ReCSystemPropertyLocation.HARDWARE);
			if (listRequiredNotDefined.size() > 0) {
				System.out.println("The following system properties are required and where not defined:");
				for (ReCSystemProperty reCSystemProperty : listRequiredNotDefined) {
					System.out.println(reCSystemProperty.toString());
				}
				System.exit(-1);
			}

			ORBBean.getORBBean();
			String driverClassName = ReCSystemProperty.HARDWARE_DRIVER_CLASS.getValue();
			if (driverClassName != null) {                            
				Class<?> driverClass = ClassUtils.findClass(driverClassName, FullServerMain.class.getClassLoader());
				new BaseHardware((IDriver) driverClass.getDeclaredConstructor().newInstance());

				try {
					Thread.currentThread().join();
				} catch (final Exception ignored) {
				}
				ORBBean.getORBBean().killORB();
			} else {
				LOGGER.log(Level.SEVERE, "Driver Class not defined...");
			}

		} catch (final Exception e) {
			e.printStackTrace();
			LOGGER.log(Level.SEVERE, "Error on FullServerMain...", e);
		} finally {
			ProcessingManager.getInstance().shutdown();
		}

	}
}
