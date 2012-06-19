/*
 * ServerMain.java
 *
 * Created on 27 de Fevereiro de 2005, 20:07
 */

package pt.utl.ist.elab.driver.vpend2m;

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

	private static String Pend2M_HARDWARE_LOGGER = "Pend2M.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.Pend2M_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.Pend2M_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new Pend2MDriver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.Pend2M_HARDWARE_LOGGER));
		}
	}
}
