package com.linkare.rec.repository;

import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;

public final class HardwareAcquisitionConfigSearch implements IDLEntity
{
    private DateTimeSearch startTimeSearch = null;
    private FrequencySearch frequencySearch = null;
    private ChannelAcquisitionConfigSearch[] channelsConfigSearch = null;
    private ParameterConfig[] selectedHardwareParameters = null;
    private SamplesNumSearch samplesNumSearch = null;
    
    public HardwareAcquisitionConfigSearch()
    {
    } // ctor
    
    public HardwareAcquisitionConfigSearch(DateTimeSearch startTimeSearch, FrequencySearch frequencySearch, ChannelAcquisitionConfigSearch[] channelsConfigSearch, ParameterConfig[] selectedHardwareParameters, SamplesNumSearch samplesNumSearch)
    {
	setStartTimeSearch(startTimeSearch);
	setFrequencySearch(frequencySearch);
	setChannelsConfigSearch(channelsConfigSearch);
	setSelectedHardwareParameters(selectedHardwareParameters);
	setSamplesNumSearch(samplesNumSearch);
    }
    
    /** Getter for property startTimeSearch.
     * @return Value of property startTimeSearch.
     *
     */
    public DateTimeSearch getStartTimeSearch()
    {
	return startTimeSearch;
    }
    
    /** Setter for property startTimeSearch.
     * @param startTimeSearch New value of property startTimeSearch.
     *
     */
    public void setStartTimeSearch(DateTimeSearch startTimeSearch)
    {
	this.startTimeSearch = startTimeSearch;
    }
    
    /** Getter for property frequencySearch.
     * @return Value of property frequencySearch.
     *
     */
    public FrequencySearch getFrequencySearch()
    {
	return frequencySearch;
    }
    
    /** Setter for property frequencySearch.
     * @param frequencySearch New value of property frequencySearch.
     *
     */
    public void setFrequencySearch(FrequencySearch frequencySearch)
    {
	this.frequencySearch = frequencySearch;
    }
    
    /** Getter for property channelsConfigSearch.
     * @return Value of property channelsConfigSearch.
     *
     */
    public ChannelAcquisitionConfigSearch[] getChannelsConfigSearch()
    {
	return this.channelsConfigSearch;
    }
    
    /** Setter for property channelsConfigSearch.
     * @param channelsConfigSearch New value of property channelsConfigSearch.
     *
     */
    public void setChannelsConfigSearch(ChannelAcquisitionConfigSearch[] channelsConfigSearch)
    {
	this.channelsConfigSearch = channelsConfigSearch;
    }
    
    /** Getter for property selectedHardwareParameters.
     * @return Value of property selectedHardwareParameters.
     *
     */
    public ParameterConfig[] getSelectedHardwareParameters()
    {
	return this.selectedHardwareParameters;
    }
    
    /** Setter for property selectedHardwareParameters.
     * @param selectedHardwareParameters New value of property selectedHardwareParameters.
     *
     */
    public void setSelectedHardwareParameters(ParameterConfig[] selectedHardwareParameters)
    {
	this.selectedHardwareParameters = selectedHardwareParameters;
    }
    
    /** Getter for property samplesNumSearch.
     * @return Value of property samplesNumSearch.
     *
     */
    public SamplesNumSearch getSamplesNumSearch()
    {
	return samplesNumSearch;
    }
    
    /** Setter for property samplesNumSearch.
     * @param samplesNumSearch New value of property samplesNumSearch.
     *
     */
    public void setSamplesNumSearch(SamplesNumSearch samplesNumSearch)
    {
	this.samplesNumSearch = samplesNumSearch;
    }
    
    
    public boolean isValid(HardwareAcquisitionConfig hwConfig)
    {
	if(hwConfig==null) return false;
	
	
	if(getStartTimeSearch()!=null && !getStartTimeSearch().isValid(hwConfig.getTimeStart()) )
	    return false;
	
	if(getFrequencySearch()!=null && !getFrequencySearch().isValid(hwConfig.getSelectedFrequency()))
	    return false;
	
	if(getSamplesNumSearch()!=null && !getSamplesNumSearch().isValid(hwConfig.getTotalSamples()))
	    return false;
	
	ParameterConfig[] params=getSelectedHardwareParameters();
	
	if(params!=null)
	{
	    for(int i=0;i<params.length;i++)
	    {
		String paramName=params[i].getParameterName();
		String paramValue=params[i].getParameterValue();
		if(paramName!=null && paramValue!=null)
		{
		    ParameterConfig configParam=hwConfig.getSelectedHardwareParameter(paramName);
		    if ( ! (configParam!=null && configParam.getParameterValue()!=null && configParam.getParameterValue().equals(paramValue)) )
			return false;
		}
	    }
	    
	}
	
	if(getChannelsConfigSearch()!=null)
	{
	    for(int i=0;i<getChannelsConfigSearch().length;i++)
	    {
		String channelName=getChannelsConfigSearch()[i].getChannelName();
		if(channelName!=null)
		{
		    if(!getChannelsConfigSearch()[i].isValid(hwConfig.getChannelsConfig(channelName)))
			return false;
		}
	    }
	}
	
	
	return true;
    }
    
} // class HardwareAcquisitionConfigSearch
