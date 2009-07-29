package com.linkare.rec.export.domain;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.export.domain.embeddable.EmbDateTime;
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
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "SAMPLES_PACKET")
@AttributeOverride(name = "theData", column = @Column(nullable = false))
public class ESamplesPacket implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer packetNumber;
    private Integer totalPackets;
    private List<EColumnPhysicsValue> theData;
    private EmbDateTime timeStart = null;
    private EReCMultiCastDataProducer eReCMultiCastDataProducer;

    public ESamplesPacket() {
    }

    public ESamplesPacket(SamplesPacket samplesPacket) throws InvalidTypeValException, InvalidMultiplierException {
        if (samplesPacket != null) {
            this.setPacketNumber(samplesPacket.getPacketNumber());
            this.setTotalPackets(samplesPacket.getTotalPackets());
            PhysicsValue[][] aux = samplesPacket.getData();
            if (aux != null) {
                List<EColumnPhysicsValue> list = new ArrayList<EColumnPhysicsValue>();
                for (PhysicsValue[] physicsValue : aux) {
                    if (physicsValue != null) {
                        addBidirectEColumnPhysicsValue(new EColumnPhysicsValue(physicsValue));
                    }
                }

            }
            if (samplesPacket.getTimeStart() != null) {
                this.setTimeStart(new EmbDateTime(samplesPacket.getTimeStart()));
            }
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

    public Integer getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(Integer packetNumber) {
        this.packetNumber = packetNumber;
    }

    public Integer getTotalPackets() {
        return totalPackets;
    }

    public void setTotalPackets(Integer totalPackets) {
        this.totalPackets = totalPackets;
    }

    @OneToMany(mappedBy = "samplesPacket", cascade = CascadeType.PERSIST)
    public List<EColumnPhysicsValue> getTheData() {
        return theData;
    }

    public void setTheData(List<EColumnPhysicsValue> theData) {
        this.theData = theData;
    }

    @Embedded
    public EmbDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(EmbDateTime timeStart) {
        this.timeStart = timeStart;
    }

    @ManyToOne
    @JoinColumn(name = "RECMULTICASTDATAPRODUCER_ID")
    public EReCMultiCastDataProducer getEReCMultiCastDataProducer() {
        return eReCMultiCastDataProducer;
    }

    public void setEReCMultiCastDataProducer(EReCMultiCastDataProducer eReCMultiCastDataProducer) {
        this.eReCMultiCastDataProducer = eReCMultiCastDataProducer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ESamplesPacket)) {
            return false;
        }
        ESamplesPacket other = (ESamplesPacket) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    }

    //bidirectionality
    public void addBidirectEColumnPhysicsValue(EColumnPhysicsValue eColumnPhysicsValue) {
        if (eColumnPhysicsValue != null) {
            if (eColumnPhysicsValue.getSamplesPacket() != null) {
                eColumnPhysicsValue.getSamplesPacket().getTheData().remove(eColumnPhysicsValue);
            }
            eColumnPhysicsValue.setSamplesPacket(this);
            if (this.getTheData() == null) {
                this.setTheData(new ArrayList<EColumnPhysicsValue>());
            }
            this.getTheData().add(eColumnPhysicsValue);

        }
    }//    public void setBidirectEReCMultiCastDataProducer(EReCMultiCastDataProducer eReCMultiCastDataProducer) {
//        if (eReCMultiCastDataProducer != null) {
//            if (this.eReCMultiCastDataProducer != null) {
//                this.eReCMultiCastDataProducer.getSamplesPacketMatrix().remove(this);
//            }
//            this.eReCMultiCastDataProducer = eReCMultiCastDataProducer;
//            eReCMultiCastDataProducer.getSamplesPacketMatrix().add(this);
//        }
//    }
}
