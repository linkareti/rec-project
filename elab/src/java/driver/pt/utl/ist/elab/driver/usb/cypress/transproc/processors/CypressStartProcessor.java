/*
 * CypressHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc.processors;

import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver;
import pt.utl.ist.elab.driver.usb.cypress.transproc.AbstractCypressProcessor;
import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

public class CypressStartProcessor extends AbstractCypressProcessor
{
    
    public static final String COMMAND_IDENTIFIER = AbstractCypressDriver.START_STRING;
    
    public CypressStartProcessor()
    {
        super(COMMAND_IDENTIFIER);
    }
    
    public boolean process(CypressCommand command)
    {
        command.addCommandData(COMMAND_IDENTIFIER,Boolean.TRUE);
        return true;
    }
    
}
