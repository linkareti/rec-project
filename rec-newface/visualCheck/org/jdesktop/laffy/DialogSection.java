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

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * DialogSection
 * 
 * @author Created by Jasper Potts (Sep 6, 2007)
 * @version 1.0
 */
public abstract class DialogSection extends Section {

    public DialogSection(String name) {
	super(name);
	JComponent content = getDialogContent();
	JInternalFrame internalFrame = new JInternalFrame(name) {
	    public boolean isSelected() {
		return true;
	    }
	};
	internalFrame.getContentPane().add(content, BorderLayout.CENTER);
	internalFrame.pack();
	internalFrame.setVisible(true);
	//        internalFrame.setPreferredSize(new Dimension(100,100));
	contentPanel.setContent(internalFrame);
    }

    protected Action createFloatAction() {
	return new ShowDialogAction();
    }

    protected abstract void showDialog();

    protected abstract JComponent getDialogContent();

    // =================================================================================================================
    // Show Dialog Action

    private class ShowDialogAction extends AbstractAction {
	/** Creates an {@code Action}. */
	public ShowDialogAction() {
	    super(getName());
	    DialogSection.this.addPropertyChangeListener("name", new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
		    putValue(Action.NAME, getName());
		}
	    });
	}

	/** Invoked when an action occurs. */
	public void actionPerformed(ActionEvent event) {
	    showDialog();
	}
    }
}
