/*
 * AbstractSerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 16:03
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractCypressProcessor implements CypressProcessor {
	private String commandIdentifier = null;

	/** Creates a new instance of AbstractSerialPortProcessor */
	public AbstractCypressProcessor(final String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
		CypressTranslatorProcessorManager.registerProcessor(this);
	}

	@Override
	public String getCommandIdentifier() {
		return commandIdentifier;
	}

	@Override
	public boolean isData() {
		return false;
	}

}
