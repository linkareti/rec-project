package com.linkare.rec.data.config;

public final class ChannelAcquisitionConfig implements org.omg.CORBA.portable.IDLEntity {
	/** Holds value of property timeStart. */
	private com.linkare.rec.data.synch.DateTime timeStart;

	/** Holds value of property selectedFrequency. */
	private com.linkare.rec.data.synch.Frequency selectedFrequency;

	public com.linkare.rec.data.metadata.Scale selected_scale;

	/** Holds value of property selectedChannelParameters. */
	private com.linkare.rec.data.config.ParameterConfig[] selectedChannelParameters;

	/** Holds value of property totalSamples. */
	private int total_samples;

	/** Holds value of property channelName. */
	private String channelName;

	//
	// Default constructor
	//
	public ChannelAcquisitionConfig() {
	}

	//
	// Constructor with fields initialization
	// @param time_start time_start struct member
	// @param selected_frequency selected_frequency struct member
	// @param selected_scale_index selected_scale_index struct member
	// @param selected_channel_parameters selected_channel_parameters struct
	// member
	// @param total_samples total_samples struct member
	//
	public ChannelAcquisitionConfig(String channelName, com.linkare.rec.data.synch.DateTime time_start,
			com.linkare.rec.data.synch.Frequency selected_frequency,
			com.linkare.rec.data.metadata.Scale selected_scale,
			com.linkare.rec.data.config.ParameterConfig[] selected_channel_parameters, int total_samples) {
		this.setChannelName(channelName);
		this.setTimeStart(time_start);
		this.setSelectedFrequency(selected_frequency);
		this.setSelectedScale(selected_scale);
		this.setSelectedChannelParameters(selected_channel_parameters);
		this.setTotalSamples(total_samples);
	}

	//
	// Copy Constructor
	//
	public ChannelAcquisitionConfig(ChannelAcquisitionConfig other) {
		this.setChannelName(new String(other.getChannelName()));
		this.setTimeStart(new com.linkare.rec.data.synch.DateTime(other.getTimeStart()));
		this.setSelectedFrequency(new com.linkare.rec.data.synch.Frequency(other.getSelectedFrequency()));
		this.setSelectedScale(other.getSelectedScale());

		ParameterConfig[] temp = null;
		if (other.getSelectedChannelParameters() != null) {
			temp = new ParameterConfig[other.getSelectedChannelParameters().length];
			System.arraycopy(other.getSelectedChannelParameters(), 0, temp, 0, temp.length);
		}

		this.setSelectedChannelParameters(temp);
		temp = null;
		System.gc();
		this.setTotalSamples(other.getTotalSamples());
	}

	public ChannelAcquisitionConfig(com.linkare.rec.data.metadata.ChannelInfo info) {
		setChannelName(info.getChannelName());

		ParameterConfig[] params = null;
		com.linkare.rec.data.metadata.ChannelParameter[] params_info = null;

		if ((params_info = info.getChannelParameters()) != null) {
			for (int i = 0; i < params_info.length; i++) {
				if (params_info[i] != null)
					params[i] = new ParameterConfig(params_info[i].getParameterName(), params_info[i]
							.getSelectedParameterValue());
			}
			this.setSelectedChannelParameters(params);
		}

		this.setSelectedFrequency(info.getSelectedFrequency());
		this.setSelectedScale(info.getActualSelectedScale());
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
	 * Getter for property selectedScaleIndex.
	 * 
	 * @return Value of property selectedScaleIndex.
	 */
	public com.linkare.rec.data.metadata.Scale getSelectedScale() {
		return this.selected_scale;
	}

	/**
	 * Setter for property selectedScaleIndex.
	 * 
	 * @param selectedScaleIndex New value of property selectedScaleIndex.
	 */
	public void setSelectedScale(com.linkare.rec.data.metadata.Scale selected_scale) {
		this.selected_scale = selected_scale;
	}

	/**
	 * Indexed getter for property selectedChannelParameters.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ParameterConfig getSelectedChannelParameters(int index) {
		if (this.selectedChannelParameters != null && index < this.selectedChannelParameters.length)
			return this.selectedChannelParameters[index];

		throw new RuntimeException("No ParameterConfig at that index...");
	}

	/**
	 * Getter for property selectedChannelParameters.
	 * 
	 * @return Value of property selectedChannelParameters.
	 */
	public com.linkare.rec.data.config.ParameterConfig[] getSelectedChannelParameters() {
		return this.selectedChannelParameters;
	}

	public com.linkare.rec.data.config.ParameterConfig getSelectedChannelParameter(String parameter_name) {
		if (this.selectedChannelParameters != null && parameter_name != null) {
			for (int i = 0; i < selectedChannelParameters.length; i++)
				if (parameter_name.equals(selectedChannelParameters[i].getParameterName()))
					return selectedChannelParameters[i];
		}
		return null;

	}

	public String getSelectedChannelParameterValue(String parameter_name) {
		ParameterConfig param = null;
		if ((param = getSelectedChannelParameter(parameter_name)) != null)
			return param.getParameterValue();

		return null;
	}

	/**
	 * Indexed setter for property selectedChannelParameters.
	 * 
	 * @param index Index of the property.
	 * @param selectedChannelParameters New value of the property at
	 *            <CODE>index</CODE>.
	 */
	public void setSelectedChannelParameters(int index,
			com.linkare.rec.data.config.ParameterConfig selectedChannelParameters) {
		if (this.selectedChannelParameters != null && index < this.selectedChannelParameters.length)
			this.selectedChannelParameters[index] = selectedChannelParameters;
		else {
			ParameterConfig[] temp = new ParameterConfig[index + 1];
			if (this.selectedChannelParameters != null)
				System.arraycopy(this.selectedChannelParameters, 0, temp, 0, this.selectedChannelParameters.length);

			temp[index] = selectedChannelParameters;
			this.selectedChannelParameters = temp;
			temp = null;
			System.gc();
		}
	}

	public void addSelectedChannelParameter(com.linkare.rec.data.config.ParameterConfig selectedChannelParameter) {
		if (this.selectedChannelParameters == null)
			setSelectedChannelParameters(0, selectedChannelParameter);
		else
			setSelectedChannelParameters(selectedChannelParameters.length, selectedChannelParameter);
	}

	/**
	 * Setter for property selectedChannelParameters.
	 * 
	 * @param selectedChannelParameters New value of property
	 *            selectedChannelParameters.
	 */
	public void setSelectedChannelParameters(com.linkare.rec.data.config.ParameterConfig[] selectedChannelParameters) {
		this.selectedChannelParameters = selectedChannelParameters;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	public int getTotalSamples() {
		return this.total_samples;
	}

	/**
	 * Setter for property totalSamples.
	 * 
	 * @param totalSamples New value of property totalSamples.
	 */
	public void setTotalSamples(int totalSamples) {
		this.total_samples = totalSamples;
	}

	/**
	 * Returns a string representation of the object. In general, the
	 * <code>toString</code> method returns a string that "textually represents"
	 * this object. The result should be a concise but informative
	 * representation that is easy for a person to read. It is recommended that
	 * all subclasses override this method.
	 * <p>
	 * The <code>toString</code> method for class <code>Object</code> returns a
	 * string consisting of the name of the class of which the object is an
	 * instance, the at-sign character `<code>@</code>', and the unsigned
	 * hexadecimal representation of the hash code of the object. In other
	 * words, this method returns a string equal to the value of: <blockquote>
	 * 
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @return a string representation of the object.
	 */
	public String toString() {
		StringBuffer strBufOut = new StringBuffer();
		String linesep = System.getProperty("line.separator");

		if (channelName != null)
			strBufOut.append("\tData Channel Name: ").append(channelName).append(linesep);

		if (timeStart != null)
			strBufOut.append("\tData Channel Start Time: ").append(timeStart.toSimpleString()).append(linesep);

		if (selectedFrequency != null)
			strBufOut.append("\tData Channel Frequency: ").append(selectedFrequency).append(linesep);

		if (selected_scale != null)
			strBufOut.append("\tData Channel Scale: ").append(selected_scale).append(linesep);

		if (total_samples != com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value)
			strBufOut.append("\tData Channel Total Samples: ").append(total_samples).append(linesep);
		else if (total_samples == com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value)
			strBufOut.append("\tData Channel Total Samples Undetermined!").append(linesep);

		if (selectedChannelParameters != null) {
			strBufOut.append("\tData Channel Parameters: ").append(linesep);
			for (int i = 0; i < selectedChannelParameters.length; i++) {
				if (selectedChannelParameters[i] != null)
					strBufOut.append("\t\t").append(selectedChannelParameters[i]).append(linesep);
			}
		}

		return strBufOut.toString();
	}

	/**
	 * Getter for property channelName.
	 * 
	 * @return Value of property channelName.
	 */
	public String getChannelName() {
		return this.channelName;
		// return ReCResourceBundle.findStringOrDefault(this.channelName,
		// this.channelName);
	}

	/**
	 * Setter for property channelName.
	 * 
	 * @param channelName New value of property channelName.
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

} // class ChannelAcquisitionConfig
