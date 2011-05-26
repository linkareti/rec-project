/*
 * ServerMain.java
 *
 * Created on 3 de Abril de 2005, 05:55
 */

package pt.utl.ist.elab.driver.vdi;

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
	private static String DI_HARDWARE_LOGGER = "DI.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.DI_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.DI_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new DIDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.DI_HARDWARE_LOGGER));
		}
	}
}