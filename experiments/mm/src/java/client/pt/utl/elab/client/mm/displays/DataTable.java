/*
 * TableFreqRMS.java
 *
 * Created on 16 October 2003, 23:33
 */

package pt.utl.ist.elab.virtual.client.mm.displays;

import com.linkare.rec.impl.baseUI.table.*;

/**
 *
 * @author  Andr�
 */
public class DataTable extends MultSeriesTable
{
    
    /** Creates a new instance of TableFreqRMS */
    public DataTable() 
    {
        super();
        setColArray(new int[]{0,1,2});
    }
}
