package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * 
 * @author artur
 */
@Embeddable
public class ByteArrayValue {

    // this will be saved in the File System
    @Transient
    private byte[] data = null;

    @Column(name = "MIMETYPE")
    private String mimeType = null;

    @Column(name = "VIRTUALPATH")
    private String virtualPath = null;

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

    public String getVirtualPath() {
	return virtualPath;
    }

    public void setVirtualPath(String virtualPath) {
	this.virtualPath = virtualPath;
    }
}
