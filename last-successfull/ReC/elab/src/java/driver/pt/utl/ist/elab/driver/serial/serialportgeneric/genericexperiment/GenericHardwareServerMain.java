package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.utils.ORBBean;

/**
 * Please, replace this class by your own class if you don't want such a generic
 * class that recieves a stamp driver class name through an argument.
 * 
 * @author José Pedro Pereira - Linkare TI
 * @version 0.2 : March 2009
 */
public abstract class GenericHardwareServerMain {

	private static final Logger LOGGER = Logger.getLogger(GenericHardwareServerMain.class.getName());

	/**
	 * @param args : ORB Bean to be loaded
	 */
	public static void main(final String[] args) {
		try {
			LOGGER.log(Level.INFO, "Starting Driver");
			ORBBean.getORBBean();

			new BaseHardware(new GenericSerialPortDriver());

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
