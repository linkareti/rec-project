/*
 * Deactivator.java
 *
 * Created on 5 de Novembro de 2002, 15:40
 */

package com.linkare.rec.impl.utils;

import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Deactivator extends Thread {
	private Deactivatable deactivatable = null;
	private final long DEACTIVATION_TIME = 40000;
	private boolean deactivated = false;

	private Logger logger = null;

	/**
	 * Creates a new instance of Deactivator
	 * 
	 * @param deactivatable
	 */
	public Deactivator(final Deactivatable deactivatable) {
		super("Deactivator Thread...");
		setDaemon(true);
		setPriority(Thread.NORM_PRIORITY - 2);
		start();

		this.deactivatable = deactivatable;
	}

	/**
	 * Creates a new instance of Deactivator
	 * 
	 * @param deactivatable
	 * @param logger
	 */
	public Deactivator(final Deactivatable deactivatable, final Logger logger) {
		this(deactivatable);

		this.logger = logger;
	}

	@Override
	public void run() {

		synchronized (this) {
			try {
				this.wait(DEACTIVATION_TIME);
			} catch (final InterruptedException ignored) {
				return;
			}
		}
		while (!deactivated) {
			synchronized (this) {
				try {
					this.wait(DEACTIVATION_TIME);
				} catch (final InterruptedException ignored) {
					return;
				}
			}

			tryToDeactivate();
		}

		// System.out.println("***********DEACTIVATOR ENDED ITS JOB***************");
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
		} catch (final Exception e) {
			deactivated = false;
			LoggerUtil.logThrowable("Exception while trying to deactivate.", e, logger);
		}
	}
}
