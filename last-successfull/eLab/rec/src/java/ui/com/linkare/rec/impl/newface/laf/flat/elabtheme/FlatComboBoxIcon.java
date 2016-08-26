/* 
 * FlatComboBoxIcon.java created on 2009/04/03
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * 
 * @author joao
 */
public class FlatComboBoxIcon implements Icon, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8200352117513893373L;

	/**
	 * Paints the horizontal bars for the
	 */
	@Override
	public void paintIcon(final Component c, final Graphics g, final int x, final int y) {
		final JComponent component = (JComponent) c;
		final int iconWidth = getIconWidth();

		g.translate(x, y);

		// g.setColor( component.isEnabled() ? FlatTheme.getValue("ComboBox" +
		// AbstractStyle.ARROWBUTTON_ENABLEDFOREGROUND) :
		// FlatTheme.getValue("ComboBox" +
		// AbstractStyle.ARROWBUTTON_DISABLEDFOREGROUND) );
		g.setColor(component.isEnabled() ? Color.white : Color.white);
		g.drawLine(0, 0, iconWidth - 1, 0);
		g.drawLine(1, 1, 1 + (iconWidth - 3), 1);
		g.drawLine(2, 2, 2 + (iconWidth - 5), 2);
		g.drawLine(3, 3, 3 + (iconWidth - 7), 3);
		g.drawLine(4, 4, 4 + (iconWidth - 9), 4);

		g.translate(-x, -y);
	}

	/**
	 * Created a stub to satisfy the interface.
	 */
	@Override
	public int getIconWidth() {
		return 10;
	}

	/**
	 * Created a stub to satisfy the interface.
	 */
	@Override
	public int getIconHeight() {
		return 5;
	}

}
