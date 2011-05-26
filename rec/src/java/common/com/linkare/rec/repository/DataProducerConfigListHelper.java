package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

abstract public class DataProducerConfigListHelper {
	private static String _id = "IDL:com/linkare/rec/repository/DataProducerConfigList:1.0";

	public static void insert(final Any a, final DataProducerConfig[] that) {
		final OutputStream out = a.create_output_stream();
		a.type(DataProducerConfigListHelper.type());
		DataProducerConfigListHelper.write(out, that);
		a.read_value(out.create_input_stream(), DataProducerConfigListHelper.type());
	}

	public static DataProducerConfig[] extract(final Any a) {
		return DataProducerConfigListHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;

	public static synchronized TypeCode type() {
		if (DataProducerConfigListHelper.__typeCode == null) {
			DataProducerConfigListHelper.__typeCode = DataProducerConfigHelper.type();
			DataProducerConfigListHelper.__typeCode = ORB.init().create_sequence_tc(0,
					DataProducerConfigListHelper.__typeCode);
			DataProducerConfigListHelper.__typeCode = ORB.init().create_alias_tc(DataProducerConfigListHelper.id(),
					"DataProducerConfigList", DataProducerConfigListHelper.__typeCode);
		}
		return DataProducerConfigListHelper.__typeCode;
	}

	public static String id() {
		return DataProducerConfigListHelper._id;
	}

	public static DataProducerConfig[] read(final InputStream istream) {
		DataProducerConfig value[] = null;
		final int _len0 = istream.read_long();
		value = new DataProducerConfig[_len0];
		for (int _o1 = 0; _o1 < value.length; ++_o1) {
			value[_o1] = DataProducerConfigHelper.read(istream);
		}
		return value;
	}

	public static void write(final OutputStream ostream, final DataProducerConfig[] value) {
		ostream.write_long(value.length);
		for (int _i0 = 0; _i0 < value.length; ++_i0) {
			DataProducerConfigHelper.write(ostream, value[_i0]);
		}
	}

}
