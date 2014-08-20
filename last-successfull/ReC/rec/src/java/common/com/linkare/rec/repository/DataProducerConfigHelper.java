package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import com.linkare.rec.data.config.HardwareAcquisitionConfigHelper;

abstract public class DataProducerConfigHelper {
	private static String _id = "IDL:com/linkare/rec/repository/DataProducerConfig:1.0";

	public static void insert(final Any a, final DataProducerConfig that) {
		final OutputStream out = a.create_output_stream();
		a.type(DataProducerConfigHelper.type());
		DataProducerConfigHelper.write(out, that);
		a.read_value(out.create_input_stream(), DataProducerConfigHelper.type());
	}

	public static DataProducerConfig extract(final Any a) {
		return DataProducerConfigHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (DataProducerConfigHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (DataProducerConfigHelper.__typeCode == null) {
					if (DataProducerConfigHelper.__active) {
						return ORB.init().create_recursive_tc(DataProducerConfigHelper._id);
					}
					DataProducerConfigHelper.__active = true;
					final StructMember[] _members0 = new StructMember[3];
					TypeCode _tcOf_members0 = null;
					_tcOf_members0 = HardwareAcquisitionConfigHelper.type();
					_members0[0] = new StructMember("hardwareConfig", _tcOf_members0, null);
					_tcOf_members0 = ORB.init().create_wstring_tc(0);
					_tcOf_members0 = ORB.init().create_alias_tc(DataProducerIdHelper.id(), "DataProducerId",
							_tcOf_members0);
					_members0[1] = new StructMember("id", _tcOf_members0, null);
					_tcOf_members0 = ORB.init().create_wstring_tc(0);
					_members0[2] = new StructMember("hardware_unique_id", _tcOf_members0, null);
					DataProducerConfigHelper.__typeCode = ORB.init().create_struct_tc(DataProducerConfigHelper.id(),
							"DataProducerConfig", _members0);
					DataProducerConfigHelper.__active = false;
				}
			}
		}
		return DataProducerConfigHelper.__typeCode;
	}

	public static String id() {
		return DataProducerConfigHelper._id;
	}

	public static DataProducerConfig read(final InputStream istream) {
		final DataProducerConfig value = new DataProducerConfig();
		value.setHardwareConfig(HardwareAcquisitionConfigHelper.read(istream));
		value.setID(istream.read_wstring());
		value.setHardwareUniqueID(istream.read_wstring());
		return value;
	}

	public static void write(final OutputStream ostream, final DataProducerConfig value) {
		HardwareAcquisitionConfigHelper.write(ostream, value.getHardwareConfig());
		ostream.write_wstring(value.getID());
		ostream.write_wstring(value.getHardwareUniqueID());
	}

}
