/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.thomson.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampThomsonProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "val:";

	public static final String CORRENTE = "CORRENTE";
	public static final String TENSAO = "TENSAO";

	/** Creates a new instance of StampHelloProcessor */
	public StampThomsonProcessor() {
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

		int corrente = 0;
		int tensao = 0;

		String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr[0] != null) {
			try {
				corrente = Integer.parseInt(splitedStr[0]) * 2000 / 170;
				Integer oCorrente = new Integer(corrente);
				command.addCommandData(CORRENTE, oCorrente);

				tensao = (Integer.parseInt(splitedStr[1]) * 5 * 40000 / 255) / 27;
				Integer oTensao = new Integer(tensao);
				command.addCommandData(TENSAO, oTensao);

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
