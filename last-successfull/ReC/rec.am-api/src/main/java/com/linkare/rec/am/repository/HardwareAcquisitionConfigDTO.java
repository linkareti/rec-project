/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.repository;

import java.util.List;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class HardwareAcquisitionConfigDTO extends AbstractBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private DateTimeDTO timeStart;

    private FrequencyDTO frequency;

    private List<ParameterConfigDTO> hardwareParameters;

    private int totalSamples;

    private List<ChannelAcquisitionConfigDTO> channelsConfig;

    private String familiarName;

    private String hardwareUniqueID;

    public List<ChannelAcquisitionConfigDTO> getChannelsConfig() {
	return channelsConfig;
    }

    public void setChannelsConfig(List<ChannelAcquisitionConfigDTO> channelsConfig) {
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

    public FrequencyDTO getFrequency() {
	return frequency;
    }

    public void setFrequency(FrequencyDTO frequency) {
	this.frequency = frequency;
    }

    public List<ParameterConfigDTO> getHardwareParameters() {
	return hardwareParameters;
    }

    public void setHardwareParameters(List<ParameterConfigDTO> hardwareParameters) {
	this.hardwareParameters = hardwareParameters;
    }

    public DateTimeDTO getTimeStart() {
	return timeStart;
    }

    public void setTimeStart(DateTimeDTO timeStart) {
	this.timeStart = timeStart;
    }

    public int getTotalSamples() {
	return totalSamples;
    }

    public void setTotalSamples(int totalSamples) {
	this.totalSamples = totalSamples;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("HardwareAcquisitionConfigDTO [timeStart=");
	builder.append(timeStart);
	builder.append(", frequency=");
	builder.append(frequency);
	builder.append(", hardwareParameters=");
	builder.append(hardwareParameters);
	builder.append(", totalSamples=");
	builder.append(totalSamples);
	builder.append(", channelsConfig=");
	builder.append(channelsConfig);
	builder.append(", familiarName=");
	builder.append(familiarName);
	builder.append(", hardwareUniqueID=");
	builder.append(hardwareUniqueID);
	builder.append("]");
	return builder.toString();
    }

}
