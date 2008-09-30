/*
 * MultiPanelExperimentGraph.java
 *
 * Created on June 11, 2004, 1:02 PM
 */

package com.linkare.rec.impl.baseUI.graph;

import javax.swing.Icon;

import com.linkare.rec.impl.client.experiment.ExpDataDisplay;
import com.linkare.rec.impl.client.experiment.ExpDataModel;
import com.linkare.rec.impl.client.experiment.ExpDataModelListener;
import com.linkare.rec.impl.client.experiment.NewExpDataEvent;


/**
 *
 * @author André Neto - LEFT - IST
 */
public class MultiPanelExperimentGraph extends javax.swing.JPanel implements ExpDataDisplay, ExpDataModelListener
{
    
    private Object[] expDataList = null;
    private javax.swing.JPanel jPanelContainer;
    private javax.swing.JScrollPane jScrollPane1;
    
    public MultiPanelExperimentGraph(Object[] expDataList) 
    {        
        this.expDataList = expDataList;        
        setLayout(new java.awt.BorderLayout());

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanelContainer = new javax.swing.JPanel();
        
        jPanelContainer.setLayout(new java.awt.GridLayout(expDataList.length, 1));
        for(int i=0; i<expDataList.length; i++)
        {
            jPanelContainer.add((java.awt.Component)expDataList[i]);
        }

        jScrollPane1.setViewportView(jPanelContainer);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);        
    }
     
    
    public void dataModelStoped() 
    {
        for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).dataModelStoped();
        }
    }    
    
    public javax.swing.JComponent getDisplay() 
    {
        return this;
    }
    
    public Icon getIcon() 
    {
        return new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/chart16.gif"));
    }
    
    public javax.swing.JMenuBar getMenuBar() 
    {
        return null;
    }
    
    public javax.swing.JToolBar getToolBar() 
    {
        return null;
    }
        
    public void newSamples(NewExpDataEvent evt) 
    {
        for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).newSamples(evt);
        }
    }
    
    public void setExpDataModel(ExpDataModel model) 
    {
        for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataDisplay)(expDataList[i])).setExpDataModel(model);
        }
    }
    
    public void dataModelEnded()
    {
	for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).dataModelEnded();
        }
    }
    
    public void dataModelError()
    {
	for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).dataModelError();
        }
    }
    
    public void dataModelStarted()
    {
	for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).dataModelStarted();
        }
    }
    
    public void dataModelStartedNoData()
    {
	for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).dataModelStartedNoData();
        }
    }
    
    public void dataModelWaiting()
    {
	for(int i=0; i<expDataList.length; i++)
        {
            ((ExpDataModelListener)(expDataList[i])).dataModelWaiting();
        }
    }
    
}
