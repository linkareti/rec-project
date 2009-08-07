/*
 * Data.java
 *
 * Created on 5 April 2005, 0:53
 */

package pt.utl.ist.elab.client.vdi.displays;

import com.linkare.rec.impl.baseUI.table.*;

/**
 *
 * @author  Queiro'
 */
public class DataTable extends MultSeriesTable
{
    
    /** Creates a new instance of Data */
    public DataTable() 
    {
        super();
        setColArray(new int[]{0,5,6,9,10});
    }
}