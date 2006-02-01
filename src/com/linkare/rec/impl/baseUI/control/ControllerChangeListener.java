/*
 * ControlChangeListener.java
 *
 * Created on 19/07/04 at 18:09 pm
 */

package com.linkare.rec.impl.baseUI.control;

/**
 *
 * @author  André
 */
public interface ControllerChangeListener extends java.util.EventListener
{   
    public void controlChanged(ControllerEvent evt);
}
