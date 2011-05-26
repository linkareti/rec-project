/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.planck.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String NUMSAMPLES_STR = "NumSamples";

	public static final String COLOR_STR = "Filter1";
	public static final String DARK_LEVEL_STR = "Filter2";
	public static final String TETAI_STR = "AngPos";
	public static final String MODE_STR = "Mode";

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

		final int numsamples = ((Integer) command.getCommandData(StampConfigTranslator.NUMSAMPLES_STR)).intValue();

		int color = 0;
		final String colStr = command.getCommandData(StampConfigTranslator.COLOR_STR).toString();
		if (colStr.equalsIgnoreCase("Yellow")) {
			color = 0;
		} else if (colStr.equalsIgnoreCase("Green")) {
			color = 1;
		} else if (colStr.equalsIgnoreCase("Pink")) {
			color = 2;
		} else {
			color = 3;
		}

		int dlevel = 0;
		final String dlevelStr = command.getCommandData(StampConfigTranslator.DARK_LEVEL_STR).toString();
		if (dlevelStr.equalsIgnoreCase("0")) {
			dlevel = 5;
		} else if (colStr.equalsIgnoreCase("20")) {
			dlevel = 4;
		} else if (colStr.equalsIgnoreCase("40")) {
			dlevel = 3;
		} else if (colStr.equalsIgnoreCase("60")) {
			dlevel = 2;
		} else if (colStr.equalsIgnoreCase("80")) {
			dlevel = 1;
		} else {
			dlevel = 0;
		}

		int mode = 0;
		final String modeStr = command.getCommandData(StampConfigTranslator.MODE_STR).toString();
		if (modeStr.equalsIgnoreCase("All")) {
			mode = 0;
		} else {
			mode = 1;
		}

		final float tetai = ((Float) command.getCommandData(StampConfigTranslator.TETAI_STR)).floatValue();
		// int teta = (int)(tetai * 434.7f - 2505.4f);
		final int teta = (int) ((tetai - 12) * 662.8f + 430f);

		final String commandStr = command.getCommandIdentifier() + " " + numsamples + " " + color + " " + dlevel + " "
				+ teta + " " + mode;
		command.setCommand(commandStr);
		return true;
	}

}
