package com.linkare.rec.am.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author artur
 */
@Entity
@Table(name = "HARDWARE_ACQUISITION_CONFIG")
public class HardwareAcquisitionConfig extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Embedded
    private DateTime timeStart;

    @Embedded
    private Frequency frequency;

    @OneToMany(mappedBy = "hardware")
    private List<HardwareParameterConfig> hardwareParameters;

    @Column(name = "TOTAL_SAMPLES")
    private int totalSamples;

    @OneToMany(mappedBy = "hardwareAcquisitionConfig")
    private List<ChannelAcquisitionConfig> channelsConfig;

    @Column(name = "FAMILIAR_NAME")
    private String familiarName;

    @Column(name = "HARDWARE_UNIQUE_ID")
    private String hardwareUniqueID;

    @OneToOne
    @JoinColumn(name = "KEY_DATA_PRODUCER")
    private DataProducer recMultiCastDataProducer;

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

    public void setSelectedFrequency(Frequency frequency) {
	this.frequency = frequency;
    }

    public Integer getTotalSamples() {
	return totalSamples;
    }

    public void setTotalSamples(Integer totalSamples) {
	this.totalSamples = totalSamples;
    }

    public List<ChannelAcquisitionConfig> getChannelsConfig() {
	return channelsConfig;
    }

    public void setChannelsConfig(List<ChannelAcquisitionConfig> channelsConfig) {
	this.channelsConfig = channelsConfig;
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

    public List<HardwareParameterConfig> getHardwareParameters() {
	return hardwareParameters;
    }

    public void setHardwareParameters(List<HardwareParameterConfig> hardwareParameters) {
	this.hardwareParameters = hardwareParameters;
    }

    public DataProducer getRecMultiCastDataProducer() {
	return recMultiCastDataProducer;
    }

    public void setRecMultiCastDataProducer(DataProducer recMultiCastDataProducer) {
	this.recMultiCastDataProducer = recMultiCastDataProducer;
    }

}
