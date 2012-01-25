/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */
package pt.utl.ist.elab.driver.gamma.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampGammaProcessor extends AbstractStampProcessor {

	public static final String COMMAND_IDENTIFIER = "G";
	public static final String ONDA_MIC = "OndaMic";
	public static final String PRESSAO = "Pressao";
	public static final String TIME = "time";
	public static int period = 1;
	public static int clock_freq = 1;

	/** Creates a new instance of StampHelloProcessor */
	public StampGammaProcessor() {
		super(StampGammaProcessor.COMMAND_IDENTIFIER);
	}

	/**
	 * This method makes the Processor process the command passed in...
	 * 
	 * @param command the command that this processor will have to process
	 * @return boolean - wether the processing was successful
	 * 
	 */
	@Override
	public boolean process(final StampCommand command) {

		int ondamic = 0;
		int pressao = 0;
		final int time = 0;

		final String[] splitedStr = command.getCommand().split("\t");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampGammaProcessor.COMMAND_IDENTIFIER)
				&& splitedStr != null && splitedStr.length >= 2 && splitedStr[0] != null && splitedStr[1] != null) {
			try {
				if ("PARAMETROS".equals(splitedStr[0])) {
					StampGammaProcessor.clock_freq = Integer.parseInt(splitedStr[1]);
					StampGammaProcessor.period = Integer.parseInt(splitedStr[3]);

				} else {
					final Float oTime = new Float((StampGammaProcessor.period * 1F) / StampGammaProcessor.clock_freq);
					command.addCommandData(StampGammaProcessor.TIME, oTime);

					pressao = Integer.parseInt(splitedStr[0]);
					final Float oPressao = new Float(pressao * 0.398 + 98);
					command.addCommandData(StampGammaProcessor.PRESSAO, oPressao);

					ondamic = Integer.parseInt(splitedStr[1]);
					final Float oOndamic = new Float((ondamic) * 5);
					command.addCommandData(StampGammaProcessor.ONDA_MIC, oOndamic);
				}
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
