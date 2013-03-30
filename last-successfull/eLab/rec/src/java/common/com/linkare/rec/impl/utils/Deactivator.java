package com.linkare.rec.impl.utils;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.omg.PortableServer.POAPackage.ObjectNotActive;

import com.linkare.rec.impl.threading.ExecutorScheduler;
import com.linkare.rec.impl.threading.ScheduledWorkUnit;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Deactivator extends ScheduledWorkUnit {

	private Deactivatable deactivatable = null;
	private final long DEACTIVATION_TIME = 40000;
	private boolean deactivated = false;

	/**
	 * Creates a new instance of Deactivator
	 * 
	 * @param deactivatable
	 */
	public Deactivator(final Deactivatable deactivatable) {
		this.deactivatable = deactivatable;

		ExecutorScheduler.scheduleAtFixedRate(this, DEACTIVATION_TIME, DEACTIVATION_TIME, TimeUnit.MILLISECONDS);
	}

	@Override
	public void run() {
		tryToDeactivate();
		if (deactivated) {
			shutdown();
		}
	}

	public void tryToDeactivate() {
		if (!deactivatable.isDeactivationPossible()) {
			return;
		}

		if (deactivatable.getOID() == null) {
			// System.out.println("deactivatable has an object id = null... returning");
			return;
		}
		final byte[] oid = deactivatable.getOID().getBytes();
		try {
			/*
			 * System.out.println("************************************");
			 * System.out.println("************************************");
			 * System.out.println("Going to deactivate object with ID=" + (new
			 * String(oid)) + " in POA "+deactivatable.getPOA().the_name());
			 */
			deactivatable.getPOA().deactivate_object(oid);
			/*
			 * System.out.println("Apparently deactivated object with ID=" +
			 * (new String(oid)) +
			 * " in POA "+deactivatable.getPOA().the_name());
			 * System.out.println("************************************");
			 * System.out.println("************************************");
			 */
			deactivated = true;
		} catch(final ObjectNotActive e) {
			deactivated = true;
			LOGGER.log(Level.SEVERE, "Exception while trying to deactivate. Object was not active anymore, so not trying to deactivate it again!", e);
		} catch (final Exception e) {
			deactivated = false;
			LOGGER.log(Level.SEVERE, "Exception while trying to deactivate.", e);
		}
	}
}
