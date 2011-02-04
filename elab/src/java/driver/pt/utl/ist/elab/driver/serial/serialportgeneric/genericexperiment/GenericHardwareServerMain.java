/*
 * GenericHardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.driver.IDriver;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * Please, replace this class by your own class if you don't want such a generic
 * class that recieves a stamp driver class name through an argument.
 * 
 * @author José Pedro Pereira - Linkare TI
 * @version 0.2 : March 2009
 */
public abstract class GenericHardwareServerMain {

	private static String HARDWARE_LOGGER = "GenericHardware.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args : ORB Bean to be loaded
	 */
	public static void main(String[] args) {
		try {
			Logger.getLogger(HARDWARE_LOGGER).log(Level.INFO, "Starting Driver");
			ORBBean.getORBBean();
			@SuppressWarnings("unused")
			BaseHardware baseHardware = new BaseHardware((IDriver) new GenericSerialPortDriver());
			Thread.currentThread().join();

		} catch (Exception e) {
			e.printStackTrace();
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(HARDWARE_LOGGER));
		}
	}

}
