/*
 * ApparatusTreeCellRenderer.java
 *
 * Created on 08 May 2003, 23:34
 */

package old;

import java.awt.Dimension;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig;
import com.linkare.rec.impl.client.customizer.*;
import com.linkare.rec.impl.client.apparatus.Apparatus;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class LaboratoryApparatusTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer
{
    private javax.swing.ImageIcon labIcon = null;
    private javax.swing.ImageIcon apparatusIcon = null;
    private ReCBaseUIConfig recBaseUIConfig = null;
    
    public LaboratoryApparatusTreeCellRenderer()
    {
	labIcon= new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/LabIcon16_2.gif"));
	apparatusIcon= new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/hcontrol16.gif"));
	setMinimumSize(new Dimension(18,18));
        recBaseUIConfig = ReCBaseUIConfig.sharedInstance();
    }
    
    public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus)
    {
	
	super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
/*        
	if (leaf)
	{
	    com.linkare.rec.impl.client.apparatus.Apparatus app=(com.linkare.rec.impl.client.apparatus.Apparatus)((javax.swing.tree.DefaultMutableTreeNode)value).getUserObject();           
	    
            ICustomizer customizer ;
	    try
	    {
                //Now will be reading the customizer from the xml client! :)
		//ICustomizer customizer = CustomizerUIUtil.loadCustomizer(app.getHardwareInfo().getURLCustomizerClass());
                customizer = CustomizerUIUtil.loadCustomizer(app.getCustomizer());                
		//setIcon(customizer.getCustomizerIcon());
                setIcon(app.getIcon());                
	    }
            catch(Exception e)
	    {
		setIcon(apparatusIcon);
	    }
	                
	    setToolTipText("<html>" + app.getHardwareInfo().getDescriptionText() + "</html>");
	}
	else
	    setIcon(labIcon);
	*/
                
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
