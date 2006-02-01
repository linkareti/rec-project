/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.usb.cypress.gamma.processors;

import pt.utl.ist.elab.driver.usb.cypress.transproc.*;
import pt.utl.ist.elab.driver.usb.cypress.*;

/**
 *
 * @author  bruno
 */
public class CypressGammaProcessor2 extends AbstractCypressProcessor
{
    public static final String COMMAND_IDENTIFIER = "TIME=";
    public static final String TIME = "time";

    /** Creates a new instance of StampHelloProcessor */
    public CypressGammaProcessor2()
    {
	super(COMMAND_IDENTIFIER);
    }
    
    /** This method makes the Processor process the command passed in...
     *
     * @param command the command that this processor will have to process
     * @return boolean - wether the processing was successfull
     *
     */
    public boolean process(CypressCommand command)
    {
	
	int time = 0;
	
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
                time = Integer.parseInt(splitedStr[0]);
		Float oTime = new Float(time / 2F);
		command.addCommandData(TIME, oTime);
                		
		command.setData(true);
		return true;
		
	    }
	    catch(NumberFormatException e)
	    {
		e.printStackTrace();
		return false;
	    }
	}
	
	return false;
    }
    
    public boolean isData()
    {
	return true;
    }
}
