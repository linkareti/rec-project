/*
 * HardwareBinder.java
 *
 * Created on 16 de Janeiro de 2003, 11:38
 */

package com.linkare.rec.impl.utils;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.acquisition.HardwareState;
import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.threading.ProcessingManager;
import com.linkare.rec.impl.wrappers.MultiCastControllerWrapper;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareBinder implements Runnable {
	public static final String MULTICAST_INIT_REF = ReCSystemProperty.MULTICAST_INITREF.getValue();

	private static final Logger LOGGER = Logger.getLogger(HardwareBinder.class.getName());

	private Hardware hardware = null;

	private final long WAIT_PERIOD = 40000;

	private ScheduledFuture<?> hardwareBinderTask = null;

	private ReadWriteLock rwLock = new ReentrantReadWriteLock(true);

	/** Creates a new instance of HardwareBinder */
	public HardwareBinder() {

	}

	@Override
	public void run() {
		try {
			rwLock.readLock().lock();

			if (hardware == null) {
				LOGGER.log(Level.FINE,
						"The hardware binder task is going to be cancelled as it has no hardware reference to bind...");

				try {
					// upgrade to write lock - still have to release readLock
					rwLock.readLock().unlock();
					rwLock.writeLock().lock();

					if (hardwareBinderTask != null) {
						hardwareBinderTask.cancel(true);
						hardwareBinderTask = null;
					}

				} finally {
					// downgrade by aquiring read lock before releasing write
					// lock
					rwLock.readLock().lock();
					rwLock.writeLock().unlock();
				}
			} else {
				HardwareInfo info = hardware.getHardwareInfo();
				if (info == null || info.getHardwareUniqueID() == null) {
					LOGGER.log(Level.FINE,
							"Hardware does not have hardwareInfo or the unique hardware id is null... Not binding to multicast!");
				} else if (hardware.getHardwareState() == HardwareState.UNKNOWN) {
					LOGGER.log(Level.FINE, "Hardware state is 'UNKNOWN'... Not binding to multicast!");

				} else {
					try {
						final MultiCastControllerWrapper mcw = ORBBean.getORBBean().resolveMultiCast();
						mcw.registerHardware(hardware);

					} catch (final Exception e) {
						LOGGER.log(Level.WARNING,
								"Problem registering hardware with multicast controller" + e.getMessage(), e);
					}
				}
			}
		} finally {
			rwLock.readLock().unlock();
		}

	}

	public void setHardware(final Hardware hardware) {
		try {
			rwLock.readLock().lock();
			if (this.hardware == hardware) {
				return;
			}
			try {
				// upgrade to writeLock requires releasing the readLock first
				rwLock.readLock().unlock();
				rwLock.writeLock().lock();

				if (hardwareBinderTask != null) {
					hardwareBinderTask.cancel(true);
					hardwareBinderTask = null;
				}

				this.hardware = hardware;

				if (this.hardware != null) {
					hardwareBinderTask = ProcessingManager.getInstance().scheduleAtFixedRate(this, 0, WAIT_PERIOD,
							TimeUnit.MILLISECONDS);
				}
			} finally {
				// downgrade by aquiring readlock before releasing the
				// writeLock...
				rwLock.readLock().lock();
				rwLock.writeLock().unlock();
			}
		} finally {
			rwLock.readLock().unlock();
		}

	}
}
