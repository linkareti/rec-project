/* 
 * FlatSplitPaneUI.java created on Mar 10, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.laf.flat;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * @deprecated
 * @author Henrique Fernandes
 */
public class FlatSplitPaneUI extends BasicSplitPaneUI {

	public static ComponentUI createUI(JComponent x) {
		return new FlatSplitPaneUI();
	}

	@Override
	public BasicSplitPaneDivider createDefaultDivider() {
		return new FlatSplitPaneDivider(this);
	}
}
