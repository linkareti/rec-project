/*
 * StampPolaroidProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.polaroid.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;

/**
 *
 * @author  bruno
 */
public class StampPolaroidProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "POS=";
    public static final String INTENSIDADE = "Intensidade";
    public static final String ANGULO = "Angulo";
    /** Creates a new instance of StampHelloProcessor */
    public StampPolaroidProcessor()
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
	
	float angulo = 0;
        float intensidade = 0;
	
	String[] splitedStr = command.getCommand().split("\t");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr!=null && splitedStr.length>=2 && splitedStr[0]!=null && splitedStr[1]!=null)
	{
	    try
	    {
                angulo = Float.parseFloat(splitedStr[0]);
                Float oAngulo = new Float(angulo * 0.1535);
                command.addCommandData(ANGULO, oAngulo);

                intensidade = Float.parseFloat(splitedStr[1]);
                Float oIntensidade = new Float((intensidade * 5) / 4095F);
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
    
    @Override
    public boolean isData()
    {
	return true;
    }
}
