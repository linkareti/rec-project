/*
 * ServerMain.java
 *
 * Created on 3 de Abril de 2005, 05:55
 */

package pt.utl.ist.elab.driver.vbs;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Pedro Queiro'
 */
public class ServerMain {
	// O codigo desta classe e' sempre igual!!! Alterar so' os nomes para o
	// vosso caso!
	private static String BS_HARDWARE_LOGGER = "BS.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(BS_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(BS_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new BSDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(BS_HARDWARE_LOGGER));
		}
	}
}