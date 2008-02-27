/*
 * DefaultOperation.java
 *
 * Created on 2 de Janeiro de 2004, 16:03
 */

package com.linkare.rec.impl.multicast.security;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Administrator
 */
public class DefaultOperation implements IOperation
{
    private short operation;
    private HashMap<String, Object> properties;
    
    /** Creates a new instance of DefaultOperation */
    public DefaultOperation()
    {
	setOperation((short)-1);
	setProperties(new HashMap<String, Object>());
    }
    
    /** Creates a new instance of DefaultOperation */
    public DefaultOperation(short operation)
    {
	setOperation(operation);
	setProperties(new HashMap<String, Object>());
    }
    
    
    /** Creates a new instance of DefaultOperation */
    public DefaultOperation(short operation,Map<String, Object> properties)
    {
	setOperation(operation);
	setProperties(properties);
    }
    
    /** Getter for property operation.
     * @return Value of property operation.
     *
     */
    public short getOperation()
    {
	return operation;
    }
    
    /** Setter for property operation.
     * @param operation New value of property operation.
     *
     */
    public void setOperation(short operation)
    {
	this.operation = operation;
    }
    
    /** Getter for property properties.
     * @return Value of property properties.
     *
     */
    public Map<String, Object> getProperties()
    {
	return properties;
    }
    
    /** Setter for property properties.
     * @param properties New value of property properties.
     *
     */
    public void setProperties(Map<String, Object> properties)
    {
	this.properties = new HashMap<String, Object>(properties);
    }
    
    
    
    public String toString()
    {
	switch(operation)
	{
	    case OP_CONFIG:
		return "CONFIG Config="+getProperties().get(PROPKEY_ACQ_CONFIG);
	    case OP_ENUM_HARDWARES:
		return "ENUM_HARDWARES";
	    case OP_ENUM_USERS:
		return "ENUM_USERS";
	    case OP_GET_DATAPRODUCER:
		return "GET_DATAPRODUCER";
	    case OP_GET_DATAPRODUCERSTATE:
		return "GET_DATAPRODUCERSTATE";
	    case OP_GET_HARDWARESTATE:
		return "GET_HARDWARESTATE";
	    case OP_GET_SAMPLES:
		return "GET_SAMPLES";
	    case OP_LIST_HARDWARE:
		return "LIST_HARDWARE";
	    case OP_LIST_USER:
		return "LIST_USER";
	    case OP_LOCK:
		return "LOCK";
	    case OP_RESET:
		return "RESET";
	    case OP_SEND_MESSAGE:
		return "SEND_MESSAGE";
	    case OP_START:
		return "START";
	    case OP_START_OUTPUT:
		return "START_OUTPUT";
	    case OP_STOP:
		return "STOP";
	     case OP_GET_HARDWAREINFO:
		return "GET_HARDWAREINFO HardwareInfo="+getProperties().get(PROPKEY_HARDWAREINFO);
	    default:
		return "UNKNOW OPERATION = NUMBER("+operation+")";
	}
    }
    
    public boolean equals(Object other)
    {
	if(other==null || !(other instanceof IOperation))
	    return false;
	
	IOperation otherOperation=(IOperation) other;
	
	return otherOperation.getOperation()==getOperation() && getProperties().equals(otherOperation.getProperties());
	
    }
    
    
}
