/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.planck.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampPlanck2Processor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "V1:";
    public static final String FOTOCEL = "fotocel";

    /** Creates a new instance of StampHelloProcessor */
    public StampPlanck2Processor()
    {
	super(COMMAND_IDENTIFIER);
    }
    
    /** This method makes the Processor process the command passed in...
     *
     * @param command the command that this processor will have to process
     * @return boolean - wether the processing was successfull
     *
     */
    public boolean process(StampCommand command)
    {
	int potential = 0;
	
	String[] splitedStr = command.getCommand().split(" ");	
        
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {                
		potential = Integer.parseInt(splitedStr[0]);                
                Float oPotential = new Float(potential*5f/4096f);                
                
		command.addCommandData(FOTOCEL,oPotential);
		
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
