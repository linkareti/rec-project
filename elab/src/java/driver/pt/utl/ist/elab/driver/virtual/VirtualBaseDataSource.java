/*
 * VirtualDataSource.java
 *
 * Created on October 16, 2004, 6:48 PM
 */

package pt.utl.ist.elab.driver.virtual;

/**
 *
 * @author  andre
 */

import com.linkare.rec.impl.driver.BaseDataSource;

public abstract class VirtualBaseDataSource extends BaseDataSource
{
    
    /** Creates a new instance of VirtualDataSource */
    public VirtualBaseDataSource()
    {
    }
    
    public abstract void startProduction();
    
}
