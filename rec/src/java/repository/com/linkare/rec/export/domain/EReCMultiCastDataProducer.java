package com.linkare.rec.export.domain;

import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.ejb.RecMDBBean;
import com.linkare.rec.export.exceptions.InvalidMultiplierException;
import com.linkare.rec.export.exceptions.InvalidTypeValException;
import com.linkare.rec.impl.data.SamplesPacketMatrix;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.multicast.ReCMultiCastDataProducer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "RECMULTICAST_DATAPRODUCER")
public class EReCMultiCastDataProducer implements Serializable {

    private static final Log log = LogFactory.getLog(EReCMultiCastDataProducer.class);
    private static final long serialVersionUID = 1L;
    private Long id;
    private EHardwareAcquisitionConfig cachedAcqHeader;
    private String cachedDataProducerName;
    private String oid;
    private List<ESamplesPacket> samplesPacketMatrix;

    public EReCMultiCastDataProducer() {
    }

    public EReCMultiCastDataProducer(ReCMultiCastDataProducer reCMultiCastDataProducer) {
        if (reCMultiCastDataProducer != null) {
            try {
                this.setBidirectEHardwareAcquisitionConfig(new EHardwareAcquisitionConfig(reCMultiCastDataProducer.getAcquisitionHeader()));
            } catch (InvalidTypeValException ex) {
                log.error("Invalid TypeVal", ex);
            } catch (InvalidMultiplierException ex) {
                log.error("Invalid Multiplier", ex);
            } catch (NotAvailableException ex) {
                // dont do nothing
            }
            this.setCachedDataProducerName(reCMultiCastDataProducer.getDataProducerName());
            this.setOid(reCMultiCastDataProducer.getOID());

            try {
                if (reCMultiCastDataProducer.getSamplesPacketSource() != null) {
                    SamplesPacketMatrix aux = (SamplesPacketMatrix) reCMultiCastDataProducer.getSamplesPacketSource();
                    SamplesPacket[] aux2 = aux.getSamplesPackets();
                    if (aux2 != null) {
                        this.setSamplesPacketMatrix(new ArrayList<ESamplesPacket>());

                        for (SamplesPacket samplesPacket : aux2) {
                            if (samplesPacket != null) {
                                this.addBidirectESamplesPacket(new ESamplesPacket(samplesPacket));
                            }
                        }
                    }
                }

            } catch (InvalidTypeValException ex) {
                log.error("Invalid TypeVal", ex);
            } catch (InvalidMultiplierException ex) {
                log.error("Invalid Multiplier", ex);
            } catch (SamplesPacketReadException e) {
                // dont do nothing
            }
        }
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "recMultiCastDataProducer", cascade = {CascadeType.PERSIST}, optional = false)
    public EHardwareAcquisitionConfig getCachedAcqHeader() {
        return cachedAcqHeader;
    }

    public void setCachedAcqHeader(EHardwareAcquisitionConfig cachedAcqHeader) {
        this.cachedAcqHeader = cachedAcqHeader;
    }

    public String getCachedDataProducerName() {
        return cachedDataProducerName;
    }

    public void setCachedDataProducerName(String cachedDataProducerName) {
        this.cachedDataProducerName = cachedDataProducerName;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    @OneToMany(mappedBy = "eReCMultiCastDataProducer", cascade = CascadeType.PERSIST)
    public List<ESamplesPacket> getSamplesPacketMatrix() {
        return samplesPacketMatrix;
    }

    public void setSamplesPacketMatrix(List<ESamplesPacket> samplesPacketMatrix) {
        this.samplesPacketMatrix = samplesPacketMatrix;
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
        if (!(object instanceof EReCMultiCastDataProducer)) {
            return false;
        }
        EReCMultiCastDataProducer other = (EReCMultiCastDataProducer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + this.getId() + "]";
    }

    //bidirectionality 
    public void addBidirectESamplesPacket(ESamplesPacket eSamplesPacket) {
        if (eSamplesPacket != null) {
            if (eSamplesPacket.getEReCMultiCastDataProducer() != null) {
                eSamplesPacket.getEReCMultiCastDataProducer().getSamplesPacketMatrix().remove(eSamplesPacket);
            }
            this.getSamplesPacketMatrix().add(eSamplesPacket);
            eSamplesPacket.setEReCMultiCastDataProducer(this);

        }
    }

    public void setBidirectEHardwareAcquisitionConfig(EHardwareAcquisitionConfig ehardwareAcquisitionConfig) {
        if (ehardwareAcquisitionConfig != null) {
            if (ehardwareAcquisitionConfig.getRecMultiCastDataProducer() != null) {
                ehardwareAcquisitionConfig.getRecMultiCastDataProducer().setCachedAcqHeader(null);
            }
            this.setCachedAcqHeader(ehardwareAcquisitionConfig);
            ehardwareAcquisitionConfig.setRecMultiCastDataProducer(this);

        }
    }
}
