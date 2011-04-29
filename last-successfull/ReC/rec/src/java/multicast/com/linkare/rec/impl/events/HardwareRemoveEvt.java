/*
 * HardwareRemoveEvt.java
 *
 * Created on 30 de Outubro de 2002, 12:06
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareRemoveEvt implements Prioritazible{

	/** Holds value of property hardwareUniqueId. */
	private String hardwareUniqueId;

	/** Creates a new instance of HardwareRemoveEvt */
	public HardwareRemoveEvt(String hardwareUniqueId) {
		this.hardwareUniqueId = hardwareUniqueId;
	}

	/**
	 * Getter for property hardwareUniqueId.
	 * 
	 * @return Value of property hardwareUniqueId.
	 */
	public String getHardwareUniqueId() {
		return this.hardwareUniqueId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MINIMUM;
	}

}
