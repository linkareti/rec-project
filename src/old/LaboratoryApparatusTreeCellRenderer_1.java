/*
 * ApparatusTreeCellRenderer.java
 *
 * Created on 08 May 2003, 23:34
 */

package old;

import java.awt.Dimension;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.client.customizer.*;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class LaboratoryApparatusTreeCellRenderer_1 extends javax.swing.tree.DefaultTreeCellRenderer
{
    private javax.swing.ImageIcon labIcon=null;
    private javax.swing.ImageIcon apparatusIcon=null;
    
    public LaboratoryApparatusTreeCellRenderer_1()
    {
	labIcon= new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/LabIcon16_2.gif"));
	apparatusIcon= new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/hcontrol16.gif"));
	setMinimumSize(new Dimension(18,18));
    }
    
    public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus)
    {
	
	super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
	if (leaf)
	{
	    com.linkare.rec.impl.client.apparatus.Apparatus app=(com.linkare.rec.impl.client.apparatus.Apparatus)((javax.swing.tree.DefaultMutableTreeNode)value).getUserObject();
	    
	    try
	    {
		ICustomizer customizer=CustomizerUIUtil.loadCustomizer(app.getHardwareInfo().getURLCustomizerClass());
		setIcon(customizer.getCustomizerIcon());
	    }catch(Exception e)
	    {
		setIcon(apparatusIcon);
	    }
	    
	    setToolTipText("<html>"+app.getHardwareInfo().getDescriptionText()+"</html>");
	}
	else
	    setIcon(labIcon);
	
	setPreferredSize(new Dimension(getPreferredSize().width,18));
	
	return this;
    }
    
    private String toolTipText="";
    
    public void setToolTipText(String text)
    {
	this.toolTipText=text;
    }
    
    public String getToolTipText(java.awt.event.MouseEvent evt)
    {
	return toolTipText;
    }
}
