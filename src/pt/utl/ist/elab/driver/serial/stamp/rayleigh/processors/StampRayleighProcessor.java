/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.rayleigh.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;

/**
 *
 * @author  bruno
 */
public class StampRayleighProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "v:";
    public static final String SIGNAL = "sig";
    public static final String POSITION = "pos";

    /** Creates a new instance of StampHelloProcessor */
    public StampRayleighProcessor()
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
	
	int sinal = 0;
	int pos = 0;
        double sinald = 0;
	
	String[] splitedStr = command.getCommand().split(" ");	
        
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
		sinal = Integer.parseInt(splitedStr[0]);
                sinald = 5d * sinal / 1023d;
                
                Double oSinal = new Double(sinald);
                                
		command.addCommandData(SIGNAL, oSinal);		
                
                pos = Integer.parseInt(splitedStr[1]);                
                Integer oPos = new Integer(pos);
                
		command.addCommandData(POSITION,oPos);
		
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
