/*
 * LabConnector.java
 *
 * Created on 13 de Maio de 2003, 18:06
 */

package com.linkare.rec.impl.client.apparatus;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
/**
 *
 * @author  jp
 */
public interface ApparatusConnector
{	
	//Version 7.0 change: removed method setUser and setPassword and changed it to setUserInfo... wich is much more generic
	public void setUserInfo (com.linkare.rec.acquisition.UserInfo userInfo);
    
	public void connect();
	public void lock();
	public void configure(HardwareAcquisitionConfig config);
	public void start();
	public void stop();
	public void reset();
	public void disconnect();	
	
	/** Registers ApparatusConnectorListener to receive events.
	 * @param listener The listener to register.
	 */
	public void addApparatusConnectorListener(ApparatusConnectorListener listener);
	
	/** Removes ApparatusConnectorListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public void removeApparatusConnectorListener(ApparatusConnectorListener listener);
	
}
