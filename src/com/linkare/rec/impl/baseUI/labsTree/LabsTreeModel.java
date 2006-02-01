/*
 * LabsTreeModel.java
 *
 * Created on 22 de Janeiro de 2004, 1:08
 */

package com.linkare.rec.impl.baseUI.labsTree;

import javax.swing.event.*;
import javax.swing.tree.*;
import com.linkare.rec.impl.baseUI.config.*;

import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
/**
 *
 * @author  jp
 */
public class LabsTreeModel extends DefaultTreeModel
{
    /** Utility field used by event firing mechanism. */
    private EventListenerList listenerList =  null;
    
    //The tree root, static so it can be accessed by outside classes
    public static DefaultMutableTreeNode treeRoot = null;
    
    /** Creates a new instance of LabsTreeModel */
    public LabsTreeModel(Object userObject, boolean allowsChildren)
    {
        super(new DefaultMutableTreeNode(userObject), true);
    }
    
    public void populateTree(ReCBaseUIConfig config)
    {        
        DefaultMutableTreeNode root = (DefaultMutableTreeNode)getRoot();
        treeRoot = root;
        DefaultMutableTreeNode labroot = (DefaultMutableTreeNode)getRoot();
        DefaultMutableTreeNode approot = (DefaultMutableTreeNode)getRoot();
        DefaultMutableTreeNode node = null;
        WebResource[] wr = config.getWebResource();
        
        for(int a = 0; a < wr.length; a++)
        {
            wr[a].setEnabled(true);
            node = new DefaultMutableTreeNode(wr[a]);
            node.setAllowsChildren(false);
            root.add(node);
        }
        
        Lab[] labs = config.getLab();
        for(int i = 0; i < labs.length; i++)
        {
            labroot = new DefaultMutableTreeNode(labs[i]);
            root.add(labroot);
            addPropChangeListener(labs[i]);
            
            wr = labs[i].getWebResource();
            for(int k = 0; k < wr.length; k++)
            {
                wr[k].setEnabled(true);
                labroot.add(new DefaultMutableTreeNode(wr[k]));
            }
            
            Apparatus[] apparatus = labs[i].getApparatus();
            for(int j = 0; j < apparatus.length; j++)
            {
                addPropChangeListener(apparatus[j]);
                
                approot = new DefaultMutableTreeNode(apparatus[j]);
                labroot.add(approot);
                
                wr = apparatus[j].getWebResource();
                for(int r = 0; r < wr.length; r++)
                {
                    wr[r].setEnabled(true);
                    node = new DefaultMutableTreeNode(wr[r]);
                    node.setAllowsChildren(false);
                    approot.add(node);
                }
                
                DefaultAcquisitionConfig [] configs = apparatus[j].getDefaultAcquisitionConfig();
                for(int c = 0; c < configs.length; c++)
                {
                    addPropChangeListener(configs[c]);
                    node = new DefaultMutableTreeNode(configs[c]);
                    node.setAllowsChildren(false);
                    approot.add(node);
                }
                
                
                Display[] displays = apparatus[j].getDisplay();
                for(int d = 0; d < displays.length; d++)
                {
                    if(displays[d].getOfflineCapable())
                        displays[d].setEnabled(true);
                    addPropChangeListener(displays[d]);
                    node = new DefaultMutableTreeNode(displays[d]);
                    node.setAllowsChildren(false);
                    approot.add(node);
                }
            }
        }
        
        reload();
    }
    
    public Apparatus getApparatus(String uniqueID)
    {
        java.util.Enumeration allChild = ((DefaultMutableTreeNode)root).breadthFirstEnumeration();
        while(allChild.hasMoreElements())
        {
            Object currentNode = ((DefaultMutableTreeNode)allChild.nextElement()).getUserObject();
            if(currentNode instanceof Apparatus)
            {
                if(((Apparatus)currentNode).getLocation().equals(uniqueID))
                {
                    return (Apparatus)currentNode;
                }
            }
        }
        return null;
    }
    
    public void addPropChangeListener(DisplayNode node)
    {
        //needed to change the default add method name, because, lab, apparatus, etc. were overriding the addPropertyChageListener
        node.addDisplayNodePropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                displayNodePropertyChanged(evt);
            }
        });
        
    }
    
    private void displayNodePropertyChanged(java.beans.PropertyChangeEvent evt)
    {
        if(evt.getPropertyName().equals("enable"))
            firePropertyChangeListenerPropertyChange(new java.beans.PropertyChangeEvent(this, "reload", Boolean.TRUE, Boolean.TRUE));
    }
    
    /**
     * Registers PropertyChangeListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        if (listenerList == null )
        {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(java.beans.PropertyChangeListener.class, listener);
    }
    
    /**
     * Removes PropertyChangeListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        listenerList.remove(java.beans.PropertyChangeListener.class, listener);
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void firePropertyChangeListenerPropertyChange(java.beans.PropertyChangeEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==java.beans.PropertyChangeListener.class)
            {
                ((java.beans.PropertyChangeListener)listeners[i+1]).propertyChange(event);
            }
        }
    }
    
}
