/*
 * SerialPortTranslatorAdapter.java
 *
 * Created on 11 de Novembro de 2002, 15:58
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractSerialPortTranslator implements SerialPortTranslator {
	private String commandIdentifier = null;

	/** Creates a new instance of SerialPortTranslatorAdapter */
	public AbstractSerialPortTranslator(String commandIdentifier) {
		this.commandIdentifier = commandIdentifier;
		SerialPortTranslatorProcessorManager.registerTranslator(this);
	}

	public String getCommandIdentifier() {
		return commandIdentifier;
	}

}
