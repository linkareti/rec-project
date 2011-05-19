package com.linkare.rec.data.metadata;

public class ParameterType implements org.omg.CORBA.portable.IDLEntity {
	//
	// Internal member value
	//
	private int value;

	//
	// Enum member SelectionListValue value
	//
	protected static final int _SelectionListValue = 0;

	//
	// Enum member SelectionListValue
	//
	public static final ParameterType SelectionListValue = new ParameterType(_SelectionListValue);

	//
	// Enum member ContinuousValue value
	//
	protected static final int _ContinuousValue = 1;

	//
	// Enum member ContinuousValue
	//
	public static final ParameterType ContinuousValue = new ParameterType(_ContinuousValue);

	//
	// Enum member OnOffValue value
	//
	protected static final int _OnOffValue = 2;

	//
	// Enum member OnOffValue
	//
	public static final ParameterType OnOffValue = new ParameterType(_OnOffValue);

	//
	// Enum member BlackBoxValue value
	//
	protected static final int _BlackBoxValue = 3;

	//
	// Enum member BlackBoxValue
	//
	public static final ParameterType BlackBoxValue = new ParameterType(_BlackBoxValue);

	//
	// Return the internal member value
	// @return the member value
	//
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (value == _SelectionListValue || value == _OnOffValue || value == _ContinuousValue
				|| value == _BlackBoxValue)
			this.value = value;
		else
			throw new org.omg.CORBA.BAD_OPERATION();
	}

	//
	// Private constructor
	// @param the enum value for this new member
	//
	public ParameterType(int value) {
		setValue(value);
	}

	// JavaBeans Constructor
	public ParameterType() {
		setValue(_OnOffValue);
	}

	//
	// Copy constructor
	// @param the enum value for this new member
	//
	public ParameterType(ParameterType other) {
		setValue(other.value);
	}

	public String toString() {
		switch (this.getValue()) {
		case _ContinuousValue:
			return "Continuous Value";
		case _SelectionListValue:
			return "Selection List Value";
		case _OnOffValue:
			return "On/Off Value";
		case _BlackBoxValue:
			return "Uninterpreted Value";
		default:
			return "";
		}
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof ParameterType))
			return false;

		return ((ParameterType) other).getValue() == getValue();
	}
} // class ParameterType
