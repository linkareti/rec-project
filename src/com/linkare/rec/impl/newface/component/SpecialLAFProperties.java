/* 
 * SpecialLAFProperties.java created on Mar 11, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

/**
 * 
 * @author Henrique Fernandes
 */
public enum SpecialLAFProperties {
    
    ENABLED_FOREGROUND_ON_DARK("enabledForegroundOnDark"),
    BACKGROUND_LIGHTCOLOR("Background.lightColor"),
    BACKGROUND_DARKCOLOR("Background.darkColor"),
    ;

    private String name;

    SpecialLAFProperties(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }
}