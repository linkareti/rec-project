/* 
 * FlatToolBarUI.java created on Feb 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolBarUI;

/**
 * @deprecated
 * @author Henrique Fernandes
 */
public class FlatToolBarUI extends BasicToolBarUI {

    public static ComponentUI createUI(JComponent x) {
	return new FlatToolBarUI();
    }
    
    public FlatToolBarUI() {
	super();
    }

    /*
     * @see javax.swing.plaf.ComponentUI#update(java.awt.Graphics,
     * javax.swing.JComponent)
     */
    @Override
    public void update(Graphics g, JComponent c) {
	Color oldColor = g.getColor();
	g.setColor(UIManager.getColor("ToolBar.background"));
	super.update(g, c);
	g.setColor(oldColor);
    }
    
}
