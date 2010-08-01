package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

abstract public class HardwareAcquisitionConfigSearchListHelper {
	private static String _id = "IDL:com/linkare/rec/repository/HardwareAcquisitionConfigSearchList:1.0";

	public static void insert(Any a, HardwareAcquisitionConfigSearch[] that) {
		OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static HardwareAcquisitionConfigSearch[] extract(Any a) {
		return read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;

	public static synchronized TypeCode type() {
		if (__typeCode == null) {
			__typeCode = HardwareAcquisitionConfigSearchHelper.type();
			__typeCode = ORB.init().create_sequence_tc(0, __typeCode);
			__typeCode = ORB.init().create_alias_tc(HardwareAcquisitionConfigSearchListHelper.id(),
					"HardwareAcquisitionConfigSearchList", __typeCode);
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static HardwareAcquisitionConfigSearch[] read(InputStream istream) {
		HardwareAcquisitionConfigSearch value[] = null;
		int _len0 = istream.read_long();
		value = new HardwareAcquisitionConfigSearch[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1)
			value[_o1] = HardwareAcquisitionConfigSearchHelper.read(istream);
		return value;
	}

	public static void write(OutputStream ostream, HardwareAcquisitionConfigSearch[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0)
			HardwareAcquisitionConfigSearchHelper.write(ostream, value[_i0]);
	}

}
