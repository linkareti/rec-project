/*
 * DefaultXYDatasetProxy.java
 *
 * Created on 04 June 2003, 00:04
 */

package pt.utl.ist.elab.client.meteo.displays;
import com.linkare.rec.data.config.HardwareAcquisitionConfig;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public class DefaultTimeDatasetProxy extends org.jfree.data.xy.AbstractXYDataset implements com.linkare.rec.impl.client.experiment.ExpDataModelListener
{

    /** Creates a new instance of DefaultXYDatasetProxy */
    public DefaultTimeDatasetProxy()
    {
    }
    
    public void dataModelRunning()
    {
	fireDatasetChanged();
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
			
	String ch_nameX= "Tempo";
	
	String multiplierY=expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale().getMultiplier().toString();
	String ph_unit_symbolY=expDataModel.getChannelConfig(getChannelDisplayY()).getSelectedScale().getPhysicsUnitSymbol();
	String ch_nameY=expDataModel.getChannelConfig(getChannelDisplayY()).getChannelName();
	
	return ch_nameX + " vs "+ch_nameY + " ["+ multiplierY+ph_unit_symbolY+"]";        
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
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount() || expDataModel.getValueAt(item, getChannelTime()) == null)
	{
		return 0;
	}	
        return expDataModel.getValueAt(item, getChannelTime()).getValueNumber().longValue();
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
	if(expDataModel==null || !expDataModel.isDataAvailable() || series>=expDataModel.getChannelCount() || expDataModel.getValueAt(item, getChannelDisplayY()) == null || expDataModel.getValueAt(item, getChannelTime()) == null)
	{
		return 0;
	}
	
	return expDataModel.getValueAt(item, getChannelDisplayY()).getValueNumber().doubleValue();
    }
    
    
    private ExpDataModel expDataModel;
        
    /** Holds value of property channelDisplayY. */
    private int channelDisplayY;
    
    /** Holds value of property channelDisplayY. */
    private int channelTime;
    
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
    public int getChannelTime()
    {
	return this.channelTime;
    }    
    
    /** Setter for property channelDisplay.
     * @param channelDisplay New value of property channelDisplay.
     */
    public void setChannelTime(int channelTime)
    {
	this.channelTime = channelTime;
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
        return org.jfree.data.DomainOrder.NONE;
    }
    
}
