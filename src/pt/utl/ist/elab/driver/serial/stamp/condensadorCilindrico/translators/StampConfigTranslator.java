/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */
package pt.utl.ist.elab.driver.serial.stamp.condensadorCilindrico.translators;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;

/**
 *
 * @author  bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator {

    public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
    public static final String START_POS_STR = "StartPosition";
    public static final String END_POS_STR = "EndPosition";
    public static final String NUMSAMPLES_STR = "Numero de Amostras";
    public static final String FREQ_INTERVAL_STR = "Intervalo entre amostras";
    public static final String CALIBRATE_STR = "Calibrate";

    /** Creates a new instance of StampRelayTranslator */
    public StampConfigTranslator() {
        super(COMMAND_IDENTIFIER);
    }

    public boolean translate(StampCommand command) {
        if (command.getCommandIdentifier() == null) {
            return false;
        }
        if (!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER)) {
            return false;
        }
        int numsamples = ((Integer) command.getCommandData(NUMSAMPLES_STR)).intValue();
        float startPosf = ((Float) command.getCommandData(START_POS_STR)).floatValue();
        startPosf = startPosf * 670/24;
        float endPosf = ((Float) command.getCommandData(END_POS_STR)).floatValue();
        endPosf = endPosf * 670/24;
        int endPos = (int) Math.floor(endPosf);
        int startPos = (int) Math.floor(startPosf);

        int numPoints = ((Integer) command.getCommandData(FREQ_INTERVAL_STR)).intValue();
        int calib=((Integer) command.getCommandData(CALIBRATE_STR)).intValue();
        

        if (startPos > 670 || startPos < 0) {
            System.out.println("startPos is wrong..." + startPos);
            return false;
        }

        if (endPos > 670 || endPos < 0) {
            System.out.println("endPos is wrong..." + endPos);
            return false;
        }

        if (endPos == startPos) {
            System.out.println("startPos is equal to endPos...");
            return false;
        }

        if (numsamples > 10000) {
            System.out.println("numsamples>10000");
            return false;
        }

        if (numsamples < 1) {
            System.out.println("numsamples<1");
            return false;
        }

        if (numPoints < 1 || numPoints > 670) {
            System.out.println("numPoints is wrong..." + numPoints);
            return false;
        }
        
        if(calib!=0 && calib!=1)
        {
            System.out.println("Calib is wrong..."+calib);
        }

        String endPosStr = "" + endPos;
        while (endPosStr.length() < 2) {
            endPosStr = "0" + endPosStr;
        }
        String startPosStr = "" + startPos;
        while (startPosStr.length() < 2) {
            startPosStr = "0" + startPosStr;
        }
        String numSamplesStr = "" + numsamples;
        while (numSamplesStr.length() < 3) {
            numSamplesStr = "0" + numSamplesStr;
        }
        String numPointsStr = "" + numPoints ;
        while (numPointsStr.length() < 4) {
            numPointsStr = "0" + numPointsStr;
        }
        String commandStr = command.getCommandIdentifier() + " " + calib + " " + startPosStr + " " + endPosStr + " " + numPointsStr+" "+numSamplesStr;
        command.setCommand(commandStr);

        return true;
    }
}
