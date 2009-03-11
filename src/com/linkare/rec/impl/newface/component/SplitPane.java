/* 
 * SplitPane.java created on Feb 14, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;

/**
 * 
 * @author Henrique Fernandes
 */
public class SplitPane extends JSplitPane {

    private static final long serialVersionUID = 8779412805575129170L;

    /**
     * 
     */
    public SplitPane() {
	setBorder(BorderFactory.createEmptyBorder());
	setContinuousLayout(false);
    }
    
}
