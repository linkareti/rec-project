/*
 * AbstractSerialPortTranslatorAndProcessor.java
 *
 * Created on 12 de Novembro de 2002, 15:15
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

/**
 *
 * @author  jp
 */
public abstract class AbstractStampTranslatorAndProcessor implements StampTranslator, StampProcessor
{
	private String commandIdentifier=null;
	/** Creates a new instance of AbstractSerialPortTranslatorAndProcessor */
	public AbstractStampTranslatorAndProcessor(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
		StampTranslatorProcessorManager.registerProcessor(this);
	}
	
	public boolean accepts(StampCommand command)
	{
		return commandIdentifier.equals(command.getCommandIdentifier());
	}
	
}
