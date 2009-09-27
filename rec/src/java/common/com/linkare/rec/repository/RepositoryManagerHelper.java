package com.linkare.rec.repository;

import org.omg.CORBA.Any;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.ORB;
import org.omg.CORBA.TypeCode;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;

abstract public class RepositoryManagerHelper {
	private static String _id = "IDL:com/linkare/rec/repository/RepositoryManager:1.0";

	public static void insert(Any a, RepositoryManager that) {
		OutputStream out = a.create_output_stream();
		a.type(type());
		write(out, that);
		a.read_value(out.create_input_stream(), type());
	}

	public static RepositoryManager extract(Any a) {
		return read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;

	public static synchronized TypeCode type() {
		if (__typeCode == null) {
			__typeCode = ORB.init().create_interface_tc(RepositoryManagerHelper.id(), "RepositoryManager");
		}
		return __typeCode;
	}

	public static String id() {
		return _id;
	}

	public static RepositoryManager read(InputStream istream) {
		return narrow(istream.read_Object(_RepositoryManagerStub.class));
	}

	public static void write(OutputStream ostream, RepositoryManager value) {
		ostream.write_Object((org.omg.CORBA.Object) value);
	}

	public static RepositoryManager narrow(org.omg.CORBA.Object obj) {
		if (obj == null)
			return null;
		else if (obj instanceof RepositoryManager)
			return (RepositoryManager) obj;
		else if (!obj._is_a(id()))
			throw new BAD_PARAM();
		else {
			Delegate delegate = ((ObjectImpl) obj)._get_delegate();
			_RepositoryManagerStub stub = new _RepositoryManagerStub();
			stub._set_delegate(delegate);
			return stub;
		}
	}

}
