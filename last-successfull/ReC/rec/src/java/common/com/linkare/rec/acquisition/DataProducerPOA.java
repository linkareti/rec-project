package com.linkare.rec.acquisition;

/**
 * com/linkare/rec/acquisition/DataProducerPOA.java . Generated by the
 * IDL-to-Java compiler (portable), version "3.1" from
 * I:/Projects/REC/IdlCompile/ReC7.idl Sexta-feira, 2 de Janeiro de 2004 15H12m
 * GMT
 */

@SuppressWarnings("unchecked")
// ok, not known at this time
public abstract class DataProducerPOA extends org.omg.PortableServer.Servant implements
		com.linkare.rec.acquisition.DataProducerOperations, org.omg.CORBA.portable.InvokeHandler {
	// Constructors

	private static java.util.Hashtable _methods = new java.util.Hashtable();
	static {
		DataProducerPOA._methods.put("getAcquisitionHeader", new java.lang.Integer(0));
		DataProducerPOA._methods.put("getSamples", new java.lang.Integer(1));
		DataProducerPOA._methods.put("getDataProducerName", new java.lang.Integer(2));
		DataProducerPOA._methods.put("getDataProducerState", new java.lang.Integer(3));
		DataProducerPOA._methods.put("getMaxPacketNum", new java.lang.Integer(4));
		DataProducerPOA._methods.put("registerDataReceiver", new java.lang.Integer(5));
	}

	@Override
	public org.omg.CORBA.portable.OutputStream _invoke(final String $method,
			final org.omg.CORBA.portable.InputStream in, final org.omg.CORBA.portable.ResponseHandler $rh) {
		org.omg.CORBA.portable.OutputStream out = null;
		final java.lang.Integer __method = (java.lang.Integer) DataProducerPOA._methods.get($method);
		if (__method == null) {
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		switch (__method.intValue()) {

		// ::com::linkare::rec::data::metadata::HardwareAcquisitionHeader
		// getAcquisitionHeader() raises (NotAvailableException);
		case 0: // com/linkare/rec/acquisition/DataProducer/getAcquisitionHeader
		{
			try {
				com.linkare.rec.data.config.HardwareAcquisitionConfig $result = null;
				$result = getAcquisitionHeader();
				out = $rh.createReply();
				com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.NotAvailableException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAvailableExceptionHelper.write(out, $ex);
			}
			break;
		}

			// and getSamples(0,getMaxPacketNum());
		case 1: // com/linkare/rec/acquisition/DataProducer/getSamples
		{
			try {
				final int num_packet_start = in.read_long();
				final int num_packet_end = in.read_long();
				com.linkare.rec.data.acquisition.SamplesPacket $result[] = null;
				$result = getSamples(num_packet_start, num_packet_end);
				out = $rh.createReply();
				com.linkare.rec.data.acquisition.SamplesPacketListHelper.write(out, $result);
			} catch (final com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.NotAnAvailableSamplesPacketExceptionHelper.write(out, $ex);
			}
			break;
		}

			// ::org::omg::CORBA::WStringValue getConfiguratorURL();
		case 2: // com/linkare/rec/acquisition/DataProducer/getDataProducerName
		{
			String $result = null;
			$result = getDataProducerName();
			out = $rh.createReply();
			org.omg.CORBA.WStringValueHelper.write(out, $result);
			break;
		}

			// Version 7.0 Change this... now we may get any of the states...
		case 3: // com/linkare/rec/acquisition/DataProducer/getDataProducerState
		{
			com.linkare.rec.acquisition.DataProducerState $result = null;
			$result = getDataProducerState();
			out = $rh.createReply();
			com.linkare.rec.acquisition.DataProducerStateHelper.write(out, $result);
			break;
		}

		case 4: // com/linkare/rec/acquisition/DataProducer/getMaxPacketNum
		{
			int $result = 0;
			$result = getMaxPacketNum();
			out = $rh.createReply();
			out.write_long($result);
			break;
		}

		case 5: // com/linkare/rec/acquisition/DataProducer/registerDataReceiver
		{
			try {
				final com.linkare.rec.acquisition.DataReceiver data_receiver = com.linkare.rec.acquisition.DataReceiverHelper
						.read(in);
				registerDataReceiver(data_receiver);
				out = $rh.createReply();
			} catch (final com.linkare.rec.acquisition.MaximumClientsReached $ex) {
				out = $rh.createExceptionReply();
				com.linkare.rec.acquisition.MaximumClientsReachedHelper.write(out, $ex);
			}
			break;
		}

		default:
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
		}

		return out;
	} // _invoke

	// Type-specific CORBA::Object operations
	private static String[] __ids = { "IDL:com/linkare/rec/acquisition/DataProducer:1.0" };

	@Override
	public String[] _all_interfaces(final org.omg.PortableServer.POA poa, final byte[] objectId) {
		return DataProducerPOA.__ids.clone();
	}

	public DataProducer _this() {
		return DataProducerHelper.narrow(super._this_object());
	}

	public DataProducer _this(final org.omg.CORBA.ORB orb) {
		return DataProducerHelper.narrow(super._this_object(orb));
	}

} // class DataProducerPOA
