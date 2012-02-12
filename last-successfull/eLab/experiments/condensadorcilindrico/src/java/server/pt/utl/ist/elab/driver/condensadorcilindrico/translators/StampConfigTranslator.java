/*
 * StampConfigTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */
package pt.utl.ist.elab.driver.condensadorcilindrico.translators;

import java.util.logging.Level;
import java.util.logging.Logger;
import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * 
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class StampConfigTranslator extends AbstractStampTranslator {

    public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
    public static final String START_POS_STR = "StartPosition";
    public static final String END_POS_STR = "EndPosition";
    public static final String NUMSAMPLES_STR = "Numero de Amostras";
    public static final String CALIBRATE_STR = "Calibrate";
    private static final Logger LOGGER = Logger.getLogger(StampConfigTranslator.class.getName());

    /** Creates a new instance of StampRelayTranslator */
    public StampConfigTranslator() {
        super(StampConfigTranslator.COMMAND_IDENTIFIER);
    }

    @Override
    public boolean translate(final StampCommand command) {
        if (command.getCommandIdentifier() == null
                || !command.getCommandIdentifier().equalsIgnoreCase(StampConfigTranslator.COMMAND_IDENTIFIER)) {
            return false;
        }

        int numPoints = ((Integer) command.getCommandData(NUMSAMPLES_STR)).intValue();
        int startPos = ((Integer) command.getCommandData(START_POS_STR)).intValue();
        int endPos = ((Integer) command.getCommandData(END_POS_STR)).intValue();
        int calib = ((Integer) command.getCommandData(CALIBRATE_STR)).intValue();
        
        startPos = (670 / 228) * startPos;
        endPos = (670 / 228) * endPos;
        

        final String commandStr = command.getCommandIdentifier() + " " + calib + " " + startPos + " " + endPos
                + " " + numPoints;
        
        LOGGER.fine("sending the command: " + commandStr);

        command.setCommand(commandStr);
        return true;
    }
}
