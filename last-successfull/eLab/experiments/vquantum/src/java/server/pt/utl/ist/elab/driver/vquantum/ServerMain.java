/*
 * ServerMain.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.driver.vquantum;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author nomead
 */
public class ServerMain {

	private static String Quantum_HARDWARE_LOGGER = "Quantum.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(Quantum_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(Quantum_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean();

			BaseHardware baseHardware = new BaseHardware(new QuantumDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean().killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(Quantum_HARDWARE_LOGGER));
		}
	}
}
