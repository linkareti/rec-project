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
import com.linkare.rec.web.config.Apparatus;

/**
 * 
 * @author Henrique Fernandes
 */
public class ApparatusRenderer extends JLabel implements ListCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3255888693182744820L;

	private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 2, 1, 2);

	private Apparatus apparatus;

//	private Color virtualBackgroundSelected=null;
//	private Color virtualBackgroundUnSelected=nullLOGGERLOGGER;
	
	public ApparatusRenderer() {
		super();
		setOpaque(true);
		setBorder(ApparatusRenderer.NO_FOCUS_BORDER);
//		virtualBackgroundSelected=ReCApplication.getApplication().getContext().getResourceMap().getColor("virtualExperiments.background");
//		virtualBackgroundUnSelected=virtualBackgroundSelected.brighter();
	}

	@Override
	public Component getListCellRendererComponent(final JList list, final Object value, final int index,
			final boolean isSelected, final boolean cellHasFocus) {

		// Value must be an Apparatus
		apparatus = (Apparatus) value;

		if (apparatus != null) {

			if (isSelected) {
//				if (apparatus.isVirtual()) {
//					setBackground(virtualBackgroundSelected);
//				} else {
					setBackground(list.getSelectionBackground());
//				}
				setForeground(list.getSelectionForeground());
			} else {
//				if (apparatus.isVirtual()) {
//					setBackground(virtualBackgroundUnSelected);
//				} else {
					setBackground(list.getBackground());
//				}
				setForeground(list.getForeground());
			}

			// Font
			setFont(list.getFont());

			// Icon
			setIcon(ReCResourceBundle.findImageIconOrDefault(apparatus.getIconLocationBundleKey(), null));

			// Text
			String displayStringBundleKey = apparatus.getDisplayStringBundleKey();
			setText(ReCResourceBundle.findStringOrDefault(displayStringBundleKey, displayStringBundleKey));

			// State
			setEnabled(apparatus.isEnabled());
		}

		return this;
	}

}
