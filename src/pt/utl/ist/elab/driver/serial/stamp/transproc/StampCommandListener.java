/*
 * StampCommandListener.java
 *
 * Created on 15 de Maio de 2003, 15:38
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

/**
 *
 * @author  jp
 */
public interface StampCommandListener extends java.util.EventListener
{
	
	public void handleStampCommand(StampCommand command);
	
}
