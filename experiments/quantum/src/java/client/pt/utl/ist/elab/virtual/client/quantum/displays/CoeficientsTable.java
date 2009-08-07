/*
 * CoeficientsTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.virtual.client.quantum.displays;

import com.linkare.rec.impl.baseUI.table.MultSeriesTable;

/**
 *
 * @author  nomead
 */
public class CoeficientsTable extends MultSeriesTable {
    
    /** Creates a new instance of CoeficientsTable */
    public CoeficientsTable(){
        super();
        setColArray(new int[]{9,2,3});
    }
}
