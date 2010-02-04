package com.linkare.rec.data.metadata;

public final class FrequencyScale implements org.omg.CORBA.portable.IDLEntity {
	/** Holds value of property minimumFrequency. */
	private com.linkare.rec.data.synch.Frequency minimumFrequency;

	/** Holds value of property maximumFrequency. */
	private com.linkare.rec.data.synch.Frequency maximumFrequency;

	/** Holds value of property stepFrequency. */
	private com.linkare.rec.data.synch.Frequency stepFrequency;

	// it helps to designate frequencies by name... not necessary!
	private String frequency_scale_label = null;

	//
	// Default constructor
	//
	public FrequencyScale() {
	}

	//
	// Constructor with fields initialization
	// @param f_min f_min struct member
	// @param f_max f_max struct member
	// @param step step struct member
	//
	public FrequencyScale(com.linkare.rec.data.synch.Frequency f_min, com.linkare.rec.data.synch.Frequency f_max,
			com.linkare.rec.data.synch.Frequency step) {
		this.setMinimumFrequency(f_min);
		this.setMaximumFrequency(f_max);
		this.setStepFrequency(step);
	}

	//
	// Constructor with fields initialization
	// @param frequency_scale_label The Frequency Scale Label - might be null
	// @param f_min f_min struct member
	// @param f_max f_max struct member
	// @param step step struct member
	//
	public FrequencyScale(String frequency_scale_label, com.linkare.rec.data.synch.Frequency f_min,
			com.linkare.rec.data.synch.Frequency f_max, com.linkare.rec.data.synch.Frequency step) {
		this.setFrequencyScaleLabel(frequency_scale_label);
		this.setMinimumFrequency(f_min);
		this.setMaximumFrequency(f_max);
		this.setStepFrequency(step);
	}

	//
	// Copy Constructor
	//
	public FrequencyScale(FrequencyScale other) {
		this.setFrequencyScaleLabel(new String(other.getFrequencyScaleLabel()));
		this.setMinimumFrequency(new com.linkare.rec.data.synch.Frequency(other.getMinimumFrequency()));
		this.setMaximumFrequency(new com.linkare.rec.data.synch.Frequency(other.getMaximumFrequency()));
		this.setStepFrequency(new com.linkare.rec.data.synch.Frequency(other.getStepFrequency()));
	}

	/**
	 * Getter for property minimumFrequency.
	 * 
	 * @return Value of property minimumFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getMinimumFrequency() {
		return this.minimumFrequency;
	}

	/**
	 * Setter for property minimumFrequency.
	 * 
	 * @param minimumFrequency New value of property minimumFrequency.
	 */
	public void setMinimumFrequency(com.linkare.rec.data.synch.Frequency minimumFrequency) {
		this.minimumFrequency = minimumFrequency;
	}

	/**
	 * Getter for property maximumFrequency.
	 * 
	 * @return Value of property maximumFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getMaximumFrequency() {
		return this.maximumFrequency;
	}

	/**
	 * Setter for property maximumFrequency.
	 * 
	 * @param maximumFrequency New value of property maximumFrequency.
	 */
	public void setMaximumFrequency(com.linkare.rec.data.synch.Frequency maximumFrequency) {
		this.maximumFrequency = maximumFrequency;
	}

	/**
	 * Getter for property stepFrequency.
	 * 
	 * @return Value of property stepFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getStepFrequency() {
		return this.stepFrequency;
	}

	/**
	 * Setter for property stepFrequency.
	 * 
	 * @param stepFrequency New value of property stepFrequency.
	 */
	public void setStepFrequency(com.linkare.rec.data.synch.Frequency stepFrequency) {
		this.stepFrequency = stepFrequency;
	}

	/**
	 * Getter for property frequency_scale_label.
	 * 
	 * @return Value of property frequency_scale_label.
	 */
	public String getFrequencyScaleLabel() {
		return this.frequency_scale_label;
	}

	/**
	 * Setter for property frequency_scale_label.
	 * 
	 * @param frequency_scale_label New value of property frequency_scale_label.
	 */
	public void setFrequencyScaleLabel(String frequency_scale_label) {
		this.frequency_scale_label = frequency_scale_label;
	}

	public String toString() {
		if (frequency_scale_label != null)
			return frequency_scale_label;

		return "[" + maximumFrequency + ":" + maximumFrequency + ",\u0394=" + stepFrequency + "]";
	}

} // class FrequencyScale
