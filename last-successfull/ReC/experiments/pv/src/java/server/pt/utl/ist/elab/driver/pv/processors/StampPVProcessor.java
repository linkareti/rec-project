/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.pv.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampPVProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "PRESSAO";
	public static final String PRESSAO = "PRESSAO";
	public static final String VOLUME = "VOLUME";

	/** Creates a new instance of StampHelloProcessor */
	public StampPVProcessor() {
		super(StampPVProcessor.COMMAND_IDENTIFIER);
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
		int pressure = 0;
		int volume = 0;
		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampPVProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				pressure = Integer.parseInt(splitedStr[0]);
				final Integer oPressure = new Integer(pressure);
				command.addCommandData(StampPVProcessor.PRESSAO, oPressure);

				volume = Integer.parseInt(splitedStr[2]);
				final Float oVolume = new Float((float) volume / 1000);
				command.addCommandData(StampPVProcessor.VOLUME, oVolume);

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
