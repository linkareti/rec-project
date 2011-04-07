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
public class HardwareAcquisitionConfigDTO extends AbstractBaseDTO {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private DateTimeDTO timeStart;

    private FrequencyDTO frequency;

    private List<HardwareParameterConfigDTO> hardwareParameters;

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

    public List<HardwareParameterConfigDTO> getHardwareParameters() {
	return hardwareParameters;
    }

    public void setHardwareParameters(List<HardwareParameterConfigDTO> hardwareParameters) {
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
}
