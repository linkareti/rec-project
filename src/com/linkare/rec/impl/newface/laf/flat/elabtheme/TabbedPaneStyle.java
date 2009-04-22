
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.FlatComboBoxUI;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;
import com.sun.media.ui.ComboBox;


public class TabbedPaneStyle extends DefaultStyle{

	public static final String ID = "TabbedPane";
		
	//LABEL FONT
	public static final Font FONT_TABBEDPANE = new FontUIResource("Arial", Font.BOLD, 10);	
		
	//PROPERTIES
	private static final String  PT_FONT  = "font";	

	/**
	 * Creates the <code>ComboBoxStyle</code>.
	 */
	public TabbedPaneStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(PT_FONT, FONT_TABBEDPANE);
	}
}
