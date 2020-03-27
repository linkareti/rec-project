/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.conducaocalor.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampTProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "Temp:";
	public static final String TEMPERATURE_1_BRASS = "Temperature 1 - Brass";
	public static final String TEMPERATURE_2_BRASS = "Temperature 2 - Brass";
	public static final String TEMPERATURE_3_BRASS = "Temperature 3 - Brass";
	public static final String TEMPERATURE_1_IRON = "Temperature 1 - Iron";
	public static final String TEMPERATURE_2_IRON = "Temperature 2 - Iron";
	public static final String TEMPERATURE_3_IRON = "Temperature 3 - Iron";
	public static final String TEMPERATURE_1_COPPER = "Temperature 1 - Copper";
	public static final String TEMPERATURE_2_COPPER = "Temperature 2 - Copper";
	public static final String TEMPERATURE_3_COPPER = "Temperature 3 - Copper";

	/** Creates a new instance of StampHelloProcessor */
	public StampTProcessor() {
		super(StampTProcessor.COMMAND_IDENTIFIER);
	}

	/**
	 * This method makes the Processor process the command passed in...
	 * 
	 * @param command the command that this processor will have to process
	 * @return boolean - wether the processing was successfull
	 * 
	 */
	@Override
	public boolean process(final StampCommand command) {

		int temperature1Brass = 0;
		int temperature2Brass = 0;
		int temperature3Brass = 0;
		int temperature1Iron = 0;
		int temperature2Iron = 0;
		int temperature3Iron = 0;
		int temperature1Copper = 0;
		int temperature2Copper = 0;
		int temperature3Copper = 0;

		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampTProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null && splitedStr.length == 10) {
			try {
				temperature1Brass = Integer.parseInt(splitedStr[1]);
				final Float oTemperature1Brass = Float.valueOf(temperature1Brass / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_1_BRASS, oTemperature1Brass);

				temperature2Brass = Integer.parseInt(splitedStr[2]);
				final Float oTemperature2Brass = Float.valueOf(temperature2Brass / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_2_BRASS, oTemperature2Brass);

				temperature3Brass = Integer.parseInt(splitedStr[3]);
				final Float oTemperature3Brass = Float.valueOf(temperature3Brass / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_3_BRASS, oTemperature3Brass);

				temperature1Iron = Integer.parseInt(splitedStr[4]);
				final Float oTemperature1Iron = Float.valueOf(temperature1Iron / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_1_IRON, oTemperature1Iron);

				temperature2Iron = Integer.parseInt(splitedStr[5]);
				final Float oTemperature2Iron = Float.valueOf(temperature2Iron / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_2_IRON, oTemperature2Iron);

				temperature3Iron = Integer.parseInt(splitedStr[6]);
				final Float oTemperature3Iron = Float.valueOf(temperature3Iron / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_3_IRON, oTemperature3Iron);

				temperature1Copper = Integer.parseInt(splitedStr[7]);
				final Float oTemperature1Copper = Float.valueOf(temperature1Copper / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_1_COPPER, oTemperature1Copper);

				temperature2Copper = Integer.parseInt(splitedStr[8]);
				final Float oTemperature2Copper = Float.valueOf(temperature2Copper / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_2_COPPER, oTemperature2Copper);

				temperature3Copper = Integer.parseInt(splitedStr[9]);
				final Float oTemperature3Copper = Float.valueOf(temperature3Copper / 10.);
				command.addCommandData(StampTProcessor.TEMPERATURE_3_COPPER, oTemperature3Copper);

				command.setData(true);
				return true;

			} catch (final NumberFormatException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	@Override
	public boolean isData() {
		return true;
	}
}
