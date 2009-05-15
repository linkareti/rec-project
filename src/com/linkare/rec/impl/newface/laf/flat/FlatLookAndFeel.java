/* 
 * FlatLookAndFeel.java created on Jan 31, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.metal.MetalLookAndFeel;

import com.linkare.rec.impl.newface.laf.flat.elabtheme.ElabTheme;
import com.linkare.rec.impl.newface.laf.flat.theme.FlatTheme;
import javax.swing.plaf.basic.BasicMenuUI;

/**
 * Implements a Flat LookAndFeel targeted for web.
 * 
 * @author Henrique Fernandes
 */
public class FlatLookAndFeel extends 
//	SynthLookAndFeel // The LAF from Java 5
	MetalLookAndFeel // The current base LAF for Flat 
//BasicLookAndFeel // The initial default Base LAF for FlatLookAndFeel. 
{

	private static final Logger log = Logger.getLogger(FlatLookAndFeel.class.getName());

	private static final long serialVersionUID = 3410155899630816365L;

	private FlatTheme theme;

    /** Creates a new FlatLookAndFeel with the default ElabTheme */
	public FlatLookAndFeel() {
		this(new ElabTheme());
	}

	/**
	 * Creates a new {@link FlatLookAndFeel} with the given Theme.
	 * @param theme 
	 */
	public FlatLookAndFeel(FlatTheme theme) {
		this.theme = theme;
        FlatFocusRenderer.install(); // Setup focus
	}

	@Override
	public String getID() {
		return "Flat";
	}

	@Override
	public String getName() {
		return "FlatLaf";
	}

	@Override
	public String getDescription() {
		return "Flat Look And Feel";
	}

	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}

	public FlatTheme getTheme() {
		return theme;
	}

	@Override
	protected void initClassDefaults(UIDefaults table)
	{
		super.initClassDefaults(table);

		Object[] classDefaults = {
				"SplitPaneUI", BasicSplitPaneUI.class.getName(),
				"ScrollBarUI", FlatScrollBarUI.class.getName(),
				"ComboBoxUI", FlatComboBoxUI.class.getName(),
				"TabbedPaneUI", FlatTabbedPaneUI.class.getName(),
				"MenuUI", BasicMenuUI.class.getName(),
				"FlatButtonUI", FlatButtonUI.class.getName(),
				"ButtonUI", FlatOriginalButtonUI.class.getName(),
				"RadioButtonUI", FlatRadioButtonUI.class.getName(),
				"CheckBoxUI", FlatCheckBoxUI.class.getName(),
				"SliderUI", FlatSliderUI.class.getName(),
		};

		if (log.isLoggable(Level.FINER)) {
			log.finer("FlatLookAndFeel Class Defaults: " + Arrays.deepToString(classDefaults));
		}

		table.putDefaults(classDefaults);
	}

	@Override
	protected void initComponentDefaults(UIDefaults table) {
		super.initComponentDefaults(table);
		// TODO Set key bindings like Nimbus LAF
	}

	@Override
	public UIDefaults getDefaults() {
		UIDefaults table = super.getDefaults();
		theme.addCustomEntriesToTable(table);
		return table;
	}
}
