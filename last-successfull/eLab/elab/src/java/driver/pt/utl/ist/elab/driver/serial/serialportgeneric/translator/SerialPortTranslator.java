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

	public static boolean translateConfig(final SerialPortCommand command,
			final List<OneParameterNode> commandParameterNodes) {

		// check if it is a cfg command and has parameters
		if (command.getCommandIdentifier() == null
				|| !SerialPortCommandList.CFG.toString().toLowerCase().equals(command.getCommandIdentifier())
				|| commandParameterNodes == null || commandParameterNodes.size() == 0) {
			return false;
		}

		// the parameter values validation must be made in the GUI

		// ordering list by order
		Collections.sort(commandParameterNodes, new OneParameterNodeOrderComparator());

		// build the command
		final StringBuilder commandStr = new StringBuilder(command.getCommandIdentifier());
		for (final OneParameterNode oneParameterNode : commandParameterNodes) {
			final String parameterValue = command.getCommandData(oneParameterNode.getOrder().toString());
			final String formatedParameterValue = oneParameterNode.formatOutput(Double.valueOf(parameterValue));
			commandStr.append("\t").append(formatedParameterValue);
		}
		command.setCommand(commandStr.toString());

		return true;
	}

	public static void translate(final SerialPortCommand command) {
		command.setCommand(command.getCommandIdentifier());
	}

}
