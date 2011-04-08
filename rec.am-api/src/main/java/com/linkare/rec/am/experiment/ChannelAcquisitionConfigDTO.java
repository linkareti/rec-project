/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

import java.util.List;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class ChannelAcquisitionConfigDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private DateTimeDTO timeStart;

    private FrequencyDTO frequency;

    private ScaleDTO scale;

    private List<ParameterConfigDTO> channelParameters;

    private int totalSamples;

    private String channelName;

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

    public List<ParameterConfigDTO> getChannelParameters() {
	return channelParameters;
    }

    public void setChannelParameters(List<ParameterConfigDTO> channelParameters) {
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

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ChannelAcquisitionConfigDTO [timeStart=");
	builder.append(timeStart);
	builder.append(", frequency=");
	builder.append(frequency);
	builder.append(", scale=");
	builder.append(scale);
	builder.append(", channelParameters=");
	builder.append(channelParameters);
	builder.append(", totalSamples=");
	builder.append(totalSamples);
	builder.append(", channelName=");
	builder.append(channelName);
	builder.append("]");
	return builder.toString();
    }

}
