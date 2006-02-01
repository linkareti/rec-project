/*
 * PosSigTable.java
 *
 * Created on July 9, 2004, 1:40 PM
 */

package pt.utl.ist.elab.client.serial.stamp.rayleigh.displays;

/**
 *
 * @author  andre
 */

import pt.utl.ist.elab.rec.impl.baseUI.table.*;

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
