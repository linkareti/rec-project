package com.linkare.rec.data.metadata;

import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.synch.Frequency;
import com.linkare.rec.impl.data.FrequencyUtil;
import com.linkare.rec.impl.exceptions.WrongConfigurationExceptionConstants;

public final class ChannelInfo implements IDLEntity {
	/** Holds value of property frequencies. */
	private FrequencyScale[] frequencies;

	/** Holds value of property scales. */
	private Scale[] scales;

	/** Holds value of property channelName. */
	private String channelName;

	/** Holds value of property actualSelectedScale. */
	public Scale selected_scale = null;

	/** Holds value of property actualSelectedFrequencyIndex. */
	private Frequency selectedFrequency;

	/** Holds value of property channelParameters. */
	private com.linkare.rec.data.metadata.ChannelParameter[] channelParameters;

	/** Holds value of property channelDirection. */
	private com.linkare.rec.data.metadata.ChannelDirection channelDirection;

	/** Holds value of property channelIndependent. */
	private boolean channelIndependent;

	/** Holds value of property samplingScale. */
	private com.linkare.rec.data.metadata.SamplesNumScale samplingScale;

	//
	// Default constructor
	//
	public ChannelInfo() {
	}

	//
	// Constructor with fields initialization
	// @param channel_name channel_name struct member
	// @param scales scales struct member
	// @param actual_selected_scale_index actual_selected_scale_index struct
	// member
	// @param frequencies frequencies struct member
	// @param actual_selected_frequency_index actual_selected_frequency_index
	// struct member
	// @param channel_parameters channel_parameters struct member
	// @param channel_direction channel_direction struct member
	// @param is_channel_independent is_channel_independent struct member
	//
	public ChannelInfo(String channel_name, com.linkare.rec.data.metadata.Scale[] scales, Scale actual_selected_scale,
			com.linkare.rec.data.metadata.FrequencyScale[] frequencies, Frequency selected_frequency,
			com.linkare.rec.data.metadata.ChannelParameter[] channel_parameters,
			com.linkare.rec.data.metadata.ChannelDirection channel_direction, boolean is_channel_independent,
			SamplesNumScale samplingScale) {
		this.setChannelName(channel_name);
		this.setScales(scales);
		this.setActualSelectedScale(actual_selected_scale);
		this.setFrequencies(frequencies);
		this.setSelectedFrequency(selected_frequency);
		this.setChannelParameters(channel_parameters);
		this.setChannelDirection(channel_direction);
		this.setChannelIndependent(is_channel_independent);
		this.setSamplingScale(samplingScale);
	}

	//
	// Copy Constructor
	//
	public ChannelInfo(ChannelInfo other) {
		this.setChannelName(new String(other.getChannelName()));
		Scale[] temp = null;
		if (other.getScales() != null) {
			temp = new Scale[other.getScales().length];
			System.arraycopy(other.getScales(), 0, temp, 0, temp.length);
		}
		this.setScales(temp);
		temp = null;
		System.gc();

		this.setActualSelectedScale(other.getActualSelectedScale());

		FrequencyScale[] temp2 = null;
		if (other.getFrequencies() != null) {
			temp2 = new FrequencyScale[other.getFrequencies().length];
			System.arraycopy(other.getFrequencies(), 0, temp2, 0, temp2.length);
		}

		this.setFrequencies(temp2);
		temp2 = null;
		System.gc();

		this.setSelectedFrequency(other.getSelectedFrequency());

		ChannelParameter[] temp3 = null;
		if (other.getChannelParameters() != null) {
			temp3 = new ChannelParameter[other.getChannelParameters().length];
			System.arraycopy(other.getChannelParameters(), 0, temp3, 0, temp3.length);
		}
		this.setChannelParameters(temp3);
		temp3 = null;
		System.gc();

		this.setChannelDirection(other.getChannelDirection());
		this.setChannelIndependent(other.isChannelIndependent());

		this.setSamplingScale(other.getSamplingScale());
	}

	/**
	 * Getter for property channelName.
	 * 
	 * @return Value of property channelName.
	 */
	public String getChannelName() {
		return this.channelName;
	}

	/**
	 * Setter for property channelName.
	 * 
	 * @param channelName New value of property channelName.
	 */
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	/**
	 * Indexed getter for property scales.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.Scale getScales(int index) {
		return this.scales[index];
	}

	/**
	 * Getter for property scales.
	 * 
	 * @return Value of property scales.
	 */
	public com.linkare.rec.data.metadata.Scale[] getScales() {
		return this.scales;
	}

	/**
	 * Indexed setter for property scales.
	 * 
	 * @param index Index of the property.
	 * @param scales New value of the property at <CODE>index</CODE>.
	 */
	public void setScales(int index, com.linkare.rec.data.metadata.Scale scales) {
		if (this.scales != null && index < this.scales.length)
			this.scales[index] = scales;
		else {
			Scale[] temp = new Scale[index + 1];
			if (this.scales != null)
				System.arraycopy(this.scales, 0, temp, 0, this.scales.length);

			temp[index] = scales;
			this.scales = temp;
			temp = null;
			System.gc();
		}

	}

	public void addScales(com.linkare.rec.data.metadata.Scale scales) {
		if (this.scales == null)
			setScales(0, scales);
		else
			setScales(this.scales.length, scales);
	}

	/**
	 * Setter for property scales.
	 * 
	 * @param scales New value of property scales.
	 */
	public void setScales(com.linkare.rec.data.metadata.Scale[] scales) {
		this.scales = scales;
	}

	/**
	 * Getter for property actualSelectedScale.
	 * 
	 * @return Value of property actualSelectedScale.
	 */
	public Scale getActualSelectedScale() {
		return this.selected_scale;
	}

	/**
	 * Setter for property actualSelectedScaleIndex.
	 * 
	 * @param actualSelectedScale New value of property actualSelectedScale.
	 */
	public void setActualSelectedScale(Scale actualSelectedScale) {
		this.selected_scale = actualSelectedScale;
	}

	/**
	 * Indexed getter for property frequencies.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.FrequencyScale getFrequencies(int index) {
		if (this.frequencies != null && index < this.frequencies.length)
			return this.frequencies[index];

		throw new RuntimeException("No FrequencyScale available at that index...");
	}

	/**
	 * Getter for property frequencies.
	 * 
	 * @return Value of property frequencies.
	 */
	public com.linkare.rec.data.metadata.FrequencyScale[] getFrequencies() {
		return this.frequencies;
	}

	/**
	 * Indexed setter for property frequencies.
	 * 
	 * @param index Index of the property.
	 * @param frequencies New value of the property at <CODE>index</CODE>.
	 */
	public void setFrequencies(int index, com.linkare.rec.data.metadata.FrequencyScale frequencies) {

		if (this.frequencies != null && index < this.frequencies.length)
			this.frequencies[index] = frequencies;
		else {
			FrequencyScale[] temp = new FrequencyScale[index + 1];
			if (this.frequencies != null)
				System.arraycopy(this.frequencies, 0, temp, 0, this.frequencies.length);

			temp[index] = frequencies;
			this.frequencies = temp;
			temp = null;
			System.gc();
		}

	}

	public void addFrequencies(com.linkare.rec.data.metadata.FrequencyScale frequencies) {

		if (this.frequencies == null)
			setFrequencies(0, frequencies);
		else
			setFrequencies(this.frequencies.length, frequencies);

	}

	/**
	 * Setter for property frequencies.
	 * 
	 * @param frequencies New value of property frequencies.
	 */
	public void setFrequencies(com.linkare.rec.data.metadata.FrequencyScale[] frequencies) {
		this.frequencies = frequencies;
	}

	/**
	 * Getter for property actualSelectedFrequencyIndex.
	 * 
	 * @return Value of property actualSelectedFrequencyIndex.
	 */
	public Frequency getSelectedFrequency() {
		return this.selectedFrequency;
	}

	/**
	 * Setter for property actualSelectedFrequencyIndex.
	 * 
	 * @param actualSelectedFrequencyIndex New value of property
	 *            actualSelectedFrequencyIndex.
	 */
	public void setSelectedFrequency(Frequency selectedFrequency) {
		this.selectedFrequency = selectedFrequency;
	}

	/**
	 * Indexed getter for property channelParameters.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.ChannelParameter getChannelParameters(int index) {
		return this.channelParameters[index];
	}

	public com.linkare.rec.data.metadata.ChannelParameter getChannelParameter(String parameter_name) {
		if (this.channelParameters != null && parameter_name != null) {
			for (int i = 0; i < channelParameters.length; i++)
				if (parameter_name.equals(channelParameters[i].getParameterName()))
					return channelParameters[i];
		}
		return null;

	}

	public String getChannelParameterValue(String parameter_name) {
		ChannelParameter param = null;
		if ((param = getChannelParameter(parameter_name)) != null)
			return param.getSelectedParameterValue();

		return null;
	}

	/**
	 * Getter for property channelParameters.
	 * 
	 * @return Value of property channelParameters.
	 */
	public com.linkare.rec.data.metadata.ChannelParameter[] getChannelParameters() {
		return this.channelParameters;
	}

	/**
	 * Indexed setter for property channelParameters.
	 * 
	 * @param index Index of the property.
	 * @param channelParameters New value of the property at <CODE>index</CODE>.
	 */
	public void setChannelParameters(int index, com.linkare.rec.data.metadata.ChannelParameter channelParameters) {
		if (this.channelParameters != null && index < this.channelParameters.length)
			this.channelParameters[index] = channelParameters;
		else {
			ChannelParameter[] temp = new ChannelParameter[index + 1];
			if (this.channelParameters != null)
				System.arraycopy(this.channelParameters, 0, temp, 0, this.channelParameters.length);

			temp[index] = channelParameters;
			this.channelParameters = temp;
			temp = null;
			System.gc();
		}

	}

	public void addChannelParameters(com.linkare.rec.data.metadata.ChannelParameter channelParameters) {
		if (this.channelParameters == null)
			setChannelParameters(0, channelParameters);
		else
			setChannelParameters(this.channelParameters.length, channelParameters);

	}

	/**
	 * Setter for property channelParameters.
	 * 
	 * @param channelParameters New value of property channelParameters.
	 */
	public void setChannelParameters(com.linkare.rec.data.metadata.ChannelParameter[] channelParameters) {
		this.channelParameters = channelParameters;
	}

	/**
	 * Getter for property channelDirection.
	 * 
	 * @return Value of property channelDirection.
	 */
	public com.linkare.rec.data.metadata.ChannelDirection getChannelDirection() {
		return this.channelDirection;
	}

	/**
	 * Setter for property channelDirection.
	 * 
	 * @param channelDirection New value of property channelDirection.
	 */
	public void setChannelDirection(com.linkare.rec.data.metadata.ChannelDirection channelDirection) {
		this.channelDirection = channelDirection;
	}

	/**
	 * Getter for property channelIndependent.
	 * 
	 * @return Value of property channelIndependent.
	 */
	public boolean isChannelIndependent() {
		return this.channelIndependent;
	}

	/**
	 * Setter for property channelIndependent.
	 * 
	 * @param channelIndependent New value of property channelIndependent.
	 */
	public void setChannelIndependent(boolean channelIndependent) {
		this.channelIndependent = channelIndependent;
	}

	/**
	 * Getter for property samplingScale.
	 * 
	 * @return Value of property samplingScale.
	 */
	public com.linkare.rec.data.metadata.SamplesNumScale getSamplingScale() {
		return this.samplingScale;
	}

	/**
	 * Setter for property samplingScale.
	 * 
	 * @param samplingScale New value of property samplingScale.
	 */
	public void setSamplingScale(com.linkare.rec.data.metadata.SamplesNumScale samplingScale) {
		this.samplingScale = samplingScale;
	}

	/*
	 * public void updateFromChannelAcquisitionHeader(ChannelAcquisitionConfig
	 * ch_acq_header) { for(int i=0;i<channelParameters.length;i++) {
	 * channelParameters
	 * [i].setSelectedParameterValue(ch_acq_header.getSelectedChannelParameterValue
	 * (channelParameters[i].getParameterName())); } }
	 */

	public void validateConfig(ChannelAcquisitionConfig config) throws WrongConfigurationException {
		// check selected frequency

		// TODO:JP
		boolean freq_ok = false;
		if (config.getSelectedFrequency() == null) {
			// frequency not defined... don't know better
			freq_ok = true;
		} else if (frequencies == null) {
			// don't know better
			freq_ok = true;

		} else {
			for (int i = 0; i < frequencies.length && !freq_ok; i++) {
				freq_ok = freq_ok || FrequencyUtil.isInScale(config.getSelectedFrequency(), frequencies[i]);
			}
		}

		if (!freq_ok)
			throw new WrongConfigurationException(WrongConfigurationExceptionConstants.FREQUENCY_NOT_IN_SCALES);

		boolean params_ok = true;
		if (config.getSelectedChannelParameters() == null) {
			// params not defined... don't know better
			params_ok = true;
		} else if (channelParameters == null) {
			// don't know better
			params_ok = true;
		} else {
			for (int i = 0; i < channelParameters.length && params_ok; i++) {
				String param_value = config.getSelectedChannelParameterValue(channelParameters[i].getParameterName());
				if (param_value != null)
					params_ok = params_ok && channelParameters[i].isSelectedValueValid(param_value);
			}
		}

		if (!params_ok)
			throw new WrongConfigurationException(WrongConfigurationExceptionConstants.PARAMETER_INVALID);

		// check bigger then now, at least, or null
		// not now! Maybe at a later time I'll have time to do it!
		// config.getTimeStart();

		// check total_samples with samplingScale
		int total_samples = config.getTotalSamples();

		if (samplingScale != null)
			if (!(total_samples >= samplingScale.getMinSamples() && total_samples <= samplingScale.getMaxSamples() && ((total_samples - samplingScale
					.getMinSamples()) % samplingScale.getStep()) == 0))
				throw new WrongConfigurationException(WrongConfigurationExceptionConstants.SAMPLING_SCALE_INVALID);

		com.linkare.rec.data.metadata.Scale scale_selected_in_conf = config.getSelectedScale();
		if (scale_selected_in_conf == null)
			return;

		boolean scales_ok = false;
		for (int i = 0; i < scales.length && !scales_ok; i++) {
			scales_ok = scales_ok || scale_selected_in_conf.equals(scales[i]);
		}
		if (!scales_ok)
			throw new WrongConfigurationException(WrongConfigurationExceptionConstants.SCALE_INVALID);

	}

	public ChannelAcquisitionConfig createBaseChannelConfig(HardwareInfo info) {
		// check selected frequency
		ChannelAcquisitionConfig config = new ChannelAcquisitionConfig();

		if (frequencies != null && frequencies.length > 0 && frequencies[0] != null)
			config.setSelectedFrequency(frequencies[0].getMinimumFrequency());

		if (channelParameters != null)
			for (int i = 0; i < channelParameters.length; i++)
				config.addSelectedChannelParameter(new ParameterConfig(channelParameters[i].getParameterName(),
						channelParameters[i].getSelectedParameterValue()));

		config.setChannelName(getChannelName());

		if (samplingScale != null)
			config.setTotalSamples(samplingScale.getMinSamples());
		else if (info.getSamplingScale() != null)
			config.setTotalSamples(info.getSamplingScale().getMinSamples());

		if (scales != null && scales.length > 0 && scales[0] != null)
			config.setSelectedScale(scales[0]);

		// TODO - Commented out by JP to check serialization of nulls
		// should revert as soon as debug ends...
		// config.setTimeStart(new DateTime());

		return config;
	}

} // class ChannelInfo
