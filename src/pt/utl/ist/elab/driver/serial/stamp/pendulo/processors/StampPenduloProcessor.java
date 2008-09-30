/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.pendulo.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampPenduloProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "D";
    public static final String ANGLE_VEL="Angular Speed";
    /** Creates a new instance of StampHelloProcessor */
    public StampPenduloProcessor()
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
	float angle_vel= 0;
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
		angle_vel= Float.parseFloat(splitedStr[0]);
		Float oangle_vel= new Float(5.*(angle_vel-2048.)/4096.);
		command.addCommandData(ANGLE_VEL,oangle_vel);
		
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
