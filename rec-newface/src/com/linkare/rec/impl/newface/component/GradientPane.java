/* 
 * GradientPane.java created on Mar 9, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Window;

import javax.swing.UIManager;

import com.linkare.rec.impl.newface.utils.LAFConnector.SpecialELabProperties;

/**
 * 
 * @author Henrique Fernandes
 */
public class GradientPane extends AbstractContentPane {

	private static final long serialVersionUID = -1489776676140309136L;

	public enum GradientStyle {
		VERTICAL_LINEAR_LIGHT_TO_DARK, VERTICAL_LINEAR_DARK_TO_LIGHT,
	}

	private GradientStyle style = GradientStyle.VERTICAL_LINEAR_LIGHT_TO_DARK;

	private Color lightColor = UIManager.getColor(SpecialELabProperties.GRADIENT_LIGHTCOLOR.getName());

	private Color darkColor = UIManager.getColor(SpecialELabProperties.GRADIENT_DARKCOLOR.getName());

	/** Default */
	public GradientPane() {
		this(null, null);
	}

	public GradientPane(GradientStyle style) {
		this(style, null);
	}

	/** Creates a new GradientPane */
	public GradientPane(GradientStyle style, Window container) {
		super(container);
		this.style = style;
		init();
	}

	/**
	 * Init Properties
	 */
	private void init() {
		setOpaque(true);

		if (lightColor == null) {
			setLightColor(Color.WHITE);
		}
		if (darkColor == null) {
			setDarkColor(UIManager.getColor("control"));
		}
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		GradientPaint p = null;

		if (this.style == GradientStyle.VERTICAL_LINEAR_LIGHT_TO_DARK) {
			p = new GradientPaint(0, 0, lightColor, 0, getHeight(), darkColor);
		} else {
			p = new GradientPaint(0, 0, darkColor, 0, getHeight(), lightColor);
		}

		Paint oldPaint = g2.getPaint();
		g2.setPaint(p);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.setPaint(oldPaint);

	}

	/**
	 * @return the style
	 */
	public GradientStyle getStyle() {
		return style;
	}

	/**
	 * @return the lightColor
	 */
	public Color getLightColor() {
		return lightColor;
	}

	/**
	 * @return the darkColor
	 */
	public Color getDarkColor() {
		return darkColor;
	}

	/**
	 * @param style
	 *            the style to set
	 */
	public void setStyle(GradientStyle style) {
		this.style = style;
	}

	/**
	 * @param lightColor
	 *            the lightColor to set
	 */
	public void setLightColor(Color lightColor) {
		this.lightColor = lightColor;
	}

	/**
	 * @param darkColor
	 *            the darkColor to set
	 */
	public void setDarkColor(Color darkColor) {
		this.darkColor = darkColor;
	}

}
