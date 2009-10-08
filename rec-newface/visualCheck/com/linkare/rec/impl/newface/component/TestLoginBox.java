/* 
 * TestLoginBox.java created on Mar 10, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.ReCApplication;
import org.jdesktop.application.Application;

/**
 * @author Henrique Fernandes
 */
public class TestLoginBox extends ReCApplication {

    public TestLoginBox() {
	// Required for AppFramework instatiation
    }

    @Override
    protected void showView() {
	DefaultDialog<SimpleLoginBox> dialog = new DefaultDialog<SimpleLoginBox>(new SimpleLoginBox());
	//dialog.setDefaultCloseOperation(DefaultDialog.EXIT_ON_CLOSE);
	dialog.setVisible(true);
	System.exit(0);
    }

    public static void main(String[] args) {
	Application.launch(TestLoginBox.class, args);
    }

}
