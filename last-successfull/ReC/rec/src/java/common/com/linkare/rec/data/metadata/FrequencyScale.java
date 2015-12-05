package com.linkare.rec.data.metadata;

public final class FrequencyScale implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5317813822004515622L;

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
	public FrequencyScale(final com.linkare.rec.data.synch.Frequency f_min,
			final com.linkare.rec.data.synch.Frequency f_max, final com.linkare.rec.data.synch.Frequency step) {
		setMinimumFrequency(f_min);
		setMaximumFrequency(f_max);
		setStepFrequency(step);
	}

	//
	// Constructor with fields initialization
	// @param frequency_scale_label The Frequency Scale Label - might be null
	// @param f_min f_min struct member
	// @param f_max f_max struct member
	// @param step step struct member
	//
	public FrequencyScale(final String frequency_scale_label, final com.linkare.rec.data.synch.Frequency f_min,
			final com.linkare.rec.data.synch.Frequency f_max, final com.linkare.rec.data.synch.Frequency step) {
		setFrequencyScaleLabel(frequency_scale_label);
		setMinimumFrequency(f_min);
		setMaximumFrequency(f_max);
		setStepFrequency(step);
	}

	//
	// Copy Constructor
	//
	public FrequencyScale(final FrequencyScale other) {
		setFrequencyScaleLabel(new String(other.getFrequencyScaleLabel()));
		setMinimumFrequency(new com.linkare.rec.data.synch.Frequency(other.getMinimumFrequency()));
		setMaximumFrequency(new com.linkare.rec.data.synch.Frequency(other.getMaximumFrequency()));
		setStepFrequency(new com.linkare.rec.data.synch.Frequency(other.getStepFrequency()));
	}

	/**
	 * Getter for property minimumFrequency.
	 * 
	 * @return Value of property minimumFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getMinimumFrequency() {
		return minimumFrequency;
	}

	/**
	 * Setter for property minimumFrequency.
	 * 
	 * @param minimumFrequency New value of property minimumFrequency.
	 */
	public void setMinimumFrequency(final com.linkare.rec.data.synch.Frequency minimumFrequency) {
		this.minimumFrequency = minimumFrequency;
	}

	/**
	 * Getter for property maximumFrequency.
	 * 
	 * @return Value of property maximumFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getMaximumFrequency() {
		return maximumFrequency;
	}

	/**
	 * Setter for property maximumFrequency.
	 * 
	 * @param maximumFrequency New value of property maximumFrequency.
	 */
	public void setMaximumFrequency(final com.linkare.rec.data.synch.Frequency maximumFrequency) {
		this.maximumFrequency = maximumFrequency;
	}

	/**
	 * Getter for property stepFrequency.
	 * 
	 * @return Value of property stepFrequency.
	 */
	public com.linkare.rec.data.synch.Frequency getStepFrequency() {
		return stepFrequency;
	}

	/**
	 * Setter for property stepFrequency.
	 * 
	 * @param stepFrequency New value of property stepFrequency.
	 */
	public void setStepFrequency(final com.linkare.rec.data.synch.Frequency stepFrequency) {
		this.stepFrequency = stepFrequency;
	}

	/**
	 * Getter for property frequency_scale_label.
	 * 
	 * @return Value of property frequency_scale_label.
	 */
	public String getFrequencyScaleLabel() {
		return frequency_scale_label;
	}

	/**
	 * Setter for property frequency_scale_label.
	 * 
	 * @param frequency_scale_label New value of property frequency_scale_label.
	 */
	public void setFrequencyScaleLabel(final String frequency_scale_label) {
		this.frequency_scale_label = frequency_scale_label;
	}

	@Override
	public String toString() {
		if (frequency_scale_label != null) {
			return frequency_scale_label;
		}

		return "[" + maximumFrequency + ":" + maximumFrequency + ",\u0394=" + stepFrequency + "]";
	}

} // class FrequencyScale
