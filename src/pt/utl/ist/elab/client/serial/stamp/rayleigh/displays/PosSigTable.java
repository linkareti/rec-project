/*
 * PosSigTable.java
 *
 * Created on July 9, 2004, 1:40 PM
 */

package pt.utl.ist.elab.client.serial.stamp.rayleigh.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 *
 * @author André Neto - LEFT - IST
 */


public class PosSigTable extends MultSeriesTable
{
    
    /** Creates a new instance of PosSigTable */
    public PosSigTable() 
    {
        super();
        setColArray(new int[]{0,1,2});
    }    
    
    public String getName()
    {
        return "Tabela";
    }     
}
