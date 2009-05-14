/*
 * SerialPortTranslatorAdapter.java
 *
 * Created on 11 de Novembro de 2002, 15:58
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractStampTranslator implements StampTranslator
{
	private String commandIdentifier=null;

	/** Creates a new instance of SerialPortTranslatorAdapter */
	public AbstractStampTranslator(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
		StampTranslatorProcessorManager.registerTranslator(this);
	}
	
	public String getCommandIdentifier()
	{
	    return commandIdentifier;
	}
	
}
