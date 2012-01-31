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
        
        boolean messageProcessed = false;

        if (command == null || command.getCommand() == null || command.getCommand().isEmpty()) {
            LOGGER.finest("Ok got a null command or a command without any command in it!");
            return messageProcessed;
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
                final Integer param1 = Integer.valueOf(splitedCommand[1]); // 2303
                final Integer param2 = Integer.valueOf(splitedCommand[2]); // 0
                final Integer param3 = Integer.valueOf(splitedCommand[3]); // 100
                final Integer param4 = Integer.valueOf(splitedCommand[4]); // 300
                messageProcessed = true;
                return messageProcessed;
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
                    messageProcessed = true;
                } catch (final NumberFormatException e) {
                    LOGGER.warning("Couldn't parse the numbers");
                    messageProcessed = false;
                }
            }
            LOGGER.finest("setting this command as " + (messageProcessed ? "" : "NOT ") + "data");
            return messageProcessed;
        }
        
        LOGGER.warning("Couldn't process this command: " + command.getCommand());
        messageProcessed = false;
        return messageProcessed;
    }
    
    @Override
	public boolean isData() {
		return true;
	}
    
}
