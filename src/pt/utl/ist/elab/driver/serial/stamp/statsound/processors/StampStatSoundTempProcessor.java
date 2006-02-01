/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.statsound.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;

/**
 *
 * @author  bruno
 */
public class StampStatSoundTempProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "TEMP";
    
    /** Creates a new instance of StampHelloProcessor */
    public StampStatSoundTempProcessor()
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
        int temp=0;        
	String[] splitedStr = command.getCommand().split(" ");
        if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
		temp=Integer.parseInt(splitedStr[0]);
                Integer oTemp=new Integer(temp);
		command.addCommandData(COMMAND_IDENTIFIER,oTemp);		
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
