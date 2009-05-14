/*
 * ApparatusListSource.java
 *
 * Created on 08 May 2003, 22:53
 */

package com.linkare.rec.impl.client.apparatus;

/**
 *
 * @author  Jose Pedro Pereira
 */
public interface ApparatusListSource
{
    
    /** Registers ApparatusListSourceListener to receive events.
     * @param listener The listener to register.
     */
    public void addApparatusListSourceListener(ApparatusListSourceListener listener);
    
    /** Removes ApparatusListSourceListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public void removeApparatusListSourceListener(ApparatusListSourceListener listener);
    
}
