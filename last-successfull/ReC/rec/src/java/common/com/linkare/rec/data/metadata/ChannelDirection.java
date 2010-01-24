package com.linkare.rec.data.metadata;

public class ChannelDirection implements org.omg.CORBA.portable.IDLEntity {
	//
	// Internal member value
	//
	private int value;

	//
	// Enum member CHANNEL_INPUT value
	//
	private static final int _CHANNEL_INPUT = 0;

	//
	// Enum member CHANNEL_INPUT
	//
	public static final ChannelDirection CHANNEL_INPUT = new ChannelDirection(_CHANNEL_INPUT);

	//
	// Enum member CHANNEL_OUTPUT value
	//
	private static final int _CHANNEL_OUTPUT = 1;

	//
	// Enum member CHANNEL_OUTPUT
	//
	public static final ChannelDirection CHANNEL_OUTPUT = new ChannelDirection(_CHANNEL_OUTPUT);

	//
	// Return the internal member value
	// @return the member value
	//
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		if (value == _CHANNEL_INPUT || value == _CHANNEL_OUTPUT)
			this.value = value;
		else
			throw new org.omg.CORBA.BAD_OPERATION();
	}

	//
	// Return a enum member from its value
	// @param an enum value
	// @return an enum member
	//
	public String toString() {
		switch (value) {
		case 0:
			return "Input Channel";
		case 1:
			return "Output Channel";
		default:
			return "";
		}
	}

	//
	// Private constructor
	// @param the enum value for this new member
	//
	public ChannelDirection(int value) {
		setValue(value);
	}

	//
	// Bean constructor
	//
	public ChannelDirection() {
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof ChannelDirection))
			return false;

		return ((ChannelDirection) other).getValue() == getValue();
	}
} // class ChannelDirection
