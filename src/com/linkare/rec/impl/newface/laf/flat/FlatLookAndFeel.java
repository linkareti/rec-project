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

import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.text.DefaultEditorKit;

import com.linkare.rec.impl.newface.laf.flat.elabtheme.ElabTheme2;
import com.linkare.rec.impl.newface.laf.flat.theme.FlatTheme;

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

	/**
	 * Creates a new {@link FlatLookAndFeel} with the given Theme.
	 * @param theme 
	 */
	public FlatLookAndFeel(FlatTheme theme) {
		this.theme = theme;
	}

	/** Creates a new FlatLookAndFeel with the default ElabTheme */
	public FlatLookAndFeel() {
		theme = new ElabTheme2();
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
		
		// Key bindings are already set in the Metal LAF
//		Object fieldInputMap = new UIDefaults.LazyInputMap(new Object[] {
//				"ctrl C", DefaultEditorKit.copyAction,
//				"ctrl V", DefaultEditorKit.pasteAction,
//				"ctrl X", DefaultEditorKit.cutAction,
//				"COPY", DefaultEditorKit.copyAction,
//				"PASTE", DefaultEditorKit.pasteAction,
//				"CUT", DefaultEditorKit.cutAction,
//				"shift LEFT", DefaultEditorKit.selectionBackwardAction,
//				"shift KP_LEFT", DefaultEditorKit.selectionBackwardAction,
//				"shift RIGHT", DefaultEditorKit.selectionForwardAction,
//				"shift KP_RIGHT", DefaultEditorKit.selectionForwardAction,
//				"ctrl LEFT", DefaultEditorKit.previousWordAction,
//				"ctrl KP_LEFT", DefaultEditorKit.previousWordAction,
//				"ctrl RIGHT", DefaultEditorKit.nextWordAction,
//				"ctrl KP_RIGHT", DefaultEditorKit.nextWordAction,
//				"ctrl shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
//				"ctrl shift KP_LEFT", DefaultEditorKit.selectionPreviousWordAction,
//				"ctrl shift RIGHT", DefaultEditorKit.selectionNextWordAction,
//				"ctrl shift KP_RIGHT", DefaultEditorKit.selectionNextWordAction,
//				"ctrl A", DefaultEditorKit.selectAllAction,
//				"HOME", DefaultEditorKit.beginLineAction,
//				"END", DefaultEditorKit.endLineAction,
//				"shift HOME", DefaultEditorKit.selectionBeginLineAction,
//				"shift END", DefaultEditorKit.selectionEndLineAction,
//				"BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
//				"ctrl H", DefaultEditorKit.deletePrevCharAction,
//				"DELETE", DefaultEditorKit.deleteNextCharAction,
//				"RIGHT", DefaultEditorKit.forwardAction,
//				"LEFT", DefaultEditorKit.backwardAction,
//				"KP_RIGHT", DefaultEditorKit.forwardAction,
//				"KP_LEFT", DefaultEditorKit.backwardAction,
//				"ENTER", JTextField.notifyAction,
//				"ctrl BACK_SLASH", "unselect"/*DefaultEditorKit.unselectAction*/,
//				"control shift O", "toggle-componentOrientation"/*DefaultEditorKit.toggleComponentOrientation*/
//		});
//
//		Object passwordInputMap = new UIDefaults.LazyInputMap(new Object[] {
//				"ctrl C", DefaultEditorKit.copyAction,
//				"ctrl V", DefaultEditorKit.pasteAction,
//				"ctrl X", DefaultEditorKit.cutAction,
//				"COPY", DefaultEditorKit.copyAction,
//				"PASTE", DefaultEditorKit.pasteAction,
//				"CUT", DefaultEditorKit.cutAction,
//				"shift LEFT", DefaultEditorKit.selectionBackwardAction,
//				"shift KP_LEFT", DefaultEditorKit.selectionBackwardAction,
//				"shift RIGHT", DefaultEditorKit.selectionForwardAction,
//				"shift KP_RIGHT", DefaultEditorKit.selectionForwardAction,
//				"ctrl LEFT", DefaultEditorKit.beginLineAction,
//				"ctrl KP_LEFT", DefaultEditorKit.beginLineAction,
//				"ctrl RIGHT", DefaultEditorKit.endLineAction,
//				"ctrl KP_RIGHT", DefaultEditorKit.endLineAction,
//				"ctrl shift LEFT", DefaultEditorKit.selectionBeginLineAction,
//				"ctrl shift KP_LEFT", DefaultEditorKit.selectionBeginLineAction,
//				"ctrl shift RIGHT", DefaultEditorKit.selectionEndLineAction,
//				"ctrl shift KP_RIGHT", DefaultEditorKit.selectionEndLineAction,
//				"ctrl A", DefaultEditorKit.selectAllAction,
//				"HOME", DefaultEditorKit.beginLineAction,
//				"END", DefaultEditorKit.endLineAction,
//				"shift HOME", DefaultEditorKit.selectionBeginLineAction,
//				"shift END", DefaultEditorKit.selectionEndLineAction,
//				"BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
//				"ctrl H", DefaultEditorKit.deletePrevCharAction,
//				"DELETE", DefaultEditorKit.deleteNextCharAction,
//				"RIGHT", DefaultEditorKit.forwardAction,
//				"LEFT", DefaultEditorKit.backwardAction,
//				"KP_RIGHT", DefaultEditorKit.forwardAction,
//				"KP_LEFT", DefaultEditorKit.backwardAction,
//				"ENTER", JTextField.notifyAction,
//				"ctrl BACK_SLASH", "unselect"/*DefaultEditorKit.unselectAction*/,
//				"control shift O", "toggle-componentOrientation"/*DefaultEditorKit.toggleComponentOrientation*/
//		});
//
//		Object[] componentDefaults = {
//				"TextField.focusInputMap", fieldInputMap,
//				"PasswordField.focusInputMap", passwordInputMap,
//		};
//
//		table.putDefaults(componentDefaults);
	}

	@Override
	public UIDefaults getDefaults() {
		UIDefaults table = super.getDefaults();
		theme.addCustomEntriesToTable(table);
		return table;
	}
}
