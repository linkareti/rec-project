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
	public HardwareParameterConfigListHolder(final com.linkare.rec.data.config.ParameterConfig[] initial) {
		value = initial;
	}

	//
	// Read HardwareParameterConfigList from a marshalled stream
	// @param istream the input stream
	//
	@Override
	public void _read(final org.omg.CORBA.portable.InputStream istream) {
		value = HardwareParameterConfigListHelper.read(istream);
	}

	//
	// Write HardwareParameterConfigList into a marshalled stream
	// @param ostream the output stream
	//
	@Override
	public void _write(final org.omg.CORBA.portable.OutputStream ostream) {
		HardwareParameterConfigListHelper.write(ostream, value);
	}

	//
	// Return the HardwareParameterConfigList TypeCode
	// @return a TypeCode
	//
	@Override
	public org.omg.CORBA.TypeCode _type() {
		return HardwareParameterConfigListHelper.type();
	}

}
