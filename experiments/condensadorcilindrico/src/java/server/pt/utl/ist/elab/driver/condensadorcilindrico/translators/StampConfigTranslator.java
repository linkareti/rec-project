/*
 * StampConfigTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */
package pt.utl.ist.elab.driver.condensadorcilindrico.translators;

import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	/**
	 * 
	 */
	public static final float SCALE_FACTOR_POSITIONS_TO_HARDWARE = (670f / 228f);

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
	public static final String START_POS_STR = "StartPosition";
	public static final String END_POS_STR = "EndPosition";
	public static final String NUMSAMPLES_STR = "Numero de Amostras";
	private static final Logger LOGGER = Logger.getLogger(StampConfigTranslator.class.getName());

	/** Creates a new instance of StampRelayTranslator */
	public StampConfigTranslator() {
		super(StampConfigTranslator.COMMAND_IDENTIFIER);
	}

	@Override
	public boolean translate(final StampCommand command) {
		if (command.getCommandIdentifier() == null
				|| !command.getCommandIdentifier().equalsIgnoreCase(StampConfigTranslator.COMMAND_IDENTIFIER)) {
			return false;
		}

		int numPoints = ((Integer) command.getCommandData(NUMSAMPLES_STR)).intValue();
		float startPos = ((Integer) command.getCommandData(START_POS_STR)).floatValue();
		float endPos = ((Integer) command.getCommandData(END_POS_STR)).floatValue();

		startPos = SCALE_FACTOR_POSITIONS_TO_HARDWARE * startPos;
		endPos = SCALE_FACTOR_POSITIONS_TO_HARDWARE * endPos;

		int startPosInt = (int) (startPos);
		int endPosInt = (int) (endPos);

		final String commandStr = command.getCommandIdentifier() + " " + startPosInt + " " + endPosInt + " "
				+ numPoints;

		LOGGER.fine("sending the command: " + commandStr);

		command.setCommand(commandStr);
		return true;
	}
}
