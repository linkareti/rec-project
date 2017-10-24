/* 
 * TextFieldStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.util.Map;

import com.linkare.rec.impl.newface.laf.flat.theme.AbstractStyle;
import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class LabelStyle extends DefaultStyle {

	public static final String ID = "Label";

	/**
	 * Creates the <code>TextFieldStyle</code>.
	 */
	public LabelStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return LabelStyle.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updatePropertyMap(final Map<String, Object> map) {
		super.updatePropertyMap(map);
		map.remove(AbstractStyle.FOREGROUND);
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}
