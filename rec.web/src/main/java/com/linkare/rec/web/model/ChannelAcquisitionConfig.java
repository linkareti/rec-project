package com.linkare.rec.web.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "CHANNEL_AQUISITION_CONFIG")
public class ChannelAcquisitionConfig extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Embedded
    private DateTime timeStart;

    @Embedded
    @Column(name = "FREQUENCY")
    private Frequency frequency;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "KEY_SCALE", nullable = false, updatable = false)
    private Scale scale;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "channelAcquisitionConfig")
    private List<ParameterConfig> channelParameters;

    @Column(name = "TOTAL_SAMPLES")
    private int totalSamples;

    @Column(name = "CHANNEL_NAME")
    private String channelName;

    @ManyToOne
    @JoinColumn(name = "KEY_HARDWARE_ACQUISITION_CONFIG")
    private HardwareAcquisitionConfig hardwareAcquisitionConfig;

    public ChannelAcquisitionConfig() {
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

    public Scale getScale() {
	return scale;
    }

    public void setScale(Scale scale) {
	this.scale = scale;
    }

    public List<ParameterConfig> getChannelParameters() {
	return channelParameters;
    }

    public void setChannelParameters(List<ParameterConfig> channelParameters) {
	this.channelParameters = channelParameters;

	// bidirectionality
	if (channelParameters != null) {
	    for (final ParameterConfig parameterConfig : channelParameters) {
		parameterConfig.setChannelAcquisitionConfig(this);
	    }
	}

    }

    public int getTotalSamples() {
	return totalSamples;
    }

    public void setTotalSamples(int totalSamples) {
	this.totalSamples = totalSamples;
    }

    public String getChannelName() {
	return channelName;
    }

    public void setChannelName(String channelName) {
	this.channelName = channelName;
    }

    public HardwareAcquisitionConfig getHardwareAcquisitionConfig() {
	return hardwareAcquisitionConfig;
    }

    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig hardwareAcquisitionConfig) {
	this.hardwareAcquisitionConfig = hardwareAcquisitionConfig;
    }

}
