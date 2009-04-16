/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * TestComponents.java
 *
 * Created on 15/Abr/2009, 22:45:39
 */

package com.linkare.rec.impl.newface.component;

import com.linkare.rec.impl.newface.UserInterfaceTest;

/**
 *
 * @author JOE
 */
public class TestComponents extends UserInterfaceTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestComponents().run();
	}

	@Override
	public void runUserInterface() {
		DefaultDialog<FormComponents> dialog = new DefaultDialog<FormComponents>(new FormComponents());
		dialog.setVisible(true);
		System.exit(STATUS_SUCCESS);
	}

}
