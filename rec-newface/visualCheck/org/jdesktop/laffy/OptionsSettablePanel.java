/*
 * Laffy - Swing Look and Feel Sampler
 * Copyright (C) Sun Microsystems
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA
 */
package org.jdesktop.laffy;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

/**
 * OptionsSettablePanel
 * 
 * @author Created by Jasper Potts (Oct 8, 2007)
 * @version 1.0
 */
public abstract class OptionsSettablePanel extends JPanel {

	protected final static Color FORCED_BACKGROUND = new Color(151, 174, 20);

	protected OptionsSettablePanel() {
		super();
		Laffy.getInstance().addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if ("forceNonOpaque".equals(evt.getPropertyName())) {
					setForceComponentsNonOpaque((Boolean) evt.getNewValue());
				} else if ("forceBackgroundColor".equals(evt.getPropertyName())) {
					setForceComponentsBackgroundColor((Boolean) evt.getNewValue());
				} else if ("forceComponentsToBasicUI".equals(evt.getPropertyName())) {
					setForceComponentsToBasicUI((Boolean) evt.getNewValue());
				}
			}
		});
	}

	public void setForceComponentsNonOpaque(boolean force) {
	}

	public void setForceComponentsBackgroundColor(boolean force) {
	}

	public void setForceComponentsToBasicUI(boolean force) {
	}

}
