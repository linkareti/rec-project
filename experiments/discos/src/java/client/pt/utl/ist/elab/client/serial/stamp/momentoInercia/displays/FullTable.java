/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.client.serial.stamp.momentoInercia.displays;

import pt.utl.ist.elab.rec.impl.baseUI.table.*;
import pt.utl.ist.elab.rec.impl.client.experiment.*;

/**
 *
 * @author  Andr�
 */
public class FullTable extends MultSeriesTable
{
    
    /** Creates a new instance of TableFreqRMS */
    public FullTable() 
    {
        super();
        setColArray(new int[]{2,0,1});
    }

    public String getName()
    {
        return "Tabela";
    }     
}
