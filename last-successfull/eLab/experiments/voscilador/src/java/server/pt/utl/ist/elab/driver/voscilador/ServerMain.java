/*
 * RobotServer.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.driver.voscilador;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author RF
 */
public class ServerMain {
	// O código desta classe é sempre igual!!! Alterar só os nomes para o vosso
	// caso!
	private static String Oscilador_HARDWARE_LOGGER = "Oscilador.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.Oscilador_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.Oscilador_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new OsciladorDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.Oscilador_HARDWARE_LOGGER));
		}
	}
}
