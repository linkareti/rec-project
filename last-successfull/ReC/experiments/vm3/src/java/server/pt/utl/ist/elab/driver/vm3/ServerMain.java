/*
 * ServerMain.java
 *
 * Created on 17 de Fevereiro de 2005, 11:18
 */

package pt.utl.ist.elab.driver.vm3;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author n0dP2
 */
public class ServerMain {

	private static String M3_HARDWARE_LOGGER = "M3.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.M3_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.M3_HARDWARE_LOGGER));
		}
	}

	/** Creates a new instance of ServerMain */
	public ServerMain() {
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {

		try {
			ORBBean.getORBBean();

			final BaseHardware baseHardware = new BaseHardware(new M3Driver());

			Thread.currentThread().join();

		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.M3_HARDWARE_LOGGER));
		}
		// TODO code application logic here
	}
}
