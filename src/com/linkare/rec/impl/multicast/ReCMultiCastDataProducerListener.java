/*
 * ProxyDataProducerCallBack.java
 *
 * Created on 5 de Novembro de 2002, 14:46
 */

package com.linkare.rec.impl.multicast;

import java.util.logging.Level;
/**
 *
 * @author  jp
 */
public interface ReCMultiCastDataProducerListener
{
    public void oneDataReceiverGone();
    public void log(Level debugLevel, String message);
    public void logThrowable(String message,Throwable t);
}
