/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc.processors;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

public class StampStartProcessor extends AbstractStampProcessor {

	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.START_STRING;

	public StampStartProcessor() {
		super(StampStartProcessor.COMMAND_IDENTIFIER);
	}

	@Override
	public boolean process(final StampCommand command) {
		command.addCommandData(StampStartProcessor.COMMAND_IDENTIFIER, Boolean.TRUE);
		return true;
	}

}
