/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */
package pt.utl.ist.elab.driver.condensadorcilindrico.processors;

import java.util.logging.Level;
import java.util.logging.Logger;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 * Parse and process the experiment's response
 * @author Ricardo Espírito Santo - Linkare TI
 */
public class StampCCProcessor extends AbstractStampProcessor {

    public static final String COMMAND_IDENTIFIER = "C";
    public static final String CAPACIDADE_FREQ_STR = "CAPACIDADE_FREQ";
    public static final String CAPACIDADE_FARAD_STR = "CAPACIDADE_FARAD";
    public static final String DISTANCIA_STR = "VOLUME";
    private static final Logger LOGGER = Logger.getLogger(StampCCProcessor.class.getName());

    /** 
     * Creates a new instance of StampHelloProcessor 
     */
    public StampCCProcessor() {
        super(StampCCProcessor.COMMAND_IDENTIFIER);
    }

    @Override
    public boolean process(final StampCommand command) {

        LOGGER.log(Level.FINER, "PROCESSING the following command: " + command);

        float capacidadeFreq = 0.f;
        float capacidadeFarad = 0.f;
        float distancia = 0.f;
        final String[] splitedStr = command.getCommand().split(" ");

        if (command.getCommandIdentifier().equalsIgnoreCase(StampCCProcessor.COMMAND_IDENTIFIER) && splitedStr != null
            && splitedStr.length >= 2 && splitedStr[0] != null && splitedStr[1] != null) {
            try {
                capacidadeFreq = Integer.parseInt(splitedStr[0]);
                final Float oCapacidadeFreq = new Float(capacidadeFreq);
                command.addCommandData(StampCCProcessor.CAPACIDADE_FREQ_STR, oCapacidadeFreq);

                capacidadeFarad = capacidadeFreq * 5 / 50;
                final Float oCapacidadeFarad = new Float(capacidadeFreq);
                command.addCommandData(StampCCProcessor.CAPACIDADE_FARAD_STR, oCapacidadeFarad);

                distancia = Integer.parseInt(splitedStr[1]);
                final Float oDistancia = new Float(distancia / 1000);
                command.addCommandData(StampCCProcessor.DISTANCIA_STR, oDistancia);

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
