package com.linkare.rec.acquisition;

@SuppressWarnings("unchecked")
public abstract class HardwarePOA extends org.omg.PortableServer.Servant implements
		com.linkare.rec.acquisition.HardwareOperations, org.omg.CORBA.portable.InvokeHandler {

	// Constructors

	private static java.util.Hashtable _methods = new java.util.Hashtable();
	static {
		HardwarePOA._methods.put("getHardwareInfo", new java.lang.Integer(0));
		HardwarePOA._methods.put("getHardwareState", new java.lang.Integer(1));
		HardwarePOA._methods.put("registerDataClient", new java.lang.Integer(2));
		HardwarePOA._methods.put("getDataClient", new java.lang.Integer(3));
		HardwarePOA._methods.put("configure", new java.lang.Integer(4));
		HardwarePOA._methods.put("start", new java.lang.Integer(5));
		HardwarePOA._methods.put("startOutput", new java.lang.Integer(6));
		HardwarePOA._methods.put("stop", new java.lang.Integer(7));
		HardwarePOA._methods.put("reset", new java.lang.Integer(8));
		HardwarePOA._methods.put("getDataProducer", new java.lang.Integer(9));
	}

	@Override
	public org.omg.CORBA.portable.OutputStream _invoke(final String $method,
			final org.omg.CORBA.portable.InputStream in, final org.omg.CORBA.portable.ResponseHandler $rh) {
		org.omg.CORBA.portable.OutputStream out = null;
		final java.lang.Integer __method = (java.lang.Integer) HardwarePOA._methods.get($method);
		if (__method == null) {
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		switch (__method.intValue()) {
		case 0: // com/linkare/rec/acquisition/Hardware/getHardwareInfo
		{
			com.linkare.rec.data.metadata.HardwareInfo $result = null;
			$result = getHardwareInfo();
			out = $rh.createReply();
			com.linkare.rec.data.metadata.HardwareInfoHelper.write(out, $result);
			break;
		}
		case 1: // com/linkare/rec/acquisition/Hardware/getHardwareState
		{
			com.linkare.rec.acquisition.HardwareState $result = null;
			$result = getHardwareState();
			out = $rh.createReply();
			com.linkare.rec.acquisition.HardwareStateHelper.write(out, $result);
			break;
		}

		case 2: // com/linkare/rec/acquisition/Hardware/registerDataClient
		{
			try {
				final com.linkare.rec.acquisition.DataClient data_client = com.linkare.rec.acquisition.DataClientHelper
						.read(in);
				registerDataClient(data_client);
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.NotAuthorized $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
			}
			break;
		}

		case 3: // com/linkare/rec/acquisition/Hardware/getDataClient
		{
			com.linkare.rec.acquisition.DataClient $result = null;
			$result = getDataClient();
			out = $rh.createReply();
			com.linkare.rec.acquisition.VTDataClientHelper.write(out, $result);
			break;
		}

		case 4: // com/linkare/rec/acquisition/Hardware/configure
		{
			try {
				final com.linkare.rec.data.config.HardwareAcquisitionConfig config = com.linkare.rec.data.config.HardwareAcquisitionConfigHelper
						.read(in);
				configure(config);
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (final com.linkare.rec.acquisition.WrongConfigurationException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.WrongConfigurationExceptionHelper.write(out, $ex);
			}
			break;
		}

		case 5: // com/linkare/rec/acquisition/Hardware/start
		{
			try {
				final com.linkare.rec.acquisition.DataReceiver receiver = com.linkare.rec.acquisition.DataReceiverHelper
						.read(in);
				com.linkare.rec.acquisition.DataProducer $result = null;
				$result = start(receiver);
				out = $rh.createReply();
				com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			}
			break;
		}

		case 6: // com/linkare/rec/acquisition/Hardware/startOutput
		{
			try {
				final com.linkare.rec.acquisition.DataReceiver receiver = com.linkare.rec.acquisition.DataReceiverHelper
						.read(in);
				final com.linkare.rec.acquisition.DataProducer data_source = com.linkare.rec.acquisition.DataProducerHelper
						.read(in);
				com.linkare.rec.acquisition.DataProducer $result = null;
				$result = startOutput(receiver, data_source);
				out = $rh.createReply();
				com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			}
			break;
		}

		case 7: // com/linkare/rec/acquisition/Hardware/stop
		{
			try {
				stop();
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			}
			break;
		}

		case 8: // com/linkare/rec/acquisition/Hardware/reset
		{
			try {
				reset();
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			}
			break;
		}

		case 9: // com/linkare/rec/acquisition/Hardware/getDataProducer
		{
			try {
				com.linkare.rec.acquisition.DataProducer $result = null;
				$result = getDataProducer();
				out = $rh.createReply();
				com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.IncorrectStateException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
			} catch (final com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			}
			break;
		}

		default:
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		return out;
	} // _invoke

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/acquisition/Hardware:1.0" };

	@Override
	public String[] _all_interfaces(final org.omg.PortableServer.POA poa, final byte[] objectId) {
		return HardwarePOA.__ids.clone();
	}

	public Hardware _this() {
		return HardwareHelper.narrow(super._this_object());
	}

	public Hardware _this(final org.omg.CORBA.ORB orb) {
		return HardwareHelper.narrow(super._this_object(orb));
	}

} // class HardwarePOA
