/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.serial.stamp.conducaoCalor.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 *
 * @author  Andr�
 */
public class Sensor2Table extends MultSeriesTable
{
    
    /** Creates a new instance of TableFreqRMS */
    public Sensor2Table() 
    {
        super();
        setColArray(new int[]{9,1,4,7});
    }

    public String getName()
    {
        return "Sensor 2";
    }     
}
