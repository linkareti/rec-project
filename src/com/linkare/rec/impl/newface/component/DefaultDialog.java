/* 
 * DefaultDialog.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import javax.swing.JDialog;

/**
 * Represents the default dialog and launcher for the ReC Application.
 * <p>
 * All common dialogs must be launched using <code>DefaultDialog</code>.
 * 
 * @param <C>
 *            The dialog content type
 * 
 * @author Henrique Fernandes
 */
public class DefaultDialog<C extends AbstractContentPane> extends JDialog implements ContentPaneListener {

    private static final long serialVersionUID = -4557205313830746322L;

    private C content;

    public DefaultDialog(C content) {
	super();
	this.content = content;

	init();
	setModal(true);
    }

    protected void init() {
	content.addContentPaneListener(this);
	setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
	add(content);
	pack();
	setLocationRelativeTo(null);
    }

    /**
     * @return the dialog content.
     */
    public C getContent() {
	return content;
    }

    @Override
    public void contentPaneClose(Object evt) {
	setVisible(false);
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
    public static int showUnexpectedErrorPane(Exception error) {
	DefaultDialog<UnexpectedErrorPane> dialog = new DefaultDialog<UnexpectedErrorPane>(new UnexpectedErrorPane(
		error));

	dialog.setVisible(true);
	return dialog.getContent().getActionValue();
    }

}
