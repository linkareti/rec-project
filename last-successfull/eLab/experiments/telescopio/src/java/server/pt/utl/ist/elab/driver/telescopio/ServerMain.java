/*
 * RobotServer.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.driver.telescopio;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Andr�
 */
public class ServerMain {
	private static String TELESCOPIO_HARDWARE_LOGGER = "Telescopio.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.TELESCOPIO_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.TELESCOPIO_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new TelescopioDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.TELESCOPIO_HARDWARE_LOGGER));
		}
	}
}
