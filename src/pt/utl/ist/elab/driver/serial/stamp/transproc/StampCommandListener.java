/*
 * StampCommandListener.java
 *
 * Created on 15 de Maio de 2003, 15:38
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public interface StampCommandListener extends java.util.EventListener
{
	
	public void handleStampCommand(StampCommand command);
	
}
