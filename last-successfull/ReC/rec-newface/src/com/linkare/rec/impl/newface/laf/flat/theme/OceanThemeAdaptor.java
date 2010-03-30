/* 
 * OceanThemeAdaptor.java created on 2009/04/01
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.theme;

import java.util.Arrays;

import javax.swing.UIDefaults;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

/**
 * @author Henrique Fernandes
 */
public class OceanThemeAdaptor extends OceanTheme {

	@Override
	public void addCustomEntriesToTable(UIDefaults table) {
		super.addCustomEntriesToTable(table);

		// .30 0 DDE8F3 white secondary2
		java.util.List buttonGradient = Arrays.asList(new Object[] { new Float(.3f), new Float(0f), new ColorUIResource(0xd6e2e0),
				getWhite(), new ColorUIResource(0xd6e2e0) });

		Object[] defaults = new Object[] {
				"Button.gradient",
				buttonGradient,

				// 0c1011 -> 2a3436 
				"MenuBar.gradient",
				Arrays.asList(new Object[] { new Float(1f), new Float(0f), new ColorUIResource(0x0c1011), new ColorUIResource(0x2a3436),
						new ColorUIResource(0x2a3436) }), };
		table.putDefaults(defaults);
	}

	@Override
	protected ColorUIResource getPrimary3() {
		return new ColorUIResource(0xd6e2e0);
	}
}
