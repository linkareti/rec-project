package com.linkare.rec.data.metadata;

public final class SamplesNumScale implements org.omg.CORBA.portable.IDLEntity {
	/** Holds value of property minSamples. */
	private int minSamples;

	/** Holds value of property maxSamples. */
	private int maxSamples;

	/** Holds value of property minSamples. */
	private int step;

	public SamplesNumScale() {
	} // ctor

	public SamplesNumScale(int min_samples, int max_samples, int step) {
		setMinSamples(min_samples);
		setMaxSamples(max_samples);
		setStep(step);
	}

	/**
	 * Getter for property minSamples.
	 * 
	 * @return Value of property minSamples.
	 */
	public int getMinSamples() {
		return this.minSamples;
	}

	/**
	 * Setter for property minSamples.
	 * 
	 * @param minSamples New value of property minSamples.
	 */
	public void setMinSamples(int minSamples) {
		this.minSamples = minSamples;
	}

	/**
	 * Getter for property maxSamples.
	 * 
	 * @return Value of property maxSamples.
	 */
	public int getMaxSamples() {
		return this.maxSamples;
	}

	/**
	 * Setter for property maxSamples.
	 * 
	 * @param maxSamples New value of property maxSamples.
	 */
	public void setMaxSamples(int maxSamples) {
		this.maxSamples = maxSamples;
	}

	/**
	 * Getter for property step.
	 * 
	 * @return Value of property step.
	 */
	public int getStep() {
		return this.step;
	}

	/**
	 * Setter for property step.
	 * 
	 * @param step New value of property step.
	 */
	public void setStep(int step) {
		this.step = step;
	}

	// ctor

} // class SamplesNumScale
