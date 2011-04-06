/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

import java.util.Collection;

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

    private FrequencyDTO selectedFrequency;

    private Collection<HardwareParameterConfigDTO> selectedHardwareParameters;

    private int totalSamples;

    private Collection<ChannelAcquisitionConfigDTO> channelsConfig;

    private String familiarName;

    private String hardwareUniqueID;

    public Collection<ChannelAcquisitionConfigDTO> getChannelsConfig() {
	return channelsConfig;
    }

    public void setChannelsConfig(Collection<ChannelAcquisitionConfigDTO> channelsConfig) {
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

    public FrequencyDTO getSelectedFrequency() {
	return selectedFrequency;
    }

    public void setSelectedFrequency(FrequencyDTO selectedFrequency) {
	this.selectedFrequency = selectedFrequency;
    }

    public Collection<HardwareParameterConfigDTO> getSelectedHardwareParameters() {
	return selectedHardwareParameters;
    }

    public void setSelectedHardwareParameters(Collection<HardwareParameterConfigDTO> selectedHardwareParameters) {
	this.selectedHardwareParameters = selectedHardwareParameters;
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
