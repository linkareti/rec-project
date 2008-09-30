/*
 * LaboratoryHardwareTree.java
 *
 * Created on 08 May 2003, 23:02
 */

package com.linkare.rec.impl.baseUI.labsTree;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.linkare.rec.impl.baseUI.config.Apparatus;
import com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig;
import com.linkare.rec.impl.baseUI.config.Display;
import com.linkare.rec.impl.baseUI.config.DisplayNode;
import com.linkare.rec.impl.baseUI.config.Lab;
import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
import com.linkare.rec.impl.baseUI.config.WebResource;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public class LaboratoryTree extends javax.swing.JPanel
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
    private DisplayNodeTreeCellRenderer renderer;
    private LabsTreeModel model;
    private LabsTreeCellEditor realEditor;
    
    /** Creates new form LaboratoryHardwareTree */
    public LaboratoryTree()
    {
        model = new LabsTreeModel("", true);
        
        //Use this to properly reload the tree when needed!
        model.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                updateTree();
            }
        });
        renderer = new DisplayNodeTreeCellRenderer();
        
        initComponents();
        
        realEditor = new LabsTreeCellEditor();
        
        labTree.setCellEditor(realEditor);
        
        labTree.setEditable(true);
        
        ToolTipManager.sharedInstance().registerComponent(labTree);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        jScrollPane1 = new javax.swing.JScrollPane();
        labTree = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        labTree.setCellRenderer(renderer);
        labTree.setFocusCycleRoot(true);
        labTree.setModel(model);
        labTree.setScrollsOnExpand(false);
        labTree.setShowsRootHandles(true);
        labTree.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                labTreeMousePressed(evt);
            }
        });
        labTree.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                labTreePropertyChange(evt);
            }
        });

        jScrollPane1.setViewportView(labTree);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }//GEN-END:initComponents

    private void labTreePropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_labTreePropertyChange
    {//GEN-HEADEREND:event_labTreePropertyChange
        //it came from the editor, it was the only way I found to detect two clicks events in the editor...
        if(evt.getPropertyName().equals("ddc"))
        {
            TreePath selPath = labTree.getPathForRow(((Integer)evt.getNewValue()).intValue());
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
            Object o = currentNode.getUserObject();
            
            Lab l = getParentLab(currentNode);
            Apparatus app = getParentApparatus(currentNode);            
            fireTreeSelectionChangeListenerDisplaySelectionChange(new DisplaySelectionEvent(this, (Display)o, app, l));
        }
    }//GEN-LAST:event_labTreePropertyChange
    
    private void labTreeMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labTreeMousePressed
    {//GEN-HEADEREND:event_labTreeMousePressed
        if(evt.getClickCount() == 2)
        {
            int selRow = labTree.getRowForLocation(evt.getX(), evt.getY());
            TreePath selPath = labTree.getPathForLocation(evt.getX(), evt.getY());
            
            if(selRow < 0)
            {
                return;
            }
            
            DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
            Object o = currentNode.getUserObject();
            
            Lab l = getParentLab(currentNode);
            Apparatus app = getParentApparatus(currentNode);
            
            if(o instanceof Lab)
            {
                fireTreeSelectionChangeListenerLabSelectionChange(new LabSelectionEvent(this, (Lab)o));
            }
            else if(o instanceof Apparatus)
            {
                fireTreeSelectionChangeListenerApparatusSelectionChange(new ApparatusSelectionEvent(this, (Apparatus)o, l));
            }
            else if(o instanceof WebResource)
            {
                fireTreeSelectionChangeListenerWebResourceSelectionChange(new WebResourceSelectionEvent(this, (WebResource)o, app, l));
            }
            else if(o instanceof DefaultAcquisitionConfig)
            {
                fireTreeSelectionChangeListenerDefaultConfigSelectionChange(new DefaultConfigSelectionEvent(this, (DefaultAcquisitionConfig)o, app, l));
            }
            else if(o instanceof Display)
            {
                fireTreeSelectionChangeListenerDisplaySelectionChange(new DisplaySelectionEvent(this, (Display)o, app, l));
            }
        }
    }//GEN-LAST:event_labTreeMousePressed
    
    private Apparatus getParentApparatus(DefaultMutableTreeNode node)
    {
        Object o = null;
        
        while(node.getParent() != null)
        {
            o = node.getUserObject();
            if(o instanceof Apparatus)
            {
                return (Apparatus)o;
            }
            if(node.getParent() != null)
                node = (DefaultMutableTreeNode)node.getParent();
            else
                return null;
        }
        
        return null;
    }
    
    private Lab getParentLab(DefaultMutableTreeNode node)
    {
        Object o = null;
        
        while(node.getParent() != null)
        {
            o = node.getUserObject();
            if(o instanceof Lab)
            {
                return (Lab)o;
            }
            if(node.getParent() != null)
                node = (DefaultMutableTreeNode)node.getParent();
            else
                return null;
        }
        return null;
    }
    
    /**
     * Registers TreeSelectionChangeListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addTreeSelectionChangeListener(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener listener)
    {
        if (listenerList == null )
        {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class, listener);
    }
    
    /**
     * Removes TreeSelectionChangeListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeTreeSelectionChangeListener(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener listener)
    {
        listenerList.remove(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class, listener);
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerApparatusSelectionChange(com.linkare.rec.impl.baseUI.labsTree.ApparatusSelectionEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).apparatusSelectionChange(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerDefaultConfigSelectionChange(com.linkare.rec.impl.baseUI.labsTree.DefaultConfigSelectionEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).defaultConfigSelectionChange(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerDisplaySelectionChange(com.linkare.rec.impl.baseUI.labsTree.DisplaySelectionEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).displaySelectionChange(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerWebResourceSelectionChange(com.linkare.rec.impl.baseUI.labsTree.WebResourceSelectionEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).webResourceSelectionChange(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireTreeSelectionChangeListenerLabSelectionChange(com.linkare.rec.impl.baseUI.labsTree.LabSelectionEvent event)
    {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2)
        {
            if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
            {
                ((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).labSelectionChange(event);
            }
        }
    }
    
    public void populateTree(ReCBaseUIConfig config)
    {
        model.populateTree(config);
    }
    
    public LabsTreeModel getModel()
    {
        return model;
    }
    
    public Apparatus getApparatus(String uniqueID)
    {
        return model.getApparatus(uniqueID);
    }
    
    public DisplayNode getCurrentSelectedNode()
    {
        if(labTree.getSelectionPath() == null)
            return null;
        
        Object selected = labTree.getSelectionPath().getLastPathComponent();
        if(selected instanceof DefaultMutableTreeNode)
            if(((DefaultMutableTreeNode)selected).getUserObject() instanceof DisplayNode)
                return (DisplayNode)((DefaultMutableTreeNode)selected).getUserObject();
        
        return null;
    }
    
    
    public void updateTree()
    {
        if(!labTree.isVisible() || model == null)
            return;
        
        synchronized(this)
        {            
            labTree.repaint();
        }
        /*java.util.ArrayList expandedPaths = getExpandedPaths();
        model.reload();
        expandPaths(expandedPaths);*/
    }
    
    /**
     * Records the list of currently expanded paths in the specified tree.
     * This method is meant to be called before calling the
     * <code>reload()</code> methods to allow the tree to store the paths.
     *
     * @param  tree      the tree
     * @param  pathlist  the list of expanded paths
     */
    private java.util.ArrayList getExpandedPaths()
    {
        java.util.ArrayList<TreePath> expandedPaths = new java.util.ArrayList<TreePath>();
        addExpandedPaths(labTree.getPathForRow(0), expandedPaths);
        return expandedPaths;
    }
    
    /**
     * Adds the expanded descendants of the specifed path in the specified
     * tree to the internal expanded list.
     *
     * @param  tree      the tree
     * @param  path      the path
     * @param  pathlist  the list of expanded paths
     */
    private void addExpandedPaths(javax.swing.tree.TreePath path, java.util.ArrayList<TreePath> pathlist)
    {
        java.util.Enumeration enume = labTree.getExpandedDescendants(path);
        if(enume == null)
            return;
        while(enume.hasMoreElements())
        {
            TreePath tp = (TreePath)enume.nextElement();
            pathlist.add(tp);
            addExpandedPaths(tp, pathlist);
        }
    }
    
    /**
     * Re-expands the expanded paths in the specified tree.  This method is
     * meant to be called before calling the <code>reload()</code> methods
     * to allow the tree to store the paths.
     *
     * @param  tree      the tree
     * @param  pathlist  the list of expanded paths
     */
    private void expandPaths(java.util.ArrayList pathlist)
    {
        for(int i = 0; i < pathlist.size(); i++)
        {
            labTree.expandPath((TreePath)pathlist.get(i));
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree labTree;
    // End of variables declaration//GEN-END:variables
    
    /**
     * Utility field used by event firing mechanism.
     */
    private javax.swing.event.EventListenerList listenerList =  null;
    
}
