package com.linkare.rec.repository;

import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;

public final class DataProducerConfig implements IDLEntity {
	private HardwareAcquisitionConfig hardwareConfig = null;
	private String ID = null;
	private String hardwareUniqueID = null;

	public DataProducerConfig() {
	} // ctor

	public DataProducerConfig(HardwareAcquisitionConfig hardwareConfig, String ID, String hardwareUniqueID) {
		setID(ID);
		setHardwareUniqueID(hardwareUniqueID);
		setHardwareConfig(hardwareConfig);
	}

	/**
	 * Getter for property ID.
	 * 
	 * @return Value of property ID.
	 * 
	 */
	public java.lang.String getID() {
		return ID;
	}

	/**
	 * Setter for property ID.
	 * 
	 * @param ID New value of property ID.
	 * 
	 */
	public void setID(java.lang.String ID) {
		this.ID = ID;
	}

	/**
	 * Getter for property hardwareUniqueID.
	 * 
	 * @return Value of property hardwareUniqueID.
	 * 
	 */
	public java.lang.String getHardwareUniqueID() {
		return hardwareUniqueID;
	}

	/**
	 * Setter for property hardwareUniqueID.
	 * 
	 * @param hardwareUniqueID New value of property hardwareUniqueID.
	 * 
	 */
	public void setHardwareUniqueID(java.lang.String hardwareUniqueID) {
		this.hardwareUniqueID = hardwareUniqueID;
	}

	/**
	 * Getter for property hardwareConfig.
	 * 
	 * @return Value of property hardwareConfig.
	 * 
	 */
	public HardwareAcquisitionConfig getHardwareConfig() {
		return hardwareConfig;
	}

	/**
	 * Setter for property hardwareConfig.
	 * 
	 * @param hardwareConfig New value of property hardwareConfig.
	 * 
	 */
	public void setHardwareConfig(HardwareAcquisitionConfig hardwareConfig) {
		this.hardwareConfig = hardwareConfig;
	}

	// ctor

} // class DataProducerConfig
