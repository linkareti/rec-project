/*
 * TreeSelectionAdapter.java
 *
 * Created on July 19, 2004, 6:14 PM
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */
public abstract class TreeSelectionAdapter implements TreeSelectionChangeListener
{
            
    public void apparatusSelectionChange(ApparatusSelectionEvent selectevent) {
    }
    
    public void defaultConfigSelectionChange(DefaultConfigSelectionEvent selectevent) {
    }
    
    public void displaySelectionChange(DisplaySelectionEvent selectevent) {
    }
    
    public void labSelectionChange(LabSelectionEvent selectevent) {
    }
    
    public void webResourceSelectionChange(WebResourceSelectionEvent selectevent) {
    }
    
}
