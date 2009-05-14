/*
 * StampRelayTranslator.java
 *
 * Created on 12 de Novembro de 2002, 15:44
 */

package pt.utl.ist.elab.driver.polaroid.translators;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampTranslator;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampConfigTranslator extends AbstractStampTranslator
{
	
	public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_OUT_STRING;
	
	public static final String POS_INI_POL_STR = "PosIniPolMovel";
        public static final String POS_END_POL_STR = "PosEndPolMovel";
        public static final String POS_FIXO_STR = "PosFixo";
        public static final String LUZ_POL_STR = "LuzPol";
        public static final String CALIB_STR = "Calib";
	public static final String NUMSAMPLES_STR="Numero de Amostras";
	
	/** Creates a new instance of StampRelayTranslator */
	public StampConfigTranslator()
	{
		super(COMMAND_IDENTIFIER);
	}
	
	public boolean translate(StampCommand command)
	{
		if(command.getCommandIdentifier()==null) return false;
		if(!command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER)) return false;
		
                int numsamples = ((Integer)command.getCommandData(NUMSAMPLES_STR)).intValue();
		int posini = (((Integer)command.getCommandData(POS_INI_POL_STR)).intValue() * 155) / 180;
		int posend = (((Integer)command.getCommandData(POS_END_POL_STR)).intValue() * 155) / 180;
                int posfixo = (((Integer)command.getCommandData(POS_FIXO_STR)).intValue() * 155) / 180;
                int luzpol = ((Integer)command.getCommandData(LUZ_POL_STR)).intValue();
                int calib = ((Integer)command.getCommandData(CALIB_STR)).intValue();
								    
		String posinistr = "" + posini;
		while(posinistr.length()<3)
			posinistr = "0" + posinistr;
                
                String posendstr = "" + posend;
		while(posendstr.length()<3)
			posendstr = "0" + posendstr;
                
                String posfixostr = "" + posfixo;
		while(posfixostr.length()<3)
			posfixostr = "0" + posfixostr;
                
		String commandStr = command.getCommandIdentifier() + " " + posfixostr + " " +  posinistr + " " + posendstr + " " + calib + " " + luzpol;
		command.setCommand(commandStr);
		
		return true;
	}
	
}
