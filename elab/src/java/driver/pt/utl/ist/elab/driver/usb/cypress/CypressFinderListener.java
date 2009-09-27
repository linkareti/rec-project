/*
 * CypressFinderListener.java
 *
 * Created on 15 de Maio de 2003, 11:56
 */

package pt.utl.ist.elab.driver.usb.cypress;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */

import javax.usb.UsbDevice;

public interface CypressFinderListener extends java.util.EventListener {
	public void cypressFound(UsbDevice device);
}
