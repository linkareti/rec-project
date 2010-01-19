/*
 * ServerMain.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.driver.vcartpole;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author nomead
 */
public class ServerMain {

	private static String CartPole_HARDWARE_LOGGER = "CartPole.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(CartPole_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(CartPole_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new CartPoleDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(CartPole_HARDWARE_LOGGER));
		}
	}
}
