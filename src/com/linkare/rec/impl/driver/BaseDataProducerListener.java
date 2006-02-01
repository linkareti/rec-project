/*
 * BaseDataProducerListener.java
 *
 * Created on 16 de Maio de 2003, 9:45
 */

package com.linkare.rec.impl.driver;

/**
 *
 * @author  jp
 */
public interface BaseDataProducerListener extends java.util.EventListener
{
	public void baseDataProducerIsEmpty(BaseDataProducer producer);
}
