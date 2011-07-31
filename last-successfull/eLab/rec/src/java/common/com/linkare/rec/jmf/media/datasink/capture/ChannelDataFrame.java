package com.linkare.rec.jmf.media.datasink.capture;

public class ChannelDataFrame {

    private long[][] channelsData;
    private double[] channelsVRMS;

    public ChannelDataFrame(long[][] channelsData, double[] channelsVRMS) {
	this.channelsData = channelsData;
	this.channelsVRMS = channelsVRMS;
    }

    public long[][] getChannelsData() {
	return channelsData;
    }

    public void setChannelsData(long[][] channelsData) {
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

    public long[] getChannelData(int channel) {
	return channelsData[channel];
    }

    public double getChannelVRMS(int channel) {
	return channelsVRMS[channel];
    }
}
