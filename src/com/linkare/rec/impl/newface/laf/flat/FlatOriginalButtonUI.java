/* 
 * FlatButtonUI.java created on 2009/04/29
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalButtonUI;

import sun.swing.SwingUtilities2;

import com.linkare.rec.impl.newface.component.FlatButton;
import com.sun.xml.internal.bind.v2.model.impl.ModelBuilder;


/**
 * 
 * @author João Florindo
 */
public class FlatOriginalButtonUI extends MetalButtonUI{
	
	private final static String propertyPrefix = "Button" + ".";
	 
	public static ComponentUI createUI(JComponent x) {
		return new FlatOriginalButtonUI();
	}
	  
    protected String getPropertyPrefix() {
        return propertyPrefix;
    }
    
    // ********************************
    //          Paint
    // ********************************
	
    
    /* 
     * Para não ser desenhado o focus no Button
	 */
	@Override
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
    }


    /* 
     * Para não ser desenhado o focus no Button
	 */
	@Override
	protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect,
			Rectangle textRect, Rectangle iconRect) {
	
	}

}
