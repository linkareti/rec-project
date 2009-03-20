/* 
 * ToolBarButton.java created on Feb 5, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * 
 * @author Henrique Fernandes
 */
public class ToolBarButton extends JButton {

	private static final long serialVersionUID = 4968001190347564024L;

	public ToolBarButton() {
		setBorderPainted(false);
		setBorder(BorderFactory.createEmptyBorder());
	}

}
