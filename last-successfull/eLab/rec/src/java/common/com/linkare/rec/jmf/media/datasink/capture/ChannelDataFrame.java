package com.linkare.rec.jmf.media.datasink.capture;

import javax.media.format.AudioFormat;

public class ChannelDataFrame {

	private double[][] channelsData;
	private double[] channelsVRMS;
	private AudioFormat captureFormat;

	public ChannelDataFrame(double[][] channelsData, double[] channelsVRMS, AudioFormat captureFormat) {
		this.channelsData = channelsData;
		this.channelsVRMS = channelsVRMS;
		this.captureFormat = captureFormat;
	}

	public double[][] getChannelsData() {
		return channelsData;
	}

	public void setChannelsData(double[][] channelsData) {
		this.channelsData = channelsData;
	}

	public double[] getChannelsVRMS() {
		return channelsVRMS;
	}

	public void setChannelsVRMS(double[] channelsVRMS) {
		this.channelsVRMS = channelsVRMS;
	}

	public int getNumChannels() {
		return channelsData.length;
	}

	public double[] getChannelData(int channel) {
		return channelsData[channel];
	}

	public double getChannelVRMS(int channel) {
		return channelsVRMS[channel];
	}

	/**
	 * @return the captureFormat
	 */
	public AudioFormat getCaptureFormat() {
		return captureFormat;
	}

	/**
	 * @param captureFormat the captureFormat to set
	 */
	public void setCaptureFormat(AudioFormat captureFormat) {
		this.captureFormat = captureFormat;
	}

}
