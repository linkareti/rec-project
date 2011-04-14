package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.util.Map;

public class SliderStyle extends DefaultStyle {

	public static final String ID = "Slider";

	//COLORS	
	private static final Color COLOR_SLIDER_FG = new Color(0x515151);
	private static final Color COLOR_SLIDER_BG = new Color(0xE4EEED);

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
	}
}
