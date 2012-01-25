package pt.utl.ist.elab.driver.condensadorcilindrico.processors;

import java.util.logging.Level;
import java.util.logging.Logger;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * Parse and process the experiment's response
 * 
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class StampCCProcessor extends AbstractStampProcessor {

    public static final String COMMAND_IDENTIFIER = "C";
    public static final String CAPACITY = "CAPACITY";
    public static final String DISTANCE = "DISTANCE";
    private static final Logger LOGGER = Logger.getLogger(StampCCProcessor.class.getName());

    /** 
     * Creates a new instance of StampCCProcessor 
     */
    public StampCCProcessor() {
        super(COMMAND_IDENTIFIER);
    }

    @Override
    public boolean process(final StampCommand command) {

        final String[] splitedCommand = command.getCommand().split(" ");

        if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedCommand != null
            && splitedCommand.length >= 2 && splitedCommand[0] != null && splitedCommand[1] != null) {
            try {
                
                final int distance = Integer.parseInt(splitedCommand[1]);
                final Float floatDistance = new Float(distance / 1000);
                command.addCommandData(DISTANCE, floatDistance);
                LOGGER.log(Level.FINEST, "PROCESSING the command got us Distance as: " + floatDistance);
                
                final int capacity = Integer.parseInt(splitedCommand[2]);
                final Float floatCapacity = new Float(capacity);
                command.addCommandData(CAPACITY, floatCapacity);
                LOGGER.log(Level.FINEST, "PROCESSING the command got us Capacitance as: " + floatCapacity);

                command.setData(true);

                return true;
            } catch (final NumberFormatException e) {
                LOGGER.log(Level.WARNING, "Couldn't process this command: [" + splitedCommand + "]");
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
