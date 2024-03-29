package pt.utl.ist.elab.driver.decay;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;
/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ServerMain {

	private static final Logger LOGGER = Logger.getLogger(ServerMain.class.getName());
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			ORBBean.getORBBean();

			//new BaseHardware(new RadioactividadeStampDriver());

			try {
				Thread.currentThread().join();
			} catch (final Exception ignored) {
			}

			ORBBean.getORBBean().killORB();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Error on ServerMain...", e);
		}
	}

}
