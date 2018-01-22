package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/WrongConfigurationExceptionHelper.java .
 * Generated by the IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

public abstract class WrongConfigurationExceptionHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/WrongConfigurationException:1.0";

	public static void insert(final org.omg.CORBA.Any a,
			final com.linkare.rec.acquisition.WrongConfigurationException that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(WrongConfigurationExceptionHelper.type());
		WrongConfigurationExceptionHelper.write(out, that);
		a.read_value(out.create_input_stream(), WrongConfigurationExceptionHelper.type());
	}

	public static com.linkare.rec.acquisition.WrongConfigurationException extract(final org.omg.CORBA.Any a) {
		return WrongConfigurationExceptionHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (WrongConfigurationExceptionHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (WrongConfigurationExceptionHelper.__typeCode == null) {
					if (WrongConfigurationExceptionHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(WrongConfigurationExceptionHelper._id);
					}
					WrongConfigurationExceptionHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[1];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[0] = new org.omg.CORBA.StructMember("errorCode", _tcOf_members0, null);
					WrongConfigurationExceptionHelper.__typeCode = org.omg.CORBA.ORB.init().create_exception_tc(
							com.linkare.rec.acquisition.WrongConfigurationExceptionHelper.id(),
							"WrongConfigurationException", _members0);
					WrongConfigurationExceptionHelper.__active = false;
				}
			}
		}
		return WrongConfigurationExceptionHelper.__typeCode;
	}

	public static String id() {
		return WrongConfigurationExceptionHelper._id;
	}

	public static com.linkare.rec.acquisition.WrongConfigurationException read(
			final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.acquisition.WrongConfigurationException value = new com.linkare.rec.acquisition.WrongConfigurationException();
		// read and discard the repository ID
		istream.read_string();
		value.errorCode = istream.read_long();
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.WrongConfigurationException value) {
		// write the repository ID
		ostream.write_string(WrongConfigurationExceptionHelper.id());
		ostream.write_long(value.errorCode);
	}

}