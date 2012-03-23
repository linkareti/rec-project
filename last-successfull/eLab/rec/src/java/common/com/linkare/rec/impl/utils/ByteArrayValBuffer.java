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

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 */
	public ByteArrayValBuffer(final String mimeType) {
		setMimeType(mimeType);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param data
	 */
	public ByteArrayValBuffer(final String mimeType, final byte[] data) {
		setMimeType(mimeType);
		setData(data);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param fileLocation
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final String mimeType, final String fileLocation) throws IOException {
		setMimeType(mimeType);
		readInData(fileLocation);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param fileLocation
	 * @param startIndex
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final String mimeType, final String fileLocation, final int startIndex)
			throws IOException {
		setMimeType(mimeType);
		readInData(fileLocation, startIndex);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param fileLocation
	 * @param startIndex
	 * @param endIndex
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final String mimeType, final String fileLocation, final int startIndex, final int endIndex)
			throws IOException {
		setMimeType(mimeType);
		readInData(fileLocation, startIndex, endIndex);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param url
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final String mimeType, final URL url) throws IOException {
		setMimeType(mimeType);
		readInData(url);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param url
	 * @param startIndex
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final String mimeType, final URL url, final int startIndex) throws IOException {
		setMimeType(mimeType);
		readInData(url, startIndex);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param mimeType
	 * @param url
	 * @param startIndex
	 * @param endIndex
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final String mimeType, final URL url, final int startIndex, final int endIndex)
			throws FileNotFoundException, IOException {
		setMimeType(mimeType);
		readInData(url, startIndex, endIndex);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param url
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final URL url) throws IOException {
		setMimeType(url.openConnection().getContentType());
		readInData(url);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param url
	 * @param startIndex
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final URL url, final int startIndex) throws IOException {
		setMimeType(url.openConnection().getContentType());
		readInData(url, startIndex);
	}

	/**
	 * Creates a new instance of ByteArrayValBuffer
	 * 
	 * @param url
	 * @param startIndex
	 * @param endIndex
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ByteArrayValBuffer(final URL url, final int startIndex, final int endIndex) throws IOException {
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
	public byte getData(final int index) {
		return data[index];
	}

	/**
	 * Getter for property data.
	 * 
	 * @return Value of property data.
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Indexed setter for property data.
	 * 
	 * @param index Index of the property.
	 * @param data New value of the property at <CODE>index</CODE>.
	 */
	public void setData(final int index, final byte data) {
		this.data[index] = data;
	}

	/**
	 * Setter for property data.
	 * 
	 * @param data New value of property data.
	 */
	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public final void setData(final byte[] data) {
		this.data = data;
	}

	/**
	 * Getter for property mimeType.
	 * 
	 * @return Value of property mimeType.
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * Setter for property mimeType.
	 * 
	 * @param mimeType New value of property mimeType.
	 */
	public final void setMimeType(final String mimeType) {
		this.mimeType = mimeType;
	}

	private final void readInData(final URL url) throws IOException {
		final URLConnection con = url.openConnection();
		final java.io.InputStream is = con.getInputStream();
		readInData(is, 0, is.available());
	}

	private void readInData(final URL url, final int startIndex) throws IOException {
		final URLConnection con = url.openConnection();
		final java.io.InputStream is = con.getInputStream();
		readInData(is, startIndex, is.available());
	}

	private void readInData(final URL url, final int startIndex, final int endIndex) throws IOException {
		final URLConnection con = url.openConnection();
		final java.io.InputStream is = con.getInputStream();
		readInData(is, startIndex, endIndex);
	}

	private void readInData(final String fileLocation) throws IOException {
		final java.io.InputStream is = new FileInputStream(fileLocation);
		readInData(is, 0, is.available());
	}

	private void readInData(final String fileLocation, final int startIndex) throws IOException {
		final java.io.InputStream is = new FileInputStream(fileLocation);
		readInData(is, startIndex, is.available());
	}

	private void readInData(final String fileLocation, final int startIndex, final int endIndex)
			throws FileNotFoundException, IOException {
		final java.io.InputStream is = new FileInputStream(fileLocation);
		readInData(is, startIndex, endIndex);
	}

	private void readInData(final InputStream is, final int startIndex) throws IOException {
		readInData(is, startIndex, is.available());
	}

	private void readInData(final InputStream is, final int startIndex, final int endIndex)
			throws FileNotFoundException, IOException {
		data = new byte[endIndex - startIndex];
		is.read(data, startIndex, endIndex - startIndex);
	}

	public ByteArrayValue getByteArrayValue() {
		return new ByteArrayValue(getData(), getMimeType());
	}

}
