package com.linkare.rec.am.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "DATA_PRODUCER")
public class DataProducer extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "KEY_HARDWARE_ACQUISITION_CONFIG", nullable = false, updatable = false)
    private HardwareAcquisitionConfig acqHeader;

    @Column(name = "DATA_PRODUCER_NAME")
    private String dataProducerName;

    @Column(name = "OID", unique = true)
    private String oid;

    @Column(name = "USER")
    private String user;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "KEY_SAMPLES_PACKET_BLOB", updatable = false)
    private SamplesPacketBlob serializedSamples;

    public DataProducer() {
    }

    public HardwareAcquisitionConfig getAcqHeader() {
	return acqHeader;
    }

    public void setAcqHeader(HardwareAcquisitionConfig acqHeader) {
	this.acqHeader = acqHeader;
    }

    public String getDataProducerName() {
	return dataProducerName;
    }

    public void setDataProducerName(String dataProducerName) {
	this.dataProducerName = dataProducerName;
    }

    public String getOid() {
	return oid;
    }

    public void setOid(String oid) {
	this.oid = oid;
    }

    public String getUser() {
	return user;
    }

    public void setUser(String user) {
	this.user = user;
    }

    public SamplesPacketBlob getSerializedSamples() {
	return serializedSamples;
    }

    public void setSerializedSamples(SamplesPacketBlob serializedSamples) {
	this.serializedSamples = serializedSamples;
    }

    public byte[] getSamplesPacketMatrixSerialized() {
	return getSerializedSamples() != null ? getSerializedSamples().getSamplesPacketMatrixSerialized() : null;
    }

    public void setSamplesPacketMatrixSerialized(byte[] samplesPacketMatrixSerialized) {
	final SamplesPacketBlob samplesPacketBlob = new SamplesPacketBlob();
	samplesPacketBlob.setSamplesPacketMatrixSerialized(samplesPacketMatrixSerialized);
	this.serializedSamples = samplesPacketBlob;
    }

}
