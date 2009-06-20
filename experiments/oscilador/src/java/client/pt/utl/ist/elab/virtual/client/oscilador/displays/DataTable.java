/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.virtual.client.oscilador.displays;

import com.linkare.rec.impl.baseUI.table.*;

/**
 *
 * @author  RF
 */
public class DataTable extends MultSeriesTable
{
    
    /** Creates a new instance of TableFreqRMS */
    public DataTable() 
    {
        super();
        setColArray(new int[]{0,1,2,3,4,13,14,15});
    }
}
