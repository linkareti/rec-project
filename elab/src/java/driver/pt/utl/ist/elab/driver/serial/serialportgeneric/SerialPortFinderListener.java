/*
 * StampFinderListener.java
 *
 * Created on 15 de Maio de 2003, 11:56
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric;

import gnu.io.SerialPort;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface SerialPortFinderListener extends java.util.EventListener
{
	public void stampFound(SerialPort sPort);
}
