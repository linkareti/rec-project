package com.linkare.rec.jmf.media.protocol.function;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.media.Buffer;
import javax.media.Duration;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.Time;
import javax.media.control.FormatControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PullBufferDataSource;
import javax.media.protocol.PullBufferStream;
import javax.media.protocol.SourceStream;

public class DataSource extends PullBufferDataSource {

	private static final int BITS_PER_BYTE = 8;

	private static final int NUM_CHANNELS_DEFAULT = 2;

	private static final int ENDIAN_DEFAULT = AudioFormat.LITTLE_ENDIAN;

	private static final int SIGNED_DEFAULT = AudioFormat.SIGNED;

	AudioFormat audioFormat = null;
	private int numChannels;
	private int sampleSizeInBytes;
	private double sampleRate;

	private FunctorType functorType = FunctorType.SILENCE;

	private FunctionWavePullBufferStream pullSourceStream = null;
	private PullBufferStream[] pullSourceStreams = new PullBufferStream[1];

	public Object[] controls = new Object[0];

	private FunctorDataControl functorDataControl = new FunctorDataControl();

	private static final Logger LOGGER = Logger.getLogger(DataSource.class.getName());

	public DataSource() {
	}

	@Override
	public PullBufferStream[] getStreams() {
		LOGGER.fine("Returning streams " + (pullSourceStreams != null ? pullSourceStreams.length : 0));
		if (pullSourceStreams != null) {
			for (PullBufferStream pullSourceStream : pullSourceStreams) {
				if (pullSourceStream != null) {
					LOGGER.fine("Stream " + pullSourceStream.getClass().getName());
				}
			}
		}
		return this.pullSourceStreams;
	}

	@Override
	public void connect() throws IOException {
		initCheck();
		parseLocator();
	}

	private void parseLocator() {
		MediaLocator locator = getLocator();
		// "function://44100/16/sin/10.2/30"
		String options = locator.getRemainder().substring("//".length());
		StringTokenizer tokenizer = new StringTokenizer(options, "/", false);
		if (tokenizer.countTokens() < 3) {
			throw new RuntimeException("Locator not correctly defined " + locator);
		}
		String sampleRateStr = tokenizer.nextToken();
		String bitsPerChannelStr = tokenizer.nextToken();
		String functorTypeStr = tokenizer.nextToken();
		StringBuilder functorOptions = new StringBuilder();
		while (tokenizer.hasMoreTokens()) {
			functorOptions.append("/").append(tokenizer.nextToken());
		}

		try {
			sampleRate = Integer.parseInt(sampleRateStr);
		} catch (Exception e) {
			throw new RuntimeException("Sample Rate is not a valid integer " + sampleRateStr + " on locator " + locator);
		}
		try {
			int bitsPerChannel = Integer.parseInt(bitsPerChannelStr);
			validateBitsPerChannel(locator, bitsPerChannelStr, bitsPerChannel);
			validateSampleSizeInBytes(locator, bitsPerChannelStr, bitsPerChannel);
			this.sampleSizeInBytes = bitsPerChannel / BITS_PER_BYTE;

		} catch (NumberFormatException e) {
			throw new RuntimeException("Bits Per Channel is not a valid integer " + bitsPerChannelStr + " on locator "
					+ locator);
		}

		FunctorType functor = FunctorType.getByFunctionName(functorTypeStr);
		if (functor == null)
			throw new RuntimeException("Function is not one of the valid choices " + functorTypeStr + " on locator "
					+ locator);

		setFunctorType(functor);

		this.functorType.parseOptions(functorOptions.toString());

		this.numChannels = NUM_CHANNELS_DEFAULT;

		audioFormat = new AudioFormat(AudioFormat.LINEAR, sampleRate, sampleSizeInBytes * BITS_PER_BYTE,
				NUM_CHANNELS_DEFAULT, ENDIAN_DEFAULT, SIGNED_DEFAULT);

		FormatControl formatControl = new FunctionWaveFormatControl(this);

		FunctorTypeControl functorControl = new FunctorTypeControl(this);

		controls = new Object[] { formatControl, functorControl, functorDataControl };

		pullSourceStream = new FunctionWavePullBufferStream();
		pullSourceStreams[0] = pullSourceStream;

	}

	private void validateSampleSizeInBytes(MediaLocator locator, String bitsPerChannelStr, int bitsPerChannel) {
		int sampleSizeInBytes = bitsPerChannel / BITS_PER_BYTE;
		if (sampleSizeInBytes < 0 || sampleSizeInBytes > 2) {
			throw new RuntimeException("Bits Per Channel is not a valid bitsize " + bitsPerChannelStr + " on locator "
					+ locator);
		}
	}

	private void validateBitsPerChannel(MediaLocator locator, String bitsPerChannelStr, int bitsPerChannel) {
		if (bitsPerChannel % BITS_PER_BYTE != 0)
			throw new RuntimeException("Bits Per Channel is not a valid bitsize " + bitsPerChannelStr + " on locator "
					+ locator);
	}

	@Override
	public void disconnect() {
	}

	@Override
	public String getContentType() {
		return ContentDescriptor.RAW;
	}

	@Override
	public Object getControl(String controlType) {
		return null;
	}

	@Override
	public Object[] getControls() {
		return controls;
	}

	@Override
	public Time getDuration() {
		return Duration.DURATION_UNBOUNDED;
	}

	@Override
	public void start() throws IOException {
	}

	@Override
	public void stop() throws IOException {
	}

	private class FunctionWavePullBufferStream implements PullBufferStream {

		private ByteBuffer generatingBuffer = null;

		private int sizeOfGeneratingArray;

		private int bufferSequenceNr;

		double multiplyFactor = 1.;

		private int incrementOnArray = 1;

		/**
		 * Creates the <code>DataSource.FunctionWavePullBufferStream</code>.
		 */
		public FunctionWavePullBufferStream() {
			int sampleSizeInBits = DataSource.this.audioFormat.getSampleSizeInBits();
			sizeOfGeneratingArray = (int) (DataSource.this.sampleSizeInBytes * DataSource.this.numChannels * (int) (DataSource.this.sampleRate / 10));
			LOGGER.fine("Size of generating array : " + sizeOfGeneratingArray);
			generatingBuffer = ByteBuffer.allocate(sizeOfGeneratingArray);
			// The functors generate a value between -1 and 1, and this
			// value should be multiplied by the
			// maximum generation value for the wave quantization in
			// place... as the wave is signed, there are
			// the scale is divided by 2 (positives and negatives - hence
			// the sampleSizeInBits-1 in exponent)
			// but the positive values include the 0, hence the -1
			// subtracted from the power
			multiplyFactor = Math.pow(2, sampleSizeInBits - 1) - 1;
			// but now, we don't want to overflow, so let's decrease this
			// multiplyFactor by 10% (only using 90% of the available
			// scale), which should make us generate
			// sound in between a nice scaled value
			multiplyFactor *= 0.9;

			boolean bigEndian = DataSource.this.audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;
			incrementOnArray = DataSource.this.sampleSizeInBytes * DataSource.this.numChannels;

			generatingBuffer.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);

		}

		@Override
		public boolean endOfStream() {
			return false;
		}

		@Override
		public ContentDescriptor getContentDescriptor() {
			return null;
		}

		@Override
		public long getContentLength() {
			return SourceStream.LENGTH_UNKNOWN;
		}

		@Override
		public Object getControl(String controlType) {

			Class<?> controlClass;
			try {
				controlClass = Class.forName(controlType);
			} catch (ClassNotFoundException e) {
				return null;
			}

			for (Object control : getControls()) {
				if (controlClass.isAssignableFrom(control.getClass())) {
					return control;
				}
			}

			return null;
		}

		@Override
		public Object[] getControls() {
			return controls;
		}

		/**
		 * @param generatingBuffer
		 * @param sampleSizeInBytes
		 * @param value
		 */
		private void putOnBuffer(long value) {
			for (int i = 0; i < numChannels; i++) {
				switch (sampleSizeInBytes) {
				case 1:// byte
					generatingBuffer.put((byte) value);
					break;
				case 2:// short
					generatingBuffer.putShort((short) value);
					break;
				case 4:// int
					generatingBuffer.putInt((int) value);
					break;
				case 8:// long
					generatingBuffer.putLong(value);
					break;
				}
			}
		}

		public void calculateNextFrame() {

			final Functor functor;
			final FunctorType functorTypeLocal;
			synchronized (functorType) {
				functorTypeLocal = DataSource.this.functorType;
			}
			LOGGER.fine(">>>>> Generating sound of type " + functorTypeLocal.getFunctionName() + " with frequency "
					+ functorTypeLocal.getFunctorControl().getFrequency());
			functor = functorTypeLocal.getFunctor();

			generatingBuffer.rewind();

			for (int i = 0; i < generatingBuffer.limit(); i += incrementOnArray) {
				long value = (long) Math.floor(functor.getNextValue() * multiplyFactor);
				DataSource.this.functorDataControl.notifyNewValues(value);
				putOnBuffer(value);
			}
			synchronized (functorTypeLocal) {
				functorTypeLocal.notifyAll();
			}

		}

		@Override
		public Format getFormat() {
			return audioFormat;
		}

		@Override
		public void read(Buffer buffer) throws IOException {
			calculateNextFrame();
			buffer.setFormat(audioFormat);
			buffer.setLength(generatingBuffer.capacity());
			buffer.setData(generatingBuffer.array());
			buffer.setDiscard(false);
			buffer.setOffset(0);
			buffer.setSequenceNumber(bufferSequenceNr++);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean willReadBlock() {
			return false;
		}

	}

	public Format getAudioFormat() {
		return audioFormat;
	}

	public FunctorType getFunctorType() {
		return functorType;
	}

	public void setFunctorType(FunctorType functorType) {
		synchronized (this.functorType) {
			FunctorType oldFunctor = this.functorType;
			this.functorType = functorType;
			this.functorType.getFunctor().setTimeDelta(1. / sampleRate);
			try {
				oldFunctor.wait(1000);
			} catch (InterruptedException e) {
			}

			// try {
			// BeanInfo oldFunctorBeanInfo =
			// java.beans.Introspector.getBeanInfo(oldFunctor.getClass());
			// BeanInfo newFunctorBeanInfo =
			// Introspector.getBeanInfo(newFunctor.getClass());
			// Map<String, PropertyDescriptor> newFunctorProperties = new
			// HashMap<String, PropertyDescriptor>(
			// newFunctorBeanInfo.getPropertyDescriptors().length);
			// for (PropertyDescriptor newPropertyDesc :
			// newFunctorBeanInfo.getPropertyDescriptors()) {
			// newFunctorProperties.put(newPropertyDesc.getName(),
			// newPropertyDesc);
			// }
			//
			// for (PropertyDescriptor propertyDesc :
			// oldFunctorBeanInfo.getPropertyDescriptors()) {
			// if (propertyDesc.getReadMethod() != null
			// && newFunctorProperties.containsKey(propertyDesc.getName())) {
			// PropertyDescriptor newPropertyDesc =
			// newFunctorProperties.get(propertyDesc.getName());
			// if (newPropertyDesc.getWriteMethod() != null
			// && newPropertyDesc.getPropertyType() ==
			// propertyDesc.getPropertyType()) {
			// Object oldValue =
			// propertyDesc.getReadMethod().invoke(oldFunctor);
			// newPropertyDesc.getWriteMethod().invoke(newFunctor, oldValue);
			// }
			// }
			// }
			//
			// } catch (IntrospectionException e) {
			// e.printStackTrace();
			// } catch (IllegalArgumentException e) {
			// e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// e.printStackTrace();
			// } catch (InvocationTargetException e) {
			// e.printStackTrace();
			// }

		}

	}

}
