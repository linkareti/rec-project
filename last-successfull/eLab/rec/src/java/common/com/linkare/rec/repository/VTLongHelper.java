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

	public static void insert(Any a, VTLong that) {
		org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static VTLong extract(Any a) {
		return read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (__typeCode == null) {
			synchronized (TypeCode.class) {
				if (__typeCode == null) {
					if (__active) {
						return ORB.init().create_recursive_tc(_id);
					}
					__active = true;
					__typeCode = ORB.init().get_primitive_tc(TCKind.tk_long);
					__typeCode = ORB.init().create_value_box_tc(_id, "VTLong", __typeCode);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static VTLong read(org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (VTLong) ((InputStream) istream).read_value(_instance);
	}

	public Serializable read_value(org.omg.CORBA.portable.InputStream istream) {
		int tmp;
		tmp = istream.read_long();
		return new VTLong(tmp);
	}

	public static void write(org.omg.CORBA.portable.OutputStream ostream, VTLong value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, _instance);
	}

	public void write_value(org.omg.CORBA.portable.OutputStream ostream, Serializable value) {
		if (!(value instanceof VTLong)) {
			throw new MARSHAL();
		}
		VTLong valueType = (VTLong) value;
		ostream.write_long(valueType.getValue());
	}

	public String get_id() {
		return _id;
	}

}
