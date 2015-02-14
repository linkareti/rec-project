package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import com.linkare.rec.data.synch.VTFrequencyHelper;

abstract public class FrequencySearchHelper {
	private static String _id = "IDL:com/linkare/rec/repository/FrequencySearch:1.0";

	public static void insert(final Any a, final FrequencySearch that) {
		final OutputStream out = a.create_output_stream();
		a.type(FrequencySearchHelper.type());
		FrequencySearchHelper.write(out, that);
		a.read_value(out.create_input_stream(), FrequencySearchHelper.type());
	}

	public static FrequencySearch extract(final Any a) {
		return FrequencySearchHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;
	private static boolean __active = false;

	public static synchronized TypeCode type() {
		if (FrequencySearchHelper.__typeCode == null) {
			synchronized (TypeCode.class) {
				if (FrequencySearchHelper.__typeCode == null) {
					if (FrequencySearchHelper.__active) {
						return ORB.init().create_recursive_tc(FrequencySearchHelper._id);
					}
					FrequencySearchHelper.__active = true;
					final StructMember[] _members0 = new StructMember[2];
					TypeCode _tcOf_members0 = null;
					_tcOf_members0 = VTFrequencyHelper.type();
					_members0[0] = new StructMember("minFrequency", _tcOf_members0, null);
					_tcOf_members0 = VTFrequencyHelper.type();
					_members0[1] = new StructMember("maxFrequency", _tcOf_members0, null);
					FrequencySearchHelper.__typeCode = ORB.init().create_struct_tc(FrequencySearchHelper.id(),
							"FrequencySearch", _members0);
					FrequencySearchHelper.__active = false;
				}
			}
		}
		return FrequencySearchHelper.__typeCode;
	}

	public static String id() {
		return FrequencySearchHelper._id;
	}

	public static FrequencySearch read(final InputStream istream) {
		final FrequencySearch value = new FrequencySearch();
		value.setMinFrequency(VTFrequencyHelper.read(istream));
		value.setMaxFrequency(VTFrequencyHelper.read(istream));
		return value;
	}

	public static void write(final OutputStream ostream, final FrequencySearch value) {
		VTFrequencyHelper.write(ostream, value.getMinFrequency());
		VTFrequencyHelper.write(ostream, value.getMaxFrequency());
	}

}
