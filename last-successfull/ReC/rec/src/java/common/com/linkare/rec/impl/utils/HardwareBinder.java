/*
 * ReferenceBinder.java
 *
 * Created on 16 de Janeiro de 2003, 11:38
 */

package com.linkare.rec.impl.utils;

import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.MultiCastController;
import com.linkare.rec.acquisition.MultiCastControllerHelper;
import com.linkare.rec.impl.wrappers.MultiCastControllerWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareBinder extends Thread {
	public static final String SYSPROP_MULTICAST_INIT_REF = "ReC.MultiCastController.InitRef";
	public static final String MULTICAST_INIT_REF = Defaults.defaultIfEmpty(
			System.getProperty(HardwareBinder.SYSPROP_MULTICAST_INIT_REF), "MultiCastController");

	private Hardware hardware = null;
	private final long WAIT_PERIOD = 40000;

	// Before trying to register please wait some time, to send the correct ID
	// to the multicast...
	private final long STARTUP_PERIOD = 30000;

	/** Creates a new instance of DirectoryCreator */
	public HardwareBinder() {
		super("Hardware Binder Thread...");
		setDaemon(true);
		setPriority(Thread.NORM_PRIORITY - 2);
		start();
	}

	@Override
	public void run() {

		synchronized (this) {
			try {
				this.wait(STARTUP_PERIOD);
			} catch (final InterruptedException ignored) {
				return;
			}
		}
		while (true) {
			bindHardware();
			synchronized (this) {
				try {
					this.wait(WAIT_PERIOD);
				} catch (final InterruptedException ignored) {
					return;
				}
			}
		}
	}

	public void bindHardware() {
		try {
			final MultiCastController mcc = MultiCastControllerHelper.narrow(ORBBean.getORBBean().getORB()
					.resolve_initial_references(HardwareBinder.MULTICAST_INIT_REF));

			/** Andre added the MultiCastControllerWrapper...check with JP! */
			final MultiCastControllerWrapper mcw = new MultiCastControllerWrapper(mcc);
			mcw.registerHardware(hardware);

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	public void setHardware(final Hardware hardware) {
		this.hardware = hardware;
	}
}
