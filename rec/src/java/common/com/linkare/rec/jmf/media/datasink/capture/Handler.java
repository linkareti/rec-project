package com.linkare.rec.jmf.media.datasink.capture;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	private boolean printed = false;

	private Buffer buffer = new Buffer();

	private static int receivedBufferNumber = 0;

	private static final Logger LOGGER = Logger.getLogger(Handler.class.getName());

	private static final boolean LOGGING_IS_AT_FINE_LEVEL = LOGGER.isLoggable(Level.FINE);

	private static final Map<Integer, String> ALL_BUFFER_FLAGS = new HashMap<Integer, String>();
	static {
		Field[] allBufferFields = Buffer.class.getDeclaredFields();
		for (Field field : allBufferFields) {
			if (field.getName().startsWith("FLAG_")) {
				try {
					Integer fieldValue = field.getInt(null);
					ALL_BUFFER_FLAGS.put(fieldValue, field.getName());
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				}
			}
		}
	}

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

			if (LOGGING_IS_AT_FINE_LEVEL) {
				LOGGER.fine("Buffer Nr " + (receivedBufferNumber++));
				LOGGER.fine("Seq Nr of buffer[" + (receivedBufferNumber) + "]:" + buffer.getSequenceNumber());
				LOGGER.fine("Length of buffer[" + (receivedBufferNumber) + "]:" + buffer.getLength());
				LOGGER.fine("Offset of buffer[" + (receivedBufferNumber) + "]:" + buffer.getOffset());
				LOGGER.fine("Timestamp of buffer[" + (receivedBufferNumber) + "]:" + buffer.getTimeStamp());
				LOGGER.fine("Duration of buffer[" + (receivedBufferNumber) + "]:" + buffer.getDuration());
				
				LOGGER.fine("IsDiscard on buffer [" + (receivedBufferNumber) + "]:" + buffer.isDiscard());
				LOGGER.fine("IsEOM on buffer [" + (receivedBufferNumber) + "]:" + buffer.isEOM());
				
				LOGGER.fine("Flags of buffer[" + (receivedBufferNumber) + "]:" + expressBufferFlags(buffer.getFlags()));
			}

			channelData.setBuffer(buffer);
			DataSinkEvent event = new DataSinkEvent(this, "new.frame.available");
			for (DataSinkListener listener : listeners) {
				listener.dataSinkUpdate(event);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param flags
	 * @return
	 */
	private String expressBufferFlags(int flags) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Integer, String> flagEntry : ALL_BUFFER_FLAGS.entrySet()) {
			if ((flagEntry.getKey().intValue() & flags) == flagEntry.getKey().intValue()) {
				sb.append(flagEntry.getValue()).append("|");
			}
		}
		return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
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

	public ChannelDataFrame captureFrame() {
		return channelData.readDataFrame();
	}

}
