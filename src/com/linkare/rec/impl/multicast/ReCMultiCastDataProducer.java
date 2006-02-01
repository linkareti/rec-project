/*
 * ReCMultiCastDataProducer.java
 *
 * Created on 5 de Novembro de 2002, 12:54
 */

package com.linkare.rec.impl.multicast;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerHelper;
import com.linkare.rec.acquisition.DataProducerOperations;
import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.DataReceiverHelper;
import com.linkare.rec.acquisition.DataReceiverOperations;
import com.linkare.rec.acquisition.MaxPacketNumUnknown;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.acquisition.NotAuthorized;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.multicast.security.DefaultResource;
import com.linkare.rec.impl.utils.DataCollector;
import com.linkare.rec.impl.utils.Deactivatable;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.utils.ObjectID;
import com.linkare.rec.impl.wrappers.DataProducerWrapper;
import com.linkare.rec.impl.exceptions.NotAnAvailableSamplesPacketExceptionConstants;
import java.io.Serializable;
import java.util.logging.Level;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.Servant;
import java.io.*;



/**
 *
 * @author  jp
 */
public class ReCMultiCastDataProducer extends DataCollector implements DataProducerOperations, Deactivatable, Serializable
{
    
    private transient DataProducerWrapper remoteDataProducer=null;
    private transient DataProducer _this=null;
    private transient DataReceiverQueue dataReceiversQueue=null;
    private transient DataReceiverQueueAdapter dataReceiverQueueAdapter=null;
    private HardwareAcquisitionConfig cachedAcqHeader=null;
    private String cachedDataProducerName=null;
    private String oid=null;
    private transient ReCMultiCastDataProducerListener reCMultiCastDataProducerListener=null;
    private IResource resource=null;
    private transient ReCMultiCastDataProducerDataReceiver dataReceiver=null;
    
    private boolean alreadySerialized = false;
    private int maximum_receivers=1;
    
    private void writeObject(ObjectOutputStream stream) throws IOException
    {
        alreadySerialized = true;
        stream.defaultWriteObject();
    }
    
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        stream.defaultReadObject();
        initInternalQueue();
    }
    
    public ReCMultiCastDataProducer(IResource resource,int maximum_receivers,String fileName)
    {
        super();
        this.fileName=fileName;
        
        this.resource=resource;
        this.maximum_receivers=maximum_receivers;
        initInternalQueue();
        dataReceiver=new ReCMultiCastDataProducerDataReceiver();
    }
    
    private void initInternalQueue()
    {
        dataReceiverQueueAdapter=new DataReceiverQueueAdapter();
        dataReceiversQueue=new DataReceiverQueue(dataReceiverQueueAdapter,maximum_receivers);
    }
    
    //just for serialize
    public ReCMultiCastDataProducer()
    {
        //do not call super()  - it should release the acquisition Thread...
        //but if we were serialized there's no need in doing it...
    }
    
    public DataReceiver getDataReceiver()
    {
        return dataReceiver._this();
    }
    public void setRemoteDataProducer(DataProducer remoteDataProducer)
    {
        this.remoteDataProducer=new DataProducerWrapper(remoteDataProducer);
        setPriority(Thread.MAX_PRIORITY-1);
        try
        {
            setLargestPacketKnown(getRemoteDataProducer().getMaxPacketNum());
        }catch(Exception e)
        {
            logThrowable("Error getting Remote Data Producer Max Packet Num",e);
        }
        try
        {
            setRemoteDataProducerState(getRemoteDataProducer().getDataProducerState());
        }catch(Exception e)
        {
            logThrowable("Error getting Remote Data Producer Max Packet Num",e);
        }
        //first caching of dataproducername
        getDataProducerName();
    }
    
    public void setOID(String oid)
    {
        this.oid=oid;
        java.util.Map props=resource.getProperties();
        props.put(IResource.PROPKEY_DATAPRODUCER_ID, oid);
        ((DefaultResource)resource).setProperties(props);
    }
    
    
    public DataProducer _this()
    {
        if(_this!=null)
            return _this;
        
        if(oid==null)
            setOID(getFileName());
        
        try
        {
            log(Level.FINEST,"Trying to create DataProducer CORBA Object... " + getOID());
            Servant servant=ORBBean.getORBBean().registerDataProducerPOAServant(DataProducer.class,this,ORBBean.StrToOid(getOID()));
            log(Level.FINEST,"Registered with the POA... " + getOID());
            return (_this=DataProducerHelper.narrow(ORBBean.getORBBean().getDataProducerPOA().servant_to_reference(servant)));
        }catch(Exception e)
        {
            logThrowable("Couldn't register this DataProducer with the ORB!",e);
        }
        return _this;
    }
    
    public HardwareAcquisitionConfig getAcquisitionHeader()
    throws NotAvailableException
    {
        if(cachedAcqHeader==null && remoteDataProducer!=null)
        {
            try
            {
                cachedAcqHeader=remoteDataProducer.getAcquisitionHeader();
            }
            catch(NotAvailableException e)
            {
                logThrowable("Couldn't get Acquisition Header! - Rethrowing exception...",e);
                throw e;
            }
            catch(Exception e)
            {
                logThrowable("Other reason - Couldn't get Acquisition Header! Throwing not available exception...",e);
                throw new NotAvailableException();
            }
        }
        
        
        return cachedAcqHeader;
    }
    
    
    
    /** Operation getDataProducerName
     */
    public String getDataProducerName()
    {
        if(cachedDataProducerName==null && remoteDataProducer!=null)
        {
            try
            {
                cachedDataProducerName=remoteDataProducer.getDataProducerName();
            }
            catch(Exception e)
            {
                logThrowable("Other reason - Couldn't get DataProducerName!",e);
            }
        }
        return cachedDataProducerName;
    }
    
    
    
    
    public String getOID()
    {
        return this.oid;
    }
    
    public POA getPOA()
    {
        try
        {
            return ORBBean.getORBBean().getDataProducerPOA();
        }catch(Exception e)
        {
            //e.printStackTrace();
        }
        return null;
    }
    
    public boolean isDeactivationPossible()
    {
        if(getDataProducerState().equals(DataProducerState.DP_ENDED)
        || getDataProducerState().equals(DataProducerState.DP_ERROR)
        || getDataProducerState().equals(DataProducerState.DP_STOPED))
        {
            log(Level.FINE,getOID()+" is now deactivatable!");
            log(Level.FINE,getOID() +" State is "+getDataProducerState() );
            return true;
        }
        else
        {
            log(Level.FINE,getOID() +" is not deactivatable! State is "+getDataProducerState() );
            
            return false;
        }
    }
    
    private String fileName=null;
    public String getFileName()
    {
        return fileName;
    }
    
    
    
    public void registerDataReceiver(DataReceiver data_receiver) throws MaximumClientsReached
    {
        log(Level.INFO,"Received request to register a new DataReceiver in ReCMultiCastDataProducer " + getOID() );
        try
        {            
            dataReceiversQueue.add(data_receiver,resource,getDataProducerState());            
        }
        catch(NotAuthorized e)
        {
            logThrowable("Couldn't register data receiver: " + data_receiver,e);
        }
        catch(MaximumClientsReached mcr)
        {
            logThrowable("Couldn't register data receiver: " + data_receiver,mcr);
            throw mcr;
        }
    }
    
    
    
    public void fireNewSamples()
    {
        dataReceiversQueue.newSamples(getMaxPacketNum());
    }
    
    public void fireStateChanged()
    {
        dataReceiversQueue.stateChanged(getDataProducerState());
    }
    
    
    
    /*Proxy Logging methods*/
    public void log(Level debugLevel, String message)
    {
        if(getReCMultiCastDataProducerListener() != null)
            getReCMultiCastDataProducerListener().log(debugLevel,"DataProducer "+getOID()+" - "+message);
    }
    
    public void logThrowable(String message, Throwable t)
    {
        if(getReCMultiCastDataProducerListener() != null)
            getReCMultiCastDataProducerListener().logThrowable("DataProducer "+getOID()+" - "+message,t);
    }
    
    public void fireOneDataReceiverGone()
    {
        if(getReCMultiCastDataProducerListener() != null)
            getReCMultiCastDataProducerListener().oneDataReceiverGone();
    }
    
    /** Getter for property reCMultiCastDataProducerListener.
     * @return Value of property reCMultiCastDataProducerListener.
     *
     */
    public ReCMultiCastDataProducerListener getReCMultiCastDataProducerListener()
    {
        return reCMultiCastDataProducerListener;
    }
    
    /** Setter for property reCMultiCastDataProducerListener.
     * @param reCMultiCastDataProducerListener New value of property reCMultiCastDataProducerListener.
     *
     */
    public void setReCMultiCastDataProducerListener(ReCMultiCastDataProducerListener reCMultiCastDataProducerListener)
    {
        this.reCMultiCastDataProducerListener = reCMultiCastDataProducerListener;
    }
    
    public DataProducerWrapper getRemoteDataProducer()
    {
        return remoteDataProducer;
    }
    
    /** Getter for property resource.
     * @return Value of property resource.
     *
     */
    public IResource getResource()
    {
        return resource;
    }
    
    public void shutdown()
    {
        setRemoteDataProducerState(DataProducerState.DP_ERROR);
        dataReceiversQueue.shutdown();
        shutdownThread();
    }
    
    public SamplesPacket[] getSamples(int packetIndexStart,int packetIndexEnd)
    throws NotAnAvailableSamplesPacketException
    {
        try
        {
            return getSamplesPacketSource().getSamplesPackets(packetIndexStart,packetIndexEnd);
        }catch(SamplesPacketReadException e)
        {
            logThrowable("Error getting samples packet "+e.getErrorPacketNumber() +" - Throwing NotAnAvailableSamplesPacketException...",e);
            throw new NotAnAvailableSamplesPacketException(NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE, e.getErrorPacketNumber());
        }
    }
    
    
    public int getMaxPacketNum()
    {
        int maxpacket = getSamplesPacketSource().getLargestNumPacket();
        return maxpacket;
    }
    
    public DataProducerState getDataProducerState()
    {        
        return getDataCollectorState().toDataProducerState();
    }
    
    public boolean alreadySerialized()
    {
        return alreadySerialized;
    }
    
    /*Inner class - DataReceiver implementation */
    private class ReCMultiCastDataProducerDataReceiver implements DataReceiverOperations
    {
        private transient DataReceiver _this=null;
        private transient ObjectID objectID=new ObjectID();
        
        public DataReceiver _this()
        {
            if(_this!=null)
                return _this;
            
            try
            {
                return (_this=DataReceiverHelper.narrow(ORBBean.getORBBean().getAutoIdRootPOA().servant_to_reference(ORBBean.getORBBean().registerAutoIdRootPOAServant(DataReceiver.class,this,objectID))));
            }catch(Exception e)
            {
                logThrowable("Couldn't register DataReceiver with the ORB!",e);
            }
            
            return _this;
        }
        
        public void newSamples(int maxpacketNum)
        {
            ReCMultiCastDataProducer.this.setLargestPacketKnown(maxpacketNum);
        }
        
        public void stateChanged(DataProducerState newState)
        {
            ReCMultiCastDataProducer.this.setRemoteDataProducerState(newState);
        }
        
        public void shutdown()
        {
            try
            {
                ORBBean.getORBBean().getAutoIdRootPOA().deactivate_object(objectID.getOid());
            }catch(Exception e)
            {
                logThrowable(getClass().getName()+" Error deactivating object " +objectID,e);
            }
        }
        
        public void clientsListChanged()
        {
            //BIG SILENT NOOP - Hardwares Won't call this method
        }
        
    }
    /*Inner class - DataReceiver implementation */
    
    /*Inner classs IDataReceiverQueueListener implementation*/
    public class DataReceiverQueueAdapter implements IDataReceiverQueueListener
    {
        
        public void log(Level debugLevel, String message)
        {
            ReCMultiCastDataProducer.this.log(debugLevel, message);
        }
        
        public void logThrowable(String message, Throwable t)
        {
            ReCMultiCastDataProducer.this.logThrowable(message,t);
        }
        
        public void oneDataReceiverForQueueIsGone()
        {
            ReCMultiCastDataProducer.this.fireOneDataReceiverGone();
        }
        
    }
    
    
    
}//ReCMultiCastDataProducer
