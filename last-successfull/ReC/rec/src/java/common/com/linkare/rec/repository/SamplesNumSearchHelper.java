package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

abstract public class SamplesNumSearchHelper {
	private static String _id = "IDL:com/linkare/rec/repository/SamplesNumSearch:1.0";

	public static void insert(final Any a, final SamplesNumSearch that) {
		final OutputStream out = a.create_output_stream();
		a.type(SamplesNumSearchHelper.type());
		SamplesNumSearchHelper.write(out, that);
		a.read_value(out.create_input_stream(), SamplesNumSearchHelper.type());
	}

	public static SamplesNumSearch extract(final Any a) {
		return SamplesNumSearchHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (SamplesNumSearchHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (SamplesNumSearchHelper.__typeCode == null) {
					if (SamplesNumSearchHelper.__active) {
						return ORB.init().create_recursive_tc(SamplesNumSearchHelper._id);
					}
					SamplesNumSearchHelper.__active = true;
					final StructMember[] _members0 = new StructMember[2];
					TypeCode _tcOf_members0 = null;
					_tcOf_members0 = VTLongHelper.type();
					_members0[0] = new StructMember("minSamplesNum", _tcOf_members0, null);
					_tcOf_members0 = VTLongHelper.type();
					_members0[1] = new StructMember("maxSamplesNum", _tcOf_members0, null);
					SamplesNumSearchHelper.__typeCode = ORB.init().create_struct_tc(SamplesNumSearchHelper.id(),
							"SamplesNumSearch", _members0);
					SamplesNumSearchHelper.__active = false;
				}
			}
		}
		return SamplesNumSearchHelper.__typeCode;
	}

	public static String id() {
		return SamplesNumSearchHelper._id;
	}

	public static SamplesNumSearch read(final InputStream istream) {
		final SamplesNumSearch value = new SamplesNumSearch();
		value.setMinSamplesNum(VTLongHelper.read(istream));
		value.setMaxSamplesNum(VTLongHelper.read(istream));
		return value;
	}

	public static void write(final OutputStream ostream, final SamplesNumSearch value) {
		VTLongHelper.write(ostream, value.getMinSamplesNum());
		VTLongHelper.write(ostream, value.getMaxSamplesNum());
	}

}
