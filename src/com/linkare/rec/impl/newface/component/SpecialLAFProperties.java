/* 
 * SpecialLAFProperties.java created on Mar 11, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

/**
 * The special LAF properties bridge. Use this holder to define special
 * resources provided on the default ReC Theme on Flat Look and Feel.
 * 
 * @author Henrique Fernandes
 */
public enum SpecialLAFProperties {

	ENABLED_FOREGROUND_ON_DARK("enabledForegroundOnDark"), 
	GRADIENT_LIGHTCOLOR("gradientLightColor"), 
	GRADIENT_DARKCOLOR("gradientDarkColor");

	private String name;

	SpecialLAFProperties(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}