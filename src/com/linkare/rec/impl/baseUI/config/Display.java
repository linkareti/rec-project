/**
 *	This generated bean class Display
 *	matches the schema element 'Display'.
 *
 *	Generated on Mon Jan 26 00:03:11 GMT 2004
 */

package com.linkare.rec.impl.baseUI.config;

import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.ValidateException;
import java.beans.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.*;

public class Display extends DisplayNode implements com.linkare.rec.impl.baseUI.config.CommonBean,OrderedItem
{
    private int _Order=Integer.MAX_VALUE;
    private boolean _OfflineCapable = false;
    private boolean _Selected = true;
    private String _DisplayStringBundleKey ="";
    private String _IconLocationBundleKey = "";
    private String _ToolTipBundleKey = "";
    private String _ClassLocationBundleKey="";
    private PropertyChangeSupport eventListeners;
    
    public Display()
    {
    }
    
    // Deep copy
    public Display(com.linkare.rec.impl.baseUI.config.Display source)
    {
	_Order = source._Order;
	_OfflineCapable = source._OfflineCapable;
        _Selected = source._Selected;
	_DisplayStringBundleKey = source._DisplayStringBundleKey;
	_IconLocationBundleKey = source._IconLocationBundleKey;
	_ToolTipBundleKey = source._ToolTipBundleKey;
	_ClassLocationBundleKey = source._ClassLocationBundleKey;
	eventListeners = source.eventListeners;
    }
    
    // This attribute is mandatory
    public void setOrder(int value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "order",  new Integer(getOrder()), new Integer(value));
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
    public void setOfflineCapable(boolean value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "offlineCapable", new Boolean(getOfflineCapable()), new Boolean(value));
	}
	_OfflineCapable = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public boolean getOfflineCapable()
    {
	return _OfflineCapable;
    }
    
    // This attribute is mandatory
    public void setSelected(boolean value)
    {
        super.setSelected(value);
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "selected", new Boolean(getSelected()), new Boolean(value));
	}
	_Selected = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public boolean getSelected()
    {
	return _Selected;
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
    public void setToolTipBundleKey(String value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "toolTipBundleKey", getToolTipBundleKey(), value);
	}
	_ToolTipBundleKey = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public String getToolTipBundleKey()
    {
	return _ToolTipBundleKey;
    }
    
    // This attribute is mandatory
    public void setClassLocationBundleKey(String value)
    {
	PropertyChangeEvent event = null;
	if (eventListeners != null )
	{
	    event = new PropertyChangeEvent(this, "classLocationBundleKey", getClassLocationBundleKey(), value);
	}
	_ClassLocationBundleKey = value;
	if (eventListeners != null)
	    eventListeners.firePropertyChange(event);
    }
    
    public String getClassLocationBundleKey()
    {
	return _ClassLocationBundleKey;
    }
    
    
    public void writeNode(Writer out, String nodeName, String indent) throws IOException
    {
	out.write(indent);
	out.write("<");
	out.write(nodeName);
	// order is an attribute
	out.write(" order");	// NOI18N
	out.write("='");	// NOI18N
	com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, Integer.toString(_Order,10), true);
	out.write("'");	// NOI18N
	
	// offlineCapable is an attribute
	out.write(" offlineCapable");	// NOI18N
	out.write("='");	// NOI18N
	com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, BooleanXMLParser.toString(_OfflineCapable), true);
	out.write("'");	// NOI18N
        
        // selected is an attribute
	out.write(" selected");	// NOI18N
	out.write("='");	// NOI18N
	com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, BooleanXMLParser.toString(_Selected), true);
	out.write("'");	// NOI18N

	// displayStringBundleKey is an attribute
	if (_DisplayStringBundleKey != null)
	{
	    out.write(" displayStringBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _DisplayStringBundleKey, true);
	    out.write("'");	// NOI18N
	}
	// iconLocationBundleKey is an attribute
	if (_IconLocationBundleKey != null)
	{
	    out.write(" iconLocationBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _IconLocationBundleKey, true);
	    out.write("'");	// NOI18N
	}
	// toolTipBundleKey is an attribute
	if (_ToolTipBundleKey != null)
	{
	    out.write(" toolTipBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _ToolTipBundleKey, true);
	    out.write("'");	// NOI18N
	}
	// classLocationBundleKey is an attribute
	if (_ClassLocationBundleKey != null)
	{
	    out.write(" classLocationBundleKey");	// NOI18N
	    out.write("='");	// NOI18N
	    com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _ClassLocationBundleKey, true);
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
	    attr = (Attr) attrs.getNamedItem("offlineCapable");
	    if (attr != null)
	    {
		_OfflineCapable = BooleanXMLParser.parseBoolean(attr.getValue());
	    }
            attr = (Attr) attrs.getNamedItem("selected");
	    if (attr != null)
	    {
		_Selected = BooleanXMLParser.parseBoolean(attr.getValue());
                super.setSelected(_Selected);
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
	    attr = (Attr) attrs.getNamedItem("toolTipBundleKey");
	    if (attr != null)
	    {
		_ToolTipBundleKey = attr.getValue();
	    }
	    attr = (Attr) attrs.getNamedItem("classLocationBundleKey");
	    if (attr != null)
	    {
		_ClassLocationBundleKey = attr.getValue();
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
	// Validating property order
	
	// Validating property displayStringBundleKey
	if (getDisplayStringBundleKey() == null)
	{
	    throw new ValidateException("getDisplayStringBundleKey() == null", "displayStringBundleKey", this);	// NOI18N
	}
	// Validating property iconLocationBundleKey
	if (getIconLocationBundleKey() == null)
	{
	    throw new ValidateException("getIconLocationBundleKey() == null", "iconLocationBundleKey", this);	// NOI18N
	}
	// Validating property toolTipBundleKey
	if (getToolTipBundleKey() == null)
	{
	    throw new ValidateException("getToolTipBundleKey() == null", "toolTipBundleKey", this);	// NOI18N
	}
	// Validating property classLocationBundleKey
	if (getClassLocationBundleKey() == null)
	{
	    throw new ValidateException("getClassLocationBundleKey() == null", "classLocationBundleKey", this);	// NOI18N
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
    
    public void changePropertyByName(String name, Object value)
    {
	if (name == null) return;
	name = name.intern();
	if (name.equals("order"))
	    setOrder(((Integer)value).intValue());
	else if (name.equals("offlineCapable"))
	    setOfflineCapable(((Boolean)value).booleanValue());
	else if (name.equals("displayStringBundleKey"))
	    setDisplayStringBundleKey((String)value);
	else if (name.equals("iconLocationBundleKey"))
	    setIconLocationBundleKey((String)value);
	else if (name.equals("toolTipBundleKey"))
	    setToolTipBundleKey((String)value);
	else if (name.equals("classLocationBundleKey"))
	    setClassLocationBundleKey((String)value);
	else
	    throw new IllegalArgumentException(name+" is not a valid property name for Display");
    }
    
    public Object fetchPropertyByName(String name)
    {
	if (name.equals("order"))
	    return new Integer(getOrder());
	if (name.equals("offlineCapable"))
	    return new Boolean(getOfflineCapable());
	if (name.equals("displayStringBundleKey"))
	    return getDisplayStringBundleKey();
	if (name.equals("iconLocationBundleKey"))
	    return getIconLocationBundleKey();
	if (name.equals("toolTipBundleKey"))
	    return getToolTipBundleKey();
	if (name.equals("classLocationBundleKey"))
	    return getClassLocationBundleKey();
	throw new IllegalArgumentException(name+" is not a valid property name for Display");
    }
    
    // Return an array of all of the properties that are beans and are set.
    public com.linkare.rec.impl.baseUI.config.CommonBean[] childBeans(boolean recursive)
    {
	List<CommonBean> children = new LinkedList<CommonBean>();
	childBeans(recursive, children);
	com.linkare.rec.impl.baseUI.config.CommonBean[] result = new com.linkare.rec.impl.baseUI.config.CommonBean[children.size()];
	return (com.linkare.rec.impl.baseUI.config.CommonBean[]) children.toArray(result);
    }
    
    // Put all child beans into the beans list.
    public void childBeans(boolean recursive, List beans)
    {
    }
    
    public boolean equals(Object o)
    {
	if (o == this)
	    return true;
	if (!(o instanceof com.linkare.rec.impl.baseUI.config.Display))
	    return false;
	com.linkare.rec.impl.baseUI.config.Display inst = (com.linkare.rec.impl.baseUI.config.Display) o;
	if (_Order != inst._Order)
	    return false;
	if (_OfflineCapable !=  inst._OfflineCapable )
	    return false;
        if (_Selected !=  inst._Selected )
	    return false;
	if (!(_DisplayStringBundleKey == null ? inst._DisplayStringBundleKey == null : _DisplayStringBundleKey.equals(inst._DisplayStringBundleKey)))
	    return false;
	if (!(_IconLocationBundleKey == null ? inst._IconLocationBundleKey == null : _IconLocationBundleKey.equals(inst._IconLocationBundleKey)))
	    return false;
	if (!(_ToolTipBundleKey == null ? inst._ToolTipBundleKey == null : _ToolTipBundleKey.equals(inst._ToolTipBundleKey)))
	    return false;
	if (!(_ClassLocationBundleKey == null ? inst._ClassLocationBundleKey == null : _ClassLocationBundleKey.equals(inst._ClassLocationBundleKey)))
	    return false;
	return true;
    }
    
    public int hashCode()
    {
	int result = 17;
	result = 37*result + _Order;
	result = 37*result + (_OfflineCapable ? 0 : 1);
        result = 37*result + (_Selected ? 0 : 1);
	result = 37*result + (_DisplayStringBundleKey == null ? 0 : _DisplayStringBundleKey.hashCode());
	result = 37*result + (_IconLocationBundleKey == null ? 0 : _IconLocationBundleKey.hashCode());
	result = 37*result + (_ToolTipBundleKey == null ? 0 : _ToolTipBundleKey.hashCode());
	result = 37*result + (_ClassLocationBundleKey == null ? 0 : _ClassLocationBundleKey.hashCode());
	return result;
    }
    
    public String getDisplayNameBundleKey()
    {
	return getDisplayStringBundleKey();
    }
    
    public String toString()
    {
	StringWriter sw = new StringWriter();
	try
	{
	    writeNode(sw, "Display", "");
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
