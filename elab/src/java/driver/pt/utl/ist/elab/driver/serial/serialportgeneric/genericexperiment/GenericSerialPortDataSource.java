/*
 * GenericStampDataSource.java
 *
 * Created on 15 de Maio de 2003, 19:38
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.genericexperiment;

import pt.utl.ist.elab.driver.serial.serialportgeneric.AbstractSerialPortDataSource;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class GenericSerialPortDataSource extends AbstractSerialPortDataSource {

	/** Creates a new instance of GenericStampDataSource */
	public GenericSerialPortDataSource() {
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPacketSize() {
		// TODO Auto-generated method stub
		return super.getPacketSize();
		
		// FIXME this is an example from g experiment
//		setPacketSize((int) Math.ceil(1. / (8. * config.getSelectedFrequency().getFrequency() * config.getSelectedFrequency().getMultiplier().getExpValue())));
	}

}
