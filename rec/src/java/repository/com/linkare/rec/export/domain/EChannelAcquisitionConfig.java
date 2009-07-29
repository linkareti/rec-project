package com.linkare.rec.export.domain;

import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.export.domain.embeddable.EmbDateTime;
import com.linkare.rec.export.domain.embeddable.EmbFrequency;
import com.linkare.rec.export.exceptions.InvalidMultiplierException;
import com.linkare.rec.export.exceptions.InvalidTypeValException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "CHANNELAQUISITION_CONFIG")
public class EChannelAcquisitionConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private EmbDateTime timeStart;
    private EmbFrequency selectedFrequency;
    private EScale selectedScale;
    private List<EChannelParameterConfig> selectedChannelParameters;
    private Integer totalSamples;
    private String channelName;
    private EHardwareAcquisitionConfig hardwareAcquisitionConfig;

    public EChannelAcquisitionConfig() {
    }

    public EChannelAcquisitionConfig(ChannelAcquisitionConfig channelAcquisitionConfig) throws InvalidTypeValException, InvalidMultiplierException {
        if (channelAcquisitionConfig != null) {
            this.setTimeStart(new EmbDateTime(channelAcquisitionConfig.getTimeStart()));
            this.setSelectedFrequency(new EmbFrequency(channelAcquisitionConfig.getSelectedFrequency()));
            this.setSelectedScale(new EScale(channelAcquisitionConfig.getSelectedScale()));
            if (channelAcquisitionConfig.getSelectedChannelParameters() != null) {
                this.setSelectedChannelParameters(new ArrayList<EChannelParameterConfig>());
                for (ParameterConfig parameterConfig : channelAcquisitionConfig.getSelectedChannelParameters()) {
                    this.addBidirectEChannelParameterConfig(new EChannelParameterConfig(parameterConfig));
                }
            }
            this.setTotalSamples(channelAcquisitionConfig.getTotalSamples());
            this.setChannelName(channelAcquisitionConfig.getChannelName());
        }

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    public EmbDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(EmbDateTime timeStart) {
        this.timeStart = timeStart;
    }

    @Embedded
    public EmbFrequency getSelectedFrequency() {
        return selectedFrequency;
    }

    public void setSelectedFrequency(EmbFrequency selectedFrequency) {
        this.selectedFrequency = selectedFrequency;
    }

    @OneToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "SCALE_ID")
    public EScale getSelectedScale() {
        return selectedScale;
    }

    public void setSelectedScale(EScale selectedScale) {
        this.selectedScale = selectedScale;
    }

    @OneToMany(mappedBy = "channel", cascade = CascadeType.PERSIST)
    public List<EChannelParameterConfig> getSelectedChannelParameters() {
        return selectedChannelParameters;
    }

    public void setSelectedChannelParameters(List<EChannelParameterConfig> selectedChannelParameters) {
        this.selectedChannelParameters = selectedChannelParameters;
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

    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    @JoinColumn(name = "HARDWAREACQUISITIONCONFIG_ID")
    public EHardwareAcquisitionConfig getHardwareAcquisitionConfig() {
        return hardwareAcquisitionConfig;
    }

    public void setHardwareAcquisitionConfig(EHardwareAcquisitionConfig hardwareAcquisitionConfig) {
        this.hardwareAcquisitionConfig = hardwareAcquisitionConfig;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EChannelAcquisitionConfig)) {
            return false;
        }
        EChannelAcquisitionConfig other = (EChannelAcquisitionConfig) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    }

    //bidirectionality 
    public void addBidirectEChannelParameterConfig(EChannelParameterConfig eChannelParameterConfig) {
        if (eChannelParameterConfig != null) {
            if (eChannelParameterConfig.getChannel() != null) {
                eChannelParameterConfig.getChannel().getSelectedChannelParameters().remove(eChannelParameterConfig);
            }
            this.getSelectedChannelParameters().add(eChannelParameterConfig);
            eChannelParameterConfig.setChannel(this);
        }
    }//    public void setBidirectEHardwareAcquisitionConfig(EHardwareAcquisitionConfig eHardwareAcquisitionConfig){
//        if (eHardwareAcquisitionConfig != null){
//            
//        }
//    }
}
