/*
 * DefaultResource.java Created on 2 de Janeiro de 2004, 16:02
 */

package com.linkare.rec.impl.multicast.security;

import java.util.HashMap;
import java.util.Map;

/**
 * This class implements the default method for Resources, which area a way of
 * integrating security in the SecurityManager
 * 
 * @see com.linkare.rec.impl.multicast.security.SecurityManagerFactory
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultResource implements IResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7520963159666907685L;

	private Map<String, String> properties = null;

	private IResource enclosingResource = null;

	private ResourceType resourceType = ResourceType.ROOT;

	/** Creates a new instance of DefaultResource 
	 * @param enclosingResource 
	 * @param resourceType 
	 * @param properties */
	public DefaultResource(final IResource enclosingResource, final ResourceType resourceType,
			final Map<String, String> properties) {
		setProperties(properties);
		setEnclosingResource(enclosingResource);
		setResourceType(resourceType);
	}

	public DefaultResource(final IResource enclosingResource, final Map<String, String> properties) {
		this(enclosingResource, DefaultResource.deriveChildResourceType(enclosingResource), properties);
	}

	public DefaultResource(final IResource enclosingResource) {
		this(enclosingResource, DefaultResource.deriveChildResourceType(enclosingResource),
				new HashMap<String, String>());
	}

	public DefaultResource(final Map<String, String> properties) {
		this(null, DefaultResource.deriveChildResourceType(null), new HashMap<String, String>(properties));
	}

	public DefaultResource() {
		this(null, DefaultResource.deriveChildResourceType(null), new HashMap<String, String>());
	}

	/**
	 * Setter for property properties.
	 * 
	 * @param properties New value of property properties.
	 */
	public void setProperties(final Map<String, String> properties) {
		this.properties = new HashMap<String, String>(properties);
	}

	/**
	 * Setter for property enclosingResource.
	 * 
	 * @param enclosingResource New value of property enclosingResource.
	 */
	public void setEnclosingResource(final IResource enclosingResource) {
		this.enclosingResource = enclosingResource;
	}

	/**
	 * Setter for property resourceType.
	 * 
	 * @param resourceType New value of property resourceType.
	 */
	public void setResourceType(final ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	private static ResourceType deriveChildResourceType(final IResource resource) {
		if (resource == null) {
			return ResourceType.MCCONTROLLER;
		}
		return resource.getResourceType().getChildType();
	}

	public DefaultResource createChildResource() {
		return new DefaultResource(this);
	}

	public DefaultResource createChildResource(final Map<String, String> properties) {
		return new DefaultResource(this, properties);
	}

	/**
	 * Getter for property properties.
	 * 
	 * @return Value of property properties.
	 */
	@Override
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * Getter for property enclosingResource.
	 * 
	 * @return Value of property enclosingResource.
	 */
	@Override
	public IResource getEnclosingResource() {
		return enclosingResource;
	}

	/**
	 * Getter for property resourceType.
	 * 
	 * @return Value of property resourceType.
	 */
	@Override
	public ResourceType getResourceType() {
		return resourceType;
	}

	@Override
	public String toString() {

		return getResourceType().getName() + " @ " + getProperties().get(getResourceType().getPropertyKey());

	}

	@Override
	public boolean equals(final Object other) {
		if (other == null || !(other instanceof IResource)) {
			return false;
		}

		final IResource otherResource = (IResource) other;

		return otherResource.getResourceType() == getResourceType()
				&& otherResource.getProperties().equals(getProperties());

	}

}
