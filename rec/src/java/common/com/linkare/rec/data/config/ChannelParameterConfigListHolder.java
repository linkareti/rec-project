package com.linkare.rec.data.config;

final public class ChannelParameterConfigListHolder implements org.omg.CORBA.portable.Streamable {
	//
	// Internal ChannelParameterConfigList value
	//
	public com.linkare.rec.data.config.ParameterConfig[] value;

	//
	// Default constructor
	//
	public ChannelParameterConfigListHolder() {
	}

	//
	// Constructor with value initialisation
	// @param initial the initial value
	//
	public ChannelParameterConfigListHolder(com.linkare.rec.data.config.ParameterConfig[] initial) {
		value = initial;
	}

	//
	// Read ChannelParameterConfigList from a marshalled stream
	// @param istream the input stream
	//
	public void _read(org.omg.CORBA.portable.InputStream istream) {
		value = ChannelParameterConfigListHelper.read(istream);
	}

	//
	// Write ChannelParameterConfigList into a marshalled stream
	// @param ostream the output stream
	//
	public void _write(org.omg.CORBA.portable.OutputStream ostream) {
		ChannelParameterConfigListHelper.write(ostream, value);
	}

	//
	// Return the ChannelParameterConfigList TypeCode
	// @return a TypeCode
	//
	public org.omg.CORBA.TypeCode _type() {
		return ChannelParameterConfigListHelper.type();
	}

}
