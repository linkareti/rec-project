/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.planck.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

/**
 *
 * @author  bruno
 */
public class StampPlanck3Processor extends AbstractStampProcessor
{
    public static final String COMMAND_IDENTIFIER = "null";

    /** Creates a new instance of StampHelloProcessor */
    public StampPlanck3Processor()
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
        command.addCommandData(null,null);
        command.setData(true);
        return true;				
    }
    
    public boolean isData()
    {
	return true;
    }    
}
