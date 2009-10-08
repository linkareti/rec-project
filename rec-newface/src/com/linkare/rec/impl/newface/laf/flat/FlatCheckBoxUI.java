/* 
 * FlatCheckBoxUI.java created on 2009/05/07
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;

/**
 * 
 * @author joao
 */
public class FlatCheckBoxUI extends FlatRadioButtonUI {

    public static ComponentUI createUI(JComponent x) {
	return new FlatCheckBoxUI();
    }
}
