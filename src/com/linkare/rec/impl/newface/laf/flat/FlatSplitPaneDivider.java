/* 
 * FlatSplitPaneDivider.java created on Mar 10, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import java.awt.Graphics;

import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * @deprecated 
 * @author Henrique Fernandes
 */
public class FlatSplitPaneDivider extends BasicSplitPaneDivider {

    private static final long serialVersionUID = 221479197170409718L;

    /**
     * @param ui
     */
    public FlatSplitPaneDivider(BasicSplitPaneUI ui) {
	super(ui);
    }

    @Override
    public void paint(Graphics g) {
	// There is nothing to paint
	// TODO Paint the middle drag symbol
    }
}
