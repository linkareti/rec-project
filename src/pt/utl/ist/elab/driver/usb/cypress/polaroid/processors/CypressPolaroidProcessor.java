/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.usb.cypress.polaroid.processors;

import pt.utl.ist.elab.driver.usb.cypress.transproc.AbstractCypressProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

/**
 *
 * @author  bruno
 */
public class CypressPolaroidProcessor extends AbstractCypressProcessor
{
    public static final String COMMAND_IDENTIFIER = "POS=";
    public static final String INTENSIDADE = "Intensidade";
    public static final String ANGULO = "Angulo";
    /** Creates a new instance of StampHelloProcessor */
    public CypressPolaroidProcessor()
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
	
	int angulo = 0;
        float intensidade = 0;
	
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
                angulo = Integer.parseInt(splitedStr[0]);
		Integer oAngulo = new Integer((angulo * 180) / 155);
		command.addCommandData(ANGULO, oAngulo);
                
		intensidade = Integer.parseInt(splitedStr[1]);
		Float oIntensidade = new Float((intensidade * 5) / 1024F);
		command.addCommandData(INTENSIDADE, oIntensidade);
		
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
