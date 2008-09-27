/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.serial.stamp.gamma.translators;

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
    
    public static final String VOLUME_STR = "Volume";
    public static final String FREQ_STR = "Freq";
    public static final String NUMSAMPLES_STR="Numero de Amostras";
    
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
        int volume = ((Integer)command.getCommandData(VOLUME_STR)).intValue();
        
        int vol_to_send = 350- ((volume - 5 ) * 350)/15;
        
        int T = (int)(1. / ((Integer)command.getCommandData(FREQ_STR)).doubleValue()/5.4253E-04);
        
        String commandStr = command.getCommandIdentifier() + " " + vol_to_send + " " +  T + " " + numsamples;
        command.setCommand(commandStr);
        
        return true;
    }
    
}
