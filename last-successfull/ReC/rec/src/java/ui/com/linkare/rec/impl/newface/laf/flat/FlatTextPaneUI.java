/* 
 * FlatTextPaneUI.java created on 12 Mar 2013
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.beans.PropertyChangeEvent;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextPaneUI;
import javax.swing.text.JTextComponent;

/**
 * 
 * @author jpereira - Linkare TI
 */
public class FlatTextPaneUI extends BasicTextPaneUI {

	public static ComponentUI createUI(JComponent c) {
		return new FlatTextPaneUI();
	}

	/**
	 * Creates the <code>FlatTextPaneUI</code>.
	 */
	public FlatTextPaneUI() {
		super();
	}

	@Override
	protected void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("editable") || evt.getPropertyName().equals("enabled")
				|| evt.getPropertyName().equals("opaque")) {
			JTextComponent c = (JTextComponent) evt.getSource();
			if (c.isOpaque()) {
				super.propertyChange(evt);
			} 
		}
	}
}
