/* 
 * DefaultDialog.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Window;

import javax.swing.JDialog;
import javax.swing.WindowConstants;

/**
 * Represents the default dialog for the ReC Application.
 * <p>
 * All common dialogs must be launched using <code>DefaultDialog</code>.
 * 
 * @param <C> The dialog content type.
 * 
 * @author Henrique Fernandes
 */
public class DefaultDialog<C extends AbstractContentPane> extends JDialog {

	private static final long serialVersionUID = -4557205313830746322L;

	protected C content;

	public DefaultDialog(final C content) {
		this(null, "", content);
	}

	public DefaultDialog(final Window owner, final String title, final C content) {
		super(owner, title);
		this.content = content;
		this.content.setContainer(this);
		add(content);
		init();
	}

	protected void init() {
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setModal(true);
	}

	/**
	 * @return the dialog content.
	 */
	public C getContent() {
		return content;
	}

}
