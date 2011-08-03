/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.statsound.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampStatSoundProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "POS";

	/** Creates a new instance of StampHelloProcessor */
	public StampStatSoundProcessor() {
		super(StampStatSoundProcessor.COMMAND_IDENTIFIER);
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
		int pos = 0;
		final String[] splitedStr = command.getCommand().split(" ");
		if (command.getCommandIdentifier().equalsIgnoreCase(StampStatSoundProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				pos = Integer.parseInt(splitedStr[0]);
				command.addCommandData(StampStatSoundProcessor.COMMAND_IDENTIFIER, pos);
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
