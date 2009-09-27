/*
 * AbstractStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 18:03
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric;

import pt.utl.ist.elab.driver.serial.serialportgeneric.transproc.SerialPortCommand;

import com.linkare.rec.impl.driver.BaseDataSource;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractSerialPortDataSource extends BaseDataSource {
	public abstract void processDataCommand(SerialPortCommand cmd);
}
