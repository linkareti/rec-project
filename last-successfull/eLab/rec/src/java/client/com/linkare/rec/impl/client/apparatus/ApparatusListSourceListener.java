/*
 * ApparatusListSourceListener.java
 *
 * Created on 08 May 2003, 22:54
 */

package com.linkare.rec.impl.client.apparatus;

/**
 * 
 * @author Jose Pedro Pereira
 */
public interface ApparatusListSourceListener extends java.util.EventListener {
	public void apparatusListChanged(ApparatusListChangeEvent newApparatusList);

}
