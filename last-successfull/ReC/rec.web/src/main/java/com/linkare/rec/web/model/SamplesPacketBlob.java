package com.linkare.rec.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * Only to make lazy loadind of blob possible
 * 
 * 
 * @author Artur Correia - Linkare TI
 * 
 */
@Entity
@Table(name = "SAMPLES_PACKET_BLOB")
public class SamplesPacketBlob extends DefaultDomainObject {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @Lob
    @Column(name = "SAMPLES_PACKET_MATRIX_BLOB")
    private byte[] samplesPacketMatrixSerialized;

    public byte[] getSamplesPacketMatrixSerialized() {
	return samplesPacketMatrixSerialized;
    }

    public void setSamplesPacketMatrixSerialized(byte[] samplesPacketMatrixSerialized) {
	this.samplesPacketMatrixSerialized = samplesPacketMatrixSerialized;
    }

}
