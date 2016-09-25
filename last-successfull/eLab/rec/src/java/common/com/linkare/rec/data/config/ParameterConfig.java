package com.linkare.rec.data.config;

import java.util.ResourceBundle;

public final class ParameterConfig implements org.omg.CORBA.portable.IDLEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6831632775475652156L;

	private static final ResourceBundle resourceBundle = ResourceBundle
			.getBundle("com/linkare/rec/data/resources/messages");

	private static final String UNDEFINED_PARAMETER_NAME = ParameterConfig.resourceBundle
			.getString("rec.ui.parameter.config.undefined.parameter.name");

	private static final String UNDEFINED = ParameterConfig.resourceBundle
			.getString("rec.ui.parameter.config.undefined");

	/** Holds value of property parameterName. */
	private String parameterName;

	/** Holds value of property parameterValue. */
	private String parameterValue;

	//
	// Default constructor
	//
	public ParameterConfig() {
	}

	//
	// Constructor with fields initialization
	// @param parameter_name parameter_name struct member
	// @param parameter_value parameter_value struct member
	//
	public ParameterConfig(final String parameter_name, final String parameter_value) {
		setParameterName(parameter_name);
		setParameterValue(parameter_value);
	}

	//
	// Copy Constructor
	//
	public ParameterConfig(final ParameterConfig other) {
		setParameterName(other.getParameterName());
		setParameterValue(other.getParameterValue());
	}

	/**
	 * Getter for property parameterName.
	 * 
	 * @return Value of property parameterName.
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * Setter for property parameterName.
	 * 
	 * @param parameterName New value of property parameterName.
	 */
	public void setParameterName(final String parameterName) {
		this.parameterName = parameterName;
	}

	/**
	 * Getter for property parameterValue.
	 * 
	 * @return Value of property parameterValue.
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * Setter for property parameterValue.
	 * 
	 * @param parameterValue New value of property parameterValue.
	 */
	public void setParameterValue(final String parameterValue) {
		this.parameterValue = parameterValue;
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
		return "" + (parameterName == null ? ParameterConfig.UNDEFINED_PARAMETER_NAME : parameterName) + " : "
				+ (parameterValue == null ? ParameterConfig.UNDEFINED : parameterValue);
	}

} // class ParameterConfig