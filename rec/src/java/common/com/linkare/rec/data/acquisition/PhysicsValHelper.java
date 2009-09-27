package com.linkare.rec.data.acquisition;

abstract public class PhysicsValHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/PhysicsVal:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.acquisition.PhysicsVal that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.acquisition.PhysicsVal extract(org.omg.CORBA.Any a) {
		return read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (__typeCode == null) {
			org.omg.CORBA.TypeCode _disTypeCode0;
			_disTypeCode0 = com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.type();
			org.omg.CORBA.UnionMember[] _members0 = new org.omg.CORBA.UnionMember[8];
			org.omg.CORBA.TypeCode _tcOf_members0;
			org.omg.CORBA.Any _anyOf_members0;

			// Branch for boolean_value (case label BooleanVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.BooleanVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_boolean);
			_members0[0] = new org.omg.CORBA.UnionMember("boolean_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for byte_value (case label ByteVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.ByteVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
			_members0[1] = new org.omg.CORBA.UnionMember("byte_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for short_value (case label ShortVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.ShortVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_short);
			_members0[2] = new org.omg.CORBA.UnionMember("short_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for int_value (case label IntVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.IntVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
			_members0[3] = new org.omg.CORBA.UnionMember("int_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for long_value (case label LongVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.LongVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_longlong);
			_members0[4] = new org.omg.CORBA.UnionMember("long_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for float_value (case label FloatVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.FloatVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_float);
			_members0[5] = new org.omg.CORBA.UnionMember("float_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for double_value (case label DoubleVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.DoubleVal);
			_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_double);
			_members0[6] = new org.omg.CORBA.UnionMember("double_value", _anyOf_members0, _tcOf_members0, null);

			// Branch for byte_array_value (case label ByteArrayVal)
			_anyOf_members0 = org.omg.CORBA.ORB.init().create_any();
			com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.insert(_anyOf_members0,
					com.linkare.rec.data.acquisition.PhysicsValueType.ByteArrayVal);
			_tcOf_members0 = com.linkare.rec.data.acquisition.ByteArrayValueHelper.type();
			_members0[7] = new org.omg.CORBA.UnionMember("byte_array_value", _anyOf_members0, _tcOf_members0, null);
			__typeCode = org.omg.CORBA.ORB.init().create_union_tc(
					com.linkare.rec.data.acquisition.PhysicsValHelper.id(), "PhysicsVal", _disTypeCode0, _members0);
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.acquisition.PhysicsVal read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.acquisition.PhysicsVal new_one = new com.linkare.rec.data.acquisition.PhysicsVal();

		new_one.setDiscriminator(com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.read(istream));
		if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._BooleanVal) {
			new_one.setBooleanValue(istream.read_boolean());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._ByteVal) {
			new_one.setByteValue(istream.read_octet());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._ShortVal) {
			new_one.setShortValue(istream.read_short());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._IntVal) {
			new_one.setIntValue(istream.read_long());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._LongVal) {
			new_one.setLongValue(istream.read_longlong());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._FloatVal) {
			new_one.setFloatValue(istream.read_float());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._DoubleVal) {
			new_one.setDoubleValue(istream.read_double());
		} else if (new_one.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._ByteArrayVal) {
			new_one.setByteArrayValue(com.linkare.rec.data.acquisition.ByteArrayValueHelper.read(istream));
		}

		return new_one;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.data.acquisition.PhysicsVal value) {
		com.linkare.rec.data.acquisition.PhysicsValueTypeHelper.write(ostream, value.getDiscriminator());
		if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._BooleanVal) {
			ostream.write_boolean(value.isBooleanValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._ByteVal) {
			ostream.write_octet(value.getByteValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._ShortVal) {
			ostream.write_short(value.getShortValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._IntVal) {
			ostream.write_long(value.getIntValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._LongVal) {
			ostream.write_longlong(value.getLongValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._FloatVal) {
			ostream.write_float(value.getFloatValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._DoubleVal) {
			ostream.write_double(value.getDoubleValue());
		} else if (value.getDiscriminator().value() == com.linkare.rec.data.acquisition.PhysicsValueType._ByteArrayVal) {
			com.linkare.rec.data.acquisition.ByteArrayValueHelper.write(ostream, value.getByteArrayValue());
		}
	}

}
