/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.conducaocalor.translators;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

	public static String HEAT_SCT_LOGGER = "HeatStampConfigTranslator.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(StampConfigTranslator.HEAT_SCT_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(StampConfigTranslator.HEAT_SCT_LOGGER));
		}
	}

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;

	public static final String HEAT_TIME_STR = "Tempo aquecimento";
	public static final String MAX_HEAT_STR = "Temperatura maxima";
	public static final String MODE_STR = "Modo";
	public static final String NUMSAMPLES_STR = "NumSamples";
	public static final String TBS_STR = "TBS";

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
		final int TBS = ((Integer) command.getCommandData(StampConfigTranslator.TBS_STR)).intValue();
		final int heat_time = ((Integer) command.getCommandData(StampConfigTranslator.HEAT_TIME_STR)).intValue();
		final String mode = (String) command.getCommandData(StampConfigTranslator.MODE_STR);
		final int max_heat = ((Integer) command.getCommandData(StampConfigTranslator.MAX_HEAT_STR)).intValue();

		final int modo = mode.equalsIgnoreCase("Estacionario") ? 0 : 1;

		final String modeStr = "" + modo;

		String heatTimeStr = "" + heat_time;
		while (heatTimeStr.length() < 3) {
			heatTimeStr = "0" + heatTimeStr;
		}

		String maxHeatStr = "" + max_heat;
		while (maxHeatStr.length() < 3) {
			maxHeatStr = "0" + maxHeatStr;
		}

		String numSamplesStr = "" + (numsamples); // JP tinha numsamples - 1,
		// pq??
		while (numSamplesStr.length() < 4) {
			numSamplesStr = "0" + numSamplesStr;
		}

		String TBSStr = "" + TBS;
		while (TBSStr.length() < 3) {
			TBSStr = "0" + TBSStr;
		}

		final String commandStr = command.getCommandIdentifier() + " " + TBSStr + " " + heatTimeStr + " " + maxHeatStr
				+ " " + numSamplesStr + " " + modeStr;
		command.setCommand(commandStr);
		return true;
	}

}
