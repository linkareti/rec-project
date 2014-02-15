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

public final class VTHardwareAcquisitionConfigSearchListHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTHardwareAcquisitionConfigSearchList:1.0";

	private static VTHardwareAcquisitionConfigSearchListHelper _instance = new VTHardwareAcquisitionConfigSearchListHelper();

	public VTHardwareAcquisitionConfigSearchListHelper() {
	}

	public static void insert(final Any a, final HardwareAcquisitionConfigSearch[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTHardwareAcquisitionConfigSearchListHelper.type());
		VTHardwareAcquisitionConfigSearchListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTHardwareAcquisitionConfigSearchListHelper.type());
	}

	public static HardwareAcquisitionConfigSearch[] extract(final Any a) {
		return VTHardwareAcquisitionConfigSearchListHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTHardwareAcquisitionConfigSearchListHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTHardwareAcquisitionConfigSearchListHelper.__typeCode == null) {
					if (VTHardwareAcquisitionConfigSearchListHelper.__active) {
						return ORB.init().create_recursive_tc(VTHardwareAcquisitionConfigSearchListHelper._id);
					}
					VTHardwareAcquisitionConfigSearchListHelper.__active = true;
					VTHardwareAcquisitionConfigSearchListHelper.__typeCode = HardwareAcquisitionConfigSearchHelper
							.type();
					VTHardwareAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_sequence_tc(0,
							VTHardwareAcquisitionConfigSearchListHelper.__typeCode);
					VTHardwareAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_alias_tc(
							HardwareAcquisitionConfigSearchListHelper.id(), "HardwareAcquisitionConfigSearchList",
							VTHardwareAcquisitionConfigSearchListHelper.__typeCode);
					VTHardwareAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_value_box_tc(
							VTHardwareAcquisitionConfigSearchListHelper._id, "VTHardwareAcquisitionConfigSearchList",
							VTHardwareAcquisitionConfigSearchListHelper.__typeCode);
					VTHardwareAcquisitionConfigSearchListHelper.__active = false;
				}
			}
		}
		return VTHardwareAcquisitionConfigSearchListHelper.__typeCode;
	}

	public static String id() {
		return VTHardwareAcquisitionConfigSearchListHelper._id;
	}

	public static HardwareAcquisitionConfigSearch[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (HardwareAcquisitionConfigSearch[]) ((InputStream) istream)
				.read_value(VTHardwareAcquisitionConfigSearchListHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		HardwareAcquisitionConfigSearch[] tmp;
		tmp = HardwareAcquisitionConfigSearchListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final HardwareAcquisitionConfigSearch[] value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTHardwareAcquisitionConfigSearchListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof HardwareAcquisitionConfigSearch[])) {
			throw new MARSHAL();
		}
		final HardwareAcquisitionConfigSearch[] valueType = (HardwareAcquisitionConfigSearch[]) value;
		HardwareAcquisitionConfigSearchListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTHardwareAcquisitionConfigSearchListHelper._id;
	}

}
