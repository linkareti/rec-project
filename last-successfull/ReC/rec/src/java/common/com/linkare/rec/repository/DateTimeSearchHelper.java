package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.ORB;
import org.omg.CORBA.StructMember;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;

import com.linkare.rec.data.synch.VTDateTimeHelper;

abstract public class DateTimeSearchHelper {
	private static String _id = "IDL:com/linkare/rec/repository/DateTimeSearch:1.0";

	public static void insert(Any a, DateTimeSearch that) {
		OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static DateTimeSearch extract(Any a) {
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
					StructMember[] _members0 = new StructMember[2];
					TypeCode _tcOf_members0 = null;
					_tcOf_members0 = VTDateTimeHelper.type();
					_members0[0] = new StructMember("minDateTime", _tcOf_members0, null);
					_tcOf_members0 = VTDateTimeHelper.type();
					_members0[1] = new StructMember("maxDateTime", _tcOf_members0, null);
					__typeCode = ORB.init().create_struct_tc(DateTimeSearchHelper.id(), "DateTimeSearch", _members0);
					__active = false;
				}
			}
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static DateTimeSearch read(InputStream istream) {
		DateTimeSearch value = new DateTimeSearch();
		value.setMinDateTime(VTDateTimeHelper.read(istream));
		value.setMaxDateTime(VTDateTimeHelper.read(istream));
		return value;
	}

	public static void write(OutputStream ostream, DateTimeSearch value) {
		VTDateTimeHelper.write(ostream, value.getMinDateTime());
		VTDateTimeHelper.write(ostream, value.getMaxDateTime());
	}

}
