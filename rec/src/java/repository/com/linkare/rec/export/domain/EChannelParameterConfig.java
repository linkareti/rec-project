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
@DiscriminatorValue(value = "CHANNEL")
public class EChannelParameterConfig extends EParameterConfig {

    private static final long serialVersionUID = 1L;
    private EChannelAcquisitionConfig channel;

    public EChannelParameterConfig() {
    }

    public EChannelParameterConfig(ParameterConfig parameterConfig) {
        if (parameterConfig != null) {
            this.setParameterName(parameterConfig.getParameterName());
            this.setParameterValue(parameterConfig.getParameterValue());
        }
    }

    @ManyToOne
    @JoinColumn(name = "CHANNELACQUISITIONCONFIG_ID")
    public EChannelAcquisitionConfig getChannel() {
        return channel;
    }

    public void setChannel(EChannelAcquisitionConfig channel) {
        this.channel = channel;
    }

    /*@Override
    public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
    }
    @Override
    public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EChannelParameterConfig)) {
    return false;
    }
    EChannelParameterConfig other = (EChannelParameterConfig) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
    return false;
    }
    return true;
    } */
    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.getId() + "]";
    }
}
