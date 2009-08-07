/*
 * DataTable.java
 *
 * Created on 1 de Mar�o de 2005, 2:38
 */

package pt.utl.ist.elab.client.vpend2m.displays;

import com.linkare.rec.impl.baseUI.table.*;

/**
 *
 * @author  Antonio J. R. Figueiredo
 *          Last Review : 6/04/2005
 */
public class DataTable extends MultSeriesTable {
    
    /** Creates a new instance of DataTable */
    public DataTable(){
        super();
        setColArray(new int[]{0,2,1,3,4});
    }
}
