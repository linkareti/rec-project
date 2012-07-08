package com.linkare.rec.am;

import java.beans.ConstructorProperties;
import java.io.Serializable;

/**
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
public class HardwareInfoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String driverVersion;

    private String hardwareName;

    private String hardwareVersion;

    private String hardwareManufacturer;

    private String descriptionText;

    private String hardwareUniqueID;

    private String familiarName = null;

    @ConstructorProperties({ "driverVersion", "hardwareName", "hardwareVersion", "hardwareManufacturer", "descriptionText", "hardwareUniqueID", "familiarName" })
    public HardwareInfoDTO(String driverVersion, String hardwareName, String hardwareVersion, String hardwareManufacturer, String descriptionText,
	    String hardwareUniqueID, String familiarName) {
	super();
	this.driverVersion = driverVersion;
	this.hardwareName = hardwareName;
	this.hardwareVersion = hardwareVersion;
	this.hardwareManufacturer = hardwareManufacturer;
	this.descriptionText = descriptionText;
	this.hardwareUniqueID = hardwareUniqueID;
	this.familiarName = familiarName;
    }

    public String getDriverVersion() {
	return driverVersion;
    }


    public void setDriverVersion(String driverVersion) {
	this.driverVersion = driverVersion;
    }

    public String getHardwareName() {
	return hardwareName;
    }

    public void setHardwareName(String hardwareName) {
	this.hardwareName = hardwareName;
    }

    public String getHardwareVersion() {
	return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
	this.hardwareVersion = hardwareVersion;
    }

    public String getHardwareManufacturer() {
	return hardwareManufacturer;
    }

    public void setHardwareManufacturer(String hardwareManufacturer) {
	this.hardwareManufacturer = hardwareManufacturer;
    }

    public String getDescriptionText() {
	return descriptionText;
    }

    public void setDescriptionText(String descriptionText) {
	this.descriptionText = descriptionText;
    }

    public String getHardwareUniqueID() {
	return hardwareUniqueID;
    }

    public void setHardwareUniqueID(String hardwareUniqueID) {
	this.hardwareUniqueID = hardwareUniqueID;
    }

    public String getFamiliarName() {
	return familiarName;
    }

    public void setFamiliarName(String familiarName) {
	this.familiarName = familiarName;
    }

}
