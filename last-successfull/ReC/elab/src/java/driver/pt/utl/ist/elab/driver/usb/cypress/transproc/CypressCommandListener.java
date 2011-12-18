/*
 * CypressCommandListener.java
 *
 * Created on 15 de Maio de 2003, 15:38
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface CypressCommandListener extends java.util.EventListener {

	public void handleCypressCommand(CypressCommand command);

}
