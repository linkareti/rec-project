/*
 * WebResource.java
 *
 * Created on 22 de Maio de 2003, 16:50
 */

package old.configOld;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;
import javax.jnlp.*;
import com.linkare.rec.impl.protocols.ReCProtocols;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class WebResource implements Comparable,ActionListener
{
    
    /** Holds value of property tooltip. */
    private String tooltip;
    
    /** Holds value of property displayString. */
    private String displayString;
    
    /** Holds value of property href. */
    private String url;
    
    /** Holds value of property icon. */
    private String iconURL="recresource:///com/linkare/rec/impl/baseUI/resources/webpage16.gif";
    
    /** Holds value of property order. */
    private int order;
    
    private javax.swing.JMenuItem menuItem = null;
    
    /** Creates a new instance of WebResource */
    public WebResource()
    {
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
    
    /** Getter for property displayString.
     * @return Value of property displayString.
     */
    public String getDisplayString()
    {
	return this.displayString;
    }
    
    /** Setter for property displayString.
     * @param displayString New value of property displayString.
     */
    public void setDisplayString(String displayString)
    {
	this.displayString = displayString;
    }
    
    /** Getter for property href.
     * @return Value of property href.
     */
    public String getURL()
    {
	return this.url;
    }
    
    /** Setter for property href.
     * @param href New value of property href.
     */
    public void setURL(String url)
    {
	this.url = url;
    }
    
    /** Getter for property icon.
     * @return Value of property icon.
     */
    public String getIconURL()
    {
	return this.iconURL;
    }
    
    /** Setter for property icon.
     * @param icon New value of property icon.
     */
    public void setIconURL(String iconURL)
    {
	this.iconURL= iconURL;
    }
    
    /** Getter for property order.
     * @return Value of property order.
     */
    public int getOrder()
    {
	return this.order;
    }
    
    /** Setter for property order.
     * @param order New value of property order.
     */
    public void setOrder(int order)
    {
	this.order = order;
    }
    
    public int compareTo(Object o)
    {
	WebResource other=(WebResource)o;
	
	return (getOrder()-other.getOrder());
    }
    
    public JMenuItem getJMenuItem()
    {
	if(menuItem!=null) return menuItem;
	
	
	menuItem=new JMenuItem(getDisplayString());
	try
	{
	    menuItem.setIcon(new ImageIcon(ReCProtocols.getURL(iconURL)));
	}catch(Exception e)
	{
	    
	}
	
	if(tooltip!=null)
	    menuItem.setToolTipText(tooltip);
	else
	    menuItem.setToolTipText(displayString);
	
	
	menuItem.addActionListener(this);
	
	return menuItem;
    }
    
    /** Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e)
    {
	if(e.getSource()==menuItem)
	{
	    try
	    {
		URL url=ReCProtocols.getURL(getURL());
		BasicService bs=(BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
		bs.showDocument(url);
	    }catch(Exception exc)
	    {
		exc.printStackTrace();
	    }
	}
    }    
    
}
