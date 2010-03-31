package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/IncorrectStateExceptionHelper.java . Generated by
 * the IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

public abstract class IncorrectStateExceptionHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0";

	public static void insert(org.omg.CORBA.Any a, com.linkare.rec.acquisition.IncorrectStateException that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static com.linkare.rec.acquisition.IncorrectStateException extract(org.omg.CORBA.Any a) {
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
					org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[3];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[0] = new org.omg.CORBA.StructMember("errorCode", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.acquisition.VTHardwareStateHelper.type();
					_members0[1] = new org.omg.CORBA.StructMember("actualState", _tcOf_members0, null);
					_tcOf_members0 = com.linkare.rec.acquisition.VTHardwareStateHelper.type();
					_members0[2] = new org.omg.CORBA.StructMember("requiredState", _tcOf_members0, null);
					__typeCode = org.omg.CORBA.ORB.init().create_exception_tc(
							com.linkare.rec.acquisition.IncorrectStateExceptionHelper.id(), "IncorrectStateException",
							_members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static com.linkare.rec.acquisition.IncorrectStateException read(org.omg.CORBA.portable.InputStream istream) {
		com.linkare.rec.acquisition.IncorrectStateException value = new com.linkare.rec.acquisition.IncorrectStateException();
		// read and discard the repository ID
		istream.read_string();
		value.errorCode = istream.read_long();
		value.actualState = (com.linkare.rec.acquisition.HardwareState) com.linkare.rec.acquisition.VTHardwareStateHelper
				.read(istream);
		value.requiredState = (com.linkare.rec.acquisition.HardwareState) com.linkare.rec.acquisition.VTHardwareStateHelper
				.read(istream);
		return value;
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream,
			com.linkare.rec.acquisition.IncorrectStateException value) {
		// write the repository ID
		ostream.write_string(id());
		ostream.write_long(value.errorCode);
		com.linkare.rec.acquisition.VTHardwareStateHelper.write(ostream, value.actualState);
		com.linkare.rec.acquisition.VTHardwareStateHelper.write(ostream, value.requiredState);
	}

}
