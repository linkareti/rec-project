/* 
 * ContentPaneEvent.java created on Feb 11, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.util.EventObject;

/**
 * 
 * @author Henrique Fernandes
 */
public class ContentPaneEvent extends EventObject {

    private static final long serialVersionUID = -2430118478492283598L;

    /**
     * @param source
     */
    public ContentPaneEvent(Object source) {
	super(source);
    }

}
