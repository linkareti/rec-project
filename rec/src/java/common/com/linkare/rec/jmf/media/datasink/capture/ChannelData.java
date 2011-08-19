package com.linkare.rec.jmf.media.datasink.capture;

import java.util.LinkedList;

import javax.media.Buffer;
import javax.media.format.AudioFormat;

public class ChannelData {

	private static final int BYTE_11111111 = 255;
	private static final int BITS_PER_BYTE = 8;

	private double[][] channelWaveValues = new double[0][0];
	private double[] channelsVRMS;

	private double[][] tempWaveValues;
	private double[] tempVRMS;

	private static final int[] BUFFER_LOCK = new int[0];
	private LinkedList<Buffer> buffers;

	private AudioFormat audioFormat;
	private static double normalizationValue;

	public ChannelData() {
		buffers = new LinkedList<Buffer>();

	}

	public void setBuffer(Buffer buffer) {
		// just hold on to it for later use... if needed!
		synchronized (BUFFER_LOCK) {
			if (buffers.size() >= 5) {
				buffers.remove(0);
			}
			buffers.add(buffer);
		}
	}

	private void calculateChannelData(byte[] data, int sizeOfSampleInBytes, int numChannels, boolean signed,
			boolean bigEndian) {
		final int sampleIncrementOffset = sizeOfSampleInBytes * numChannels;
		byte[] sampleData = new byte[sizeOfSampleInBytes];

		for (int channel = 0; channel < numChannels; channel++) {
			double accumulatedPower = 0;

			int displacementOfSampleForChannelIndex = channel * sizeOfSampleInBytes;

			for (int sampleNr = 0; sampleNr < tempWaveValues[channel].length; sampleNr++) {
				System.arraycopy(data, (sampleNr * sampleIncrementOffset) + displacementOfSampleForChannelIndex,
						sampleData, 0, sampleData.length);
				double sampleValue = bigEndian ? fromBigEndian(sampleData, signed) : fromLittleEndian(sampleData,
						signed);
				// FIXME - just testing stuff
				System.out.println("sampleValue=" + sampleValue);

				tempWaveValues[channel][sampleNr] = sampleValue;
				accumulatedPower += sampleValue * sampleValue;
			}
			double meanPower = accumulatedPower / (double) tempWaveValues[channel].length;
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
		byte[] data = null;
		synchronized (BUFFER_LOCK) {
			int totalLenOfBuffers = 0;
			for (Buffer b : buffers) {
				totalLenOfBuffers += b.getLength();
			}
			data = new byte[totalLenOfBuffers];
			int offSetData = 0;
			for (Buffer b : buffers) {
				System.arraycopy(b.getData(), b.getOffset(), data, offSetData, b.getLength());
				offSetData += b.getLength();
			}

			audioFormat = (AudioFormat) buffers.get(0).getFormat();
			normalizationValue = Math.pow(2, audioFormat.getSampleSizeInBits() - 1);
		}

		final int numChannels = audioFormat.getChannels();
		final int sizeOfSampleInBytes = audioFormat.getSampleSizeInBits() / BITS_PER_BYTE;
		allocateChannelsData(numChannels, sizeOfSampleInBytes, data.length);
		final boolean signed = audioFormat.getSigned() == AudioFormat.SIGNED;
		final boolean bigEndian = audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;
		calculateChannelData(data, sizeOfSampleInBytes, numChannels, signed, bigEndian);
		return new ChannelDataFrame(channelWaveValues, channelsVRMS, audioFormat);
	}

}