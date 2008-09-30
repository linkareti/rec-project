/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.serial.stamp.planck.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 *
 * @author  Andr�
 */
public class Mode0Table extends MultSeriesTable
{
    
    /** Creates a new instance of TableFreqRMS */
    public Mode0Table() 
    {
        super();
        setColArray(new int[]{1,2});
    }

    public String getName()
    {
        return "Modo Varrimento";
    }     
}
