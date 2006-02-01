/*
 * LaboratoryHardwareTree.java
 *
 * Created on 08 May 2003, 23:02
 */

package old;

import javax.swing.tree.*;
import com.linkare.rec.impl.baseUI.config.*;
import com.linkare.rec.impl.baseUI.labsTree.*;;


/**
 *
 * @author  Jos� Pedro Pereira
 */
public class LaboratoryApparatusTree extends javax.swing.JPanel
{
	
	/** Creates new form LaboratoryHardwareTree */
	public LaboratoryApparatusTree()
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
	    laboratoryApparatusTreeCellRenderer = new com.linkare.rec.impl.baseUI.LaboratoryApparatusTreeCellRenderer();
	    jScrollPane1 = new javax.swing.JScrollPane();
	    labTree = new javax.swing.JTree();
	    
	    
	    setLayout(new java.awt.BorderLayout());
	    
	    labTree.setCellRenderer(laboratoryApparatusTreeCellRenderer);
	    labTree.setFocusCycleRoot(true);
	    labTree.setScrollsOnExpand(false);
	    labTree.setShowsRootHandles(true);
	    labTree.addMouseListener(new java.awt.event.MouseAdapter()
	    {
		public void mousePressed(java.awt.event.MouseEvent evt)
		{
		    labTreeMousePressed(evt);
		}
	    });
	    
	    jScrollPane1.setViewportView(labTree);
	    
	    add(jScrollPane1, java.awt.BorderLayout.CENTER);
	    
	}//GEN-END:initComponents
	
	private void labTreeMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_labTreeMousePressed
	{//GEN-HEADEREND:event_labTreeMousePressed
            if(evt.getClickCount() == 2)
            {
                int selRow = labTree.getRowForLocation(evt.getX(), evt.getY());
                TreePath selPath = labTree.getPathForLocation(evt.getX(), evt.getY());
                if(selRow != -1)
                {
                    DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                    Object o = (currentNode).getUserObject();

                    Lab l = getParentLab(currentNode);                                
                    Apparatus app = getParentApparatus(currentNode);                                
                    
                    if(o instanceof Lab)
                    {
                        fireSelectionChangeListenerApparatusSelectionChange(new LabSelectionEvent((Lab)o));                        
                    }
                    else if(o instanceof Apparatus)
                    {
                        fireSelectionChangeListenerApparatusSelectionChange(new ApparatusSelectionEvent((Apparatus)o, l));
                    }
                    else if(o instanceof WebResource)
                    {
                        fireSelectionChangeListenerApparatusSelectionChange(new WebResourceSelectionEvent((WebResource)o, app, l));
                    }
                    else if(o instanceof DefaultAcquisitionConfig)
                    {
                        fireSelectionChangeListenerApparatusSelectionChange(new DefaultConfigSelectionEvent((DefaultAcquisitionConfig)o, app, l));
                    }
                    else if(o instanceof Display)
                    {
                        fireSelectionChangeListenerApparatusSelectionChange(new DisplaySelectionEvent((Display)o, app, l));
                    }
                }
            }
	}//GEN-LAST:event_labTreeMousePressed
		
	/** Getter for property model.
	 * @return Value of property model.
	 */
	public TreeModel getModel()
	{
		return this.labTree.getModel();
	}
	
	/** Setter for property model.
	 * @param model New value of property model.
	 */
	public void setModel(TreeModel model)
	{
		this.labTree.setModel(model);
	}
	
	/** Registers ApparatusSelectionChangeListener to receive events.
	 * @param listener The listener to register.
	 */
	public synchronized void addApparatusSelectionChangeListener(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener listener)
	{
		if (listenerList == null )
		{
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class, listener);
	}
	
	/** Removes ApparatusSelectionChangeListener from the list of listeners.
	 * @param listener The listener to remove.
	 */
	public synchronized void removeApparatusSelectionChangeListener(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener listener)
	{
		listenerList.remove(com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class, listener);
	}
	
	/** Notifies all registered listeners about the event.
	 *
	 * @param event The event to be fired
	 */
	private void fireSelectionChangeListenerApparatusSelectionChange(Object event)
	{
		if (listenerList == null) return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length-2; i>=0; i-=2)
		{
			if (listeners[i]==com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener.class)
			{
                            if(event instanceof ApparatusSelectionEvent)
                            {
				((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).apparatusSelectionChange((ApparatusSelectionEvent)event);
                            }
                            else if(event instanceof LabSelectionEvent)
                            {
				((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).labSelectionChange((LabSelectionEvent)event);
                            }
                            else if(event instanceof WebResourceSelectionEvent)
                            {
				((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).webResourceSelectionChange((WebResourceSelectionEvent)event);
                            }
                            else if(event instanceof DefaultConfigSelectionEvent)
                            {
				((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).defaultConfigSelectionChange((DefaultConfigSelectionEvent)event);
                            }
                            else if(event instanceof DisplaySelectionEvent)
                            {
				((com.linkare.rec.impl.baseUI.labsTree.TreeSelectionChangeListener)listeners[i+1]).displaySelectionChange((DisplaySelectionEvent)event);
                            }
			}
		}
	}
        
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
            }
            
            return null;
        }
	
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTree labTree;
	private javax.swing.JScrollPane jScrollPane1;
	private com.linkare.rec.impl.baseUI.LaboratoryApparatusTreeCellRenderer laboratoryApparatusTreeCellRenderer;
	// End of variables declaration//GEN-END:variables
	
	/** Utility field used by event firing mechanism. */
	private javax.swing.event.EventListenerList listenerList =  null;
	
}
