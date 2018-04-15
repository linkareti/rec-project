/*
 * SamplesPacketMatrixIO.java
 *
 * Created on 5 de Janeiro de 2004, 17:59
 */

package com.linkare.rec.impl.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.linkare.rec.data.acquisition.SamplesPacket;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class SamplesPacketMatrixIO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2275422019247210861L;
	private SequencedObjectIO ioDelegate = null;

	public SamplesPacketMatrixIO() throws IOException {
		ioDelegate = new SequencedObjectIO();
	}

	/**
	 * Getter for property file.
	 * 
	 * @return Value of property file.
	 * @throws IOException 
	 * 
	 */
	public File getFile() throws IOException {
		return ioDelegate.getFile();
	}

	/**
	 * Setter for property file.
	 * 
	 * @param file New value of property file.
	 * @throws IOException 
	 * 
	 */
	public void setFile(final File file) throws IOException {
		ioDelegate.setFile(file);
	}

	public SamplesPacket[] getSamplesPackets(final int packetStart, final int packetEnd)
			throws SamplesPacketReadException {
		try {
			final Object[] oRead = ioDelegate.readObjects(packetStart, packetEnd);
			final SamplesPacket[] retVal = new SamplesPacket[packetEnd - packetStart + 1];
			System.arraycopy(oRead, 0, retVal, 0, retVal.length);
			return retVal;
		} catch (final SequencedObjectReadException e) {
			throw new SamplesPacketReadException(e, e.getErrorIndex());
		}
	}

	public void write(final SamplesPacket[] packets) throws IOException {
		ioDelegate.writeObjects(packets);
	}

	public void write(final Object[] packets) throws IOException {
		ioDelegate.writeObjects(packets);
	}

	public void write(final List packets) throws IOException {
		ioDelegate.writeObjects(packets.toArray());
	}

	public int size() {
		return ioDelegate.size();
	}

}
