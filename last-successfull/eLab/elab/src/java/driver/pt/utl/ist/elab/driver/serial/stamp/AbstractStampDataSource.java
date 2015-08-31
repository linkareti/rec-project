/*
 * AbstractStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 18:03
 */

package pt.utl.ist.elab.driver.serial.stamp;

import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

import com.linkare.rec.impl.driver.BaseDataSource;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractStampDataSource extends BaseDataSource {
	public abstract void processDataCommand(StampCommand cmd);
}
