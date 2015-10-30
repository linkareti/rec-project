package com.linkare.rec.repository;

import java.io.Serializable;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.BoxedValueHelper;
import org.omg.CORBA_2_3.portable.InputStream;
import org.omg.CORBA_2_3.portable.OutputStream;

public final class VTLongHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTLong:1.0";

	private static VTLongHelper _instance = new VTLongHelper();

	public VTLongHelper() {
	}

	public static void insert(final Any a, final VTLong that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTLongHelper.type());
		VTLongHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTLongHelper.type());
	}

	public static VTLong extract(final Any a) {
		return VTLongHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTLongHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTLongHelper.__typeCode == null) {
					if (VTLongHelper.__active) {
						return ORB.init().create_recursive_tc(VTLongHelper._id);
					}
					VTLongHelper.__active = true;
					VTLongHelper.__typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
					VTLongHelper.__typeCode = ORB.init().create_value_box_tc(VTLongHelper._id, "VTLong",
							VTLongHelper.__typeCode);
					VTLongHelper.__active = false;
				}
			}
		}
		return VTLongHelper.__typeCode;
	}

	public static String id() {
		return VTLongHelper._id;
	}

	public static VTLong read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (VTLong) ((InputStream) istream).read_value(VTLongHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		int tmp;
		tmp = istream.read_long();
		return new VTLong(tmp);
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final VTLong value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTLongHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof VTLong)) {
			throw new MARSHAL();
		}
		final VTLong valueType = (VTLong) value;
		ostream.write_long(valueType.getValue());
	}

	@Override
	public String get_id() {
		return VTLongHelper._id;
	}

}
