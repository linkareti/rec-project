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

public final class VTSamplesNumSearchHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTSamplesNumSearch:1.0";

	private static VTSamplesNumSearchHelper _instance = new VTSamplesNumSearchHelper();

	public VTSamplesNumSearchHelper() {
	}

	public static void insert(final Any a, final SamplesNumSearch that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTSamplesNumSearchHelper.type());
		VTSamplesNumSearchHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTSamplesNumSearchHelper.type());
	}

	public static SamplesNumSearch extract(final Any a) {
		return VTSamplesNumSearchHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTSamplesNumSearchHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTSamplesNumSearchHelper.__typeCode == null) {
					if (VTSamplesNumSearchHelper.__active) {
						return ORB.init().create_recursive_tc(VTSamplesNumSearchHelper._id);
					}
					VTSamplesNumSearchHelper.__active = true;
					VTSamplesNumSearchHelper.__typeCode = SamplesNumSearchHelper.type();
					VTSamplesNumSearchHelper.__typeCode = ORB.init().create_value_box_tc(VTSamplesNumSearchHelper._id,
							"VTSamplesNumSearch", VTSamplesNumSearchHelper.__typeCode);
					VTSamplesNumSearchHelper.__active = false;
				}
			}
		}
		return VTSamplesNumSearchHelper.__typeCode;
	}

	public static String id() {
		return VTSamplesNumSearchHelper._id;
	}

	public static SamplesNumSearch read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (SamplesNumSearch) ((InputStream) istream).read_value(VTSamplesNumSearchHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		final SamplesNumSearch tmp = SamplesNumSearchHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream, final SamplesNumSearch value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTSamplesNumSearchHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof SamplesNumSearch)) {
			throw new MARSHAL();
		}
		final SamplesNumSearch valueType = (SamplesNumSearch) value;
		SamplesNumSearchHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTSamplesNumSearchHelper._id;
	}

}
