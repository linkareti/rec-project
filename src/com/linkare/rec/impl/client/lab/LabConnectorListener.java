/*
 * LabConnectorListener.java
 *
 * Created on 13 de Maio de 2003, 18:08
 */

package com.linkare.rec.impl.client.lab;

/**
 *
 * @author  jp
 */
public interface LabConnectorListener extends java.util.EventListener
{
	public void labStatusChanged(com.linkare.rec.impl.client.lab.LabConnectorEvent evt);
}
