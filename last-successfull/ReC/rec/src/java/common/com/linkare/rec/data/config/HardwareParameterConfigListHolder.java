package com.linkare.rec.data.config;

final public class HardwareParameterConfigListHolder implements org.omg.CORBA.portable.Streamable {
	//
	// Internal HardwareParameterConfigList value
	//
	public com.linkare.rec.data.config.ParameterConfig[] value;

	//
	// Default constructor
	//
	public HardwareParameterConfigListHolder() {
	}

	//
	// Constructor with value initialisation
	// @param initial the initial value
	//
	public HardwareParameterConfigListHolder(com.linkare.rec.data.config.ParameterConfig[] initial) {
		value = initial;
	}

	//
	// Read HardwareParameterConfigList from a marshalled stream
	// @param istream the input stream
	//
	public void _read(org.omg.CORBA.portable.InputStream istream) {
		value = HardwareParameterConfigListHelper.read(istream);
	}

	//
	// Write HardwareParameterConfigList into a marshalled stream
	// @param ostream the output stream
	//
	public void _write(org.omg.CORBA.portable.OutputStream ostream) {
		HardwareParameterConfigListHelper.write(ostream, value);
	}

	//
	// Return the HardwareParameterConfigList TypeCode
	// @return a TypeCode
	//
	public org.omg.CORBA.TypeCode _type() {
		return HardwareParameterConfigListHelper.type();
	}

}
