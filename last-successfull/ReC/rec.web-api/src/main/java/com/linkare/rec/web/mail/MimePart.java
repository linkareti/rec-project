package com.linkare.rec.web.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.activation.FileDataSource;

public class MimePart implements Serializable {

    private static final long serialVersionUID = 5810802147068460596L;

    private String partName;
    private byte[] partContent;
    private String mimeType;
    private int cid;

    public MimePart(int cid, String partName, byte[] partContent,
	    String mimeType) {
	super();
	this.partName = partName;
	this.partContent = partContent;
	this.mimeType = mimeType;
	this.cid = cid;
    }

    public MimePart() {
    }

    /**
     * @return the partName
     */
    public String getPartName() {
	return partName;
    }

    /**
     * @param partName
     *            the partName to set
     */
    public void setPartName(String partName) {
	this.partName = partName;
    }

    /**
     * @return the partContent
     */
    public byte[] getPartContent() {
	return partContent;
    }

    /**
     * @param partContent
     *            the partContent to set
     */
    public void setPartContent(byte[] partContent) {
	this.partContent = partContent;
    }

    public String getMimeType() {
	return mimeType;
    }

    public void setMimeType(String mimeType) {
	this.mimeType = mimeType;
    }

    /**
     * @return the cid
     */
    public int getCid() {
	return cid;
    }

    /**
     * @param cid
     *            the cid to set
     */
    public void setCid(int cid) {
	this.cid = cid;
    }

    public static final MimePart createFromFile(int cid, File f)
	    throws IOException {
	FileDataSource fds = new FileDataSource(f);
	MimePart retVal = new MimePart(cid, f.getName(), readFileContent(f),
		fds.getContentType());
	return retVal;
    }

    private static byte[] readFileContent(File f) throws IOException {
	byte[] buffer = new byte[2048];
	byte[] data = new byte[0];
	FileInputStream fis = null;
	try {
	    fis = new FileInputStream(f);
	    int countBytes = 0;
	    while ((countBytes = fis.read(buffer)) != -1) {
		byte[] temp = new byte[data.length + countBytes];
		System.arraycopy(data, 0, temp, 0, data.length);
		System.arraycopy(buffer, 0, temp, data.length, countBytes);
		data = temp;
		temp = null;
	    }
	    return data;
	} finally {
	    if (fis != null) {
		fis.close();
	    }
	}
    }

    @Override
    public String toString() {
	return "MimePart [partName=" + partName + ", mimeType=" + mimeType + ", cid=" + cid + "]";
    }
}
