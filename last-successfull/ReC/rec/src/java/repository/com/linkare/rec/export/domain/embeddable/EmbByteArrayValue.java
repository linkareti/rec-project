package com.linkare.rec.export.domain.embeddable;

import com.linkare.rec.data.acquisition.ByteArrayValue;
import com.linkare.rec.utils.Configuration;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 *
 * @author artur
 */
@Embeddable
public class EmbByteArrayValue implements Serializable {

    private byte[] data = null;
    private String mimeType = null;
    private String virtualPath = null;

    public EmbByteArrayValue() {
    }

    public EmbByteArrayValue(ByteArrayValue byteArrayValue) {
        if (byteArrayValue != null) {
            this.setData(byteArrayValue.getData());
            this.setMimeType(byteArrayValue.getMimeType());
            this.setVirtualPath(Configuration.getProperty("virtual path where we will saved the data"));
        }
    }
    // this will be saved in the File System
    @Transient
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
