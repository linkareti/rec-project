/*
 * StampPolaroidProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.polaroid.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author bruno
 */
public class StampPolaroidProcessor extends AbstractStampProcessor {
	public static final String COMMAND_IDENTIFIER = "POS=";
	public static final String INTENSIDADE = "Intensidade";
	public static final String ANGULO = "Angulo";

	/** Creates a new instance of StampHelloProcessor */
	public StampPolaroidProcessor() {
		super(StampPolaroidProcessor.COMMAND_IDENTIFIER);
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

		int angulo = 0;
		float intensidade = 0;

		final String[] splitedStr = command.getCommand().split("\t");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampPolaroidProcessor.COMMAND_IDENTIFIER)
				&& splitedStr[0] != null) {
			try {
				angulo = Integer.parseInt(splitedStr[0]);
				final Float oAngulo = Float.valueOf((angulo * 180F) / 1300F);
				command.addCommandData(StampPolaroidProcessor.ANGULO, oAngulo);

				intensidade = Integer.parseInt(splitedStr[1]);
				final Float oIntensidade = Float.valueOf((intensidade * 5F) / 4096F);
				command.addCommandData(StampPolaroidProcessor.INTENSIDADE, oIntensidade);

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
