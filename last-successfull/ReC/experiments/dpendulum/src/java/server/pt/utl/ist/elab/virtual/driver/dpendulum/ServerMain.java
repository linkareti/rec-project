/*
 * RobotServer.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.virtual.driver.dpendulum;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ServerMain {
	private static String DPENDULUM_HARDWARE_LOGGER = "DPendulum.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(DPENDULUM_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DPENDULUM_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new DPendulumDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(DPENDULUM_HARDWARE_LOGGER));
		}
	}
}
