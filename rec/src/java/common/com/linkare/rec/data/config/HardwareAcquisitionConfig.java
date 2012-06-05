package com.linkare.rec.data.config;

import java.util.ResourceBundle;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

public final class HardwareAcquisitionConfig implements org.omg.CORBA.portable.IDLEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8746936361307546270L;

	private static final ResourceBundle resourceBundle = ResourceBundle
			.getBundle("com/linkare/rec/data/resources/messages");

	private static final String APPARATUS = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.apparatus");

	private static final String EXPERIENCE_START_TIME = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.experience.start.time");

	private static final String FREQUENCY = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.frequency");

	private static final String TOTAL_SAMPLES = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.total.samples");

	private static final String TOTAL_SAMPLES_UNDETERMINED = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.total.samples.undetermined");

	private static final String PARAMETERS = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.parameters");

	private static final String CHANNELS_CONFIGURATION = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.channels.onfiguration");

	private static final String CHANNEL = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.channel");

	private static final String CONFIGURATION = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.configuration");

	private static final String CONFIGURATION_UNDEFINED = HardwareAcquisitionConfig.resourceBundle
			.getString("rec.ui.hardware.acquisition.config.configuration.undefined");

	/** Holds value of property timeStart. */
	private com.linkare.rec.data.synch.DateTime timeStart;

	/** Holds value of property selectedFrequency. */
	private com.linkare.rec.data.synch.Frequency selectedFrequency;

	/** Holds value of property selectedChannelParameters. */
	private com.linkare.rec.data.config.ParameterConfig[] selectedHardwareParameters;

	/** Holds value of property totalSamples. */
	private int totalSamples;

	/** Holds value of property channelsConfig. */
	private com.linkare.rec.data.config.ChannelAcquisitionConfig[] channelsConfig;

	/** Holds value of property familiarName. */
	private String familiarName;

	/** Holds value of property hardwareUniqueID. */
	private String hardwareUniqueID;

	//
	// Default constructor
	//
	public HardwareAcquisitionConfig() {
	}

	//
	// Constructor with fields initialization
	// @param time_start time_start struct member
	// @param selected_frequency selected_frequency struct member
	// @param channels_config channels_config struct member
	// @param selected_hardware_parameters selected_hardware_parameters struct
	// member
	// @param total_samples total_samples struct member
	//
	public HardwareAcquisitionConfig(final String familiarName, final String hardwareUniqueID,
			final com.linkare.rec.data.synch.DateTime time_start,
			final com.linkare.rec.data.synch.Frequency selected_frequency,
			final com.linkare.rec.data.config.ChannelAcquisitionConfig[] channels_config,
			final com.linkare.rec.data.config.ParameterConfig[] selected_hardware_parameters, final int total_samples) {
		setFamiliarName(familiarName);
		setHardwareUniqueID(hardwareUniqueID);
		setTimeStart(time_start);
		setSelectedFrequency(selected_frequency);
		this.setSelectedHardwareParameters(selected_hardware_parameters);
		this.setChannelsConfig(channels_config);
		setTotalSamples(total_samples);
	}

	public HardwareAcquisitionConfig(final HardwareAcquisitionConfig other) {

		setFamiliarName(other.getFamiliarName());
		setHardwareUniqueID(other.getHardwareUniqueID());
		setTimeStart(new com.linkare.rec.data.synch.DateTime(other.getTimeStart()));
		setSelectedFrequency(new com.linkare.rec.data.synch.Frequency(other.getSelectedFrequency()));

		ParameterConfig[] temp = null;
		if (other.getSelectedHardwareParameters() != null) {
			temp = new ParameterConfig[other.getSelectedHardwareParameters().length];
			for (int i = 0; i < other.getSelectedHardwareParameters().length; i++) {
				temp[i] = new ParameterConfig(other.getSelectedHardwareParameters(i));
			}
			// DELETEME System.arraycopy(other.getSelectedHardwareParameters(),
			// 0, temp, 0, temp.length);
		}
		this.setSelectedHardwareParameters(temp);
		temp = null;

		ChannelAcquisitionConfig[] temp2 = null;
		if (other.getChannelsConfig() != null) {
			temp2 = new ChannelAcquisitionConfig[other.getChannelsConfig().length];
			for (int i = 0; i < other.getChannelsConfig().length; i++) {
				temp2[i] = new ChannelAcquisitionConfig(other.getChannelsConfig(i));
			}
			// DELETEME System.arraycopy(other.getChannelsConfig(), 0, temp2, 0,
			// temp2.length);
		}
		this.setChannelsConfig(temp2);
		temp2 = null;
		setTotalSamples(other.getTotalSamples());
	}

	public HardwareAcquisitionConfig(final com.linkare.rec.data.metadata.HardwareInfo info) {
		setFamiliarName(info.getFamiliarName());
		setHardwareUniqueID(info.getHardwareUniqueID());

		ParameterConfig[] params = null;
		com.linkare.rec.data.metadata.ChannelParameter[] params_info = null;

		if ((params_info = info.getHardwareParameters()) != null) {
			params = new ParameterConfig[params_info.length];
			for (int i = 0; i < params_info.length; i++) {
				if (params_info[i] != null) {
					params[i] = new ParameterConfig(params_info[i].getParameterName(),
							params_info[i].getSelectedParameterValue());
				}
			}
			this.setSelectedHardwareParameters(params);
		}

		setSelectedFrequency(info.getSelectedFrequency());

		com.linkare.rec.data.metadata.ChannelInfo[] ch_infos = null;
		if ((ch_infos = info.getChannelsInfo()) != null)

		{
			final ChannelAcquisitionConfig[] ch_acq_configs = new ChannelAcquisitionConfig[ch_infos.length];

			for (int i = 0; i < ch_acq_configs.length; i++) {
				ch_acq_configs[i] = new ChannelAcquisitionConfig(ch_infos[i]);
			}

			this.setChannelsConfig(ch_acq_configs);
		}

	}

	/**
	 * Getter for property timeStart.
	 * 
	 * @return Value of property timeStart.
	 */
	public com.linkare.rec.data.synch.DateTime getTimeStart() {
		return timeStart;
	}

	/**
	 * Setter for property timeStart.
	 * 
	 * @param timeStart New value of property timeStart.
	 */
	public void setTimeStart(final com.linkare.rec.data.synch.DateTime timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * Getter for property selectedFrequency.
	 * 
	 * @return Value of property selectedFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getSelectedFrequency() {
		return selectedFrequency;
	}

	/**
	 * Setter for property selectedFrequency.
	 * 
	 * @param selectedFrequency New value of property selectedFrequency.
	 */
	public void setSelectedFrequency(final com.linkare.rec.data.synch.Frequency selectedFrequency) {
		this.selectedFrequency = selectedFrequency;
	}

	/**
	 * Indexed getter for property selectedChannelParameters.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ParameterConfig getSelectedHardwareParameters(final int index) {
		if (selectedHardwareParameters != null && index < selectedHardwareParameters.length) {
			return selectedHardwareParameters[index];
		}

		throw new RuntimeException("No ParameterConfig at that index...");
	}

	/**
	 * Getter for property selectedChannelParameters.
	 * 
	 * @return Value of property selectedChannelParameters.
	 */
	public com.linkare.rec.data.config.ParameterConfig[] getSelectedHardwareParameters() {
		return selectedHardwareParameters;
	}

	public com.linkare.rec.data.config.ParameterConfig getSelectedHardwareParameter(final String parameter_name) {
		if (selectedHardwareParameters != null && parameter_name != null) {
			for (final ParameterConfig selectedHardwareParameter : selectedHardwareParameters) {
				if (parameter_name.equals(selectedHardwareParameter.getParameterName())) {
					return selectedHardwareParameter;
				}
			}
		}
		return null;

	}

	public String getSelectedHardwareParameterValue(final String parameter_name) {
		ParameterConfig param = null;
		if ((param = getSelectedHardwareParameter(parameter_name)) != null) {
			return param.getParameterValue();
		}

		return null;
	}

	/**
	 * Indexed setter for property selectedHardwareParameters.
	 * 
	 * @param index Index of the property.
	 * @param selectedHardwareParameters New value of the property at
	 *            <CODE>index</CODE>.
	 */
	public void setSelectedHardwareParameters(final int index,
			final com.linkare.rec.data.config.ParameterConfig selectedHardwareParameters) {
		if (this.selectedHardwareParameters != null && index < this.selectedHardwareParameters.length) {
			this.selectedHardwareParameters[index] = selectedHardwareParameters;
		} else {
			ParameterConfig[] temp = new ParameterConfig[index + 1];
			if (this.selectedHardwareParameters != null) {
				System.arraycopy(this.selectedHardwareParameters, 0, temp, 0, this.selectedHardwareParameters.length);
			}

			temp[index] = selectedHardwareParameters;
			this.selectedHardwareParameters = temp;
			temp = null;
		}
	}

	public void addSelectedHardwareParameter(final com.linkare.rec.data.config.ParameterConfig selectedHardwareParameter) {
		if (selectedHardwareParameters == null) {
			setSelectedHardwareParameters(0, selectedHardwareParameter);
		} else {
			setSelectedHardwareParameters(selectedHardwareParameters.length, selectedHardwareParameter);
		}
	}

	/**
	 * Setter for property selectedChannelParameters.
	 * 
	 * @param selectedChannelParameters New value of property
	 *            selectedChannelParameters.
	 */
	public void setSelectedHardwareParameters(
			final com.linkare.rec.data.config.ParameterConfig[] selectedHardwareParameters) {
		this.selectedHardwareParameters = selectedHardwareParameters;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	public int getTotalSamples() {
		return totalSamples;
	}

	/**
	 * Setter for property totalSamples.
	 * 
	 * @param totalSamples New value of property totalSamples.
	 */
	public void setTotalSamples(final int totalSamples) {
		this.totalSamples = totalSamples;
	}

	/**
	 * Indexed getter for property channelsConfig.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ChannelAcquisitionConfig getChannelsConfig(final int index) {
		if (channelsConfig != null && index < channelsConfig.length) {
			return channelsConfig[index];
		}

		throw new RuntimeException("No ChannelAcquisitionConfig at that index...");
	}

	/**
	 * Indexed getter for property channelsConfig.
	 * 
	 * @param channelName
	 * 
	 * @param name Name of the channel.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ChannelAcquisitionConfig getChannelsConfig(final String channelName) {
		if (channelName == null) {
			return null;
		}

		if (channelsConfig != null) {
			for (final ChannelAcquisitionConfig element : channelsConfig) {
				if (element != null && element.getChannelName() != null && element.getChannelName().equals(channelName)) {
					return element;
				}
			}
		}

		return null;

	}

	/**
	 * Getter for property channelsConfig.
	 * 
	 * @return Value of property channelsConfig.
	 */
	public com.linkare.rec.data.config.ChannelAcquisitionConfig[] getChannelsConfig() {
		return channelsConfig;
	}

	/**
	 * Indexed setter for property channelsConfig.
	 * 
	 * @param index Index of the property.
	 * @param channelsConfig New value of the property at <CODE>index</CODE>.
	 */
	public void setChannelsConfig(final int index,
			final com.linkare.rec.data.config.ChannelAcquisitionConfig channelsConfig) {
		if (this.channelsConfig != null && index < this.channelsConfig.length) {
			this.channelsConfig[index] = channelsConfig;
		} else {
			ChannelAcquisitionConfig[] temp = new ChannelAcquisitionConfig[index + 1];
			if (this.channelsConfig != null) {
				System.arraycopy(this.channelsConfig, 0, temp, 0, this.channelsConfig.length);
			}

			temp[index] = channelsConfig;
			this.channelsConfig = temp;
			temp = null;
		}

	}

	public void addChannelConfig(final com.linkare.rec.data.config.ChannelAcquisitionConfig channelConfig) {
		if (channelsConfig == null) {
			setChannelsConfig(0, channelConfig);
		} else {
			setChannelsConfig(channelsConfig.length, channelConfig);
		}

	}

	/**
	 * Setter for property channelsConfig.
	 * 
	 * @param channelsConfig New value of property channelsConfig.
	 */
	public void setChannelsConfig(final com.linkare.rec.data.config.ChannelAcquisitionConfig[] channelsConfig) {
		this.channelsConfig = channelsConfig;
	}

	@Override
	public String toString() {
		final StringBuffer strBufOut = new StringBuffer();
		final String linesep = System.getProperty("line.separator");

		if (familiarName != null) {
			strBufOut.append(HardwareAcquisitionConfig.APPARATUS).append(familiarName).append(linesep);
		}

		if (timeStart != null) {
			strBufOut.append(HardwareAcquisitionConfig.EXPERIENCE_START_TIME).append(timeStart.toSimpleString())
					.append(linesep);
		}

		if (selectedFrequency != null) {
			strBufOut.append(HardwareAcquisitionConfig.FREQUENCY).append(selectedFrequency).append(linesep);
		}

		if (totalSamples != com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append(HardwareAcquisitionConfig.TOTAL_SAMPLES).append(totalSamples).append(linesep);
		} else if (totalSamples == com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append(HardwareAcquisitionConfig.TOTAL_SAMPLES_UNDETERMINED).append(linesep);
		}

		if (selectedHardwareParameters != null) {
			strBufOut.append(HardwareAcquisitionConfig.PARAMETERS).append(linesep);
			for (final ParameterConfig selectedHardwareParameter : selectedHardwareParameters) {
				if (selectedHardwareParameter != null) {
					strBufOut.append("\t").append(selectedHardwareParameter).append(linesep);
				}
			}
		}

		if (channelsConfig != null) {
			strBufOut.append(HardwareAcquisitionConfig.CHANNELS_CONFIGURATION).append(linesep);
			for (int i = 0; i < channelsConfig.length; i++) {
				if (channelsConfig[i] != null) {
					strBufOut
							.append("\t" + HardwareAcquisitionConfig.CHANNEL + i
									+ HardwareAcquisitionConfig.CONFIGURATION).append(linesep)
							.append(channelsConfig[i]).append(linesep);
				} else {
					strBufOut.append(
							"\t" + HardwareAcquisitionConfig.CHANNEL + i
									+ HardwareAcquisitionConfig.CONFIGURATION_UNDEFINED).append(linesep);
				}
			}
		}

		return strBufOut.toString();
	}

	/**
	 * Getter for property familiarName.
	 * 
	 * @return Value of property familiarName.
	 */
	public String getFamiliarName() {
		return familiarName;
	}

	/**
	 * Setter for property familiarName.
	 * 
	 * @param familiarName New value of property familiarName.
	 */
	public void setFamiliarName(final String familiarName) {
		this.familiarName = familiarName;
	}

	/**
	 * Getter for property hardwareUniqueID.
	 * 
	 * @return Value of property hardwareUniqueID.
	 */
	public String getHardwareUniqueID() {
		return hardwareUniqueID;
	}

	/**
	 * Setter for property hardwareUniqueID.
	 * 
	 * @param hardwareUniqueID New value of property hardwareUniqueID.
	 */
	public void setHardwareUniqueID(final String hardwareUniqueID) {
		this.hardwareUniqueID = hardwareUniqueID;
	}

    /**
     * Replicates an {@link HardwareAcquisitionConfig} and replaces the keys to {@link ReCResourceBundle} to the real values, depending of the {@link Locale}.
     * 
     * @param acquisitionConfig
     *            The acquisition config to copy from.
     * @return A new {@link HardwareAcquisitionConfig} with the values for the channel name, scale label, physics unit name and symbol translated from the
     *         resource bundles, if they exist. If one of these keys do not exist, it will stay with the key as the value of the property.
     */
    public static HardwareAcquisitionConfig translatePropertyBundles(HardwareAcquisitionConfig acquisitionConfig) {
	HardwareAcquisitionConfig otherConfig = new HardwareAcquisitionConfig(acquisitionConfig);
	for (ChannelAcquisitionConfig channel : otherConfig.getChannelsConfig()) {
	    channel.setChannelName(ReCResourceBundle.findStringOrDefault(channel.getChannelName(), channel.getChannelName()));
	    channel.getSelectedScale().setScaleLabel(ReCResourceBundle.findStringOrDefault(channel.getSelectedScale().getScaleLabel(),
											   channel.getSelectedScale().getScaleLabel()));
	    channel.getSelectedScale().setPhysicsUnitName(ReCResourceBundle.findStringOrDefault(channel.getSelectedScale().getPhysicsUnitName(),
												channel.getSelectedScale().getPhysicsUnitName()));
	    channel.getSelectedScale().setPhysicsUnitSymbol(ReCResourceBundle.findStringOrDefault(channel.getSelectedScale().getPhysicsUnitSymbol(),
												  channel.getSelectedScale().getPhysicsUnitSymbol()));
	}
	return otherConfig;
    }

} // class HardwareAcquisitionConfig
