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
public class StampGammaProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "G";
    public static final String ONDA_MIC = "OndaMic";
    public static final String PRESSAO = "Pressao";
    public static final String TIME = "time";
    
    /** Creates a new instance of StampHelloProcessor */
    public StampGammaProcessor()
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
	int time = 0;
        
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr!=null && splitedStr.length>=3 && splitedStr[0]!=null && splitedStr[1]!=null && splitedStr[2]!=null)
	{
	    try
	    {                
                pressao = Integer.parseInt(splitedStr[0]);
		Float oPressao = new Float(106 + ( (pressao * 1.68) * 1000) / 4096F);
		command.addCommandData(PRESSAO, oPressao);
                
                ondamic = Integer.parseInt(splitedStr[1]);
		Float oOndamic = new Float(( (ondamic) * 5) / 4096F);
		command.addCommandData(ONDA_MIC, oOndamic);                		
		
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
