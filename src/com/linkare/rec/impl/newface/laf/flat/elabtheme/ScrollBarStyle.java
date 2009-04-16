/* 
 * ScrollBarStyle.java created on 2009/04/14
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.awt.Color;
import java.awt.Font;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.plaf.DimensionUIResource;
import javax.swing.plaf.FontUIResource;

/**
 * 
 * @author João FLorindo
 */
public class ScrollBarStyle extends DefaultStyle{

	public static final String ID = "ScrollBar";
	
	//PROPERTIES
	public static final String PT_WIDTH = "width";
	public static final String PT_BACKGROUND = "background";
	public static final String PT_FOREGROUND = "foreground";
	public static final String PT_THUMBSHADOW = "thumbShadow";
	public static final String PT_HIGHLIGHT = "highlight";
	public static final String PT_THUMB = "thumb";
	public static final String PT_TRACKHIGHLIGHT = "trackHighlight";
	public static final String PT_THUMBDARKSHADOW = "thumbDarkShadow";
	public static final String PT_MINIMUMTHUMBSIZE = "minimumThumbSize";
	public static final String PT_THUMBHEIGHT = "thumbHeight";
	
	
	
	//COLORS
	public static final Color COLOR_BACKGROUND = new Color(0xDAE1DF);
	public static final Color COLOR_FOREGROUND = new Color(0x848485);
	
	
//	
	
	
	
	/**
	 * Creates the <code>ScrollBarStyle</code>.
	 */
	public ScrollBarStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(PT_BACKGROUND, COLOR_BACKGROUND);
		map.put(PT_FOREGROUND, COLOR_FOREGROUND);
		map.put(PT_WIDTH, 40);
		map.put(PT_THUMBSHADOW, COLOR_BACKGROUND);
		map.put(PT_THUMB, COLOR_BACKGROUND);
		map.put(PT_TRACKHIGHLIGHT, COLOR_BACKGROUND);

	}
	
	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)
	

}
