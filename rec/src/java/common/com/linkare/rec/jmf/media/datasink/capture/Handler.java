package com.linkare.rec.jmf.media.datasink.capture;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.media.Buffer;
import javax.media.DataSink;
import javax.media.IncompatibleSourceException;
import javax.media.MediaLocator;
import javax.media.control.BufferControl;
import javax.media.control.FormatControl;
import javax.media.datasink.DataSinkEvent;
import javax.media.datasink.DataSinkListener;
import javax.media.format.AudioFormat;
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

	private NotificationThread notificationThread = new NotificationThread();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.media.MediaHandler#setSource(javax.media.protocol.DataSource)
	 */
	@Override
	public void setSource(DataSource source) throws IOException, IncompatibleSourceException {
		System.out.println("++++++++ SET SOURCE");
		this.dataSource = source;

		if (this.dataSource != null && ContentDescriptor.RAW.equals(this.dataSource.getContentType())
				&& dataSource instanceof PushBufferDataSource) {

			currentTime=System.currentTimeMillis();
			((PushBufferDataSource) dataSource).getStreams()[0].setTransferHandler(this);

			Object[] controls = dataSource.getControls();
			for (Object control : controls) {
				if (control instanceof BufferControl) {
					BufferControl bufferControl = (BufferControl) control;
					// define the buffer as small as possible
					bufferControl.setBufferLength(50L);
				}
				if (control instanceof FormatControl) {
					FormatControl formatControl = (FormatControl) control;
					AudioFormat audioFormat = (AudioFormat) formatControl.getFormat();
					channelData.setAudioFormat(audioFormat);
				}
			}

		} else {
			throw new RuntimeException("Unable to connect to this data source of type " + dataSource.getClass());
		}
		this.dataSource = source;
	}

	long currentTime = -1L;

	@Override
	public void transferData(PushBufferStream stream) {
		try {
			buffer.setData(null);
			stream.read(buffer);

			AudioFormat currentFormat = (AudioFormat) buffer.getFormat();

			double bufferLengthInTime = currentFormat.computeDuration(buffer.getLength()) / 1000000.;

			double timeSinceLastBuffer = System.currentTimeMillis() - currentTime;
			
			if (timeSinceLastBuffer > 2 * bufferLengthInTime ) {
				System.out.println("Time since last acquisition was " + timeSinceLastBuffer
						+ "ms but the buffer transferred only represents " + bufferLengthInTime + "ms");
				
				restartDataSource();
			}

			currentTime = System.currentTimeMillis();

			if (!channelData.setBuffer(buffer)) {
				synchronized (notificationThread) {
					notificationThread.notifyAll();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void restartDataSource() {
		Object[] controls = dataSource.getControls();
		for (Object control : controls) {
			if (control instanceof BufferControl) {
				try {
					dataSource.stop();
					dataSource.start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		System.out.println("++++++++ SET OUTPUT LOCATOR");
		this.outputLocator = output;

	}

	@Override
	public MediaLocator getOutputLocator() {
		return outputLocator;
	}

	@Override
	public void start() throws IOException {
		synchronized (notificationThread) {
			notificationThread.start();
		}

	}

	@Override
	public void stop() throws IOException {
		synchronized (notificationThread) {
			notificationThread.stopNow();
			notificationThread = new NotificationThread();
		}
	}

	@Override
	public void open() throws IOException, SecurityException {
		System.out.println("++++++++ OPENING");

	}

	@Override
	public void close() {
		System.out.println("++++++++ CLOSING");
	}

	@Override
	public String getContentType() {
		System.out.println("++++++++ GET CONTENT TYPE");
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
	 * @param numSamples
	 * @param frequencyInHz the desired frequency in Hz we wish to capture.
	 * @param loadWaveValues flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS. When true, it
	 *            loads only the waves. When false, it loads only the VRMS.
	 * @return
	 */
	public ChannelDataFrame captureFrame(final int numSamples, final double frequencyInHz, final boolean loadWaveValues) {
		return channelData.readDataFrame(numSamples, frequencyInHz, loadWaveValues);
	}

	/**
	 * @return the deltaTime
	 */
	public long getDeltaTime() {
		return this.deltaTime;
	}

	/**
	 * 
	 * @author José Pedro Pereira - Linkare TI
	 */
	public class NotificationThread extends Thread {
		private volatile boolean stop = false;
		private final DataSinkEvent event = new DataSinkEvent(Handler.this, "new.frame.available");

		@Override
		public void run() {
			while (!stop) {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
						stop = true;
					}
				}
				if (!stop) {
					for (DataSinkListener listener : listeners) {
						listener.dataSinkUpdate(event);
					}
				}
			}
		}

		/**
		 * 
		 */
		public void stopNow() {
			stop = true;
			synchronized (this) {
				this.notifyAll();
			}
		}
	}

}
