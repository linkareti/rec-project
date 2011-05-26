/*
 * ServerMain.java
 *
 * Created on ?????
 */

package pt.utl.ist.elab.driver.vyounginterf;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Emanuel Antunes
 */
public class ServerMain {
	// O código desta classe é sempre igual!!! Alterar só os nomes para o vosso
	// caso!
	private static String YOUNGINTERF_HARDWARE_LOGGER = "YoungInterf.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.YOUNGINTERF_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.YOUNGINTERF_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new YoungInterfDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.YOUNGINTERF_HARDWARE_LOGGER));
		}
	}
}
