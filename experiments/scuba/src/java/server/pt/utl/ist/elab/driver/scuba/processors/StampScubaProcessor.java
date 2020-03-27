/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.scuba.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampScubaProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "PRESSOES";
	public static final String PRESSAO_CH_0 = "PRESSAO_CH_0";
	public static final String PRESSAO_CH_1 = "PRESSAO_CH_1";
	public static final String PRESSAO_CH_2 = "PRESSAO_CH_2";
	public static final String PRESSAO_CH_3 = "PRESSAO_CH_3";
	public static final String PROFUNDIDADE = "PROFUNDIDADE";

	/** Creates a new instance of StampHelloProcessor */
	public StampScubaProcessor() {
		super(StampScubaProcessor.COMMAND_IDENTIFIER);
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
		float pressure0 = 0.F;
		float pressure1 = 0.F;
		float pressure2 = 0.F;
		float pressure3 = 0.F;
		int profundidade = 0;

		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampScubaProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				pressure0 = Float.parseFloat(splitedStr[0]);
				Float oPressure = Float.valueOf(10. * (pressure0 / 2048.));
				command.addCommandData(StampScubaProcessor.PRESSAO_CH_0, oPressure);

				pressure1 = Float.parseFloat(splitedStr[1]);
				oPressure = Float.valueOf(10. * (pressure1 / 2048.));
				command.addCommandData(StampScubaProcessor.PRESSAO_CH_1, oPressure);

				pressure2 = Float.parseFloat(splitedStr[2]);
				oPressure = Float.valueOf(10. * (pressure2 / 2048.));
				command.addCommandData(StampScubaProcessor.PRESSAO_CH_2, oPressure);

				pressure3 = Float.parseFloat(splitedStr[3]);
				oPressure = Float.valueOf(10. * (pressure3 / 2048.));
				command.addCommandData(StampScubaProcessor.PRESSAO_CH_3, oPressure);

				profundidade = Integer.parseInt(splitedStr[4]);
				final Integer oProfundidade = Integer.valueOf(profundidade);
				command.addCommandData(StampScubaProcessor.PROFUNDIDADE, oProfundidade);

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
