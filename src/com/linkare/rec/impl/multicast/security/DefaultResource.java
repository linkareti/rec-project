/*
 * DefaultResource.java
 *
 * Created on 2 de Janeiro de 2004, 16:02
 */

package com.linkare.rec.impl.multicast.security;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Administrator
 */
public class DefaultResource implements IResource
{
    private HashMap properties=null;
    private IResource enclosingResource=null;
    private short resourceType=-1;
    /** Creates a new instance of DefaultResource */
    public DefaultResource(IResource enclosingResource, short resourceType, Map properties )
    {
	setProperties(properties);
	setEnclosingResource(enclosingResource);
	setResourceType(resourceType);
    }
    
    public DefaultResource(IResource enclosingResource, Map properties )
    {
	this(enclosingResource, deriveChildResourceType(enclosingResource), properties);
    }
    
    public DefaultResource(IResource enclosingResource)
    {
	this(enclosingResource, deriveChildResourceType(enclosingResource), new HashMap());
    }
    
    public DefaultResource(Map properties)
    {
	this(null, deriveChildResourceType(null), new HashMap(properties));
    }
    
    public DefaultResource()
    {
	this(null, deriveChildResourceType(null), new HashMap());
    }
    /** Setter for property properties.
     * @param properties New value of property properties.
     *
     */
    public void setProperties(Map properties)
    {
	this.properties = new HashMap(properties);
    }
    
    /** Setter for property enclosingResource.
     * @param enclosingResource New value of property enclosingResource.
     *
     */
    public void setEnclosingResource(com.linkare.rec.impl.multicast.security.IResource enclosingResource)
    {
	this.enclosingResource = enclosingResource;
    }
    
    /** Setter for property resourceType.
     * @param resourceType New value of property resourceType.
     *
     */
    public void setResourceType(short resourceType)
    {
	this.resourceType = resourceType;
    }
    
    
    
    
    private static short deriveChildResourceType(IResource resource)
    {
	if(resource==null) return RES_MCCONTROLLER;
	switch(resource.getResourceType())
	{
	    case RES_MCCONTROLLER:
		return RES_MCHARDWARE;
	    case RES_MCHARDWARE:
		return RES_DATAPRODUCER;
	    case RES_DATAPRODUCER:
		return RES_CHANNEL;
	    case RES_CHANNEL:
		return RES_DATASAMPLES;
	    case RES_DATASAMPLES:
		return RES_DATASAMPLES;
	    default:
		return -1;
	}
    }
    
    public DefaultResource createChildResource()
    {
	return new DefaultResource(this);
    }
    
    public DefaultResource createChildResource(Map properties)
    {
	return new DefaultResource(this,properties);
    }
    
    /** Getter for property properties.
     * @return Value of property properties.
     *
     */
    public Map getProperties()
    {
	return properties;
    }
    
    
    /** Getter for property enclosingResource.
     * @return Value of property enclosingResource.
     *
     */
    public IResource getEnclosingResource()
    {
	return enclosingResource;
    }
    
    /** Getter for property resourceType.
     * @return Value of property resourceType.
     *
     */
    public short getResourceType()
    {
	return resourceType;
    }
    
    
    
     public String toString()
    {
	switch(getResourceType())
	{
	    case RES_MCCONTROLLER:
		return "MULTICASTCONTROLLER @ " + getProperties().get(PROPKEY_MCCONTROLLER_LOCATION);
	    case RES_MCHARDWARE:
		return "MULTICASTHARDWARE ID=" + getProperties().get(PROPKEY_MCHARDWARE_ID);
	    case RES_DATAPRODUCER:
		return "DATAPRODUCER ID="+getProperties().get(PROPKEY_DATAPRODUCER_ID);
	    case RES_CHANNEL:
		return "CHANNEL Name="+getProperties().get(PROPKEY_CHANNEL_NAME);
	    case RES_DATASAMPLES:
		return "DATASAMPLES N="+getProperties().get(PROPKEY_DATASAMPLE_NR);
	    default:
		return "UNKNOW RESOURCE = NUMBER("+getResourceType()+")";
	}
    }
    
    public boolean equals(Object other)
    {
	if(other==null || !(other instanceof IResource))
	    return false;
	
	IResource otherResource=(IResource) other;
	
	
	return otherResource.getResourceType()==getResourceType() && otherResource.getProperties().equals(getProperties());
	
    }
    
    
    
}
