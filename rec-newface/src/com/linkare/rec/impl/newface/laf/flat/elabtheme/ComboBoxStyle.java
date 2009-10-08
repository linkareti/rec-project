package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.FlatComboBoxUI;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;
import com.sun.media.ui.ComboBox;

public class ComboBoxStyle extends DefaultStyle {

    public static final String ID = "ComboBox";

    //LABEL FONT
    public static final Font FONT_COMBOBOX = new FontUIResource("Arial", Font.BOLD, 12);

    //PROPERTIES
    private static final String PT_COMBOBOX_BG = "background";
    private static final String PT_COMBOBOX_FG = "foreground";
    private static final String PT_SELECTIONBACKGROUND = "selectionBackground";
    private static final String PT_SELECTIONFOREGROUND = "selectionForeground";

    //COLORS	
    private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x8CABB3);
    private static final Color COLOR_COMBOBOX_FG = BLACK_DEFAULT_COLOR;
    private static final Color COLOR_COMBOBOX_BG = WHITE_DEFAULT_COLOR;
    private static final Color COLOR_COMBOBOX_FONT = new Color(0x64797F);
    private static final Color COLOR_SELECTION_BG = new Color(0xFCEFCD);
    private static final Color COLOR_SELECTION_FG = BLACK_DEFAULT_COLOR;
    private static final Color COLOR_ARROW_ENABLED = WHITE_DEFAULT_COLOR;
    private static final Color COLOR_ARROW_DISABLED = WHITE_DEFAULT_COLOR;

    //BORDER
    private static final Border BORDER_COMBOBOX = BorderFactory.createLineBorder(COLOR_BORDER_SOLID_THIN_BLUE);

    //COMBOBOXUI
    private static final FlatComboBoxUI flatComboBoxUI = null;

    /**
     * Creates the <code>ComboBoxStyle</code>.
     */
    public ComboBoxStyle() {
	super();
    }

    @Override
    protected String defineStyleId() {
	return ID;
    }

    @Override
    public void updatePropertyMap(Map<String, Object> map) {
	super.updatePropertyMap(map);
	map.put(BORDER, BORDER_COMBOBOX);
	map.put(PT_COMBOBOX_FG, COLOR_COMBOBOX_FG);
	map.put(PT_COMBOBOX_BG, COLOR_COMBOBOX_BG);
	map.put(PT_SELECTIONBACKGROUND, COLOR_SELECTION_BG);
	map.put(PT_SELECTIONFOREGROUND, COLOR_SELECTION_FG);

	map.put(FONT, FONT_COMBOBOX);
    }
}
