/*
 * StampFinderListener.java
 *
 * Created on 15 de Maio de 2003, 11:56
 */

package pt.utl.ist.elab.driver.serial.stamp;

import javax.comm.SerialPort;
/**
 *
 * @author  jp
 */
public interface StampFinderListener extends java.util.EventListener
{
	public void stampFound(SerialPort sPort);
}
