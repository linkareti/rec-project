/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.usb.cypress.gamma.translators;

import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver;
import pt.utl.ist.elab.driver.usb.cypress.transproc.AbstractCypressTranslator;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

/**
 *
 * @author  bruno
 */
public class CypressConfigTranslator extends AbstractCypressTranslator
{
    
    public static final String COMMAND_IDENTIFIER = AbstractCypressDriver.CONFIG_OUT_STRING;
    
    public static final String VOLUME_STR = "Volume";
    public static final String FREQ_STR = "Freq";
    public static final String NUMSAMPLES_STR="Numero de Amostras";
    
    /** Creates a new instance of StampRelayTranslator */
    public CypressConfigTranslator()
    {
        super(COMMAND_IDENTIFIER);
    }
    
    public boolean translate(CypressCommand command)
    {
        if(command.getCommandIdentifier()==null) return false;
        if(!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER)) return false;
        
        int numsamples = ((Integer)command.getCommandData(NUMSAMPLES_STR)).intValue() - 20;
        int volume = ((Integer)command.getCommandData(VOLUME_STR)).intValue();
        
        int vol_to_send = (volume - 5 ) * 33 / 15;
        
        int T = (1000000 / ((Integer)command.getCommandData(FREQ_STR)).intValue());
        
        String commandStr = command.getCommandIdentifier() + " " + vol_to_send + " " +  T + " " + numsamples;
        command.setCommand(commandStr);
        
        return true;
    }
    
}
