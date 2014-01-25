package com.linkare.rec.repository;

import java.io.Serializable;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class VTChannelNameHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTChannelName:1.0";

	private static VTChannelNameHelper _instance = new VTChannelNameHelper();

	public VTChannelNameHelper() {
	}

	public static void insert(final Any a, final String that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTChannelNameHelper.type());
		VTChannelNameHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTChannelNameHelper.type());
	}

	public static String extract(final Any a) {
		return VTChannelNameHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTChannelNameHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTChannelNameHelper.__typeCode == null) {
					if (VTChannelNameHelper.__active) {
						return ORB.init().create_recursive_tc(VTChannelNameHelper._id);
					}
					VTChannelNameHelper.__active = true;
					VTChannelNameHelper.__typeCode = ORB.init().create_wstring_tc(0);
					VTChannelNameHelper.__typeCode = ORB.init().create_value_box_tc(VTChannelNameHelper._id,
							"VTChannelName", VTChannelNameHelper.__typeCode);
					VTChannelNameHelper.__active = false;
				}
			}
		}
		return VTChannelNameHelper.__typeCode;
	}

	public static String id() {
		return VTChannelNameHelper._id;
	}

	public static String read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (String) ((InputStream) istream).read_value(VTChannelNameHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		String tmp;
		tmp = istream.read_wstring();
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final String value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTChannelNameHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof String)) {
			throw new MARSHAL();
		}
		final String valueType = (String) value;
		ostream.write_wstring(valueType);
	}

	@Override
	public String get_id() {
		return VTChannelNameHelper._id;
	}

}
