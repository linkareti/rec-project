/*
 * DefaultXYDatasetProxy.java
 *
 * Created on 04 June 2003, 00:04
 */

package com.linkare.rec.impl.client.experiment;
import com.linkare.rec.data.acquisition.*;
import com.linkare.rec.data.config.*;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public class DefaultXYDatasetProxy extends org.jfree.data.xy.AbstractXYDataset implements com.linkare.rec.impl.client.experiment.ExpDataModelListener
{
    
    /** Creates a new instance of DefaultXYDatasetProxy */
    public DefaultXYDatasetProxy()
    {
	
    }
    
    public void dataModelRunning()
    {
	fireDatasetChanged();
    }
    
    public void dataModelStoped() 
    {
        fireDatasetChanged();
    }
    
    public void dataModelEnded() 
    {
        fireDatasetChanged();
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
        
    int nsamples = 0;
    public void newSamples(NewExpDataEvent evt)
    {
        for(int i=evt.getSamplesStartIndex(); i<evt.getSamplesEndIndex(); i++)
        {            
            if(expDataModel.getValueAt(i, getChannelDisplayY()) != null)            
            {
                nsamples++;                
            }
        }
        fireDatasetChanged();
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
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
		return null;
	}
		
	String multiplierX=expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale().getMultiplier().toString();
	String ph_unit_symbolX=expDataModel.getChannelConfig(getChannelDisplayX()).getSelectedScale().getPhysicsUnitSymbol();
	String ch_nameX=expDataModel.getChannelConfig(getChannelDisplayX()).getChannelName();
	
	String multiplierY=expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale().getMultiplier().toString();
	String ph_unit_symbolY=expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale().getPhysicsUnitSymbol();
	String ch_nameY=expDataModel.getChannelConfig(getChannelDisplayY()).getChannelName();
	
	return ch_nameX + " ["+ multiplierX+ph_unit_symbolX+"] vs "+ch_nameY + " ["+ multiplierY+ph_unit_symbolY+"]";
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
	
	if(expDataModel.getTotalSamples()==-1) return 0;
	//return expDataModel.getTotalSamples();
        return nsamples;
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
	
	return (expDataModel.getValueAt(item,getChannelDisplayX()) != null)?expDataModel.getValueAt(item,getChannelDisplayX()).getValueNumber().doubleValue():0;
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
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
		return 0;
	}
	
	return (expDataModel.getValueAt(item,getChannelDisplayY()) != null)?expDataModel.getValueAt(item,getChannelDisplayY()).getValueNumber().doubleValue():0;
    }
    
    
    private ExpDataModel expDataModel;
    
    /** Holds value of property channelDisplay. */
    private int channelDisplayX;
    
    /** Holds value of property channelDisplayY. */
    private int channelDisplayY;
    
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
    public int getChannelDisplayX()
    {
	return this.channelDisplayX;
    }
    
    /** Setter for property channelDisplay.
     * @param channelDisplay New value of property channelDisplay.
     */
    public void setChannelDisplayX(int channelDisplayX)
    {
	this.channelDisplayX = channelDisplayX;
    }
    
    /** Getter for property channelDisplayY.
     * @return Value of property channelDisplayY.
     */
    public int getChannelDisplayY()
    {
	return this.channelDisplayY;
    }
    
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public void setChannelDisplayY(int channelDisplayY)
    {
	this.channelDisplayY = channelDisplayY;
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
