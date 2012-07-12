/*
 * RobotServer.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.driver.voscilador;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author RF
 */
public class ServerMain {
	private static final Logger LOGGER=Logger.getLogger(ServerMain.class.getName());

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			new BaseHardware(new OsciladorDriver());

			try {
				Thread.currentThread().join();
			} catch (final Exception ignored) {
			}

			ORBBean.getORBBean().killORB();

		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE,"Error on ServerMain...", e);
		}
	}
}
