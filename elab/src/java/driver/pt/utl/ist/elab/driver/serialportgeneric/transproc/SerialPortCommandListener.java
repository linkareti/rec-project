/*
 * StampCommandListener.java
 *
 * Created on 15 de Maio de 2003, 15:38
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface SerialPortCommandListener extends java.util.EventListener
{
	
	public void handleStampCommand(SerialPortCommand command);
	
}
