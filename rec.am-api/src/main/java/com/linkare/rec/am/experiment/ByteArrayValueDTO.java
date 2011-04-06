/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.am.experiment;

/**
 * 
 * @author artur
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
	return "ByteArrayValueDTO [mimeType=" + mimeType + ", virtualPath=" + virtualPath + "]";
    }

}
