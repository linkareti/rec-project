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

public final class VTChannelAcquisitionConfigSearchListHelper implements BoxedValueHelper {
	private static String _id = "IDL:com/linkare/rec/repository/VTChannelAcquisitionConfigSearchList:1.0";

	private static VTChannelAcquisitionConfigSearchListHelper _instance = new VTChannelAcquisitionConfigSearchListHelper();

	public VTChannelAcquisitionConfigSearchListHelper() {
	}

	public static void insert(final Any a, final ChannelAcquisitionConfigSearch[] that) {
		final org.omg.CORBA.portable.OutputStream out = a.create_output_stream();
		a.type(VTChannelAcquisitionConfigSearchListHelper.type());
		VTChannelAcquisitionConfigSearchListHelper.write(out, that);
		a.read_value(out.create_input_stream(), VTChannelAcquisitionConfigSearchListHelper.type());
	}

	public static ChannelAcquisitionConfigSearch[] extract(final Any a) {
		return VTChannelAcquisitionConfigSearchListHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (VTChannelAcquisitionConfigSearchListHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (VTChannelAcquisitionConfigSearchListHelper.__typeCode == null) {
					if (VTChannelAcquisitionConfigSearchListHelper.__active) {
						return ORB.init().create_recursive_tc(VTChannelAcquisitionConfigSearchListHelper._id);
					}
					VTChannelAcquisitionConfigSearchListHelper.__active = true;
					VTChannelAcquisitionConfigSearchListHelper.__typeCode = ChannelAcquisitionConfigSearchHelper.type();
					VTChannelAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_sequence_tc(0,
							VTChannelAcquisitionConfigSearchListHelper.__typeCode);
					VTChannelAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_alias_tc(
							ChannelAcquisitionConfigSearchListHelper.id(), "ChannelAcquisitionConfigSearchList",
							VTChannelAcquisitionConfigSearchListHelper.__typeCode);
					VTChannelAcquisitionConfigSearchListHelper.__typeCode = ORB.init().create_value_box_tc(
							VTChannelAcquisitionConfigSearchListHelper._id, "VTChannelAcquisitionConfigSearchList",
							VTChannelAcquisitionConfigSearchListHelper.__typeCode);
					VTChannelAcquisitionConfigSearchListHelper.__active = false;
				}
			}
		}
		return VTChannelAcquisitionConfigSearchListHelper.__typeCode;
	}

	public static String id() {
		return VTChannelAcquisitionConfigSearchListHelper._id;
	}

	public static ChannelAcquisitionConfigSearch[] read(final org.omg.CORBA.portable.InputStream istream) {
		if (!(istream instanceof InputStream)) {
			throw new BAD_PARAM();
		}
		return (ChannelAcquisitionConfigSearch[]) ((InputStream) istream)
				.read_value(VTChannelAcquisitionConfigSearchListHelper._instance);
	}

	@Override
	public Serializable read_value(final org.omg.CORBA.portable.InputStream istream) {
		ChannelAcquisitionConfigSearch[] tmp;
		tmp = ChannelAcquisitionConfigSearchListHelper.read(istream);
		return tmp;
	}

	public static void write(final org.omg.CORBA.portable.OutputStream ostream,
			final ChannelAcquisitionConfigSearch[] value) {
		if (!(ostream instanceof OutputStream)) {
			throw new BAD_PARAM();
		}
		((OutputStream) ostream).write_value(value, VTChannelAcquisitionConfigSearchListHelper._instance);
	}

	@Override
	public void write_value(final org.omg.CORBA.portable.OutputStream ostream, final Serializable value) {
		if (!(value instanceof ChannelAcquisitionConfigSearch[])) {
			throw new MARSHAL();
		}
		final ChannelAcquisitionConfigSearch[] valueType = (ChannelAcquisitionConfigSearch[]) value;
		ChannelAcquisitionConfigSearchListHelper.write(ostream, valueType);
	}

	@Override
	public String get_id() {
		return VTChannelAcquisitionConfigSearchListHelper._id;
	}

}
