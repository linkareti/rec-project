/* 
 * UndecoratedDialog.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;

/**
 * An Undecorated Dialog for the ReC Application.
 * <p>
 * 
 * @param <C>
 *            The dialog content type
 * 
 * @author Henrique Fernandes
 */
public class UndecoratedDialog<C extends AbstractContentPane> extends DefaultDialog<C> {

	private static final long serialVersionUID = -605288573925533710L;

	/**
	 * @param content
	 */
	public UndecoratedDialog(C content) {
		super(content);
	}

	public UndecoratedDialog(C content, Component container) {
		super(content, container);
	}

	@Override
	protected void init() {
		setUndecorated(true);
		super.init();
	}

}
