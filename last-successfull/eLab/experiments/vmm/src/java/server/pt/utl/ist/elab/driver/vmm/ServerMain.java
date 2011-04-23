/*
 * RobotServer.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.driver.vmm;

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
	// O código desta classe é sempre igual!!! Alterar só os nomes para o vosso
	// caso!
	private static String MM_HARDWARE_LOGGER = "MM.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(MM_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(MM_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean();

			BaseHardware baseHardware = new BaseHardware(new MMDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(MM_HARDWARE_LOGGER));
		}
	}
}
