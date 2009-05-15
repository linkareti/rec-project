/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc.processors;

import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

public class StampIdProcessor extends AbstractStampProcessor
{
    
    public StampIdProcessor()
    {
	super(pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver.ID_STR);
    }
    
    
    public boolean process(StampCommand command)
    {
	command.addCommandData(getCommandIdentifier(),Boolean.TRUE);
	return true;
    }
    
    /** Getter for property identifierStr.
     * @return Value of property identifierStr.
     */
  /*public String getIdentifierStr()
  {
	  return pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver.ID_STR;
  }*/
    

}
