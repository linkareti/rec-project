/*
 * SerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 15:51
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

public interface CypressProcessor {
	public String getCommandIdentifier();

	public boolean process(CypressCommand command);

	public boolean isData();
}
