/**
 * 
 */
package com.linkare.rec.impl.multicast.security;

/**
 * This enum permits the use of resource types for security Auth... Safe way -
 * Java 5 - Tiger
 * 
 * @author José Pedro Pereira - Linkare TI
 * 
 */
public enum ResourceType {

	/**
	 * This is the data sample in physical terms
	 */
	DATASAMPLES("Data Sample", "DataSample.Number", null),

	/**
	 * This is the sensor in physical terms
	 */
	CHANNEL("Channel", "Channel.Name", DATASAMPLES),

	/**
	 * This is the actual experiment in physical terms
	 */
	DATAPRODUCER("Data Producer", "DataProducer.UniqueID", CHANNEL),

	/**
	 * This is the apparatus in physical terms
	 */
	MCHARDWARE("MultiCast Hardware", "MCHardware.UniqueID", DATAPRODUCER),

	/**
	 * This is the lab in physical terms
	 */
	MCCONTROLLER("MultiCastController", "MCController.Location", MCHARDWARE),

	ROOT("undefined", "undefined", MCCONTROLLER);

	private String propKey = null;
	private ResourceType childType = null;
	private String name = null;

	private ResourceType(String name, String propKey, ResourceType childType) {
		this.name = name;
		this.propKey = propKey;
		this.childType = childType;
	}

	public String getName() {
		return name;
	}

	public String getPropertyKey() {
		return propKey;
	}

	public ResourceType getChildType() {
		return childType;
	}
}
