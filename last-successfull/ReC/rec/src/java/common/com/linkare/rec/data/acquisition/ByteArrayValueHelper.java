package com.linkare.rec.data.acquisition;

abstract public class ByteArrayValueHelper {
	private static String _id = "IDL:com/linkare/rec/data/acquisitionData/ByteArrayValue:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.data.acquisition.ByteArrayValue that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.data.acquisition.ByteArrayValue extract(org.omg.CORBA.Any a) {
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[2];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_octet);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_sequence_tc(0, _tcOf_members0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.data.acquisition.ByteArrayHelper.id(), "ByteArray", _tcOf_members0);
					_members0[0] = new org.omg.CORBA.StructMember("data", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.WStringValueHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember("mime_type", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.data.acquisition.ByteArrayValueHelper.id(), "ByteArrayValue", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.data.acquisition.ByteArrayValue read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.data.acquisition.ByteArrayValue value = new com.linkare.rec.data.acquisition.ByteArrayValue();
		value.setData(com.linkare.rec.data.acquisition.ByteArrayHelper.read(istream));
		value.setMimeType((String) org.omg.CORBA.WStringValueHelper.read(istream));
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.data.acquisition.ByteArrayValue value) {
		com.linkare.rec.data.acquisition.ByteArrayHelper.write(ostream, value.getData());
		org.omg.CORBA.WStringValueHelper.write(ostream, value.getMimeType());
	}

}
