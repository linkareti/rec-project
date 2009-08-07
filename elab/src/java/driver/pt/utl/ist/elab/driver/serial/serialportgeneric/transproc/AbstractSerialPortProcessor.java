/*
 * AbstractSerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 16:03
 */

package pt.utl.ist.elab.driver.serial.serialportgeneric.transproc;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractSerialPortProcessor implements SerialPortProcessor
{
	private String commandIdentifier=null;
	
	/** Creates a new instance of AbstractSerialPortProcessor */
	public AbstractSerialPortProcessor(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
		SerialPortTranslatorProcessorManager.registerProcessor(this);
	}
	
	public String getCommandIdentifier()
	{
	    return commandIdentifier;
	}
	
	public boolean isData()
	{
	    return false;
	}
	
}
