package com.linkare.rec.data.metadata;

abstract public class ChannelInfoHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ChannelInfo:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.metadata.ChannelInfo that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.metadata.ChannelInfo extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (__typeCode == null) {
					if (__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(_id);
					}
					__active = true;
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[9];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ChannelNameHelper.id(), "ChannelName", _tcOf_members0);
					_members0[0] = new org.omg.CORBA.StructMember("channel_name", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.ScaleHelper.type();
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_sequence_tc(0, _tcOf_members0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ScaleListHelper.id(), "ScaleList", _tcOf_members0);
					_members0[1] = new org.omg.CORBA.StructMember("scales", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTScaleHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember("selected_scale", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.type();
					_members0[3] = new org.omg.CORBA.StructMember("frequencies", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.synch.VTFrequencyHelper.type();
					_members0[4] = new org.omg.CORBA.StructMember("selected_frequency", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTChannelParameterListHelper.type();
					_members0[5] = new org.omg.CORBA.StructMember("channel_parameters", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.ChannelDirectionHelper.type();
					_members0[6] = new org.omg.CORBA.StructMember("channel_direction", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean);
					_members0[7] = new org.omg.CORBA.StructMember("is_channel_independent", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.type();
					_members0[8] = new org.omg.CORBA.StructMember("sampling_scale", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.metadata.ChannelInfoHelper.id(), "ChannelInfo", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.metadata.ChannelInfo read(org.omg.CORBA.portable.InputStream istream) {

		com.linkare.rec.data.metadata.ChannelInfo new_one = new com.linkare.rec.data.metadata.ChannelInfo();

		new_one.setChannelName(com.linkare.rec.data.metadata.ChannelNameHelper.read(istream));
		new_one.setScales(com.linkare.rec.data.metadata.ScaleListHelper.read(istream));
		new_one.setActualSelectedScale(com.linkare.rec.data.metadata.VTScaleHelper.read(istream));
		new_one.setFrequencies(com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.read(istream));
		new_one.setSelectedFrequency(com.linkare.rec.data.synch.VTFrequencyHelper.read(istream));
		new_one.setChannelParameters(com.linkare.rec.data.metadata.VTChannelParameterListHelper.read(istream));
		new_one.setChannelDirection(com.linkare.rec.data.metadata.ChannelDirectionHelper.read(istream));
		new_one.setChannelIndependent(istream.read_boolean());
		new_one.setSamplingScale(com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.read(istream));
		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.data.metadata.ChannelInfo value) {

		com.linkare.rec.data.metadata.ChannelNameHelper.write(ostream, value.getChannelName());
		com.linkare.rec.data.metadata.ScaleListHelper.write(ostream, value.getScales());
		com.linkare.rec.data.metadata.VTScaleHelper.write(ostream, value.getActualSelectedScale());
		com.linkare.rec.data.metadata.VTFrequencyScaleListHelper.write(ostream, value.getFrequencies());
		com.linkare.rec.data.synch.VTFrequencyHelper.write(ostream, value.getSelectedFrequency());
		com.linkare.rec.data.metadata.VTChannelParameterListHelper.write(ostream, value.getChannelParameters());
		com.linkare.rec.data.metadata.ChannelDirectionHelper.write(ostream, value.getChannelDirection());
		ostream.write_boolean(value.isChannelIndependent());
		com.linkare.rec.data.metadata.VTSamplesNumScaleHelper.write(ostream, value.getSamplingScale());

	}
}
