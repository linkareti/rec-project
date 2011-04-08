/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class ByteArrayValueDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // this will be saved in the File System
    private byte[] data = null;

    private String mimeType = null;

    private String virtualPath = null;

    public ByteArrayValueDTO() {
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

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("ByteArrayValueDTO [data.lenght=");
	builder.append(data != null ? data.length : "null");
	builder.append(", mimeType=");
	builder.append(mimeType);
	builder.append(", virtualPath=");
	builder.append(virtualPath);
	builder.append("]");
	return builder.toString();
    }

}
