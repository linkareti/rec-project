/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc.processors;

import pt.utl.ist.elab.driver.serial.stamp.AbstractStampDriver;
import pt.utl.ist.elab.driver.serial.stamp.transproc.AbstractStampProcessor;
import pt.utl.ist.elab.driver.serial.stamp.transproc.StampCommand;

public class StampConfiguredProcessor extends AbstractStampProcessor
{
  
  public static final String COMMAND_IDENTIFIER = AbstractStampDriver.CONFIG_ACCEPTED_STRING;
  
  public StampConfiguredProcessor()
  {
    super(COMMAND_IDENTIFIER);
  }

  public boolean process(StampCommand command)
  {
    command.addCommandData(COMMAND_IDENTIFIER,Boolean.TRUE);
    return true;
  }
  
}
