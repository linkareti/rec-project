/*
 * MyDefaultXYExperimentGraph.java
 *
 * Created on October 16, 2003, 12:06 PM
 */

package pt.utl.ist.elab.virtual.guipack.graphs;


import com.linkare.rec.impl.client.experiment.*;
import javax.swing.Icon;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.renderer.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.chart.labels.*;
import com.linkare.rec.data.config.*;
import com.linkare.rec.data.metadata.Scale;

/**
 *
 * @author  Andre
 */
public class MultSeriesXYExperimentGraphExtended extends javax.swing.JPanel implements GraphSamplesFunction, ExpDataDisplay, ExpDataModelListener
{
    
    /** Creates a new instance of MyDefaultXYExperimentGraph */
    public MultSeriesXYExperimentGraphExtended() 
    {   
        initComponents();
    }
    
    private static String UI_CLIENT_LOGGER="ReC.baseUI";
    
    
    static
    {   
        Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
	if(l==null)
	{
            LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
	}
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()
    {
        defaultXYDatasetProxy = new MultSeriesXYDataSetProxyExtended(this);
        scrollPane = new javax.swing.JScrollPane();
        labelWaitData = new javax.swing.JLabel();

        defaultXYDatasetProxy.setChannelDisplayY(1);

        setLayout(new java.awt.BorderLayout());

        labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelWaitData.setText("waiting for data...");
        scrollPane.setViewportView(labelWaitData);

        add(scrollPane, java.awt.BorderLayout.CENTER);
    }


    // Variables declaration - do not modify
    private MultSeriesXYDataSetProxyExtended defaultXYDatasetProxy;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel labelWaitData;
    // End of variables declaration

    /** Holds value of property channelX. */
    private int channelX;	

    /** Holds value of property channelY. */
    private int channelY;

    public javax.swing.JComponent getDisplay()
    {
        return this;
    }

    public Icon getIcon()
    {
        return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/chart16.gif"));
    }

    ExpDataModel expDataModel = null;
    
    public void setExpDataModel(ExpDataModel expDataModel)
    {

	if(expDataModel!=null)
	    expDataModel.removeExpDataModelListener(this);
	
	this.expDataModel = expDataModel;
	
	if(this.expDataModel!=null)
	{
	    this.expDataModel.addExpDataModelListener(this);
            defaultXYDatasetProxy.setExpDataModel(expDataModel);	    
	}        
    }

    public String getName()
    {
        return "Chart";
    }


    public javax.swing.JMenuBar getMenuBar(){ return null;} 
    public javax.swing.JToolBar getToolBar(){ return null;} 
    
    public void dataModelStoped()
    {//BIG SILENT NOOP
    }

    public void dataModelEnded()
    {//BIG SILENT NOOP
    }
    
    public void dataModelError()
    {//BIG SILENT NOOP
    }
    
    public void dataModelStarted()
    {
	defaultXYDatasetProxy.dataModelStarted();
        if(header == null)
            headerAvailable(expDataModel.getAcquisitionConfig());
    }
    
    public void dataModelStartedNoData()
    {
        if(header == null)
            headerAvailable(expDataModel.getAcquisitionConfig());
    }
    
    public void dataModelWaiting()
    {//BIG SILENT NOOP
    }
        
    private HardwareAcquisitionConfig header = null;
    private void headerAvailable(HardwareAcquisitionConfig header)
    {
        if(header == null)
            return;
        
        this.header = header;
        Scale scaleX=header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayX()).getSelectedScale();
        String chnX=header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayX()).getChannelName();
        String pusX=scaleX.getPhysicsUnitSymbol();
        String multiplierX=scaleX.getMultiplier().toString();

        Scale scaleY;
        String chnY;
        String pusY;
        String multiplierY;
        
        scaleY=header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayAtYArray(0)).getSelectedScale();
        chnY=header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayAtYArray(0)).getChannelName();
        pusY=scaleY.getPhysicsUnitSymbol();
        multiplierY=scaleY.getMultiplier().toString();            

        NumberAxis xAxis = new NumberAxis(chnX+" ["+multiplierX+pusX+"]");
        xAxis.setAutoRange(true);
        xAxis.setAutoRangeStickyZero(false);
        xAxis.setAutoRangeIncludesZero(false);

        NumberAxis yAxis = null;
        if(defaultXYDatasetProxy.getChannelDisplayYArray().length == 1)
        {
            yAxis = new NumberAxis(chnY+" ["+multiplierY+pusY+"]");
        }
        else
        {
            yAxis = new NumberAxis("");
        }
        yAxis.setAutoRange(true);
        yAxis.setAutoRangeStickyZero(false);
        yAxis.setAutoRangeIncludesZero(false);

        XYPlot plot = new XYPlot(defaultXYDatasetProxy, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES,new StandardXYToolTipGenerator()));
        plot.setRenderer(new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES,new StandardXYToolTipGenerator()));

        chart = new JFreeChart(header.getFamiliarName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        ChartPanel panel=new ChartPanel(chart);

        panel.setPreferredSize(new java.awt.Dimension(350,250));
        panel.setMouseZoomable(true,false);

        scrollPane.remove(labelWaitData);
        scrollPane.setViewportView(panel);
    }
    
    /*public void setSeriesVisible(int series, boolean visible)
    {
        if(getChannelDisplayYArray().length==0)
        {
            return;
        }
        if(visible)
        {
            defaultXYDatasetProxy.addSeries(getChannelDisplayAtYArray(series));
        }
        else
        {
            defaultXYDatasetProxy.removeSeries(getChannelDisplayAtYArray(series));
        }
    } */       

    private boolean isScaleSet=false;

    private JFreeChart chart =null;

    public void newSamples(NewExpDataEvent evt)
    {

    }

    /** Getter for property channelDisplayX.
     * @return Value of property channelDisplayX.
     */
    public int getChannelDisplayX()
    {
        return defaultXYDatasetProxy.getChannelDisplayX();
    }

    /** Setter for property channelDisplayX.
     * @param channelDisplayX New value of property channelDisplayX.
     */
    public void setChannelDisplayX(int channelDisplayX)
    {
        defaultXYDatasetProxy.setChannelDisplayX(channelDisplayX);
    }

    /** Getter for property channelDisplayY.
     * @return Value of property channelDisplayY.
     */
    public int getChannelDisplayY()
    {
        return defaultXYDatasetProxy.getChannelDisplayY();
    }

    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public void setChannelDisplayY(int channelDisplayY)
    {
        //defaultXYDatasetProxy.setChannelDisplayY(channelDisplayY);
        setChannelDisplayYArray(new int[]{channelDisplayY});
    }
    
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public int getChannelDisplayAtYArray(int channel)
    {
	return defaultXYDatasetProxy.getChannelDisplayAtYArray(channel);
    }    
    
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public int[] getChannelDisplayYArray()
    {
	return defaultXYDatasetProxy.getChannelDisplayYArray();
    }    
        
    /** Setter for property channelDisplayY.
     * @param channelDisplayY New value of property channelDisplayY.
     */
    public void setChannelDisplayYArray(int[] channelDisplayYArray)
    {
	defaultXYDatasetProxy.setChannelDisplayYArray(channelDisplayYArray);
    }    

    /**Deprecated!! Use getUpdateFrequency*/
    public int getUpdatePercentage()
    {
        return getUpdateFrequency();
    }
    
    /**Deprecated!! Use setUpdateFrequency*/
    public void setUpdatePercentage(int updatePercentage)
    {
        //this.updatePercentage = updatePercentage; 
        setUpdateFrequency(updatePercentage);
    }
    
    public int getUpdateFrequency()
    {
        return defaultXYDatasetProxy.getUpdateFrequency();
    }
    
    /**Update from updateFrequency to updateFrequency points*/
    public void setUpdateFrequency(int updateFrequency)
    {   
        defaultXYDatasetProxy.setUpdateFrequency(updateFrequency);
    }
    
    public double getValueX(int ch, double val){
        return val;
    }
    
    public double getValueY(int ch, double val){
        return val;
    }
}
