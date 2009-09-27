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
		super(COMMAND_IDENTIFIER);
	}

	/**
	 * This method makes the Processor process the command passed in...
	 * 
	 * @param command the command that this processor will have to process
	 * @return boolean - wether the processing was successfull
	 * 
	 */
	public boolean process(StampCommand command) {
		int pressure = 0;
		int volume = 0;
		String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0] != null) {
			try {
				pressure = Integer.parseInt(splitedStr[0]);
				Integer oPressure = new Integer(pressure);
				command.addCommandData(PRESSAO, oPressure);

				volume = Integer.parseInt(splitedStr[2]);
				Float oVolume = new Float((float) volume / 1000);
				command.addCommandData(VOLUME, oVolume);

				command.setData(true);

				return true;

			} catch (NumberFormatException e) {
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	public boolean isData() {
		return true;
	}
}
