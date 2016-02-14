/*
 * SamplesSourceEventListener.java
 *
 * Created on 8 de Janeiro de 2004, 16:46
 */

package com.linkare.rec.impl.data;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface SamplesSourceEventListener extends java.util.EventListener {
	public void newSamples(SamplesSourceEvent evt);
}
