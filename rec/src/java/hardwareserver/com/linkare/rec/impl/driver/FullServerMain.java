/* 
 * FullServerMain.java created on Jul 23, 2012
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.driver;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.threading.ProcessingManager;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Gedsimon Pereira - Linkare TI
 */
public class FullServerMain {

	private static final Logger LOGGER = Logger.getLogger(FullServerMain.class.getName());
	private static String SYS_EXPERIMENT_DRIVER_CLASS = "experiment.driver.class";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean();
			String driverClassName = System.getProperty(SYS_EXPERIMENT_DRIVER_CLASS);
			if (driverClassName != null) {
				Class<?> driverClass = Class.forName(driverClassName);
				new BaseHardware((IDriver) driverClass.newInstance());

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
