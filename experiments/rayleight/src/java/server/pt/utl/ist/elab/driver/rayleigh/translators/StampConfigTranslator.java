/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.rayleigh.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;
/**
 *
 * @author  bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator
{
	
	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
	
        public static final String NUMSAMPLES_STR = "NumSamples";
        
	public static final String DELTAX_STR = "DeltaX";
        
        /**MODO = TIPO DE EXPERIENCIA; COR (UPDATE NEEDED)...*/
        int modo = 1;
        int cor = 1;
	
	/** Creates a new instance of StampRelayTranslator */
	public StampConfigTranslator()
	{
		super(COMMAND_IDENTIFIER);
	}
	
	public boolean translate(StampCommand command)
	{
		if(command.getCommandIdentifier()==null) return false;
		if(!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER)) return false;
		
                int numsamples = ((Integer)command.getCommandData(NUMSAMPLES_STR)).intValue();
                String numsamples_str = "" + numsamples;
                while(numsamples_str.length() < 3)
                {
                    numsamples_str = "0" + numsamples_str;
                }
                
                int deltax = Integer.parseInt(command.getCommandData(DELTAX_STR).toString().trim());
                String deltax_str = "" + deltax;
                while(deltax_str.length() < 4)
                {
                    deltax_str = "0" + deltax_str;
                }                                
                
		String commandStr = command.getCommandIdentifier() + " " + deltax_str + " " + numsamples_str + " " + modo + " " + cor;
		command.setCommand(commandStr);		
		return true;
	}
	
}
