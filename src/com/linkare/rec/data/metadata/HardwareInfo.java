package com.linkare.rec.data.metadata;

import com.linkare.rec.impl.data.FrequencyUtil;
import com.linkare.rec.impl.data.PhysicsValueUtil;
import com.linkare.rec.data.config.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.impl.exceptions.*;

public final class HardwareInfo implements org.omg.CORBA.portable.IDLEntity
{
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
	public HardwareInfo()
	{ }
	
	/**
	 * Constructor with fields initialization
	 * @param	FamiliarName	FamiliarName struct member
	 * @param	DriverVersion	DriverVersion struct member
	 * @param	HardwareName	HardwareName struct member
	 * @param	HardwareVersion	HardwareVersion struct member
	 * @param	HardwareManufacturer	HardwareManufacturer struct member
	 * @param	DescriptionText	DescriptionText struct member
	 * @param	hardware_unique_id	hardware_unique_id struct member
	 * @param	channels_info	channels_info struct member
	 * @param	hardware_parameters	hardware_parameters struct member
	 * @param	hardware_frequencies	hardware_frequencies struct member
	 */
	public HardwareInfo(String familiarName, String DriverVersion, String HardwareName, String HardwareVersion, String HardwareManufacturer, String DescriptionText, String url_customizer_class, String hardware_unique_id, com.linkare.rec.data.metadata.ChannelInfo[] channels_info, com.linkare.rec.data.metadata.ChannelParameter[] hardware_parameters, com.linkare.rec.data.metadata.FrequencyScale[] hardware_frequencies,com.linkare.rec.data.synch.Frequency selectedFrequency,SamplesNumScale samplingScale)
	{
		this.setFamiliarName(familiarName);
		this.setDriverVersion(DriverVersion);
		this.setHardwareName(HardwareName);
		this.setHardwareVersion(HardwareVersion);
		this.setHardwareManufacturer(HardwareManufacturer);
		this.setDescriptionText(DescriptionText);
		this.setHardwareUniqueID(hardware_unique_id);
		this.setChannelsInfo(channels_info);
		this.setHardwareParameters(hardware_parameters);
		this.setHardwareFrequencies(hardware_frequencies);
		this.setSelectedFrequency(selectedFrequency);
		this.setSamplingScale(samplingScale);
	}
	
	/**
	 * Copy Constructor
	 */
	public HardwareInfo(HardwareInfo other)
	{
		this.setFamiliarName(other.getFamiliarName());
		this.setDriverVersion(other.getDriverVersion());
		this.setHardwareName(other.getHardwareName());
		this.setHardwareVersion(other.getHardwareVersion());
		this.setHardwareManufacturer(other.getHardwareManufacturer());
		this.setDescriptionText(other.getDescriptionText());
		this.setHardwareUniqueID(other.getHardwareUniqueID());
		
		ChannelInfo[] temp=null;
		if(other.getChannelsInfo()!=null)
		{
			temp=new ChannelInfo[other.getChannelsInfo().length];
			System.arraycopy(other.getChannelsInfo(),0,temp,0,temp.length);
		}
		this.setChannelsInfo(temp);temp=null;System.gc();
		
		ChannelParameter[] temp2=null;
		if(other.getHardwareParameters()!=null)
		{
			temp2=new ChannelParameter[other.getHardwareParameters().length];
			System.arraycopy(other.getHardwareParameters(),0,temp2,0,temp2.length);
		}
		this.setHardwareParameters(temp2);temp2=null;System.gc();
		
		FrequencyScale[] temp3=null;
		if(other.getHardwareFrequencies()!=null)
		{
			temp3=new FrequencyScale[other.getHardwareFrequencies().length];
			System.arraycopy(other.getHardwareFrequencies(),0,temp3,0,temp3.length);
		}
		this.setHardwareFrequencies(temp3);temp3=null;System.gc();
		
		this.setSelectedFrequency(other.getSelectedFrequency());
		
		this.setSamplingScale(other.getSamplingScale());
	}
	
	/** Getter for property familiarName.
	 * @return Value of property familiarName.
	 */
	public String getFamiliarName()
	{
		return this.familiarName;
	}
	
	/** Setter for property familiarName.
	 * @param familiarName New value of property familiarName.
	 */
	public void setFamiliarName(String familiarName)
	{
		this.familiarName= familiarName;
	}
	
	
	/** Getter for property driverVersion.
	 * @return Value of property driverVersion.
	 */
	public String getDriverVersion()
	{
		return this.driverVersion;
	}
	
	/** Setter for property driverVersion.
	 * @param driverVersion New value of property driverVersion.
	 */
	public void setDriverVersion(String driverVersion)
	{
		this.driverVersion = driverVersion;
	}
	
	/** Getter for property hardwareName.
	 * @return Value of property hardwareName.
	 */
	public String getHardwareName()
	{
		return this.hardwareName;
	}
	
	/** Setter for property hardwareName.
	 * @param hardwareName New value of property hardwareName.
	 */
	public void setHardwareName(String hardwareName)
	{
		this.hardwareName = hardwareName;
	}
	
	/** Getter for property hardwareVersion.
	 * @return Value of property hardwareVersion.
	 */
	public String getHardwareVersion()
	{
		return this.hardwareVersion;
	}
	
	/** Setter for property hardwareVersion.
	 * @param hardwareVersion New value of property hardwareVersion.
	 */
	public void setHardwareVersion(String hardwareVersion)
	{
		this.hardwareVersion = hardwareVersion;
	}
	
	/** Getter for property hardwareManufacturer.
	 * @return Value of property hardwareManufacturer.
	 */
	public String getHardwareManufacturer()
	{
		return this.hardwareManufacturer;
	}
	
	/** Setter for property hardwareManufacturer.
	 * @param hardwareManufacturer New value of property hardwareManufacturer.
	 */
	public void setHardwareManufacturer(String hardwareManufacturer)
	{
		this.hardwareManufacturer = hardwareManufacturer;
	}
	
	/** Getter for property descriptionText.
	 * @return Value of property descriptionText.
	 */
	public String getDescriptionText()
	{
		return this.descriptionText;
	}
	
	/** Setter for property descriptionText.
	 * @param descriptionText New value of property descriptionText.
	 */
	public void setDescriptionText(String descriptionText)
	{
		this.descriptionText = descriptionText;
	}
	
	/** Getter for property hardwareUniqueID.
	 * @return Value of property hardwareUniqueID.
	 */
	public String getHardwareUniqueID()
	{
		return this.hardwareUniqueID;
	}
	
	/** Setter for property hardwareUniqueID.
	 * @param hardwareUniqueID New value of property hardwareUniqueID.
	 */
	public void setHardwareUniqueID(String hardwareUniqueID)
	{
		this.hardwareUniqueID = hardwareUniqueID;
	}
	
	/** Indexed getter for property channelsInfo.
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.ChannelInfo getChannelsInfo(int index)
	{
		if(this.channelsInfo!=null && index<this.channelsInfo.length)
			return this.channelsInfo[index];
		
		throw new RuntimeException("No ChannelInfo at that index...");
	}
	
	/** Getter for property channelsInfo.
	 * @return Value of property channelsInfo.
	 */
	public com.linkare.rec.data.metadata.ChannelInfo[] getChannelsInfo()
	{
		return this.channelsInfo;
	}
	
	/** Indexed setter for property channelsInfo.
	 * @param index Index of the property.
	 * @param channelsInfo New value of the property at <CODE>index</CODE>.
	 */
	public void setChannelsInfo(int index, com.linkare.rec.data.metadata.ChannelInfo channelsInfo)
	{
		if(this.channelsInfo!=null && index<this.channelsInfo.length)
			this.channelsInfo[index] = channelsInfo;
		else
		{
			ChannelInfo[] temp=new ChannelInfo[index+1];
			if(this.channelsInfo!=null)
				System.arraycopy(this.channelsInfo,0,temp,0,this.channelsInfo.length);
			temp[index]=channelsInfo;
			this.channelsInfo=temp;temp=null;System.gc();
		}
	}
	
	public void addChannelsInfo(com.linkare.rec.data.metadata.ChannelInfo channelsInfo)
	{
		if(this.channelsInfo==null)
			setChannelsInfo(0,channelsInfo);
		else
			setChannelsInfo(this.channelsInfo.length,channelsInfo);
	}
	/** Setter for property channelsInfo.
	 * @param channelsInfo New value of property channelsInfo.
	 */
	public void setChannelsInfo(com.linkare.rec.data.metadata.ChannelInfo[] channelsInfo)
	{
		this.channelsInfo = channelsInfo;
	}
	
	/** Indexed getter for property hardwareParameters.
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.ChannelParameter getHardwareParameters(int index)
	{
		if(this.hardwareParameters!=null && index<this.hardwareParameters.length)
			return this.hardwareParameters[index];
		
		throw new RuntimeException("No HardwareParameter at that index...");
	}
	
	public com.linkare.rec.data.metadata.ChannelParameter getHardwareParameter(String parameter_name)
	{
		if(this.hardwareParameters!=null && parameter_name!=null)
		{
			for(int i=0;i<hardwareParameters.length;i++)
				if(parameter_name.equals(hardwareParameters[i].getParameterName()))
					return hardwareParameters[i];
		}
		return null;
		
	}
	
	public String getHardwareParameterValue(String parameter_name)
	{
		ChannelParameter param=null;
		if((param=getHardwareParameter(parameter_name))!=null)
			return param.getSelectedParameterValue();
		
		return null;
	}
	
	
	
	/** Getter for property hardwareParameters.
	 * @return Value of property hardwareParameters.
	 */
	public com.linkare.rec.data.metadata.ChannelParameter[] getHardwareParameters()
	{
		return this.hardwareParameters;
	}
	
	/** Indexed setter for property hardwareParameters.
	 * @param index Index of the property.
	 * @param hardwareParameters New value of the property at <CODE>index</CODE>.
	 */
	public void setHardwareParameters(int index, com.linkare.rec.data.metadata.ChannelParameter hardwareParameters)
	{
		if(this.hardwareParameters!=null && index<this.hardwareParameters.length)
			this.hardwareParameters[index] = hardwareParameters;
		else
		{
			ChannelParameter[] temp=new ChannelParameter[index+1];
			if(this.hardwareParameters!=null)
				System.arraycopy(this.hardwareParameters,0,temp,0,this.hardwareParameters.length);
			temp[index]=hardwareParameters;
			this.hardwareParameters=temp;temp=null;System.gc();
		}
	}
	
	
	public void addHardwareParameters(com.linkare.rec.data.metadata.ChannelParameter hardwareParameters)
	{
		if(this.hardwareParameters==null)
			setHardwareParameters(0,hardwareParameters);
		else
			setHardwareParameters(this.hardwareParameters.length,hardwareParameters);
	}
	
	
	/** Setter for property hardwareParameters.
	 * @param hardwareParameters New value of property hardwareParameters.
	 */
	public void setHardwareParameters(com.linkare.rec.data.metadata.ChannelParameter[] hardwareParameters)
	{
		this.hardwareParameters = hardwareParameters;
	}
	
	/** Indexed getter for property hardwareFrequencies.
	 * @param index Index of the property.
	 * @return Value of the property at <CODE>index</CODE>.
	 */
	public com.linkare.rec.data.metadata.FrequencyScale getHardwareFrequencies(int index)
	{
		if(this.hardwareFrequencies!=null && index<this.hardwareFrequencies.length)
			return this.hardwareFrequencies[index];
		
		throw new RuntimeException("No FrequencyScale at that index...");
	}
	
	/** Getter for property hardwareFrequencies.
	 * @return Value of property hardwareFrequencies.
	 */
	public com.linkare.rec.data.metadata.FrequencyScale[] getHardwareFrequencies()
	{
		return this.hardwareFrequencies;
	}
	
	/** Indexed setter for property hardwareFrequencies.
	 * @param index Index of the property.
	 * @param hardwareFrequencies New value of the property at <CODE>index</CODE>.
	 */
	public void setHardwareFrequencies(int index, com.linkare.rec.data.metadata.FrequencyScale hardwareFrequencies)
	{
		if(this.hardwareFrequencies !=null && index<this.hardwareFrequencies .length)
			this.hardwareFrequencies [index] = hardwareFrequencies ;
		else
		{
			FrequencyScale[] temp=new FrequencyScale[index+1];
			if(this.hardwareFrequencies !=null)
				System.arraycopy(this.hardwareFrequencies ,0,temp,0,this.hardwareFrequencies.length);
			temp[index]=hardwareFrequencies ;
			this.hardwareFrequencies=temp;temp=null;System.gc();
		}
		
	}
	
	public void addHardwareFrequencies(com.linkare.rec.data.metadata.FrequencyScale hardwareFrequencies)
	{
		if(this.hardwareFrequencies==null)
			setHardwareFrequencies(0,hardwareFrequencies);
		else
			setHardwareFrequencies(this.hardwareFrequencies.length,hardwareFrequencies);
		
	}
	
	/** Setter for property hardwareFrequencies.
	 * @param hardwareFrequencies New value of property hardwareFrequencies.
	 */
	public void setHardwareFrequencies(com.linkare.rec.data.metadata.FrequencyScale[] hardwareFrequencies)
	{
		
		this.hardwareFrequencies = hardwareFrequencies;
	}
	
	/** Getter for property samplingScale.
	 * @return Value of property samplingScale.
	 */
	public com.linkare.rec.data.metadata.SamplesNumScale getSamplingScale()
	{
		return this.samplingScale;
	}
	
	/** Setter for property samplingScale.
	 * @param samplingScale New value of property samplingScale.
	 */
	public void setSamplingScale(com.linkare.rec.data.metadata.SamplesNumScale samplingScale)
	{
		this.samplingScale = samplingScale;
	}
	
	/** Getter for property selectedFrequency.
	 * @return Value of property selectedFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getSelectedFrequency()
	{
		return this.selectedFrequency;
	}
	
	/** Setter for property selectedFrequency.
	 * @param selectedFrequency New value of property selectedFrequency.
	 */
	public void setSelectedFrequency(com.linkare.rec.data.synch.Frequency selectedFrequency)
	{
		this.selectedFrequency = selectedFrequency;
	}
	
	
	/*public void updateFromHardwareAcquisitionConfig(HardwareAcquisitionConfig acq_header)
	{
		for(int i=0;i<hardwareParameters.length;i++)
		{
		hardwareParameters[i].setSelectedParameterValue(acq_header.getSelectedHardwareParameterValue(hardwareParameters[i].getParameterName()));
		}
		for(int i=0;i<channelsInfo.length;i++)
		{
			channelsInfo[i].updateFromChannelAcquisitionHeader(acq_header.getSamplesInfo(i));
		}
	}*/
	
	public void validateConfig(HardwareAcquisitionConfig config)
	throws WrongConfigurationException
	{
		//check selected frequency
		
		boolean freq_ok=false;
		if(config.getSelectedFrequency()==null)
		{
			//frequency not defined... don't know better
			freq_ok=true;
		}
		else if(hardwareFrequencies==null)
		{
			//don't know better
			freq_ok=true;
			
		}
		else
		{
			for(int i=0;i<hardwareFrequencies.length && !freq_ok;i++)
			{
				freq_ok = freq_ok || FrequencyUtil.isInScale(config.getSelectedFrequency(),hardwareFrequencies[i]);
			}
		}
		
		if(!freq_ok)
			throw new WrongConfigurationException(WrongConfigurationExceptionConstants.FREQUENCY_NOT_IN_SCALES);
		
		
		boolean params_ok=true;
		if(config.getSelectedHardwareParameters()==null)
		{
			//params not defined... don't know better
			params_ok=true;
		}
		else if(hardwareParameters==null)
		{
			//don't know better
			params_ok=true;
		}
		else
		{
			for(int i=0;i<hardwareParameters.length && params_ok;i++)
			{
				String param_value=config.getSelectedHardwareParameterValue(
							hardwareParameters[i].getParameterName()
						    );
				if( param_value!=null )
					params_ok = params_ok && hardwareParameters[i].isSelectedValueValid(param_value);
			}
		}
		
		if(!params_ok)
			throw new WrongConfigurationException(WrongConfigurationExceptionConstants.PARAMETER_INVALID);
		
		//check bigger then now, at least, or null
		//not now! Maybe at a later time I'll have time to do it!
		//config.getTimeStart();
		
		//check total_samples with samplingScale
		int total_samples=config.getTotalSamples();
		
		if(	! ( total_samples>=samplingScale.getMinSamples() &&
		total_samples<=samplingScale.getMaxSamples() &&
		((total_samples-samplingScale.getMinSamples())%samplingScale.getStep())==0
		)
		)
			throw new WrongConfigurationException(WrongConfigurationExceptionConstants.SAMPLING_SCALE_INVALID);
		
		//Check channels config
		config.getChannelsConfig();
		
		boolean channel_config_ok=true;
		if(config.getChannelsConfig()==null)
		{
			//channel configs not defined... don't know better
			channel_config_ok=true;
		}
		else if(channelsInfo==null)
		{
			//don't know better
			channel_config_ok=true;
		}
		else
		{
			for(int i=0;i<channelsInfo.length && i<config.getChannelsConfig().length && channel_config_ok;i++)
			{
				channelsInfo[i].validateConfig(config.getChannelsConfig(i));
			}
		}
		
	}
	
	public HardwareAcquisitionConfig createBaseHardwareAcquisitionConfig()
	{
		//check selected frequency
		HardwareAcquisitionConfig config=new HardwareAcquisitionConfig();
		
		if(hardwareFrequencies!=null && hardwareFrequencies.length>0 && hardwareFrequencies[0]!=null)
			config.setSelectedFrequency(hardwareFrequencies[0].getMinimumFrequency());
		
		if(hardwareParameters!=null)
			for(int i=0;i<hardwareParameters.length;i++)
				config.addSelectedHardwareParameter(new ParameterConfig(hardwareParameters[i].getParameterName(),hardwareParameters[i].getSelectedParameterValue()));
		
		config.setTotalSamples(samplingScale.getMinSamples());
		config.setFamiliarName(getFamiliarName());
		config.setHardwareUniqueID(getHardwareUniqueID());
		config.setTimeStart(new com.linkare.rec.data.synch.DateTime());
		
		if(channelsInfo!=null)
		{
			for(int i=0;i<channelsInfo.length;i++)
			{
			    if(channelsInfo[i]!=null)
				config.addChannelConfig(channelsInfo[i].createBaseChannelConfig(this));
			}
		}
		
		return config;
	}
	
	
	
} // class HardwareInfo
