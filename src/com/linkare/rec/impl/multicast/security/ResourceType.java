/**
 * 
 */
package com.linkare.rec.impl.multicast.security;

/**
 * This enum permits the use of resource types
 * for security Auth... Safe way - Java 5 - Tiger
 * 
 * @author José Pedro Pereira - Linkare TI
 *
 */
public enum ResourceType {
	DATASAMPLES("Data Sample","DataSample.Number",null),
    CHANNEL("Channel","Channel.Name",DATASAMPLES),
    DATAPRODUCER("Data Producer","DataProducer.UniqueID",CHANNEL),
    MCHARDWARE("MultiCast Hardware","MCHardware.UniqueID",DATAPRODUCER),
    MCCONTROLLER("MultiCastController","MCController.Location",MCHARDWARE),
	UNDEFINED("undefined","undefined",MCCONTROLLER)
	;
	
	private String propKey=null;
	private ResourceType childType=null;
	private String name=null;
	private ResourceType(String name,String propKey,ResourceType childType)
	{
		this.name=name;
		this.propKey=propKey;
		this.childType=childType;
	}
	
	public String getName()
	{
		return name;
	}
	public String getPropertyKey()
	{
		return propKey;
	}
	public ResourceType getChildType()
	{
		return childType;
	}
}
