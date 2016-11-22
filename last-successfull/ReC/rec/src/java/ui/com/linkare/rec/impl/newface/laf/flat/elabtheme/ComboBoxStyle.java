package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.FlatComboBoxUI;
import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;

public class ComboBoxStyle extends DefaultStyle {

	public static final String ID = "ComboBox";

	// LABEL FONT
	public static final Font FONT_COMBOBOX = new FontUIResource("Arial", Font.BOLD, 12);

	// PROPERTIES
	private static final String PT_COMBOBOX_BG = "background";
	private static final String PT_COMBOBOX_FG = "foreground";
	private static final String PT_SELECTIONBACKGROUND = "selectionBackground";
	private static final String PT_SELECTIONFOREGROUND = "selectionForeground";

	// COLORS
	private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x8CABB3);
	private static final Color COLOR_COMBOBOX_FG = DefaultStyle.BLACK_DEFAULT_COLOR;
	private static final Color COLOR_COMBOBOX_BG = DefaultStyle.WHITE_DEFAULT_COLOR;
	private static final Color COLOR_COMBOBOX_FONT = new Color(0x64797F);
	private static final Color COLOR_SELECTION_BG = new Color(0xFCEFCD);
	private static final Color COLOR_SELECTION_FG = DefaultStyle.BLACK_DEFAULT_COLOR;
	private static final Color COLOR_ARROW_ENABLED = DefaultStyle.WHITE_DEFAULT_COLOR;
	private static final Color COLOR_ARROW_DISABLED = DefaultStyle.WHITE_DEFAULT_COLOR;

	// BORDER
	private static final Border BORDER_COMBOBOX = BorderFactory
			.createLineBorder(ComboBoxStyle.COLOR_BORDER_SOLID_THIN_BLUE);

	// COMBOBOXUI
	private static final FlatComboBoxUI flatComboBoxUI = null;

	/**
	 * Creates the <code>ComboBoxStyle</code>.
	 */
	public ComboBoxStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ComboBoxStyle.ID;
	}

	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(AbstractStyle.BORDER, ComboBoxStyle.BORDER_COMBOBOX);
		map.put(ComboBoxStyle.PT_COMBOBOX_FG, ComboBoxStyle.COLOR_COMBOBOX_FG);
		map.put(ComboBoxStyle.PT_COMBOBOX_BG, ComboBoxStyle.COLOR_COMBOBOX_BG);
		map.put(ComboBoxStyle.PT_SELECTIONBACKGROUND, ComboBoxStyle.COLOR_SELECTION_BG);
		map.put(ComboBoxStyle.PT_SELECTIONFOREGROUND, ComboBoxStyle.COLOR_SELECTION_FG);

		map.put(AbstractStyle.FONT, ComboBoxStyle.FONT_COMBOBOX);
	}
}
