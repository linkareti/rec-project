package pt.utl.ist.elab.driver.optica;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author npadriano
 */
public class ServerMain {

	private static String OPTICA_HARDWARE_LOGGER = "OpticaHardware.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(OPTICA_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(OPTICA_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean();

			new BaseHardware(new OpticaDriver());

			try {
				Thread.currentThread().join();
			} catch (Exception ignored) {
			}

			ORBBean.getORBBean().killORB();
		} catch (Exception e) {
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(OPTICA_HARDWARE_LOGGER));
		}
	}

}
