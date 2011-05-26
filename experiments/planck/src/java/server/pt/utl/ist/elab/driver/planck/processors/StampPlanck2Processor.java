/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.planck.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampPlanck2Processor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "V1:";
	public static final String FOTOCEL = "fotocel";

	/** Creates a new instance of StampHelloProcessor */
	public StampPlanck2Processor() {
		super(StampPlanck2Processor.COMMAND_IDENTIFIER);
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
		int potential = 0;

		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampPlanck2Processor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				potential = Integer.parseInt(splitedStr[0]);
				final Float oPotential = new Float(potential * 5f / 4096f);

				command.addCommandData(StampPlanck2Processor.FOTOCEL, oPotential);

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
