/*
 * CypressHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc.processors;

import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver;
import pt.utl.ist.elab.driver.usb.cypress.transproc.AbstractCypressProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

public class CypressNotConfiguredProcessor extends AbstractCypressProcessor {

	public static final String COMMAND_IDENTIFIER = AbstractCypressDriver.CONFIG_NOT_DONE_STRING;

	public CypressNotConfiguredProcessor() {
		super(CypressNotConfiguredProcessor.COMMAND_IDENTIFIER);
	}

	@Override
	public boolean process(final CypressCommand command) {
		command.addCommandData(CypressNotConfiguredProcessor.COMMAND_IDENTIFIER, Boolean.TRUE);
		return true;
	}

}
