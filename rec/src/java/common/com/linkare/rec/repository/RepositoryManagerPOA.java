package com.linkare.rec.repository;

import java.util.Hashtable;

import org.omg.CORBA.BAD_OPERATION;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.ORB;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerHelper;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.UserInfoHelper;

@SuppressWarnings("unchecked")
public abstract class RepositoryManagerPOA extends Servant implements RepositoryManagerOperations, InvokeHandler {

	// Constructors

	private static Hashtable<String, Integer> _methods = new Hashtable<String, Integer>();
	static {
		RepositoryManagerPOA._methods.put("getDataProducer", new java.lang.Integer(0));
		RepositoryManagerPOA._methods.put("listDataProducers", new java.lang.Integer(1));
	}

	@Override
	public OutputStream _invoke(final String $method, final InputStream in, final ResponseHandler $rh) {
		OutputStream out = null;
		final java.lang.Integer __method = (java.lang.Integer) RepositoryManagerPOA._methods.get($method);
		if (__method == null) {
			throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
		}

		switch (__method.intValue()) {
		case 0: // com/linkare/rec/repository/RepositoryManager/getDataProducer
		{
			final UserInfo user = UserInfoHelper.read(in);
			final String id = DataProducerIdHelper.read(in);
			DataProducer $result = null;
			$result = getDataProducer(user, id);
			out = $rh.createReply();
			DataProducerHelper.write(out, $result);
			break;
		}

		case 1: // com/linkare/rec/repository/RepositoryManager/listDataProducers
		{
			final UserInfo user = UserInfoHelper.read(in);
			final HardwareAcquisitionConfigSearch[] search_params = VTHardwareAcquisitionConfigSearchListHelper
					.read(in);
			DataProducerConfig $result[] = null;
			$result = listDataProducers(user, search_params);
			out = $rh.createReply();
			DataProducerConfigListHelper.write(out, $result);
			break;
		}

		default:
			throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
		}

		return out;
	} // _invoke

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/repository/RepositoryManager:1.0" };

	@Override
	public String[] _all_interfaces(final POA poa, final byte[] objectId) {
		return RepositoryManagerPOA.__ids.clone();
	}

	public RepositoryManager _this() {
		return RepositoryManagerHelper.narrow(super._this_object());
	}

	public RepositoryManager _this(final ORB orb) {
		return RepositoryManagerHelper.narrow(super._this_object(orb));
	}

} // class RepositoryManagerPOA
