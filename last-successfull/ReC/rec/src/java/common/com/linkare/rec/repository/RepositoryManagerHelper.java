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

	public static void insert(final Any a, final RepositoryManager that) {
		final OutputStream out = a.create_output_stream();
		a.type(RepositoryManagerHelper.type());
		RepositoryManagerHelper.write(out, that);
		a.read_value(out.create_input_stream(), RepositoryManagerHelper.type());
	}

	public static RepositoryManager extract(final Any a) {
		return RepositoryManagerHelper.read(a.create_input_stream());
	}

	private static TypeCode __typeCode = null;

	public static synchronized TypeCode type() {
		if (RepositoryManagerHelper.__typeCode == null) {
			RepositoryManagerHelper.__typeCode = ORB.init().create_interface_tc(RepositoryManagerHelper.id(),
					"RepositoryManager");
		}
		return RepositoryManagerHelper.__typeCode;
	}

	public static String id() {
		return RepositoryManagerHelper._id;
	}

	public static RepositoryManager read(final InputStream istream) {
		return RepositoryManagerHelper.narrow(istream.read_Object(_RepositoryManagerStub.class));
	}

	public static void write(final OutputStream ostream, final RepositoryManager value) {
		ostream.write_Object(value);
	}

	public static RepositoryManager narrow(final org.omg.CORBA.Object obj) {
		if (obj == null) {
			return null;
		} else if (obj instanceof RepositoryManager) {
			return (RepositoryManager) obj;
		} else if (!obj._is_a(RepositoryManagerHelper.id())) {
			throw new BAD_PARAM();
		} else {
			final Delegate delegate = ((ObjectImpl) obj)._get_delegate();
			final _RepositoryManagerStub stub = new _RepositoryManagerStub();
			stub._set_delegate(delegate);
			return stub;
		}
	}

}
