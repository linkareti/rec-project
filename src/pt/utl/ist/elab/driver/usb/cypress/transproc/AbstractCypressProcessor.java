/*
 * AbstractSerialPortProcessor.java
 *
 * Created on 11 de Novembro de 2002, 16:03
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

/**
 *
 * @author  jp
 */
public abstract class AbstractCypressProcessor implements CypressProcessor
{
	private String commandIdentifier=null;
	
	/** Creates a new instance of AbstractSerialPortProcessor */
	public AbstractCypressProcessor(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
		CypressTranslatorProcessorManager.registerProcessor(this);
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
