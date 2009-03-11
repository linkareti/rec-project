/* 
 * ReCModel.java created on Jan 30, 2009
 * 
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.newconfig;

import java.awt.Dimension;

import com.linkare.rec.impl.newface.ReCView;

/**
 * The <code>ReCModel</code> interface specifies the methods the
 * <code>ReCView</code> will use to access the Remote Experience Control data model. <p>
 * 
 * The <code>ReCView</code> can be set up to display and interact with any data
 * model which implements the <code>ReCModel</code>.
 *  
 * @author Henrique Fernandes
 * @see ReCView
 */
public interface ReCModel {

    Dimension getApplicationPreferredSize();

}
