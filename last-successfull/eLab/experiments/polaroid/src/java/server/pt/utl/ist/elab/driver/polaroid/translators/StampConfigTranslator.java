/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.polaroid.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String POS_INI_POL_STR = "PosIniPolMovel";
	public static final String POS_END_POL_STR = "PosEndPolMovel";
	public static final String POS_FIXO_STR = "PosFixo";
	public static final String LUZ_POL_STR = "LuzPol";
	public static final String CALIB_STR = "Calib";
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
		final int posini = (((Integer) command.getCommandData(StampConfigTranslator.POS_INI_POL_STR)).intValue() * 155) / 180;
		final int posend = (((Integer) command.getCommandData(StampConfigTranslator.POS_END_POL_STR)).intValue() * 155) / 180;
		final int posfixo = (((Integer) command.getCommandData(StampConfigTranslator.POS_FIXO_STR)).intValue() * 155) / 180;
		final int luzpol = ((Integer) command.getCommandData(StampConfigTranslator.LUZ_POL_STR)).intValue();
		final int calib = ((Integer) command.getCommandData(StampConfigTranslator.CALIB_STR)).intValue();

		String posinistr = "" + posini;
		while (posinistr.length() < 3) {
			posinistr = "0" + posinistr;
		}

		String posendstr = "" + posend;
		while (posendstr.length() < 3) {
			posendstr = "0" + posendstr;
		}

		String posfixostr = "" + posfixo;
		while (posfixostr.length() < 3) {
			posfixostr = "0" + posfixostr;
		}

		final String commandStr = command.getCommandIdentifier() + " " + calib + " " + luzpol + " " + 
							posfixostr + " " + posinistr + " " + posendstr;
		command.setCommand(commandStr);

		return true;
	}

}
