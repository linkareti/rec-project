package com.linkare.rec.am.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 
 * @author artur
 */
@Entity
@DiscriminatorValue(value = "HARDWARE")
public class HardwareParameterConfig extends ParameterConfig {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "KEY_HARDWARE_ACQUISITION_CONFIG")
    private HardwareAcquisitionConfig hardware;

    public HardwareParameterConfig() {
    }

    public HardwareAcquisitionConfig getHardware() {
	return hardware;
    }

    public void setHardware(HardwareAcquisitionConfig hardware) {
	this.hardware = hardware;
    }

}
