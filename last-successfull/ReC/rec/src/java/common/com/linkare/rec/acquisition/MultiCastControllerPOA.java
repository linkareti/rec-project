package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/MultiCastControllerPOA.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */
@SuppressWarnings("unchecked")
public abstract class MultiCastControllerPOA extends org.omg.PortableServer.Servant implements
		com.linkare.rec.acquisition.MultiCastControllerOperations, org.omg.CORBA.portable.InvokeHandler {

	// Constructors

	private static java.util.Hashtable _methods = new java.util.Hashtable();
	static {
		MultiCastControllerPOA._methods.put("enumerateHardware", new java.lang.Integer(0));
		MultiCastControllerPOA._methods.put("registerDataClient", new java.lang.Integer(1));
		MultiCastControllerPOA._methods.put("getClientList", new java.lang.Integer(2));
		MultiCastControllerPOA._methods.put("sendMessage", new java.lang.Integer(3));
		MultiCastControllerPOA._methods.put("registerHardware", new java.lang.Integer(4));
	}

	@Override
	public org.omg.CORBA.portable.OutputStream _invoke(final String $method,
			final org.omg.CORBA.portable.InputStream in, final org.omg.CORBA.portable.ResponseHandler $rh) {
		org.omg.CORBA.portable.OutputStream out = null;
		final java.lang.Integer __method = (java.lang.Integer) MultiCastControllerPOA._methods.get($method);
		if (__method == null) {
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		switch (__method.intValue()) {

		// version 5 - check permissions before givin' out the Hardware List
		case 0: // com/linkare/rec/acquisition/MultiCastController/enumerateHardware
		{
			try {
				final com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.MultiCastHardware[] $result = null;
				$result = enumerateHardware(user);
				out = $rh.createReply();
				com.linkare.rec.acquisition.VTMultiCastHardwareListHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (final com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 1: // com/linkare/rec/acquisition/MultiCastController/registerDataClient
		{
			try {
				final com.linkare.rec.acquisition.DataClient data_client = com.linkare.rec.acquisition.DataClientHelper
						.read(in);
				registerDataClient(data_client);
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.MaximumClientsReached $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.MaximumClientsReachedHelper.write(out, $ex);
			} catch (final com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// version 5 added suport for messages
		case 2: // com/linkare/rec/acquisition/MultiCastController/getClientList
		{
			try {
				final com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.UserInfo[] $result = null;
				$result = getClientList(user);
				out = $rh.createReply();
				com.linkare.rec.acquisition.VTUserInfoListHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (final com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// version 5 added suport for messages
		case 3: // com/linkare/rec/acquisition/MultiCastController/sendMessage
		{
			try {
				final com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				final String clientTo = com.linkare.rec.acquisition.VTClientNameHelper.read(in);
				final String message = in.read_wstring();
				sendMessage(user, clientTo, message);
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (final com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// Version 7.0 Addition - Trying to remove dependy on NameService
		case 4: // com/linkare/rec/acquisition/MultiCastController/registerHardware
		{
			final com.linkare.rec.acquisition.Hardware hardware = com.linkare.rec.acquisition.HardwareHelper.read(in);
			registerHardware(hardware);
			out = $rh.createReply();
			break;
		}

		default:
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		return out;
	} // _invoke

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/acquisition/MultiCastController:1.0" };

	@Override
	public String[] _all_interfaces(final org.omg.PortableServer.POA poa, final byte[] objectId) {
		return MultiCastControllerPOA.__ids.clone();
	}

	public MultiCastController _this() {
		return MultiCastControllerHelper.narrow(super._this_object());
	}

	public MultiCastController _this(final org.omg.CORBA.ORB orb) {
		return MultiCastControllerHelper.narrow(super._this_object(orb));
	}

} // class MultiCastControllerPOA
