package com.linkare.rec.acquisition;

//Version 7.0 Addition
abstract public class PropertyHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/Property:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.Property that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(PropertyHelper.type());
		PropertyHelper.write(out, that);
		a.read_value(out.create_input_stream(), PropertyHelper.type());
	}

	public static com.linkare.rec.acquisition.Property extract(final org.omg.CORBA.Any a) {
		return PropertyHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (PropertyHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (PropertyHelper.__typeCode == null) {
					if (PropertyHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(PropertyHelper._id);
					}
					PropertyHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[2];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_members0[0] = new org.omg.CORBA.StructMember("name", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_any);
					_members0[1] = new org.omg.CORBA.StructMember("value", _tcOf_members0, null);
					PropertyHelper.__typeCode = org.omg.CORBA.ORB.init().create_struct_tc(
							com.linkare.rec.acquisition.PropertyHelper.id(), "Property", _members0);
					PropertyHelper.__active = false;
				}
			}
		}
		return PropertyHelper.__typeCode;
	}

	public static String id() {
		return PropertyHelper._id;
	}

	public static com.linkare.rec.acquisition.Property read(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.acquisition.Property value = new com.linkare.rec.acquisition.Property();
		value.setName(istream.read_wstring());
		value.setValue(istream.read_any());
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.Property value) {
		ostream.write_wstring(value.getName());
		ostream.write_any(value.getValue());
	}

}
