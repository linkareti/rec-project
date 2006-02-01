/*
 * EventQueueDispatcher.java
 *
 * Created on 28 de Outubro de 2002, 16:38
 */

package com.linkare.rec.impl.utils;

/**
 *
 * @author  jp
 */
public interface EventQueueDispatcher
{
    
    public void dispatchEvent(Object evt);
    public int getPriority();
}
