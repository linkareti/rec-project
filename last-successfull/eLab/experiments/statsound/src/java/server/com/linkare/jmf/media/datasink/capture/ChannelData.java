package com.linkare.jmf.media.datasink.capture;

import java.math.BigInteger;

import javax.media.Buffer;
import javax.media.format.AudioFormat;

public class ChannelData {

    private static final int BYTE_11111111 = 255;
    private static final int BITS_PER_BYTE = 8;

    private long[][] channelsData=new long[0][0];
    private double[] channelsVRMS;

    private long[][] tempData;
    private double[] tempVRMS;

    public ChannelData() {

    }

    public void fromBuffer(Buffer buffer) {
	byte[] data = new byte[buffer.getLength()];
	System.arraycopy(buffer.getData(), buffer.getOffset(), data, 0, buffer.getLength());
	AudioFormat audioFormat = (AudioFormat) buffer.getFormat();
	final int numChannels = audioFormat.getChannels();
	final int sizeOfSampleInBytes = audioFormat.getSampleSizeInBits() / BITS_PER_BYTE;
	allocateChannelsData(numChannels, data.length);
	final boolean signed = audioFormat.getSigned() == AudioFormat.SIGNED;
	final boolean bigEndian = audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;
	calculateChannelData(data, sizeOfSampleInBytes, numChannels, signed, bigEndian);
	
    }

    private void calculateChannelData(byte[] data, int sizeOfSampleInBytes, int numChannels, boolean signed, boolean bigEndian) {
	final int sampleIncrementOffset = sizeOfSampleInBytes * numChannels;
	byte[] sampleData = new byte[sizeOfSampleInBytes];

	for (int channel = 0; channel < numChannels; channel++) {
	    double accumulatedPower = 0;

	    for (int sampleNr = 0; sampleNr < data.length; sampleNr += sampleIncrementOffset) {
		System.arraycopy(data, sampleNr + channel * sizeOfSampleInBytes, sampleData, 0, sampleData.length);
		long sampleValue = bigEndian ? fromBigEndian(sampleData, signed) : fromLittleEndian(sampleData, signed);
		tempData[channel][sampleNr] = sampleValue;
		accumulatedPower += sampleValue * sampleValue;
	    }
	    accumulatedPower = Math.sqrt(accumulatedPower) / tempData[channel].length;
	    tempVRMS[channel] = Math.log(accumulatedPower);
	}
	synchronized (channelsData) {
	    channelsData = tempData;
	    channelsVRMS = tempVRMS;
	}
    }

    private void allocateChannelsData(int numChannels, int length) {
	tempData = new long[numChannels][length];
	tempVRMS = new double[numChannels];
    }

    private static long fromLittleEndian(byte[] data, boolean signed) {
	long val = 0;
	long signingDisplacement = signed ? Byte.MIN_VALUE : 0;
	for (int i = 0; i < data.length; i++) {
	    // on little endian encoding,
	    int positionalByteShift = i * BITS_PER_BYTE;
	    // sign as required
	    long currentByte = data[i] - signingDisplacement;
	    // keep only the latest 8 bits from each block of 8 bits (byte)
	    currentByte = currentByte & BYTE_11111111;
	    //sum it to the running value
	    val = val | currentByte << positionalByteShift;
	}
	return val;
    }

    private static long fromBigEndian(byte[] data, boolean signed) {
	long val = 0;
	int maxI = data.length - 1;
	long signingDisplacement = signed ? Byte.MIN_VALUE : 0;
	for (int i = 0; i < data.length; i++) {
	    // on big endian encoding, positional shift is reverse ordered
	    int positionalByteShift = (maxI - i) * BITS_PER_BYTE;
	    // sign as required
	    long currentByte = data[i] - signingDisplacement;
	    // keep only the latest 8 bits from each block of 8 bits (byte)
	    currentByte=currentByte & BYTE_11111111;
	    //sum it to the running value
	    val = val | (currentByte << positionalByteShift);
	}
	return val;
    }

    public ChannelDataFrame readDataFrame(){
	synchronized (channelsData) {
	    return new ChannelDataFrame(channelsData,channelsVRMS);
	}
    }
    
}
