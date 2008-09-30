/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.serial.stamp.penduloGravitico.translators;

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
    
    public static final String HEIGHT_INIT_STR="Altura";
    public static final String TETA_INIT_STR="Angulo inicial";
    public static final String NUMSAMPLES_STR="Numero de Amostras";
    public static final String FREQ_INTERVAL_STR="Intervalo entre amostras";
    
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
        float height = ((Float)command.getCommandData(HEIGHT_INIT_STR)).floatValue();
        float teta = ((Float)command.getCommandData(TETA_INIT_STR)).floatValue();
        int freq = ((Integer)command.getCommandData(FREQ_INTERVAL_STR)).intValue();
        
        
        if(height>24.9 || height<15.1)
        {
            System.out.println("Crashed at height=" + height);
            return false;
        }
        
        if(teta>13.6 || teta<1.1)
        {
            System.out.println("Crashed at teta=" + teta);
            return false;
        }
        
        if(numsamples>999 || numsamples<1)
        {
            System.out.println("Crashed at samples=" + numsamples);
            return false;
        }
        
        if(freq<0 || freq>110)
        {
            System.out.println("Crashed at freq=" + freq);
            return false;
        }
        
        int height_stamp = (int)((24.837 - height) / 0.012);
        
        String heightstr = "" + height_stamp;
        while(heightstr.length() < 3)
            heightstr = "0" + heightstr;
        
        int teta_stamp = (int)((2.4 + teta) / 0.176);
        
        String tetastr = "" + teta_stamp;
        while(tetastr.length() < 2)
            tetastr = "0" + teta_stamp;
        
        String numSamplesStr = "" + numsamples;
        while(numSamplesStr.length() < 3)
            numSamplesStr = "0" + numSamplesStr;
        
        String freqStr = "" + freq;
        while(freqStr.length()<3)
            freqStr = "0"+freqStr;
        
        String commandStr = command.getCommandIdentifier() + " " + heightstr + " " +  tetastr  + " " + numSamplesStr + " " + freqStr;
        command.setCommand(commandStr);
        
        return true;
    }    
}
