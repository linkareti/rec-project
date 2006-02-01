/*
 * TablePistonRMS.java
 *
 * Created on 16 October 2003, 23:35
 */

package pt.utl.ist.elab.client.serial.stamp.statsound.displays;

/**
 *
 * @author  Andre
 */
public class TablePistonRMS extends MyDefaultTable
{
    
    /** Creates a new instance of TablePistonRMS */
    public TablePistonRMS() 
    {
        super();
        setColArray(new int[]{0,2,3});
    }
    
    public String getName()
    {
        return "Table Piston vs RMS";
    }        
}
