/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.statsound.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampStatSoundProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "POS";
    
    /** Creates a new instance of StampHelloProcessor */
    public StampStatSoundProcessor()
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
	int pos=0;
	String[] splitedStr = command.getCommand().split(" ");
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
		pos=Integer.parseInt(splitedStr[0]);
                Integer oPos=new Integer(pos);
		command.addCommandData(COMMAND_IDENTIFIER,oPos);		
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
