package com.linkare.rec.jmf.media.datasink.capture;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.media.Buffer;
import javax.media.format.AudioFormat;

import com.linkare.rec.jmf.ReCJMFUtils;

public class ChannelData {

	private static final int BITS_PER_BYTE = 8;

	private double[][] channelWaveValues = null;
	private double[] channelsVRMS = null;

	private static final int[] BUFFER_LOCK = new int[0];

	private volatile boolean capturing = false;

	private AudioFormat audioFormat;
	private static double normalizationValue;

	private ByteBuffer bb = null;

	public ChannelData() {
	}

	public boolean setBuffer(Buffer buffer) {
		if (capturing) {
			// just hold on to it for later use... if needed!
			synchronized (BUFFER_LOCK) {
				bb.put((byte[]) buffer.getData(), buffer.getOffset(), Math.min(bb.remaining(), buffer.getLength()));
				BUFFER_LOCK.notifyAll();
			}
		}
		return capturing;
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
	private void calculateChannelData(final int sizeOfSampleInBytes, final int numChannels, final boolean signed,
			final boolean bigEndian, final int overSampleDisplacement, final int numSamples,
			final boolean loadWaveValues) {

		bb.rewind();
		for (int sampleNr = 0; sampleNr < numSamples; sampleNr++) {
			bb.position(sampleNr * numChannels * overSampleDisplacement);
			for (int channel = 0; channel < numChannels; channel++) {
				double sampleValue = getData(sizeOfSampleInBytes);

				if (loadWaveValues) {
					channelWaveValues[channel][sampleNr] = sampleValue;
				} else {
					channelsVRMS[channel] += sampleValue * sampleValue;
				}
			}
		}
		if (!loadWaveValues) {
			for (int i = 0; i < channelsVRMS.length; i++) {
				channelsVRMS[i] /= (double) numSamples;
				channelsVRMS[i] = 10 * Math.log10(channelsVRMS[i]);
			}
		}
	}

	/**
	 * @param sizeOfSampleInBytes
	 * @param data
	 * @return
	 */
	private double getData(int sizeOfSampleInBytes) {

		switch (sizeOfSampleInBytes) {
		case 1:
			return convertToScale(bb.get());
		case 2:
			return convertToScale(bb.getShort());
		case 4:
			return convertToScale(bb.getInt());
		case 8:
			return convertToScale(bb.getLong());
		default:
			throw new RuntimeException("sizeOfSampleInBytes is not a primitive java type : '" + sizeOfSampleInBytes
					+ "'");
		}
	}

	/**
	 * 
	 * @param numChannels
	 * @param numSamples
	 * @param loadWaveValues flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS.
	 */
	private void allocateChannelsData(final int numChannels, final int numSamples, final boolean loadWaveValues) {
		if (loadWaveValues) {
			channelWaveValues = new double[numChannels][numSamples];
		} else {
			channelsVRMS = new double[numChannels];
		}
	}

	private static double convertToScale(long val) {
		// bring the initial value to the 0 in terms of scale
		double result = val; // - normalizationValue;
		// normalize the value to the -1 to 1 scale
		result /= normalizationValue;
		return result;
	}

	/**
	 * @param numSamples the desired number of samples
	 * @param acquisitionFrequency the desired frequency in Hz we wish to
	 *            capture.
	 * @param loadWaveValues flag to control if the allocation is to be
	 *            performed for the wave values or for the VRMS. When true, it
	 *            loads only the waves. When false, it loads only the VRMS.
	 * @return
	 */
	public ChannelDataFrame readDataFrame(final int numSamples, double acquisitionFrequency,
			final boolean loadWaveValues) {
		int numChannels = audioFormat.getChannels();
		int sizeOfSampleInBytes = audioFormat.getSampleSizeInBits() / BITS_PER_BYTE;
		final int overSampleDisplacement = (int) (audioFormat.getSampleRate() / acquisitionFrequency);
		final boolean signed = audioFormat.getSigned() == AudioFormat.SIGNED;
		final boolean bigEndian = audioFormat.getEndian() == AudioFormat.BIG_ENDIAN;

		synchronized (BUFFER_LOCK) {
			int numberOfBytesToAllocate = numSamples * numChannels * sizeOfSampleInBytes * overSampleDisplacement;
			long startTime = System.currentTimeMillis();
			bb = ByteBuffer.allocate(numberOfBytesToAllocate);
			bb.order(bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);

			startTime = System.currentTimeMillis();
			capturing = true;
			while (bb.remaining() > 0) {
				try {
					BUFFER_LOCK.wait();
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
			capturing = false;
			long timeEnd = System.currentTimeMillis();
			if ((timeEnd - startTime) > 1.1 * ((double) numSamples) * 1000. / acquisitionFrequency) {
				ReCJMFUtils.LOGGER.fine("Done Capturing - oops - took to long: " + (timeEnd - startTime));
			}

			startTime = System.currentTimeMillis();

			allocateChannelsData(numChannels, numSamples, loadWaveValues);

			normalizationValue = Math.pow(2, audioFormat.getSampleSizeInBits() - 1);

			startTime = System.currentTimeMillis();
			calculateChannelData(sizeOfSampleInBytes, numChannels, signed, bigEndian, overSampleDisplacement,
					numSamples, loadWaveValues);

			return new ChannelDataFrame(channelWaveValues, channelsVRMS, audioFormat);
		}
	}

	/**
	 * @param audioFormat
	 */
	public void setAudioFormat(final AudioFormat audioFormat) {
		this.audioFormat = audioFormat;
	}

	public boolean isCapturing() {
		return capturing;
	}

}