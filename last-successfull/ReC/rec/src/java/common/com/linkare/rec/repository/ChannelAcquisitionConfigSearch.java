package com.linkare.rec.repository;

import org.omg.CORBA.portable.IDLEntity;

import com.linkare.rec.data.config.ChannelAcquisitionConfig;
import com.linkare.rec.data.config.ParameterConfig;
import com.linkare.rec.data.metadata.Scale;

public final class ChannelAcquisitionConfigSearch implements IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8923035127225605680L;
	private String channelName = null;
	private DateTimeSearch startTimeSearch = null;
	private FrequencySearch frequencySearch = null;
	private Scale selectedScale = null;
	private ParameterConfig[] selectedChannelParameters = null;
	private SamplesNumSearch samplesNumSearch = null;

	public ChannelAcquisitionConfigSearch() {
	} // ctor

	public ChannelAcquisitionConfigSearch(final String channelName, final DateTimeSearch startTimeSearch,
			final FrequencySearch frequencySearch, final Scale selectedScale,
			final ParameterConfig[] selectedChannelParameters, final SamplesNumSearch samplesNumSearch) {
		setChannelName(channelName);
		setStartTimeSearch(startTimeSearch);
		setFrequencySearch(frequencySearch);
		setSelectedScale(selectedScale);
		setSelectedChannelParameters(selectedChannelParameters);
		setSamplesNumSearch(samplesNumSearch);
	}

	/**
	 * Getter for property channelName.
	 * 
	 * @return Value of property channelName.
	 * 
	 */
	public java.lang.String getChannelName() {
		return channelName;
	}

	/**
	 * Setter for property channelName.
	 * 
	 * @param channelName New value of property channelName.
	 * 
	 */
	public void setChannelName(final java.lang.String channelName) {
		this.channelName = channelName;
	}

	/**
	 * Getter for property startTimeSearch.
	 * 
	 * @return Value of property startTimeSearch.
	 * 
	 */
	public DateTimeSearch getStartTimeSearch() {
		return startTimeSearch;
	}

	/**
	 * Setter for property startTimeSearch.
	 * 
	 * @param startTimeSearch New value of property startTimeSearch.
	 * 
	 */
	public void setStartTimeSearch(final DateTimeSearch startTimeSearch) {
		this.startTimeSearch = startTimeSearch;
	}

	/**
	 * Getter for property frequencySearch.
	 * 
	 * @return Value of property frequencySearch.
	 * 
	 */
	public FrequencySearch getFrequencySearch() {
		return frequencySearch;
	}

	/**
	 * Setter for property frequencySearch.
	 * 
	 * @param frequencySearch New value of property frequencySearch.
	 * 
	 */
	public void setFrequencySearch(final FrequencySearch frequencySearch) {
		this.frequencySearch = frequencySearch;
	}

	/**
	 * Getter for property selectedScale.
	 * 
	 * @return Value of property selectedScale.
	 * 
	 */
	public Scale getSelectedScale() {
		return selectedScale;
	}

	/**
	 * Setter for property selectedScale.
	 * 
	 * @param selectedScale New value of property selectedScale.
	 * 
	 */
	public void setSelectedScale(final Scale selectedScale) {
		this.selectedScale = selectedScale;
	}

	/**
	 * Getter for property selectedChannelParameters.
	 * 
	 * @return Value of property selectedChannelParameters.
	 * 
	 */
	public ParameterConfig[] getSelectedChannelParameters() {
		return selectedChannelParameters;
	}

	/**
	 * Setter for property selectedChannelParameters.
	 * 
	 * @param selectedChannelParameters New value of property
	 *            selectedChannelParameters.
	 * 
	 */
	public void setSelectedChannelParameters(final ParameterConfig[] selectedChannelParameters) {
		this.selectedChannelParameters = selectedChannelParameters;
	}

	/**
	 * Getter for property samplesNumSearch.
	 * 
	 * @return Value of property samplesNumSearch.
	 * 
	 */
	public SamplesNumSearch getSamplesNumSearch() {
		return samplesNumSearch;
	}

	/**
	 * Setter for property samplesNumSearch.
	 * 
	 * @param samplesNumSearch New value of property samplesNumSearch.
	 * 
	 */
	public void setSamplesNumSearch(final SamplesNumSearch samplesNumSearch) {
		this.samplesNumSearch = samplesNumSearch;
	}

	public boolean isValid(final ChannelAcquisitionConfig channelConfig) {
		if (channelConfig == null) {
			return false;
		}

		if (getStartTimeSearch() != null && !getStartTimeSearch().isValid(channelConfig.getTimeStart())) {
			return false;
		}

		if (getFrequencySearch() != null && !getFrequencySearch().isValid(channelConfig.getSelectedFrequency())) {
			return false;
		}

		if (getSelectedScale() != null && !getSelectedScale().equals(channelConfig.getSelectedScale())) {
			return false;
		}

		if (getSamplesNumSearch() != null && !getSamplesNumSearch().isValid(channelConfig.getTotalSamples())) {
			return false;
		}

		final ParameterConfig[] params = getSelectedChannelParameters();

		if (params != null) {
			for (final ParameterConfig param : params) {
				final String paramName = param.getParameterName();
				final String paramValue = param.getParameterValue();
				if (paramName != null && paramValue != null) {
					final ParameterConfig configParam = channelConfig.getSelectedChannelParameter(paramName);
					if (!(configParam != null && configParam.getParameterValue() != null && configParam
							.getParameterValue().equals(paramValue))) {
						return false;
					}
				}
			}

		}

		return true;
	}

} // class ChannelAcquisitionConfigSearch
