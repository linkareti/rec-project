/**
 *	This generated bean class LocalizationBundle
 *	matches the schema element 'LocalizationBundle'.
 *
 *	Generated on Tue Jan 27 02:51:16 GMT 2004
 */

package com.linkare.rec.impl.baseUI.config;

import java.util.LinkedList;
import java.util.List;

public class LocalizationBundle implements com.linkare.rec.impl.baseUI.config.CommonBean {
	private java.lang.String _Location;
	private java.lang.String _Name;
	private java.beans.PropertyChangeSupport eventListeners;

	public LocalizationBundle() {
		_Location = "";
		_Name = "";
	}

	// Deep copy
	public LocalizationBundle(com.linkare.rec.impl.baseUI.config.LocalizationBundle source) {
		_Location = source._Location;
		_Name = source._Name;
		eventListeners = source.eventListeners;
	}

	// This attribute is mandatory
	public void setLocation(java.lang.String value) {
		java.beans.PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new java.beans.PropertyChangeEvent(this, "location", getLocation(), value);
		}
		_Location = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public java.lang.String getLocation() {
		return _Location;
	}

	// This attribute is mandatory
	public void setName(java.lang.String value) {
		java.beans.PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new java.beans.PropertyChangeEvent(this, "name", getName(), value);
		}
		_Name = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public java.lang.String getName() {
		return _Name;
	}

	public void writeNode(java.io.Writer out, String nodeName, String indent) throws java.io.IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// location is an attribute
		if (_Location != null) {
			out.write(" location"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _Location, true);
			out.write("'"); // NOI18N
		}
		// name is an attribute
		if (_Name != null) {
			out.write(" name"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _Name, true);
			out.write("'"); // NOI18N
		}
		out.write(">\n");
		String nextIndent = indent + "	";
		out.write(indent);
		out.write("</" + nodeName + ">\n");
	}

	public void readNode(org.w3c.dom.Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			org.w3c.dom.Attr attr;
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("location");
			if (attr != null) {
				_Location = attr.getValue();
			}
			attr = (org.w3c.dom.Attr) attrs.getNamedItem("name");
			if (attr != null) {
				_Name = attr.getValue();
			}
		}
		org.w3c.dom.NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			org.w3c.dom.Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode
					.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
		}
	}

	public void validate() throws com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.ValidateException {
		boolean restrictionFailure = false;
		// Validating property location
		if (getLocation() == null) {
			throw new com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.ValidateException("getLocation() == null",
					"location", this); // NOI18N
		}
		// Validating property name
		if (getName() == null) {
			throw new com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.ValidateException("getName() == null", "name",
					this); // NOI18N
		}
	}

	public void addPropertyChangeListener(java.beans.PropertyChangeListener listener) {
		if (eventListeners == null) {
			eventListeners = new java.beans.PropertyChangeSupport(this);
		}
		eventListeners.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(java.beans.PropertyChangeListener listener) {
		if (eventListeners == null) {
			return;
		}
		eventListeners.removePropertyChangeListener(listener);
		if (!eventListeners.hasListeners(null)) {
			eventListeners = null;
		}
	}

	public void _setPropertyChangeSupport(java.beans.PropertyChangeSupport listeners) {
		eventListeners = listeners;
	}

	public void changePropertyByName(String name, Object value) {
		if (name == null)
			return;
		name = name.intern();
		if (name.equals("location"))
			setLocation((java.lang.String) value);
		else if (name.equals("name"))
			setName((java.lang.String) value);
		else
			throw new IllegalArgumentException(name + " is not a valid property name for LocalizationBundle");
	}

	public Object fetchPropertyByName(String name) {
		if (name.equals("location"))
			return getLocation();
		if (name.equals("name"))
			return getName();
		throw new IllegalArgumentException(name + " is not a valid property name for LocalizationBundle");
	}

	// Return an array of all of the properties that are beans and are set.
	public CommonBean[] childBeans(boolean recursive) {
		List<CommonBean> children = new LinkedList<CommonBean>();
		childBeans(recursive, children);
		com.linkare.rec.impl.baseUI.config.CommonBean[] result = new com.linkare.rec.impl.baseUI.config.CommonBean[children
				.size()];
		return (com.linkare.rec.impl.baseUI.config.CommonBean[]) children.toArray(result);
	}

	// Put all child beans into the beans list.
	public void childBeans(boolean recursive, java.util.List beans) {
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof com.linkare.rec.impl.baseUI.config.LocalizationBundle))
			return false;
		com.linkare.rec.impl.baseUI.config.LocalizationBundle inst = (com.linkare.rec.impl.baseUI.config.LocalizationBundle) o;
		if (!(_Location == null ? inst._Location == null : _Location.equals(inst._Location)))
			return false;
		if (!(_Name == null ? inst._Name == null : _Name.equals(inst._Name)))
			return false;
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (_Location == null ? 0 : _Location.hashCode());
		result = 37 * result + (_Name == null ? 0 : _Name.hashCode());
		return result;
	}

	public String toString() {
		java.io.StringWriter sw = new java.io.StringWriter();
		try {
			writeNode(sw, "LocalizationBundle", "");
		} catch (java.io.IOException e) {
			// How can we actually get an IOException on a StringWriter?
			// We'll just ignore it.
		}
		return sw.toString();
	}
}

/*
 * The following schema file has been used for generation:
 * 
 * <?xml version='1.0' encoding='UTF-8'?>
 * 
 * 
 * 
 * <!ELEMENT DefaultAcquisitionConfig> <!ATTLIST DefaultAcquisitionConfig order
 * CDATA #REQUIRED displayStringBundleKey CDATA #REQUIRED iconLocationBundleKey
 * CDATA "" toolTipBundleKey CDATA "" classLocationBundleKey CDATA #REQUIRED >
 * 
 * <!ELEMENT Display> <!ATTLIST Display order CDATA #REQUIRED offlineCapable
 * (true|false|0|1|yes|no) "false" displayStringBundleKey CDATA #REQUIRED
 * iconLocationBundleKey CDATA "" toolTipBundleKey CDATA ""
 * classLocationBundleKey CDATA #REQUIRED >
 * 
 * <!ELEMENT Apparatus
 * (DefaultAcquisitionConfig|Display|WebResource|LocalizationBundle)*> <!ATTLIST
 * Apparatus order CDATA #REQUIRED displayStringBundleKey CDATA #REQUIRED
 * iconLocationBundleKey CDATA "" toolTipBundleKey CDATA ""
 * dataModelClassLocationBundleKey CDATA "" videoLocation CDATA ""
 * headerDisplayClassLocationBundleKey CDATA "" location CDATA #REQUIRED >
 * 
 * <!ELEMENT Lab (Apparatus|WebResource|LocalizationBundle)*> <!ATTLIST Lab
 * order CDATA #REQUIRED iconLocationBundleKey CDATA "" toolTipBundleKey CDATA
 * "" displayStringBundleKey CDATA #REQUIRED location CDATA #REQUIRED >
 * 
 * <!ELEMENT WebResource> <!ATTLIST WebResource order CDATA #REQUIRED
 * internalBrowser (true|false|0|1|yes|no) "false" toolTipLocationBundleKey
 * CDATA "" displayStringBundleKey CDATA #REQUIRED iconLocationBundleKey CDATA
 * "" locationBundleKey CDATA #REQUIRED >
 * 
 * <!ELEMENT LocalizationBundle> <!ATTLIST LocalizationBundle location CDATA
 * #REQUIRED name CDATA #REQUIRED >
 * 
 * <!ELEMENT ReCBaseUIConfig (Lab|WebResource|LocalizationBundle)*> <!ATTLIST
 * ReCBaseUIConfig aboutPageLocationBundleKey CDATA "" autoConnectLab
 * (true|false|0|1|yes|no) "false" showVideoFrame (true|false|0|1|yes|no)
 * "false" enableVideoFrame (true|false|0|1|yes|no) "true" showChatFrame
 * (true|false|0|1|yes|no) "false" enableChatFrame (true|false|0|1|yes|no)
 * "true" showUserList (true|false|0|1|yes|no) "false" enableUsersList
 * (true|false|0|1|yes|no) "true" usersListRefreshRateMs CDATA "2000"
 * enterApparatusChatRoom (true|false|0|1|yes|no) "false" iconLocationBundleKey
 * CDATA "" frameTitleBundleKey CDATA #REQUIRED iconSponsorLocationBundleKey
 * CDATA "" helpPageLocationBundleKey CDATA "" >
 */
