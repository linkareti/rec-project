/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

import java.util.List;

/**
 * 
 * @author artur
 */
public class ChannelAcquisitionConfigDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private DateTimeDTO timeStart;

    private FrequencyDTO frequency;

    private ScaleDTO scale;

    private List<ChannelParameterConfigDTO> channelParameters;

    private int totalSamples;

    private String channelName;

    private HardwareAcquisitionConfigDTO hardwareAcquisitionConfig;

    public ChannelAcquisitionConfigDTO() {
    }

    public DateTimeDTO getTimeStart() {
	return timeStart;
    }

    public void setTimeStart(DateTimeDTO timeStart) {
	this.timeStart = timeStart;
    }

    public FrequencyDTO getFrequency() {
	return frequency;
    }

    public void setFrequency(FrequencyDTO frequency) {
	this.frequency = frequency;
    }

    public ScaleDTO getScale() {
	return scale;
    }

    public void setScale(ScaleDTO scale) {
	this.scale = scale;
    }

    public List<ChannelParameterConfigDTO> getChannelParameters() {
	return channelParameters;
    }

    public void setChannelParameters(List<ChannelParameterConfigDTO> channelParameters) {
	this.channelParameters = channelParameters;
    }

    public Integer getTotalSamples() {
	return totalSamples;
    }

    public void setTotalSamples(Integer totalSamples) {
	this.totalSamples = totalSamples;
    }

    public String getChannelName() {
	return channelName;
    }

    public void setChannelName(String channelName) {
	this.channelName = channelName;
    }

}
