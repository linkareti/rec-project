/*
 * FlatButtonUI.java created on Apr 20, 2009
 *
 * Copyright 2009 HFernandes. All rights reserved.
 * HFernandes PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 *
 * @author Henrique Fernandes
 */
public class FlatButtonUI extends MetalButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new FlatButtonUI();
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b,
            Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        // Focus is managed by FlatFocusRenderer
    }
}
