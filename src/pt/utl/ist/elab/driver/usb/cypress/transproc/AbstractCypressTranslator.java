/*
 * SerialPortTranslatorAdapter.java
 *
 * Created on 11 de Novembro de 2002, 15:58
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

/**
 *
 * @author  jp
 */
public abstract class AbstractCypressTranslator implements CypressTranslator
{
	private String commandIdentifier=null;

	/** Creates a new instance of SerialPortTranslatorAdapter */
	public AbstractCypressTranslator(String commandIdentifier)
	{
		this.commandIdentifier=commandIdentifier;
		CypressTranslatorProcessorManager.registerTranslator(this);
	}
	
	public String getCommandIdentifier()
	{
	    return commandIdentifier;
	}
	
}
