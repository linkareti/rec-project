/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.gamma.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;

/**
 *
 * @author  bruno
 */
public class StampGammaProcessor1 extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "VAL=";
    public static final String ONDA_MIC = "OndaMic";
    public static final String PRESSAO = "Pressao";
    /** Creates a new instance of StampHelloProcessor */
    public StampGammaProcessor1()
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
	
	int ondamic = 0;
        int pressao = 0;
	
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {                
                pressao = Integer.parseInt(splitedStr[0]);
		Float oPressao = new Float(106 + ( (pressao * 1.68) * 1000) / 4096F);
		command.addCommandData(PRESSAO, oPressao);
                
                ondamic = Integer.parseInt(splitedStr[1]);
		Float oOndamic = new Float(( (ondamic) * 5) / 4096F);
		command.addCommandData(ONDA_MIC, oOndamic);                		
		
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
