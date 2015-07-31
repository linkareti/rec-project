package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/NotOwnerExceptionHelper.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

public abstract class NotOwnerExceptionHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/NotOwnerException:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.NotOwnerException that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(NotOwnerExceptionHelper.type());
		NotOwnerExceptionHelper.write(out, that);
		a.read_value(out.create_input_stream(), NotOwnerExceptionHelper.type());
	}

	public static com.linkare.rec.acquisition.NotOwnerException extract(final org.omg.CORBA.Any a) {
		return NotOwnerExceptionHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;
	private static boolean __active = false;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (NotOwnerExceptionHelper.__typeCode == null) {
			synchronized (org.omg.CORBA.TypeCode.class) {
				if (NotOwnerExceptionHelper.__typeCode == null) {
					if (NotOwnerExceptionHelper.__active) {
						return org.omg.CORBA.ORB.init().create_recursive_tc(NotOwnerExceptionHelper._id);
					}
					NotOwnerExceptionHelper.__active = true;
					final org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember[2];
					org.omg.CORBA.TypeCode _tcOf_members0 = null;
					_tcOf_members0 = org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.tk_long);
					_members0[0] = new org.omg.CORBA.StructMember("errorCode", _tcOf_members0, null);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_wstring_tc(0);
					_tcOf_members0 = org.omg.CORBA.ORB.init().create_alias_tc(
							com.linkare.rec.acquisition.ClientNameHelper.id(), "ClientName", _tcOf_members0);
					_members0[1] = new org.omg.CORBA.StructMember("currentOwner", _tcOf_members0, null);
					NotOwnerExceptionHelper.__typeCode = org.omg.CORBA.ORB.init().create_exception_tc(
							com.linkare.rec.acquisition.NotOwnerExceptionHelper.id(), "NotOwnerException", _members0);
					NotOwnerExceptionHelper.__active = false;
				}
			}
		}
		return NotOwnerExceptionHelper.__typeCode;
	}

	public static String id() {
		return NotOwnerExceptionHelper._id;
	}

	public static com.linkare.rec.acquisition.NotOwnerException read(final org.omg.CORBA.portable.InputStream istream) {
		final com.linkare.rec.acquisition.NotOwnerException value = new com.linkare.rec.acquisition.NotOwnerException();
		// read and discard the repository ID
		istream.read_string();
		value.errorCode = istream.read_long();
		value.currentOwner = istream.read_wstring();
		return value;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.NotOwnerException value) {
		// write the repository ID
		ostream.write_string(NotOwnerExceptionHelper.id());
		ostream.write_long(value.errorCode);
		ostream.write_wstring(value.currentOwner);
	}

}