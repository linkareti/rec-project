/*
 * HardwareAddEvt.java
 *
 * Created on 30 de Outubro de 2002, 12:05
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.acquisition.Hardware;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class HardwareAddEvt implements Prioritazible{

	/** Holds value of property hardware. */
	private Hardware hardware;

	/** Creates a new instance of HardwareAddEvt */
	public HardwareAddEvt(Hardware hardware) {
		this.hardware = hardware;
	}

	/**
	 * Getter for property hardware.
	 * 
	 * @return Value of property hardware.
	 */
	public Hardware getHardware() {
		return this.hardware;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MINIMUM;
	}

}
