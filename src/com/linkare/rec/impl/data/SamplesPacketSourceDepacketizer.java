/*
 * DiscardablePhysicsValueMatrixPacketizer.java
 *
 * Created on 7 de Janeiro de 2004, 20:00
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.acquisition.SamplesPacket;
import com.linkare.rec.data.acquisition.TOTAL_SAMPLES_UNDEFINED;
import com.linkare.rec.data.acquisition.TOTAL_PACKETS_UNDEFINED;
import java.util.ArrayList;
import javax.swing.event.EventListenerList;
import com.linkare.rec.acquisition.MaxPacketNumUnknown;

/**
 *
 * @author  Administrator
 */
public class SamplesPacketSourceDepacketizer implements SamplesSource
{
    private SamplesPacketSource samplesPacketSource;
    private ArrayList samplesLocations=null;
    private int lastSampleCount = TOTAL_SAMPLES_UNDEFINED.value;
    private int largestPacketGot = TOTAL_PACKETS_UNDEFINED.value;
    private SamplesPacketSourceAdapter adapter=null;
    /** Utility field used by event firing mechanism. */
    private EventListenerList listenerList =  null;
    
    /** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
    public SamplesPacketSourceDepacketizer()
    {
	this(null);
    }
    
    /** Creates a new instance of DiscardablePhysicsValueMatrixPacketizer */
    public SamplesPacketSourceDepacketizer(SamplesPacketSource samplesPacketSource)
    {
	setSamplesPacketSource(samplesPacketSource);
    }
    
    
    public PhysicsValue[][] getSamples(int sampleStartIndex, int sampleEndIndex) throws SamplesReadException
    {
	if(sampleStartIndex>sampleEndIndex)
	    throw new SamplesReadException("Start index is bigger than end index", sampleEndIndex);
	
	
	PhysicsValue[][] retVal=new PhysicsValue[sampleEndIndex-sampleStartIndex+1][];
	
	for(int i=0;i<retVal.length;i++)
	{
	    retVal[i]=getSample(sampleStartIndex+i);
	}
	
	return retVal;
    }
    
    private PhysicsValue[] getSample(int sampleIndex) throws SamplesReadException
    {
	
	if(samplesLocations==null || getSamplesPacketSource()==null)
	    throw new SamplesReadException("Error depacketizing... probably source is not set yet", sampleIndex);
	
	try
	{
	    int[] sampleLocation=(int[])samplesLocations.get(sampleIndex);
	    SamplesPacket packet=samplesPacketSource.getSamplesPackets(sampleLocation[0],sampleLocation[0])[0];
	    return packet.getData(sampleLocation[1]);
	    
	}catch(Exception e)
	{
	    e.printStackTrace();
	    throw new SamplesReadException("Error depacketizing... couldn't fetch sample", sampleIndex);
	}
    }
    
    
    private void refreshState(int largestPacketIndex)
    {
	if(largestPacketGot>=largestPacketIndex) return;
	try
	{
	    while(largestPacketGot<largestPacketIndex)
	    {
		
		
		SamplesPacket packet=samplesPacketSource.getSamplesPackets(largestPacketGot+1, largestPacketGot+1)[0];
	    
		PhysicsValue[][] rows=packet.getData();
		
		
		++largestPacketGot;
		
		if(rows==null) continue;
		
		for(int r=0;r<rows.length;r++)
		{
		    samplesLocations.add(new int[]{largestPacketGot,r});
		}
		
		if(lastSampleCount==MaxPacketNumUnknown.value)
		    lastSampleCount=rows.length-1;
		else
		    lastSampleCount+=rows.length;
		

	    }
	}catch(SamplesPacketReadException ignored){}
	
	fireNewSamples(lastSampleCount);
	
    }
    
    
    /** Registers SamplesSourceEventListener to receive events.
     * @param listener The listener to register.
     *
     */
    public synchronized void addSamplesSourceEventListener(SamplesSourceEventListener listener)
    {
	if (listenerList == null )
	{
	    listenerList = new EventListenerList();
	}
	listenerList.add(SamplesSourceEventListener.class, listener);
    }
    
    /** Removes SamplesSourceEventListener from the list of listeners.
     * @param listener The listener to remove.
     *
     */
    public synchronized void removeSamplesSourceEventListener(SamplesSourceEventListener listener)
    {
	listenerList.remove(SamplesSourceEventListener.class, listener);
    }
    
    /** Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     *
     */
    private void fireNewSamples(int sampleLargestIndex)
    {
	SamplesSourceEvent event=new SamplesSourceEvent(this, sampleLargestIndex);
	if (listenerList == null) return;
	Object[] listeners = listenerList.getListenerList();
	for (int i = listeners.length-2; i>=0; i-=2)
	{
	    if (listeners[i]==SamplesSourceEventListener.class)
	    {
		((SamplesSourceEventListener)listeners[i+1]).newSamples(event);
	    }
	}
    }
    
    /** Getter for property source.
     * @return Value of property samplesPacketSource.
     *
     */
    public SamplesPacketSource getSamplesPacketSource()
    {
	return samplesPacketSource;
    }
    
    /** Setter for property source.
     * @param source New value of property samplesPacketSource.
     *
     */
    public void setSamplesPacketSource(SamplesPacketSource samplesPacketSource)
    {
	if(this.samplesPacketSource!=null && this.samplesPacketSource==samplesPacketSource)
	{
	    return;
	}
	
	if(this.samplesPacketSource!=null && adapter!=null)
	{
	    samplesPacketSource.removeSamplesPacketSourceEventListener(adapter);
	}
	
	
	this.samplesPacketSource=samplesPacketSource;
	
	this.samplesLocations=new ArrayList(100);

	if(this.samplesPacketSource!=null)
	{
	    if(adapter==null)
		adapter=new SamplesPacketSourceAdapter();
	    
	    this.samplesPacketSource.addSamplesPacketSourceEventListener(adapter);
	}
    }
    
    public int getLastSampleNum()
    {
	return lastSampleCount;
    }
    
    private class SamplesPacketSourceAdapter implements SamplesPacketSourceEventListener
    {
	
	public void newSamplesPackets(SamplesPacketSourceEvent evt)
	{
	    //System.out.println("SamplesPacketSourceDepacktizer refreshing state... "+ evt.getPacketLargestIndex());
	    if(evt!=null)
		refreshState(evt.getPacketLargestIndex());
	}
	
    }
    
}
