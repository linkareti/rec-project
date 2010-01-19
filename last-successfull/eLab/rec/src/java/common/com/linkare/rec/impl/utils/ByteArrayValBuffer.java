/*
 * ByteArrayValBuffer.java
 *
 * Created on 9 de Maio de 2003, 18:25
 */

package com.linkare.rec.impl.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.linkare.rec.data.acquisition.ByteArrayValue;

/*
 import javax.media.protocol.*;
 import javax.media.*;
 */
/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ByteArrayValBuffer {

	/** Holds value of property data. */
	private byte[] data;

	/** Holds value of property mimeType. */
	private String mimeType;

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer() {
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType) {
		setMimeType(mimeType);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, byte[] data) {
		setMimeType(mimeType);
		setData(data);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, String file_loc) throws FileNotFoundException, IOException {
		setMimeType(mimeType);
		readInData(file_loc);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, String file_loc, int startIndex) throws FileNotFoundException,
			IOException {
		setMimeType(mimeType);
		readInData(file_loc, startIndex);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, String file_loc, int startIndex, int endIndex)
			throws FileNotFoundException, IOException {
		setMimeType(mimeType);
		readInData(file_loc, startIndex, endIndex);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, URL url) throws FileNotFoundException, IOException {
		setMimeType(mimeType);
		readInData(url);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, URL url, int startIndex) throws FileNotFoundException, IOException {
		setMimeType(mimeType);
		readInData(url, startIndex);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(String mimeType, URL url, int startIndex, int endIndex) throws FileNotFoundException,
			IOException {
		setMimeType(mimeType);
		readInData(url, startIndex, endIndex);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(URL url) throws FileNotFoundException, IOException {
		setMimeType(url.openConnection().getContentType());
		readInData(url);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(URL url, int startIndex) throws FileNotFoundException, IOException {
		setMimeType(url.openConnection().getContentType());
		readInData(url, startIndex);
	}

	/** Creates a new instance of ByteArrayValBuffer */
	public ByteArrayValBuffer(URL url, int startIndex, int endIndex) throws FileNotFoundException, IOException {
		setMimeType(url.openConnection().getContentType());
		readInData(url, startIndex, endIndex);
	}

	/*
	 * public ByteArrayValBuffer(PullSourceStream pss,int startIndex,int
	 * endIndex) { setMimeType(pss.getContentDescriptor().getContentType());
	 * data=new byte[pss.]; pss.read(data,0,data.length); }
	 * 
	 * public ByteArrayValBuffer(PushSourceStream pss) {
	 * setMimeType(pss.getContentDescriptor().getContentType()); data=new
	 * byte[pss.getContentLength()]; pss.read(data,0,data.length); }
	 */

	/**
	 * Indexed getter for property data.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public byte getData(int index) {
		return this.data[index];
	}

	/**
	 * Getter for property data.
	 * 
	 * @return Value of property data.
	 */
	public byte[] getData() {
		return this.data;
	}

	/**
	 * Indexed setter for property data.
	 * 
	 * @param index Index of the property.
	 * @param data New value of the property at <CODE>index</CODE>.
	 */
	public void setData(int index, byte data) {
		this.data[index] = data;
	}

	/**
	 * Setter for property data.
	 * 
	 * @param data New value of property data.
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Getter for property mimeType.
	 * 
	 * @return Value of property mimeType.
	 */
	public String getMimeType() {
		return this.mimeType;
	}

	/**
	 * Setter for property mimeType.
	 * 
	 * @param mimeType New value of property mimeType.
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	private void readInData(URL url) throws FileNotFoundException, IOException {
		URLConnection con = url.openConnection();
		java.io.InputStream is = con.getInputStream();
		readInData(is, 0, is.available());
	}

	private void readInData(URL url, int startIndex) throws FileNotFoundException, IOException {
		URLConnection con = url.openConnection();
		java.io.InputStream is = con.getInputStream();
		readInData(is, startIndex, is.available());
	}

	private void readInData(URL url, int startIndex, int endIndex) throws FileNotFoundException, IOException {
		URLConnection con = url.openConnection();
		java.io.InputStream is = con.getInputStream();
		readInData(is, startIndex, endIndex);
	}

	private void readInData(String file_loc) throws FileNotFoundException, IOException {
		java.io.InputStream is = new FileInputStream(file_loc);
		readInData(is, 0, is.available());
	}

	private void readInData(String file_loc, int startIndex) throws FileNotFoundException, IOException {
		java.io.InputStream is = new FileInputStream(file_loc);
		readInData(is, startIndex, is.available());
	}

	private void readInData(String file_loc, int startIndex, int endIndex) throws FileNotFoundException, IOException {
		java.io.InputStream is = new FileInputStream(file_loc);
		readInData(is, startIndex, endIndex);
	}

	private void readInData(InputStream is, int startIndex) throws FileNotFoundException, IOException {
		readInData(is, startIndex, is.available());
	}

	private void readInData(InputStream is, int startIndex, int endIndex) throws FileNotFoundException, IOException {
		data = new byte[endIndex - startIndex];
		is.read(data, startIndex, endIndex - startIndex);
	}

	public ByteArrayValue getByteArrayValue() {
		return new ByteArrayValue(getData(), getMimeType());
	}

}
