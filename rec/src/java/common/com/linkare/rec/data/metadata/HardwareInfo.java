package com.linkare.rec.data.metadata;

import java.util.Arrays;

import com.linkare.rec.acquisition.WrongConfigurationException;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.impl.data.FrequencyUtil;
import com.linkare.rec.impl.exceptions.WrongConfigurationExceptionConstants;
import com.linkare.rec.impl.utils.ValidationErrors;

public final class HardwareInfo implements org.omg.CORBA.portable.IDLEntity {

	/**
     * 
     */
	private static final long serialVersionUID = 2621083036892229458L;
	/** Holds value of property driverVersion. */
	private String driverVersion;
	/** Holds value of property hardwareName. */
	private String hardwareName;
	/** Holds value of property hardwareVersion. */
	private String hardwareVersion;
	/** Holds value of property hardwareManufacturer. */
	private String hardwareManufacturer;
	/** Holds value of property descriptionText. */
	private String descriptionText;
	/** Holds value of property hardwareUniqueID. */
	private String hardwareUniqueID;
	/** Holds value of property familiarName. */
	private String familiarName = null;
	/** Holds value of property channelsInfo. */
	private com.linkare.rec.data.metadata.ChannelInfo[] channelsInfo;
	/** Holds value of property hardwareParameters. */
	private com.linkare.rec.data.metadata.ChannelParameter[] hardwareParameters;
	/** Holds value of property hardwareFrequencies. */
	private com.linkare.rec.data.metadata.FrequencyScale[] hardwareFrequencies;
	/** Holds value of property selectedFrequency. */
	private com.linkare.rec.data.synch.Frequency selectedFrequency;
	/** Holds value of property samplingScale. */
	private com.linkare.rec.data.metadata.SamplesNumScale samplingScale;

	/**
	 * Default constructor
	 */
	public HardwareInfo() {
	}

	/**
	 * Constructor with fields initialization
	 * 
	 * @param familiarName
	 * 
	 * @param FamiliarName FamiliarName struct member
	 * @param DriverVersion DriverVersion struct member
	 * @param HardwareName HardwareName struct member
	 * @param HardwareVersion HardwareVersion struct member
	 * @param HardwareManufacturer HardwareManufacturer struct member
	 * @param DescriptionText DescriptionText struct member
	 * @param url_customizer_class
	 * @param hardware_unique_id hardware_unique_id struct member
	 * @param channels_info channels_info struct member
	 * @param hardware_parameters hardware_parameters struct member
	 * @param hardware_frequencies hardware_frequencies struct member
	 * @param selectedFrequency
	 * @param samplingScale
	 */
	public HardwareInfo(final String familiarName, final String DriverVersion, final String HardwareName,
			final String HardwareVersion, final String HardwareManufacturer, final String DescriptionText,
			final String url_customizer_class, final String hardware_unique_id,
			final com.linkare.rec.data.metadata.ChannelInfo[] channels_info,
			final com.linkare.rec.data.metadata.ChannelParameter[] hardware_parameters,
			final com.linkare.rec.data.metadata.FrequencyScale[] hardware_frequencies,
			final com.linkare.rec.data.synch.Frequency selectedFrequency, final SamplesNumScale samplingScale) {
		setFamiliarName(familiarName);
		setDriverVersion(DriverVersion);
		setHardwareName(HardwareName);
		setHardwareVersion(HardwareVersion);
		setHardwareManufacturer(HardwareManufacturer);
		setDescriptionText(DescriptionText);
		setHardwareUniqueID(hardware_unique_id);
		this.setChannelsInfo(channels_info);
		this.setHardwareParameters(hardware_parameters);
		this.setHardwareFrequencies(hardware_frequencies);
		setSelectedFrequency(selectedFrequency);
		setSamplingScale(samplingScale);
	}

	/**
	 * Copy Constructor - This creates a deep copy of the {@link HardwareInfo}
	 * object
	 * 
	 * @param other The other {@link HardwareInfo} to copy from
	 */
	public HardwareInfo(final HardwareInfo other) {
		setFamiliarName(other.getFamiliarName());
		setDriverVersion(other.getDriverVersion());
		setHardwareName(other.getHardwareName());
		setHardwareVersion(other.getHardwareVersion());
		setHardwareManufacturer(other.getHardwareManufacturer());
		setDescriptionText(other.getDescriptionText());
		setHardwareUniqueID(other.getHardwareUniqueID());

		ChannelInfo[] temp = null;
		if (other.getChannelsInfo() != null) {
			temp = new ChannelInfo[other.getChannelsInfo().length];
			System.arraycopy(other.getChannelsInfo(), 0, temp, 0, temp.length);
		}
		this.setChannelsInfo(temp);
		temp = null;

		ChannelParameter[] temp2 = null;
		if (other.getHardwareParameters() != null) {
			temp2 = new ChannelParameter[other.getHardwareParameters().length];
			System.arraycopy(other.getHardwareParameters(), 0, temp2, 0, temp2.length);
		}
		this.setHardwareParameters(temp2);
		temp2 = null;

		FrequencyScale[] temp3 = null;
		if (other.getHardwareFrequencies() != null) {
			temp3 = new FrequencyScale[other.getHardwareFrequencies().length];
			System.arraycopy(other.getHardwareFrequencies(), 0, temp3, 0, temp3.length);
		}
		this.setHardwareFrequencies(temp3);
		temp3 = null;

		setSelectedFrequency(other.getSelectedFrequency());

		setSamplingScale(other.getSamplingScale());
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
	 * Getter for property driverVersion.
	 * 
	 * @return Value of property driverVersion.
	 */
	public String getDriverVersion() {
		return driverVersion;
	}

	/**
	 * Setter for property driverVersion.
	 * 
	 * @param driverVersion New value of property driverVersion.
	 */
	public void setDriverVersion(final String driverVersion) {
		this.driverVersion = driverVersion;
	}

	/**
	 * Getter for property hardwareName.
	 * 
	 * @return Value of property hardwareName.
	 */
	public String getHardwareName() {
		return hardwareName;
	}

	/**
	 * Setter for property hardwareName.
	 * 
	 * @param hardwareName New value of property hardwareName.
	 */
	public void setHardwareName(final String hardwareName) {
		this.hardwareName = hardwareName;
	}

	/**
	 * Getter for property hardwareVersion.
	 * 
	 * @return Value of property hardwareVersion.
	 */
	public String getHardwareVersion() {
		return hardwareVersion;
	}

	/**
	 * Setter for property hardwareVersion.
	 * 
	 * @param hardwareVersion New value of property hardwareVersion.
	 */
	public void setHardwareVersion(final String hardwareVersion) {
		this.hardwareVersion = hardwareVersion;
	}

	/**
	 * Getter for property hardwareManufacturer.
	 * 
	 * @return Value of property hardwareManufacturer.
	 */
	public String getHardwareManufacturer() {
		return hardwareManufacturer;
	}

	/**
	 * Setter for property hardwareManufacturer.
	 * 
	 * @param hardwareManufacturer New value of property hardwareManufacturer.
	 */
	public void setHardwareManufacturer(final String hardwareManufacturer) {
		this.hardwareManufacturer = hardwareManufacturer;
	}

	/**
	 * Getter for property descriptionText.
	 * 
	 * @return Value of property descriptionText.
	 */
	public String getDescriptionText() {
		return descriptionText;
	}

	/**
	 * Setter for property descriptionText.
	 * 
	 * @param descriptionText New value of property descriptionText.
	 */
	public void setDescriptionText(final String descriptionText) {
		this.descriptionText = descriptionText;
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
	 * Indexed getter for property channelsInfo.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.ChannelInfo getChannelsInfo(final int index) {
		if (channelsInfo != null && index < channelsInfo.length) {
			return channelsInfo[index];
		}

		throw new RuntimeException("No ChannelInfo at that index...");
	}

	/**
	 * Getter for property channelsInfo.
	 * 
	 * @return Value of property channelsInfo.
	 */
	public com.linkare.rec.data.metadata.ChannelInfo[] getChannelsInfo() {
		return channelsInfo;
	}

	/**
	 * Indexed setter for property channelsInfo.
	 * 
	 * @param index Index of the property.
	 * @param channelsInfo New value of the property at <CODE>index</CODE>.
	 */
	public void setChannelsInfo(final int index, final com.linkare.rec.data.metadata.ChannelInfo channelsInfo) {
		if (this.channelsInfo != null && index < this.channelsInfo.length) {
			this.channelsInfo[index] = channelsInfo;
		} else {
			ChannelInfo[] temp = new ChannelInfo[index + 1];
			if (this.channelsInfo != null) {
				System.arraycopy(this.channelsInfo, 0, temp, 0, this.channelsInfo.length);
			}
			temp[index] = channelsInfo;
			this.channelsInfo = temp;
			temp = null;
		}
	}

	public void addChannelsInfo(final com.linkare.rec.data.metadata.ChannelInfo channelsInfo) {
		if (this.channelsInfo == null) {
			setChannelsInfo(0, channelsInfo);
		} else {
			setChannelsInfo(this.channelsInfo.length, channelsInfo);
		}
	}

	/**
	 * Setter for property channelsInfo.
	 * 
	 * @param channelsInfo New value of property channelsInfo.
	 */
	public void setChannelsInfo(final com.linkare.rec.data.metadata.ChannelInfo[] channelsInfo) {
		this.channelsInfo = channelsInfo;
	}

	/**
	 * Indexed getter for property hardwareParameters.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.ChannelParameter getHardwareParameters(final int index) {
		if (hardwareParameters != null && index < hardwareParameters.length) {
			return hardwareParameters[index];
		}

		throw new RuntimeException("No HardwareParameter at that index...");
	}

	public com.linkare.rec.data.metadata.ChannelParameter getHardwareParameter(final String parameter_name) {
		if (hardwareParameters != null && parameter_name != null) {
			for (final ChannelParameter hardwareParameter : hardwareParameters) {
				if (parameter_name.equals(hardwareParameter.getParameterName())) {
					return hardwareParameter;
				}
			}
		}
		return null;

	}

	public String getHardwareParameterValue(final String parameter_name) {
		ChannelParameter param = null;
		if ((param = getHardwareParameter(parameter_name)) != null) {
			return param.getSelectedParameterValue();
		}

		return null;
	}

	/**
	 * Getter for property hardwareParameters.
	 * 
	 * @return Value of property hardwareParameters.
	 */
	public com.linkare.rec.data.metadata.ChannelParameter[] getHardwareParameters() {
		return hardwareParameters;
	}

	/**
	 * Indexed setter for property hardwareParameters.
	 * 
	 * @param index Index of the property.
	 * @param hardwareParameters New value of the property at <CODE>index</CODE>
	 *            .
	 */
	public void setHardwareParameters(final int index,
			final com.linkare.rec.data.metadata.ChannelParameter hardwareParameters) {
		if (this.hardwareParameters != null && index < this.hardwareParameters.length) {
			this.hardwareParameters[index] = hardwareParameters;
		} else {
			ChannelParameter[] temp = new ChannelParameter[index + 1];
			if (this.hardwareParameters != null) {
				System.arraycopy(this.hardwareParameters, 0, temp, 0, this.hardwareParameters.length);
			}
			temp[index] = hardwareParameters;
			this.hardwareParameters = temp;
			temp = null;
		}
	}

	public void addHardwareParameters(final com.linkare.rec.data.metadata.ChannelParameter hardwareParameters) {
		if (this.hardwareParameters == null) {
			setHardwareParameters(0, hardwareParameters);
		} else {
			setHardwareParameters(this.hardwareParameters.length, hardwareParameters);
		}
	}

	/**
	 * Setter for property hardwareParameters.
	 * 
	 * @param hardwareParameters New value of property hardwareParameters.
	 */
	public void setHardwareParameters(final com.linkare.rec.data.metadata.ChannelParameter[] hardwareParameters) {
		this.hardwareParameters = hardwareParameters;
	}

	/**
	 * Indexed getter for property hardwareFrequencies.
	 * 
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.FrequencyScale getHardwareFrequencies(final int index) {
		if (hardwareFrequencies != null && index < hardwareFrequencies.length) {
			return hardwareFrequencies[index];
		}

		throw new RuntimeException("No FrequencyScale at that index...");
	}

	/**
	 * Getter for property hardwareFrequencies.
	 * 
	 * @return Value of property hardwareFrequencies.
	 */
	public com.linkare.rec.data.metadata.FrequencyScale[] getHardwareFrequencies() {
		return hardwareFrequencies;
	}

	/**
	 * Indexed setter for property hardwareFrequencies.
	 * 
	 * @param index Index of the property.
	 * @param hardwareFrequencies New value of the property at
	 *            <CODE>index</CODE>.
	 */
	public void setHardwareFrequencies(final int index,
			final com.linkare.rec.data.metadata.FrequencyScale hardwareFrequencies) {
		if (this.hardwareFrequencies != null && index < this.hardwareFrequencies.length) {
			this.hardwareFrequencies[index] = hardwareFrequencies;
		} else {
			FrequencyScale[] temp = new FrequencyScale[index + 1];
			if (this.hardwareFrequencies != null) {
				System.arraycopy(this.hardwareFrequencies, 0, temp, 0, this.hardwareFrequencies.length);
			}
			temp[index] = hardwareFrequencies;
			this.hardwareFrequencies = temp;
			temp = null;
		}

	}

	public void addHardwareFrequencies(final com.linkare.rec.data.metadata.FrequencyScale hardwareFrequencies) {
		if (this.hardwareFrequencies == null) {
			setHardwareFrequencies(0, hardwareFrequencies);
		} else {
			setHardwareFrequencies(this.hardwareFrequencies.length, hardwareFrequencies);
		}

	}

	/**
	 * Setter for property hardwareFrequencies.
	 * 
	 * @param hardwareFrequencies New value of property hardwareFrequencies.
	 */
	public void setHardwareFrequencies(final com.linkare.rec.data.metadata.FrequencyScale[] hardwareFrequencies) {

		this.hardwareFrequencies = hardwareFrequencies;
	}

	/**
	 * Getter for property samplingScale.
	 * 
	 * @return Value of property samplingScale.
	 */
	public com.linkare.rec.data.metadata.SamplesNumScale getSamplingScale() {
		return samplingScale;
	}

	/**
	 * Setter for property samplingScale.
	 * 
	 * @param samplingScale New value of property samplingScale.
	 */
	public void setSamplingScale(final com.linkare.rec.data.metadata.SamplesNumScale samplingScale) {
		this.samplingScale = samplingScale;
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

	/*
	 * public void updateFromHardwareAcquisitionConfig(HardwareAcquisitionConfig
	 * acq_header) { for(int i=0;i<hardwareParameters.length;i++) {
	 * hardwareParameters
	 * [i].setSelectedParameterValue(acq_header.getSelectedHardwareParameterValue
	 * (hardwareParameters[i].getParameterName())); } for(int
	 * i=0;i<channelsInfo.length;i++) {
	 * channelsInfo[i].updateFromChannelAcquisitionHeader
	 * (acq_header.getSamplesInfo(i)); } }
	 */
	public void validateConfig(final HardwareAcquisitionConfig config) throws WrongConfigurationException {
		
		ValidationErrors errors = new ValidationErrors();

		// check selected frequency
		if (config.getSelectedFrequency() != null && hardwareFrequencies != null) {
			boolean freq_ok = false;
			for (int i = 0; i < hardwareFrequencies.length && !freq_ok; i++) {
				freq_ok = freq_ok || FrequencyUtil.isInScale(config.getSelectedFrequency(), hardwareFrequencies[i]);
			}
			if (!freq_ok) {
				errors.addError(
						"frequency = " + config.getSelectedFrequency() + ", scales "
								+ Arrays.deepToString(hardwareFrequencies),
						WrongConfigurationExceptionConstants.FREQUENCY_NOT_IN_SCALES);
			}
		}

		// check selected parameters
		if (config.getSelectedHardwareParameters() != null && hardwareParameters != null) {
			for (int i = 0; i < hardwareParameters.length; i++) {
				String parameterName = hardwareParameters[i].getParameterName();
				final String paramValue = config.getSelectedHardwareParameterValue(parameterName);
				if (paramValue != null) {
					boolean paramOk = hardwareParameters[i].isSelectedValueValid(paramValue);
					if (!paramOk) {
						errors.addError("Parameter '" + parameterName + "' with value '" + paramValue + "' is invalid "
								+ hardwareParameters[i], WrongConfigurationExceptionConstants.PARAMETER_INVALID);
					}
				}
			}
		}

		// check total_samples with samplingScale
		final int totalSamples = config.getTotalSamples();

		if (!(totalSamples >= samplingScale.getMinSamples() && totalSamples <= samplingScale.getMaxSamples() && ((totalSamples - samplingScale
				.getMinSamples()) % samplingScale.getStep()) == 0)) {
			errors.addError(
					"total samples " + totalSamples + " is not in the correct scale [" + samplingScale.getMinSamples()
							+ "," + samplingScale.getMaxSamples() + "]",
					WrongConfigurationExceptionConstants.SAMPLING_SCALE_INVALID);
		}

		if (config.getChannelsConfig() != null && channelsInfo != null) {
			for (int i = 0; i < channelsInfo.length && i < config.getChannelsConfig().length; i++) {
				channelsInfo[i].validateConfig(config.getChannelsConfig(i), errors);
			}
		}

		if (errors.size() > 0) {
			throw new WrongConfigurationException(errors.toString(), WrongConfigurationExceptionConstants.SCALE_INVALID);
		}

	}

	public HardwareAcquisitionConfig createBaseHardwareAcquisitionConfig() {
		// check selected frequency
		final HardwareAcquisitionConfig config = new HardwareAcquisitionConfig();

		if (hardwareFrequencies != null && hardwareFrequencies.length > 0 && hardwareFrequencies[0] != null) {
			config.setSelectedFrequency(hardwareFrequencies[0].getMinimumFrequency());
		}

		if (hardwareParameters != null) {
			for (final ChannelParameter hardwareParameter : hardwareParameters) {
				config.addSelectedHardwareParameter(new ParameterConfig(hardwareParameter.getParameterName(),
						hardwareParameter.getSelectedParameterValue()));
			}
		}

		config.setTotalSamples(samplingScale.getMinSamples());
		config.setFamiliarName(getFamiliarName());
		config.setHardwareUniqueID(getHardwareUniqueID());
		config.setTimeStart(new com.linkare.rec.data.synch.DateTime());

		if (channelsInfo != null) {
			for (final ChannelInfo element : channelsInfo) {
				if (element != null) {
					config.addChannelConfig(element.createBaseChannelConfig(this));
				}
			}
		}

		return config;
	}
} // class HardwareInfo
