//TODO - Migrate the java source file to the correct package and declare the package here as required.
//package @experiment.package@;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * @author José Pedro Pereira - Linkare TI
 */
public class ServerMain {

	private static final Logger LOGGER=Logger.getLogger(ServerMain.class.getName());

	/**
	 * @param args the command line arguments
	 */
	public static void main(final String[] args) {
		try {
			ORBBean.getORBBean();
			
			//TODO - Uncomment the next line and define the correct driver class
			//new BaseHardware(new mag3dDriver());

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
