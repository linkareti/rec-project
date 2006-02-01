/*
 * WebResourceCollection.java
 *
 * Created on 1 de Julho de 2003, 17:07
 */

package old.configOld;

import javax.swing.*;
/**
 *
 * @author  jp
 */
public class LabConfigCollection extends java.util.Vector
{
    private String description="Available Labotarories";
    private String tooltip="Available Labotarories";
    private String iconURL="/com/linkare/rec/impl/baseUI/resources/earth16.gif";
    
    public LabConfigCollection()
    {
	super();
    }
    /** Creates a new instance of WebResourceCollection */
    public LabConfigCollection(String description, String iconURL, String tooltip)
    {
	super();
	this.description=description;
	this.iconURL=iconURL;
	this.tooltip=tooltip;
    }
    
    
/*    public JMenu getJMenu()
    {
	JMenu menu=new JMenu(description);
	try
	{
	    menu.setIcon((new javax.swing.ImageIcon(getClass().getResource(iconURL))));
	}catch(Exception e){}
	
	menu.setToolTipText(tooltip);
	
	Object[] oResources=toArray();
	java.util.Arrays.sort(oResources);
	
	for(int i=0;i<oResources.length;i++)
	{
	    try
	    {
		menu.add( ((WebResource)oResources[i]).getJMenuItem() );
	    }
	    catch(Exception e)
	    {
	    }
	}
	
	
	return menu;
    }
*/

    
    public LabConfig getLabConfig(int i)
    {
	return (LabConfig)this.get(i);
    }
}
