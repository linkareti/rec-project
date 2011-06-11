package pt.utl.ist.elab.driver.aleatorio;

/*
 * ServerMain.java
 *
 * Created on 4 de Junho de 2003, 16:47
 */

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Pedro Carvalho - LEFT - IST
 */
public class ServerMain {

	private static String ALEATORIO_HARDWARE_LOGGER = "Aleatorio.Logger";
	static {
		final Logger l = LogManager.getLogManager().getLogger(ServerMain.ALEATORIO_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(ServerMain.ALEATORIO_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();
			new BaseHardware(new AleatorioDriver());

			try {
				Thread.currentThread().join();
			} catch (final Exception ignored) {
			}

			ORBBean.getORBBean().killORB();
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(ServerMain.ALEATORIO_HARDWARE_LOGGER));
		}
		/*
		 * AleatorioDriver aleatorioDriver = new AleatorioDriver();
		 * aleatorioDriver
		 * .init((HardwareInfo)aleatorioDriver.getHardwareInfo());
		 */
	}

}// ServerMain.class
