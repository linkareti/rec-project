/* 
 * SerialPortTranslator.java created on 19 Aug 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package pt.utl.ist.elab.driver.serial.serialportgeneric.translator;

import java.util.Collections;
import java.util.List;

import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommand;
import pt.utl.ist.elab.driver.serial.serialportgeneric.command.SerialPortCommandList;
import pt.utl.ist.elab.driver.serial.serialportgeneric.config.OneParameterNode;

/**
 * 
 * @author npadriano
 */
public class SerialPortTranslator {

	/**
	 * Creates the <code>SerialPortTranslator</code>.
	 */
	private SerialPortTranslator() {
	}
	
	public static boolean translateConfig(SerialPortCommand command, List<OneParameterNode> commandParameterNodes) {

		// check if it is a cfg command and has parameters
		if (command.getCommandIdentifier() == null
				|| !SerialPortCommandList.CFG.toString().toLowerCase().equals(command.getCommandIdentifier())
				|| commandParameterNodes == null || commandParameterNodes.size() == 0) {
			return false;
		}
		
		// FIXME TODO validate the parameter values

		// validation example code from g experiment
//		int numsamples = ((Integer) command.getCommandData(NUMSAMPLES_STR)).intValue();
//		int delay_time = ((Integer) command.getCommandData(DELAY_STR)).intValue();
//		int power = ((Integer) command.getCommandData(POWER_STR)).intValue();
//
//		if (power > 100 || power < 0) {
//			System.out.println("power launch is wrong..." + power);
//			return false;
//		}
//
//		if (delay_time > 250 || delay_time < 10) {
//			System.out.println("delay_time is wrong..." + delay_time);
//			return false;
//		}
//
//		if (numsamples > (int) Math.floor(500. - ((float) delay_time - 1.) * 480. / 249.)) {
//			System.out.println("numsamples>=500-(delay_time-1)*480/249)");
//			return false;
//		}
//
//		if (numsamples < 10) {
//			System.out.println("numsamples<10");
//			return false;
//		}
//
//		String powerstr = "" + power;
//		while (powerstr.length() < 3)
//			powerstr = "0" + powerstr;
//
//		String delay_time_str = "" + delay_time;
//		while (delay_time_str.length() < 3)
//			delay_time_str = "0" + delay_time_str;
//
//		String numSamplesStr = "" + (numsamples - 1);
//		while (numSamplesStr.length() < 4)
//			numSamplesStr = "0" + numSamplesStr;
		
		// ordering list by order
		Collections.sort(commandParameterNodes, new OneParameterNodeOrderComparator());
		
		// build the command
		String commandStr = command.getCommandIdentifier();
		for (OneParameterNode oneParameterNode : commandParameterNodes) {
			String parameterValue = command.getCommandData(oneParameterNode.getOrder().toString());
			String formatedParameterValue = oneParameterNode.formatOutput(Double.valueOf(parameterValue));
			commandStr.concat("\t").concat(formatedParameterValue);
		}
		command.setCommand(commandStr);

		return true;
	}
	
	public static void translateReset(SerialPortCommand command) {
		command.setCommand(command.getCommandIdentifier());
	}
	
	public static void translateStart(SerialPortCommand command) {
		command.setCommand(command.getCommandIdentifier());
	}
	
	public static void translateStop(SerialPortCommand command) {
		command.setCommand(command.getCommandIdentifier());
	}

}
