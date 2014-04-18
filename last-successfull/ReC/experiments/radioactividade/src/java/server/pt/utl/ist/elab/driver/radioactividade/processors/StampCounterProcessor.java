/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.radioactividade.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampCounterProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "COUNTER";
	public static final String COUNTER = "Contagens";

	/** Creates a new instance of StampHelloProcessor */
	public StampCounterProcessor() {
		super(StampCounterProcessor.COMMAND_IDENTIFIER);
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
		int countedHits = 0;
		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampCounterProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				countedHits = Integer.parseInt(splitedStr[0]);
				final Integer nHits = new Integer(countedHits);
				command.addCommandData(StampCounterProcessor.COUNTER, nHits);
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
