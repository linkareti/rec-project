/*
 * DefaultXYDatasetProxy.java
 *
 * Created on 04 June 2003, 00:04
 */

package com.linkare.rec.impl.client.experiment;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.synch.*;
import com.linkare.rec.data.*;
import java.util.*;
import com.linkare.rec.data.metadata.*;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class DefaultHistogramDatasetProxy extends org.jfree.data.xy.AbstractIntervalXYDataset implements com.linkare.rec.impl.client.experiment.ExpDataModelListener
{
    
    private Vector oCountHits=null;
    
    /** Creates a new instance of DefaultXYDatasetProxy */
    public DefaultHistogramDatasetProxy()
    {
        
    }
    
    public void dataModelWaiting()
    {
    }
    
    public void dataModelStoped()
    {
        //recalculateAll();
    }
    
    public void dataModelEnded()
    {
    }
    
    public void dataModelError()
    {
    }
    
    public void dataModelStarted()
    {
    }
    
    public void dataModelStartedNoData()
    {
    }
    
    private double stDev=0.;
    private double xMean=0.;
    private int nSamples=0;
    private int nClasses=0;
    private double deltaX=0.;
    
    private void recalculateAll()
    {
        if(expDataModel==null) return;
        nSamples=expDataModel.getTotalSamples();
        
        xMean=0.;
        for(int i=0;i<nSamples;i++)
        {
            if(expDataModel.getValueAt(i,channelDisplay) != null)
                xMean+=expDataModel.getValueAt(i,channelDisplay).getValue().toDouble();
        }
        
        xMean/=(double)nSamples;
        //nClasses=(int)Math.round(3.+(double)nSamples/25.);
        nClasses=(int)Math.round(2.+(double)nSamples/30.)*2+1;
        stDev=0.;
        for(int i=0;i<nSamples;i++)
        {
            if(expDataModel.getValueAt(i,channelDisplay) != null)
                stDev+=Math.pow(expDataModel.getValueAt(i,channelDisplay).getValue().toDouble()-xMean,2);
        }
        
        stDev/=(nSamples==1?1.:(double)nSamples)-1.;
        stDev=Math.sqrt(stDev);
        
        deltaX=6.*stDev/(double)nClasses;
        
        oCountHits=new Vector(nClasses);
        for(int i=0;i<nClasses;i++)
        {
            oCountHits.add(i,new Long(0));//iniciar contagens a zero...
        }
        
        for(int i=0;i<nSamples;i++)
        {
            if(expDataModel.getValueAt(i,channelDisplay) != null)
            {
                double xValue=expDataModel.getValueAt(i,channelDisplay).getValue().toDouble();
                int block_to_add=(int)Math.floor((xValue-xMean)/deltaX+((double)nClasses)/2.); //adicionar ao bloco em que a diferen�a entre x e xMean em rela��o a delta + bloco central displace
                if(block_to_add<0) block_to_add=0;
                if(block_to_add>=nClasses) block_to_add=nClasses-1;
                oCountHits.set(block_to_add,new Long( ((Long)oCountHits.get(block_to_add)).longValue()+1) );//adicionar ao valor anterior...
            }
        }
        
        fireDatasetChanged();
    }
    
    
    
    public void newSamples(NewExpDataEvent evt)
    {
        for(int i=evt.getSamplesStartIndex(); i<=evt.getSamplesEndIndex(); i++)
            if(expDataModel.getValueAt(i, channelDisplay) != null)
            {
                recalculateAll();
                break;
            }
    }
    
    /** Returns the number of series in the dataset.
     *
     * @return the series count.
     */
    public int getSeriesCount()
    {
        if(oCountHits==null)
        {
            
            return 0;
        }
        
        return 1;
    }
    
    /** Returns the name of a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return the name of the series.
     */
    public Comparable getSeriesKey(int series)
    {
        if(oCountHits==null)
        {
            return null;
        }
        
        String multiplier=expDataModel.getChannelConfig(channelDisplay).getSelectedScale().getMultiplier().toString();
        String ph_unit_symbol=expDataModel.getChannelConfig(channelDisplay).getSelectedScale().getPhysicsUnitSymbol();
        String ch_name=expDataModel.getChannelConfig(channelDisplay).getChannelName();
        
        return ch_name + " ["+ multiplier+ph_unit_symbol+"]";
    }
    
    /** Returns the number of items in a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return the number of items within the series.
     */
    public int getItemCount(int series)
    {
        if(oCountHits==null)
        {
            
            return 0;
        }
        
        return oCountHits.size();
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
    {//item varia entre 0 e nClasses-1
        if(oCountHits==null || item>=oCountHits.size())
        {
            return 0;
        }
        
        //xMean+(i-nClasses/2)*deltaX+deltaX/2
        //return new Double(xMean+deltaX*((double)item-((double)nClasses)/2.)+deltaX/2.);
        return xMean+deltaX*((double)item-((double)nClasses)/2.);
    }
    
    /** Returns the y-value for an item within a series.
     *
     * @param series  the series (zero-based index).
     * @param item  the item (zero-based index).
     *
     * @return the y-value.
     */
    public double getYValue(int series, int item)
    {
        if(oCountHits==null || item>=oCountHits.size())
        {
            return 0;
        }
        
        return ((Long)oCountHits.get(item)).longValue();
    }
    
    
    private ExpDataModel expDataModel;
    
    /** Holds value of property channelDisplay. */
    private int channelDisplay=0;
    
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
    
    /** Getter for property channelDisplay.
     * @return Value of property channelDisplay.
     */
    public int getChannelDisplay()
    {
        return this.channelDisplay;
    }
    
    /** Setter for property channelDisplay.
     * @param channelDisplay New value of property channelDisplay.
     */
    public void setChannelDisplay(int channelDisplay)
    {
        this.channelDisplay = channelDisplay;
    }
    
    /** Returns the ending X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item within a series (zero-based index).
     *
     * @return the ending X value for the specified series and item.
     */
    public double getEndXValue(int series, int item)
    {
        if(oCountHits==null || item>=oCountHits.size())
        {
            return 0;
        }
        //agora � que parece bem... n�o?
        return xMean+deltaX*((double)item-((double)nClasses)/2.)+deltaX/2.;
        
    }
    
    /** Returns the ending Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item within a series (zero-based index).
     *
     * @return the ending Y value for the specified series and item.
     */
    public double getEndYValue(int series, int item)
    {
        return getYValue(series,item);
    }
    
    /** Returns the starting X value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item within a series (zero-based index).
     *
     * @return the starting X value for the specified series and item.
     */
    public double getStartXValue(int series, int item)
    {
        if(oCountHits==null || item>=oCountHits.size())
        {
            return 0;
        }
        return xMean+deltaX*((double)item-((double)nClasses)/2.)-deltaX/2.;
        
    }
    
    /** Returns the starting Y value for the specified series and item.
     *
     * @param series  the series (zero-based index).
     * @param item  the item within a series (zero-based index).
     *
     * @return starting Y value for the specified series and item.
     */
    public double getStartYValue(int series, int item)
    {
        return 0;
    }
    
    public Number getEndX(int series, int item)
    {
        return new Double(getEndXValue(series, item));
    }
    
    public Number getEndY(int series, int item)
    {
        return new Double(getEndYValue(series, item));
    }
    
    public Number getStartX(int series, int item)
    {
        return new Double(getStartXValue(series, item));
    }
    
    public Number getStartY(int series, int item)
    {
        return new Double(getStartYValue(series, item));
    }
    
    public Number getX(int series, int item)
    {
        return new Double(getXValue(series, item));
    }
    
    public Number getY(int series, int item)
    {
        return new Double(getYValue(series, item));
    }
    
    public org.jfree.data.DomainOrder getDomainOrder()
    {
        return org.jfree.data.DomainOrder.NONE;
    }        
}
