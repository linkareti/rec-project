/* 
 * ContentPaneListener.java created on Feb 12, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.util.EventListener;

/**
 * 
 * @author Henrique Fernandes
 */
public interface ContentPaneListener extends EventListener {

    /**
     * @param evt The source event.
     */
    public void contentPaneClose(Object evt);
    
}
