/* 
 * MultiCastExperimentStats.java created on 1 Nov 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.data.metadata.HardwareInfo;
import com.linkare.rec.impl.config.ReCSystemProperty;
import com.linkare.rec.impl.data.FrequencyUtil;

/**
 * 
 * @author npadriano
 */
public class MultiCastExperimentStats implements Serializable {

	/** Generated UID */
	private static final long serialVersionUID = -2442955578118794705L;

	private static final Logger LOGGER = Logger.getLogger(MultiCastExperimentStats.class.getName());

	private static String baseDir = null;
	static {
		MultiCastExperimentStats.baseDir = ReCSystemProperty.USER_DIR.getValue()
				+ ReCSystemProperty.FILE_SEPARATOR.getValue() + "ExperimentStats";
		java.io.File f = new java.io.File(MultiCastExperimentStats.baseDir);
		if (!f.exists()) {
			f.mkdirs();
		}
		f = null;
	}

	private final String hardwareUniqueId;
	private final long lockPeriod;

	private long totalNumberOfExecutions = 0;
	private long runningAverageTimeOfExecution = 0;
	private long totalLockEventsSent = 0;
	private transient long experimentStartTimestamp = 0;

	/**
	 * Creates the <code>MultiCastExperimentStats</code>.
	 * 
	 * @param hardwareInfo
	 * @param lockPeriod
	 */
	private MultiCastExperimentStats(final HardwareInfo hardwareInfo, final long lockPeriod) {
		this.lockPeriod = lockPeriod;
		hardwareUniqueId = hardwareInfo.getHardwareUniqueID();

		LOGGER.log(Level.INFO, "Stats instantiated for hardware " + hardwareUniqueId + " with lock period "
				+ lockPeriod);

		runningAverageTimeOfExecution = FrequencyUtil.getMaximumExperimentTime(hardwareInfo);
	}

	public synchronized void lockEventSent() {
		totalLockEventsSent++;
	}

	public synchronized void startExperimentStats() {
		if (experimentStartTimestamp == 0) {
			experimentStartTimestamp = System.currentTimeMillis();
		}
	}

	public synchronized void stopExperimentStats() {
		if (experimentStartTimestamp > 0) {
			final long totalTimeOfCurrentExecution = System.currentTimeMillis() - experimentStartTimestamp;
			experimentStartTimestamp = 0;

			runningAverageTimeOfExecution = (runningAverageTimeOfExecution * totalNumberOfExecutions + totalTimeOfCurrentExecution)
					/ (totalNumberOfExecutions + 1);

			totalNumberOfExecutions++;
		}
	}

	public synchronized long calcAverageExecutionTime() {
		final double averageUsedLocks = (totalLockEventsSent == 0 ? 1 : ((double) totalNumberOfExecutions)
				/ ((double) totalLockEventsSent));
		// long averageExecutionTime = (long) (runningAverageTimeOfExecution *
		// averageUsedLocks);
		final long averageExecutionTime = (long) (runningAverageTimeOfExecution * averageUsedLocks) + lockPeriod;

		return averageExecutionTime;
	}

	public synchronized void shutdown() {
		LOGGER.log(Level.INFO, "Stats shutdown for hardware " + hardwareUniqueId);
		writeObject();
	}

	/**
	 * @param hardwareInfo
	 * @param lockPeriod
	 * @return
	 */
	public static MultiCastExperimentStats getInstance(final HardwareInfo hardwareInfo, final long lockPeriod) {
		final File file = new File(MultiCastExperimentStats.getFileName(hardwareInfo.getHardwareUniqueID()));
		if (file.exists()) {
			try {
				LOGGER.log(Level.FINE, "Getting stats instance from file.");
				return MultiCastExperimentStats.readObject(file);
			} catch (final Exception e) {
				e.printStackTrace();
				LOGGER.log(Level.WARNING,
						"Exception ocurred reading serialized stats object. Message: " + e.getMessage());
			}
		}
		LOGGER.log(Level.FINE, "Getting new stats instance.");
		return new MultiCastExperimentStats(hardwareInfo, lockPeriod);
	}

	private static String getFileName(final String hardwareUniqueId) {
		return MultiCastExperimentStats.baseDir + ReCSystemProperty.FILE_SEPARATOR.getValue() + hardwareUniqueId
				+ ".ser";
	}

	private void writeObject() {
		final File file = new File(MultiCastExperimentStats.getFileName(hardwareUniqueId));
		LOGGER.log(Level.FINE, "Writing stats to file " + file.getAbsoluteFile());
		if (file.exists()) {
			file.delete();
		}

		FileObjectOutputStream oos;
		try {
			oos = new FileObjectOutputStream(file);
			oos.writeObject(this);
			oos.flush();
			oos.close();
		} catch (final IOException e) {
			e.printStackTrace();
			LOGGER.log(Level.WARNING, "Exception ocurred writing serialized stats object. Message: " + e.getMessage());
		}
	}

	private static MultiCastExperimentStats readObject(final File file) throws IOException, ClassNotFoundException {
		LOGGER.log(Level.FINE, "Reading stats from file " + file.getAbsoluteFile());

		final FileObjectInputStream ois = new FileObjectInputStream(file);
		final Object o = ois.readObject();
		ois.close();
		return (MultiCastExperimentStats) o;
	}

}