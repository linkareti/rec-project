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

public final class VTFrequencySearchHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTFrequencySearch:1.0";

	private static VTFrequencySearchHelper _instance = new VTFrequencySearchHelper();

	public VTFrequencySearchHelper() {
	}

	public static void insert(final Any a, final FrequencySearch that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTFrequencySearchHelper.type());
		VTFrequencySearchHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTFrequencySearchHelper.type());
	}

	public static FrequencySearch extract(final Any a) {
		return VTFrequencySearchHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTFrequencySearchHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTFrequencySearchHelper.__typeCode == null) {
					if (VTFrequencySearchHelper.__active) {
						return ORB.init().create_recursive_tc(VTFrequencySearchHelper._id);
					}
					VTFrequencySearchHelper.__active = true;
					VTFrequencySearchHelper.__typeCode = FrequencySearchHelper.type();
					VTFrequencySearchHelper.__typeCode = ORB.init().create_value_box_tc(VTFrequencySearchHelper._id,
							"VTFrequencySearch", VTFrequencySearchHelper.__typeCode);
					VTFrequencySearchHelper.__active = false;
				}
			}
		}
		return VTFrequencySearchHelper.__typeCode;
	}

	public static String id() {
		return VTFrequencySearchHelper._id;
	}

	public static FrequencySearch read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (FrequencySearch) ((InputStream) istream).read_value(VTFrequencySearchHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final FrequencySearch tmp = FrequencySearchHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final FrequencySearch value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTFrequencySearchHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof FrequencySearch)) {
			throw new MARSHAL();
		}
		final FrequencySearch valueType = (FrequencySearch) value;
		FrequencySearchHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTFrequencySearchHelper._id;
	}

}
