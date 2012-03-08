/* 
 * FlatButton.java created on 2009/04/28
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * 
 * @author João Florindo
 */
public class FlatButton extends JButton implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2049201533365453880L;

	private static final String uiClassID = "FlatButtonUI";

	private Color borderColor;

	private Color gradientTop = FlatButton.GRADIENTTOP_OFF;
	private Color gradientBottom = FlatButton.GRADIENTBOTTOM_OFF;
	private Color foreground = FlatButton.FOREGROUND_OFF;

	public static final Color GRADIENTTOP_OFF = new Color(0xacd651);
	public static final Color GRADIENTBOTTOM_OFF = new Color(0x9fcb42);
	public static final Color GRADIENTTOP_ON = new Color(0xffae48);
	public static final Color GRADIENTBOTTOM_ON = new Color(0xff930d);

	public static final Color COLORBORDER_OFF = new Color(0x7da921);
	public static final Color COLORBORDER_ON = new Color(0xe38b1d);

	public static final Color FOREGROUND_OFF = new Color(0x2B7A3B);
	public static final Color FOREGROUND_ON = new Color(0x89381E);

	public FlatButton() {
		super();

		if (UIManager.get(getUIClassID()) == null) {
			setUI(new MetalButtonUI());
		}
	}

	@Override
	public String getUIClassID() {
		return FlatButton.uiClassID;
	}

	/**
	 * @param gradientTopOff the gradientTop to set
	 */
	public void setGradientTop(final Color gradientTop) {
		this.gradientTop = gradientTop;
	}

	/**
	 * @return the gradientTop
	 */
	public Color getGradientTop() {
		return gradientTop;
	}

	/**
	 * @param gradientBottom the gradientBottom to set
	 */
	public void setGradientBottom(final Color gradientBottom) {
		this.gradientBottom = gradientBottom;
	}

	/**
	 * @return the gradientBottom
	 */
	public Color getGradientBottom() {
		return gradientBottom;
	}

	/**
	 * @param foreground the foreground to set
	 */
	@Override
	public void setForeground(final Color foreground) {
		this.foreground = foreground;
	}

	/**
	 * @return the foreground
	 */
	@Override
	public Color getForeground() {
		return foreground;
	}

	/**
	 * @param borderColor the borderColor to set
	 */
	public void setBorderColor(final Color borderColor) {
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor),
				BorderFactory.createEmptyBorder(4, 16, 4, 16)));
	}

	/**
	 * @return the borderColor
	 */
	public Color getBorderColor() {
		return borderColor;
	}

}
