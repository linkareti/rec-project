/*
 * DefaultExpDataModel.java
 *
 * Created on 7 de Maio de 2003, 12:48
 */

package com.linkare.rec.impl.client.experiment;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class DefaultExpDataModel extends AbstractExpDataModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1255034627816487663L;
	private static String DATA_RECEIVER_LOGGER = "DataReceiver.Logger";
	private int channelCount = -1;

	private DateTime timeStart = null;
	// private DataAcquisitionThread dataCollector=null;
	private Hashtable<Integer, ChannelAcquisitionConfig> channels = null;

	static {
		final Logger l = LogManager.getLogManager().getLogger(DefaultExpDataModel.DATA_RECEIVER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(DefaultExpDataModel.DATA_RECEIVER_LOGGER));
		}
	}

	/** Creates a new instance of DefaultExpDataModel */
	public DefaultExpDataModel() {
		super();
	}

	@Override
	public void fireNewSamples() {
		// int maxpacket = getSamplesPacketSource().getLargestNumPacket();
		// fireExpDataModelListenerNewSamples(maxpacket);
	}

	/**
	 * Getter for property channelCount.
	 * 
	 * @return Value of property channelCount.
	 */
	@Override
	public int getChannelCount() {
		if (channelCount != -1) {
			return channelCount;
		}

		try {
			channelCount = getAcquisitionConfig().getChannelsConfig().length;
		} catch (final NullPointerException npe) {
			LoggerUtil.logThrowable("Couldn't get Channel Count from Acquisition Header!", npe, LogManager
					.getLogManager().getLogger(DefaultExpDataModel.DATA_RECEIVER_LOGGER));
		}

		return channelCount;
	}

	private Hashtable<Integer, ChannelAcquisitionConfig> getChannels() {
		
		if (channels != null) {
			return channels;
		}
		try {
			final ChannelAcquisitionConfig[] ch_configs = getAcquisitionConfig().getChannelsConfig();

			if (ch_configs == null) {
				return null;
			}

			if (channels == null) {
				channels = new Hashtable<Integer, ChannelAcquisitionConfig>(ch_configs.length);
			}

			for (int i = 0; i < ch_configs.length; i++) {
				channels.put(i, ch_configs[i]);
			}

		} catch (final NullPointerException npe) {
			LoggerUtil.logThrowable("Couldn't get Channel Count from Acquisition Header!", npe, LogManager
					.getLogManager().getLogger(DefaultExpDataModel.DATA_RECEIVER_LOGGER));
		}

		return channels;
	}

	@Override
	public int getChannelIndex(final String channelName) {
		int channelIndex = -1;

		if (getChannels() != null && channelName != null) {
			for (final Map.Entry<Integer, ChannelAcquisitionConfig> channelsMapEntrie : getChannels().entrySet()) {
				if (channelName.equals(channelsMapEntrie.getValue().getChannelName())) {
					channelIndex = channelsMapEntrie.getKey();
					break;
				}
			}
		}
		return channelIndex;
	}

	@Override
	public String getChannelName(final int channelIndex) {
		if (getChannels() == null) {
			return null;
		}

		return getChannels().get(channelIndex).getChannelName();
	}

	@Override
	public ChannelAcquisitionConfig getChannelConfig(final int channelIndex) {
		if (getChannels() == null) {
			return null;
		}

		return getChannels().get(channelIndex);
	}

	@Override
	public ChannelAcquisitionConfig getChannelConfig(final String channelName) {
		if (getChannels() == null) {
			return null;
		}

		return getChannelConfig(getChannelIndex(channelName));
	}

	@Override
	public com.linkare.rec.data.synch.DateTime getTimeStamp(final int sampleIndex) {
		return calcTimeStamp(sampleIndex);
	}

	private com.linkare.rec.data.synch.DateTime calcTimeStamp(final int sampleIndex) {
		if (getTimeStart() == null) {
			return null;
		}

		try {
			return getTimeStart().calculateDateTime(getAcquisitionConfig().getSelectedFrequency(), sampleIndex);
		} catch (final NullPointerException npe) {
			LoggerUtil.logThrowable("Couldn't calculate TimeStamp for sample " + sampleIndex, npe, LogManager
					.getLogManager().getLogger(DefaultExpDataModel.DATA_RECEIVER_LOGGER));
		}

		return null;
	}

	private DateTime getTimeStart() {
		if (timeStart != null) {
			return timeStart;
		}

		try {
			timeStart = getAcquisitionConfig().getTimeStart();
		} catch (final NullPointerException npe) {
			LoggerUtil.logThrowable("Couldn't get starting time from Acquisition Header!", npe, LogManager
					.getLogManager().getLogger(DefaultExpDataModel.DATA_RECEIVER_LOGGER));
		}

		return timeStart;
	}

	@Override
	public void clientsListChanged() {
	}

}
