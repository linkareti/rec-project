package com.linkare.rec.impl.newface.display;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.config.Display;

public class DefaultDisplayFactory extends AbstractDisplayFactory {

	private static final Logger log = Logger.getLogger(DefaultDisplayFactory.class.getName());

	/** Default Constructor */
	public DefaultDisplayFactory() {
	}

	@Override
	public void destroyDisplays() {
		// Big silent noop (As seen on RecBaseUI)
	}

	@Override
	public List<ExpDataDisplay> getDisplays() {
		List<Display> initDisplays = getInitDisplays();
		List<ExpDataDisplay> tempDisplays = new ArrayList<ExpDataDisplay>();

		for (Display display : initDisplays) {
			try {
				String beanName = ReCResourceBundle.findStringOrDefault(display.getClassLocationBundleKey(), null);
				if (beanName == null)
					continue;
				Object dataDisplayTemp = java.beans.Beans.instantiate(this.getClass().getClassLoader(), beanName.trim());
				if (java.beans.Beans.isInstanceOf(dataDisplayTemp, ExpDataDisplay.class))
					tempDisplays.add(new ExpDataDisplayTreeIconAndName((ExpDataDisplay) dataDisplayTemp, display));

			} catch (Throwable t) {
				log.log(Level.SEVERE, "Could not instantiate display", t);
			}
		}
		return tempDisplays;
	}

	// Hacked... this is the way I found to, without changing to source code, set
	// tree image and label in the experiment tabs... i think this is the logic way..
	private class ExpDataDisplayTreeIconAndName implements ExpDataDisplay {
		private ExpDataDisplay expDisplay = null;
		private Display treeDisplay = null;

		public ExpDataDisplayTreeIconAndName(ExpDataDisplay expDisplay, Display treeDisplay) {
			this.expDisplay = expDisplay;
			this.treeDisplay = treeDisplay;
		}

		public javax.swing.JComponent getDisplay() {
			return expDisplay.getDisplay();
		}

		public javax.swing.Icon getIcon() {
			return ReCResourceBundle.findImageIconOrDefault(treeDisplay.getIconLocationBundleKey(), null);
		}

		public javax.swing.JMenuBar getMenuBar() {
			return expDisplay.getMenuBar();
		}

		public String getName() {
			return ReCResourceBundle.findStringOrDefault(treeDisplay.getDisplayStringBundleKey(), expDisplay.getName());
		}

		public javax.swing.JToolBar getToolBar() {
			return expDisplay.getToolBar();
		}

		public void setExpDataModel(com.linkare.rec.impl.client.experiment.ExpDataModel model) {
			expDisplay.setExpDataModel(model);
		}
	}

}
