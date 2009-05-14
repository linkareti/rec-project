/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.statsound.displays;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class TableFreqRMS extends MyDefaultTable
{
    
    /** Creates a new instance of TableFreqRMS */
    public TableFreqRMS() 
    {
        super();
        setColArray(new int[]{1,2,3});
    }

    public String getName()
    {
        return "Table Freq vs RMS";
    }     
}
