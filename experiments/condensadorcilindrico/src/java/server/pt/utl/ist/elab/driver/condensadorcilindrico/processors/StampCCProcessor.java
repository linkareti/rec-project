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

        LOGGER.log(Level.FINEST, "Going to process a new Command: " + command.getCommand());

        final String[] splitedCommand = command.getCommand().split("\t");
        LOGGER.log(Level.FINEST, "Splitting it into : " + splitedCommand.length + " parts as follows:");

        int i = 0;
        for (final String part : splitedCommand) {
            LOGGER.log(Level.FINEST, i++ + ": " + part);
        }
        LOGGER.log(Level.FINEST, "starting to interpret it now...");

        if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedCommand != null && splitedCommand.length >= 2) {

            if ("PARAMETROS".equals(splitedCommand[0])) {
                LOGGER.log(Level.FINEST, "setting the parameters for sth");
                // Do sth with this ?! dunno what yet
            } else {

                try {
                    LOGGER.log(Level.FINEST, "PROCESSING the following as the distance: " + splitedCommand[0]);
                    final int distance = Integer.parseInt(splitedCommand[0]);
                    final Float floatDistance = new Float(distance / 1000);
                    command.addCommandData(DISTANCE, floatDistance);
                    LOGGER.log(Level.FINEST, "got us Distance as: " + floatDistance);

                    LOGGER.log(Level.FINEST, "PROCESSING the following as the capacity: " + splitedCommand[1]);
                    final int capacity = Integer.parseInt(splitedCommand[1]);
                    final Double doubleCapacity = new Double(capacity);
                    command.addCommandData(CAPACITY, doubleCapacity);
                    LOGGER.log(Level.FINEST, "got us Capacitance as: " + doubleCapacity);
                } catch (final NumberFormatException e) {
                    LOGGER.log(Level.WARNING, "Couldn't parse the numbers");
                    return false;
                }
            }
            LOGGER.log(Level.FINEST, "setting this as data and returning true");
            command.setData(true);
            return true;
        }
        
        LOGGER.log(Level.WARNING, "Couldn't process this command: " + command.getCommand());
        return false;
    }

    @Override
    public boolean isData() {
        return true;
    }
}
