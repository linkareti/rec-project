package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

abstract public class DataProducerIdHelper {
	private static String _id = "IDL:com/linkare/rec/repository/DataProducerId:1.0";

	public static void insert(final Any a, final String that) {
		final OutputStream out = a.create_output_stream();
		a.type(DataProducerIdHelper.type());
		DataProducerIdHelper.write(out, that);
		a.read_value(out.create_input_stream(), DataProducerIdHelper.type());
	}

	public static String extract(final Any a) {
		return DataProducerIdHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;

	public static synchronized TypeCode type() {
		if (DataProducerIdHelper.__typeCode == null) {
			DataProducerIdHelper.__typeCode = ORB.init().create_wstring_tc(0);
			DataProducerIdHelper.__typeCode = ORB.init().create_alias_tc(DataProducerIdHelper.id(), "DataProducerId",
					DataProducerIdHelper.__typeCode);
		}
		return DataProducerIdHelper.__typeCode;
	}

	public static String id() {
		return DataProducerIdHelper._id;
	}

	public static String read(final InputStream istream) {
		String value = null;
		value = istream.read_wstring();
		return value;
	}

	public static void write(final OutputStream ostream, final String value) {
		ostream.write_wstring(value);
	}

}
