/*
 * IDataClientForQueueCallback.java
 *
 * Created on 12 de Maio de 2003, 18:36
 */

package com.linkare.rec.impl.multicast;

import java.util.logging.Level;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface IClientQueueListener 
{
    public void dataClientForQueueIsGone(DataClientForQueue dcfq);
    public void log(Level debugLevel, String message);
    public void logThrowable(String message,Throwable t);
}
