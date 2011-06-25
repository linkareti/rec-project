package com.linkare.rec.jmf.media.protocol.function;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.media.Buffer;
import javax.media.Duration;
import javax.media.Format;
import javax.media.MediaLocator;
import javax.media.Time;
import javax.media.control.FormatControl;
import javax.media.format.AudioFormat;
import javax.media.protocol.BufferTransferHandler;
import javax.media.protocol.ContentDescriptor;
import javax.media.protocol.PushBufferDataSource;
import javax.media.protocol.PushBufferStream;
import javax.media.protocol.SourceStream;

public class DataSource extends PushBufferDataSource {

    private static final int BYTE_11111111 = 255;

    private static final int BITS_PER_BYTE = 8;

    private static final int NUM_CHANNELS_DEFAULT = 2;

    private static final int ENDIAN_DEFAULT = AudioFormat.LITTLE_ENDIAN;

    private static final int SIGNED_DEFAULT = AudioFormat.SIGNED;

    AudioFormat audioFormat = null;
    private int numChannels;
    private int sampleSizeInBytes;
    private double sampleRate;

    private FunctorType functorType = FunctorType.SILENCE;

    private FunctionWavePushBufferStream pushSourceStream = new FunctionWavePushBufferStream();
    private PushBufferStream[] pushSourceStreams = new PushBufferStream[] { pushSourceStream };

    public Object[] controls = new Object[0];

    public DataSource() {
    }

    @Override
    public PushBufferStream[] getStreams() {
	return this.pushSourceStreams;
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
	    throw new RuntimeException("Bits Per Channel is not a valid integer " + bitsPerChannelStr + " on locator " + locator);
	}

	FunctorType functor = FunctorType.getByFunctionName(functorTypeStr);
	if (functor == null)
	    throw new RuntimeException("Function is not one of the valid choices " + functorTypeStr + " on locator " + locator);

	setFunctorType(functor);

	this.functorType.parseOptions(functorOptions.toString());

	this.numChannels = NUM_CHANNELS_DEFAULT;

	audioFormat = new AudioFormat(AudioFormat.LINEAR, sampleRate, sampleSizeInBytes * BITS_PER_BYTE, NUM_CHANNELS_DEFAULT, ENDIAN_DEFAULT, SIGNED_DEFAULT);

	FormatControl formatControl = new FunctionWaveFormatControl(this);

	FunctorTypeControl functorControl = new FunctorTypeControl(this);

	controls = new Object[] { formatControl, functorControl };

    }

    private void validateSampleSizeInBytes(MediaLocator locator, String bitsPerChannelStr, int bitsPerChannel) {
	int sampleSizeInBytes = bitsPerChannel / BITS_PER_BYTE;
	if (sampleSizeInBytes < 0 || sampleSizeInBytes > 2) {
	    throw new RuntimeException("Bits Per Channel is not a valid bitsize " + bitsPerChannelStr + " on locator " + locator);
	}
    }

    private void validateBitsPerChannel(MediaLocator locator, String bitsPerChannelStr, int bitsPerChannel) {
	if (bitsPerChannel % BITS_PER_BYTE != 0)
	    throw new RuntimeException("Bits Per Channel is not a valid bitsize " + bitsPerChannelStr + " on locator " + locator);
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
	this.pushSourceStream.start();
    }

    @Override
    public void stop() throws IOException {
	this.pushSourceStream.stop();
    }

    private class FunctionWavePushBufferStream implements Runnable, PushBufferStream {

	private Thread innerGenerationThread = null;

	private byte[] generatingBuffer = null;

	private volatile boolean stop = false;

	private volatile BufferTransferHandler bufferTransferHandler;

	private int sizeOfGeneratingArray;

	private int bufferSequenceNr;

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

	private void start() {
	    if (innerGenerationThread == null) {
		stop = false;
		innerGenerationThread = new Thread(this);
		innerGenerationThread.start();
	    }
	}

	private void stop() {
	    if (innerGenerationThread != null) {
		stop = true;
		try {
		    if (Thread.currentThread() != innerGenerationThread) {
			innerGenerationThread.join();
		    }
		    innerGenerationThread = null;
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
	    }
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

	@Override
	public void run() {
	    while (!stop) {

		final Functor functor;
		synchronized (functorType) {
		    functor = DataSource.this.functorType.getFunctor();
		}
		int sampleSizeInBits = DataSource.this.audioFormat.getSampleSizeInBits();
		sizeOfGeneratingArray = (int) (DataSource.this.sampleSizeInBytes * DataSource.this.sampleRate / 4 * DataSource.this.numChannels);
		generatingBuffer = new byte[sizeOfGeneratingArray];
		double multiplyFactor = Math.pow(2, sampleSizeInBits) - 1;
		boolean bigEndian = DataSource.this.audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;
		boolean signed = DataSource.this.audioFormat.getSigned() == AudioFormat.SIGNED;
		int incrementOnArray = DataSource.this.sampleSizeInBytes * DataSource.this.numChannels;

		for (int i = 0; i < generatingBuffer.length && !stop; i += incrementOnArray) {
		    long value = (long) Math.floor(functor.getNextValue() * multiplyFactor);
		    // now convert the value according to big ending/little endian
		    byte[] byteArrayValue = bigEndian ? toBigEndian(value, DataSource.this.sampleSizeInBytes, signed)
			    : toLittleEndian(value, DataSource.this.sampleSizeInBytes, signed);
		    //Do not use BigInteger class because toByteArray() method returns the two's complement

		    // Iterate on channels - keeping same value for right or left
		    for (int c = 0; c < DataSource.this.numChannels; c++) {
			int destPos = i + c * DataSource.this.sampleSizeInBytes;

			System.arraycopy(byteArrayValue, 0, generatingBuffer, destPos, DataSource.this.sampleSizeInBytes);
		    }
		}

		if (!stop) {
		    bufferTransferHandler.transferData(this);
		}

	    }

	}

	@Override
	public Format getFormat() {
	    return audioFormat;
	}

	@Override
	public void read(Buffer buffer) throws IOException {
	    buffer.setFormat(audioFormat);
	    buffer.setData(generatingBuffer);
	    buffer.setLength(generatingBuffer.length);
	    buffer.setDiscard(false);
	    buffer.setOffset(0);
	    buffer.setSequenceNumber(bufferSequenceNr++);
	}

	@Override
	public void setTransferHandler(BufferTransferHandler transferHandler) {
	    this.bufferTransferHandler = transferHandler;
	}

    }

    private static byte[] toLittleEndian(long val, int numBytes, boolean signed) {
	byte[] data = new byte[numBytes];
	// if the byte[] data is to be signed, displace it to the lower bound
	// value for a byte...
	long signingDisplacement = signed ? Byte.MIN_VALUE : 0;
	for (int i = 0; i < numBytes; i++) {
	    // on little endian encoding,
	    int positionalByteShift = i * BITS_PER_BYTE;
	    // keep only the latest 8 bits from each block of 8 bits (byte)
	    long currentByte = (val >> positionalByteShift) & BYTE_11111111;
	    // sign as required
	    currentByte = currentByte - signingDisplacement;
	    // cast it to byte
	    data[i] = (byte) currentByte;
	}

	return data;
    }

    private static byte[] toBigEndian(long val, int numBytes, boolean signed) {
	byte[] data = toLittleEndian(val, numBytes, signed);
	byte temp;
	int lenOfTheArray = data.length;
	int uboundOfTheArray = lenOfTheArray - 1;
	// just have to reverse the bytes up to the middle of the array... easy
	// and quick
	for (int positionFromStart = 0, positionFromEnd = uboundOfTheArray; positionFromStart < lenOfTheArray / 2; positionFromStart++, positionFromEnd--) {
	    temp = data[positionFromStart];
	    data[positionFromStart] = data[positionFromEnd];
	    data[positionFromEnd] = temp;
	}
	return data;
    }

    public Format getAudioFormat() {
	return audioFormat;
    }

    public FunctorType getFunctorType() {
	return functorType;
    }

    public void setFunctorType(FunctorType functorType) {
	synchronized (this.functorType) {
	    Functor oldFunctor = this.functorType.getFunctor();
	    this.functorType = functorType;

	    Functor newFunctor = this.getFunctorType().getFunctor();

	    try {
		BeanInfo oldFunctorBeanInfo = java.beans.Introspector.getBeanInfo(oldFunctor.getClass());
		BeanInfo newFunctorBeanInfo = Introspector.getBeanInfo(newFunctor.getClass());
		Map<String, PropertyDescriptor> newFunctorProperties = new HashMap<String, PropertyDescriptor>(
													       newFunctorBeanInfo.getPropertyDescriptors().length);
		for (PropertyDescriptor newPropertyDesc : newFunctorBeanInfo.getPropertyDescriptors()) {
		    newFunctorProperties.put(newPropertyDesc.getName(), newPropertyDesc);
		    System.out.println("New functor property " + newPropertyDesc.getName());
		}

		for (PropertyDescriptor propertyDesc : oldFunctorBeanInfo.getPropertyDescriptors()) {
		    if (propertyDesc.getReadMethod() != null && newFunctorProperties.containsKey(propertyDesc.getName())) {
			PropertyDescriptor newPropertyDesc = newFunctorProperties.get(propertyDesc.getName());
			if (newPropertyDesc.getWriteMethod() != null && newPropertyDesc.getPropertyType() == propertyDesc.getPropertyType()) {
			    Object oldValue = propertyDesc.getReadMethod().invoke(oldFunctor);
			    newPropertyDesc.getWriteMethod().invoke(newFunctor, oldValue);
			}
		    }
		}

	    } catch (IntrospectionException e) {
		e.printStackTrace();
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }

	    this.functorType.getFunctor().setTimeDelta(1. / sampleRate);

	}

    }

}
