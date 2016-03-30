package com.linkare.rec.web.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.activation.FileDataSource;

public class Attachment implements Serializable {

	private static final long serialVersionUID = 7985615688285737975L;

	private String fileName;
	private byte[] fileContent;
	private String mimeType;

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public Attachment(String fileName, byte[] fileContent, String mimeType) {
		super();
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.mimeType = mimeType;
	}

	public Attachment() {
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * @param fileContent
	 *            the fileContent to set
	 */
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public static final Attachment createFromFile(File f) throws IOException {
		FileDataSource fds = new FileDataSource(f);
		Attachment retVal = new Attachment(f.getName(), readFileContent(f),
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
		return "Attachment [fileName=" + fileName + ", mimeType=" + mimeType
				+ "]";
	}
}
