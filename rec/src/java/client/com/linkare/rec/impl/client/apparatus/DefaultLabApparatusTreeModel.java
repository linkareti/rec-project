/*
 * DefaultLabApparatusTreeModel.java
 *
 * Created on 08 May 2003, 23:18
 */

package com.linkare.rec.impl.client.apparatus;

import javax.swing.tree.DefaultMutableTreeNode;
/**
 *
 * @author  Jose Pedro Pereira
 */
public class DefaultLabApparatusTreeModel extends javax.swing.tree.DefaultTreeModel implements com.linkare.rec.impl.client.apparatus.ApparatusListSourceListener
{
    private static final DefaultMutableTreeNode rootNode=new DefaultMutableTreeNode("Laboratory", true);
    
    /** Holds value of property apparatusListSource. */
    private ApparatusListSource apparatusListSource;
    
    /** Creates a new instance of DefaultLabApparatusTreeModel */
    public DefaultLabApparatusTreeModel()
    {
	super(rootNode);
    }
    
    /** Getter for property apparatusListSource.
     * @return Value of property apparatusListSource.
     */
    public ApparatusListSource getApparatusListSource()
    {
	return this.apparatusListSource;
    }
    
    /** Setter for property apparatusListSource.
     * @param apparatusListSource New value of property apparatusListSource.
     */
    public void setApparatusListSource(ApparatusListSource apparatusListSource)
    {
	if(this.apparatusListSource!=null)
	{
	    this.apparatusListSource.removeApparatusListSourceListener(this);
	}
	this.apparatusListSource = apparatusListSource;
	if(apparatusListSource!=null)
	{
	    apparatusListSource.addApparatusListSourceListener(this);
	}
    }
    
    public void apparatusListChanged(ApparatusListChangeEvent newApparatusListEvt)
    {
	rootNode.removeAllChildren();
	
//	this.reload();
	
	if(newApparatusListEvt==null) return;
	
	Apparatus[] newApparatusList=newApparatusListEvt.getApparatus();
	
	if(newApparatusList==null) return;
	
	for(int i=0;i<newApparatusList.length;i++)
	{
	    Apparatus apparatus=newApparatusList[i];
	    if(apparatus!=null)
	    {
		DefaultMutableTreeNode node=new DefaultMutableTreeNode(apparatus,false);
		rootNode.add(node);
	    }
	}
	
	this.reload();
    }
    
}
