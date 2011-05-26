/* 
 * DefaultStyle.java created on Mar 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.FontUIResource;
import javax.xml.bind.annotation.XmlAttribute;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

/**
 * Represents Elab's style default values for all components.
 * 
 * @author Henrique Fernandes
 */
public class DefaultStyle extends AbstractStyle {

	// -------------------------------------------------------------------------
	// Common values

	// Debug

	// public static final BorderUIResource DEBUG_RED_BORDER = new
	// BorderUIResource(BorderFactory
	// .createLineBorder(Color.RED));
	// public static final BorderUIResource DEBUG_GREEN_BORDER = new
	// BorderUIResource(BorderFactory
	// .createLineBorder(Color.GREEN));
	// public static final BorderUIResource DEBUG_BLUE_BORDER = new
	// BorderUIResource(BorderFactory
	// .createLineBorder(Color.BLUE));

	// Colors

	public static final Color WHITE_DEFAULT_COLOR = new Color(0xFFFFFF);

	public static final Color BLACK_DEFAULT_COLOR = new Color(0x000000);

	public static final Color DEFAULT_FOREGROUND = new Color(0x231F20);

	public static final Color SELECTION_BACKGROUND_DEFAULT_COLOR = new Color(0xFFECC6);

	public static final Color SELECTION_FOREGROUND_DEFAULT_COLOR = new Color(0x231F20);

	// Fonts

	public static final FontUIResource DEFAULT_FONT = new FontUIResource("Arial", Font.PLAIN, 13);

	// Borders

	public static final Border SOLID_THIN_BLACK_BORDER = BorderFactory
			.createLineBorder(DefaultStyle.BLACK_DEFAULT_COLOR);

	public static final BorderUIResource EMPTY_BORDER = new BorderUIResource(BorderFactory.createEmptyBorder());

	public static final BorderUIResource EMPTY_BORDER_MARGIN_2 = new BorderUIResource(BorderFactory.createEmptyBorder(
			4, 6, 4, 6));
	public static final BorderUIResource EMPTY_BORDER_MARGIN_4 = new BorderUIResource(BorderFactory.createEmptyBorder(
			6, 4, 4, 4));

	/**
	 * Creates the <code>DefaultStyle</code>.
	 */
	public DefaultStyle() {
		super();
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		// Common properties definition
		map.put(AbstractStyle.FOREGROUND, DefaultStyle.DEFAULT_FOREGROUND);
		map.put(AbstractStyle.FONT, DefaultStyle.DEFAULT_FONT);
		map.put(AbstractStyle.SELECTION_BACKGROUND, DefaultStyle.SELECTION_BACKGROUND_DEFAULT_COLOR);
		map.put(AbstractStyle.SELECTION_FOREGROUND, DefaultStyle.SELECTION_FOREGROUND_DEFAULT_COLOR);
		map.put(AbstractStyle.DEFAULT_WHITE, DefaultStyle.WHITE_DEFAULT_COLOR);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

	/**
	 * @return The Foreground property value as String.
	 */
	@XmlAttribute
	public String getForeground() {
		return getPropertyMap().get(AbstractStyle.FOREGROUND).toString();
	}

	/**
	 * @return The Font property value as String.
	 */
	@XmlAttribute
	public String getFont() {
		return getPropertyMap().get(AbstractStyle.FONT).toString();
	}

	// (...)

}
