/*
 * TunnelingTable.java
 *
 * Created on 2 de Dezembro de 2004, 2:38
 */

package pt.utl.ist.elab.virtual.client.quantum.displays;

import com.linkare.rec.impl.baseUI.table.*;

/**
 *
 * @author  nomead
 */
public class TunnelingTable extends MultSeriesTable {
    
    /** Creates a new instance of TunnelingTable */
    public TunnelingTable(){
        super();
        setColArray(new int[]{1,4,5,6});
    }
}
