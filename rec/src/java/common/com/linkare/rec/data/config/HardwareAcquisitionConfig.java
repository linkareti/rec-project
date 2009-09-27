package com.linkare.rec.data.config;

public final class HardwareAcquisitionConfig implements org.omg.CORBA.portable.IDLEntity {
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
	public HardwareAcquisitionConfig(String familiarName, String hardwareUniqueID,
			com.linkare.rec.data.synch.DateTime time_start, com.linkare.rec.data.synch.Frequency selected_frequency,
			com.linkare.rec.data.config.ChannelAcquisitionConfig[] channels_config,
			com.linkare.rec.data.config.ParameterConfig[] selected_hardware_parameters, int total_samples) {
		this.setFamiliarName(familiarName);
		this.setHardwareUniqueID(hardwareUniqueID);
		this.setTimeStart(time_start);
		this.setSelectedFrequency(selected_frequency);
		this.setSelectedHardwareParameters(selected_hardware_parameters);
		this.setChannelsConfig(channels_config);
		this.setTotalSamples(total_samples);
	}

	public HardwareAcquisitionConfig(HardwareAcquisitionConfig other) {

		this.setFamiliarName(new String(other.getFamiliarName()));
		this.setHardwareUniqueID(new String(other.getHardwareUniqueID()));
		this.setTimeStart(new com.linkare.rec.data.synch.DateTime(other.getTimeStart()));
		this.setSelectedFrequency(new com.linkare.rec.data.synch.Frequency(other.getSelectedFrequency()));

		ParameterConfig[] temp = null;
		if (other.getSelectedHardwareParameters() != null) {
			temp = new ParameterConfig[other.getSelectedHardwareParameters().length];
			System.arraycopy(other.getSelectedHardwareParameters(), 0, temp, 0, temp.length);
		}
		this.setSelectedHardwareParameters(temp);
		temp = null;
		System.gc();

		ChannelAcquisitionConfig[] temp2 = null;
		if (other.getChannelsConfig() != null) {
			temp2 = new ChannelAcquisitionConfig[other.getChannelsConfig().length];
			System.arraycopy(other.getChannelsConfig(), 0, temp2, 0, temp2.length);
		}
		this.setChannelsConfig(temp2);
		temp2 = null;
		System.gc();
		this.setTotalSamples(other.getTotalSamples());
	}

	public HardwareAcquisitionConfig(com.linkare.rec.data.metadata.HardwareInfo info) {
		setFamiliarName(info.getFamiliarName());
		setHardwareUniqueID(info.getHardwareUniqueID());

		ParameterConfig[] params = null;
		com.linkare.rec.data.metadata.ChannelParameter[] params_info = null;

		if ((params_info = info.getHardwareParameters()) != null) {
			params = new ParameterConfig[params_info.length];
			for (int i = 0; i < params_info.length; i++) {
				if (params_info[i] != null)
					params[i] = new ParameterConfig(params_info[i].getParameterName(), params_info[i]
							.getSelectedParameterValue());
			}
			this.setSelectedHardwareParameters(params);
		}

		this.setSelectedFrequency(info.getSelectedFrequency());

		com.linkare.rec.data.metadata.ChannelInfo[] ch_infos = null;
		if ((ch_infos = info.getChannelsInfo()) != null)

		{
			ChannelAcquisitionConfig[] ch_acq_configs = new ChannelAcquisitionConfig[ch_infos.length];

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
		return this.timeStart;
	}

	/**
	 * Setter for property timeStart.
	 * 
	 * @param timeStart New value of property timeStart.
	 */
	public void setTimeStart(com.linkare.rec.data.synch.DateTime timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * Getter for property selectedFrequency.
	 * 
	 * @return Value of property selectedFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getSelectedFrequency() {
		return this.selectedFrequency;
	}

	/**
	 * Setter for property selectedFrequency.
	 * 
	 * @param selectedFrequency New value of property selectedFrequency.
	 */
	public void setSelectedFrequency(com.linkare.rec.data.synch.Frequency selectedFrequency) {
		this.selectedFrequency = selectedFrequency;
	}

	/**
	 * Indexed getter for property selectedChannelParameters.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ParameterConfig getSelectedHardwareParameters(int index) {
		if (this.selectedHardwareParameters != null && index < this.selectedHardwareParameters.length)
			return this.selectedHardwareParameters[index];

		throw new RuntimeException("No ParameterConfig at that index...");
	}

	/**
	 * Getter for property selectedChannelParameters.
	 * 
	 * @return Value of property selectedChannelParameters.
	 */
	public com.linkare.rec.data.config.ParameterConfig[] getSelectedHardwareParameters() {
		return this.selectedHardwareParameters;
	}

	public com.linkare.rec.data.config.ParameterConfig getSelectedHardwareParameter(String parameter_name) {
		if (this.selectedHardwareParameters != null && parameter_name != null) {
			for (int i = 0; i < selectedHardwareParameters.length; i++)
				if (parameter_name.equals(selectedHardwareParameters[i].getParameterName()))
					return selectedHardwareParameters[i];
		}
		return null;

	}

	public String getSelectedHardwareParameterValue(String parameter_name) {
		ParameterConfig param = null;
		if ((param = getSelectedHardwareParameter(parameter_name)) != null)
			return param.getParameterValue();

		return null;
	}

	/**
	 * Indexed setter for property selectedHardwareParameters.
	 * 
	 * @param index Index of the property.
	 * @param selectedHardwareParameters New value of the property at
	 *            <CODE>index</CODE>.
	 */
	public void setSelectedHardwareParameters(int index,
			com.linkare.rec.data.config.ParameterConfig selectedHardwareParameters) {
		if (this.selectedHardwareParameters != null && index < this.selectedHardwareParameters.length)
			this.selectedHardwareParameters[index] = selectedHardwareParameters;
		else {
			ParameterConfig[] temp = new ParameterConfig[index + 1];
			if (this.selectedHardwareParameters != null)
				System.arraycopy(this.selectedHardwareParameters, 0, temp, 0, this.selectedHardwareParameters.length);

			temp[index] = selectedHardwareParameters;
			this.selectedHardwareParameters = temp;
			temp = null;
			System.gc();
		}
	}

	public void addSelectedHardwareParameter(com.linkare.rec.data.config.ParameterConfig selectedHardwareParameter) {
		if (this.selectedHardwareParameters == null)
			setSelectedHardwareParameters(0, selectedHardwareParameter);
		else
			setSelectedHardwareParameters(selectedHardwareParameters.length, selectedHardwareParameter);
	}

	/**
	 * Setter for property selectedChannelParameters.
	 * 
	 * @param selectedChannelParameters New value of property
	 *            selectedChannelParameters.
	 */
	public void setSelectedHardwareParameters(com.linkare.rec.data.config.ParameterConfig[] selectedHardwareParameters) {
		this.selectedHardwareParameters = selectedHardwareParameters;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	public int getTotalSamples() {
		return this.totalSamples;
	}

	/**
	 * Setter for property totalSamples.
	 * 
	 * @param totalSamples New value of property totalSamples.
	 */
	public void setTotalSamples(int totalSamples) {
		this.totalSamples = totalSamples;
	}

	/**
	 * Indexed getter for property channelsConfig.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ChannelAcquisitionConfig getChannelsConfig(int index) {
		if (this.channelsConfig != null && index < this.channelsConfig.length)
			return this.channelsConfig[index];

		throw new RuntimeException("No ChannelAcquisitionConfig at that index...");
	}

	/**
	 * Indexed getter for property channelsConfig.
	 * 
	 * @param name Name of the channel.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ChannelAcquisitionConfig getChannelsConfig(String channelName) {
		if (channelName == null)
			return null;

		if (channelsConfig != null) {
			for (int i = 0; i < channelsConfig.length; i++) {
				if (channelsConfig[i] != null && channelsConfig[i].getChannelName() != null
						&& channelsConfig[i].getChannelName().equals(channelName))
					return channelsConfig[i];
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
		return this.channelsConfig;
	}

	/**
	 * Indexed setter for property channelsConfig.
	 * 
	 * @param index Index of the property.
	 * @param channelsConfig New value of the property at <CODE>index</CODE>.
	 */
	public void setChannelsConfig(int index, com.linkare.rec.data.config.ChannelAcquisitionConfig channelsConfig) {
		if (this.channelsConfig != null && index < this.channelsConfig.length)
			this.channelsConfig[index] = channelsConfig;
		else {
			ChannelAcquisitionConfig[] temp = new ChannelAcquisitionConfig[index + 1];
			if (this.channelsConfig != null)
				System.arraycopy(this.channelsConfig, 0, temp, 0, this.channelsConfig.length);

			temp[index] = channelsConfig;
			this.channelsConfig = temp;
			temp = null;
			System.gc();
		}

	}

	public void addChannelConfig(com.linkare.rec.data.config.ChannelAcquisitionConfig channelConfig) {
		if (channelsConfig == null)
			setChannelsConfig(0, channelConfig);
		else
			setChannelsConfig(channelsConfig.length, channelConfig);

	}

	/**
	 * Setter for property channelsConfig.
	 * 
	 * @param channelsConfig New value of property channelsConfig.
	 */
	public void setChannelsConfig(com.linkare.rec.data.config.ChannelAcquisitionConfig[] channelsConfig) {
		this.channelsConfig = channelsConfig;
	}

	public String toString() {
		StringBuffer strBufOut = new StringBuffer();
		String linesep = System.getProperty("line.separator");

		if (familiarName != null)
			strBufOut.append("Apparatus: ").append(familiarName).append(linesep);

		if (timeStart != null)
			strBufOut.append("Experience Start Time: ").append(timeStart.toSimpleString()).append(linesep);

		if (selectedFrequency != null)
			strBufOut.append("Frequency: ").append(selectedFrequency).append(linesep);

		if (totalSamples != com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value)
			strBufOut.append("Total Samples: ").append(totalSamples).append(linesep);
		else if (totalSamples == com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value)
			strBufOut.append("Total Samples Undetermined!").append(linesep);

		if (selectedHardwareParameters != null) {
			strBufOut.append("Parameters: ").append(linesep);
			for (int i = 0; i < selectedHardwareParameters.length; i++) {
				if (selectedHardwareParameters[i] != null)
					strBufOut.append("\t").append(selectedHardwareParameters[i]).append(linesep);
			}
		}

		if (channelsConfig != null) {
			strBufOut.append("Channels Configuration: ").append(linesep);
			for (int i = 0; i < channelsConfig.length; i++) {
				if (channelsConfig[i] != null)
					strBufOut.append("\tChannel " + i + " configuration:").append(linesep).append(channelsConfig[i])
							.append(linesep);
				else
					strBufOut.append("\tChannel " + i + " configuration undefined...").append(linesep);
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
		return this.familiarName;
	}

	/**
	 * Setter for property familiarName.
	 * 
	 * @param familiarName New value of property familiarName.
	 */
	public void setFamiliarName(String familiarName) {
		this.familiarName = familiarName;
	}

	/**
	 * Getter for property hardwareUniqueID.
	 * 
	 * @return Value of property hardwareUniqueID.
	 */
	public String getHardwareUniqueID() {
		return this.hardwareUniqueID;
	}

	/**
	 * Setter for property hardwareUniqueID.
	 * 
	 * @param hardwareUniqueID New value of property hardwareUniqueID.
	 */
	public void setHardwareUniqueID(String hardwareUniqueID) {
		this.hardwareUniqueID = hardwareUniqueID;
	}

} // class HardwareAcquisitionConfig
