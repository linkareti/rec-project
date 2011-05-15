/*
 * SerialPortTranslator.java
 *
 * Created on 11 de Novembro de 2002, 15:50
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

public interface CypressTranslator {
	public String getCommandIdentifier();

	public boolean translate(CypressCommand command);
}
