/* 
 * PriorityRunnable.java created on Mar 17, 2011
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.threading;

import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author artur
 */
public interface PriorityRunnable extends Runnable {

	public EnumPriority getPriority();

}
