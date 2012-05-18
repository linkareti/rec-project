/*
 * ServerMain.java
 *
 * Created on 27 de Fevereiro de 2005, 20:07
 */

package pt.utl.ist.elab.driver.rollpaper;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Antonio J. R. Figueiredo Last Review : 6/04/2005
 */
public class ServerMain {

	private static String RollPaper_HARDWARE_LOGGER = "RollPaper.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.RollPaper_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.RollPaper_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new RollPaperDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.RollPaper_HARDWARE_LOGGER));
		}
	}
}
