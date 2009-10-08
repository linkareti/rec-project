package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class FButtonUI extends MetalButtonUI {

    public static ComponentUI createUI(JComponent x) {
	return new FButtonUI();
    }

    @Override
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
	// Focus is rendered by FlatFocusRenderer
    }

}
