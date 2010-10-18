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
import java.awt.font.TextAttribute;
import java.text.AttributedString;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.metal.MetalRadioButtonUI;
import javax.swing.text.View;


/**
 * 
 * @author João FLorindo
 */
public class FlatRadioButtonUI extends MetalRadioButtonUI {

	public static ComponentUI createUI(JComponent x) {
		return new FlatRadioButtonUI();
	}

	@Override
	public synchronized void paint(Graphics g, JComponent c) {
		AbstractButton abstractButton = (AbstractButton) c;
		ButtonModel model = abstractButton.getModel();

		Dimension size = c.getSize();

		int w = size.width;
		int h = size.height;

		Font f = c.getFont();
		g.setFont(f);
		FontMetrics fm = g.getFontMetrics();

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

		if (abstractButton instanceof javax.swing.JRadioButton) {
			altIcon = new javax.swing.ImageIcon(FlatRadioButtonUI.class.getResource("resources/radioButton.png"));
			selectedIcon = new javax.swing.ImageIcon(FlatRadioButtonUI.class.getResource("resources/radioButtonSelected.png"));
			disableIcon = new javax.swing.ImageIcon(FlatRadioButtonUI.class.getResource("resources/radioButtonDisable.png"));
		} else if (abstractButton instanceof javax.swing.JCheckBox) {
			altIcon = new javax.swing.ImageIcon(FlatRadioButtonUI.class.getResource("resources/checkBox.png"));
			selectedIcon = new javax.swing.ImageIcon(FlatRadioButtonUI.class.getResource("resources/checkBoxSelected.png"));
			disableIcon = new javax.swing.ImageIcon(FlatRadioButtonUI.class.getResource("resources/checkBoxDisable.png"));
		}

		String text = SwingUtilities.layoutCompoundLabel(c, fm, abstractButton.getText(), altIcon != null ? altIcon : getDefaultIcon(), abstractButton
				.getVerticalAlignment(), abstractButton.getHorizontalAlignment(), abstractButton.getVerticalTextPosition(), abstractButton.getHorizontalTextPosition(), viewRect,
				iconRect, textRect, abstractButton.getIconTextGap());

		// fill background
		if (c.isOpaque()) {
			g.setColor(abstractButton.getBackground());
			g.fillRect(0, 0, size.width, size.height);
		}

		// Paint the radio button
		if (altIcon != null) {

			if (!model.isEnabled()) {
				if (model.isSelected()) {
					altIcon = selectedIcon;
				} else {
					altIcon = disableIcon;
				}
			} else if (model.isEnabled()) {
				if (model.isSelected()) {
					altIcon = selectedIcon;
				}
			}
		}
		altIcon.paintIcon(c, g, iconRect.x, iconRect.y);

		// Draw the Text
		if (text != null) {
			View v = (View) c.getClientProperty(BasicHTML.propertyKey);
			if (v != null) {
				v.paint(g, textRect);
			} else {
				int mnemIndex = abstractButton.getDisplayedMnemonicIndex();
				if (model.isEnabled()) {
					// *** paint the text normally
					g.setColor(abstractButton.getForeground());
				} else {
					// *** paint the text disabled
					g.setColor(getDisabledTextColor());
				}
				if(mnemIndex>0 && mnemIndex<=text.length())
				{
					AttributedString as=new AttributedString(text);
					as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON,mnemIndex-1,mnemIndex);
					g.drawString(as.getIterator(), textRect.x, textRect.y + fm.getAscent());
				} else {
					g.drawString(text, textRect.x, textRect.y + fm.getAscent());
				}
			}
			if (abstractButton.hasFocus() && abstractButton.isFocusPainted() && textRect.width > 0 && textRect.height > 0) {
				paintFocus(g, textRect, size);
			}
		}
	}
}
