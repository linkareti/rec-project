/*
 * BaseDataProducerListener.java
 *
 * Created on 16 de Maio de 2003, 9:45
 */

package com.linkare.rec.impl.driver;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface BaseDataProducerListener extends java.util.EventListener
{
	public void baseDataProducerIsEmpty(BaseDataProducer producer);
}
