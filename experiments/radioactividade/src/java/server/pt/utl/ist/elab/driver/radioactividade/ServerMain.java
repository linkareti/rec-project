/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.radioactividade;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ServerMain {

	private static String RADIOACTIVIDADE_HARDWARE_LOGGER = "RadioactividadeHardware.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.RADIOACTIVIDADE_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.RADIOACTIVIDADE_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new RadioactividadeStampDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil
					.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.RADIOACTIVIDADE_HARDWARE_LOGGER));
		}
	}

}
