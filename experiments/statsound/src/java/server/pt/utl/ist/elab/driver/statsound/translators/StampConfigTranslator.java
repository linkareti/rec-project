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
		super(StampConfigTranslator.COMMAND_IDENTIFIER);
	}

	@Override
	public boolean translate(final StampCommand command) {
		if (command.getCommandIdentifier() == null) {
			return false;
		}
		if (!command.getCommandIdentifier().equalsIgnoreCase(StampConfigTranslator.COMMAND_IDENTIFIER)) {
			return false;
		}
		final int numSamples = ((Integer) command.getCommandData(StampConfigTranslator.NUMSAMPLES_STR)).intValue();
		final int xini = ((Integer) command.getCommandData(StampConfigTranslator.POS_INIT_STR)).intValue() - 1264;
		final int xfin = ((Integer) command.getCommandData(StampConfigTranslator.POS_FIN_STR)).intValue() - 1264;
		final int reset = ((Integer) command.getCommandData(StampConfigTranslator.RESET_STR)).intValue();

		final String status = (String) command.getCommandData(StampConfigTranslator.STATUS_STR);

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

		final String resetStr = "" + reset;

		System.out.println("Received:\n");
		System.out.println("numSamplesStr=" + numSamplesStr);
		System.out.println("xiniStr=" + xiniStr);
		System.out.println("xfinStr=" + xfinStr);
		System.out.println("statusStr=" + statusStr);
		System.out.println("resetStr=" + resetStr);

		final String commandStr = command.getCommandIdentifier() + " " + numSamplesStr + " " + xiniStr + " " + xfinStr
				+ " " + statusStr + " " + resetStr;
		command.setCommand(commandStr);
		return true;
	}
}
