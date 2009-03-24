/* 
 * DefaultDialog.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

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
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(DefaultDialog.class.getName());

	private C content;

	public DefaultDialog(C content) {
		this(content, null);
	}
	
	public DefaultDialog(C content, Component container) {
		super();
		this.content = content;
		init();
		setLocationRelativeTo(container);
	}

	protected void init() {
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		add(content);
		pack();
		setModal(true);
	}

	/**
	 * @return the dialog content.
	 */
	public C getContent() {
		return content;
	}

	// -------------------------------------------------------------------------
	// Static

	/**
	 * Shows an unexpected error friendly dialog. (Modal)
	 * 
	 * @param error
	 *            The unexpected exception error.
	 * 
	 * @return The <code>UnexpectedErrorPane</code> action command.
	 * @see UnexpectedErrorPane
	 */
	public static String showUnexpectedErrorPane(Exception error) {
		final DefaultDialog<UnexpectedErrorPane> dialog = new DefaultDialog<UnexpectedErrorPane>(new UnexpectedErrorPane(
				error));
		dialog.getContent().addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false); // for any action
			}
		});
		dialog.setVisible(true);
		return dialog.getContent().getActionValue();
	}

}
