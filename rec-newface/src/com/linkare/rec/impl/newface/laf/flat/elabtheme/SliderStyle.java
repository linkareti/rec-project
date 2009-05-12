
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.border.Border;
import javax.swing.plaf.FontUIResource;

import com.linkare.rec.impl.newface.laf.flat.FlatComboBoxUI;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;
import com.sun.media.ui.ComboBox;


public class SliderStyle extends DefaultStyle{

	public static final String ID = "Slider";
		
	//LABEL FONT
	public static final Font FONT_SLIDER = new FontUIResource("Arial", Font.BOLD, 12);	
	
	//COLORS	
	private static final Color COLOR_SLIDER_FG = BLACK_DEFAULT_COLOR;
	private static final Color COLOR_SLIDER_BG = WHITE_DEFAULT_COLOR;
	

	//DIMENSION
	private static final String TRACKWIDTH = "trackWidth";
	private static final String VERTICALSIZE = "verticalSize";
	
	/**
	 * Creates the <code>ComboBoxStyle</code>.
	 */
	public SliderStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(FOREGROUND, COLOR_SLIDER_FG);
		map.put(BACKGROUND, COLOR_SLIDER_BG);
		map.put(FONT, FONT_SLIDER);
	}
}
