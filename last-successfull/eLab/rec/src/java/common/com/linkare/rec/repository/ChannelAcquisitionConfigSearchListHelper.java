package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

abstract public class ChannelAcquisitionConfigSearchListHelper {
	private static String _id = "IDL:com/linkare/rec/repository/ChannelAcquisitionConfigSearchList:1.0";

	public static void insert(final Any a, final ChannelAcquisitionConfigSearch[] that) {
		final OutputStream out = a.create_output_stream();
		a.type(ChannelAcquisitionConfigSearchListHelper.type());
		ChannelAcquisitionConfigSearchListHelper.write(out, that);
		a.read_value(out.create_input_stream(), ChannelAcquisitionConfigSearchListHelper.type());
	}

	public static ChannelAcquisitionConfigSearch[] extract(final Any a) {
		return ChannelAcquisitionConfigSearchListHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;

	public static synchronized TypeCode type() {
		if (ChannelAcquisitionConfigSearchListHelper.__typeCode == null) {
			ChannelAcquisitionConfigSearchListHelper.__typeCode = ChannelAcquisitionConfigSearchHelper.type();
			ChannelAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_sequence_tc(0,
					ChannelAcquisitionConfigSearchListHelper.__typeCode);
			ChannelAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_alias_tc(
					ChannelAcquisitionConfigSearchListHelper.id(), "ChannelAcquisitionConfigSearchList",
					ChannelAcquisitionConfigSearchListHelper.__typeCode);
		}
		return ChannelAcquisitionConfigSearchListHelper.__typeCode;
	}

	public static String id() {
		return ChannelAcquisitionConfigSearchListHelper._id;
	}

	public static ChannelAcquisitionConfigSearch[] read(final InputStream istream) {
		ChannelAcquisitionConfigSearch value[] = null;
		final int _len0 = istream.read_long();
		value = new ChannelAcquisitionConfigSearch[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = ChannelAcquisitionConfigSearchHelper.read(istream);
		}
		return value;
	}

	public static void write(final OutputStream ostream, final ChannelAcquisitionConfigSearch[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			ChannelAcquisitionConfigSearchHelper.write(ostream, value[_i0]);
		}
	}

}
