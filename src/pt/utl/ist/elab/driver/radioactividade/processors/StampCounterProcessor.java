/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.radioactividade.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampCounterProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "COUNTER";
    public static final String COUNTER="Contagens";
    /** Creates a new instance of StampHelloProcessor */
    public StampCounterProcessor()
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
	int countedHits = 0;
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
		countedHits = Integer.parseInt(splitedStr[0]);
		Integer nHits = new Integer(countedHits);
		command.addCommandData(COUNTER,nHits);
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
