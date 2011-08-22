package com.linkare.rec.jmf.media.datasink.capture;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.DataSink;
import javax.media.IncompatibleSourceException;
import javax.media.MediaLocator;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.DataSource;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;

/**
 * @author José Pedro Pereira - Linkare TI
 * 
 */
public class Handler implements DataSink, BufferTransferHandler {

	private ChannelData channelData = new ChannelData();

	private DataSource dataSource;

	private MediaLocator outputLocator;

	private List<DataSinkListener> listeners = new Vector<DataSinkListener>();

	private Buffer buffer = new Buffer();

	private long deltaTime = 1000;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.media.MediaHandler#setSource(javax.media.protocol.DataSource)
	 */
	@Override
	public void setSource(DataSource source) throws IOException, IncompatibleSourceException {
		this.dataSource = source;
		if (this.dataSource != null && ContentDescriptor.RAW.equals(this.dataSource.getContentType())
				&& dataSource instanceof PushBufferDataSource) {
			((PushBufferDataSource) dataSource).getStreams()[0].setTransferHandler(this);
		} else {
			throw new RuntimeException("Unable to connect to this data source of type " + dataSource.getClass());
		}
	}

	@Override
	public void transferData(PushBufferStream stream) {
		try {
			buffer.setData(null);
			stream.read(buffer);
			channelData.setBuffer(buffer);
			DataSinkEvent event = new DataSinkEvent(this, "new.frame.available");
			for (DataSinkListener listener : listeners) {
				listener.dataSinkUpdate(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object[] getControls() {
		return null;
	}

	@Override
	public Object getControl(String controlType) {
		return null;
	}

	@Override
	public void setOutputLocator(MediaLocator output) {
		this.outputLocator = output;

	}

	@Override
	public MediaLocator getOutputLocator() {
		return outputLocator;
	}

	@Override
	public void start() throws IOException {
	}

	@Override
	public void stop() throws IOException {
	}

	@Override
	public void open() throws IOException, SecurityException {
	}

	@Override
	public void close() {
	}

	@Override
	public String getContentType() {
		return ContentDescriptor.RAW;
	}

	@Override
	public void addDataSinkListener(DataSinkListener listener) {
		this.listeners.add(listener);
	}

	@Override
	public void removeDataSinkListener(DataSinkListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * 
	 * @param frequencyInHz the desired frequency in Hz we wish to capture.
	 * @param switchToLoadWavesOrVRMS flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS. When true, it
	 *            loads only the waves. When false, it loads only the VRMS.
	 * @return
	 */
	public ChannelDataFrame captureFrame(final double frequencyInHz, final boolean switchToLoadWavesOrVRMS) {
		return channelData.readDataFrame(frequencyInHz, switchToLoadWavesOrVRMS);
	}

	/**
	 * @return the deltaTime
	 */
	public long getDeltaTime() {
		return deltaTime;
	}

}
