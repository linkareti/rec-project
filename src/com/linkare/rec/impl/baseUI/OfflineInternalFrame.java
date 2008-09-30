/*
 * OfflineInternalFrame.java
 *
 * Created on August 3, 2004, 12:37 PM
 */

package com.linkare.rec.impl.baseUI;

/**
 *
 * @author André Neto - LEFT - IST
 */

import java.util.ArrayList;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.Icon;
import javax.swing.JComponent;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.logging.LoggerUtil;

public class OfflineInternalFrame extends javax.swing.JInternalFrame
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
    
    /**
     * Holds value of property hardwareID.
     */
    private String hardwareID;
    
    private ArrayList<ExpDataDisplay> dataDisplayList = null;
    
    private static final String DISPLAY_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.display", "Display");	
    
    /** Creates new form OfflineInternalFrame */
    public OfflineInternalFrame()
    {
	initComponents();	
    }
    
    /** Creates new form OfflineInternalFrame */
    public OfflineInternalFrame(String hardwareID)
    {
	initComponents();	
	this.hardwareID = hardwareID;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jTabbedPaneOffline = new javax.swing.JTabbedPane();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        jTabbedPaneOffline.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPaneOffline.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        getContentPane().add(jTabbedPaneOffline, java.awt.BorderLayout.CENTER);

        pack();
    }//GEN-END:initComponents

    public synchronized void addOfflineDisplay(ExpDataDisplay dataDisplay)
    {
	if (dataDisplayList == null )
	{
	    dataDisplayList = new ArrayList<ExpDataDisplay>();
	}
		
	dataDisplayList.add(dataDisplay);

	try
	{
	    final Icon icon=dataDisplay.getIcon();
	    String nameTab=dataDisplay.getName();
	    if(nameTab==null) nameTab = DISPLAY_STR;
		int i=1;

	    if(jTabbedPaneOffline.indexOfTab(nameTab)!=-1)
	    {
		while(jTabbedPaneOffline.indexOfTab(nameTab+" "+i)!=-1)
		    ++i;

		nameTab+=" " + i;
	    }

	    JComponent displayComp=dataDisplay.getDisplay();
	    TabClosablePanel panel = new TabClosablePanel();
	    panel.addPropertyChangeListener(new java.beans.PropertyChangeListener()
	    {
		public void propertyChange(java.beans.PropertyChangeEvent evt)
		{
		    tabClosed(evt);
		}
	    }
	    );
	    
	    panel.add(displayComp, java.awt.BorderLayout.CENTER);
	    
	    String nameTabFinal = nameTab;	    	    
	    jTabbedPaneOffline.addTab(nameTabFinal, icon, panel);
	}
	catch(Exception e)
	{
	    LoggerUtil.logThrowable("Couldn't add DataDisplay Component " + dataDisplay + " to ExperimentInternalFrame!",e,Logger.getLogger(UI_CLIENT_LOGGER));
	}
    }
    
    public synchronized void removeExpDataDisplay(ExpDataDisplay dataDisplay)
    {
	if (dataDisplayList != null )
	{
	    dataDisplayList.remove(dataDisplay);
	}

	if(dataDisplay.getDisplay()!=null)
	{
	    for(int i = 0; i<jTabbedPaneOffline.getComponentCount(); i++)
	    {
		if(jTabbedPaneOffline.getComponent(i) instanceof TabClosablePanel)
		{
		    TabClosablePanel tab = (TabClosablePanel)jTabbedPaneOffline.getComponent(i);
		    for(int j=0; j<tab.getComponentCount(); j++)
		    {
			if(tab.getComponent(j) == dataDisplay)
			    jTabbedPaneOffline.remove(tab);
		    }
		}
	    }
	}
    }
    
    public void tabClosed(java.beans.PropertyChangeEvent evt)
    {
	jTabbedPaneOffline.remove((TabClosablePanel)evt.getSource());
    }
    
    /**
     * Getter for property hardwareID.
     * @return Value of property hardwareID.
     */
    public String getHardwareID()
    {
	return this.hardwareID;
    }    
        
    /**
     * Setter for property hardwareID.
     * @param hardwareID New value of property hardwareID.
     */
    public void setHardwareID(String hardwareID)
    {
	this.hardwareID = hardwareID;
    }    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane jTabbedPaneOffline;
    // End of variables declaration//GEN-END:variables
    
}
