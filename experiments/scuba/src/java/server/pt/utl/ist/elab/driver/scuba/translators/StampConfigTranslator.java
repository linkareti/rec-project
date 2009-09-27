/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.scuba.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String XINI_STR = "xini";
	public static final String XFIN_STR = "xfin";
	public static final String NUMSAMPLES_STR = "Numero de Amostras";
	public static final String CALIB_STR = "calib";

	/** Creates a new instance of StampRelayTranslator */
	public StampConfigTranslator() {
		super(COMMAND_IDENTIFIER);
	}

	public boolean translate(StampCommand command) {
		if (command.getCommandIdentifier() == null)
			return false;
		if (!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER))
			return false;

		int numsamples = ((Integer) command.getCommandData(NUMSAMPLES_STR)).intValue();
		int xini = ((Integer) command.getCommandData(XINI_STR)).intValue();
		int xfin = ((Integer) command.getCommandData(XFIN_STR)).intValue();
		int calib = ((Integer) command.getCommandData(CALIB_STR)).intValue();

		if (xini > 900 || xini < 0) {
			System.out.println("xini is wrong..." + xini);
			return false;
		}

		if (xfin > 900 || xfin < 0) {
			System.out.println("xfin is wrong..." + xfin);
			return false;
		}

		if (xfin == xini) {
			System.out.println("xini is equal to xfin...");
			return false;
		}

		if (numsamples > Math.abs(xini - xfin) * 45) {
			System.out.println("numsamples>=|xini-xfin|*45");
			return false;
		}

		if (numsamples < 1) {
			System.out.println("numsamples<1");
			return false;
		}

		String xinistr = "" + xini;
		while (xinistr.length() < 3)
			xinistr = "0" + xinistr;

		String xfinstr = "" + xfin;
		while (xfinstr.length() < 3)
			xfinstr = "0" + xfinstr;

		String numSamplesStr = "" + (numsamples - 1);
		while (numSamplesStr.length() < 3)
			numSamplesStr = "0" + numSamplesStr;

		String calibStr = "" + calib;

		String commandStr = command.getCommandIdentifier() + " " + xinistr + " " + xfinstr + " " + numSamplesStr + " "
				+ calibStr;
		command.setCommand(commandStr);

		return true;
	}

}
