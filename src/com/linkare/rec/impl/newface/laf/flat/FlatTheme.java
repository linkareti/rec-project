/* 
 * FlatTheme.java created on Feb 25, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.FontUIResource;

/**
 * @deprecated
 * @author Henrique Fernandes
 * @author João Florindo
 */
public abstract class FlatTheme {
    
    /**
     * UIDefaults Keys
     */
    public enum UIResourceKeyEnum {
	MENUBAR_BORDER_KEY("MenuBar.border"),
	MENUITEM_DISABLEDFOREGROUND_KEY("MenuItem.disabledForeground"),
	MENUITEM_FOREGROUND_KEY("MenuItem.foreground"),
	MENUITEM_SELECTIONFOREGROUND_KEY("MenuItem.selectionForeground"),
	TOOLBAR_BACKGROUND_KEY("ToolBar.background"),
	TOOLBAR_BORDER_KEY("ToolBar.border"), 
	PANEL_BACKGROUND("Panel.background"), 
	PANEL_FOREGROUND("Panel.foreground"), 
	PANEL_FONT("Panel.font"), 
	BACKGROUND_DARKCOLOR("Background.darkColor"),
	BACKGROUND_LIGHTCOLOR("Background.lightColor"), 
	ENABLED_FOREGROUND_ON_DARK("enabledForegroundOnDark"), 
	TEXTFIELD_BORDER("TextField.border");
	
	private String name;

	UIResourceKeyEnum(String resourceName) {
	    this.name = resourceName;
	}

	public String getName() {
	    return name;
	}
    }

    // Use these borders for debug only
    
    public static final BorderUIResource DEBUG_RED_BORDER = new BorderUIResource(BorderFactory.createLineBorder(Color.RED));
    
    public static final BorderUIResource DEBUG_GREEN_BORDER = new BorderUIResource(BorderFactory.createLineBorder(Color.GREEN));
    
    public static final BorderUIResource DEBUG_BLUE_BORDER = new BorderUIResource(BorderFactory.createLineBorder(Color.BLUE));
    
    
    // Global Resources
    
    public static final Color WHITE = new Color(0xFFFFFF);
    
    public static final Color BLACK = new Color(0x000000);

    public static final BorderUIResource EMPTY_BORDER = new BorderUIResource(BorderFactory.createEmptyBorder());

    /**
     * Add this theme's custom entries to the defaults table.
     * 
     * @param table the defaults table, non-null
     */
    public void addCustomEntriesToTable(UIDefaults table) {
    	
	Object[] defaults = new Object[] {
		// General
		UIResourceKeyEnum.ENABLED_FOREGROUND_ON_DARK.getName(), 
			getEnabledForegroundOnDark(),
			
		// Controls
		UIResourceKeyEnum.TEXTFIELD_BORDER.getName(), 
			getTextfieldBorder(),
		
		// Background Gradient
		UIResourceKeyEnum.BACKGROUND_DARKCOLOR.getName(), 
			getBackgroundDarkColor(),
		UIResourceKeyEnum.BACKGROUND_LIGHTCOLOR.getName(),
			getBackgroundLightColor(),
		
		// MenuBar
		"MenuBarUI", FlatMenuBarUI.class.getName(), // Hack for MetalLookAndFeel inheritance
		UIResourceKeyEnum.MENUBAR_BORDER_KEY.getName(), 
			getEmptyBorder(),
		UIResourceKeyEnum.MENUITEM_DISABLEDFOREGROUND_KEY.getName(),
			getDisabledForegroundOnDark(),
		UIResourceKeyEnum.MENUITEM_FOREGROUND_KEY.getName(),
			getEnabledForegroundOnDark(),
		UIResourceKeyEnum.MENUITEM_SELECTIONFOREGROUND_KEY.getName(), 
			getSelectionForegroundOnDark(),
			
		// ToolBar
		UIResourceKeyEnum.TOOLBAR_BACKGROUND_KEY.getName(), 
			getBlack(),
		UIResourceKeyEnum.TOOLBAR_BORDER_KEY.getName(), 
			getEmptyBorder(),
		
		// Panel
		UIResourceKeyEnum.PANEL_BACKGROUND.getName(), 
			getPanelBackground(),
		UIResourceKeyEnum.PANEL_FOREGROUND.getName(),
			getPanelForeground(),
			
//		UIResourceKeyEnum.PANEL_FONT.getName(),
//			getControlBodyFont(),
			
	};

	table.putDefaults(defaults);
    }
    
    public abstract Border getTextfieldBorder();

	public abstract Color getControlForeground();
    
    public abstract FontUIResource getControlBodyFont();

    public abstract Color getPanelForeground();

    public abstract Color getPanelBackground();

    private BorderUIResource getEmptyBorder() {
	return EMPTY_BORDER;
    }

    private Color getBlack() {
	return BLACK;
    }
    
    public abstract Color getDisabledForegroundOnDark();
    
    public abstract Color getEnabledForegroundOnDark();
    
    public abstract Color getSelectionForegroundOnDark();
    
    public abstract Color getBackgroundLightColor();
    
    public abstract Color getBackgroundDarkColor();
    
    public abstract Color getForegroundOnDark();
}
