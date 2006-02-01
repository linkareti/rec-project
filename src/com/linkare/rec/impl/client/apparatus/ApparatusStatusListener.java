/*
 * ApparatusStatusListener.java
 *
 * Created on July 20, 2004, 12:43 PM
 */

package com.linkare.rec.impl.client.apparatus;

/**
 *
 * @author  andre
 */
public interface ApparatusStatusListener 
{
    void hardwareStateChange (com.linkare.rec.acquisition.HardwareState newState);    
}
