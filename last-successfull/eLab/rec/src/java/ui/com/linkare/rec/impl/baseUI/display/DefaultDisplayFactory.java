/*
 * DefaultDisplayFactory.java
 *
 * Created on August 2, 2004, 5:07 PM
 */

package com.linkare.rec.impl.baseUI.display;

/**
 *
 * @author André Neto - LEFT - IST
 */

import java.util.Vector;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.logging.LoggerUtil;

public class DefaultDisplayFactory extends AbstractDisplayFactory {
	private static String UI_CLIENT_LOGGER = "ReC.baseUI";

	static {
		final Logger l = LogManager.getLogManager().getLogger(DefaultDisplayFactory.UI_CLIENT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DefaultDisplayFactory.UI_CLIENT_LOGGER));
		}
	}

	/** Creates a new instance of DefaultDisplayFactory */
	public DefaultDisplayFactory() {
	}

	@Override
	public void destroyDisplays() {// Big silent noop
	}

	@Override
	public com.linkare.rec.impl.client.experiment.ExpDataDisplay[] getDisplays() {
		final Display[] displays = getInitDisplays();
		final Vector<ExpDataDisplayTreeIconAndName> tempDisplays = new Vector<ExpDataDisplayTreeIconAndName>(
				displays.length);

		for (final Display display : displays) {
			try {
				final String beanName = ReCResourceBundle
						.findStringOrDefault(display.getClassLocationBundleKey(), null);
				if (beanName == null) {
					continue;
				}
				final Object dataDisplayTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(),
						beanName.trim());
				if (java.beans.Beans.isInstanceOf(dataDisplayTemp, ExpDataDisplay.class)) {
					tempDisplays.add(new ExpDataDisplayTreeIconAndName((ExpDataDisplay) dataDisplayTemp, display));
				}
			} catch (final Throwable e) {
				LoggerUtil.logThrowable(e.getMessage(), e, Logger.getLogger(DefaultDisplayFactory.UI_CLIENT_LOGGER));
			}
		}

		tempDisplays.trimToSize();
		return tempDisplays.toArray(new ExpDataDisplay[0]);
	}

	// Hacked... this is the way I found to, without changing to source code,
	// set
	// tree image and label in the experiment tabs... i think this is the logic
	// way..
	private class ExpDataDisplayTreeIconAndName implements ExpDataDisplay {
		private ExpDataDisplay expDisplay = null;
		private Display treeDisplay = null;

		public ExpDataDisplayTreeIconAndName(final ExpDataDisplay expDisplay, final Display treeDisplay) {
			this.expDisplay = expDisplay;
			this.treeDisplay = treeDisplay;
		}

		@Override
		public javax.swing.JComponent getDisplay() {
			return expDisplay.getDisplay();
		}

		@Override
		public javax.swing.Icon getIcon() {
			return ReCResourceBundle.findImageIconOrDefault(treeDisplay.getIconLocationBundleKey(), null);
		}

		@Override
		public javax.swing.JMenuBar getMenuBar() {
			return expDisplay.getMenuBar();
		}

		@Override
		public String getName() {
			return ReCResourceBundle.findStringOrDefault(treeDisplay.getDisplayNameBundleKey(), expDisplay.getName());
		}

		@Override
		public javax.swing.JToolBar getToolBar() {
			return expDisplay.getToolBar();
		}

		@Override
		public void setExpDataModel(final com.linkare.rec.impl.client.experiment.ExpDataModel model) {
			expDisplay.setExpDataModel(model);
		}
	}
}
