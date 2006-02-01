/*
 * DefaultTableModelProxy.java
 *
 * Created on 7 de Maio de 2003, 17:17
 */

package pt.utl.ist.elab.client.webrobot.displays.proxys;

import com.linkare.rec.data.synch.DateTime;
import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.acquisition.*;
import com.linkare.rec.data.metadata.*;
import com.linkare.rec.data.config.*;
import javax.swing.event.TableModelListener;
/**
 *
 * @author  Andr�
 */
public class DefaultChartModelProxy extends org.jfree.data.xy.XYSeriesCollection //implements ExpDataModelListener
{
    /** Holds value of property expDataModel. */
    private int numChannels;
    private int startChannelIndex;
    private boolean booleanValue;
    private DateTime firstSampleTime=new DateTime();
    private ExpDataModel expDataModel=null;	    
    private java.util.Vector vectorXY;
    private HardwareAcquisitionConfig header=null;
        
    public DefaultChartModelProxy(int startChannelIndex, int numChannels, boolean booleanValue)
    {
        super();
        this.startChannelIndex=startChannelIndex;
        this.numChannels=numChannels;
        this.booleanValue=booleanValue;
        vectorXY=new java.util.Vector(0);        
    }
    
    public void dataModelRunning()
    {
        fireDatasetChanged();
    }

    public void dataModelStoped()
    {
        fireDatasetChanged();
    }
        
    private int lastnewsamples=0;
    public void newSamples(NewExpDataEvent evt)
    {
	lastnewsamples=evt.getSamplesEndIndex();
        fireDatasetChanged();    
    }

    public int getItemCount(int series) 
    {
        return lastnewsamples;   
    }    
    
     public double getXValue(int series, int item) 
     {
        if(expDataModel==null||header==null)
        {
            return 0;
        }         
        
        if(item>lastnewsamples)
        {
            return 0;
        }
        if(item==0)
        {
            firstSampleTime=expDataModel.getTimeStamp(item);
        }
        double elapsedMilis=(double)(expDataModel.getTimeStamp(item).getMilliSeconds()-firstSampleTime.getMilliSeconds())/1000;                              
        return elapsedMilis;
    }

     /**
     * Returns the y-value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param index  the index of the item of interest (zero-based).
     *
     * @return the y-value for the specified series and item.
     */
    public double getYValue(int series, int index) 
    {   
        if(expDataModel==null||header==null)
        {
            return 0;
        }        
        /**I need this for loop, because I don't know what series are being shown...
         *if you're going to show all series you don't need this...just return
         *with series+startChannelIndex
         */
        for(int i=startChannelIndex;i<(startChannelIndex+numChannels);i++)
        {
            if(getSeriesKey(series).equals(header.getChannelsConfig(i).getChannelName()))
            {
                series=i;
                break;
            }
        }
        if(index>lastnewsamples)
        {
             return 0;
        }
        if(!booleanValue)
        {
            int value=expDataModel.getValueAt(index,series).getValue().getIntValue();
            return value;            
        }
        else
        {
            return convertBooleanInt(expDataModel.getValueAt(index,series).getValue().isBooleanValue());
        }
    }
    
    private int convertBooleanInt(boolean toConvert)
    {
        if(toConvert)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
     
    /** Getter for property expDataModel.
    * @return Value of property expDataModel.
    */
    public ExpDataModel getExpDataModel()
    {
        return this.expDataModel;
    }

    /** Setter for property expDataModel.
    * @param expDataModel New value of property expDataModel.
    */
    public void setExpDataModel(ExpDataModel expDataModel)
    {  
        vectorXY=new java.util.Vector(0);
        this.expDataModel=expDataModel;
        fireDatasetChanged();	
    }

    public void headerAvailable(HardwareAcquisitionConfig header)
    {
        this.header=header;
        if(header!=null)
        {
            for(int i=0;i<numChannels;i++)
            {
                vectorXY.addElement(new org.jfree.data.xy.XYSeries(header.getChannelsConfig(i+startChannelIndex).getChannelName()));
            }                   
            setSeriesVisible(true,0);
            fireDatasetChanged();            
        }        
    }	
    
    public void setSeriesVisible(boolean value, int series)
    {
        if(value)
        {
            addSeries((org.jfree.data.xy.XYSeries)vectorXY.elementAt(series));
        }
        else
        {
            removeSeries((org.jfree.data.xy.XYSeries)vectorXY.elementAt(series));
        }
    }            
}
