/* 
 * LAFConnector.java created on Apr 1, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.utils;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;

/**
 * 
 * @author hfernandes
 */
public class LAFConnector /* extends UIManager */{

	/**
	 * The special LAF properties bridge. Use this holder to define special resources provided on the default eLab Theme
	 * for Flat Look and Feel.
	 * 
	 * @author Henrique Fernandes
	 */
	public enum SpecialELabProperties {

		ENABLED_FOREGROUND_ON_DARK("enabledForegroundOnDark"), SELECTION_FOREGROUND_ON_DARK("selectionForegroundOnDark"),
		GRADIENT_LIGHTCOLOR("gradientLightColor"), GRADIENT_DARKCOLOR("gradientDarkColor"), DEFAULT_WHITE(".defaultWhite"), ;

		private String name;

		SpecialELabProperties(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	public static final Color getColor(SpecialELabProperties lafColorProperty) {
		return UIManager.getColor(lafColorProperty.getName());
	}

	public static final Font getFont(SpecialELabProperties lafFontProperty) {
		return UIManager.getFont(lafFontProperty.getName());
	}
}
