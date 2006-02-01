/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.serial.stamp.momentoInercia.translators;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;
/**
 *
 * @author  bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator
{
	
	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
	
	public static final String LAUNCH_STR = "Launch Iteration";
	public static final String STOP_STR = "Stop Iteration";
	public static final String NUMSAMPLES_STR = "NumSamples";
	public static final String TBS_STR = "TBS";
        public static final String ITER_STR = "Iteration";
	
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
		int launch_time = ((Integer)command.getCommandData(LAUNCH_STR)).intValue();
		int stop_time = ((Integer)command.getCommandData(STOP_STR)).intValue();		
				
                String iteration = (String)command.getCommandData(ITER_STR);
                
                if(iteration.equalsIgnoreCase("Launch"))
                {
                    stop_time = 1201;
                }
                else if(iteration.equalsIgnoreCase("Stop"))
                {
                    launch_time = 1201;
                }
                else if(iteration.equalsIgnoreCase("None"))
                {
                    stop_time = 1201;
                    launch_time = 1201;
                }
                
		String launchTimeStr = "" + launch_time;
		while(launchTimeStr.length()<4)
			launchTimeStr = "0" + launchTimeStr;
		
		String stopStr = "" + stop_time;
		while(stopStr.length()<4)
			stopStr = "0" + stopStr;
		
		String numSamplesStr = "" + (numsamples-1); //Stamp programado para começar a contar em zero
		while(numSamplesStr.length()<4)
			numSamplesStr = "0" + numSamplesStr;
		
		String TBSStr = "" + TBS;
		while(TBSStr.length()<4)
			TBSStr="0"+TBSStr;
				
		String commandStr = command.getCommandIdentifier() + " " + TBSStr + " " + numSamplesStr + " " + stopStr + " " + launchTimeStr;
		command.setCommand(commandStr);		
		return true;
	}
	
}
