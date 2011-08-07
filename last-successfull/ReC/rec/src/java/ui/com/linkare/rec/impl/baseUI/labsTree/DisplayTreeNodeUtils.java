/*
 * DisplayTreeNodeUtils.java
 *
 * Created on August 11, 2004, 1:18 AM
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig;
import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.Lab;

public abstract class DisplayTreeNodeUtils {
	/** Disable all the nodes in a lab */
	public static void disableLab(final Lab lab) {
		if (lab == null) {
			return;
		}

		lab.setEnabled(false);
		final Apparatus[] apps = lab.getApparatus();
		for (final Apparatus app : apps) {
			app.setEnabled(false);
			app.setConnected(false);

			final Display[] displays = app.getDisplay();
			for (int d = 0; d < displays.length; d++) {
				if (!displays[d].getOfflineCapable()) {
					displays[d].setEnabled(false);
				}
			}

			final DefaultAcquisitionConfig[] dfacq = app.getDefaultAcquisitionConfig();
			for (final DefaultAcquisitionConfig element : dfacq) {
				element.setEnabled(false);
			}
		}
	}

	/** Disable all the nodes in a lab */
	public static void disableAllApparatus(final Lab lab) {
		if (lab == null) {
			return;
		}

		final Apparatus[] apps = lab.getApparatus();
		for (final Apparatus app : apps) {
			final Display[] displays = app.getDisplay();
			for (int d = 0; d < displays.length; d++) {
				if (!displays[d].getOfflineCapable()) {
					displays[d].setEnabled(false);
				}
			}

			final DefaultAcquisitionConfig[] dfacq = app.getDefaultAcquisitionConfig();
			for (final DefaultAcquisitionConfig element : dfacq) {
				element.setEnabled(false);
			}

			app.setEnabled(false);
			app.setConnected(false);
		}
	}

	public static void enableAllApparatusContents(final Apparatus app) {
		final com.linkare.rec.impl.baseUI.config.Display[] dps = app.getDisplay();
		app.setConnected(true);
		for (final Display dp : dps) {
			dp.setEnabled(true);
		}

		final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig[] dftacq = app.getDefaultAcquisitionConfig();
		for (final DefaultAcquisitionConfig element : dftacq) {
			element.setEnabled(true);
		}
	}

	public static void disableAllApparatusContents(final Apparatus app) {
		final com.linkare.rec.impl.baseUI.config.Display[] dps = app.getDisplay();
		app.setConnected(false);
		for (final Display dp : dps) {
			dp.setEnabled(false);
		}

		final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig[] dftacq = app.getDefaultAcquisitionConfig();
		for (final DefaultAcquisitionConfig element : dftacq) {
			element.setEnabled(false);
		}
	}
}
