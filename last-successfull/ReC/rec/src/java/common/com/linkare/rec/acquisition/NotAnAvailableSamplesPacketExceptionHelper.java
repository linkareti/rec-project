package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/NotAnAvailableSamplesPacketExceptionHelper.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

public abstract class NotAnAvailableSamplesPacketExceptionHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/NotAnAvailableSamplesPacketException:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException extract(org.omg.CORBA.Any a) {
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
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[0] = new org.omg.CORBA.StructMember("errorCode", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[1] = new org.omg.CORBA.StructMember("firstPacketNotFound", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_exception_tc(
							com.linkare.rec.acquisition.NotAnAvailableSamplesPacketExceptionHelper.id(),
							"NotAnAvailableSamplesPacketException", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException read(
			org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException value = new com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException();
		// read and discard the repository ID
		istream.read_string();
		value.errorCode = istream.read_long();
		value.firstPacketNotFound = istream.read_long();
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException value) {
		// write the repository ID
		ostream.write_string(id());
		ostream.write_long(value.errorCode);
		ostream.write_long(value.firstPacketNotFound);
	}

}
