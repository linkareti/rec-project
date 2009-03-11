/* 
 * UndecoratedDialog.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

/**
 * An Undecorated Dialog and launcher for the ReC Application. <p>
 * 
 * @param <C> The dialog content type
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

    /*
     * @see com.linkare.rec.impl.newface.component.DefaultDialog#initComponents()
     */
    @Override
    protected void init() {
	setUndecorated(true);
	super.init();
    }
    
    /**
     * Shows an unexpected error friendly dialog. (Modal)
     * 
     * @param error The unexpected exception error.
     * 
     * @return The <code>UnexpectedErrorPane</code> result.
     * @see UnexpectedErrorPane
     */
    public static int showUnexpectedErrorPane(Exception error) {
	UndecoratedDialog<UnexpectedErrorPane> dialog = 
	    new UndecoratedDialog<UnexpectedErrorPane>(new UnexpectedErrorPane(error));
	
	dialog.setModal(true);
	dialog.setVisible(true);
	return dialog.getContent().getActionValue();
    }
}
