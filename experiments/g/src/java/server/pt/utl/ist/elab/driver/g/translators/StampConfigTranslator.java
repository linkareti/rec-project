/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.g.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String POWER_STR = "power";
	public static final String NUMSAMPLES_STR = "Numero de Amostras";
	public static final String DELAY_STR = "delay_time";

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
		final int delay_time = ((Integer) command.getCommandData(StampConfigTranslator.DELAY_STR)).intValue();
		final int power = ((Integer) command.getCommandData(StampConfigTranslator.POWER_STR)).intValue();

		if (power > 100 || power < 0) {
			System.out.println("power launch is wrong..." + power);
			return false;
		}

		if (delay_time > 250 || delay_time < 10) {
			System.out.println("delay_time is wrong..." + delay_time);
			return false;
		}

		if (numsamples > (int) Math.floor(500. - (delay_time - 1.) * 480. / 249.)) {
			System.out.println("numsamples>=500-(delay_time-1)*480/249)");
			return false;
		}

		if (numsamples < 10) {
			System.out.println("numsamples<10");
			return false;
		}

		String powerstr = "" + power;
		while (powerstr.length() < 3) {
			powerstr = "0" + powerstr;
		}

		String delay_time_str = "" + delay_time;
		while (delay_time_str.length() < 3) {
			delay_time_str = "0" + delay_time_str;
		}

		String numSamplesStr = "" + (numsamples - 1);
		while (numSamplesStr.length() < 4) {
			numSamplesStr = "0" + numSamplesStr;
		}

		final String commandStr = command.getCommandIdentifier() + " " + numSamplesStr + " " + powerstr + " "
				+ delay_time_str;
		command.setCommand(commandStr);

		return true;
	}

}
