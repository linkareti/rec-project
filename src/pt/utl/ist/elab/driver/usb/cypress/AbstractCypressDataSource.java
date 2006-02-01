/*
 * AbstractCypressDataSource.java
 *
 * Created on 15 de Maio de 2003, 18:03
 */

package pt.utl.ist.elab.driver.usb.cypress;

import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.threading.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.usb.cypress.transproc.*;
import pt.utl.ist.elab.driver.usb.cypress.transproc.processors.*;
/**
 *
 * @author  jp
 */
public abstract class AbstractCypressDataSource extends BaseDataSource
{
	public abstract void processDataCommand(CypressCommand cmd);
}
