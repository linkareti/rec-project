package pt.utl.ist.elab.driver.condensadorcilindrico.processors;

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

        if (command == null || command.getCommand() == null || command.getCommand().isEmpty()) {
            LOGGER.finest("Ok got a null command or a command without any command in it!");
            command.setData(false);
            return command.isData();
        }
        
        LOGGER.finest("Going to process a new Command: " + command.getCommand());

        final String[] splitedCommand = command.getCommand().split("\t");
        LOGGER.finest("Splitting it into : " + splitedCommand.length + " parts as follows:");

        int i = 0;
        for (final String part : splitedCommand) {
            LOGGER.finest(i++ + ": " + part);
        }
        LOGGER.finest("starting to interpret it now...");

        if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedCommand != null && splitedCommand.length >= 2) {

            if ("PARAMETROS".equals(splitedCommand[0])) {
                LOGGER.finest("setting the parameters for sth");
                // Do sth with this ?! dunno what yet
            } else {

                try {
                    LOGGER.finest("PROCESSING the following as the distance: " + splitedCommand[0]);
                    final int distance = Integer.parseInt(splitedCommand[0]);
                    final Float floatDistance = new Float(distance / 1000);
                    command.addCommandData(DISTANCE, floatDistance);
                    LOGGER.finest("got us Distance as: " + floatDistance);

                    LOGGER.finest("PROCESSING the following as the capacity: " + splitedCommand[1]);
                    final int capacity = Integer.parseInt(splitedCommand[1]);
                    final Double doubleCapacity = new Double(capacity);
                    command.addCommandData(CAPACITY, doubleCapacity);
                    LOGGER.finest("got us Capacitance as: " + doubleCapacity);
                    command.setData(true);
                } catch (final NumberFormatException e) {
                    LOGGER.warning("Couldn't parse the numbers");
                    command.setData(false);
                }
            }
            LOGGER.finest("setting this command as " + (command.isData() ? "" : "NOT ") + "data");
            return command.isData();
        }
        
        LOGGER.warning("Couldn't process this command: " + command.getCommand());
        command.setData(false);
        return command.isData();
    }
}
