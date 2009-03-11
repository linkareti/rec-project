/* 
 * TestLoginBox.java created on Mar 10, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.UserInterfaceTest;

/**
 * @author Henrique Fernandes
 */
public class TestLoginBox extends UserInterfaceTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
	new TestLoginBox().run();
    }
    
    @Override
    public Runnable userInterface() {
	return new Runnable(){
	    @Override public void run() {
		DefaultDialog<LoginBox> dialog = new DefaultDialog<LoginBox>(new LoginBox());
		dialog.setVisible(true);
		System.exit(STATUS_SUCCESS);
	    }
	};
    }

}
