/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc.processors;

import pt.utl.ist.elab.driver.usb.cypress.transproc.AbstractCypressProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

public class CypressIDProcessor extends AbstractCypressProcessor {

	public CypressIDProcessor() {
		super(pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver.ID_STR);
	}

	@Override
	public boolean process(final CypressCommand command) {
		command.addCommandData(getCommandIdentifier(), Boolean.TRUE);
		return true;
	}
}
