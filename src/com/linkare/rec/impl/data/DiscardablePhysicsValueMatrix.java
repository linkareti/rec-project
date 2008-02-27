/*
 * PhysicsValueMatrix.java
 *
 * Created on 6 de Janeiro de 2004, 13:51
 */

package com.linkare.rec.impl.data;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.impl.logging.LoggerUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;


/**
 *
 * @author  Administrator
 */
public class DiscardablePhysicsValueMatrix implements SamplesSource
{
    //The free mem should be defined by in the init scripts...
    public static final String SYSPROP_FREE_THRESHOLD_NAME = "ReC.PercentFreeMemoryThreshold2Serialization";
    public static final String FREE_THRESHOLD_NAME = com.linkare.rec.impl.utils.Defaults.defaultIfEmpty(System.getProperty(SYSPROP_FREE_THRESHOLD_NAME), "10");

    
    private HashMap<Integer, PhysicsValue[]> samplesRows=null;
    private int lastSampleCount=0;
    private int totalSamples=1;
    
    private DiscardablePhysicsValueMatrixIO ioDelegate;
    private boolean serialized=false;
    
    /** Utility field used by event firing mechanism. */
    private EventListenerList listenerList =  null;
    
    /** Utility field holding the SamplesSourceEventListener. */
    private transient SamplesSourceEventListener samplesSourceEventListener =  null;
    
    private double percentFreeThreshold = 0;
    
    /** Creates a new instance of PhysicsValueMatrix */
    public DiscardablePhysicsValueMatrix()
    {
        try
        {
            percentFreeThreshold = Double.parseDouble(FREE_THRESHOLD_NAME);
        }
        catch(NumberFormatException nfe)
        {
            percentFreeThreshold = 10.;
        }
	setTotalSamples(totalSamples);
    }
    
    /** Creates a new instance of PhysicsValueMatrix */
    public DiscardablePhysicsValueMatrix(int totalSamples)
    {
	setTotalSamples(totalSamples);
    }
    
    public void addDataRows(PhysicsValue[][] dataSamples)
    {
	if(dataSamples==null || dataSamples.length==0) 
            return;
	
	int startSampleIndex=lastSampleCount;
	HashMap<Integer, PhysicsValue[]> tempSamples=new HashMap<Integer, PhysicsValue[]>(dataSamples.length);
	for(int i=0;i<dataSamples.length;i++)
	    tempSamples.put(new Integer(startSampleIndex+i),dataSamples[i]);
	
	if(serialized)
	{
	    try
	    {
		ioDelegate.write(tempSamples);
		
                //TODO Check asap with JP...should I really return??
                //return;
	    }catch(IOException e)
	    {
		LoggerUtil.logThrowable("Unable to write packets to file... defaulting to memory...", e,Logger.getLogger("SwapIOLogger"));
	    }
	}
        else
        {
            samplesRows.putAll(tempSamples);
        }
	
	lastSampleCount+=dataSamples.length;
	
	if(shouldSerialize() && !serialized)
	{
	    try
	    {
		ioDelegate=new DiscardablePhysicsValueMatrixIO();
		ioDelegate.write(samplesRows);
		serialized=true;
		samplesRows.clear();
		System.gc();
	    }
	    catch(IOException e)
	    {
		
	    }
	}
	
	fireNewSamples(lastSampleCount-1);
    }
    
    
    public void addDataRow(PhysicsValue[] dataSample)
    {
	addDataRows(new PhysicsValue[][]
	{dataSample});
    }
    
    
    
    

    
    public void setTotalSamples(int totalSamples)
    {
	this.totalSamples=totalSamples;
	if(samplesRows==null)
	    samplesRows=new HashMap<Integer, PhysicsValue[]>(totalSamples);
	else
	{
	    Map<Integer, PhysicsValue[]> tempRows=samplesRows;
	    samplesRows=new HashMap<Integer, PhysicsValue[]>(totalSamples);
	    samplesRows.putAll(tempRows);
	}
    }
    
    public int getTotalSamples()
    {
	return totalSamples;
    }
    
    private boolean shouldSerialize()
    {	
	return ((double)Runtime.getRuntime().freeMemory()<=percentFreeThreshold*(double)Runtime.getRuntime().maxMemory());
    }
    
     
    private PhysicsValue[] removeSample(int sampleIndex) throws SamplesReadException
    {
	if(serialized)
	{
	    return ioDelegate.remove(sampleIndex, sampleIndex)[0];
	}
	
	if(samplesRows.containsKey(new Integer(sampleIndex)))
	    return (PhysicsValue[])samplesRows.remove(new Integer(sampleIndex));
	else
	    throw new SamplesReadException(new IOException("Error trying to read sample " + sampleIndex + " from memory!"), sampleIndex);
    }
    
    public PhysicsValue[][] getSamples(int sampleStartIndex, int sampleEndIndex) throws SamplesReadException
    {
	if(serialized)
	{
	    return ioDelegate.remove(sampleStartIndex, sampleEndIndex);
	}
	
	PhysicsValue[][] retVal=new PhysicsValue[sampleEndIndex-sampleStartIndex+1][];
	for(int i=sampleStartIndex;i<=sampleEndIndex;i++)
	{
	    retVal[i-sampleStartIndex]=removeSample(i);
	}

	return retVal;
    }
    
    /** Registers SamplesSourceEventListener to receive events.
     * @param listener The listener to register.
     *
     */
    public synchronized void addSamplesSourceEventListener(SamplesSourceEventListener listener)
    {
	if (listenerList == null )
	{
	    listenerList = new javax.swing.event.EventListenerList();
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
    
    public int getLastSampleNum()
    {
	return lastSampleCount;
    }
}
