/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

//TODO comentar

package pt.utl.ist.elab.driver.condensador;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author jp
 */
public class ServerMain {

	private static String CONDENSADOR_HARDWARE_LOGGER = "CondensadorHardware.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(CONDENSADOR_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(CONDENSADOR_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new CondensadorStampDriver());

			try {
				Thread.currentThread().join();
			} catch (Exception ignored) {
			}

			ORBBean.getORBBean(args).killORB();
		} catch (Exception e) {
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(CONDENSADOR_HARDWARE_LOGGER));
		}
	}

}
