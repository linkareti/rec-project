/* 
 * Prioritazible.java created on Apr 4, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.events;

import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author artur
 */
public interface Prioritazible {
	public EnumPriority getPriority();
}
