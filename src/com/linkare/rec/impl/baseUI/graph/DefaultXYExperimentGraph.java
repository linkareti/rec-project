/*
 * DefaultExperimentGraph.java
 *
 * Created on 7 de Maio de 2003, 18:47
 */

package com.linkare.rec.impl.baseUI.graph;

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
 * @author  jp
 */
public class DefaultXYExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener
{
	private static String UI_CLIENT_LOGGER="ReC.baseUI";
    
    
	static
	{
		Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
		}
	}
	
	/** Creates new form DefaultExperimentGraph */
	public DefaultXYExperimentGraph()
	{
		initComponents();
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	private void initComponents()//GEN-BEGIN:initComponents
	{
	    defaultXYDatasetProxy = new com.linkare.rec.impl.client.experiment.DefaultXYDatasetProxy();
	    scrollPane = new javax.swing.JScrollPane();
	    labelWaitData = new javax.swing.JLabel();
	    
	    defaultXYDatasetProxy.setChannelDisplayY(1);
	    
	    setLayout(new java.awt.BorderLayout());
	    
	    labelWaitData.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
	    labelWaitData.setText("waiting for data...");
	    scrollPane.setViewportView(labelWaitData);
	    
	    add(scrollPane, java.awt.BorderLayout.CENTER);
	    
	}//GEN-END:initComponents


	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.rec.impl.client.experiment.DefaultXYDatasetProxy defaultXYDatasetProxy;
	private javax.swing.JScrollPane scrollPane;
	private javax.swing.JLabel labelWaitData;
	// End of variables declaration//GEN-END:variables

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
		return new javax.swing.ImageIcon("/com/linkare/rec/impl/baseUI/resources/chart16.gif");
	}

	private ExpDataModel model;
	public void setExpDataModel(ExpDataModel model)
	{
	    this.model = model;
	    defaultXYDatasetProxy.setExpDataModel(model);
            if(model != null)
                model.addExpDataModelListener(this);
	}

	public String getName()
	{
		return "XY Chart";
	}

	
	public javax.swing.JMenuBar getMenuBar(){ return null;} 
	public javax.swing.JToolBar getToolBar(){ return null;} 
	
	public void dataModelWaiting()
	{//BIG SILENT NOOP
	}
	
	public void dataModelStoped()
	{
            if(header == null && model != null)
                headerAvailable(model.getAcquisitionConfig());
	}
	
	private boolean isScaleSet=false;
	
	private JFreeChart chart =null;
	
	public void newSamples(NewExpDataEvent evt)
	{
	    if(header == null && model != null)
                headerAvailable(model.getAcquisitionConfig());
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
	    defaultXYDatasetProxy.setChannelDisplayY(channelDisplayY);
	}
	
	public void dataModelEnded()
	{
            if(header == null && model != null)
                headerAvailable(model.getAcquisitionConfig());
	}
	
	public void dataModelError()
	{
	}
	
	public void dataModelStarted()
	{
            if(header == null && model != null)
                headerAvailable(model.getAcquisitionConfig());
	}
	
	public void dataModelStartedNoData()
	{
            if(header == null && model != null)
                headerAvailable(model.getAcquisitionConfig());
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
            
            Scale scaleY=header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayY()).getSelectedScale();
            String chnY=header.getChannelsConfig(defaultXYDatasetProxy.getChannelDisplayY()).getChannelName();
            String pusY=scaleY.getPhysicsUnitSymbol();
            String multiplierY=scaleY.getMultiplier().toString();
            
            
            NumberAxis xAxis = new NumberAxis(chnX+" ["+multiplierX+pusX+"]");
            xAxis.setAutoRange(true);
            xAxis.setAutoRangeStickyZero(false);
            xAxis.setAutoRangeIncludesZero(false);
            
            NumberAxis yAxis = new NumberAxis(chnY+" ["+multiplierY+pusY+"]");
            yAxis.setAutoRange(true);
            yAxis.setAutoRangeStickyZero(false);
            yAxis.setAutoRangeIncludesZero(false);
            
            XYPlot plot = new XYPlot(defaultXYDatasetProxy, xAxis, yAxis, new StandardXYItemRenderer(StandardXYItemRenderer.SHAPES_AND_LINES,new StandardXYToolTipGenerator()));
            
            chart = new JFreeChart(header.getFamiliarName(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
            ChartPanel panel=new ChartPanel(chart);
            
            panel.setPreferredSize(new java.awt.Dimension(350,300));
            //panel.setMinimumSize(panel.getPreferredSize());
            //panel.setSize(panel.getPreferredSize());
            
            panel.setMouseZoomable(true,false);
            
            scrollPane.remove(labelWaitData);
	    scrollPane.setViewportView(panel);
        }
	
}
