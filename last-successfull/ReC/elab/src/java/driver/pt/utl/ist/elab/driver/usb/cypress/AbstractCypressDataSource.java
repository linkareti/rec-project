/*
 * AbstractCypressDataSource.java
 *
 * Created on 15 de Maio de 2003, 18:03
 */

package pt.utl.ist.elab.driver.usb.cypress;

import pt.utl.ist.elab.driver.usb.cypress.transproc.CypressCommand;

import com.linkare.rec.impl.driver.BaseDataSource;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class AbstractCypressDataSource extends BaseDataSource {
	public abstract void processDataCommand(CypressCommand cmd);
}
