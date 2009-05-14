/*
 * DefaultResource.java Created on 2 de Janeiro de 2004, 16:02
 */

package com.linkare.rec.impl.multicast.security;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the default method for Resources,
 * which area a way of integrating security in the SecurityManager
 * @see com.linkare.rec.impl.multicast.security.SecurityManagerFactory
 * 
 * @author José Pedro Pereira -  Linkare TI
 */
public class DefaultResource implements IResource {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7520963159666907685L;

	private Map<String, String> properties = null;

	private IResource enclosingResource = null;

	private ResourceType resourceType = ResourceType.UNDEFINED;

	/** Creates a new instance of DefaultResource */
	public DefaultResource(IResource enclosingResource, ResourceType resourceType, Map<String,String> properties) {
		setProperties(properties);
		setEnclosingResource(enclosingResource);
		setResourceType(resourceType);
	}

	public DefaultResource(IResource enclosingResource, Map<String,String> properties) {
		this(enclosingResource, deriveChildResourceType(enclosingResource), properties);
	}

	public DefaultResource(IResource enclosingResource) {
		this(enclosingResource, deriveChildResourceType(enclosingResource), new HashMap<String,String>());
	}

	public DefaultResource(Map<String,String> properties) {
		this(null, deriveChildResourceType(null), new HashMap<String,String>(properties));
	}

	public DefaultResource() {
		this(null, deriveChildResourceType(null), new HashMap<String,String>());
	}

	/**
	 * Setter for property properties.
	 * 
	 * @param properties
	 *            New value of property properties.
	 */
	public void setProperties(Map<String,String> properties) {
		this.properties = new HashMap<String,String>(properties);
	}

	/**
	 * Setter for property enclosingResource.
	 * 
	 * @param enclosingResource
	 *            New value of property enclosingResource.
	 */
	public void setEnclosingResource(IResource enclosingResource) {
		this.enclosingResource = enclosingResource;
	}

	/**
	 * Setter for property resourceType.
	 * 
	 * @param resourceType
	 *            New value of property resourceType.
	 */
	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	private static ResourceType deriveChildResourceType(IResource resource) {
		if (resource == null) return ResourceType.MCCONTROLLER;
		return resource.getResourceType().getChildType();
	}

	public DefaultResource createChildResource() {
		return new DefaultResource(this);
	}

	public DefaultResource createChildResource(Map<String,String> properties) {
		return new DefaultResource(this, properties);
	}

	/**
	 * Getter for property properties.
	 * 
	 * @return Value of property properties.
	 */
	public Map<String,String> getProperties() {
		return properties;
	}

	/**
	 * Getter for property enclosingResource.
	 * 
	 * @return Value of property enclosingResource.
	 */
	public IResource getEnclosingResource() {
		return enclosingResource;
	}

	/**
	 * Getter for property resourceType.
	 * 
	 * @return Value of property resourceType.
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	public String toString() {
		
		return getResourceType().getName() + " @ " + getProperties().get(getResourceType().getPropertyKey());
		
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof IResource)) return false;

		IResource otherResource = (IResource) other;

		return otherResource.getResourceType() == getResourceType()
				&& otherResource.getProperties().equals(getProperties());

	}

}
