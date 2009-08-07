/*
 * AbstractSerialPortTranslatorAndProcessor.java
 *
 * Created on 12 de Novembro de 2002, 15:15
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractSerialPortTranslatorAndProcessor implements SerialPortTranslator, SerialPortProcessor
{
	private String commandIdentifier=null;
	/** Creates a new instance of AbstractSerialPortTranslatorAndProcessor */
	public AbstractSerialPortTranslatorAndProcessor(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
		SerialPortTranslatorProcessorManager.registerProcessor(this);
	}
	
	public boolean accepts(SerialPortCommand command)
	{
		return commandIdentifier.equals(command.getCommandIdentifier());
	}
	
}
