package com.linkare.rec.data.synch;

public final class Frequency implements org.omg.CORBA.portable.IDLEntity {
	//
	// Struct member value
	//
	private double frequency;

	//
	// Struct member applied_multiplier
	//
	private com.linkare.rec.data.Multiplier applied_multiplier;

	//
	// Struct member frequency_def_type
	//
	private com.linkare.rec.data.synch.FrequencyDefType frequency_def_type = null;

	//
	// Default constructor
	//
	public Frequency() {
		this(1., com.linkare.rec.data.Multiplier.none, FrequencyDefType.FrequencyType);
	}

	public Frequency(double frequency) {
		this(frequency, com.linkare.rec.data.Multiplier.none, FrequencyDefType.FrequencyType);
	}

	//
	// Constructor with fields initialization
	// @param value value struct member
	// @param applied_multiplier applied_multiplier struct member
	//
	public Frequency(double frequency, com.linkare.rec.data.Multiplier applied_multiplier) {
		this(frequency, applied_multiplier, FrequencyDefType.FrequencyType);

	}

	//
	// Constructor with fields initialization
	// @param value value struct member
	// @param applied_multiplier applied_multiplier struct member
	// @param frequency_def_type frequency_def_type struct member
	//
	public Frequency(double frequency, com.linkare.rec.data.Multiplier applied_multiplier,
			FrequencyDefType frequency_def_type) {
		setFrequency(frequency);
		setMultiplier(applied_multiplier);
		setFrequencyDefType(frequency_def_type);
	}

	//
	// Copy Constructor
	//
	public Frequency(Frequency other) {
		this.setFrequency(other.getFrequency());
		this.setMultiplier(new com.linkare.rec.data.Multiplier(other.getMultiplier()));
		this.setFrequencyDefType(other.getFrequencyDefType());
	}

	/**
	 * Getter for property multiplier.
	 * 
	 * @return Value of property multiplier.
	 */
	public com.linkare.rec.data.Multiplier getMultiplier() {
		return this.applied_multiplier;
	}

	/**
	 * Setter for property multiplier.
	 * 
	 * @param multiplier New value of property multiplier.
	 */
	public void setMultiplier(com.linkare.rec.data.Multiplier multiplier) {
		this.applied_multiplier = multiplier;
	}

	/**
	 * Getter for property frequency.
	 * 
	 * @return Value of property frequency.
	 */
	public double getFrequency() {
		return this.frequency;
	}

	/**
	 * Setter for property frequency.
	 * 
	 * @param frequency New value of property frequency.
	 */
	public void setFrequency(double frequency) {
		if (frequency <= 0)
			throw new RuntimeException("Frequency values must be positive: value tried=" + frequency);
		this.frequency = frequency;
	}

	public void setFrequencyDefType(FrequencyDefType frequency_def_type) {
		this.frequency_def_type = frequency_def_type;
	}

	public FrequencyDefType getFrequencyDefType() {
		return this.frequency_def_type;
	}

	public String toString() {
		java.text.NumberFormat ff = java.text.NumberFormat.getNumberInstance();
		ff.setMinimumFractionDigits(2);
		ff.setMaximumFractionDigits(2);

		return ff.format(frequency) + " " + applied_multiplier + frequency_def_type;
	}

} // class Frequency
