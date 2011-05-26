package com.linkare.rec.data.metadata;

abstract public class ChannelParameterHelper {
	private static String _id = "IDL:com/linkare/rec/data/metadata/ChannelParameter:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.data.metadata.ChannelParameter that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(ChannelParameterHelper.type());
		ChannelParameterHelper.write(out, that);
		a.read_value(out.create_input_stream(), ChannelParameterHelper.type());
	}

	public static com.linkare.rec.data.metadata.ChannelParameter extract(final org.omg.CORBA.Any a) {
		return ChannelParameterHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (ChannelParameterHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (ChannelParameterHelper.__typeCode == null) {
					if (ChannelParameterHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(ChannelParameterHelper._id);
					}
					ChannelParameterHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[4];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ParameterNameHelper.id(), "ParameterName", _tcOf_members0);
					_members0[0] = new org.omg.CORBA.StructMember("parameter_name", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.ParameterTypeHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember("parameter_type", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.data.metadata.VTParameterSelectionListHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember("parameter_selection_list", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.metadata.ParameterValueHelper.id(), "ParameterValue", _tcOf_members0);
					_members0[3] = new org.omg.CORBA.StructMember("actual_selected_value", _tcOf_members0, null);
					ChannelParameterHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.metadata.ChannelParameterHelper.id(), "ChannelParameter", _members0);
					ChannelParameterHelper.__active = false;
				}
			}
		}
		return ChannelParameterHelper.__typeCode;
	}

	public static String id() {
		return ChannelParameterHelper._id;
	}

	public static com.linkare.rec.data.metadata.ChannelParameter read(final org.omg.CORBA.portable.InputStream istream) {

		final com.linkare.rec.data.metadata.ChannelParameter new_one = new com.linkare.rec.data.metadata.ChannelParameter();

		new_one.setParameterName(com.linkare.rec.data.metadata.ParameterNameHelper.read(istream));
		new_one.setParameterType(com.linkare.rec.data.metadata.ParameterTypeHelper.read(istream));
		new_one.setParameterSelectionList(com.linkare.rec.data.metadata.VTParameterSelectionListHelper.read(istream));
		new_one.setSelectedParameterValue(istream.read_wstring());

		return new_one;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.data.metadata.ChannelParameter value) {
		com.linkare.rec.data.metadata.ParameterNameHelper.write(ostream, value.getParameterName());
		com.linkare.rec.data.metadata.ParameterTypeHelper.write(ostream, value.getParameterType());
		com.linkare.rec.data.metadata.VTParameterSelectionListHelper.write(ostream, value.getParameterSelectionList());
		ostream.write_wstring(value.getSelectedParameterValue());
	}

}
