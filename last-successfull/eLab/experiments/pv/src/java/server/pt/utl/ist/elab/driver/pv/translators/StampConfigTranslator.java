/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.pv.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String USER_POS_HIGH_STR = "UserPosHigh";
	public static final String USER_POS_LOW_STR = "UserPosLow";
	public static final String NUMSAMPLES_STR = "Numero de Amostras";
	public static final String FREQ_INTERVAL_STR = "Intervalo entre amostras";

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
		float userposhighf = ((Float) command.getCommandData(StampConfigTranslator.USER_POS_HIGH_STR)).floatValue();
		userposhighf = userposhighf * 10.F;
		float userposlowf = ((Float) command.getCommandData(StampConfigTranslator.USER_POS_LOW_STR)).floatValue();
		userposlowf = userposlowf * 10.F;
		final int userposlow = (int) Math.floor(userposlowf);
		final int userposhigh = (int) Math.floor(userposhighf);
		final int dt = ((Integer) command.getCommandData(StampConfigTranslator.FREQ_INTERVAL_STR)).intValue();

		if (userposhigh > 70 || userposhigh < 40) {
			System.out.println("userposhigh is wrong..." + userposhigh);
			return false;
		}

		if (userposlow > 70 || userposlow < 40) {
			System.out.println("userposlow is wrong..." + userposlow);
			return false;
		}

		if (userposlow == userposhigh) {
			System.out.println("userposhigh is equal to userposlow...");
			return false;
		}

		/*
		 * if(numsamples>Math.abs(userposhigh-userposlow)*8) {
		 * System.out.println("numsamples>=|uph-upl|*8"); return false; }
		 */

		if (numsamples < 1) {
			System.out.println("numsamples<1");
			return false;
		}

		/*
		 * if(dt<1 || dt>2000 || dt*numsamples>72000) {
		 * System.out.println("dt*numsamples>72000 || dt<1 || dt>2000  -  dt=" +
		 * dt + " numsaples=" + numsamples); return false; }
		 */

		// Horacio said that this parameters can't have zeros
		final String userposlowstr = "" + userposlow;
		// while (userposlowstr.length() < 4)
		// userposlowstr = "0" + userposlowstr;
		//
		final String userposhighstr = "" + userposhigh;
		// while (userposhighstr.length() < 4)
		// userposhighstr = "0" + userposhighstr;

		String numSamplesStr = "" + (numsamples - 1);
		while (numSamplesStr.length() < 3) {
			numSamplesStr = "0" + numSamplesStr;
		}

		String dtStr = "" + (dt - 1);
		while (dtStr.length() < 4) {
			dtStr = "0" + dtStr;
		}

		final String commandStr = command.getCommandIdentifier() + " " + userposlowstr + " " + userposhighstr + " "
				+ numSamplesStr + " " + dtStr;
		command.setCommand(commandStr);

		return true;
	}

}