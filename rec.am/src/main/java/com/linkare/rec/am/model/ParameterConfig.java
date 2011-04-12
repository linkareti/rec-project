package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "PARAMETER_CONFIG")
public class ParameterConfig extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "PARAMETER_NAME")
    private String parameterName;

    @Column(name = "PARAMETER_VALUE")
    private String parameterValue;

    @ManyToOne
    @JoinColumn(name = "KEY_CHANNEL_ACQUISITION_CONFIG", nullable = true, updatable = false)
    private ChannelAcquisitionConfig channelAcquisitionConfig;

    @ManyToOne
    @JoinColumn(name = "KEY_HARDWARE_ACQUISITION_CONFIG", nullable = true, updatable = false)
    private HardwareAcquisitionConfig hardwareAcquisitionConfig;

    public String getParameterName() {
	return parameterName;
    }

    public void setParameterName(String parameterName) {
	this.parameterName = parameterName;
    }

    public String getParameterValue() {
	return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
	this.parameterValue = parameterValue;
    }

    public ChannelAcquisitionConfig getChannelAcquisitionConfig() {
	return channelAcquisitionConfig;
    }

    public void setChannelAcquisitionConfig(ChannelAcquisitionConfig channelAcquisitionConfig) {
	this.channelAcquisitionConfig = channelAcquisitionConfig;
    }

    public HardwareAcquisitionConfig getHardwareAcquisitionConfig() {
	return hardwareAcquisitionConfig;
    }

    public void setHardwareAcquisitionConfig(HardwareAcquisitionConfig hardwareAcquisitionConfig) {
	this.hardwareAcquisitionConfig = hardwareAcquisitionConfig;
    }

}
