/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.serial.stamp.pendulo.translators;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;
/**
 *
 * @author  bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator
{
	
	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
	
	public static final String HEIGHT_INIT_STR="Altura";
	public static final String TETA_INIT_STR="Amplitude inicial";
	public static final String FRICTION_STR="Atrito";
	public static final String NUMSAMPLES_STR="Numero de Amostras";
	public static final String FREQ_INTERBAL_STR="Intervalo entre amostras";
	
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
		int height = ((Integer)command.getCommandData(HEIGHT_INIT_STR)).intValue();
                int teta = ((Integer)command.getCommandData(TETA_INIT_STR)).intValue();
		int freq = ((Integer)command.getCommandData(FREQ_INTERBAL_STR)).intValue();
		
		
		if(height>90 || height<50)
		    return false;
		
		if(teta>10 || teta<0)
		    return false;
		
		//teta=(int)Math.floor((float)teta*135./10.);
		teta=(int)Math.floor((float)teta*13.5);
		
		if(numsamples>10000 || numsamples<1)
		    return false;
		
		if(freq<25 || freq>125)
		    return false;
		
		if(numsamples>4*freq)
		    return false;
		
		String status=(String)command.getCommandData(FRICTION_STR);
		if(status.equals("Sem atrito"))
		    status="00";
		else if(status.equals("25 Ohm"))
		    status="01";
		else if(status.equals("17 Ohm"))
		    status="10";
		else if(status.equals("12 Ohm"))
		    status="11";
		
		
		String heightstr=""+height;
		while(heightstr.length()<2)
			heightstr="0"+heightstr;
		
		String tetastr=""+teta;
		while(tetastr.length()<3)
			tetastr="0"+tetastr;
		
                String numSamplesStr=""+(numsamples);
		while(numSamplesStr.length()<4)
			numSamplesStr="0"+numSamplesStr;
		
		String freqStr=""+(freq-1);
		while(freqStr.length()<3)
			freqStr="0"+freqStr;
		
		String commandStr = command.getCommandIdentifier() + " " + heightstr + " " +  tetastr  + " " + numSamplesStr + " " + freqStr + " " + status;
		command.setCommand(commandStr);
		
		return true;
	}
	
}
