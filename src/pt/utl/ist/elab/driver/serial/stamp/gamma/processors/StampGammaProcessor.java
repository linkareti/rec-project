/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */
package pt.utl.ist.elab.driver.serial.stamp.gamma.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;

/**
 *
 * @author  bruno
 */
public class StampGammaProcessor extends AbstractStampProcessor {

    public static final String COMMAND_IDENTIFIER = "G";
    public static final String ONDA_MIC = "OndaMic";
    public static final String PRESSAO = "Pressao";
    public static final String TIME = "time";
    public static int period = 1;
    public static int clock_freq = 1;

    /** Creates a new instance of StampHelloProcessor */
    public StampGammaProcessor() {
        super(COMMAND_IDENTIFIER);
    }

    /** This method makes the Processor process the command passed in...
     *
     * @param command the command that this processor will have to process
     * @return boolean - wether the processing was successfull
     *
     */
    public boolean process(StampCommand command) {

        int ondamic = 0;
        int pressao = 0;
        int time = 0;

        String[] splitedStr = command.getCommand().split("\t");

        if (command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && 
                splitedStr != null && 
                splitedStr.length >= 2 && 
                splitedStr[0] != null && 
                splitedStr[1] != null) {
            try {
                if ("PARAMETROS".equals(splitedStr[0])) {
                    clock_freq = Integer.parseInt(splitedStr[1]);
                    period = Integer.parseInt(splitedStr[3]);

                } else {
                    Float oTime = new Float((period * 1F) / clock_freq);
                    command.addCommandData(TIME, oTime);

                    pressao = Integer.parseInt(splitedStr[0]);
                    Float oPressao = new Float(pressao * 0.398 + 98);
                    command.addCommandData(PRESSAO, oPressao);

                    ondamic = Integer.parseInt(splitedStr[1]);
                    Float oOndamic = new Float((ondamic) * 5);
                    command.addCommandData(ONDA_MIC, oOndamic);
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
