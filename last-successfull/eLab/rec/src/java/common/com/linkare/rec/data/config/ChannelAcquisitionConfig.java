package com.linkare.rec.data.config;

import java.util.ResourceBundle;

import com.linkare.rec.data.metadata.Scale;

public final class ChannelAcquisitionConfig implements org.omg.CORBA.portable.IDLEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2314380377663357304L;

	private static final ResourceBundle resourceBundle = ResourceBundle
			.getBundle("com/linkare/rec/data/resources/messages");

	private static final String CHANNEL_NAME = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.name");

	private static final String CHANNEL_START_TIME = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.start.time");

	private static final String CHANNEL_FREQUENCY = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.frequency");

	private static final String CHANNEL_SCALE = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.scale");

	private static final String CHANNEL_TOTAL_SAMPLES = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.total.samples");

	private static final String CHANNEL_TOTAL_SAMPLES_UNDETERMINED = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.total.samples.undetermined");

	private static final String CHANNEL_PARAMETERS = ChannelAcquisitionConfig.resourceBundle
			.getString("rec.bui.channel.acquisition.config.data.channel.parameters");

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
	public ChannelAcquisitionConfig(final String channelName, final com.linkare.rec.data.synch.DateTime time_start,
			final com.linkare.rec.data.synch.Frequency selected_frequency,
			final com.linkare.rec.data.metadata.Scale selected_scale,
			final com.linkare.rec.data.config.ParameterConfig[] selected_channel_parameters, final int total_samples) {
		setChannelName(channelName);
		setTimeStart(time_start);
		setSelectedFrequency(selected_frequency);
		setSelectedScale(selected_scale);
		this.setSelectedChannelParameters(selected_channel_parameters);
		setTotalSamples(total_samples);
	}

	//
	// Copy Constructor
	//
	public ChannelAcquisitionConfig(final ChannelAcquisitionConfig other) {
		setChannelName(other.getChannelName());
		setTimeStart(other.getTimeStart() != null ? new com.linkare.rec.data.synch.DateTime(other.getTimeStart())
				: null);
		setSelectedFrequency(other.getSelectedFrequency() != null ? new com.linkare.rec.data.synch.Frequency(
				other.getSelectedFrequency()) : null);
		setSelectedScale(other.getSelectedScale() != null ? new Scale(other.getSelectedScale()) : null);

		ParameterConfig[] temp = null;
		if (other.getSelectedChannelParameters() != null) {
			temp = new ParameterConfig[other.getSelectedChannelParameters().length];
			for (int i = 0; i < other.getSelectedChannelParameters().length; i++) {
				temp[i] = new ParameterConfig(other.getSelectedChannelParameters(i));
			}
			// DELETEME System.arraycopy(other.getSelectedChannelParameters(),
			// 0, temp, 0, temp.length);
		}

		this.setSelectedChannelParameters(temp);
		temp = null;
		setTotalSamples(other.getTotalSamples());
	}

	public ChannelAcquisitionConfig(final com.linkare.rec.data.metadata.ChannelInfo info) {
		setChannelName(info.getChannelName());

		final ParameterConfig[] params = null;
		com.linkare.rec.data.metadata.ChannelParameter[] params_info = null;

		if ((params_info = info.getChannelParameters()) != null) {
			for (int i = 0; i < params_info.length; i++) {
				if (params_info[i] != null) {
					params[i] = new ParameterConfig(params_info[i].getParameterName(),
							params_info[i].getSelectedParameterValue());
				}
			}
			this.setSelectedChannelParameters(params);
		}

		setSelectedFrequency(info.getSelectedFrequency());
		setSelectedScale(info.getActualSelectedScale());
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
	 * Getter for property selectedScaleIndex.
	 * 
	 * @return Value of property selectedScaleIndex.
	 */
	public com.linkare.rec.data.metadata.Scale getSelectedScale() {
		return selected_scale;
	}

	/**
	 * Setter for property selectedScaleIndex.
	 * 
	 * @param selectedScaleIndex New value of property selectedScaleIndex.
	 */
	public void setSelectedScale(final com.linkare.rec.data.metadata.Scale selected_scale) {
		this.selected_scale = selected_scale;
	}

	/**
	 * Indexed getter for property selectedChannelParameters.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.config.ParameterConfig getSelectedChannelParameters(final int index) {
		if (selectedChannelParameters != null && index < selectedChannelParameters.length) {
			return selectedChannelParameters[index];
		}

		throw new RuntimeException("No ParameterConfig at that index...");
	}

	/**
	 * Getter for property selectedChannelParameters.
	 * 
	 * @return Value of property selectedChannelParameters.
	 */
	public com.linkare.rec.data.config.ParameterConfig[] getSelectedChannelParameters() {
		return selectedChannelParameters;
	}

	public com.linkare.rec.data.config.ParameterConfig getSelectedChannelParameter(final String parameter_name) {
		if (selectedChannelParameters != null && parameter_name != null) {
			for (final ParameterConfig selectedChannelParameter : selectedChannelParameters) {
				if (parameter_name.equals(selectedChannelParameter.getParameterName())) {
					return selectedChannelParameter;
				}
			}
		}
		return null;

	}

	public String getSelectedChannelParameterValue(final String parameter_name) {
		ParameterConfig param = null;
		if ((param = getSelectedChannelParameter(parameter_name)) != null) {
			return param.getParameterValue();
		}

		return null;
	}

	/**
	 * Indexed setter for property selectedChannelParameters.
	 * 
	 * @param index Index of the property.
	 * @param selectedChannelParameters New value of the property at
	 *            <CODE>index</CODE>.
	 */
	public void setSelectedChannelParameters(final int index,
			final com.linkare.rec.data.config.ParameterConfig selectedChannelParameters) {
		if (this.selectedChannelParameters != null && index < this.selectedChannelParameters.length) {
			this.selectedChannelParameters[index] = selectedChannelParameters;
		} else {
			ParameterConfig[] temp = new ParameterConfig[index + 1];
			if (this.selectedChannelParameters != null) {
				System.arraycopy(this.selectedChannelParameters, 0, temp, 0, this.selectedChannelParameters.length);
			}

			temp[index] = selectedChannelParameters;
			this.selectedChannelParameters = temp;
			temp = null;
		}
	}

	public void addSelectedChannelParameter(final com.linkare.rec.data.config.ParameterConfig selectedChannelParameter) {
		if (selectedChannelParameters == null) {
			setSelectedChannelParameters(0, selectedChannelParameter);
		} else {
			setSelectedChannelParameters(selectedChannelParameters.length, selectedChannelParameter);
		}
	}

	/**
	 * Setter for property selectedChannelParameters.
	 * 
	 * @param selectedChannelParameters New value of property
	 *            selectedChannelParameters.
	 */
	public void setSelectedChannelParameters(
			final com.linkare.rec.data.config.ParameterConfig[] selectedChannelParameters) {
		this.selectedChannelParameters = selectedChannelParameters;
	}

	/**
	 * Getter for property totalSamples.
	 * 
	 * @return Value of property totalSamples.
	 */
	public int getTotalSamples() {
		return total_samples;
	}

	/**
	 * Setter for property totalSamples.
	 * 
	 * @param totalSamples New value of property totalSamples.
	 */
	public void setTotalSamples(final int totalSamples) {
		total_samples = totalSamples;
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
	@Override
	public String toString() {
		final StringBuffer strBufOut = new StringBuffer();
		final String linesep = System.getProperty("line.separator");

		if (channelName != null) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_NAME).append(channelName).append(linesep);
		}

		if (timeStart != null) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_START_TIME).append(timeStart.toSimpleString())
					.append(linesep);
		}

		if (selectedFrequency != null) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_FREQUENCY).append(selectedFrequency)
					.append(linesep);
		}

		if (selected_scale != null) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_SCALE).append(selected_scale).append(linesep);
		}

		if (total_samples != com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_TOTAL_SAMPLES).append(total_samples)
					.append(linesep);
		} else if (total_samples == com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED.value) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_TOTAL_SAMPLES_UNDETERMINED).append(linesep);
		}

		if (selectedChannelParameters != null) {
			strBufOut.append("\t" + ChannelAcquisitionConfig.CHANNEL_PARAMETERS).append(linesep);
			for (final ParameterConfig selectedChannelParameter : selectedChannelParameters) {
				if (selectedChannelParameter != null) {
					strBufOut.append("\t\t").append(selectedChannelParameter).append(linesep);
				}
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
		return channelName;
		// return ReCResourceBundle.findStringOrDefault(this.channelName,
		// this.channelName);
	}

	/**
	 * Setter for property channelName.
	 * 
	 * @param channelName New value of property channelName.
	 */
	public void setChannelName(final String channelName) {
		this.channelName = channelName;
	}

} // class ChannelAcquisitionConfig
