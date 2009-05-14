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
public abstract class RepositoryManagerPOA extends Servant implements RepositoryManagerOperations, InvokeHandler
{
    
    // Constructors
    
    private static Hashtable _methods = new Hashtable();
    static
    {
	_methods.put("getDataProducer", new java.lang.Integer(0));
	_methods.put("listDataProducers", new java.lang.Integer(1));
    }
    
    public OutputStream _invoke(String $method, InputStream in, ResponseHandler $rh)
    {
	OutputStream out = null;
	java.lang.Integer __method = (java.lang.Integer)_methods.get($method);
	if (__method == null)
	    throw new BAD_OPERATION(0, CompletionStatus.COMPLETED_MAYBE);
	
	switch (__method.intValue())
	{
	    case 0:  // com/linkare/rec/repository/RepositoryManager/getDataProducer
	    {
		UserInfo user = UserInfoHelper.read(in);
		String id = DataProducerIdHelper.read(in);
		DataProducer $result = null;
		$result = this.getDataProducer(user, id);
		out = $rh.createReply();
		DataProducerHelper.write(out, $result);
		break;
	    }
	    
	    case 1:  // com/linkare/rec/repository/RepositoryManager/listDataProducers
	    {
		UserInfo user = UserInfoHelper.read(in);
		HardwareAcquisitionConfigSearch[] search_params = VTHardwareAcquisitionConfigSearchListHelper.read(in);
		DataProducerConfig $result[] = null;
		$result = this.listDataProducers(user, search_params);
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
    private static String[] __ids =
    {
	"IDL:com/linkare/rec/repository/RepositoryManager:1.0"};
	
	public String[] _all_interfaces(POA poa, byte[] objectId)
	{
	    return (String[])__ids.clone();
	}
	
	public RepositoryManager _this()
	{
	    return RepositoryManagerHelper.narrow(
	    super._this_object());
	}
	
	public RepositoryManager _this(ORB orb)
	{
	    return RepositoryManagerHelper.narrow(
	    super._this_object(orb));
	}
	
	
} // class RepositoryManagerPOA
