/*
 * ApparatusSelectionEvent.java
 *
 * Created on 19/07/04 4:42pm
 */

package com.linkare.rec.impl.baseUI.labsTree;

/**
 *
 * @author André Neto - LEFT - IST
 */

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.Lab;

public class ApparatusSelectionEvent extends LabSelectionEvent
{
    
    /** Holds value of property Apparatus. */
    private Apparatus app;
    
    /** Creates a new instance of ApparatusSelectionEvent */
    public ApparatusSelectionEvent(Object source, Apparatus app)
    {
	this(source, app, null);
    }
    
    /** Creates a new instance of ApparatusSelectionEvent */
    public ApparatusSelectionEvent(Object source, Apparatus app, Lab lab)
    {
	super(source, lab);
        this.app = app;
    }
    
    /** Getter for property Apparatus.
     * @return Value of property Apparatus.
     */
    public Apparatus getApparatus()
    {
	return this.app;
    }
}
