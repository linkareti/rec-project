/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

//TODO comentar

package pt.utl.ist.elab.driver.condensador.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampCondensadorProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "FREQUENCIA";
	public static final String FREQUENCIA = "FREQUENCIA";
	public static final String POSICAO = "POSICAO";

	/** Creates a new instance of StampHelloProcessor */
	public StampCondensadorProcessor() {
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
		int frequency = 0;
		int position = 0;
		String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0] != null) {
			try {
				frequency = Integer.parseInt(splitedStr[0]);
				Integer oFrequency = new Integer(frequency);
				command.addCommandData(FREQUENCIA, oFrequency);

				position = Integer.parseInt(splitedStr[2]);
				Float oPosition = new Float((float) position / 1000);
				command.addCommandData(POSICAO, oPosition);

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
