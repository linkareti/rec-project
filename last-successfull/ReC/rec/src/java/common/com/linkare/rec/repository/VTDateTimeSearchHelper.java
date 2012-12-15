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

public final class VTDateTimeSearchHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTDateTimeSearch:1.0";

	private static VTDateTimeSearchHelper _instance = new VTDateTimeSearchHelper();

	public VTDateTimeSearchHelper() {
	}

	public static void insert(final Any a, final DateTimeSearch that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTDateTimeSearchHelper.type());
		VTDateTimeSearchHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTDateTimeSearchHelper.type());
	}

	public static DateTimeSearch extract(final Any a) {
		return VTDateTimeSearchHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTDateTimeSearchHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTDateTimeSearchHelper.__typeCode == null) {
					if (VTDateTimeSearchHelper.__active) {
						return ORB.init().create_recursive_tc(VTDateTimeSearchHelper._id);
					}
					VTDateTimeSearchHelper.__active = true;
					VTDateTimeSearchHelper.__typeCode = DateTimeSearchHelper.type();
					VTDateTimeSearchHelper.__typeCode = ORB.init().create_value_box_tc(VTDateTimeSearchHelper._id,
							"VTDateTimeSearch", VTDateTimeSearchHelper.__typeCode);
					VTDateTimeSearchHelper.__active = false;
				}
			}
		}
		return VTDateTimeSearchHelper.__typeCode;
	}

	public static String id() {
		return VTDateTimeSearchHelper._id;
	}

	public static DateTimeSearch read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (DateTimeSearch) ((InputStream) istream).read_value(VTDateTimeSearchHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final DateTimeSearch tmp = DateTimeSearchHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final DateTimeSearch value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTDateTimeSearchHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof DateTimeSearch)) {
			throw new MARSHAL();
		}
		final DateTimeSearch valueType = (DateTimeSearch) value;
		DateTimeSearchHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTDateTimeSearchHelper._id;
	}

}
