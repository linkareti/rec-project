/*
 * ORBRegisterBean.java
 *
 * Created on 20 de Maio de 2002, 12:53
 */

package com.linkare.rec.impl.utils;

import java.applet.Applet;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Properties;
import org.omg.BiDirPolicy.BIDIRECTIONAL_POLICY_TYPE;
import org.omg.BiDirPolicy.BOTH;

import org.omg.CORBA.*;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.*;

/**
 *
 * @author  jp
 * @version
 */
@SuppressWarnings("unchecked") //TODO...
public class ORBBean
{    
    public static ORBBean getORBBean()
    {
        if(this_object==null)
        {
            this_object=new ORBBean();
        }
        
        return this_object;
    }
    
    public static ORBBean getORBBean(String[] args)
    {
        if(this_object==null)
            this_object=new ORBBean(args);
        
        return this_object;
    }
    
    public static ORBBean getORBBean(Applet applet)
    {
        if(this_object==null)
            this_object=new ORBBean(applet);
        
        return this_object;
    }
    
    
    /*Singleton Pattern*/
    private static ORBBean this_object=null;
    /** Holds value of property app_args. */
    private String[] app_args;
    
    /** Holds value of property applet. */
    private Applet applet;
    
    private org.omg.CORBA.ORB the_orb=null;
    
    private POA rootPOA=null;
    
    private POA dataProducerPOA=null;
    
    private ORBBean(String[] args)
    {
        this.app_args=args;
        initORB();
    }
    
    private ORBBean()
    {
        initORB();
    }
    
    private ORBBean(Applet applet)
    {
        this.applet=applet;
        initORB();
    }
    
    private java.lang.Object orb_synch=new java.lang.Object();
    
    private synchronized void initORB()
    {
        synchronized(orb_synch)
        {
            if(the_orb!=null) return;
            
            if(this.applet!=null)
                the_orb=ORB.init(this.applet, null);                
            else if(this.app_args!=null)
            {
                the_orb=ORB.init(this.app_args, null);                
            }
            else
            {
                Properties props=System.getProperties();
                the_orb=ORB.init(new String[]{},props);
            }
            
            ORBRunner runner=new ORBRunner(the_orb);
            try
            {
                synchronized(runner)
                {
                    runner.start();
                    //runner.wait();
                    //getNameService();
                    runner.wait(1000);
                }
            }catch(Exception e)
            {
                
            }
        }        
        
    }
    
    private class ORBRunner extends Thread
    {
        org.omg.CORBA.ORB the_orb=null;
        public ORBRunner(org.omg.CORBA.ORB the_orb)
        {
            this.the_orb=the_orb;
            setDaemon(true);
            setPriority(Thread.MAX_PRIORITY);
        }
        public void run()
        {
            synchronized(this)
            {
                this.notifyAll();
            }            
            the_orb.run();
        }
    }
    
    public org.omg.CORBA.ORB getORB()
    {
        synchronized(orb_synch)
        {
            return the_orb;
        }
    }
    
    
    Policy bidirpol=null;
    private Policy getBidirPolicy()
    {
        synchronized(orb_synch)
        {
            
            if(bidirpol==null)
            {
                try
                {
                    
                    
                    Any any=getORB().create_any();
                    
                    any.insert_ushort( BOTH.value );
                    
                    bidirpol=getORB().create_policy(BIDIRECTIONAL_POLICY_TYPE.value,any);
                    
                    PolicyManager opm = ( PolicyManager ) getORB().resolve_initial_references( "ORBPolicyManager" );
                    opm.set_policy_overrides( new Policy[]
                    {bidirpol}, SetOverrideType.ADD_OVERRIDE );
                    
                }catch(Exception e)
                {
                    //System.out.println("Couldn't init Bidir Policy...");
                    //e.printStackTrace();
                }
            }
            
            return bidirpol;
        }
    }
    
    
    public synchronized POA getDataProducerPOA() throws Exception
    {
        synchronized(orb_synch)
        {
            try
            {
                if(dataProducerPOA==null)
                {
                    
                    POA rootPOA=POAHelper.narrow(the_orb.resolve_initial_references("RootPOA"));
                    
                    rootPOA.the_POAManager().activate();
                    
                    Policy bidirpol=getBidirPolicy();
                    
                    Policy[] poa_policies=null;
                    
                    if(bidirpol==null)
                        poa_policies=new Policy[]
                        {
                            rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
                            rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                            rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
                            rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
                            rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_SERVANT_MANAGER),
                            rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                            rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL),
                        };
                    else
                        poa_policies=new Policy[]
                        {
                            bidirpol,
                            rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
                            rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                            rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
                            rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
                            rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_SERVANT_MANAGER),
                            rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                            rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL)
                        };
                        
                        
                        dataProducerPOA=rootPOA.create_POA("DataProducerPOA",null,poa_policies);
                        
                        dataProducerPOA.set_servant_manager(new DataProducerActivator());
                        
                        dataProducerPOA.the_POAManager().activate();
                }
                
                return dataProducerPOA;
            }
            catch(Exception e)
            {
                throw e;
            }
        }
    }
    
    
    private POA myRootPOA=null;
    
    public POA getRootPOA() throws Exception
    {
        synchronized(orb_synch)
        {
            try
            {
                if(myRootPOA==null)
                {
                    
                    POA rootPOA=POAHelper.narrow(the_orb.resolve_initial_references("RootPOA"));
                    rootPOA.the_POAManager().activate();
                    
                    Policy bidirpol=getBidirPolicy();
                    Policy[] poa_policies=null;
                    
                    if(bidirpol==null)
                        poa_policies=new Policy[]
                        {
                            rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
                            rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                            rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
                            rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
                            rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
                            rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                            rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL),
                        };
                    else
                        poa_policies=new Policy[]
                        {
                            bidirpol,
                            rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.USER_ID),
                            rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                            rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.NO_IMPLICIT_ACTIVATION),
                            rootPOA.create_lifespan_policy(LifespanPolicyValue.PERSISTENT),
                            rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
                            rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                            rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL),
                        };
                        
                        myRootPOA=rootPOA.create_POA("MyRootPOA",null,poa_policies);
                        
                        myRootPOA.the_POAManager().activate();
                        
                }
                return myRootPOA;
            }
            catch(Exception e)
            {
                throw e;
            }
        }
    }
    
    
    private POA myAutoIdRootPOA=null;
    
    public POA getAutoIdRootPOA() throws Exception
    {
        synchronized(orb_synch)
        {
            
            try
            {
                if(myAutoIdRootPOA==null)
                {
                    
                    
                    POA rootPOA=POAHelper.narrow(the_orb.resolve_initial_references("RootPOA"));
                    rootPOA.the_POAManager().activate();
                    
                    Policy bidirpol=getBidirPolicy();
                    Policy[] poa_policies=null;
                    
                    if(bidirpol==null)
                        poa_policies=new Policy[]
                        {
                            rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID),
                            rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                            rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION),
                            rootPOA.create_lifespan_policy(LifespanPolicyValue.TRANSIENT),
                            rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
                            rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                            rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL),
                        };
                    else
                        poa_policies=new Policy[]
                        {
                            bidirpol,
                            rootPOA.create_id_assignment_policy(IdAssignmentPolicyValue.SYSTEM_ID),
                            rootPOA.create_id_uniqueness_policy(IdUniquenessPolicyValue.UNIQUE_ID),
                            rootPOA.create_implicit_activation_policy(ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION),
                            rootPOA.create_lifespan_policy(LifespanPolicyValue.TRANSIENT),
                            rootPOA.create_request_processing_policy(RequestProcessingPolicyValue.USE_ACTIVE_OBJECT_MAP_ONLY),
                            rootPOA.create_servant_retention_policy(ServantRetentionPolicyValue.RETAIN),
                            rootPOA.create_thread_policy(ThreadPolicyValue.ORB_CTRL_MODEL)
                        };
                        
                        myAutoIdRootPOA=rootPOA.create_POA("MyAutoIdRootPOA",null,poa_policies);
                        
                        myAutoIdRootPOA.the_POAManager().activate();
                        
                        
                }
                return myAutoIdRootPOA;
            }catch(Exception e)
            {
                throw e;
            }
        }
    }
    
    
    
    
    
    public void deactivateServant(Servant servant)
    {
        synchronized(orb_synch)
        {
            try
            {
                getAutoIdRootPOA().deactivate_object(getAutoIdRootPOA().servant_to_id(servant));
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    
    public void deactivateServant(byte[] oid)
    {
        synchronized(orb_synch)
        {
            try
            {
                getAutoIdRootPOA().deactivate_object(oid);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public Servant registerDataProducerPOAServant(Class remoteInterface, java.lang.Object servantDelegate, byte[] oid)  throws Exception
    {
        synchronized(orb_synch)
        {
            String delegateInterfaceName=remoteInterface.getName()+"Operations";
            Class delegateInterface=Class.forName(delegateInterfaceName,false,this.getClass().getClassLoader());
            
            String poaTieServantClassName=remoteInterface.getName()+"POATie";
            Class poaTieServantClass=Class.forName(poaTieServantClassName,false,this.getClass().getClassLoader());
            
            Constructor servantCtr=poaTieServantClass.getConstructor(new Class[]
            {delegateInterface,POA.class});
            
            Servant servant=(Servant)servantCtr.newInstance(new java.lang.Object[]
            {servantDelegate,getDataProducerPOA()});
            
            getDataProducerPOA().activate_object_with_id(oid,servant);
            
            return servant;
        }
    }
    
    public Servant registerRootPOAServant(Class remoteInterface, java.lang.Object servantDelegate, byte[] oid)  throws Exception
    {
        synchronized(orb_synch)
        {
            String delegateInterfaceName=remoteInterface.getName()+"Operations";
            Class delegateInterface=Class.forName(delegateInterfaceName,false,this.getClass().getClassLoader());
            
            String poaTieServantClassName=remoteInterface.getName()+"POATie";
            Class poaTieServantClass=Class.forName(poaTieServantClassName,false,this.getClass().getClassLoader());
            
            Constructor servantCtr=poaTieServantClass.getConstructor(new Class[]
            {delegateInterface,POA.class});
            
            Servant servant=(Servant)servantCtr.newInstance(new java.lang.Object[]
            {servantDelegate,getRootPOA()});
            
            getRootPOA().activate_object_with_id(oid,servant);
            
            return servant;
        }
    }
    
    public Servant registerAutoIdRootPOAServant(Class remoteInterface, java.lang.Object servantDelegate,ObjectID oidOut)  throws Exception
    {
        synchronized(orb_synch)
        {
            String delegateInterfaceName=remoteInterface.getName()+"Operations";
            Class delegateInterface=Class.forName(delegateInterfaceName,false,this.getClass().getClassLoader());
            
            String poaTieServantClassName=remoteInterface.getName()+"POATie";
            Class poaTieServantClass=Class.forName(poaTieServantClassName,false,this.getClass().getClassLoader());
            
            Constructor servantCtr=poaTieServantClass.getConstructor(new Class[]
            {delegateInterface,POA.class});
             
            Servant servant=(Servant)servantCtr.newInstance(new java.lang.Object[]
            {servantDelegate,getAutoIdRootPOA()});
            
            byte[] oid=getAutoIdRootPOA().activate_object(servant);
            if(oidOut!=null)
                oidOut.setOid(oid);
            
            return servant;
        }
    }
    
    
    
    
    public NamingContextExt createDirectory(java.lang.String FullName)  throws Exception
    {
        synchronized(orb_synch)
        {
            try
            {
                
                NamingContextExt nc=getNameService();
                NameComponent[] fullname=nc.to_name(FullName);
                NamingContextExt nc1 = null;
                
                for (int j=0; j<fullname.length; j++)
                {
                    try
                    {
                        nc1 = NamingContextExtHelper.narrow(nc.bind_new_context(new NameComponent[]
                        {fullname[j]}));
                        nc = nc1;
                    }
                    catch (Exception e)
                    {
                        nc1 = NamingContextExtHelper.narrow(nc.resolve(new NameComponent[]
                        {
                            fullname[j]
                        }
                        )
                        );
                        if (nc1 == null)
                            throw new Exception("The name "+fullname[j].id+" is not a directory...");
                        
                        nc = nc1;
                    }
                }
                
                return nc;
            }
            catch (Exception e)
            {
                throw e;
            }
        }
    }
    
    
    public void bindObject(java.lang.String FullName, org.omg.CORBA.Object obj) throws Exception
    {
        synchronized(orb_synch)
        {
            try
            {
                NamingContextExt nc=getNameService();
                NameComponent[] fullPath=nc.to_name(FullName);
                nc.rebind(fullPath,obj);
            }catch (Exception e)
            {
                throw e;
            }
        }
    }
    
    private NamingContextExt ns=null;
    public NamingContextExt getNameService() throws Exception
    {
        synchronized(orb_synch)
        {
            if(ns!=null)
            {
                try
                {
                    if(ns._non_existent())
                        ns=null;
                    else
                        return ns;
                }catch(Exception e)
                {
                    ns=null;
                }
            }
            
            try
            {
                try
                {
                    //in OpenORB I think I know how to reset NameService initial references
                    //((ORB)getORB()).addInitialReference("NameService",null);
                    //org.openorb.CORBA.ORB has been deprecated... in favour of org.openorb.core.ORB
                    
                    ((org.openorb.orb.core.ORB)getORB()).addInitialReference("NameService",null);
                    
                }catch(Exception ignored)
                {
                }                
                ns=NamingContextExtHelper.narrow(getORB().resolve_initial_references("NameService"));
                return ns;
            }catch(Exception e)
            {
                //System.out.println("Couldn't create name service...");
                e.printStackTrace();
                throw e;
            }
        }
    }
    
    public org.omg.CORBA.Object getRemoteObject(String FullName) throws Exception
    {
        org.omg.CORBA.Object out = null;
        //org.omg.CORBA.Object out=getNameService().resolve_str(FullName);
        //org.omg.CORBA.Object out = getRemoteObject(getNameService().to_name(FullName));
        try
        {
            out = getNameService().resolve_str(FullName);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return out;
    }
    
    public org.omg.CORBA.Object getRemoteObject(NameComponent[] fullname) throws Exception
    {
        synchronized(the_orb)
        {
            String Fullname=getNameService().to_string(fullname);
            org.omg.CORBA.Object out=getNameService().resolve(fullname);
            return out;
        }
    }
    
    public void removeNSRegistration(NameComponent[] fullname)
    {
        synchronized(the_orb)
        {
            try
            {
                String Fullname=getNameService().to_string(fullname);
                getNameService().unbind(fullname);
                
            }catch(Exception ignored)
            {}
        }
    }
    
    public org.omg.CORBA.Object getRemoteCORBALocObject(String corbalocURL) throws Exception
    {
        if(!corbalocURL.startsWith("corbaloc"))
            return getRemoteObject(corbalocURL);
        
        
        org.omg.CORBA.Object obj=null;
        try
        {
            obj=getORB().string_to_object(corbalocURL);
        }catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
        
        return obj;
    }
    
    
    public String bindObjectToCorbalocService(String name, org.omg.CORBA.Object obj)
    {
        org.omg.CORBA.ORB orb=getORB();
        String result = null;
        try
        {
            org.omg.CORBA.Object clsobj = orb.resolve_initial_references( "CorbalocService" );
            
            
            /* Thia was for openorb 1.3.1
             *
             *
             Class cls_clz = Thread.currentThread().getContextClassLoader().loadClass(
            "org.openorb.corbaloc.CorbalocService" );
            Class clsstub_clz = Thread.currentThread().getContextClassLoader().loadClass(
            "org.openorb.corbaloc._CorbalocServiceStub" );
             
             */
            
            //And now for Openorb 1.4.0
            Class cls_clz = Thread.currentThread().getContextClassLoader().loadClass(
            "org.openorb.orb.corbaloc.CorbalocService" );
            Class clsstub_clz = Thread.currentThread().getContextClassLoader().loadClass(
            "org.openorb.orb.corbaloc._CorbalocServiceStub" );
            
            
            /* Thia was for openorb 1.3.1
             
             if ( clsobj != null && ( clsobj.getClass().isAssignableFrom( cls_clz )
            || clsobj._is_a( "IDL:openorb.org/corbaloc/CorbalocService:1.1" ) ) )
             */
            
            //And now for Openorb 1.4.0
            if ( clsobj != null && ( clsobj.getClass().isAssignableFrom( cls_clz )
            || clsobj._is_a( "IDL:orb.openorb.org/corbaloc/CorbalocService:1.1" ) ) )
            {
                // create an instance of _CorbalocServiceStub (default constructor)
                java.lang.Object clsstub_obj = getStubInstance( clsstub_clz, clsobj );
                
                // get the put method
                Method put = clsstub_obj.getClass().getMethod(
                "put", new Class[]
                { String.class, org.omg.CORBA.Object.class } );
                
                // call the _get_delegate() method on the stub
                java.lang.Object deleg = ( ( ObjectImpl ) clsobj )._get_delegate();
                
                try
                {
                    // call the put operation on the CorbalocService
                    put.invoke( clsstub_obj, new java.lang.Object[]
                    { name, obj } );
                    
                    // invocation was successful, get several methods and classes
                    
                    
                    /* This was for openorb 1.3.1
                     
                     
                     Class orgOpenorbOrbNetAddressClz =
                    Thread.currentThread().getContextClassLoader().loadClass(
                    "org.openorb.net.Address" );
                     */
                    
                    
                    Class orgOpenorbOrbNetAddressClz =
                    Thread.currentThread().getContextClassLoader().loadClass(
                    "org.openorb.orb.net.Address" );
                    
                    /* This was for openorb 1.3.1
                     *
                     *
                    Class orgOpenorbOrbCoreDelegateClz =
                    Thread.currentThread().getContextClassLoader().loadClass(
                    "org.openorb.CORBA.Delegate" );
                     */
                    
                    // And now for Openorb 1.4.0
                    Class orgOpenorbOrbCoreDelegateClz =
                    Thread.currentThread().getContextClassLoader().loadClass(
                    "org.openorb.orb.core.Delegate" );
                    
                    Method getAddresses =
                    orgOpenorbOrbCoreDelegateClz.getMethod(
                    "getAddresses", new java.lang.Class[]
                    {org.omg.CORBA.Object.class }
                    );
                    
                    Method getProtocol =
                    orgOpenorbOrbNetAddressClz.getMethod(
                    "getProtocol", new java.lang.Class[]
                    {} );
                    Method getEndpointString =
                    orgOpenorbOrbNetAddressClz.getMethod(
                    "getEndpointString", new java.lang.Class[]
                    {} );
                    
                    java.lang.Object addrs = getAddresses.invoke( deleg, new java.lang.Object[]
                    { obj } );
                    
                    String endpoint = null;
                    if ( addrs != null )
                    {
                        int len = Array.getLength( addrs );
                        for ( int i = 0; i < len; i++ )
                        {
                            java.lang.Object addr = Array.get( addrs, i );
                            String prot = ( String ) getProtocol.invoke( addr, new java.lang.Object[]
                            {} );
                            if ( prot.equals( "iiop" ) )
                            {
                                endpoint = ( String ) getEndpointString.invoke( addr,
                                new java.lang.Object[]
                                {} );
                            }
                        }
                        if ( endpoint == null )
                        {
                            endpoint = ( String ) getEndpointString.invoke( Array.get( addrs, 0 ),
                            new java.lang.Object[]
                            {} );
                        }
                        result = "corbaloc:" + endpoint + "/" + name;
                    }
                }
                catch ( Exception ex )
                {
                    // do nothing, return false
                }
            }
        }
        catch ( final Exception ex )
        {
            // do nothing, return false
        }
        return result;
    }
    
    private java.lang.Object getStubInstance(Class clz, org.omg.CORBA.Object obj)
    {
        java.lang.Object result = null;
        try
        {
            try
            {
                // JDK 1.4
                // create an instance of _NamingContextStub (default constructor)
                result = clz.newInstance();
                Method setDelegate = clz.getMethod(
                "_set_delegate", new java.lang.Class[]
                {
                    Delegate.class } );
                    setDelegate.invoke( result, new java.lang.Object[]
                    { ( (
                      ObjectImpl ) obj )._get_delegate() } );
            }
            catch ( InstantiationException ex )
            {
                // JDK 1.3
                // create an instance of _NamingContextStub (constructor takes Delegate)
                Constructor ctor = clz.getConstructor(
                new Class[]
                { Delegate.class } );
                result = ctor.newInstance( new java.lang.Object[]
                { ( (
                  ObjectImpl ) obj )._get_delegate() } );
            }
        }
        catch ( Exception ex )
        {
            ex.printStackTrace();
        }
        return result;
    }
    
    
    
    public static byte[] StrToOid(String strOid)
    {
        return strOid.getBytes();
    }
    
    
    public static String OidtoStr(byte[] Oid)
    {
        return new String(Oid);
    }
    
    public void killORB()
    {
        synchronized(orb_synch)
        {
            try
            {
                the_orb.shutdown(true);
            }catch(Throwable e)
            {
                //e.printStackTrace();
            }
            try
            {
                the_orb.destroy();
            }catch(Throwable e)
            {
                //e.printStackTrace();
            }
            the_orb=null;
        }
    }
}
