package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/DataReceiverHelper.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

abstract public class DataReceiverHelper {
	private static String _id = "IDL:com/linkare/rec/acquisition/DataReceiver:1.0";

	public static void insert(final org.omg.CORBA.Any a, final com.linkare.rec.acquisition.DataReceiver that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(DataReceiverHelper.type());
		DataReceiverHelper.write(out, that);
		a.read_value(out.create_input_stream(), DataReceiverHelper.type());
	}

	public static com.linkare.rec.acquisition.DataReceiver extract(final org.omg.CORBA.Any a) {
		return DataReceiverHelper.read(a.create_input_stream());
	}

	private static org.omg.CORBA.TypeCode __typeCode = null;

	synchronized public static org.omg.CORBA.TypeCode type() {
		if (DataReceiverHelper.__typeCode == null) {
			DataReceiverHelper.__typeCode = org.omg.CORBA.ORB.init().create_interface_tc(
					com.linkare.rec.acquisition.DataReceiverHelper.id(), "DataReceiver");
		}
		return DataReceiverHelper.__typeCode;
	}

	public static String id() {
		return DataReceiverHelper._id;
	}

	public static com.linkare.rec.acquisition.DataReceiver read(final org.omg.CORBA.portable.InputStream istream) {
		return DataReceiverHelper.narrow(istream.read_Object(_DataReceiverStub.class));
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final com.linkare.rec.acquisition.DataReceiver value) {
		ostream.write_Object(value);
	}

	public static com.linkare.rec.acquisition.DataReceiver narrow(final org.omg.CORBA.Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof com.linkare.rec.acquisition.DataReceiver) {
			return (com.linkare.rec.acquisition.DataReceiver) obj;
		} else if (!obj._is_a(DataReceiverHelper.id())) {
			throw new org.omg.CORBA.BAD_PARAM();
		} else {
			final org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
			final com.linkare.rec.acquisition._DataReceiverStub stub = new com.linkare.rec.acquisition._DataReceiverStub();
			stub._set_delegate(delegate);
			return stub;
		}
	}

}