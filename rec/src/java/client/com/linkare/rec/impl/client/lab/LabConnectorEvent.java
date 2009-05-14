/*
 * LabConnectorEvent.java
 *
 * Created on July 29, 2004, 4:02 PM
 */

package com.linkare.rec.impl.client.lab;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class LabConnectorEvent extends java.util.EventObject
{
    public final static int STATUS_CONNECTING=0;
    public final static int STATUS_CONNECTED=1;
    public final static int STATUS_DISCONNECTING=2;
    public final static int STATUS_DISCONNECTED=3;
    public final static int STATUS_UNREACHABLE=4;
    public final static int STATUS_NOT_AUTHORIZED=5;
    public final static int STATUS_MAX_USERS=6;
    public final static int STATUS_NOT_REGISTERED=7;
    
    private int statusCode = 0;
	
    public LabConnectorEvent(Object source, int statusCode)
    {
	super(source);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode()
    {
	return statusCode;
    }
}
