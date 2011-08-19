package com.linkare.rec.jmf.media.datasink.capture;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.media.Buffer;
import javax.media.format.AudioFormat;

public class ChannelData {

	private static final int BYTE_11111111 = 255;
	private static final int BITS_PER_BYTE = 8;
	private static final int NUMBER_OF_CHANNELS = 2;

	private double[][] channelWaveValues = new double[0][0];
	private double[] channelsVRMS;

	private double[][] tempWaveValues;
	private double[] tempVRMS;

	private static final int[] BUFFER_LOCK = new int[0];
	private Buffer buffer;

	private AudioFormat audioFormat;
	private static double normalizationValue;

	private static final Logger LOGGER = Logger.getLogger(ChannelData.class.getName());
	private static final boolean LOGGING_IS_AT_FINE_LEVEL = LOGGER.isLoggable(Level.FINE);

	public ChannelData() {

	}

	public void setBuffer(Buffer buffer) {
		// just hold on to it for later use... if needed!
		synchronized (BUFFER_LOCK) {
			this.buffer = buffer;
		}
	}

	private void calculateChannelData(byte[] data, int sizeOfSampleInBytes, int numChannels, boolean signed,
			boolean bigEndian) {
		final int sampleIncrementOffset = sizeOfSampleInBytes * numChannels;
		byte[] sampleData = new byte[sizeOfSampleInBytes];

		for (int channel = 0; channel < numChannels; channel++) {
			double accumulatedPower = 0;

			for (int sampleNr = 0; sampleNr < tempWaveValues.length; sampleNr += 1) {
				System.arraycopy(data, sampleNr*sampleIncrementOffset + channel * sizeOfSampleInBytes, sampleData, 0, sampleData.length);
				double sampleValue = bigEndian ? fromBigEndian(sampleData, signed) : fromLittleEndian(sampleData,
						signed);
				tempWaveValues[channel][sampleNr] = sampleValue;
				accumulatedPower += sampleValue * sampleValue;
			}
			double meanPower = accumulatedPower / (double) tempWaveValues.length;
			tempVRMS[channel] = 10 * Math.log10(meanPower);
		}
		channelWaveValues = tempWaveValues;
		channelsVRMS = tempVRMS;
	}

	private void allocateChannelsData(int numChannels, int sampleSizeInBytes, int lengthInBytes) {
		tempWaveValues = new double[numChannels][lengthInBytes / (numChannels * sampleSizeInBytes)];
		tempVRMS = new double[numChannels];
	}

	private static double fromLittleEndian(byte[] data, boolean signed) {
		long val = 0;
		long signingDisplacement = signed ? Byte.MIN_VALUE : 0;
		for (int i = 0; i < data.length; i++) {
			// on little endian encoding,
			int positionalByteShift = i * BITS_PER_BYTE;
			// sign as required
			long currentByte = data[i] - signingDisplacement;
			// keep only the latest 8 bits from each block of 8 bits (byte)
			currentByte = currentByte & BYTE_11111111;
			// sum it to the running value
			val = val | currentByte << positionalByteShift;
		}
		return convertToScale(val);
	}

	private static double fromBigEndian(byte[] data, boolean signed) {
		long val = 0;
		int maxI = data.length - 1;
		long signingDisplacement = signed ? Byte.MIN_VALUE : 0;
		for (int i = 0; i < data.length; i++) {
			// on big endian encoding, positional shift is reverse ordered
			int positionalByteShift = (maxI - i) * BITS_PER_BYTE;
			// sign as required
			long currentByte = data[i] - signingDisplacement;
			// keep only the latest 8 bits from each block of 8 bits (byte)
			currentByte = currentByte & BYTE_11111111;
			// sum it to the running value
			val = val | (currentByte << positionalByteShift);
		}
		return convertToScale(val);
	}

	private static double convertToScale(long val) {
		// bring the initial value to the 0 in terms of scale
		double result = val - normalizationValue;
		// normalize the value to the -1 to 1 scale
		result /= normalizationValue;
		return result;
	}

	public ChannelDataFrame readDataFrame() {
		byte[] data = new byte[buffer.getLength()];
		synchronized (BUFFER_LOCK) {
			System.arraycopy(buffer.getData(), buffer.getOffset(), data, 0, buffer.getLength());
			audioFormat = (AudioFormat) buffer.getFormat();
			normalizationValue = Math.pow(2, audioFormat.getSampleSizeInBits() - 1);
			if (LOGGING_IS_AT_FINE_LEVEL) {
				LOGGER.fine("Timestamp of buffer :" + buffer.getTimeStamp() + " - "
						+ toDeltaTime(buffer.getTimeStamp()));
			}
		}

		final int numChannels = audioFormat.getChannels();
		final int sizeOfSampleInBytes = audioFormat.getSampleSizeInBits() / BITS_PER_BYTE;
		allocateChannelsData(numChannels, sizeOfSampleInBytes, data.length);
		final boolean signed = audioFormat.getSigned() == AudioFormat.SIGNED;
		final boolean bigEndian = audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;
		calculateChannelData(data, sizeOfSampleInBytes, numChannels, signed, bigEndian);
		return new ChannelDataFrame(channelWaveValues, channelsVRMS, audioFormat);
	}

	/**
	 * @param timeStamp
	 * @return
	 */
	private String toDeltaTime(long timeStamp) {
		StringBuilder sb=new StringBuilder();
		double timeStampPrecision=timeStamp;
		
		long nanos=(long)(timeStampPrecision/1000. - timeStamp/1000)*1000;
		timeStamp=timeStamp/1000;
		timeStampPrecision=timeStamp;
		
		long micros=(long)(timeStampPrecision/1000. - timeStamp/1000)*1000;
		timeStamp=timeStamp/1000;
		timeStampPrecision=timeStamp;
		
		long millis=(long)(timeStampPrecision/1000. - timeStamp/1000)*1000;
		
		timeStamp=timeStamp/1000;
		timeStampPrecision=timeStamp;
		
		long hours=timeStamp/60*60;
		long minutes=(timeStamp-hours*60*60)/60;
		long seconds=timeStamp-hours*60*60-minutes*60;
		
		sb.append(hours).append(":").append(minutes).append(":").append(seconds).append(".").append(millis).append(".").append(micros).append(".").append(nanos);
		return sb.toString();
	}
}