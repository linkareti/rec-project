/*
 * LabConnector.java
 *
 * Created on 13 de Maio de 2003, 18:06
 */

package com.linkare.rec.impl.client.lab;

/**
 *
 * @author  jp
 */
public interface LabConnector
{
	//Version 7.0 change: removed method setUser and setPassword and changed it to setUserInfo... wich is much more generic
	public void setUserInfo (com.linkare.rec.acquisition.UserInfo userInfo);
	public void connect(String MCobjAddress);
	public void disconnect();	
	
	/** Registers LabConnectorListener to receive events.
	 * @param listener The listener to register.
	 */
	public void addLabConnectorListener(LabConnectorListener listener);
	
	/** Removes LabConnectorListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public void removeLabConnectorListener(LabConnectorListener listener);
	
}
