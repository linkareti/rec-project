package com.linkare.rec.impl.multicast.security;

import java.util.Map;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public interface IOperation extends java.io.Serializable {

	public static final String PROPKEY_ACQ_CONFIG = "MCHardware.AcquisitionConfig";
	public static final String PROPKEY_USERID_OTHER = "MC.UserID.Other";
	public static final String PROPKEY_HARDWARESTATE = "MCHardware.HardwareState";
	public static final String PROPKEY_REMOTE_DATAPRODUCER = "MCHardware.RemoteDataProducer";
	public static final String PROPKEY_HARDWARE_ID = "MCHardware.UniqueID";
	public static final String PROPKEY_HARDWAREINFO = "MCHardware.HardwareInfo";

	OperationType getOperation();

	Map<String, Object> getProperties();
	
}
