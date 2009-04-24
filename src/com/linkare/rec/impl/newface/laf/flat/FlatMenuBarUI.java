/* 
 * FlatMenuBarUI.java created on Feb 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicMenuBarUI;

import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources;
import com.linkare.rec.impl.newface.laf.flat.resources.FlatLAFResources.FlatLAFResourcesEnum;

/**
 * @deprecated
 * @author Henrique Fernandes
 */
public class FlatMenuBarUI extends BasicMenuBarUI {
    
    private static final BufferedImage backgroundImg = FlatLAFResources
	    .getImage(FlatLAFResourcesEnum.FLATMENUBAR_BACKGROUD_IMG.getName());

    public static ComponentUI createUI(JComponent x) {
    	return new FlatMenuBarUI();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
    	// Simply draw the background image
    	g.drawImage(backgroundImg, 0, 0, c.getWidth(), c.getHeight(), 0, 0, backgroundImg.getWidth(),
    			backgroundImg.getHeight(), null);
    }
    
}
