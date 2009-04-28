/* 
 * DefaultDialog.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;

import javax.swing.JDialog;

/**
 * Represents the default dialog for the ReC Application.
 * <p>
 * All common dialogs must be launched using <code>DefaultDialog</code>.
 * 
 * @param <C>
 *            The dialog content type.
 * 
 * @author Henrique Fernandes
 */
public class DefaultDialog<C extends AbstractContentPane> extends JDialog {

	private static final long serialVersionUID = -4557205313830746322L;

	protected C content;

    protected Component container;

	public DefaultDialog(C content) {
		this(content, null);
	}
	
	public DefaultDialog(C content, Component container) {
		super();
        this.container = container;
		this.content = content;
        this.content.setContainer(this);
        add(content);
        init();
	}

	/**
	 * @return the dialog content.
	 */
	public C getContent() {
		return content;
	}

    protected void init() {
        setPreferredSize(content.getPreferredSize());
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		pack();
		setModal(true);
		setLocationRelativeTo(container);
    }

}
