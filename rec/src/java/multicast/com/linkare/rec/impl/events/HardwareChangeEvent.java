/*
 * HardwareChangeEvent.java
 *
 * Created on 6 de Novembro de 2002, 12:38
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.threading.util.EnumPriority;


/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareChangeEvent implements Prioritazible{

	/** Creates a new instance of HardwareChangeEvent */
	public HardwareChangeEvent() {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MINIMUM;
	}

}
