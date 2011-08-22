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
	private int length;

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

	/**
	 * 
	 * @param data
	 * @param sizeOfSampleInBytes
	 * @param numChannels
	 * @param signed
	 * @param bigEndian
	 * @param overSampleDisplacement
	 * @param loadWaveValues flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS.
	 */
	private void calculateChannelData(final byte[] data, final int sizeOfSampleInBytes, final int numChannels,
			final boolean signed, final boolean bigEndian, final int overSampleDisplacement,
			final boolean loadWaveValues) {
		final int sampleIncrementOffset = sizeOfSampleInBytes * numChannels;
		byte[] sampleData = new byte[sizeOfSampleInBytes];

		for (int channel = 0; channel < numChannels; channel++) {
			double accumulatedPower = 0;

			int displacementOfSampleForChannelIndex = channel * sizeOfSampleInBytes;

			for (int sampleNr = 0; sampleNr < length; sampleNr++) {
				System.arraycopy(data, (sampleNr * sampleIncrementOffset) + displacementOfSampleForChannelIndex
						+ (sampleNr * overSampleDisplacement), sampleData, 0, sampleData.length);
				double sampleValue = bigEndian ? fromBigEndian(sampleData, signed) : fromLittleEndian(sampleData,
						signed);
				if (loadWaveValues) {
					tempWaveValues[channel][sampleNr] = sampleValue;
				} else {
					accumulatedPower += sampleValue * sampleValue;
				}
			}
			if (!loadWaveValues) {
				double meanPower = accumulatedPower / (double) tempWaveValues[channel].length;
				tempVRMS[channel] = 10 * Math.log10(meanPower);
			}
		}
		channelWaveValues = tempWaveValues;
		channelsVRMS = tempVRMS;
	}

	/**
	 * 
	 * @param numChannels
	 * @param sampleSizeInBytes
	 * @param lengthInBytes
	 * @param overSampleDisplacement
	 * @param loadWaveValues flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS.
	 */
	private void allocateChannelsData(final int numChannels, final int sampleSizeInBytes, final int lengthInBytes,
			final int overSampleDisplacement, final boolean loadWaveValues) {
		length = (lengthInBytes / (numChannels * sampleSizeInBytes)) / overSampleDisplacement;
		if (loadWaveValues) {
			tempWaveValues = new double[numChannels][length];
		} else {
			tempVRMS = new double[numChannels];
		}
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

	/**
	 * 
	 * @param switchToLoadWavesOrVRMS the desired frequency in Hz we wish to
	 *            capture.
	 * @param loadWaveValues flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS. When true, it
	 *            loads only the waves. When false, it loads only the VRMS.
	 * @return
	 */
	public ChannelDataFrame readDataFrame(final double switchToLoadWavesOrVRMS, final boolean loadWaveValues) {
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
		final int overSampleDisplacement = (int) (audioFormat.getSampleRate() / switchToLoadWavesOrVRMS);
		allocateChannelsData(numChannels, sizeOfSampleInBytes, data.length, overSampleDisplacement, loadWaveValues);
		final boolean signed = audioFormat.getSigned() == AudioFormat.SIGNED;
		final boolean bigEndian = audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;
		calculateChannelData(data, sizeOfSampleInBytes, numChannels, signed, bigEndian, overSampleDisplacement,
				loadWaveValues);
		return new ChannelDataFrame(channelWaveValues, channelsVRMS, audioFormat);
	}

}