/*
 * AbstractSerialPortTranslatorAndProcessor.java
 *
 * Created on 12 de Novembro de 2002, 15:15
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractCypressTranslatorAndProcessor implements CypressTranslator, CypressProcessor {
	private String commandIdentifier = null;

	/** Creates a new instance of AbstractSerialPortTranslatorAndProcessor 
	 * @param commandIdentifier */
	public AbstractCypressTranslatorAndProcessor(final String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
		CypressTranslatorProcessorManager.registerProcessor(this);
	}

	public boolean accepts(final CypressCommand command) {
		return commandIdentifier.equals(command.getCommandIdentifier());
	}

}