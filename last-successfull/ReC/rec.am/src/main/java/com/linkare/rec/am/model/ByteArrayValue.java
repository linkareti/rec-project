package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Embeddable
public class ByteArrayValue {

    @Column(name = "DATA")
    private byte[] data = null;

    @Column(name = "MIMETYPE")
    private String mimeType = null;

    public ByteArrayValue() {
    }

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	this.data = data;
    }

    public String getMimeType() {
	return mimeType;
    }

    public void setMimeType(String mimeType) {
	this.mimeType = mimeType;
    }
}
