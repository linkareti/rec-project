package com.linkare.rec.repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerHelper;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.acquisition.UserInfoHelper;

public class _RepositoryManagerStub extends ObjectImpl implements RepositoryManager {

	public DataProducer getDataProducer(UserInfo user, String id) {
		InputStream $in = null;
		try {
			OutputStream $out = _request("getDataProducer", true);
			UserInfoHelper.write($out, user);
			DataProducerIdHelper.write($out, id);
			$in = _invoke($out);
			DataProducer $result = DataProducerHelper.read($in);
			return $result;
		} catch (ApplicationException $ex) {
			$in = $ex.getInputStream();
			String _id = $ex.getId();
			throw new MARSHAL(_id);
		} catch (RemarshalException $rm) {
			return getDataProducer(user, id);
		} finally {
			_releaseReply($in);
		}
	} // getDataProducer

	public DataProducerConfig[] listDataProducers(UserInfo user, HardwareAcquisitionConfigSearch[] search_params) {
		InputStream $in = null;
		try {
			OutputStream $out = _request("listDataProducers", true);
			UserInfoHelper.write($out, user);
			VTHardwareAcquisitionConfigSearchListHelper.write($out, search_params);
			$in = _invoke($out);
			DataProducerConfig $result[] = DataProducerConfigListHelper.read($in);
			return $result;
		} catch (ApplicationException $ex) {
			$in = $ex.getInputStream();
			String _id = $ex.getId();
			throw new MARSHAL(_id);
		} catch (RemarshalException $rm) {
			return listDataProducers(user, search_params);
		} finally {
			_releaseReply($in);
		}
	} // listDataProducers

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/repository/RepositoryManager:1.0" };

	public String[] _ids() {
		return (String[]) __ids.clone();
	}

	private void readObject(ObjectInputStream s) throws IOException {
		String str = s.readUTF();
		String[] args = null;
		Properties props = null;
		Object obj = ORB.init(args, props).string_to_object(str);
		Delegate delegate = ((ObjectImpl) obj)._get_delegate();
		_set_delegate(delegate);
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		String[] args = null;
		Properties props = null;
		String str = ORB.init(args, props).object_to_string(this);
		s.writeUTF(str);
	}
} // class _RepositoryManagerStub
