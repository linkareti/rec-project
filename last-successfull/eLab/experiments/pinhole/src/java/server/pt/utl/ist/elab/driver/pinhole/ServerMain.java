package pt.utl.ist.elab.driver.pinhole;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 */
public class ServerMain {

	private static String PINHOLE_HARDWARE_LOGGER = "PinholeHardware.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.PINHOLE_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.PINHOLE_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			new BaseHardware(new PinholeDriver());

			try {
				Thread.currentThread().join();
			} catch (final Exception ignored) {
			}

			ORBBean.getORBBean().killORB();
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.PINHOLE_HARDWARE_LOGGER));
		}
	}

}
