/* 
 * FlatButton.java created on 2009/04/28
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.swing.JButton;

/**
 * 
 * @author João Florindo
 */
public class FlatButton extends JButton implements Serializable{

	private static final String uiClassID = "FlatButtonUI";
	
	private Color gradientTopColor = null;
	private Color gradientBottomColor = null;
	private Color pressedGradientTopColor = null;
	private Color pressedGradientBottomColor = null;
	private boolean isBackgroundGradient = false;
	
    private final PropertyChangeSupport pcs = new PropertyChangeSupport( this );
    
	public FlatButton() {
		super();
	}

	public String getUIClassID() {
		return uiClassID;
	}

	/**
	 * @param gradientTopColor the gradientTopColor to set
	 */
	public void setGradientTopColor(Color gradientTopColor) {
		this.gradientTopColor = gradientTopColor;
	}

	/**
	 * @return the gradientTopColor
	 */
	public Color getGradientTopColor() {
		return gradientTopColor;
	}

	/**
	 * @param gradientBottomColor the gradientBottomColor to set
	 */
	public void setGradientBottomColor(Color gradientBottomColor) {
		this.gradientBottomColor = gradientBottomColor;
	}

	/**
	 * @return the gradientBottomColor
	 */
	public Color getGradientBottomColor() {
		return gradientBottomColor;
	}

	/**
	 * @param pressedGradientTopColor the pressedGradientTopColor to set
	 */
	public void setPressedGradientTopColor(Color pressedGradientTopColor) {
		this.pressedGradientTopColor = pressedGradientTopColor;
	}

	/**
	 * @return the pressedGradientTopColor
	 */
	public Color getPressedGradientTopColor() {
		return pressedGradientTopColor;
	}

	/**
	 * @param pressedGradientBottomColor the pressedGradientBottomColor to set
	 */
	public void setPressedGradientBottomColor(Color pressedGradientBottomColor) {
		this.pressedGradientBottomColor = pressedGradientBottomColor;
	}

	/**
	 * @return the pressedGradientBottomColor
	 */
	public Color getPressedGradientBottomColor() {
		return pressedGradientBottomColor;
	}

	/**
	 * @param isBackgroundGradient the isBackgroundGradient to set
	 */
	public void setBackgroundGradient(boolean isBackgroundGradient) {
		this.isBackgroundGradient = isBackgroundGradient;
	}

	/**
	 * @return the isBackgroundGradient
	 */
	public boolean isBackgroundGradient() {
		return isBackgroundGradient;
	}
}
