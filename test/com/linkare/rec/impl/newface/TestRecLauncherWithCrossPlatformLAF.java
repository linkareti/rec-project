/* 
 * TestRecLauncherWithFlatLAF.java created on Mar 10, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface;

import javax.swing.UIManager;

/**
 * @author Henrique Fernandes
 */
public class TestRecLauncherWithCrossPlatformLAF extends UserInterfaceTest {

	public static void main(String[] args) {
		new TestRecLauncherWithCrossPlatformLAF().run();
	}

	@Override
	protected void initLafClassName() {
		setLafClassName(UIManager.getCrossPlatformLookAndFeelClassName());
	}

}
