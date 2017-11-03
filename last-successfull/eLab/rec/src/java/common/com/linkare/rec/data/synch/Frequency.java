package com.linkare.rec.data.synch;

public final class Frequency implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3126854141310261406L;

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

	public Frequency(final double frequency) {
		this(frequency, com.linkare.rec.data.Multiplier.none, FrequencyDefType.FrequencyType);
	}

	//
	// Constructor with fields initialization
	// @param value value struct member
	// @param applied_multiplier applied_multiplier struct member
	//
	public Frequency(final double frequency, final com.linkare.rec.data.Multiplier applied_multiplier) {
		this(frequency, applied_multiplier, FrequencyDefType.FrequencyType);

	}

	//
	// Constructor with fields initialization
	// @param value value struct member
	// @param applied_multiplier applied_multiplier struct member
	// @param frequency_def_type frequency_def_type struct member
	//
	public Frequency(final double frequency, final com.linkare.rec.data.Multiplier applied_multiplier,
			final FrequencyDefType frequency_def_type) {
		setFrequency(frequency);
		setMultiplier(applied_multiplier);
		setFrequencyDefType(frequency_def_type);
	}

	//
	// Copy Constructor
	//
	public Frequency(final Frequency other) {
		setFrequency(other.getFrequency());
		setMultiplier(new com.linkare.rec.data.Multiplier(other.getMultiplier()));
		setFrequencyDefType(other.getFrequencyDefType());
	}

	/**
	 * Getter for property multiplier.
	 * 
	 * @return Value of property multiplier.
	 */
	public com.linkare.rec.data.Multiplier getMultiplier() {
		return applied_multiplier;
	}

	/**
	 * Setter for property multiplier.
	 * 
	 * @param multiplier New value of property multiplier.
	 */
	public void setMultiplier(final com.linkare.rec.data.Multiplier multiplier) {
		applied_multiplier = multiplier;
	}

	/**
	 * Getter for property frequency.
	 * 
	 * @return Value of property frequency.
	 */
	public double getFrequency() {
		return frequency;
	}

	/**
	 * Setter for property frequency.
	 * 
	 * @param frequency New value of property frequency.
	 */
	public void setFrequency(final double frequency) {
		if (frequency <= 0) {
			throw new RuntimeException("Frequency values must be positive: value tried=" + frequency);
		}
		this.frequency = frequency;
	}

	public void setFrequencyDefType(final FrequencyDefType frequency_def_type) {
		this.frequency_def_type = frequency_def_type;
	}

	public FrequencyDefType getFrequencyDefType() {
		return frequency_def_type;
	}

	@Override
	public String toString() {
		final java.text.NumberFormat ff = java.text.NumberFormat.getNumberInstance();
		ff.setMinimumFractionDigits(2);
		ff.setMaximumFractionDigits(2);

		return ff.format(frequency) + " " + applied_multiplier + frequency_def_type;
	}

} // class Frequency
