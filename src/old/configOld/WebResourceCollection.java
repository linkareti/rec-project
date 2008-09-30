/*
 * WebResourceCollection.java
 *
 * Created on 1 de Julho de 2003, 17:07
 */

package old.configOld;

import javax.swing.*;
import com.linkare.rec.impl.protocols.*;
/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class WebResourceCollection extends java.util.Vector
{
    private String description="Web Resources";
    private String tooltip="Web Resources";
    private String iconURL="/com/linkare/rec/impl/baseUI/resources/earth16.gif";
    
    public WebResourceCollection()
    {
	super();
    }
    /** Creates a new instance of WebResourceCollection */
    public WebResourceCollection(String description,String iconURL,String tooltip)
    {
	super();
	this.description=description;
	this.iconURL=iconURL;
	this.tooltip=tooltip;
    }
    
    
    public JMenu getJMenu()
    {
	if(this.size()==0) return null;
	
	JMenu menu=new JMenu(description);
	try
	{
	    menu.setIcon((new javax.swing.ImageIcon(ReCProtocols.getURL(iconURL))));
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
    
    /** Getter for property description.
     * @return Value of property description.
     */
    public String getDescription()
    {
	return this.description;
    }
    
    /** Setter for property description.
     * @param description New value of property description.
     */
    public void setDescription(String description)
    {
	this.description = description;
    }
    
    /** Getter for property tooltip.
     * @return Value of property tooltip.
     */
    public String getTooltip()
    {
	return this.tooltip;
    }
    
    /** Setter for property tooltip.
     * @param tooltip New value of property tooltip.
     */
    public void setTooltip(String tooltip)
    {
	this.tooltip = tooltip;
    }
    
    /** Getter for property iconURL.
     * @return Value of property iconURL.
     */
    public String getIconURL()
    {
	return this.iconURL;
    }
    
    /** Setter for property iconURL.
     * @param iconURL New value of property iconURL.
     */
    public void setIconURL(String iconURL)
    {
	this.iconURL = iconURL;
    }
    
}
