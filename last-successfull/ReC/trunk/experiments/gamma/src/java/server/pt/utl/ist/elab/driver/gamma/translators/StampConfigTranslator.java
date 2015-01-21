/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.gamma.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String VOLUME_STR = "Volume";
	public static final String FREQ_STR = "Freq";
	public static final String NUMSAMPLES_STR = "Numero de Amostras";

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
		final int volume = ((Integer) command.getCommandData(StampConfigTranslator.VOLUME_STR)).intValue();
		final int frequency = ((Integer) command.getCommandData(StampConfigTranslator.FREQ_STR)).intValue();

		final String commandStr = command.getCommandIdentifier() + " " + volume + " " + frequency + " " + numsamples;
		command.setCommand(commandStr);

		return true;
	}

}
