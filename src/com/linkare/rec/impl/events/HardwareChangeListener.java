/*
 * HardwareChangeListener.java
 *
 * Created on 30 de Outubro de 2002, 12:03
 */

package com.linkare.rec.impl.events;

/**
 *
 * @author  jp
 */
public interface HardwareChangeListener
{
	/** handler for new Hardwares available on the system...*/
	public void hardwareAdded(HardwareAddEvt evt);
	
	/** handler for Hardwares removed from the system...*/
	//public void hardwareRemoved(HardwareRemoveEvt evt);
}

