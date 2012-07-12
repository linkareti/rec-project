package pt.utl.ist.elab.driver.vmovproj;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author nomead
 */
public class ServerMain {

	private static final Logger LOGGER=Logger.getLogger(ServerMain.class.getName());

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();

			new BaseHardware(new MovProjDriver());

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
