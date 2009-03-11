/* 
 * FlatLookAndFeel.java created on Jan 31, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Color;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.UIDefaults;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.basic.BasicPanelUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.text.DefaultEditorKit;

/**
 * Implements a Flat LookAndFeel targeted for web.
 * 
 * @author Henrique Fernandes
 */
public class FlatLookAndFeel<T extends FlatTheme> extends 
	MetalLookAndFeel // This inheritance can be useful later
//	BasicLookAndFeel // The Default Base LAF for FlatLookAndFeel
	{

    private static final Logger log = Logger.getLogger(FlatLookAndFeel.class.getName());
    
    private static final long serialVersionUID = 3410155899630816365L;
    
    private T theme;
    
    /**
     * Creates a new {@link FlatLookAndFeel} with the given Theme.
     */
    public FlatLookAndFeel(T theme) {
	this.theme = theme;
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

    public T getTheme() {
	return theme;
    }
    
    @Override
    protected void initClassDefaults(UIDefaults table)
    {
        super.initClassDefaults(table);
	
	Object[] classDefaults = {
		"MenuBarUI", FlatMenuBarUI.class.getName(),
		"MenuUI", FlatMenuUI.class.getName(),
		"ToolBarUI", FlatToolBarUI.class.getName(),
		"PanelUI", BasicPanelUI.class.getName(),
		"SplitPaneUI", FlatSplitPaneUI.class.getName(),
		"SplitPaneUI", FlatSplitPaneUI.class.getName(),
		"TextFieldUI", BasicTextFieldUI.class.getName()
	};
	
	if (log.isLoggable(Level.FINER)) {
	    log.finer("FlatLookAndFeel Class Defaults: " + Arrays.deepToString(classDefaults));
	}
	
	table.putDefaults(classDefaults);
    }
    
    @Override
    protected void initComponentDefaults(UIDefaults table) {
	super.initComponentDefaults(table);
	
	Object fieldInputMap = new UIDefaults.LazyInputMap(new Object[] {
		"ctrl C", DefaultEditorKit.copyAction,
		"ctrl V", DefaultEditorKit.pasteAction,
		"ctrl X", DefaultEditorKit.cutAction,
		"COPY", DefaultEditorKit.copyAction,
		"PASTE", DefaultEditorKit.pasteAction,
		"CUT", DefaultEditorKit.cutAction,
		"shift LEFT", DefaultEditorKit.selectionBackwardAction,
		"shift KP_LEFT", DefaultEditorKit.selectionBackwardAction,
		"shift RIGHT", DefaultEditorKit.selectionForwardAction,
		"shift KP_RIGHT", DefaultEditorKit.selectionForwardAction,
		"ctrl LEFT", DefaultEditorKit.previousWordAction,
		"ctrl KP_LEFT", DefaultEditorKit.previousWordAction,
		"ctrl RIGHT", DefaultEditorKit.nextWordAction,
		"ctrl KP_RIGHT", DefaultEditorKit.nextWordAction,
		"ctrl shift LEFT", DefaultEditorKit.selectionPreviousWordAction,
		"ctrl shift KP_LEFT", DefaultEditorKit.selectionPreviousWordAction,
		"ctrl shift RIGHT", DefaultEditorKit.selectionNextWordAction,
		"ctrl shift KP_RIGHT", DefaultEditorKit.selectionNextWordAction,
		"ctrl A", DefaultEditorKit.selectAllAction,
		"HOME", DefaultEditorKit.beginLineAction,
		"END", DefaultEditorKit.endLineAction,
		"shift HOME", DefaultEditorKit.selectionBeginLineAction,
		"shift END", DefaultEditorKit.selectionEndLineAction,
		"BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
		"ctrl H", DefaultEditorKit.deletePrevCharAction,
		"DELETE", DefaultEditorKit.deleteNextCharAction,
		"RIGHT", DefaultEditorKit.forwardAction,
		"LEFT", DefaultEditorKit.backwardAction,
		"KP_RIGHT", DefaultEditorKit.forwardAction,
		"KP_LEFT", DefaultEditorKit.backwardAction,
		"ENTER", JTextField.notifyAction,
		"ctrl BACK_SLASH", "unselect"/*DefaultEditorKit.unselectAction*/,
		"control shift O", "toggle-componentOrientation"/*DefaultEditorKit.toggleComponentOrientation*/
	});
	
	Object passwordInputMap = new UIDefaults.LazyInputMap(new Object[] {
		"ctrl C", DefaultEditorKit.copyAction,
		"ctrl V", DefaultEditorKit.pasteAction,
		"ctrl X", DefaultEditorKit.cutAction,
		"COPY", DefaultEditorKit.copyAction,
		"PASTE", DefaultEditorKit.pasteAction,
		"CUT", DefaultEditorKit.cutAction,
		"shift LEFT", DefaultEditorKit.selectionBackwardAction,
		"shift KP_LEFT", DefaultEditorKit.selectionBackwardAction,
		"shift RIGHT", DefaultEditorKit.selectionForwardAction,
		"shift KP_RIGHT", DefaultEditorKit.selectionForwardAction,
		"ctrl LEFT", DefaultEditorKit.beginLineAction,
		"ctrl KP_LEFT", DefaultEditorKit.beginLineAction,
		"ctrl RIGHT", DefaultEditorKit.endLineAction,
		"ctrl KP_RIGHT", DefaultEditorKit.endLineAction,
		"ctrl shift LEFT", DefaultEditorKit.selectionBeginLineAction,
		"ctrl shift KP_LEFT", DefaultEditorKit.selectionBeginLineAction,
		"ctrl shift RIGHT", DefaultEditorKit.selectionEndLineAction,
		"ctrl shift KP_RIGHT", DefaultEditorKit.selectionEndLineAction,
		"ctrl A", DefaultEditorKit.selectAllAction,
		"HOME", DefaultEditorKit.beginLineAction,
		"END", DefaultEditorKit.endLineAction,
		"shift HOME", DefaultEditorKit.selectionBeginLineAction,
		"shift END", DefaultEditorKit.selectionEndLineAction,
		"BACK_SPACE", DefaultEditorKit.deletePrevCharAction,
		"ctrl H", DefaultEditorKit.deletePrevCharAction,
		"DELETE", DefaultEditorKit.deleteNextCharAction,
		"RIGHT", DefaultEditorKit.forwardAction,
		"LEFT", DefaultEditorKit.backwardAction,
		"KP_RIGHT", DefaultEditorKit.forwardAction,
		"KP_LEFT", DefaultEditorKit.backwardAction,
		"ENTER", JTextField.notifyAction,
		"ctrl BACK_SLASH", "unselect"/*DefaultEditorKit.unselectAction*/,
		"control shift O", "toggle-componentOrientation"/*DefaultEditorKit.toggleComponentOrientation*/
	});
	
	Border textFieldBorder = BorderFactory.createLineBorder(Color.BLACK);

	Object[] componentDefaults = {
		"TextField.focusInputMap", fieldInputMap,
		"PasswordField.focusInputMap", passwordInputMap,
		"TextField.border", textFieldBorder,
		"PasswordField.border", textFieldBorder
	};
	
	if (log.isLoggable(Level.FINER)) {
	    log.finer("FlatLookAndFeel Component Defaults: " + Arrays.deepToString(componentDefaults));
	}
	
	table.putDefaults(componentDefaults);
    }

    @Override
    public UIDefaults getDefaults() {
	UIDefaults table = super.getDefaults();
        theme.addCustomEntriesToTable(table);
	return table;
    }
}
