/*
 * ICustomizerListener.java
 *
 * Created on 8 de Maio de 2003, 14:16
 */

package com.linkare.rec.impl.client.customizer;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface ICustomizerListener extends java.util.EventListener {
	public void canceled();

	public void done();
}
