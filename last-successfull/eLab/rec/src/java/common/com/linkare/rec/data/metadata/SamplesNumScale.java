package com.linkare.rec.data.metadata;

public final class SamplesNumScale implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3031903642831545687L;

	/** Holds value of property minSamples. */
	private int minSamples;

	/** Holds value of property maxSamples. */
	private int maxSamples;

	/** Holds value of property minSamples. */
	private int step;

	public SamplesNumScale() {
	} // ctor

	public SamplesNumScale(final int min_samples, final int max_samples, final int step) {
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
		return minSamples;
	}

	/**
	 * Setter for property minSamples.
	 * 
	 * @param minSamples New value of property minSamples.
	 */
	public void setMinSamples(final int minSamples) {
		this.minSamples = minSamples;
	}

	/**
	 * Getter for property maxSamples.
	 * 
	 * @return Value of property maxSamples.
	 */
	public int getMaxSamples() {
		return maxSamples;
	}

	/**
	 * Setter for property maxSamples.
	 * 
	 * @param maxSamples New value of property maxSamples.
	 */
	public void setMaxSamples(final int maxSamples) {
		this.maxSamples = maxSamples;
	}

	/**
	 * Getter for property step.
	 * 
	 * @return Value of property step.
	 */
	public int getStep() {
		return step;
	}

	/**
	 * Setter for property step.
	 * 
	 * @param step New value of property step.
	 */
	public void setStep(final int step) {
		this.step = step;
	}

	// ctor

} // class SamplesNumScale
