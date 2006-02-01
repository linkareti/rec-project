/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.momentoInercia.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;

/**
 *
 * @author  bruno
 */
public class StampMomInerciaProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "VEL:";
    public static final String VELOCITY = "velocity";
    public static final String POWER = "power";

    /** Creates a new instance of StampHelloProcessor */
    public StampMomInerciaProcessor()
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
	
	int velocity = 0;
	int potential = 0;
	
	String[] splitedStr = command.getCommand().split(" ");	
        
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0]!=null)
	{
	    try
	    {
		velocity = Integer.parseInt(splitedStr[1]);
                Float oVelocity = null;
                if(velocity == 0)
                {
                    oVelocity= new Float(0);
                }
                else
                {
                    oVelocity= new Float(60000000f/(velocity * 4 *2));
                }
                                
		command.addCommandData(VELOCITY,oVelocity);
		
                System.out.println("oVelocity =" + oVelocity.floatValue());
                
		potential = Integer.parseInt(splitedStr[2]);                
                float power = (potential*5f/4096f)*(potential*5/4096f) * 3f/4.7f;                
                
		Float oPotential = new Float((power));
		command.addCommandData(POWER,oPotential);
		
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
