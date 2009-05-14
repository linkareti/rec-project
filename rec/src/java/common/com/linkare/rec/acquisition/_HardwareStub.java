package com.linkare.rec.acquisition;

public class _HardwareStub extends org.omg.CORBA.portable.ObjectImpl implements com.linkare.rec.acquisition.Hardware
{
    
    public com.linkare.rec.data.metadata.HardwareInfo getHardwareInfo()
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("getHardwareInfo", true);
	    $in = _invoke($out);
	    com.linkare.rec.data.metadata.HardwareInfo $result = com.linkare.rec.data.metadata.HardwareInfoHelper.read($in);
	    return $result;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    return getHardwareInfo(        );
	} finally
	{
	    _releaseReply($in);
	}
    } // getHardwareInfo
    
    public com.linkare.rec.acquisition.HardwareState getHardwareState()
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("getHardwareState", true);
	    $in = _invoke($out);
	    com.linkare.rec.acquisition.HardwareState $result = com.linkare.rec.acquisition.HardwareStateHelper.read($in);
	    return $result;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    return getHardwareState(        );
	} finally
	{
	    _releaseReply($in);
	}
    } // getHardwareState
    
    public void registerDataClient(com.linkare.rec.acquisition.DataClient data_client) throws com.linkare.rec.acquisition.NotAuthorized
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("registerDataClient", true);
	    com.linkare.rec.acquisition.DataClientHelper.write($out, data_client);
	    $in = _invoke($out);
	    return;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/NotAuthorized:1.0"))
		throw com.linkare.rec.acquisition.NotAuthorizedHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    registerDataClient(data_client        );
	} finally
	{
	    _releaseReply($in);
	}
    } // registerDataClient
    
    public com.linkare.rec.acquisition.DataClient getDataClient()
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("getDataClient", true);
	    $in = _invoke($out);
	    com.linkare.rec.acquisition.DataClient $result = com.linkare.rec.acquisition.VTDataClientHelper.read($in);
	    return $result;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    return getDataClient(        );
	} finally
	{
	    _releaseReply($in);
	}
    } // getDataClient
    
    public void configure(com.linkare.rec.data.config.HardwareAcquisitionConfig config) throws com.linkare.rec.acquisition.IncorrectStateException, com.linkare.rec.acquisition.WrongConfigurationException
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("configure", true);
	    com.linkare.rec.data.config.HardwareAcquisitionConfigHelper.write($out, config);
	    $in = _invoke($out);
	    return;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0"))
		throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
	    else if (_id.equals("IDL:com/linkare/rec/acquisition/WrongConfigurationException:1.0"))
		throw com.linkare.rec.acquisition.WrongConfigurationExceptionHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    configure(config);
	} finally
	{
	    _releaseReply($in);
	}
    } // configure
    
    public com.linkare.rec.acquisition.DataProducer start(com.linkare.rec.acquisition.DataReceiver receiver) throws com.linkare.rec.acquisition.IncorrectStateException
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("start", true);
	    com.linkare.rec.acquisition.DataReceiverHelper.write($out, receiver);
	    $in = _invoke($out);
	    com.linkare.rec.acquisition.DataProducer $result = com.linkare.rec.acquisition.DataProducerHelper.read($in);
	    return $result;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0"))
		throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    return start(receiver        );
	} finally
	{
	    _releaseReply($in);
	}
    } // start
    
    public com.linkare.rec.acquisition.DataProducer startOutput(com.linkare.rec.acquisition.DataReceiver receiver, com.linkare.rec.acquisition.DataProducer data_source) throws com.linkare.rec.acquisition.IncorrectStateException
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("startOutput", true);
	    com.linkare.rec.acquisition.DataReceiverHelper.write($out, receiver);
	    com.linkare.rec.acquisition.DataProducerHelper.write($out, data_source);
	    $in = _invoke($out);
	    com.linkare.rec.acquisition.DataProducer $result = com.linkare.rec.acquisition.DataProducerHelper.read($in);
	    return $result;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0"))
		throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    return startOutput(receiver, data_source        );
	} finally
	{
	    _releaseReply($in);
	}
    } // startOutput
    
    public void stop() throws com.linkare.rec.acquisition.IncorrectStateException
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("stop", true);
	    $in = _invoke($out);
	    return;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0"))
		throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    stop(        );
	} finally
	{
	    _releaseReply($in);
	}
    } // stop
    
    public void reset() throws com.linkare.rec.acquisition.IncorrectStateException
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("reset", true);
	    $in = _invoke($out);
	    return;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0"))
		throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    reset(        );
	} finally
	{
	    _releaseReply($in);
	}
    } // reset
    
    public com.linkare.rec.acquisition.DataProducer getDataProducer() throws com.linkare.rec.acquisition.IncorrectStateException, com.linkare.rec.acquisition.NotAvailableException
    {
	org.omg.CORBA.portable.InputStream $in = null;
	try
	{
	    org.omg.CORBA.portable.OutputStream $out = _request("getDataProducer", true);
	    $in = _invoke($out);
	    com.linkare.rec.acquisition.DataProducer $result = com.linkare.rec.acquisition.DataProducerHelper.read($in);
	    return $result;
	} catch (org.omg.CORBA.portable.ApplicationException $ex)
	{
	    $in = $ex.getInputStream();
	    String _id = $ex.getId();
	    if (_id.equals("IDL:com/linkare/rec/acquisition/IncorrectStateException:1.0"))
		throw com.linkare.rec.acquisition.IncorrectStateExceptionHelper.read($in);
	    else if (_id.equals("IDL:com/linkare/rec/acquisition/NotAvailableException:1.0"))
		throw com.linkare.rec.acquisition.NotAvailableExceptionHelper.read($in);
	    else
		throw new org.omg.CORBA.MARSHAL(_id);
	} catch (org.omg.CORBA.portable.RemarshalException $rm)
	{
	    return getDataProducer(        );
	} finally
	{
	    _releaseReply($in);
	}
    } // getDataProducer
    
    // Type-specific CORBA::Object operations
    private static String[] __ids =
    {"IDL:com/linkare/rec/acquisition/Hardware:1.0"};
    
    public String[] _ids()
    {
	return (String[])__ids.clone();
    }
    
    private void readObject(java.io.ObjectInputStream s) throws java.io.IOException
    {
	String str = s.readUTF();
	String[] args = null;
	java.util.Properties props = null;
	org.omg.CORBA.Object obj = org.omg.CORBA.ORB.init(args, props).string_to_object(str);
	org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate();
	_set_delegate(delegate);
    }
    
    private void writeObject(java.io.ObjectOutputStream s) throws java.io.IOException
    {
	String[] args = null;
	java.util.Properties props = null;
	String str = org.omg.CORBA.ORB.init(args, props).object_to_string(this);
	s.writeUTF(str);
    }
} // class _HardwareStub
