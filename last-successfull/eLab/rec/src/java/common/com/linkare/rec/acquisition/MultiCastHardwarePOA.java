package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/MultiCastHardwarePOA.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sabado, 17 de Janeiro de 2004 19H00m GMT
 */

@SuppressWarnings("unchecked")
// if you get here in a middle of1 an acquisition call getAllSamplesUntilNow
public abstract class MultiCastHardwarePOA extends org.omg.PortableServer.Servant implements
		com.linkare.rec.acquisition.MultiCastHardwareOperations, org.omg.CORBA.portable.InvokeHandler {

	// Constructors

	private static java.util.Hashtable _methods = new java.util.Hashtable();
	static {
		_methods.put("getHardwareInfo", new java.lang.Integer(0));
		_methods.put("getHardwareState", new java.lang.Integer(1));
		_methods.put("requireLock", new java.lang.Integer(2));
		_methods.put("registerDataClient", new java.lang.Integer(3));
		_methods.put("configure", new java.lang.Integer(4));
		_methods.put("start", new java.lang.Integer(5));
		_methods.put("startOutput", new java.lang.Integer(6));
		_methods.put("stop", new java.lang.Integer(7));
		_methods.put("reset", new java.lang.Integer(8));
		_methods.put("getDataProducer", new java.lang.Integer(9));
		_methods.put("getClientList", new java.lang.Integer(10));
		_methods.put("sendMessage", new java.lang.Integer(11));
	}

	public org.omg.CORBA.portable.OutputStream _invoke(String $method, org.omg.CORBA.portable.InputStream in,
			org.omg.CORBA.portable.ResponseHandler $rh) {
		org.omg.CORBA.portable.OutputStream out = null;
		java.lang.Integer __method = (java.lang.Integer) _methods.get($method);
		if (__method == null)
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

		switch (__method.intValue()) {

		// that the DataClient must be regsitered...
		case 0: // com/linkare/rec/acquisition/MultiCastHardware/getHardwareInfo
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.data.metadata.HardwareInfo $result = null;
				$result = this.getHardwareInfo(user);
				out = $rh.createReply();
				com.linkare.rec.data.metadata.HardwareInfoHelper.write(out, $result);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// give me a way to build a user interface
		case 1: // com/linkare/rec/acquisition/MultiCastHardware/getHardwareState
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.HardwareState $result = null;
				$result = this.getHardwareState(user);
				out = $rh.createReply();
				com.linkare.rec.acquisition.HardwareStateHelper.write(out, $result);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// How are you?
		case 2: // com/linkare/rec/acquisition/MultiCastHardware/requireLock
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				this.requireLock(user);
				out = $rh.createReply();
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotOwnerException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotOwnerExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 3: // com/linkare/rec/acquisition/MultiCastHardware/registerDataClient
		{
			try {
				com.linkare.rec.acquisition.DataClient data_client = com.linkare.rec.acquisition.DataClientHelper
						.read(in);
				this.registerDataClient(data_client);
				out = $rh.createReply();
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.MaximumClientsReached $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.MaximumClientsReachedHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 4: // com/linkare/rec/acquisition/MultiCastHardware/configure
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.data.config.HardwareAcquisitionConfig configuration = com.linkare.rec.data.config.HardwareAcquisitionConfigHelper
						.read(in);
				this.configure(user, configuration);
				out = $rh.createReply();
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.WrongConfigurationException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.WrongConfigurationExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotOwnerException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotOwnerExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 5: // com/linkare/rec/acquisition/MultiCastHardware/start
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.DataProducer $result = null;
				$result = this.start(user);
				out = $rh.createReply();
				com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotOwnerException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotOwnerExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 6: // com/linkare/rec/acquisition/MultiCastHardware/startOutput
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.DataProducer data_source = com.linkare.rec.acquisition.DataProducerHelper
						.read(in);
				com.linkare.rec.acquisition.DataProducer $result = null;
				$result = this.startOutput(user, data_source);
				out = $rh.createReply();
				com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotOwnerException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotOwnerExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 7: // com/linkare/rec/acquisition/MultiCastHardware/stop
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				this.stop(user);
				out = $rh.createReply();
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotOwnerException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotOwnerExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 8: // com/linkare/rec/acquisition/MultiCastHardware/reset
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				this.reset(user);
				out = $rh.createReply();
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotOwnerException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotOwnerExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 9: // com/linkare/rec/acquisition/MultiCastHardware/getDataProducer
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.DataProducer $result = null;
				$result = this.getDataProducer(user);
				out = $rh.createReply();
				com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
			} catch (com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// VTClientNameList getClientQueue(in DataClient data_client) raises
			// (NotRegistered);
		case 10: // com/linkare/rec/acquisition/MultiCastHardware/getClientList
		{
			try {
				com.linkare.rec.acquisition.UserInfo user = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				com.linkare.rec.acquisition.UserInfo[] $result = null;
				$result = this.getClientList(user);
				out = $rh.createReply();
				com.linkare.rec.acquisition.VTUserInfoListHelper.write(out, $result);
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

			// version 5 added suport for messages
		case 11: // com/linkare/rec/acquisition/MultiCastHardware/sendMessage
		{
			try {
				com.linkare.rec.acquisition.UserInfo userFrom = com.linkare.rec.acquisition.UserInfoHelper.read(in);
				String clientTo = com.linkare.rec.acquisition.VTClientNameHelper.read(in);
				String message = in.read_wstring();
				this.sendMessage(userFrom, clientTo, message);
				out = $rh.createReply();
			} catch (com.linkare.rec.acquisition.NotRegistered $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotRegisteredHelper.write(out, $ex);
			} catch (com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		default:
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		return out;
	} // _invoke

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/acquisition/MultiCastHardware:1.0" };

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId) {
		return (String[]) __ids.clone();
	}

	public MultiCastHardware _this() {
		return MultiCastHardwareHelper.narrow(super._this_object());
	}

	public MultiCastHardware _this(org.omg.CORBA.ORB orb) {
		return MultiCastHardwareHelper.narrow(super._this_object(orb));
	}

} // class MultiCastHardwarePOA
