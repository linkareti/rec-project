/*
 * LabSelectionEvent.java
 *
 * Created on 19/07/04 4:42pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author  adnre
 */

import com.linkare.rec.impl.baseUI.config.*;

public class LabSelectionEvent extends java.util.EventObject
{
    
    /** Holds value of property Lab. */
    private Lab lab;
    
    /** Creates a new instance of LabSelectionEvent */
    public LabSelectionEvent(Object source, Lab lab)
    {
	super(source);
	this.lab = lab;
    }
    
    /** Getter for property Lab.
     * @return Value of property Lab.
     */
    public Lab getLab()
    {
	return this.lab;
    }         
}
