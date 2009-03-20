/* 
 * ToolBar.java created on Feb 5, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import javax.swing.JToolBar;

/**
 * 
 * @author Henrique Fernandes
 */
public class ToolBar extends JToolBar {

	private static final long serialVersionUID = -563387474458273040L;

	private static final String TOOLBAR_NAME = "ReCToolBar";

	public ToolBar() {
		super(TOOLBAR_NAME, JToolBar.HORIZONTAL);
		setFloatable(false);
		setRollover(true);
	}

}
