package com.linkare.rec.export.domain;

import com.linkare.rec.data.config.ParameterConfig;
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
public class EHardwareParameterConfig extends EParameterConfig {

    private static final long serialVersionUID = 1L;
    private EHardwareAcquisitionConfig hardware;

    public EHardwareParameterConfig() {

    }

    public EHardwareParameterConfig(ParameterConfig parameterConfig) {
        this.setParameterName(parameterConfig.getParameterName());
        this.setParameterValue(parameterConfig.getParameterValue());
    }

    @ManyToOne
    @JoinColumn(name = "HARDWAREACQUISITIONCONFIG_ID")
    public EHardwareAcquisitionConfig getHardware() {
        return hardware;
    }

    public void setHardware(EHardwareAcquisitionConfig hardware) {
        this.hardware = hardware;
    }

    /* @Override
    public int hashCode() {
    int hash = 0;
    hash += (this.getId() != null ? this.getId().hashCode() : 0);
    return hash;
    }
    @Override
    public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EHardwareParameterConfig)) {
    return false;
    }
    EHardwareParameterConfig other = (EHardwareParameterConfig) object;
    if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
    return false;
    }
    return true;
    } */
    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.getId() + "]";
    }
}
