/*
 * SoundXYDataSetProxy.java
 *
 * Created on 19 July 2003, 23:59
 */

package pt.utl.ist.elab.client.serial.stamp.statsound.displays;

import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 *
 * @author  Andr�
 */
public class SoundXYDataSetProxy  extends org.jfree.data.xy.AbstractXYDataset implements 
com.linkare.rec.impl.client.experiment.ExpDataModelListener
{        
    private ExpDataModel expDataModel;
    
    private short[] audioDataLeft;
    private short[] audioDataRight;
    private double sampleRate=48000.0;
           
    /** Creates a new instance of SoundXYDataSetProxy */
    public SoundXYDataSetProxy() 
    {
    }    
    
    public void dataModelStoped()
    {
    }
    
    public void headerAvailable(HardwareAcquisitionConfig header)
    {
	fireDatasetChanged();
    }    
    
    public void newSamples(NewExpDataEvent evt)
    {
        for(int s = evt.getSamplesStartIndex(); s<=evt.getSamplesEndIndex(); s++)
        {
            if(expDataModel.getValueAt(s, expDataModel.getChannelIndex("wave")) == null)
            {
                System.out.println("Returning null, for the channel:"+expDataModel.getChannelIndex("wave"));
                return;
            }
            System.out.println("Got bytes");
            byte[] audioBytes=expDataModel.getValueAt(s, expDataModel.getChannelIndex("wave")).getValue().getByteArrayValue().getData();
            short[] leftData=null;
            short[] rightData=null;
            leftData = new short[audioBytes.length/4];            
            rightData = new short[audioBytes.length/4];            
            for (int i=0; i+4<=audioBytes.length; i+=4) 
            {
                short LSBLeft = audioBytes[i];
                short MSBLeft = audioBytes[i+1];
                short LSBRight = audioBytes[i+2];
                short MSBRight = audioBytes[i+3];            

                short left=(short)(MSBLeft << 8 | (255 & LSBLeft));
                short right=(short)(MSBRight << 8 | (255 & LSBRight));
                leftData[i/4]=left;
                rightData[i/4]=right;
            }               
            this.audioDataLeft=leftData;
            this.audioDataRight=rightData;
            System.out.println("Dataset changed");
            fireDatasetChanged();
        }
    }
    
    /** Returns the number of series in the dataset.
     *
     * @return the series count.
     */
    public int getSeriesCount()
    {
	if(expDataModel==null || !expDataModel.isDataAvailable())
	{
		return 0;
	}
        return 2;
    }
    
    /** Returns the name of a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return the name of the series.
     */
    public Comparable getSeriesKey(int series)
    {
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
		return null;
	}
        if(series==0)
        {
            return "Channel 1";
        }
        return "Channel 2";
    }
    
    /** Returns the number of items in a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return the number of items within the series.
     */
    public int getItemCount(int series)
    {
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
            return 0;
	}
	if(expDataModel.getTotalSamples()==-1) 
        {
            return 0;        
        }
        if(series==0)
        {
            if(audioDataRight==null)
            {
                return 0;
            }	            
            return audioDataRight.length;
        }                        
        if(audioDataLeft==null)
        {
            return 0;
        }	            
        return audioDataLeft.length;        	
    }
    
    /** Returns the x-value for an item within a series.
     * <P>
     * The implementation is responsible for ensuring that the x-values are
     * presented in ascending order.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return the x-value.
     */
    public double getXValue(int series, int item)
    {
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
		return 0;
	}        
	if(series==1)
        {
            if((audioDataLeft==null||item>=audioDataLeft.length))
            {
                return 0;            
            }
            else
            {
                return item/sampleRate;
            }
        }
        else 
        {
            if((audioDataRight==null||item>=audioDataRight.length))
            {
                return 0;            
            }   
            else
            {
                return item/sampleRate;
            }            
        }        
    }    
    
    public double getYValue(int series, int item)
    {
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
		return 0;
	}	
        if(series==1)
        {
            if((audioDataLeft==null||item>=audioDataLeft.length))
            {
                return 0;            
            }
            else
            {
                return audioDataLeft[item];
            }            
        }   
        else
        {
            if((audioDataRight==null||item>=audioDataRight.length))
            {
                return 0;            
            }
            else
            {
                return audioDataRight[item];
            }                
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
	if(expDataModel!=null)
	    expDataModel.removeExpDataModelListener(this);
	
	this.expDataModel = expDataModel;        
	
	if(this.expDataModel!=null)
	{
	    this.expDataModel.addExpDataModelListener(this);
	    fireDatasetChanged();
	}
	
    }        
    
    public void dataModelEnded()
    {
    }
    
    public void dataModelError()
    {
    }
    
    public void dataModelStarted()
    {
        fireDatasetChanged();
    }
    
    public void dataModelStartedNoData()
    {
        fireDatasetChanged();
    }
    
    public void dataModelWaiting()
    {
    }
    
    public Number getX(int param, int param1)
    {
        return new Double(getXValue(param, param1));
    }
    
    public Number getY(int param, int param1)
    {
        return new Double(getYValue(param, param1));
    }
    
    public org.jfree.data.DomainOrder getDomainOrder()
    {
        return org.jfree.data.DomainOrder.ASCENDING;
    }
    
}
