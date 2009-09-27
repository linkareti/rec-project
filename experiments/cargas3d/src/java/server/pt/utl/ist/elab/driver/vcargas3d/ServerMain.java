/*
 * ServerMain.java
 *
 * Created on 22 de Mar�o de 2005, 14:38
 */

package pt.utl.ist.elab.driver.vcargas3d;

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

	private static String Cargas3D_HARDWARE_LOGGER = "Cargas3D.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(Cargas3D_HARDWARE_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(Cargas3D_HARDWARE_LOGGER));
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		try {
			ORBBean.getORBBean(args);

			BaseHardware baseHardware = new BaseHardware(new Cargas3DDriver());

			Thread.currentThread().join();

		} catch (Exception e) {
			ORBBean.getORBBean(args).killORB();
			LoggerUtil.logThrowable("Error on Main...", e, Logger.getLogger(Cargas3D_HARDWARE_LOGGER));
		}
		// TODO code application logic here
	}
}