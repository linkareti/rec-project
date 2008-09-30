package com.linkare.rec.impl.driver;

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.event.EventListenerList;

import com.linkare.rec.acquisition.DataProducer;
import com.linkare.rec.acquisition.DataProducerHelper;
import com.linkare.rec.acquisition.DataProducerOperations;
import com.linkare.rec.acquisition.DataProducerState;
import com.linkare.rec.acquisition.DataReceiver;
import com.linkare.rec.acquisition.MaximumClientsReached;
import com.linkare.rec.acquisition.NotAnAvailableSamplesPacketException;
import com.linkare.rec.acquisition.NotAvailableException;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.data.SamplesPacketReadException;
import com.linkare.rec.impl.data.SamplesPacketSourceEvent;
import com.linkare.rec.impl.data.SamplesPacketSourceEventListener;
import com.linkare.rec.impl.data.SamplesSourceEvent;
import com.linkare.rec.impl.data.SamplesSourcePacketizer;
import com.linkare.rec.impl.events.DataProducerStateChangeEvent;
import com.linkare.rec.impl.exceptions.NotAnAvailableSamplesPacketExceptionConstants;
import com.linkare.rec.impl.exceptions.NotAvailableExceptionConstants;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;
import com.linkare.rec.impl.utils.ORBBean;
import com.linkare.rec.impl.wrappers.DataReceiverWrapper;



public class BaseDataProducer implements DataProducerOperations
{
    private DataProducerState dataProducerState=DataProducerState.DP_WAITING;
    private static String BASE_DATAPRODUCER_LOGGER="BaseDataProducer.Logger";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(BASE_DATAPRODUCER_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(BASE_DATAPRODUCER_LOGGER));
        }
    }
    
    //private transient BaseHardware hardware=null;
    private transient IDataSource dataSource=null;
    private transient DataReceiverWrapper dataReceiver=null;
    private transient DataProducer _this=null;
    public SamplesSourcePacketizer packetizer=null;
    public EventQueue eventQueueDataReceiver=null;
    //private helper methods
    public DataProducer _this()
    {
        if(_this!=null)
            return _this;
        
        try
        {
            return (_this=DataProducerHelper.narrow(ORBBean.getORBBean().getAutoIdRootPOA().servant_to_reference(ORBBean.getORBBean().registerAutoIdRootPOAServant(DataProducer.class,this,null))));
        }catch(Exception e)
        {
            LoggerUtil.logThrowable("Couldn't Register this BaseDataProducer with ORB",e,Logger.getLogger(BASE_DATAPRODUCER_LOGGER));
            return null;
        }
        
    }
    
    public void stopNow()
    {
        if(dataSource!=null)
            dataSource.stopNow();
        
        //informProducerIsStopedAsynch();
    }
    
    
    /** Creates a new instance of SimulationDataProducerImpl */
    public BaseDataProducer(IDataSource dataSource,DataReceiver dataReceiver)
    {
        
        eventQueueDataReceiver=new EventQueue(new DataProducerEventsDispatcher());
        
        this.dataSource=dataSource;
        
        packetizer=new SamplesSourcePacketizer(
        dataSource.getAcquisitionHeader().getSelectedFrequency(),
        dataSource.getPacketSize(),
        (int)Math.ceil((double)dataSource.getTotalSamples()/(double)dataSource.getPacketSize())
        );
        
        packetizer.addSamplesPacketSourceEventListener(
        new SamplesPacketSourceEventListener()
        {
            public void newSamplesPackets(SamplesPacketSourceEvent evt)
            {
                fireNewSamples(evt);
            }
        }
        );
        packetizer.setSamplesSource(dataSource);
        try
        {
            registerDataReceiver(dataReceiver);
        }catch(MaximumClientsReached e)
        {
            LoggerUtil.logThrowable("Unable to register DataReceiver with this BaseDataProducer",e,Logger.getLogger(BASE_DATAPRODUCER_LOGGER));
        }
        
        
        dataSource.addIDataSourceListener(
        new IDataSourceListener()
        {
            public void dataSourceWaiting()
            {
                dataSourceStateWaiting();
            }
            public void dataSourceStarted()
            {
                dataSourceStateStarted();
            }
            public void dataSourceEnded()
            {
                dataSourceStateEnded();
            }
            public void dataSourceStoped()
            {
                dataSourceStateStoped();
            }
            public void dataSourceError()
            {
                dataSourceStateError();
            }
            public void newSamples(SamplesSourceEvent evt)
            {}
        }
        );
        
    }
    
    
    public HardwareAcquisitionConfig getAcquisitionHeader()
    throws NotAvailableException
    {
        if(dataSource!=null)
            return dataSource.getAcquisitionHeader();
        
        throw new NotAvailableException(NotAvailableExceptionConstants.NO_ACQ_HEADER);
    }
    
    private class DataProducerEventsDispatcher implements EventQueueDispatcher
    {
        
        public void dispatchEvent(Object o)
        {
            if(o instanceof SamplesPacketSourceEvent)
            {
                int num_packet=((SamplesPacketSourceEvent)o).getPacketLargestIndex();
                if(dataReceiver!=null)
                {
                    try
                    {
                        Logger.getLogger(BASE_DATAPRODUCER_LOGGER).log(Level.INFO,"New data packet available : " + num_packet);
                        dataReceiver.newSamples(num_packet);
                    }
                    catch(Exception e)
                    {
                        LoggerUtil.logThrowable("Exception informing DataReceiver of new samples "+num_packet,e,Logger.getLogger(BASE_DATAPRODUCER_LOGGER));
                    }
                }
            }
            else if(o instanceof DataProducerStateChangeEvent)
            {
                if(dataReceiver!=null)
                {
                    DataProducerStateChangeEvent event=(DataProducerStateChangeEvent)o;
                    try
                    {
                        Logger.getLogger(BASE_DATAPRODUCER_LOGGER).log(Level.INFO,"Data Producer is:" + event.getDataProducerState());
                        dataReceiver.stateChanged(event.getDataProducerState());
                    }
                    catch(Exception e)
                    {
                        LoggerUtil.logThrowable("Exception informing DataReceiver of data Producer stoped!",e,Logger.getLogger(BASE_DATAPRODUCER_LOGGER));
                    }
                }
            }
        }
        
        public int getPriority()
        {
            return Thread.NORM_PRIORITY+1;
        }
        
    }
    
    
    
    /** Utility field used by event firing mechanism. */
    private EventListenerList listenerList =  null;
    
    private void fireNewSamples(SamplesPacketSourceEvent evt)
    {
        Logger.getLogger(BASE_DATAPRODUCER_LOGGER).log(Level.INFO,"New packet available : " + evt.getPacketLargestIndex());
        setDataProducerState(DataProducerState.DP_STARTED);
        eventQueueDataReceiver.addEvent(evt);
    }
    private void fireStateChanged()
    {
        Logger.getLogger(BASE_DATAPRODUCER_LOGGER).log(Level.INFO,"Producer is now :" + getDataProducerState());
        eventQueueDataReceiver.addEvent(new DataProducerStateChangeEvent(getDataProducerState()));
    }
    
    public SamplesPacket[] getSamples(int packetStartIndex,int packetEndIndex)
    throws NotAnAvailableSamplesPacketException
    {
        try
        {
            SamplesPacket[] packets=packetizer.getSamplesPackets(packetStartIndex, packetEndIndex);
            if(packetEndIndex==getMaxPacketNum())
                fireDataProducerIsEmpty(this);
            return packets;
        }catch(SamplesPacketReadException e)
        {
            throw new NotAnAvailableSamplesPacketException(NotAnAvailableSamplesPacketExceptionConstants.PACKET_NOT_FOUND_IN_CACHE,e.getErrorPacketNumber());
        }
    }
    
    public String getDataProducerName()
    {
        return dataSource.getName();
    }
    
    
    public int getMaxPacketNum()
    {
        return packetizer.size()-1;
    }
    
    
    public void registerDataReceiver(DataReceiver dataReceiver)
    throws MaximumClientsReached
    {
        this.dataReceiver = new DataReceiverWrapper(dataReceiver);
    }
    
    /** Registers BaseDataProducerListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addBaseDataProducerListener(BaseDataProducerListener listener)
    {
        if (listenerList == null )
        {
            listenerList = new EventListenerList();
        }
        listenerList.add(BaseDataProducerListener.class, listener);
    }
    
    /** Removes BaseDataProducerListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeBaseDataProducerListener(BaseDataProducerListener listener)
    {
        listenerList.remove(BaseDataProducerListener.class, listener);
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireDataProducerIsEmpty(BaseDataProducer event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==BaseDataProducerListener.class)
            {
                ((BaseDataProducerListener)listeners[i+1]).baseDataProducerIsEmpty(event);
            }
        }
    }
    
    /** Getter for property dataProducerState.
     * @return Value of property dataProducerState.
     *
     */
    public DataProducerState getDataProducerState()
    {
        return dataProducerState;
    }
    
    /** Setter for property dataProducerState.
     * @param dataProducerState New value of property dataProducerState.
     *
     */
    public void setDataProducerState(DataProducerState dataProducerState)
    {
        if(dataProducerState.equals(this.dataProducerState))
            return;
        this.dataProducerState = dataProducerState;
        fireStateChanged();
    }
    
    
    
    
    public void dataSourceStateWaiting()
    {
        setDataProducerState(DataProducerState.DP_WAITING);
    }
    public void dataSourceStateStarted()
    {
        if(packetizer.size()>0)
            setDataProducerState(DataProducerState.DP_STARTED);
        else
            setDataProducerState(DataProducerState.DP_STARTED_NODATA);
    }
    public void dataSourceStateEnded()
    {
        setDataProducerState(DataProducerState.DP_ENDED);
    }
    public void dataSourceStateStoped()
    {
        setDataProducerState(DataProducerState.DP_STOPED);
    }
    public void dataSourceStateError()
    {
        setDataProducerState(DataProducerState.DP_ERROR);
    }
    
    
}