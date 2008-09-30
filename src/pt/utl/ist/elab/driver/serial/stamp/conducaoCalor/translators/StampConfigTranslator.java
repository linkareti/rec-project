/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.serial.stamp.conducaoCalor.translators;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator
{
    
    public static String HEAT_SCT_LOGGER="HeatStampConfigTranslator.Logger";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(HEAT_SCT_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(HEAT_SCT_LOGGER));
        }
    }
    
    public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
    
    public static final String HEAT_TIME_STR = "Tempo aquecimento";
    public static final String MAX_HEAT_STR = "Temperatura maxima";
    public static final String MODE_STR = "Modo";
    public static final String NUMSAMPLES_STR = "NumSamples";
    public static final String TBS_STR = "TBS";
    
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
        int TBS = ((Integer)command.getCommandData(TBS_STR)).intValue();
        int heat_time = ((Integer)command.getCommandData(HEAT_TIME_STR)).intValue();
        String mode = (String)command.getCommandData(MODE_STR);
        int max_heat = ((Integer)command.getCommandData(MAX_HEAT_STR)).intValue();
        
        int modo = mode.equalsIgnoreCase("Estacionario")?0:1;
        
        String modeStr = "" + modo;
        
        String heatTimeStr = "" + heat_time;
        while(heatTimeStr.length()<3)
            heatTimeStr = "0" + heatTimeStr;
        
        String maxHeatStr = "" + max_heat;
        while(maxHeatStr.length()<3)
            maxHeatStr = "0" + maxHeatStr;
        
        String numSamplesStr = "" + (numsamples); //JP tinha numsamples - 1, pq??
        while(numSamplesStr.length()<4)
            numSamplesStr = "0" + numSamplesStr;
        
        String TBSStr = "" + TBS;
        while(TBSStr.length()<3)
            TBSStr="0"+TBSStr;
        
        String commandStr = command.getCommandIdentifier() + " " + TBSStr + " " + heatTimeStr + " " + maxHeatStr + " " + numSamplesStr + " " + modeStr;
        command.setCommand(commandStr);
        return true;
    }
    
}
