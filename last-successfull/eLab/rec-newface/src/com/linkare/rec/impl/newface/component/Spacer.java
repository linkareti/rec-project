/* 
 * Spacer.java created on Mar 6, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Dimension;

import javax.swing.JComponent;

/**
 * Component for empty space fill.
 * 
 * @author Henrique Fernandes
 */
public class Spacer extends JComponent {

	private static final long serialVersionUID = -2820313727305327858L;

	private static final Dimension DEFAULT_SPACER_SIZE = new Dimension(10, 10);

	public Spacer() {
		super();
		setPreferredSize(DEFAULT_SPACER_SIZE);
		setMinimumSize(DEFAULT_SPACER_SIZE);
	}

}
