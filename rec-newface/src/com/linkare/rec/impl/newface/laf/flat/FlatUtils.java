/* 
 * FlatUtils.java created on 2009/04/30
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.linkare.rec.impl.newface.component.FlatButton;

import sun.swing.CachedPainter;

/**
 * 
 * @author João FLorindo
 */
public class FlatUtils {

    
    static boolean drawGradient(JComponent c, Graphics g, int x, int y, int w, int h, float[] fractions, Color[] colors ) {
    	LinearGradientPaint paint = null;
    	
    	try{
	    	c.setOpaque(false);
			Graphics2D g2 = (Graphics2D)g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Composite old = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
			
	    	//VERTIVAL GRADIENT
			paint = new LinearGradientPaint(x, y, w, h,	fractions, colors);
			g2.setPaint(paint);
			Shape shape = new Rectangle2D.Float(0,0,c.getWidth(), c.getHeight());
			g2.fill(shape);
			g2.setComposite(old);
			g2.dispose();
    	}catch(NullPointerException ne){
    		return false;
	    }catch(IllegalArgumentException ie){
	    	return false;
	    }

        return true;
    }
    
 
}
