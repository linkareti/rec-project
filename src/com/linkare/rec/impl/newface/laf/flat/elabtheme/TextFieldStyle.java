/* 
 * TextFieldStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class TextFieldStyle extends DefaultStyle {
	
	public static final String ID = "TextField";
	
	/**
	 * Creates the <code>TextFieldStyle</code>.
	 */
	public TextFieldStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.put(BORDER, SOLID_THIN_BLACK_BORDER);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)
	
}
