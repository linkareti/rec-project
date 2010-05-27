/* 
 * ListStyle.java created on 2009/04/30
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import java.util.Map;

/**
 * @deprecated
 * @author João FLorindo
 */
@Deprecated
public class TableStyle extends DefaultStyle {

	//ENABLED COLORS

	//LABEL FONT

	public static final String ID = "Table";

	/**
	 * Creates the <code>TableStyle</code>.
	 */
	public TableStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	@Override
	public void updatePropertyMap(Map<String, Object> map) {
		super.updatePropertyMap(map);
	}
}
