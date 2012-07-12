package pt.utl.ist.elab.driver.rollpaper;

 import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * 
 * @author Bruno Catarino - Linkare TI
 */
public class ServerMain {

	private static final Logger LOGGER=Logger.getLogger(ServerMain.class.getName());

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(final String[] args) {
	try {
	    ORBBean.getORBBean();

	    new BaseHardware(new RollPaperDriver());

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
