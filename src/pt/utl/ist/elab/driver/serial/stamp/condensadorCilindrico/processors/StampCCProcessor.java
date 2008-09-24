/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.condensadorCilindrico.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.*;
import pt.utl.ist.elab.driver.serial.stamp.*;

/**
 *
 * @author  bruno
 */
public class StampCCProcessor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "CC";
    public static final String CAPACIDADE_FREQ_STR="CAPACIDADE_FREQ";
    public static final String CAPACIDADE_FARAD_STR="CAPACIDADE_FARAD";
    public static final String DISTANCIA_STR="VOLUME";
    /** Creates a new instance of StampHelloProcessor */
    public StampCCProcessor()
    {
	super(COMMAND_IDENTIFIER);
    }
    
    /** This method makes the Processor process the command passed in...
     *
     * @param command the command that this processor will have to process
     * @return boolean - wether the processing was successfull
     *
     */
    public boolean process(StampCommand command)
    {
	float capacidadeFreq = 0.f;
	float capacidadeFarad = 0.f;
	float distancia = 0.f;
	String[] splitedStr = command.getCommand().split(" ");
	
	if(command.getCommandIdentifier().equalsIgnoreCase(COMMAND_IDENTIFIER) && splitedStr!=null && splitedStr.length>=2 && splitedStr[0]!=null && splitedStr[1]!=null)
	{
	    try
	    {
		capacidadeFreq= Integer.parseInt(splitedStr[0]);
		Float oCapacidadeFreq= new Float(capacidadeFreq);
		command.addCommandData(CAPACIDADE_FREQ_STR,oCapacidadeFreq);
		
                capacidadeFarad=capacidadeFreq*5/50;
                Float oCapacidadeFarad= new Float(capacidadeFreq);
		command.addCommandData(CAPACIDADE_FARAD_STR,oCapacidadeFarad);
		
		distancia= Integer.parseInt(splitedStr[2]);
		Float oDistancia= new Float((float)distancia/1000);
		command.addCommandData(DISTANCIA_STR,oDistancia);
		
		command.setData(true);
		
		return true;
		
	    }
	    catch(NumberFormatException e)
	    {
		e.printStackTrace();
		return false;
	    }
	}
	
	return false;
    }
    
    public boolean isData()
    {
	return true;
    }
}
