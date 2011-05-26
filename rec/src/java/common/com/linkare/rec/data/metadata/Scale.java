package com.linkare.rec.data.metadata;

public final class Scale implements org.omg.CORBA.portable.IDLEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1688440666209818701L;
	//
	// Struct member applied_multiplier
	//
	private com.linkare.rec.data.Multiplier applied_multiplier;
	//
	// Struct member min_value
	//
	private com.linkare.rec.data.acquisition.PhysicsVal min_value;

	//
	// Struct member max_value
	//
	private com.linkare.rec.data.acquisition.PhysicsVal max_value;

	//
	// Struct member default_error
	//
	private com.linkare.rec.data.acquisition.PhysicsVal default_error;

	//
	// Struct member step
	//
	private com.linkare.rec.data.acquisition.PhysicsVal step;

	//
	// Struct member PhysicsUnitName
	//
	private String PhysicsUnitName;

	//
	// Struct member PhysicsUnitSymbol
	//
	private String PhysicsUnitSymbol;

	//
	// Struct member ScaleLabel
	//
	private String scaleLabel;

	//
	// Default constructor
	//
	public Scale() {
	}

	//
	// Constructor with fields initialization
	// @param min_value min_value struct member
	// @param max_value max_value struct member
	// @param default_error default_error struct member
	// @param step step struct member
	// @param PhysicsUnitName PhysicsUnitName struct member
	// @param PhysicsUnitSymbol PhysicsUnitSymbol struct member
	// @param scaleLabel ScaleLabel struct member
	//
	public Scale(final com.linkare.rec.data.acquisition.PhysicsVal min_value,
			final com.linkare.rec.data.acquisition.PhysicsVal max_value,
			final com.linkare.rec.data.acquisition.PhysicsVal default_error,
			final com.linkare.rec.data.acquisition.PhysicsVal step,
			final com.linkare.rec.data.Multiplier applied_multiplier, final String PhysicsUnitName,
			final String PhysicsUnitSymbol, final String scaleLabel) {
		setMinimumValue(min_value);
		setMaximumValue(max_value);
		setDefaultErrorValue(default_error);
		setStepValue(step);
		setMultiplier(applied_multiplier);
		setPhysicsUnitName(PhysicsUnitName);
		setPhysicsUnitSymbol(PhysicsUnitSymbol);
		setScaleLabel(scaleLabel);
	}

	//
	// Copy Constructor
	//
	public Scale(final Scale other) {
		setMinimumValue(new com.linkare.rec.data.acquisition.PhysicsVal(other.getMinimumValue()));
		setMaximumValue(new com.linkare.rec.data.acquisition.PhysicsVal(other.getMaximumValue()));
		setDefaultErrorValue(new com.linkare.rec.data.acquisition.PhysicsVal(other.getDefaultErrorValue()));
		setStepValue(new com.linkare.rec.data.acquisition.PhysicsVal(other.getStepValue()));
		setMultiplier(new com.linkare.rec.data.Multiplier(other.getMultiplier()));
		setPhysicsUnitName(new String(other.getPhysicsUnitName()));
		setPhysicsUnitSymbol(new String(other.getPhysicsUnitSymbol()));
		setScaleLabel(new String(other.getScaleLabel()));
	}

	/**
	 * Getter for property minimumValue.
	 * 
	 * @return Value of property minimumValue.
	 */
	public com.linkare.rec.data.acquisition.PhysicsVal getMinimumValue() {
		return min_value;
	}

	/**
	 * Setter for property minimumValue.
	 * 
	 * @param minimumValue New value of property minimumValue.
	 */
	public void setMinimumValue(final com.linkare.rec.data.acquisition.PhysicsVal minimumValue) {
		min_value = minimumValue;
	}

	/**
	 * Getter for property maximumValue.
	 * 
	 * @return Value of property maximumValue.
	 */
	public com.linkare.rec.data.acquisition.PhysicsVal getMaximumValue() {
		return max_value;
	}

	/**
	 * Setter for property maximumValue.
	 * 
	 * @param maximumValue New value of property maximumValue.
	 */
	public void setMaximumValue(final com.linkare.rec.data.acquisition.PhysicsVal maximumValue) {
		max_value = maximumValue;
	}

	/**
	 * Getter for property default_error.
	 * 
	 * @return Value of property default_error.
	 */
	public com.linkare.rec.data.acquisition.PhysicsVal getDefaultErrorValue() {
		return default_error;
	}

	/**
	 * Setter for property default_error.
	 * 
	 * @param default_error New value of property default_error.
	 */
	public void setDefaultErrorValue(final com.linkare.rec.data.acquisition.PhysicsVal default_error) {
		this.default_error = default_error;
	}

	/**
	 * Getter for property step.
	 * 
	 * @return Value of property step.
	 */
	public com.linkare.rec.data.acquisition.PhysicsVal getStepValue() {
		return step;
	}

	/**
	 * Setter for property step.
	 * 
	 * @param step New value of property step.
	 */
	public void setStepValue(final com.linkare.rec.data.acquisition.PhysicsVal step) {
		this.step = step;
	}

	/**
	 * Getter for property PhysicsUnitName.
	 * 
	 * @return Value of property PhysicsUnitName.
	 */
	public String getPhysicsUnitName() {
		return PhysicsUnitName;
	}

	/**
	 * Setter for property PhysicsUnitName.
	 * 
	 * @param PhysicsUnitName New value of property PhysicsUnitName.
	 */
	public void setPhysicsUnitName(final String PhysicsUnitName) {
		this.PhysicsUnitName = PhysicsUnitName;
	}

	/**
	 * Getter for property physicsUnitSymbol.
	 * 
	 * @return Value of property physicsUnitSymbol.
	 */
	public String getPhysicsUnitSymbol() {
		return PhysicsUnitSymbol;
	}

	/**
	 * Setter for property physicsUnitSymbol.
	 * 
	 * @param physicsUnitSymbol New value of property physicsUnitSymbol.
	 */
	public void setPhysicsUnitSymbol(final String PhysicsUnitSymbol) {
		this.PhysicsUnitSymbol = PhysicsUnitSymbol;
	}

	/**
	 * Getter for property scaleLabel.
	 * 
	 * @return Value of property scaleLabel.
	 */
	public String getScaleLabel() {
		return scaleLabel;
	}

	/**
	 * Setter for property scaleLabel.
	 * 
	 * @param scaleLabel New value of property scaleLabel.
	 */
	public void setScaleLabel(final String scaleLabel) {
		this.scaleLabel = scaleLabel;
	}

	/**
	 * Getter for property applied_multiplier.
	 * 
	 * @return Value of property applied_multiplier.
	 */
	public com.linkare.rec.data.Multiplier getMultiplier() {
		return applied_multiplier;
	}

	/**
	 * Setter for property physicsUnitSymbol.
	 * 
	 * @param physicsUnitSymbol New value of property physicsUnitSymbol.
	 */
	public void setMultiplier(final com.linkare.rec.data.Multiplier applied_multiplier) {
		this.applied_multiplier = applied_multiplier;
	}

	/**
	 * Returns a string representation of the object. In general, the
	 * <code>toString</code> method returns a string that "textually represents"
	 * this object. The result should be a concise but informative
	 * representation that is easy for a person to read. It is recommended that
	 * all subclasses override this method.
	 * <p>
	 * The <code>toString</code> method for class <code>Object</code> returns a
	 * string consisting of the name of the class of which the object is an
	 * instance, the at-sign character `<code>@</code>', and the unsigned
	 * hexadecimal representation of the hash code of the object. In other
	 * words, this method returns a string equal to the value of: <blockquote>
	 * 
	 * <pre>
	 * getClass().getName() + '@' + Integer.toHexString(hashCode())
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @return a string representation of the object.
	 */
	@Override
	public String toString() {

		return (scaleLabel == null ? "" : scaleLabel + " = ") + "[" + min_value.toEngineeringNotation() + " ; "
				+ max_value.toEngineeringNotation() + " ; \u0394=" + step.toEngineeringNotation() + " ; \u03B4="
				+ default_error.toEngineeringNotation() + "] " + applied_multiplier + PhysicsUnitSymbol + " - "
				+ PhysicsUnitName;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null || !(obj instanceof Scale)) {
			return false;
		}

		final Scale other = (Scale) obj;

		return other.getMinimumValue().equals(getMinimumValue()) && other.getMaximumValue().equals(getMaximumValue())
				&& other.getStepValue().equals(getStepValue())
				&& other.getDefaultErrorValue().equals(getDefaultErrorValue())
				&& other.getMultiplier().equals(getMultiplier())
				&& other.getPhysicsUnitName().equals(getPhysicsUnitName())
				&& other.getPhysicsUnitSymbol().equals(getPhysicsUnitSymbol())
				&& other.getScaleLabel().equals(getScaleLabel());

	}

} // class Scale
