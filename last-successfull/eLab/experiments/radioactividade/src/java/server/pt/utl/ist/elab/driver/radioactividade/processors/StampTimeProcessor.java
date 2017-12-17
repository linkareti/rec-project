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
public class StampTimeProcessor extends AbstractStampProcessor {

	public static final String COMMAND_IDENTIFIER = "HISTOGRAM";
	public static final String TIMER = "TIMER";
	public static final String HEIGHT = "HEIGHT";
	public static final String HITS = "HITS";

	/** Creates a new instance of StampHelloProcessor */
	public StampTimeProcessor() {
		super(StampTimeProcessor.COMMAND_IDENTIFIER);
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
		int time = 0;
		int height = 0;

		final String[] splitedStr = command.getCommand().split(" ");

		if (command.getCommandIdentifier().equalsIgnoreCase(StampTimeProcessor.COMMAND_IDENTIFIER)) {
			if (splitedStr[0] != null && splitedStr[1] != null && splitedStr[2] != null) {
				try {
					countedHits = Integer.parseInt(splitedStr[0]);
					time = Integer.parseInt(splitedStr[1]);
					height = Integer.parseInt(splitedStr[2]);

					final Integer nHits = new Integer(countedHits);
					final Integer Time = new Integer(time);
					final Integer Height = new Integer(height);

					command.addCommandData(StampTimeProcessor.HITS, nHits);
					command.addCommandData(StampTimeProcessor.TIMER, Time);
					command.addCommandData(StampTimeProcessor.HEIGHT, Height);
					command.setData(true);

					return true;
				} catch (final NumberFormatException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return false;
	}

	@Override
	public boolean isData() {
		return true;
	}
}
