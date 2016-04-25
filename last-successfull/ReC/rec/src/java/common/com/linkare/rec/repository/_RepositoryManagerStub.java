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

	@Override
	public DataProducer getDataProducer(final UserInfo user, final String id) {
		InputStream $in = null;
		try {
			final OutputStream $out = _request("getDataProducer", true);
			UserInfoHelper.write($out, user);
			DataProducerIdHelper.write($out, id);
			$in = _invoke($out);
			final DataProducer $result = DataProducerHelper.read($in);
			return $result;
		} catch (final ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			throw new MARSHAL(_id);
		} catch (final RemarshalException $rm) {
			return getDataProducer(user, id);
		} finally {
			_releaseReply($in);
		}
	} // getDataProducer

	@Override
	public DataProducerConfig[] listDataProducers(final UserInfo user,
			final HardwareAcquisitionConfigSearch[] search_params) {
		InputStream $in = null;
		try {
			final OutputStream $out = _request("listDataProducers", true);
			UserInfoHelper.write($out, user);
			VTHardwareAcquisitionConfigSearchListHelper.write($out, search_params);
			$in = _invoke($out);
			final DataProducerConfig $result[] = DataProducerConfigListHelper.read($in);
			return $result;
		} catch (final ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			throw new MARSHAL(_id);
		} catch (final RemarshalException $rm) {
			return listDataProducers(user, search_params);
		} finally {
			_releaseReply($in);
		}
	} // listDataProducers

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/repository/RepositoryManager:1.0" };

	@Override
	public String[] _ids() {
		return _RepositoryManagerStub.__ids.clone();
	}

	private void readObject(final ObjectInputStream s) throws IOException {
		final String str = s.readUTF();
		final String[] args = null;
		final Properties props = null;
		final Object obj = ORB.init(args, props).string_to_object(str);
		final Delegate delegate = ((ObjectImpl) obj)._get_delegate();
		_set_delegate(delegate);
	}

	private void writeObject(final ObjectOutputStream s) throws IOException {
		final String[] args = null;
		final Properties props = null;
		final String str = ORB.init(args, props).object_to_string(this);
		s.writeUTF(str);
	}
} // class _RepositoryManagerStub
