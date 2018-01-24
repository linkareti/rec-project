package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/_MultiCastHardwareStub.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

// if you get here in a middle of1 an acquisition call getAllSamplesUntilNow
public class _MultiCastHardwareStub extends org.omg.CORBA.portable.ObjectImpl implements
		com.linkare.rec.acquisition.MultiCastHardware {

	// that the DataClient must be regsitered...
	@Override
	public com.linkare.rec.data.metadata.HardwareInfo getHardwareInfo(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("getHardwareInfo", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			final com.linkare.rec.data.metadata.HardwareInfo $result = com.linkare.rec.data.metadata.HardwareInfoHelper
					.read($in);
			return $result;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			return getHardwareInfo(user);
		} finally {
			_releaseReply($in);
		}
	} // getHardwareInfo

	// give me a way to build a user interface
	@Override
	public com.linkare.rec.acquisition.HardwareState getHardwareState(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("getHardwareState", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			final com.linkare.rec.acquisition.HardwareState $result = com.linkare.rec.acquisition.HardwareStateHelper
					.read($in);
			return $result;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			return getHardwareState(user);
		} finally {
			_releaseReply($in);
		}
	} // getHardwareState

	// How are you?
	@Override
	public void requireLock(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.NotOwnerException,
			com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("requireLock", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			return;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotOwnerException:1.0")) {
				throw com.linkare.rec.acquisition.NotOwnerExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			requireLock(user);
		} finally {
			_releaseReply($in);
		}
	} // requireLock

	@Override
	public void registerDataClient(final com.linkare.rec.acquisition.DataClient data_client)
			throws com.linkare.rec.acquisition.NotAvailableException,
			com.linkare.rec.acquisition.MaximumClientsReached, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("registerDataClient", true);
			com.linkare.rec.acquisition.DataClientHelper.write($out, data_client);
			$in = _invoke($out);
			return;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/MaximumClientsReached:1.0")) {
				throw com.linkare.rec.acquisition.MaximumClientsReachedHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			registerDataClient(data_client);
		} finally {
			_releaseReply($in);
		}
	} // registerDataClient

	@Override
	public void configure(final com.linkare.rec.acquisition.UserInfo user,
			final com.linkare.rec.data.config.HardwareAcquisitionConfig configuration)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.WrongConfigurationException,
			com.linkare.rec.acquisition.NotOwnerException, com.linkare.rec.acquisition.NotRegistered,
			com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("configure", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.write($out, configuration);
			$in = _invoke($out);
			return;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/WrongConfigurationException:1.0")) {
				throw com.linkare.rec.acquisition.WrongConfigurationExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotOwnerException:1.0")) {
				throw com.linkare.rec.acquisition.NotOwnerExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			configure(user, configuration);
		} finally {
			_releaseReply($in);
		}
	} // configure

	@Override
	public com.linkare.rec.acquisition.DataProducer start(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.NotOwnerException,
			com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("start", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			final com.linkare.rec.acquisition.DataProducer $result = com.linkare.rec.acquisition.DataProducerHelper
					.read($in);
			return $result;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotOwnerException:1.0")) {
				throw com.linkare.rec.acquisition.NotOwnerExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			return start(user);
		} finally {
			_releaseReply($in);
		}
	} // start

	@Override
	public com.linkare.rec.acquisition.DataProducer startOutput(final com.linkare.rec.acquisition.UserInfo user,
			final com.linkare.rec.acquisition.DataProducer data_source)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.NotOwnerException,
			com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("startOutput", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			com.linkare.rec.acquisition.DataProducerHelper.write($out, data_source);
			$in = _invoke($out);
			final com.linkare.rec.acquisition.DataProducer $result = com.linkare.rec.acquisition.DataProducerHelper
					.read($in);
			return $result;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotOwnerException:1.0")) {
				throw com.linkare.rec.acquisition.NotOwnerExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			return startOutput(user, data_source);
		} finally {
			_releaseReply($in);
		}
	} // startOutput

	@Override
	public void stop(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.NotOwnerException,
			com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("stop", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			return;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotOwnerException:1.0")) {
				throw com.linkare.rec.acquisition.NotOwnerExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			stop(user);
		} finally {
			_releaseReply($in);
		}
	} // stop

	@Override
	public void reset(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.NotOwnerException,
			com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("reset", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			return;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotOwnerException:1.0")) {
				throw com.linkare.rec.acquisition.NotOwnerExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			reset(user);
		} finally {
			_releaseReply($in);
		}
	} // reset

	@Override
	public com.linkare.rec.acquisition.DataProducer getDataProducer(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.IncorrectStateException,
			com.linkare.rec.acquisition.NotAvailableException, com.linkare.rec.acquisition.NotRegistered,
			com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("getDataProducer", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			final com.linkare.rec.acquisition.DataProducer $result = com.linkare.rec.acquisition.DataProducerHelper
					.read($in);
			return $result;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0")) {
				throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0")) {
				throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			return getDataProducer(user);
		} finally {
			_releaseReply($in);
		}
	} // getDataProducer

	// VTClientNameList getClientQueue(in DataClient data_client) raises
	// (NotRegistered);
	@Override
	public com.linkare.rec.acquisition.UserInfo[] getClientList(final com.linkare.rec.acquisition.UserInfo user)
			throws com.linkare.rec.acquisition.NotRegistered, com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("getClientList", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, user);
			$in = _invoke($out);
			final com.linkare.rec.acquisition.UserInfo[] $result = com.linkare.rec.acquisition.VTUserInfoListHelper
					.read($in);
			return $result;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			return getClientList(user);
		} finally {
			_releaseReply($in);
		}
	} // getClientList

	// version 5 added suport for messages
	@Override
	public void sendMessage(final com.linkare.rec.acquisition.UserInfo userFrom, final String clientTo,
			final String message) throws com.linkare.rec.acquisition.NotRegistered,
			com.linkare.rec.acquisition.NotAuthorized {
		org.omg.CORBA.portable.InputStream $in = null;
		try {
			final org.omg.CORBA.portable.OutputStream $out = _request("sendMessage", true);
			com.linkare.rec.acquisition.UserInfoHelper.write($out, userFrom);
			com.linkare.rec.acquisition.VTClientNameHelper.write($out, clientTo);
			$out.write_wstring(message);
			$in = _invoke($out);
			return;
		} catch (final org.omg.CORBA.portable.ApplicationException $ex) {
			$in = $ex.getInputStream();
			final String _id = $ex.getId();
			if (_id.equals("IDL:com/linkare/rec/acquisition/NotRegistered:1.0")) {
				throw com.linkare.rec.acquisition.NotRegisteredHelper.read($in);
			} else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0")) {
				throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
			} else {
				throw new org.omg.CORBA.MARSHAL(_id);
			}
		} catch (final org.omg.CORBA.portable.RemarshalException $rm) {
			sendMessage(userFrom, clientTo, message);
		} finally {
			_releaseReply($in);
		}
	} // sendMessage

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/acquisition/MultiCastHardware:1.0" };

	@Override
	public String[] _ids() {
		return _MultiCastHardwareStub.__ids.clone();
	}

	private void readObject(final java.io.ObjectInputStream s) throws java.io.IOException {
		final String str = s.readUTF();
		final String[] args = null;
		final java.util.Properties props = null;
		final org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init(args, props).string_to_object(str);
		final org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
		_set_delegate(delegate);
	}

	private void writeObject(final java.io.ObjectOutputStream s) throws java.io.IOException {
		final String[] args = null;
		final java.util.Properties props = null;
		final String str = org.omg.CORBA.ORB.init(args, props).object_to_string(this);
		s.writeUTF(str);
	}
} // class _MultiCastHardwareStub
