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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.metal.MetalButtonUI;

import com.linkare.rec.impl.newface.component.FlatButton;


/**
 * 
 * @author João Florindo
 */
public class FlatButtonUI extends MetalButtonUI{
	
	private Color color_gradient_top = new Color(0xacd651);
	private Color color_gradient_bottom = new Color(0x9fcb42);
	private static final Color DEFAULT_COLOR_BORDER = Color.BLACK;
	
	public static ComponentUI createUI(JComponent x) {
		return new FlatButtonUI();
	}
	
    protected Color getFocusColor() {
        return color_gradient_top;
    }
    
    // ********************************
    //          Paint
    // ********************************
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
    	FlatButton fButton = (FlatButton)b;
    	
    	if(fButton.getBorder()==null)
    		fButton.setBorder(BorderFactory.createLineBorder(Color.black));
    	
    	if ( fButton.isBackgroundGradient() && fButton.getPressedGradientBottomColor() != null && fButton.getPressedGradientTopColor() != null) {
			FlatUtils.drawGradient(fButton, g, 0, 0, 0, b.getHeight(),
				new float[] {.0f, 1.0f},
				new Color[] {fButton.getPressedGradientBottomColor(), fButton.getPressedGradientTopColor()});
    	}else{
    		Dimension size = b.getSize();
    		g.setColor(fButton.getBackground());
    		g.fillRect(0, 0, size.width, size.height);
    	}
    }


    public void update(Graphics g, JComponent c) {
    	FlatButton fButton = (FlatButton)c;
    	
    	if(fButton.getBorder()==null)
    		fButton.setBorder(BorderFactory.createLineBorder(DEFAULT_COLOR_BORDER));
    	    	
    	if(fButton.getGradientBottomColor() != null)
    		color_gradient_bottom = fButton.getGradientBottomColor();
    	
    	if(fButton.getGradientTopColor()!= null)
    		color_gradient_top = fButton.getGradientTopColor();
    	
    	
    	FlatUtils.drawGradient(fButton, g, 0, 0, 0, c.getHeight(),
				new float[] {.0f, 1.0f},
				new Color[] {color_gradient_bottom, color_gradient_top});
		super.paint(g,fButton);
    }
}
