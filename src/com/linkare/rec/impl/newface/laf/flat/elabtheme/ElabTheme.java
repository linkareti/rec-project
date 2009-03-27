/* 
 * ElabTheme.java created on Jan 31, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.FlatTheme;

/**
 * @deprecated
 * @author Henrique Fernandes
 */
public class ElabTheme extends FlatTheme {

    private static final Color DISABLED_FOREGROUND_ON_DARK = new Color(0x677478);

    private static final Color ENABLED_FOREGROUND_ON_DARK = new Color(0xE4EEED);

    // ASK The SELECTION_FOREGROUND_ON_DARK color?
    private static final Color SELECTION_FOREGROUND_ON_DARK = WHITE;

    private static final Color PANEL_FOREGROUND = new Color(0x231F20);
    
    private static final Color PANEL_BACKGROUND = new Color(0xE4EEED);

    // ASK The color for BACKGROUND_LIGHTCOLOR
    private static final Color BACKGROUND_LIGHTCOLOR = new Color(0x2A3436);

    // ASK The color for BACKGROUND_DARKCOLOR
    private static final Color BACKGROUND_DARKCOLOR = new Color(0x0C1011);

	private static final Border TEXTFIELD_BORDER = BorderFactory.createLineBorder(Color.BLACK);
    
//    private static final Color CONTROL_FOREGROUND = new Color(0x000000);
//    
//    private static final Color CONTROL_BACKGROUND = new Color(0xDDDDDD);

    @Override
    public Color getDisabledForegroundOnDark() {
	return DISABLED_FOREGROUND_ON_DARK;
    }

    @Override
    public Color getEnabledForegroundOnDark() {
	return ENABLED_FOREGROUND_ON_DARK;
    }

    @Override
    public Color getSelectionForegroundOnDark() {
	return SELECTION_FOREGROUND_ON_DARK;
    }

    @Override
    public FontUIResource getControlBodyFont() {
	return null;
    }

    @Override
    public Color getPanelBackground() {
	return PANEL_BACKGROUND;
    }

    @Override
    public Color getPanelForeground() {
	return PANEL_FOREGROUND;
    }

    @Override
    public Color getControlForeground() {
	return PANEL_FOREGROUND;
    }

    @Override
    public Color getBackgroundDarkColor() {
	return BACKGROUND_DARKCOLOR;
    }

    @Override
    public Color getBackgroundLightColor() {
	return BACKGROUND_LIGHTCOLOR;
    }

    @Override
    public Color getForegroundOnDark() {
	return WHITE;
    }

	@Override
	public Border getTextfieldBorder() {
		return TEXTFIELD_BORDER;
	}

}
