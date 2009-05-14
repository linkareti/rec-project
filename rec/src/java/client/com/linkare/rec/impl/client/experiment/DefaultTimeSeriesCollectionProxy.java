/*
 * DefaultXYDatasetProxy.java
 *
 * Created on 04 June 2003, 00:04
 */

package com.linkare.rec.impl.client.experiment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.linkare.rec.data.acquisition.PhysicsValueType;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public class DefaultTimeSeriesCollectionProxy extends XYSeriesCollection implements org.jfree.data.xy.XYDataset, com.linkare.rec.impl.client.experiment.ExpDataModelListener
{
    
    private int[] channelIndexes=null;
    
    /** Creates a new instance of DefaultXYDatasetProxy */
    public DefaultTimeSeriesCollectionProxy()
    {
	
    }
    
    public void dataModelWaiting()
    {
	fireDatasetChanged();
    }
    
    public void dataModelStoped()
    {
    }
    
    public void dataModelEnded()
    {
    }
    
    public void dataModelError()
    {
    }
    
    public void dataModelStarted()
    {
        if(header == null)
            headerAvailable(expDataModel.getAcquisitionConfig());
    }
    
    public void dataModelStartedNoData()
    {
        if(header == null)
            headerAvailable(expDataModel.getAcquisitionConfig());
    }       
    
    private HardwareAcquisitionConfig header = null;
    private void headerAvailable(HardwareAcquisitionConfig header)
    {
        if(header == null)
            return;
        
        this.header = header;
        
	//if no channel available... show all channels
	//otherwise, if you don't want channels to be displayed at all, set channelIndexes=new int[0];
	if(channelIndexes==null)
	{
	    channelIndexes=new int[header.getChannelsConfig().length];
	    for(int i=0;i<channelIndexes.length;i++)
		channelIndexes[i]=i;
	}
	
	int[] channelIndexesValidated=new int[channelIndexes.length];
	int indexChannelsValidated=-1;
	for(int i=0;i<channelIndexes.length;i++)
	{
	    channelIndexesValidated[i]=-1;
	    PhysicsValueType channel_type=header.getChannelsConfig(channelIndexes[i]).getSelectedScale().getMaximumValue().getDiscriminator();
	    if(channel_type!=PhysicsValueType.ByteArrayVal)
		channelIndexesValidated[++indexChannelsValidated]=channelIndexes[i];
	}
	
	channelIndexes=new int[indexChannelsValidated+1];
	
	if(indexChannelsValidated>=0)
	    System.arraycopy(channelIndexesValidated,0,channelIndexes,0,indexChannelsValidated+1);
	
	
	channelIndexesValidated=null; System.gc();
	
	for(int i=0;i<channelIndexes.length;i++)
	{
	    JToggleButton btnAddRemoveSeries=new JToggleButton(getSeriesName(i),true);
	    btnAddRemoveSeries.setActionCommand(""+i);
	    btnAddRemoveSeries.addActionListener(
		new ActionListener()
		{
		    public void actionPerformed(ActionEvent evt)
		    {
			JToggleButton btnSrc=(JToggleButton)evt.getSource();
			int seriesAddRemove=Integer.parseInt(evt.getActionCommand());
			if(btnSrc.isSelected())
			    addSeries(new XYSeries(getSeriesName(seriesAddRemove)));
			else
			    removeSeries(seriesAddRemove);
		    }
		});
	}
	
	fireDatasetChanged();
    }
    
    public void newSamples(NewExpDataEvent evt)
    {
	fireDatasetChanged();
    }
    
   
    
  
    /** Returns the number of series in the dataset.
     *
     * @return the series count.
     */
    public int getSeriesCount()
    {
	if(expDataModel==null || !expDataModel.isDataAvailable() || channelIndexes!=null)
	{
		return 0;
	}
     
	
	
	return channelIndexes.length;
    }
    
    /** Returns the name of a series.
     *
     * @param series  the series (zero-based index).
     *
     * @return the name of the series.
     */
    public String getSeriesName(int series)
    {
	if(expDataModel==null || !expDataModel.isDataAvailable() || channelIndexes==null || series>=channelIndexes.length)
	{
		return null;
	}
     
	int channel_index=channelIndexes[series];
	
	String multiplier=expDataModel.getChannelConfig(channel_index).getSelectedScale().getMultiplier().toString();
	String ph_unit_symbol=expDataModel.getChannelConfig(channel_index).getSelectedScale().getPhysicsUnitSymbol();
	String ch_name=expDataModel.getChannelConfig(channel_index).getChannelName();
     
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
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount())
	{
		return 0;
	}
     
	if(expDataModel.getTotalSamples()==-1) return 0;
	return expDataModel.getTotalSamples();
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
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount() || expDataModel.getAcquisitionConfig() == null)
	{
		return 0;
	}
     
	return expDataModel.getTimeStamp(item).getElapsedTimeInMillis(expDataModel.getAcquisitionConfig().getTimeStart());
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
     
	return expDataModel.getValueAt(item,series).getValueNumber().doubleValue();
    }
    
    
    private ExpDataModel expDataModel;
    
    /** Holds value of property btnsToolBar. */
    private JToggleButton[] btnsToolBar;
    
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
    
    /** Indexed getter for property channelIndexes.
     * @param index Index of the property.
     * @return Value of the property at <CODE>index</CODE>.
     */
    public int getChannelIndex(int index)
    {
	return this.channelIndexes[index];
    }
    
    /** Getter for property channelIndexes.
     * @return Value of property channelIndexes.
     */
    public int[] getChannelIndexes()
    {
	return this.channelIndexes;
    }
    
    /** Indexed setter for property channelIndexes.
     * @param index Index of the property.
     * @param channelIndexes New value of the property at <CODE>index</CODE>.
     */
    public void setChannelIndex(int index, int channelIndex)
    {
	this.channelIndexes[index] = channelIndex;
    }
    
    /** Setter for property channelIndexes.
     * @param channelIndexes New value of property channelIndexes.
     */
    public void setChannelIndexes(int[] channelIndexes)
    {
	this.channelIndexes = channelIndexes;
    }
    
    /** Getter for property btnsToolBar.
     * @return Value of property btnsToolBar.
     */
    public JToggleButton[] getBtnsToolBar()
    {
	return this.btnsToolBar;
    }    
}
