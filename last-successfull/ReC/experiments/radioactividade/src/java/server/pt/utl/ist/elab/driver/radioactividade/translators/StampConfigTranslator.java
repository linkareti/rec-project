/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.radioactividade.translators;

import pt.utl.ist.elab.driver.radioactividade.RadioactividadeStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String MODE_STR = "Modo de aquisicao";
	public static final String MATERIAL_STR = "Material";
	public static final String NUMSAMPLES_STR = "Numero de Amostras";
	public static final String HEIGHT_STR = "Altura do detector";

	private static final int MIN_MODE = 0;
	private static final int MAX_MODE = 2;
	private static final int MIN_MATERIAL = 0;
	private static final int MAX_MATERIAL = 8;
	public static final int MIN_HEIGHT = 0;
	public static final int STEP_HEIGHT = 10;
	public static final int MAX_HEIGHT = 100;
	public static final int MIN_SAMPLES = 1;
	public static final int MAX_SAMPLES = 100;

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

		int mode = -1;
		int material = -1;
		final String modeStr = (String) command.getCommandData(StampConfigTranslator.MODE_STR);
		final String materialStr = (String) command.getCommandData(StampConfigTranslator.MATERIAL_STR);
		final int height = Integer.parseInt((String) command.getCommandData(StampConfigTranslator.HEIGHT_STR)) / 10;
		final int numsamples = ((Integer) command.getCommandData(StampConfigTranslator.NUMSAMPLES_STR)).intValue();

		for (int i = 0; i < RadioactividadeStampDriver.ACQUISITION_MODES.length; i++) {
			System.out.println("Comparing: " + modeStr + " with " + RadioactividadeStampDriver.ACQUISITION_MODES[i]);
			if (RadioactividadeStampDriver.ACQUISITION_MODES[i].equalsIgnoreCase(modeStr)) {
				mode = i;
				break;
			}
		}
		for (int i = 0; i < RadioactividadeStampDriver.ACQUISITION_MATERIALS.length; i++) {
			if (RadioactividadeStampDriver.ACQUISITION_MATERIALS[i].equalsIgnoreCase(materialStr)) {
				material = i;
				break;
			}
		}

		if (mode == -1) {
			System.out.println("Wrong Mode");
			return false;
		}
		if (material == -1) {
			System.out.println("Wrong Material");
			return false;
		}
		if (height < StampConfigTranslator.MIN_HEIGHT || height > StampConfigTranslator.MAX_HEIGHT) {
			System.out.println("Wrong height");
			return false;
		}
		if (numsamples < StampConfigTranslator.MIN_SAMPLES || numsamples > StampConfigTranslator.MAX_SAMPLES) {
			System.out.println("Wrong height");
			return false;
		}

		String heightStr = "" + height;
		while (heightStr.length() < 2) {
			heightStr = "0" + heightStr;
		}

		String numSamplesStr = "" + (numsamples - 1);
		while (numSamplesStr.length() < 2) {
			numSamplesStr = "0" + numSamplesStr;
		}

		final String commandStr = command.getCommandIdentifier() + " " + heightStr + " " + material + " " + mode + " "
				+ numSamplesStr;
		command.setCommand(commandStr);

		return true;
	}

}
