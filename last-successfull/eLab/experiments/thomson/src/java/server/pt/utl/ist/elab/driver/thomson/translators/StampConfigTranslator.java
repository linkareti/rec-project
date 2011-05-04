/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.thomson.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String V_ACELERACAO_STR = "tensaoaceleracao";
	public static final String I_BOBINES_STR = "correntebobines";
	public static final String MODO_STR = "modo";
	public static final String NUMSAMPLES_STR = "NumSamples";

	/** Creates a new instance of StampRelayTranslator */
	public StampConfigTranslator() {
		super(COMMAND_IDENTIFIER);
	}

	public boolean translate(StampCommand command) {
		if (command.getCommandIdentifier() == null)
			return false;
		if (!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER))
			return false;

		int vAccel = ((Integer) command.getCommandData(V_ACELERACAO_STR)).intValue();

		vAccel = (int) Math.abs((vAccel * 255 * 27 / 40000) / 5);

		if (vAccel > 255)
			vAccel = 255;

		String svAccel = "" + vAccel;
		while (svAccel.length() < 3)
			svAccel = "0" + svAccel;

		int realI = ((Integer) command.getCommandData(I_BOBINES_STR)).intValue();

		int iBob = (int) (Math.abs(realI) * 170 / 2000);
		String modo = command.getCommandData(MODO_STR).toString();

		if (iBob > 255)
			iBob = 255;

		String siBob = "" + iBob;
		while (siBob.length() < 3)
			siBob = "0" + siBob;

		int invert = 0;
		if (realI < 0)
			invert = 1;

		int open = 0;
		if (modo.equalsIgnoreCase("defmag"))
			open = 1;

		String commandStr = command.getCommandIdentifier() + " " + siBob + " " + svAccel + " " + open + " " + invert;
		command.setCommand(commandStr);

		return true;
	}

}
