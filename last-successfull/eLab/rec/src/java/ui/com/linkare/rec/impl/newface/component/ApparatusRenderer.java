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

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.newface.config.Apparatus;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusRenderer extends JLabel implements ListCellRenderer {

	private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 2, 1, 2);

	private Apparatus apparatus;

	public ApparatusRenderer() {
		super();
		setOpaque(true);
		setBorder(NO_FOCUS_BORDER);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

		// Value must be an Apparatus
		apparatus = (Apparatus) value;

		if (apparatus != null) {

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
			setIcon(ReCResourceBundle.findImageIconOrDefault(apparatus.getIconLocationBundleKey(), null));

			// Text
			setText(ReCResourceBundle.findString(apparatus.getDisplayStringBundleKey()));

			// State
			setEnabled(apparatus.isEnabled());
		}

		return this;
	}

}
