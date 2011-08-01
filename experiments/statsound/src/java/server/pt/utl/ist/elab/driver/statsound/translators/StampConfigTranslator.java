/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */
package pt.utl.ist.elab.driver.statsound.translators;

import static pt.utl.ist.elab.driver.statsound.StatSoundStampDriver.*;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	/**
	 * The position sent to the hardware is related to a base/reference piston
	 * position. Therefore, we do not send what we get from the client but,
	 * instead, we send the delta.
	 */
	private static final int BASE_PISTON_POSITION = 1263;

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

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
		final int numSamples = ((Integer) command.getCommandData(NUMSAMPLES_COMMAND_PART)).intValue();
		final int xini = ((Integer) command.getCommandData(PISTON_START_COMMAND_PART)).intValue()
				- BASE_PISTON_POSITION;
		final int xfin = ((Integer) command.getCommandData(PISTON_END_COMMAND_PART)).intValue() - BASE_PISTON_POSITION;
		final int reset = ((Integer) command.getCommandData(CALIBRATION_COMMAND_PART)).intValue();
		final String status = (String) command.getCommandData(STATUS_COMMAND_PART);

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
