/*
 * ServerMain.java
 *
 * Created on 27 de Fevereiro de 2005, 20:07
 */

package pt.utl.ist.elab.virtual.driver.poisson;

import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;

/**
 * 
 * @author andre
 * 
 */
public class ServerMain {

	private static String POISSON_HARDWARE_LOGGER = "Poisson.Logger";
	static {
		Logger l = LogManager.getLogManager().getLogger(POISSON_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(POISSON_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new PoissonDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(POISSON_HARDWARE_LOGGER));
		}
	}
}
