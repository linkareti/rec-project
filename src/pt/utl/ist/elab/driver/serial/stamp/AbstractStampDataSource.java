/*
 * AbstractStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 18:03
 */

package pt.utl.ist.elab.driver.serial.stamp;

import com.linkare.rec.impl.driver.*;
import pt.utl.ist.elab.driver.serial.stamp.transproc.*;

/**
 *
 * @author  jp
 */
public abstract class AbstractStampDataSource extends BaseDataSource
{
	public abstract void processDataCommand(StampCommand cmd);

}
