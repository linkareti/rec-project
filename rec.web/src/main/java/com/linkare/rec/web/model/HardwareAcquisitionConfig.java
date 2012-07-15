package com.linkare.rec.web.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "HARDWARE_ACQUISITION_CONFIG")
public class HardwareAcquisitionConfig extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Embedded
    private DateTime timeStart;

    @Embedded
    private Frequency frequency;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "hardwareAcquisitionConfig")
    private List<ParameterConfig> hardwareParameters;

    @Column(name = "TOTAL_SAMPLES")
    private int totalSamples;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "hardwareAcquisitionConfig")
    private List<ChannelAcquisitionConfig> channelsConfig;

    @Column(name = "FAMILIAR_NAME")
    private String familiarName;

    @Column(name = "HARDWARE_UNIQUE_ID")
    private String hardwareUniqueID;

    //default constructor
    public HardwareAcquisitionConfig() {
    }

    public DateTime getTimeStart() {
	return timeStart;
    }

    public void setTimeStart(DateTime timeStart) {
	this.timeStart = timeStart;
    }

    public Frequency getFrequency() {
	return frequency;
    }

    public void setFrequency(Frequency frequency) {
	this.frequency = frequency;
    }

    public int getTotalSamples() {
	return totalSamples;
    }

    public void setTotalSamples(int totalSamples) {
	this.totalSamples = totalSamples;
    }

    public List<ChannelAcquisitionConfig> getChannelsConfig() {
	return channelsConfig;
    }

    public void setChannelsConfig(List<ChannelAcquisitionConfig> channelsConfig) {
	this.channelsConfig = channelsConfig;

	if (channelsConfig != null) {
	    for (final ChannelAcquisitionConfig channelAcquisitionConfig : channelsConfig) {
		channelAcquisitionConfig.setHardwareAcquisitionConfig(this);
	    }
	}
    }

    public String getFamiliarName() {
	return familiarName;
    }

    public void setFamiliarName(String familiarName) {
	this.familiarName = familiarName;
    }

    public String getHardwareUniqueID() {
	return hardwareUniqueID;
    }

    public void setHardwareUniqueID(String hardwareUniqueID) {
	this.hardwareUniqueID = hardwareUniqueID;
    }

    public List<ParameterConfig> getHardwareParameters() {
	return hardwareParameters;
    }

    public void setHardwareParameters(List<ParameterConfig> hardwareParameters) {
	this.hardwareParameters = hardwareParameters;

	//bidirectionality
	if (hardwareParameters != null && hardwareParameters.size() > 0) {
	    for (final ParameterConfig parameterConfig : hardwareParameters) {
		parameterConfig.setHardwareAcquisitionConfig(this);
	    }
	}
    }

}
