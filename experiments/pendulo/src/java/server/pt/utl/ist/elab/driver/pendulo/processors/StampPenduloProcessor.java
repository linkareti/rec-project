/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.pendulo.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampPenduloProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "D";
	public static final String ANGLE_VEL = "Angular Speed";

	/** Creates a new instance of StampHelloProcessor */
	public StampPenduloProcessor() {
		super(StampPenduloProcessor.COMMAND_IDENTIFIER);
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
		float angle_vel = 0;
		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampPenduloProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				angle_vel = Float.parseFloat(splitedStr[0]);
				final Float oangle_vel = new Float(5. * (angle_vel - 2048.) / 4096.);
				command.addCommandData(StampPenduloProcessor.ANGLE_VEL, oangle_vel);

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
