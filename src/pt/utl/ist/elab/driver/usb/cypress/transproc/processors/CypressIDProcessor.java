/*
 * StampHelloProcessor.java
 *
 * Created on 12 de Novembro de 2002, 16:19
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc.processors;

import pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver;
import pt.utl.ist.elab.driver.usb.cypress.transproc.*;

public class CypressIDProcessor extends AbstractCypressProcessor
{
    
    public CypressIDProcessor()
    {
	super(pt.utl.ist.elab.driver.usb.cypress.AbstractCypressDriver.ID_STR);
    }
    
    
    public boolean process(CypressCommand command)
    {
	command.addCommandData(getCommandIdentifier(),Boolean.TRUE);
	return true;
    }
}
