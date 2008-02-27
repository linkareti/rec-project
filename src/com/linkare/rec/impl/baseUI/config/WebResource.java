/**
 *	This generated bean class WebResource
 *	matches the schema element 'WebResource'.
 *
 *	Generated on Mon Jan 26 00:03:11 GMT 2004
 */

package com.linkare.rec.impl.baseUI.config;

import java.beans.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class WebResource extends DisplayNode  implements CommonBean, OrderedItem
{
    private int _Order = Integer.MAX_VALUE;
    private boolean _InternalBrowser = false;
    private String _ToolTipLocationBundleKey = "";
    private String _DisplayStringBundleKey;
    private String _IconLocationBundleKey = "";
    private String _LocationBundleKey;
    private PropertyChangeSupport eventListeners;
    
    public WebResource()
    {
    }
    
    // Deep copy
    public WebResource(WebResource source)
    {
	this.setOrder(source.getOrder());
	_InternalBrowser = source._InternalBrowser;
	_ToolTipLocationBundleKey = source._ToolTipLocationBundleKey;
	_DisplayStringBundleKey = source._DisplayStringBundleKey;
	_IconLocationBundleKey = source._IconLocationBundleKey;
	_LocationBundleKey = source._LocationBundleKey;
	eventListeners = source.eventListeners;
    }
    
    // This attribute is mandatory
    public void setOrder(int value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "order", new Integer(getOrder()), new Integer(value));
	}
	_Order = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public int getOrder()
    {
	return _Order;
    }
    
    // This attribute is mandatory
    public void setInternalBrowser(boolean value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "internalBrowser", new Boolean(isInternalBrowser()), new Boolean(value));
	}
	_InternalBrowser = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public boolean isInternalBrowser()
    {
	return _InternalBrowser;
    }

    
    // This attribute is mandatory
    public void setToolTipLocationBundleKey(String value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "toolTipLocationBundleKey", getToolTipLocationBundleKey(), value);
	}
	_ToolTipLocationBundleKey = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public String getToolTipLocationBundleKey()
    {
	return _ToolTipLocationBundleKey;
    }
    
    // This attribute is mandatory
    public void setDisplayStringBundleKey(String value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "displayStringBundleKey", getDisplayStringBundleKey(), value);
	}
	_DisplayStringBundleKey = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public String getDisplayStringBundleKey()
    {
	return _DisplayStringBundleKey;
    }
    
    // This attribute is mandatory
    public void setIconLocationBundleKey(String value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "iconLocationBundleKey", getIconLocationBundleKey(), value);
	}
	_IconLocationBundleKey = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public String getIconLocationBundleKey()
    {
	return _IconLocationBundleKey;
    }

    
    // This attribute is mandatory
    public void setLocationBundleKey(String value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "locationBundleKey", getLocationBundleKey(), value);
	}
	_LocationBundleKey = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public String getLocationBundleKey()
    {
	return _LocationBundleKey;
    }

    
    public void writeNode(Writer out, String nodeName, String indent) throws IOException
    {
	out.write(indent);
	out.write("<");
	out.write(nodeName);
	// order is an attribute
	out.write(" order");	// NOI18N
	out.write("='");	// NOI18N
	out.write(_Order);	// NOI18N
	out.write("'");	// NOI18N
	
	out.write(" internalBrowser");	// NOI18N
	out.write("='");	// NOI18N
	out.write(BooleanXMLParser.toString(_InternalBrowser));	// NOI18N
	out.write("'");	// NOI18N
	
	// toolTipLocationBundleKey is an attribute
	if (_ToolTipLocationBundleKey != null)
	{
	    out.write(" toolTipLocationBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    ReCBaseUIConfig.writeXML(out, _ToolTipLocationBundleKey, true);
	    out.write("'");	// NOI18N
	}
	// displayStringBundleKey is an attribute
	if (_DisplayStringBundleKey != null)
	{
	    out.write(" displayStringBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    ReCBaseUIConfig.writeXML(out, _DisplayStringBundleKey, true);
	    out.write("'");	// NOI18N
	}
	// iconLocationBundleKey is an attribute
	if (_IconLocationBundleKey != null)
	{
	    out.write(" iconLocationBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    ReCBaseUIConfig.writeXML(out, _IconLocationBundleKey, true);
	    out.write("'");	// NOI18N
	}
	// locationBundleKey is an attribute
	if (_LocationBundleKey != null)
	{
	    out.write(" locationBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    ReCBaseUIConfig.writeXML(out, _LocationBundleKey, true);
	    out.write("'");	// NOI18N
	}
	out.write(">\n");
	String nextIndent = indent + "	";
	out.write(indent);
	out.write("</"+nodeName+">\n");
    }
    
    public void readNode(Node node)
    {
	if (node.hasAttributes())
	{
	    NamedNodeMap attrs = node.getAttributes();
	    Attr attr;
	    attr = (Attr) attrs.getNamedItem("order");
	    if (attr != null)
	    {
		_Order = Integer.parseInt(attr.getValue());
	    }
	    attr = (Attr) attrs.getNamedItem("internalBrowser");
	    if (attr != null)
	    {
		_InternalBrowser = BooleanXMLParser.parseBoolean(attr.getValue());
	    }
	    attr = (Attr) attrs.getNamedItem("toolTipLocationBundleKey");
	    if (attr != null)
	    {
		_ToolTipLocationBundleKey = attr.getValue();
	    }
	    attr = (Attr) attrs.getNamedItem("displayStringBundleKey");
	    if (attr != null)
	    {
		_DisplayStringBundleKey = attr.getValue();
	    }
	    attr = (Attr) attrs.getNamedItem("iconLocationBundleKey");
	    if (attr != null)
	    {
		_IconLocationBundleKey = attr.getValue();
	    }
	    attr = (Attr) attrs.getNamedItem("locationBundleKey");
	    if (attr != null)
	    {
		_LocationBundleKey = attr.getValue();
	    }
	}
	NodeList children = node.getChildNodes();
	for (int i = 0, size = children.getLength(); i < size; ++i)
	{
	    Node childNode = children.item(i);
	    String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode.getLocalName().intern());
	    String childNodeValue = "";
	    if (childNode.getFirstChild() != null)
	    {
		childNodeValue = childNode.getFirstChild().getNodeValue();
	    }
	}
    }
    
    public void validate() throws ReCBaseUIConfig.ValidateException
    {
	boolean restrictionFailure = false;
	
        /*if (getToolTipLocationBundleKey() == null)
	{
	    throw new ReCBaseUIConfig.ValidateException("getToolTipLocationBundleKey() == null", "toolTipLocationBundleKey", this);	// NOI18N
	}*/
	// Validating property displayStringBundleKey
	if (getDisplayStringBundleKey() == null)
	{
	    throw new ReCBaseUIConfig.ValidateException("getDisplayStringBundleKey() == null", "displayStringBundleKey", this);	// NOI18N
	}
	// Validating property iconLocationBundleKey
	if (getIconLocationBundleKey() == null)
	{
	    throw new ReCBaseUIConfig.ValidateException("getIconLocationBundleKey() == null", "iconLocationBundleKey", this);	// NOI18N
	}
	// Validating property locationBundleKey
	if (getLocationBundleKey() == null)
	{
	    throw new ReCBaseUIConfig.ValidateException("getLocationBundleKey() == null", "locationBundleKey", this);	// NOI18N
	}
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
	if (eventListeners == null)
	{
	    eventListeners = new PropertyChangeSupport(this);
	}
	eventListeners.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
	if (eventListeners == null)
	{
	    return;
	}
	eventListeners.removePropertyChangeListener(listener);
	if (!eventListeners.hasListeners(null))
	{
	    eventListeners = null;
	}
    }
    
    public void _setPropertyChangeSupport(PropertyChangeSupport listeners)
    {
	eventListeners = listeners;
    }
    
    // Return an array of all of the properties that are beans and are set.
    public CommonBean[] childBeans(boolean recursive)
    {
	List<CommonBean> children = new LinkedList<CommonBean>();
	childBeans(recursive, children);
	CommonBean[] result = new CommonBean[children.size()];
	return (CommonBean[]) children.toArray(result);
    }
    
    // Put all child beans into the beans list.
    public void childBeans(boolean recursive, List beans)
    {
    }
    
    public boolean equals(Object o)
    {
	if (o == this)
	    return true;
	if (!(o instanceof WebResource))
	    return false;
	WebResource inst = (WebResource) o;
        //Order an InternalBrowser can't be null..
        
	if (_Order != inst._Order)
	    return false;
	if (_InternalBrowser != inst._InternalBrowser)
	    return false;
	if (!(_ToolTipLocationBundleKey == null ? inst._ToolTipLocationBundleKey == null : _ToolTipLocationBundleKey.equals(inst._ToolTipLocationBundleKey)))
	    return false;
	if (!(_DisplayStringBundleKey == null ? inst._DisplayStringBundleKey == null : _DisplayStringBundleKey.equals(inst._DisplayStringBundleKey)))
	    return false;
	if (!(_IconLocationBundleKey == null ? inst._IconLocationBundleKey == null : _IconLocationBundleKey.equals(inst._IconLocationBundleKey)))
	    return false;
	if (!(_LocationBundleKey == null ? inst._LocationBundleKey == null : _LocationBundleKey.equals(inst._LocationBundleKey)))
	    return false;
	return true;
    }
    
    public int hashCode()
    {
	int result = 17;
	result = 37*result + _Order;
	result = 37*result + (_InternalBrowser ? 0 : 1);
	result = 37*result + (_ToolTipLocationBundleKey == null ? 0 : _ToolTipLocationBundleKey.hashCode());
	result = 37*result + (_DisplayStringBundleKey == null ? 0 : _DisplayStringBundleKey.hashCode());
	result = 37*result + (_IconLocationBundleKey == null ? 0 : _IconLocationBundleKey.hashCode());
	result = 37*result + (_LocationBundleKey == null ? 0 : _LocationBundleKey.hashCode());
	return result;
    }
    
    public java.net.URL getURL() throws java.net.MalformedURLException
    {
	try
	{
	    return new java.net.URL(ReCResourceBundle.findStringOrDefault(getLocationBundleKey(), "http://www.e-escola.pt"));
	}
	catch(java.net.MalformedURLException e)
	{
	    throw e;
	}
    }
    
    public String getDisplayNameBundleKey() 
    {
        return getDisplayStringBundleKey();
    }
    
    public String getToolTipBundleKey() 
    {
        return getToolTipLocationBundleKey();
    }    
    
    public String toString()
    {
	StringWriter sw = new StringWriter();
	try
	{
	    writeNode(sw, "WebResource", "");
	} catch (IOException e)
	{
	    // How can we actually get an IOException on a StringWriter?
	    // We'll just ignore it.
	}
	return sw.toString();
    }        
}


/*
		The following schema file has been used for generation:
 
<?xml version='1.0' encoding='UTF-8'?>
 
 
 
<!ELEMENT DefaultAcquisitionConfig>
<!ATTLIST DefaultAcquisitionConfig
    order CDATA #REQUIRED
    displayStringBundleKey CDATA #REQUIRED
    iconLocationBundleKey CDATA ""
    toolTipBundleKey CDATA ""
    classLocationBundleKey CDATA #REQUIRED
  >
 
<!ELEMENT Display>
<!ATTLIST Display
    order CDATA #REQUIRED
    offlineCapable (true|false|0|1|yes|no) "false"
    displayStringBundleKey CDATA #REQUIRED
    iconLocationBundleKey CDATA ""
    toolTipBundleKey CDATA ""
    classLocationBundleKey CDATA #REQUIRED
  >
 
<!ELEMENT Apparatus (DefaultAcquisitionConfig|Display|WebResource|LocalizationBundle)*>
<!ATTLIST Apparatus
    order CDATA #REQUIRED
    displayStringBundleKey CDATA #REQUIRED
    iconLocationBundleKey CDATA ""
    toolTipBundleKey CDATA ""
    dataModelClassLocationBundleKey CDATA ""
    videoLocation CDATA ""
    headerDisplayClassLocationBundleKey CDATA ""
    location CDATA #REQUIRED
  >
 
<!ELEMENT Lab (Apparatus|WebResource|LocalizationBundle)*>
<!ATTLIST Lab
    order CDATA #REQUIRED
    iconLocationBundleKey CDATA ""
    toolTipBundleKey CDATA ""
    displayStringBundleKey CDATA #REQUIRED
    location CDATA #REQUIRED
  >
 
<!ELEMENT WebResource>
<!ATTLIST WebResource
    order CDATA #REQUIRED
    internalBrowser (true|false|0|1|yes|no) "false"
    toolTipLocationBundleKey CDATA ""
    displayStringBundleKey CDATA #REQUIRED
    iconLocationBundleKey CDATA ""
    locationBundleKey CDATA #REQUIRED
  >
 
<!ELEMENT LocalizationBundle>
<!ATTLIST LocalizationBundle
    location CDATA #REQUIRED
    name CDATA #REQUIRED
  >
 
<!ELEMENT ReCBaseUIConfig (Lab|WebResource|LocalizationBundle)*>
<!ATTLIST ReCBaseUIConfig
    aboutPageLocationBundleKey CDATA ""
    autoConnectLab (true|false|0|1|yes|no) "false"
    showVideoFrame (true|false|0|1|yes|no) "false"
    enableVideoFrame (true|false|0|1|yes|no) "true"
    showChatFrame (true|false|0|1|yes|no) "false"
    enableChatFrame (true|false|0|1|yes|no) "true"
    showUserList (true|false|0|1|yes|no) "false"
    enableUsersList (true|false|0|1|yes|no) "true"
    usersListRefreshRateMs CDATA "2000"
    enterApparatusChatRoom (true|false|0|1|yes|no) "false"
    iconLocationBundleKey CDATA ""
    frameTitleBundleKey CDATA #REQUIRED
    iconSponsorLocationBundleKey CDATA ""
    helpPageLocationBundleKey CDATA ""
  >
 
 */
