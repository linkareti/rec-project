/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.statsound.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String NUMSAMPLES_STR = "number of samples";
	public static final String POS_INIT_STR = "Piston start";
	public static final String POS_FIN_STR = "Piston end";
	public static final String STATUS_STR = "Status";
	public static final String RESET_STR = "Calibration";

	/** Creates a new instance of StampRelayTranslator */
	public StampConfigTranslator() {
		super(COMMAND_IDENTIFIER);
	}

	public boolean translate(StampCommand command) {
		if (command.getCommandIdentifier() == null) {
			return false;
		}
		if (!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER)) {
			return false;
		}
		int numSamples = ((Integer) command.getCommandData(NUMSAMPLES_STR)).intValue();
		int xini = ((Integer) command.getCommandData(POS_INIT_STR)).intValue() - 1264;
		int xfin = ((Integer) command.getCommandData(POS_FIN_STR)).intValue() - 1264;
		int reset = ((Integer) command.getCommandData(RESET_STR)).intValue();

		String status = (String) command.getCommandData(STATUS_STR);

		String numSamplesStr = "" + numSamples;
		while (numSamplesStr.length() < 3) {
			numSamplesStr = "0" + numSamplesStr;
		}

		String xiniStr = "" + xini;
		while (xiniStr.length() < 3) {
			xiniStr = "0" + xiniStr;
		}

		String xfinStr = "" + xfin;
		while (xfinStr.length() < 3) {
			xfinStr = "0" + xfinStr;
		}

		String statusStr = "" + status;
		while (statusStr.length() < 4) {
			statusStr = "0" + statusStr;
		}

		String resetStr = "" + reset;

		System.out.println("Received:\n");
		System.out.println("numSamplesStr=" + numSamplesStr);
		System.out.println("xiniStr=" + xiniStr);
		System.out.println("xfinStr=" + xfinStr);
		System.out.println("statusStr=" + statusStr);
		System.out.println("resetStr=" + resetStr);

		String commandStr = command.getCommandIdentifier() + " " + numSamplesStr + " " + xiniStr + " " + xfinStr + " "
				+ statusStr + " " + resetStr;
		command.setCommand(commandStr);
		return true;
	}
}
