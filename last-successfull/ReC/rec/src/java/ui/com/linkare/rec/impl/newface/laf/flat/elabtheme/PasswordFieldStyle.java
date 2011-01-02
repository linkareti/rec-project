/* 
 * PasswordFieldStyle.java created on Mar 27, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.elabtheme;

import com.linkare.rec.impl.newface.laf.flat.theme.Style;

/**
 * @author Henrique Fernandes
 */
@Style
public class PasswordFieldStyle extends TextFieldStyle {

	public static final String ID = "PasswordField";

	/**
	 * Creates the <code>TextFieldStyle</code>.
	 */
	public PasswordFieldStyle() {
		super();
	}

	@Override
	protected String defineStyleId() {
		return ID;
	}

	// -------------------------------------------------------------------------
	// TODO Getters (Used to marshal values to xml)

}
