package pt.utl.ist.elab.driver.vcargas3d;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author n0dP2
 */
public class ServerMain {

	private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {

		try {
			ORBBean.getORBBean();

			new BaseHardware(new Cargas3DDriver());

			Thread.currentThread().join();
		} catch (final Exception e) {
			ORBBean.getORBBean().killORB();
			LOGGER.log(Level.SEVERE, "Error on Main...", e);
		}
	}
}