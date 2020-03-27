/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.g.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampGProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "HEIGHT";
	public static final String ALTURA = "ALTURA";

	/** Creates a new instance of StampHelloProcessor */
	public StampGProcessor() {
		super(StampGProcessor.COMMAND_IDENTIFIER);
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

		int height = 0;

		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampGProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				height = Integer.parseInt(splitedStr[0]);
				final Float oHeight = Float.valueOf(0.000346F * height);
				command.addCommandData(StampGProcessor.ALTURA, oHeight);

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
