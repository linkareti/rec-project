/*
 * DefaultExpDataModel.java
 *
 * Created on 7 de Maio de 2003, 12:48
 */

package com.linkare.rec.impl.client.experiment;

import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.impl.data.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.synch.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.wrappers.*;
import java.util.*;
import java.util.logging.*;


/**
 *
 * @author  jp
 */
public class DefaultExpDataModel extends AbstractExpDataModel
{
    
    private static String DATA_RECEIVER_LOGGER="DataReceiver.Logger";
    private int channelCount=-1;
    
    private DateTime timeStart=null;
    //private DataAcquisitionThread dataCollector=null;
    private Hashtable channels=null;
    
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(DATA_RECEIVER_LOGGER));
        }
    }
    
    
    /** Creates a new instance of DefaultExpDataModel */
    public DefaultExpDataModel()
    {
        super();
    }
    
    
    
    public void fireNewSamples()
    {
    }
    
    /** Getter for property channelCount.
     * @return Value of property channelCount.
     */
    public int getChannelCount()
    {
        if(channelCount!=-1) return channelCount;
        
        try
        {
            channelCount=getAcquisitionConfig().getChannelsConfig().length;
        }
        catch(NullPointerException npe)
        {
            LoggerUtil.logThrowable("Couldn't get Channel Count from Acquisition Header!",npe,LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
        }
        
        return channelCount;
    }
    
    
    
    private Hashtable getChannels()
    {
        if(channels!=null) return channels;
        try
        {
            ChannelAcquisitionConfig[] ch_configs=getAcquisitionConfig().getChannelsConfig();
            
            if(ch_configs==null)
                return null;
            
            if(channels==null)
                channels=new Hashtable(ch_configs.length);
            
            for(int i=0;i<ch_configs.length;i++)
                channels.put(""+i,ch_configs[i]);
            
        }
        catch(NullPointerException npe)
        {
            LoggerUtil.logThrowable("Couldn't get Channel Count from Acquisition Header!",npe,LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
        }
        
        return channels;
    }
    
    public int getChannelIndex(String channelName)
    {
        if(getChannels()==null) return -1;
        
        Object[] channelsMapEntries=getChannels().entrySet().toArray();
        if(channelsMapEntries==null) return -1;
        
        for(int i=0;i<channelsMapEntries.length;i++)
        {
            if( ((ChannelAcquisitionConfig)((Map.Entry)channelsMapEntries[i]).getValue()).getChannelName().equals(channelName) )
                return Integer.parseInt((String)((Map.Entry)channelsMapEntries[i]).getKey());
        }
        
        return -1;
    }
    
    public String getChannelName(int channelIndex)
    {
        if(getChannels()==null) return null;
        
        return ((ChannelAcquisitionConfig)getChannels().get(""+channelIndex)).getChannelName();
    }
    
    public ChannelAcquisitionConfig getChannelConfig(int channelIndex)
    {
        if(getChannels()==null) return null;
        
        return ((ChannelAcquisitionConfig)getChannels().get(""+channelIndex));
    }
    
    public ChannelAcquisitionConfig getChannelConfig(String channelName)
    {
        if(getChannels()==null) return null;
        
        Object[] channelsMapEntries=getChannels().entrySet().toArray();
        if(channelsMapEntries==null) return null;
        
        for(int i=0;i<channelsMapEntries.length;i++)
        {
            if( ((ChannelAcquisitionConfig)((Map.Entry)channelsMapEntries[i]).getValue()).getChannelName().equals(channelName) )
                return (ChannelAcquisitionConfig)((Map.Entry)channelsMapEntries[i]).getValue();
        }
        
        return null;
    }
    
    public com.linkare.rec.data.synch.DateTime getTimeStamp(int sampleIndex)
    {
        return calcTimeStamp(sampleIndex);
    }
    
    private com.linkare.rec.data.synch.DateTime calcTimeStamp(int sampleIndex)
    {
        if(getTimeStart()==null)
            return null;
        
        try
        {
            return getTimeStart().calculateDateTime(getAcquisitionConfig().getSelectedFrequency(),sampleIndex);
        }
        catch(NullPointerException npe)
        {
            LoggerUtil.logThrowable("Couldn't calculate TimeStamp for sample "+sampleIndex,npe,LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
        }
        
        return null;
    }
    
    private DateTime getTimeStart()
    {
        if(timeStart!=null)
            return timeStart;
        
        try
        {
            timeStart=getAcquisitionConfig().getTimeStart();
        }
        catch(NullPointerException npe)
        {
            LoggerUtil.logThrowable("Couldn't get starting time from Acquisition Header!",npe,LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
        }
        
        return timeStart;
    }    
    
    public void clientsListChanged()
    {//TODO - make this methods abstract, after the first tests
    }
    
    
    
    
    /*
         private com.linkare.rec.data.synch.DateTime calcTimeStamp(int sampleIndex)
    {
        if(getTimeStart()==null) return null;
     
        try
        {
            return getTimeStart().calculateTimeStamp(getAcquisitionConfig().getSelectedFrequency(),sampleIndex);
        }catch(NullPointerException npe)
        {
            LoggerUtil.logThrowable("Couldn't calculate TimeStamp for sample "+sampleIndex,npe,LogManager.getLogManager().getLogger(DATA_RECEIVER_LOGGER));
        }
     
        return null;
    }
     
     
     
     
     private void addSamplesPacket(SamplesPacket packet)
    {
     
        PhysicsValue[][] data=packet.getData();
     
        if(data==null) return;
     
        int sampleIndexStart=0;
     
        if(samples!=null)
        {
            synchronized(samples)
            {
                sampleIndexStart=samples.size();
            }
        }
        else
        {
            samples=new SamplesPacketMatrix();
            timeStamps=new Vector(data.length*packet.getTotalPackets());
        }
     
        DateTime timeStart=packet.getTimeStart();
     
        //approximate solution... only works if all packets arrived...
        if(timeStart==null)
            timeStart=calcTimeStamp(sampleIndexStart);
     
     
        synchronized(samples)
        {
            samples.addSamplesPacket(packet);
            for(int i=0;i<data.length;i++)
            {
                timeStamps.add(timeStart.calculateDateTime(getAcquisitionConfig().getSelectedFrequency(),i));
            }
        }
        fireExpDataModelListenerNewSamples(new NewExpDataEvent(this,sampleIndexStart,sampleIndexStart+data.length-1));
    }*/
    
    
    
    /*********************************************************************************/
    /*private class DataAcquisitionThread extends Thread
    {
        public Thread currentThread=null;
        public volatile boolean exit=false;
        public volatile boolean pause=false;
        public Object synchWaitData=new Object();
        private int lastpacketgot=MaxPacketNumUnknown.value;
     
        public DataAcquisitionThread()
        {
            super.setPriority(Thread.MAX_PRIORITY-1);
            startNow();
        }
     
        private void startNow()
        {
            synchronized(this)
            {
                this.start();
                try
                {
                    stateChanged(DataProducerState.DP_STARTED_NODATA);
                    this.wait();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
     
        public void run()
        {
            synchronized(this)//inform  I entered running state
            {
                this.notifyAll();
            }
     
            Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.INFO,"Running data Acq Thread...");
     
            currentThread=currentThread();
     
            try
            {
                getAcquisitionConfig();
                getApparatusName();
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable("Exception when getting acquisitionHeader, configuratorURL or Producer Name",e,Logger.getLogger(DATA_RECEIVER_LOGGER));
            }
     
            setRunning(true);
            stopped = false;
     
            try
            {
                largestnumpacket=Math.max(largestnumpacket, remoteDataProducer.getMaxPacketNum());
                if(largestnumpacket != MaxPacketNumUnknown.value)
                {
                    stateChanged(DataProducerState.DP_STARTED);
                    SamplesPacket[] ps = remoteDataProducer.getSamples(lastpacketgot, largestnumpacket);
                    lastpacketgot = largestnumpacket;
                    for(int i=0;i<ps.length;i++)
                    {
                        addSamplesPacket(ps[i]);
                        yield();
                    }
     
                }
            }
            catch(Exception e)
            {
                LoggerUtil.logThrowable("Error fetching all packets available...",e,Logger.getLogger(DATA_RECEIVER_LOGGER));
                stateChanged(DataProducerState.DP_STOPED);
                producerIsStoped();
                deactivateDataReceiver();
                setRunning(false);
                return;
            }
     
     
            if(lastpacketgot!=MaxPacketNumUnknown.value && remoteDataProducer.getMaxPacketNum()==lastpacketgot && remoteDataProducer.getDataProducerState() == DataProducerState.DP_STOPED)
            {
                stateChanged(DataProducerState.DP_STOPED);
                producerIsStoped();
                deactivateDataReceiver();
                setRunning(false);
                return;
            }
     
            synchronized(synchWaitData)
            {
                if(largestnumpacket==MaxPacketNumUnknown.value)
                {
                    try
                    {
                        synchWaitData.wait();
                    }
                    catch(Exception e)
                    {
                        LoggerUtil.logThrowable("Exception while waiting for data available on ProxyDataProducer.DataAcqThread",e,Logger.getLogger(DATA_RECEIVER_LOGGER));
                        stateChanged(DataProducerState.DP_STOPED);
                        producerIsStoped();
                        deactivateDataReceiver();
                        setRunning(false);
                        return;
                    }
                }
            }
     
            if(largestnumpacket == MaxPacketNumUnknown.value)
            {
                stopped = true;
                stateChanged(DataProducerState.DP_STOPED);
                producerIsStoped();
                deactivateDataReceiver();
                setRunning(false);
                return;
            }
            if(exit)
            {
                stopped = true;
                stateChanged(DataProducerState.DP_STOPED);
                producerIsStoped();
                deactivateDataReceiver();
                setRunning(false);
                return;
            }
     
     
            while((lastpacketgot<=largestnumpacket || !remoteDataProducerEnded) && !exit)
            {
                while(pause)
                {
                    synchronized(this)
                    {
                        try
                        {
                            stateChanged(DataProducerState.DP_WAITING);
                            this.wait(500);
                        }
                        catch(InterruptedException ignored)
                        {	depacketizer.getSamples(
     
                        }
                    }
                }
     
                try
                {
                    SamplesPacket[] packets = remoteDataProducer.getSamples(lastpacketgot, largestnumpacket);
     
                    for(int i = 0; i < packets.length; i++)
                    {
                        addSamplesPacket(packets[i]);
                    }
     
                    lastpacketgot = largestnumpacket;
                    if(largestnumpacket == remoteDataProducer.getMaxPacketNum())
                    {
                        producerIsStoped();
                        stateChanged(DataProducerState.DP_STOPED);
                        deactivateDataReceiver();
                        setRunning(false);
                        stopped = true;
                        return;
                    }
     
                }catch(NotAnAvailableSamplesPacketException e)
                {
                    LoggerUtil.logThrowable("packet " + lastpacketgot +" is not available...",e,Logger.getLogger(DATA_RECEIVER_LOGGER));
                    lastpacketgot--;
                }catch(Exception e)
                {
                    LoggerUtil.logThrowable("Unknown exception thrown from getSamples... Exiting!" ,e,Logger.getLogger(DATA_RECEIVER_LOGGER));
                    producerIsStoped();
                    stateChanged(DataProducerState.DP_STOPED);
                    deactivateDataReceiver();
                    setRunning(false);
                    stopped = true;
                    return;
                }	depacketizer.getSamples(
     
                if(!remoteDataProducerEnded && !exit && lastpacketgot==largestnumpacket && !pause)
                {
                    synchronized(synchWaitData)
                    {
                        try
                        {
                            synchWaitData.wait();
                        }catch(Exception e)
                        {
                            LoggerUtil.logThrowable("Exception while waiting for data available on ProxyDataProducer.DataAcqThread",e,Logger.getLogger(DATA_RECEIVER_LOGGER));
                            producerIsStoped();
                            stateChanged(DataProducerState.DP_STOPED);
                            deactivateDataReceiver();
                            setRunning(false);
                            return;
                        }
                    }
                }
            }
     
            if(!exit)
            {
                producerIsStoped();
                stateChanged(DataProducerState.DP_STOPED);
            }
     
            deactivateDataReceiver();
            setRunning(false);
            stopped = true;
            currentThread=null;
            exit=true;	depacketizer.getSamples(
     
     
            Logger.getLogger(DATA_RECEIVER_LOGGER).log(Level.INFO,"Runned to end of data Acq Thread...");
        }
     
        public void pause()
        {
            stateChanged(DataProducerState.DP_WAITING);
            pause=true;
            synchronized(synchWaitData)
            {
                synchWaitData.notifyAll();
            }
        }
     
        public void play()
        {
            stateChanged(DataProducerState.DP_STARTED);
            pause=false;
            synchronized(this)
            {
                this.notifyAll();
            }
        }
     
        public void stopNow()
        {
            stateChanged(DataProducerState.DP_STOPED);
            exit = true;
            stopped = true;
            synchronized(synchWaitData)
            {
                synchWaitData.notifyAll();
            }
            synchronized(this)
            {
                this.notifyAll();
            }
        }
    }//DataAquisitionThread    */
}
