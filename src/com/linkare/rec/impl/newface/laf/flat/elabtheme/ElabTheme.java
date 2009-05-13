/* 
 * ElabTheme2.java created on Mar 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Scrollbar;
import java.util.HashSet;
import java.util.Set;

import javax.swing.UIDefaults;
import javax.xml.bind.annotation.XmlRootElement;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.FlatTheme;

/**
 * 
 * @author hfernandes
 */
@XmlRootElement
public class ElabTheme extends FlatTheme {

	// -------------------------------------------------------------------------
	// Custom theme properties

	public static final String GRADIENT_DARKCOLOR = "gradientDarkColor";
	private static final Color GRADIENT_DARKCOLOR_VALUE = new Color(0x0C1011);
	
	public static final String GRADIENT_LIGHTCOLOR = "gradientLightColor";
	private static final Color GRADIENT_LIGHTCOLOR_VALUE = new Color(0x2A3436);
	
	public static final String ENABLED_FOREGROUND_ON_DARK = "enabledForegroundOnDark";
	private static final Color ENABLED_FOREGROUND_ON_DARK_VALUE = new Color(0x677478);
	
	public static final String DISABLED_FOREGROUND_ON_DARK = "disabledForegroundOnDark";
    private static final Color DISABLED_FOREGROUND_ON_DARK_VALUE = new Color(0xE4EEED);
    
    public static final String SELECTION_FOREGROUND_ON_DARK = "selectionForegroundOnDark";
	private static final Color SELECTION_FOREGROUND_ON_DARK_VALUE = DefaultStyle.WHITE_DEFAULT_COLOR;
    
	/**
	 * Creates the <code>ElabTheme2</code>.
	 */
	public ElabTheme() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCustomEntriesToTable(UIDefaults table) {
		super.addCustomEntriesToTable(table);
		
		// Add custom theme entries to ui defaults
		Object[] defaults = new Object[] {
			GRADIENT_DARKCOLOR, GRADIENT_DARKCOLOR_VALUE,
			GRADIENT_LIGHTCOLOR, GRADIENT_LIGHTCOLOR_VALUE,
			ENABLED_FOREGROUND_ON_DARK, ENABLED_FOREGROUND_ON_DARK_VALUE,
			DISABLED_FOREGROUND_ON_DARK, DISABLED_FOREGROUND_ON_DARK_VALUE,
			SELECTION_FOREGROUND_ON_DARK, SELECTION_FOREGROUND_ON_DARK_VALUE,
		};
		table.putDefaults(defaults);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<Class<? extends AbstractStyle>> registerStyles() {
		Set<Class<? extends AbstractStyle>> result = new HashSet<Class<? extends AbstractStyle>>();
		
		result.add(DefaultStyle.class);
		result.add(MenuBarStyle.class);
		result.add(MenuItemStyle.class);
		result.add(PopupMenuStyle.class);
		result.add(SeparatorStyle.class);
		result.add(PanelStyle.class);
		result.add(TextFieldStyle.class);
		result.add(PasswordFieldStyle.class);
		result.add(ToolBarStyle.class);
		result.add(LabelStyle.class);
		result.add(MenuStyle.class);
		result.add(SplitPaneDividerStyle.class);
		result.add(ScrollBarStyle.class);
		result.add(ComboBoxStyle.class);
		result.add(TabbedPaneStyle.class);
		result.add(RadioButtonMenuItemStyle.class);
		result.add(CheckBoxMenuItemStyle.class);
		result.add(FlatButtonStyle.class);
		//result.add(ButtonStyle.class);
		result.add(RadioButtonStyle.class);
		result.add(CheckBoxStyle.class);
		result.add(SliderStyle.class);
		result.add(TableStyle.class);
		
		
		return result;
	}
	
}
