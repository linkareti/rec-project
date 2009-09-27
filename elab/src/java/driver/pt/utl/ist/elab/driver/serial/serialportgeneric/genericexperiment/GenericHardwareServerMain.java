/*
 * GenericHardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

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
 * @author Francisco Dias - Linkare TI, also had passed here on March 2009
 * @version 0.2 : March 2009
 */
public abstract class GenericHardwareServerMain {

	// TODO : please, replace this values
	private static String HARDWARE_LOGGER = "GenericHardware.Logger";
	private static String HARDWARE_UNIQUE_ID = "UNIQUE_ID_HERE";

	static {
		Logger l = LogManager.getLogManager().getLogger(HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args[0] : ORB Bean to be loaded
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);
			BaseHardware baseHardware = new BaseHardware((IDriver) new GenericSerialPortDriver());
			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(HARDWARE_LOGGER));
		}
	}

}
