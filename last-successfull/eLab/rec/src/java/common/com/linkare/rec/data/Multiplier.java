package com.linkare.rec.data;

public final class Multiplier implements org.omg.CORBA.portable.IDLEntity {

	//
	// Internal member value
	//
	private byte value;

	//
	// Enum member fento value
	//
	private static final byte _fento = 0;

	//
	// Enum member fento
	//
	public static final Multiplier fento = new Multiplier(_fento);

	//
	// Enum member pico value
	//
	private static final byte _pico = 1;

	//
	// Enum member pico
	//
	public static final Multiplier pico = new Multiplier(_pico);

	//
	// Enum member nano value
	//
	private static final byte _nano = 2;

	//
	// Enum member nano
	//
	public static final Multiplier nano = new Multiplier(_nano);

	//
	// Enum member micro value
	//
	private static final byte _micro = 3;

	//
	// Enum member micro
	//
	public static final Multiplier micro = new Multiplier(_micro);

	//
	// Enum member mili value
	//
	private static final byte _mili = 4;

	//
	// Enum member mili
	//
	public static final Multiplier mili = new Multiplier(_mili);

	//
	// Enum member none value
	//
	private static final byte _none = 5;

	//
	// Enum member none
	//
	public static final Multiplier none = new Multiplier(_none);

	//
	// Enum member kilo value
	//
	private static final byte _kilo = 6;

	//
	// Enum member kilo
	//
	public static final Multiplier kilo = new Multiplier(_kilo);

	//
	// Enum member mega value
	//
	private static final byte _mega = 7;

	//
	// Enum member mega
	//
	public static final Multiplier mega = new Multiplier(_mega);

	//
	// Enum member giga value
	//
	private static final byte _giga = 8;

	//
	// Enum member giga
	//
	public static final Multiplier giga = new Multiplier(_giga);

	//
	// Enum member tera value
	//
	private static final byte _tera = 9;

	//
	// Enum member tera
	//
	public static final Multiplier tera = new Multiplier(_tera);

	/**
	 * Getter for property value.
	 * 
	 * @return Value of property value.
	 */
	public byte getValue() {
		return this.value;
	}

	/**
	 * Setter for property value.
	 * 
	 * @param value New value of property value.
	 */
	public void setValue(byte value) {
		if (value == _fento || value == _pico || value == _nano || value == _micro || value == _mili || value == _none
				|| value == _kilo || value == _mega || value == _giga || value == _tera)
			this.value = value;
		else
			throw new org.omg.CORBA.BAD_OPERATION();

	}

	//
	// Public constructor
	// @param the enum value for this new member
	//
	public Multiplier() {
		this.setValue(_none);
	}

	public Multiplier(byte value) {
		this.setValue(value);
	}

	public Multiplier(Multiplier other) {
		if (other != null)
			setValue(other.getValue());
		else
			setValue(Multiplier.none.getValue());
	}

	public String toString() {
		switch (getValue()) {
		case Multiplier._fento:
			return "f";
		case Multiplier._pico:
			return "p";
		case Multiplier._nano:
			return "n";
		case Multiplier._micro:
			return "\u03BC";
		case Multiplier._mili:
			return "m";
		case Multiplier._none:
			return "";
		case Multiplier._kilo:
			return "k";
		case Multiplier._mega:
			return "M";
		case Multiplier._giga:
			return "G";
		case Multiplier._tera:
			return "T";
		default:
			return "";
		}
	}

	public String toExpString() {
		switch (getValue()) {
		case Multiplier._fento:
			return "E-15";
		case Multiplier._pico:
			return "E-12";
		case Multiplier._nano:
			return "E-9";
		case Multiplier._micro:
			return "E-6";
		case Multiplier._mili:
			return "E-3";
		case Multiplier._none:
			return "E0";
		case Multiplier._kilo:
			return "E3";
		case Multiplier._mega:
			return "E6";
		case Multiplier._giga:
			return "E9";
		case Multiplier._tera:
			return "E12";
		default:
			return "";
		}
	}

	public double getExpValue() {
		switch (getValue()) {
		case Multiplier._fento:
			return 1.E-15;
		case Multiplier._pico:
			return 1.E-12;
		case Multiplier._nano:
			return 1.E-9;
		case Multiplier._micro:
			return 1.E-6;
		case Multiplier._mili:
			return 1.E-3;
		case Multiplier._none:
			return 1.;
		case Multiplier._kilo:
			return 1.E3;
		case Multiplier._mega:
			return 1.E6;
		case Multiplier._giga:
			return 1.E9;
		case Multiplier._tera:
			return 1.E12;
		default:
			return 1.;
		}
	}

	public boolean equals(Object other) {
		if (other == null || !(other instanceof Multiplier))
			return false;

		return ((Multiplier) other).getValue() == getValue();
	}

} // class Multiplier
