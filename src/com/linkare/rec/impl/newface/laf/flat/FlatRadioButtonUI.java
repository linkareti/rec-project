/* 
 * FlatRadioButtonUI.java created on 2009/05/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.metal.MetalRadioButtonUI;
import javax.swing.text.View;

import sun.swing.SwingUtilities2;



/**
 * 
 * @author João FLorindo
 */
public class FlatRadioButtonUI extends MetalRadioButtonUI {

	public static ComponentUI createUI(JComponent x) {
		return new FlatRadioButtonUI();
	}
	
	public synchronized void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel model = b.getModel();
        
        Dimension size = c.getSize();

        int w = size.width;
        int h = size.height;

        Font f = c.getFont();
        g.setFont(f);
        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g, f);

        Rectangle viewRect = new Rectangle(size);
        Rectangle iconRect = new Rectangle();
        Rectangle textRect = new Rectangle();

        Insets i = c.getInsets();
        viewRect.x += i.left;
        viewRect.y += i.top;
        viewRect.width -= (i.right + viewRect.x);
        viewRect.height -= (i.bottom + viewRect.y);
		
        Icon altIcon = null;
        Icon selectedIcon = null;
        Icon disableIcon = null;
        
        if(b instanceof javax.swing.JRadioButton){
        	altIcon = new javax.swing.ImageIcon(getClass().getResource("./resources/radioButton.png"));
        	selectedIcon = new javax.swing.ImageIcon(getClass().getResource("./resources/radioButtonSelected.png"));
        	disableIcon = new javax.swing.ImageIcon(getClass().getResource("./resources/radioButtonDisable.png"));
		}else if(b instanceof javax.swing.JCheckBox){
			altIcon = new javax.swing.ImageIcon(getClass().getResource("./resources/checkBox.png"));
			selectedIcon = new javax.swing.ImageIcon(getClass().getResource("./resources/checkBoxSelected.png"));
			disableIcon = new javax.swing.ImageIcon(getClass().getResource("./resources/checkBoxDisable.png"));
		}
        
        String text = SwingUtilities.layoutCompoundLabel(
                c, fm, b.getText(), altIcon != null ? altIcon : getDefaultIcon(),
                b.getVerticalAlignment(), b.getHorizontalAlignment(),
                b.getVerticalTextPosition(), b.getHorizontalTextPosition(),
                viewRect, iconRect, textRect, b.getIconTextGap());
            
        // fill background
        if(c.isOpaque()) {
            g.setColor(b.getBackground());
            g.fillRect(0,0, size.width, size.height); 
        }

        
		 // Paint the radio button
        if(altIcon != null) { 
        	
        	if(!model.isEnabled()) {
        		if(model.isSelected()) {
        			altIcon = selectedIcon;
        		} else {
        			altIcon = disableIcon;
        		} 
        	} else if(model.isEnabled()) {
        		if(model.isSelected()) {
       				altIcon = selectedIcon;
        		} 
        	} 
        }  
        altIcon.paintIcon(c, g, iconRect.x, iconRect.y);
        
        // Draw the Text
        if(text != null) {
        	View v = (View) c.getClientProperty(BasicHTML.propertyKey);
        	if (v != null) {
        		v.paint(g, textRect);
        	} else {
        		int mnemIndex = b.getDisplayedMnemonicIndex();
        		if(model.isEnabled()) {
        			// *** paint the text normally
        			g.setColor(b.getForeground());
        		} else {
        			// *** paint the text disabled
        			g.setColor(getDisabledTextColor());
        		}
        		SwingUtilities2.drawStringUnderlineCharAt(c,g,text,
        				mnemIndex, textRect.x, textRect.y + fm.getAscent());
        	}
        	if(b.hasFocus() && b.isFocusPainted() &&
        			textRect.width > 0 && textRect.height > 0 ) {
        		paintFocus(g,textRect,size);
        	}
        }
	}
}
