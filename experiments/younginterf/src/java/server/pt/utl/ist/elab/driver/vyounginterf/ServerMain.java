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
		Logger l = LogManager.getLogManager().getLogger(YOUNGINTERF_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(YOUNGINTERF_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new YoungInterfDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(YOUNGINTERF_HARDWARE_LOGGER));
		}
	}
}
