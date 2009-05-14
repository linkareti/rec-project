/* 
 * FlatButton.java created on 2009/04/28
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * 
 * @author João Florindo
 */
public class FlatButton extends JButton implements Serializable{

	private static final String uiClassID = "FlatButtonUI";
	
	private Color gradientTopOff = new Color(0xacd651);
	private Color gradientBottomOff = new Color(0x9fcb42);
	private Color gradientTopOn = new Color(0xffae48);
	private Color gradientBottomOn = new Color(0xff930d);
	
	private Color colorBorderOff = new Color(0x7da921);
	private Color colorBorderOn = new Color(0xe38b1d);
	
	private Color foregroundOff = new Color(0x2B7A3B);
	private Color foregroundOn = new Color(0x89381E);
	
	public FlatButton() {
		super();

        if (UIManager.get(getUIClassID()) == null) {
            setUI(new MetalButtonUI());
        }
	}
	
	

	public String getUIClassID() {
		return uiClassID;
	}


	/**
	 * @param gradientTopOff the gradientTopOff to set
	 */
	public void setGradientTopOff(Color gradientTopOff) {
		this.gradientTopOff = gradientTopOff;
	}

	/**
	 * @return the gradientTopOff
	 */
	public Color getGradientTopOff() {
		return gradientTopOff;
	}

	/**
	 * @param gradientBottomOff the gradientBottomOff to set
	 */
	public void setGradientBottomOff(Color gradientBottomOff) {
		this.gradientBottomOff = gradientBottomOff;
	}

	/**
	 * @return the gradientBottomOff
	 */
	public Color getGradientBottomOff() {
		return gradientBottomOff;
	}

	/**
	 * @param gradientTopOn the gradientTopOn to set
	 */
	public void setGradientTopOn(Color gradientTopOn) {
		this.gradientTopOn = gradientTopOn;
	}

	/**
	 * @return the gradientTopOn
	 */
	public Color getGradientTopOn() {
		return gradientTopOn;
	}

	/**
	 * @param gradientBottomOn the gradientBottomOn to set
	 */
	public void setGradientBottomOn(Color gradientBottomOn) {
		this.gradientBottomOn = gradientBottomOn;
	}

	/**
	 * @return the gradientBottomOn
	 */
	public Color getGradientBottomOn() {
		return gradientBottomOn;
	}

	/**
	 * @param colorBorderOn the colorBorderOn to set
	 */
	public void setColorBorderOn(Color colorBorderOn) {
		this.colorBorderOn = colorBorderOn;
	}



	/**
	 * @return the colorBorderOn
	 */
	public Color getColorBorderOn() {
		return colorBorderOn;
	}



	/**
	 * @param colorBorderOff the colorBorderOff to set
	 */
	public void setColorBorderOff(Color colorBorderOff) {
		this.colorBorderOff = colorBorderOff;
	}



	/**
	 * @return the colorBorderOff
	 */
	public Color getColorBorderOff() {
		return colorBorderOff;
	}



	/**
	 * @param foregroundOff the foregroundOff to set
	 */
	public void setForegroundOff(Color foregroundOff) {
		this.foregroundOff = foregroundOff;
	}



	/**
	 * @return the foregroundOff
	 */
	public Color getForegroundOff() {
		return foregroundOff;
	}



	/**
	 * @param foregroundOn the foregroundOn to set
	 */
	public void setForegroundOn(Color foregroundOn) {
		this.foregroundOn = foregroundOn;
	}



	/**
	 * @return the foregroundOn
	 */
	public Color getForegroundOn() {
		return foregroundOn;
	}

}
