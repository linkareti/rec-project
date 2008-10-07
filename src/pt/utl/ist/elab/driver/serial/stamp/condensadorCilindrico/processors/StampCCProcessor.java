/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */
package pt.utl.ist.elab.driver.serial.stamp.condensadorCilindrico.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;

/**
 *
 * @author  bruno
 */
public class StampCCProcessor extends AbstractStampProcessor {

    public static final String COMMAND_IDENTIFIER = "C";
    public static final String CAPACIDADE_FREQ_STR = "CAPACIDADE_FREQ";
    public static final String CAPACIDADE_FARAD_STR = "CAPACIDADE_FARAD";
    public static final String DISTANCIA_STR = "VOLUME";
    public static int clock_freq = 1;
    public static int measures = 1;

    /** Creates a new instance of StampHelloProcessor */
    public StampCCProcessor() {
        super(COMMAND_IDENTIFIER);
    }

    /** This method makes the Processor process the command passed in...
     *
     * @param command the command that this processor will have to process
     * @return boolean - wether the processing was successfull
     *
     */
    public boolean process(StampCommand command) {
        float capacidadeFreq = 0.f;
        float capacidadeFarad = 0.f;
        float distancia = 0.f;
        String[] splitedStr = command.getCommand().split("\t");

        if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr != null && splitedStr.length >= 2 && splitedStr[0] != null && splitedStr[1] != null) {
            try {
                if ("PARAMETROS".equals(splitedStr[0])) {
                    clock_freq = Integer.parseInt(splitedStr[1]);
                    measures = Integer.parseInt(splitedStr[6]);

                } else {
                    capacidadeFreq = Float.parseFloat(splitedStr[0]);
                    Float oCapacidadeFreq = new Float(measures * clock_freq * 0.1 / capacidadeFreq);
                    command.addCommandData(CAPACIDADE_FREQ_STR, oCapacidadeFreq);

                    capacidadeFarad = 1000.f / capacidadeFreq;
                    Float oCapacidadeFarad = new Float(capacidadeFreq);
                    command.addCommandData(CAPACIDADE_FARAD_STR, oCapacidadeFarad);

                    distancia = Float.parseFloat(splitedStr[1]);
                    Float oDistancia = new Float(distancia / 670.f * 24.f);
                    command.addCommandData(DISTANCIA_STR, oDistancia);
                }
                command.setData(true);

                return true;

            } catch (NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public boolean isData() {
        return true;
    }
}
