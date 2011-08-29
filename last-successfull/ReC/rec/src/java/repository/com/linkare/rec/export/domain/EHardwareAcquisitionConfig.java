package com.linkare.rec.export.domain;

import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.export.domain.embeddable.EmbDateTime;
import com.linkare.rec.export.domain.embeddable.EmbFrequency;
import com.linkare.rec.export.exceptions.InvalidMultiplierException;
import com.linkare.rec.export.exceptions.InvalidTypeValException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "HARDWAREACQUISITION_CONFIG")
public class EHardwareAcquisitionConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private EmbDateTime timeStart;
    private EmbFrequency selectedFrequency;
    private List<EHardwareParameterConfig> selectedHardwareParameters;
    private Integer totalSamples;
    private List<EChannelAcquisitionConfig> channelsConfig;
    private String familiarName;
    private String hardwareUniqueID;
    private EReCMultiCastDataProducer recMultiCastDataProducer;

    //default constructor
    public EHardwareAcquisitionConfig() {
    }

    //this constructor will do the mapping between HardwareAcquisitionConfigVO to this entity
    public EHardwareAcquisitionConfig(HardwareAcquisitionConfig hardwareAcquisitionConfig)
            throws InvalidTypeValException, InvalidMultiplierException {
        if (hardwareAcquisitionConfig != null) {
            this.setTimeStart(new EmbDateTime(hardwareAcquisitionConfig.getTimeStart()));
            this.setSelectedFrequency(new EmbFrequency(hardwareAcquisitionConfig.getSelectedFrequency()));
            if (hardwareAcquisitionConfig.getSelectedHardwareParameters() != null) {
                this.setSelectedHardwareParameters(new ArrayList<EHardwareParameterConfig>());
                for (ParameterConfig parameterConfig : hardwareAcquisitionConfig.getSelectedHardwareParameters()) {
                    this.addBidirectEHardwareParameterConfig(new EHardwareParameterConfig(parameterConfig));
                }
            }
            this.setTotalSamples(hardwareAcquisitionConfig.getTotalSamples());
            if (hardwareAcquisitionConfig.getChannelsConfig() != null) {
                this.setChannelsConfig(new ArrayList<EChannelAcquisitionConfig>());
                for (ChannelAcquisitionConfig channelAcquisitionConfig : hardwareAcquisitionConfig.getChannelsConfig()) {
                    this.addBidirectEChannelAcquisitionConfig(new EChannelAcquisitionConfig(channelAcquisitionConfig));
                }
            }

            this.setFamiliarName(hardwareAcquisitionConfig.getFamiliarName());
            this.setHardwareUniqueID(hardwareAcquisitionConfig.getHardwareUniqueID());
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }

    @OneToOne
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

    public Integer getTotalSamples() {
        return totalSamples;
    }

    public void setTotalSamples(Integer totalSamples) {
        this.totalSamples = totalSamples;
    }

    @OneToMany(mappedBy = "hardwareAcquisitionConfig", cascade = CascadeType.PERSIST)
    public List<EChannelAcquisitionConfig> getChannelsConfig() {
        return channelsConfig;
    }

    public void setChannelsConfig(List<EChannelAcquisitionConfig> channelsConfig) {
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

    @OneToMany(mappedBy = "hardware", cascade = CascadeType.PERSIST)
    public List<EHardwareParameterConfig> getSelectedHardwareParameters() {
        return selectedHardwareParameters;
    }

    public void setSelectedHardwareParameters(List<EHardwareParameterConfig> selectedHardwareParameters) {
        this.selectedHardwareParameters = selectedHardwareParameters;
    }

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "RECMULTICASTDATAPRODUCER_ID")
    public EReCMultiCastDataProducer getRecMultiCastDataProducer() {
        return recMultiCastDataProducer;
    }

    public void setRecMultiCastDataProducer(EReCMultiCastDataProducer recMultiCastDataProducer) {
        this.recMultiCastDataProducer = recMultiCastDataProducer;
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
        if (!(object instanceof EHardwareAcquisitionConfig)) {
            return false;
        }
        EHardwareAcquisitionConfig other = (EHardwareAcquisitionConfig) object;
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
    public void addBidirectEHardwareParameterConfig(EHardwareParameterConfig eHardwareParameterConfig) {
        if (eHardwareParameterConfig != null) {
            if (eHardwareParameterConfig.getHardware() != null) {
                eHardwareParameterConfig.getHardware().getSelectedHardwareParameters().remove(eHardwareParameterConfig);
            }
            this.getSelectedHardwareParameters().add(eHardwareParameterConfig);
            eHardwareParameterConfig.setHardware(this);
        }
    }

    public void addBidirectEChannelAcquisitionConfig(EChannelAcquisitionConfig eChannelAcquisitionConfig) {
        if (eChannelAcquisitionConfig != null) {
            if (eChannelAcquisitionConfig.getHardwareAcquisitionConfig() != null) {
                eChannelAcquisitionConfig.getHardwareAcquisitionConfig().getChannelsConfig().remove(eChannelAcquisitionConfig);
            }
            this.getChannelsConfig().add(eChannelAcquisitionConfig);
            eChannelAcquisitionConfig.setHardwareAcquisitionConfig(this);
        }
    }
}
