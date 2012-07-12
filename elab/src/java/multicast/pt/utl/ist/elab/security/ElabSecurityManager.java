/*
 * CFNSecurityManager.java
 *
 * Created on October 20, 2004, 3:34 PM
 */

package pt.utl.ist.elab.security;

/**
 *
 * @author André Neto - LEFT - IST
 *
 * It may support sql authentication, but first I will use the CFN mail authentication...
 */

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.multicast.ReCMultiCastHardware;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.ISecurityCommunicator;
import com.linkare.rec.impl.multicast.security.ISecurityManager;
import com.linkare.rec.impl.multicast.security.IUser;
import com.linkare.rec.impl.multicast.security.OperationType;
import com.linkare.rec.impl.multicast.security.ResourceType;

public class ElabSecurityManager implements ISecurityManager {
	private static final Logger LOGGER = Logger.getLogger(ElabSecurityManager.class.getCanonicalName());

	private File logins = null;
	private FileWriter fw = null;

	/** Creates a new instance of CFNSecurityModel */
	public ElabSecurityManager() {
		logins = new File("logins.txt");
	}

	private final String LS = System.getProperty("line.separator");

	@Override
	public boolean authenticate(final IResource resource, final IUser user) {
		LOGGER.log(Level.INFO, "Authenticating " + user.getUserName());

		// First try to authenticate from the members db...
		// String userName = user.getUserName();
		// String pass = "" + new String(user.getAuth()).trim().hashCode();

		/*
		 * GregorianCalendar cal = new GregorianCalendar(); try {
		 * if(resource.getResourceType() == resource.RES_MCCONTROLLER) { fw =
		 * new FileWriter(logins, true); fw.write(userName +
		 * " logged in to lab in: " + cal.get(cal.DAY_OF_MONTH) + "/" +
		 * (cal.get(cal.MONTH) + 1) + "/" + cal.get(cal.YEAR) + " at " +
		 * cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE) + LS );
		 * fw.close(); } } catch(Exception e) {
		 * LoggerUtil.logThrowable("Error writting to file...", e,
		 * Logger.getLogger(ELAB_SECURITY_MANAGER_LOGGER)); }
		 */
		return true;
	}

	@Override
	public boolean authorize(final com.linkare.rec.impl.multicast.security.IResource resource,
			final com.linkare.rec.impl.multicast.security.IUser user,
			final com.linkare.rec.impl.multicast.security.IOperation op) {
		final String userName = user.getUserName();
		if (op.getOperation() == OperationType.OP_START) {
			try {
				final GregorianCalendar cal = new GregorianCalendar();
				fw = new FileWriter(logins, true);
				resource.getResourceType();
				fw.write(userName + " started: "
						+ resource.getProperties().get(ResourceType.MCCONTROLLER.getPropertyKey()) + " in: "
						+ cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/"
						+ cal.get(Calendar.YEAR) + " at " + cal.get(Calendar.HOUR_OF_DAY) + ":"
						+ cal.get(Calendar.MINUTE) + LS);
				fw.close();
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Error writting to file...", e);
			}
		}

		return true;
	}

	public static void main(final String args[]) {
		final String userName = "andre";
		final GregorianCalendar cal = new GregorianCalendar();
		LOGGER.log(
				Level.ALL,
				userName + "logged in to lab in: " + cal.get(Calendar.DAY_OF_MONTH) + "/"
						+ (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR) + " at "
						+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerMultiCastHardware(final List<ReCMultiCastHardware> multiCastHardwares) {
		// this security implementation doesn't require the multicast hardwares
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void registerSecurityCommunicator(final ISecurityCommunicator communicator) {
		// this security implementation doesn't require the multicast hardwares
	}

}
