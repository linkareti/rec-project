/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import com.linkare.rec.am.config.Lab;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author npadriano
 */
public class LabRenderer extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4569484644830803732L;

	private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 2, 1, 2);

	private Lab lab;

	public LabRenderer() {
		super();
		setOpaque(true);
		setBorder(LabRenderer.NO_FOCUS_BORDER);
	}

	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {

		if (value != null) {
			// Value must be an Lab
			lab = (Lab) value;

			if (isSelected) {
				setBackground(list.getSelectionBackground());
				setForeground(list.getSelectionForeground());
			} else {
				setBackground(list.getBackground());
				setForeground(list.getForeground());
			}

			// Font
			setFont(list.getFont());

			// Icon
			setIcon(ReCResourceBundle.findImageIconOrDefault(lab.getIconLocationBundleKey(), null));

			// Text
			setText(ReCResourceBundle.findStringOrDefault(lab.getLabIdStringBundleKey(), lab.getLabIdStringBundleKey()));

			// State
			setEnabled(true);
		}

		return this;
	}

}
