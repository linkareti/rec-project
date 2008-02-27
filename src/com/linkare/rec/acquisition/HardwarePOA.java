package com.linkare.rec.acquisition;
@SuppressWarnings("unchecked")
public abstract class HardwarePOA extends org.omg.PortableServer.Servant
implements com.linkare.rec.acquisition.HardwareOperations, org.omg.CORBA.portable.InvokeHandler
{

	// Constructors

	private static java.util.Hashtable _methods = new java.util.Hashtable();
	static
	{
		_methods.put("getHardwareInfo", new java.lang.Integer(0));
		_methods.put("getHardwareState", new java.lang.Integer(1));
		_methods.put("registerDataClient", new java.lang.Integer(2));
		_methods.put("getDataClient", new java.lang.Integer(3));
		_methods.put("configure", new java.lang.Integer(4));
		_methods.put("start", new java.lang.Integer(5));
		_methods.put("startOutput", new java.lang.Integer(6));
		_methods.put("stop", new java.lang.Integer(7));
		_methods.put("reset", new java.lang.Integer(8));
		_methods.put("getDataProducer", new java.lang.Integer(9));
	}

	public org.omg.CORBA.portable.OutputStream _invoke(String $method,
	org.omg.CORBA.portable.InputStream in,
	org.omg.CORBA.portable.ResponseHandler $rh)
	{
		org.omg.CORBA.portable.OutputStream out = null;
		java.lang.Integer __method = (java.lang.Integer)_methods.get($method);
		if (__method == null)
			throw new org.omg.CORBA.BAD_OPERATION(0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

		switch (__method.intValue())
		{
			case 0:  // com/linkare/rec/acquisition/Hardware/getHardwareInfo
			{
				com.linkare.rec.data.metadata.HardwareInfo $result = null;
				$result = this.getHardwareInfo();
				out = $rh.createReply();
				com.linkare.rec.data.metadata.HardwareInfoHelper.write(out, $result);
				break;
			}
			case 1:  // com/linkare/rec/acquisition/Hardware/getHardwareState
			{
				com.linkare.rec.acquisition.HardwareState $result = null;
				$result = this.getHardwareState();
				out = $rh.createReply();
				com.linkare.rec.acquisition.HardwareStateHelper.write(out, $result);
				break;
			}

			case 2:  // com/linkare/rec/acquisition/Hardware/registerDataClient
			{
				try
				{
					com.linkare.rec.acquisition.DataClient data_client = com.linkare.rec.acquisition.DataClientHelper.read(in);
					this.registerDataClient(data_client);
					out = $rh.createReply();
				} catch (com.linkare.rec.acquisition.NotAuthorized $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.NotAuthorizedHelper.write(out, $ex);
				}
				break;
			}

			case 3:  // com/linkare/rec/acquisition/Hardware/getDataClient
			{
				com.linkare.rec.acquisition.DataClient $result = null;
				$result = this.getDataClient();
				out = $rh.createReply();
				com.linkare.rec.acquisition.VTDataClientHelper.write(out, $result);
				break;
			}

			case 4:  // com/linkare/rec/acquisition/Hardware/configure
			{
				try
				{
					com.linkare.rec.data.config.HardwareAcquisitionConfig config = com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.read(in);
					this.configure(config);
					out = $rh.createReply();
				} catch (com.linkare.rec.acquisition.IncorrectStateException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
				} catch (com.linkare.rec.acquisition.WrongConfigurationException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.WrongConfigurationExceptionHelper.write(out, $ex);
				}
				break;
			}

			case 5:  // com/linkare/rec/acquisition/Hardware/start
			{
				try
				{
					com.linkare.rec.acquisition.DataReceiver receiver = com.linkare.rec.acquisition.DataReceiverHelper.read(in);
					com.linkare.rec.acquisition.DataProducer $result = null;
					$result = this.start(receiver);
					out = $rh.createReply();
					com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
				} catch (com.linkare.rec.acquisition.IncorrectStateException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
				}
				break;
			}

			case 6:  // com/linkare/rec/acquisition/Hardware/startOutput
			{
				try
				{
					com.linkare.rec.acquisition.DataReceiver receiver = com.linkare.rec.acquisition.DataReceiverHelper.read(in);
					com.linkare.rec.acquisition.DataProducer data_source = com.linkare.rec.acquisition.DataProducerHelper.read(in);
					com.linkare.rec.acquisition.DataProducer $result = null;
					$result = this.startOutput(receiver, data_source);
					out = $rh.createReply();
					com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
				} catch (com.linkare.rec.acquisition.IncorrectStateException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
				}
				break;
			}

			case 7:  // com/linkare/rec/acquisition/Hardware/stop
			{
				try
				{
					this.stop();
					out = $rh.createReply();
				} catch (com.linkare.rec.acquisition.IncorrectStateException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
				}
				break;
			}

			case 8:  // com/linkare/rec/acquisition/Hardware/reset
			{
				try
				{
					this.reset();
					out = $rh.createReply();
				} catch (com.linkare.rec.acquisition.IncorrectStateException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
				}
				break;
			}

			case 9:  // com/linkare/rec/acquisition/Hardware/getDataProducer
			{
				try
				{
					com.linkare.rec.acquisition.DataProducer $result = null;
					$result = this.getDataProducer();
					out = $rh.createReply();
					com.linkare.rec.acquisition.DataProducerHelper.write(out, $result);
				} catch (com.linkare.rec.acquisition.IncorrectStateException $ex)
				{
					out = $rh.createExceptionReply();
					com.linkare.rec.acquisition.IncorrectStateExceptionHelper.write(out, $ex);
				} catch (com.linkare.rec.acquisition.NotAvailableException $ex)
				{
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
	private static String[] __ids =
	{
		"IDL:com/linkare/rec/acquisition/Hardware:1.0"};

		public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] objectId)
		{
			return (String[])__ids.clone();
		}

		public Hardware _this()
		{
			return HardwareHelper.narrow(
			super._this_object());
		}

		public Hardware _this(org.omg.CORBA.ORB orb)
		{
			return HardwareHelper.narrow(
			super._this_object(orb));
		}


} // class HardwarePOA
