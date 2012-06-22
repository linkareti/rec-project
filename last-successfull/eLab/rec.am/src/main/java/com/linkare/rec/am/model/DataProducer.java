package com.linkare.rec.am.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;
import com.linkare.rec.am.repository.DataProducerStateEnum;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "DATA_PRODUCER")
@NamedQueries({ @NamedQuery(name = DataProducer.FIND_BY_OID_QUERYNAME, query = DataProducer.FIND_BY_OID_QUERY) })
public class DataProducer extends DefaultDomainObject {

    public static final String QUERY_PARAM_OID = "oid";
    public static final String FIND_BY_OID_QUERYNAME = "DataProducer.findByOID";
    public static final String FIND_BY_OID_QUERY = "Select dp from DataProducer dp where dp.oid like :" + QUERY_PARAM_OID;

    private static final long serialVersionUID = 1L;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "KEY_HARDWARE_ACQUISITION_CONFIG", nullable = false, updatable = false)
    private HardwareAcquisitionConfig acqHeader;

    @Column(name = "DATA_PRODUCER_NAME")
    private String dataProducerName;

    @Column(name = "OID", unique = true, nullable = false)
    private String oid;

    @Column(name = "USER")
    private String user;

    @Enumerated(EnumType.STRING)
    @Column(name = "DATA_COLLECTOR_STATE")
    private DataProducerStateEnum dataCollectorState;

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

    public DataProducerStateEnum getDataCollectorState() {
	return dataCollectorState;
    }

    public void setDataCollectorState(DataProducerStateEnum dataCollectorState) {
	this.dataCollectorState = dataCollectorState;
    }

}
