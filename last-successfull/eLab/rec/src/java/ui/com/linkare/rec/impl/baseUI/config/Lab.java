/**
 *	This generated bean class Lab
 *	matches the schema element 'Lab'.
 *
 *	Generated on Mon Jan 26 00:03:11 GMT 2004
 */

package com.linkare.rec.impl.baseUI.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.w3c.dom.Attr;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.ValidateException;
import com.linkare.rec.impl.i18n.ReCResourceBundle;

public class Lab extends DisplayNode implements com.linkare.rec.impl.baseUI.config.CommonBean, OrderedItem {
	private int _Order = Integer.MAX_VALUE;
	private String _IconLocationBundleKey = "";
	private String _ToolTipBundleKey = "";
	private String _DisplayStringBundleKey = "";
	private String _Location = "";
	private String _DesktopLocationBundleKey = "";
	private final List<Apparatus> _Apparatus = new ArrayList<Apparatus>(); // List<Apparatus>
	private final List<WebResource> _WebResource = new ArrayList<WebResource>(); // List<WebResource>
	private final List<LocalizationBundle> _LocalizationBundle = new ArrayList<LocalizationBundle>(); // List<LocalizationBundle>
	private PropertyChangeSupport eventListeners;

	public Lab() {
	}

	// Deep copy
	public Lab(final com.linkare.rec.impl.baseUI.config.Lab source) {
		_Order = source._Order;
		_IconLocationBundleKey = source._IconLocationBundleKey;
		_ToolTipBundleKey = source._ToolTipBundleKey;
		_DisplayStringBundleKey = source._DisplayStringBundleKey;
		_DesktopLocationBundleKey = source._DesktopLocationBundleKey;
		_Location = source._Location;
		for (final Object element : source._Apparatus) {
			_Apparatus.add(new com.linkare.rec.impl.baseUI.config.Apparatus(
					(com.linkare.rec.impl.baseUI.config.Apparatus) element));
		}
		for (final Object element : source._WebResource) {
			_WebResource.add(new com.linkare.rec.impl.baseUI.config.WebResource(
					(com.linkare.rec.impl.baseUI.config.WebResource) element));
		}
		for (final Object element : source._LocalizationBundle) {
			_LocalizationBundle.add(new com.linkare.rec.impl.baseUI.config.LocalizationBundle(
					(com.linkare.rec.impl.baseUI.config.LocalizationBundle) element));
		}
		eventListeners = source.eventListeners;
	}

	// This attribute is mandatory
	@Override
	public void setOrder(final int value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "order", Integer.valueOf(getOrder()), Integer.valueOf(value));
		}
		_Order = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	// It was returning a String...changed to int and added get_Order() (this
	// one returns string)
	@Override
	public int getOrder() {
		return _Order;
	}

	// This attribute is mandatory
	public void setIconLocationBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "iconLocationBundleKey", getIconLocationBundleKey(), value);
		}
		_IconLocationBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	@Override
	public String getIconLocationBundleKey() {
		return _IconLocationBundleKey;
	}

	public void setDesktopLocationBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "desktopLocationBundleKey", getIconLocationBundleKey(), value);
		}
		_DesktopLocationBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getDesktopLocationBundleKey() {
		return _DesktopLocationBundleKey;
	}

	// This attribute is mandatory
	public void setToolTipBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "toolTipBundleKey", getToolTipBundleKey(), value);
		}
		_ToolTipBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	@Override
	public String getToolTipBundleKey() {
		return _ToolTipBundleKey;
	}

	// This attribute is mandatory
	public void setDisplayStringBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "displayStringBundleKey", getDisplayStringBundleKey(), value);
		}
		_DisplayStringBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getDisplayStringBundleKey() {
		return _DisplayStringBundleKey;
	}

	// This attribute is mandatory
	public void setLocation(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "location", getLocation(), value);
		}
		_Location = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getLocation() {
		return _Location;
	}

	// This attribute is an array, possibly empty
	public void setApparatus(com.linkare.rec.impl.baseUI.config.Apparatus[] value) {
		if (value == null) {
			value = new Apparatus[0];
		}
		// Make the foreign beans take on our property change event listeners.
		for (int i = 0; i < value.length; ++i) {
			if (value[i] != null) {
				value[i]._setPropertyChangeSupport(eventListeners);
			}
		}
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "apparatus", getApparatus(), value);
		}
		_Apparatus.clear();
		for (int i = 0; i < value.length; ++i) {
			_Apparatus.add(value[i]);
		}
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public void setApparatus(final int index, final com.linkare.rec.impl.baseUI.config.Apparatus value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "apparatus.i" + index,
					_Apparatus.get(index), value);
			eventListeners.firePropertyChange(event);
		}
		_Apparatus.set(index, value);
	}

	public com.linkare.rec.impl.baseUI.config.Apparatus[] getApparatus() {
		final Apparatus[] arr = new Apparatus[_Apparatus.size()];
		return _Apparatus.toArray(arr);
	}

	public List<Apparatus> fetchApparatusList() {
		return _Apparatus;
	}

	public com.linkare.rec.impl.baseUI.config.Apparatus getApparatus(final int index) {
		return _Apparatus.get(index);
	}

	// Return the number of apparatus
	public int sizeApparatus() {
		return _Apparatus.size();
	}

	public int addApparatus(final com.linkare.rec.impl.baseUI.config.Apparatus value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_Apparatus.add(value);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "apparatus.i" + (_Apparatus.size() - 1),
					null, value);
			eventListeners.firePropertyChange(event);
		}
		return _Apparatus.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeApparatus(final com.linkare.rec.impl.baseUI.config.Apparatus value) {
		final int pos = _Apparatus.indexOf(value);
		if (pos >= 0) {
			_Apparatus.remove(pos);
			if (eventListeners != null) {
				final PropertyChangeEvent event = new PropertyChangeEvent(this, "apparatus.i" + pos, value, null);
				eventListeners.firePropertyChange(event);
			}
		}
		return pos;
	}

	// This attribute is an array, possibly empty
	public void setWebResource(com.linkare.rec.impl.baseUI.config.WebResource[] value) {
		if (value == null) {
			value = new WebResource[0];
		}
		// Make the foreign beans take on our property change event listeners.
		for (int i = 0; i < value.length; ++i) {
			if (value[i] != null) {
				value[i]._setPropertyChangeSupport(eventListeners);
			}
		}
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "webResource", getWebResource(), value);
		}
		_WebResource.clear();
		for (int i = 0; i < value.length; ++i) {
			_WebResource.add(value[i]);
		}
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public void setWebResource(final int index, final com.linkare.rec.impl.baseUI.config.WebResource value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "webResource.i" + index,
					_WebResource.get(index), value);
			eventListeners.firePropertyChange(event);
		}
		_WebResource.set(index, value);
	}

	public com.linkare.rec.impl.baseUI.config.WebResource[] getWebResource() {
		final WebResource[] arr = new WebResource[_WebResource.size()];
		return _WebResource.toArray(arr);
	}

	public List<WebResource> fetchWebResourceList() {
		return _WebResource;
	}

	public com.linkare.rec.impl.baseUI.config.WebResource getWebResource(final int index) {
		return _WebResource.get(index);
	}

	// Return the number of webResource
	public int sizeWebResource() {
		return _WebResource.size();
	}

	public int addWebResource(final com.linkare.rec.impl.baseUI.config.WebResource value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_WebResource.add(value);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this,
					"webResource.i" + (_WebResource.size() - 1), null, value);
			eventListeners.firePropertyChange(event);
		}
		return _WebResource.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeWebResource(final com.linkare.rec.impl.baseUI.config.WebResource value) {
		final int pos = _WebResource.indexOf(value);
		if (pos >= 0) {
			_WebResource.remove(pos);
			if (eventListeners != null) {
				final PropertyChangeEvent event = new PropertyChangeEvent(this, "webResource.i" + pos, value, null);
				eventListeners.firePropertyChange(event);
			}
		}
		return pos;
	}

	// This attribute is an array, possibly empty
	public void setLocalizationBundle(com.linkare.rec.impl.baseUI.config.LocalizationBundle[] value) {
		if (value == null) {
			value = new LocalizationBundle[0];
		}
		// Make the foreign beans take on our property change event listeners.
		for (int i = 0; i < value.length; ++i) {
			if (value[i] != null) {
				value[i]._setPropertyChangeSupport(eventListeners);
			}
		}
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "localizationBundle", getLocalizationBundle(), value);
		}
		_LocalizationBundle.clear();
		for (int i = 0; i < value.length; ++i) {
			_LocalizationBundle.add(value[i]);
		}
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public void setLocalizationBundle(final int index, final com.linkare.rec.impl.baseUI.config.LocalizationBundle value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "localizationBundle.i" + index,
					_LocalizationBundle.get(index), value);
			eventListeners.firePropertyChange(event);
		}
		_LocalizationBundle.set(index, value);
	}

	public com.linkare.rec.impl.baseUI.config.LocalizationBundle[] getLocalizationBundle() {
		final LocalizationBundle[] arr = new LocalizationBundle[_LocalizationBundle.size()];
		return _LocalizationBundle.toArray(arr);
	}

	public List<LocalizationBundle> fetchLocalizationBundleList() {
		return _LocalizationBundle;
	}

	public com.linkare.rec.impl.baseUI.config.LocalizationBundle getLocalizationBundle(final int index) {
		return _LocalizationBundle.get(index);
	}

	// Return the number of localizationBundle
	public int sizeLocalizationBundle() {
		return _LocalizationBundle.size();
	}

	public int addLocalizationBundle(final com.linkare.rec.impl.baseUI.config.LocalizationBundle value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_LocalizationBundle.add(value);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "localizationBundle.i"
					+ (_LocalizationBundle.size() - 1), null, value);
			eventListeners.firePropertyChange(event);
		}
		return _LocalizationBundle.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeLocalizationBundle(final com.linkare.rec.impl.baseUI.config.LocalizationBundle value) {
		final int pos = _LocalizationBundle.indexOf(value);
		if (pos >= 0) {
			_LocalizationBundle.remove(pos);
			if (eventListeners != null) {
				final PropertyChangeEvent event = new PropertyChangeEvent(this, "localizationBundle.i" + pos, value,
						null);
				eventListeners.firePropertyChange(event);
			}
		}
		return pos;
	}

	@Override
	public void writeNode(final Writer out, final String nodeName, final String indent) throws IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// order is an attribute

		out.write(" order"); // NOI18N
		out.write("='"); // NOI18N
		com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, Integer.toString(_Order, 10), true);
		out.write("'"); // NOI18N

		// iconLocationBundleKey is an attribute
		if (_IconLocationBundleKey != null) {
			out.write(" iconLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _IconLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// desktopLocationBundleKey is an attribute
		if (_DesktopLocationBundleKey != null) {
			out.write(" desktopLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _DesktopLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// toolTipBundleKey is an attribute
		if (_ToolTipBundleKey != null) {
			out.write(" toolTipBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _ToolTipBundleKey, true);
			out.write("'"); // NOI18N
		}
		// displayStringBundleKey is an attribute
		if (_DisplayStringBundleKey != null) {
			out.write(" displayStringBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _DisplayStringBundleKey, true);
			out.write("'"); // NOI18N
		}
		// location is an attribute
		if (_Location != null) {
			out.write(" location"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _Location, true);
			out.write("'"); // NOI18N
		}
		out.write(">\n");
		final String nextIndent = indent + "	";
		for (final Object element2 : _Apparatus) {
			final com.linkare.rec.impl.baseUI.config.Apparatus element = (com.linkare.rec.impl.baseUI.config.Apparatus) element2;
			if (element != null) {
				element.writeNode(out, "Apparatus", nextIndent);
			}
		}
		for (final Object element2 : _WebResource) {
			final com.linkare.rec.impl.baseUI.config.WebResource element = (com.linkare.rec.impl.baseUI.config.WebResource) element2;
			if (element != null) {
				element.writeNode(out, "WebResource", nextIndent);
			}
		}
		for (final Object element2 : _LocalizationBundle) {
			final com.linkare.rec.impl.baseUI.config.LocalizationBundle element = (com.linkare.rec.impl.baseUI.config.LocalizationBundle) element2;
			if (element != null) {
				element.writeNode(out, "LocalizationBundle", nextIndent);
			}
		}
		out.write(indent);
		out.write("</" + nodeName + ">\n");
	}

	@Override
	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem("order");
			if (attr != null) {
				_Order = Integer.parseInt(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("iconLocationBundleKey");
			if (attr != null) {
				_IconLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("desktopLocationBundleKey");
			if (attr != null) {
				_DesktopLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("toolTipBundleKey");
			if (attr != null) {
				_ToolTipBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("displayStringBundleKey");
			if (attr != null) {
				_DisplayStringBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("location");
			if (attr != null) {
				_Location = attr.getValue();
			}
		}
		final NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern()
					: childNode.getLocalName().intern());

			if ("Apparatus".equals(childNodeName)) {
				final Apparatus aApparatus = new com.linkare.rec.impl.baseUI.config.Apparatus();
				aApparatus._setPropertyChangeSupport(eventListeners);
				aApparatus.readNode(childNode);
				_Apparatus.add(aApparatus);
			} else if ("WebResource".equals(childNodeName)) {
				final WebResource aWebResource = new com.linkare.rec.impl.baseUI.config.WebResource();
				aWebResource._setPropertyChangeSupport(eventListeners);
				aWebResource.readNode(childNode);
				_WebResource.add(aWebResource);
			} else if ("LocalizationBundle".equals(childNodeName)) {
				final LocalizationBundle aLocalizationBundle = new com.linkare.rec.impl.baseUI.config.LocalizationBundle();
				aLocalizationBundle._setPropertyChangeSupport(eventListeners);
				aLocalizationBundle.readNode(childNode);
				_LocalizationBundle.add(aLocalizationBundle);
				loadLocalizationBundle(aLocalizationBundle);
			} else {
				Logger.getLogger(Lab.class.getName()).log(Level.WARNING,
						"Found a child nome of type '" + childNodeName + "' that is not recognized!");
			}
		}
	}

	public ReCResourceBundle loadLocalizationBundle(final LocalizationBundle bundle) {
		return ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
	}

	@Override
	public void validate() throws ValidateException {
		// Validating property order

		// Validating property iconLocationBundleKey
		if (getIconLocationBundleKey() == null) {
			throw new ValidateException("getIconLocationBundleKey() == null", "iconLocationBundleKey", this); // NOI18N
		}
		// Validating property toolTipBundleKey
		/*
		 * if (getToolTipBundleKey() == null) { throw new
		 * ValidateException("getToolTipBundleKey() == null",
		 * "toolTipBundleKey", this); // NOI18N }
		 */
		// Validating property displayStringBundleKey
		if (getDisplayStringBundleKey() == null) {
			throw new ValidateException("getDisplayStringBundleKey() == null", "displayStringBundleKey", this); // NOI18N
		}
		// Validating property location
		if (getLocation() == null) {
			throw new ValidateException("getLocation() == null", "location", this); // NOI18N
		}
		// Validating property apparatus
		for (int _index = 0; _index < sizeApparatus(); ++_index) {
			final com.linkare.rec.impl.baseUI.config.Apparatus element = getApparatus(_index);
			if (element != null) {
				element.validate();
			}
		}
		if (sizeApparatus() < 1) {
			throw new ValidateException("At least one apparatus must exist in the lab", "apparatus size'", this); // NOI18N
		}
		// Validating property webResource
		for (int _index = 0; _index < sizeWebResource(); ++_index) {
			final com.linkare.rec.impl.baseUI.config.WebResource element = getWebResource(_index);
			if (element != null) {
				element.validate();
			}
		}
		// Validating property localizationBundle
		for (int _index = 0; _index < sizeLocalizationBundle(); ++_index) {
			final com.linkare.rec.impl.baseUI.config.LocalizationBundle element = getLocalizationBundle(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (eventListeners == null) {
			eventListeners = new PropertyChangeSupport(this);
		}
		eventListeners.addPropertyChangeListener(listener);
		for (final Object element2 : _Apparatus) {
			final com.linkare.rec.impl.baseUI.config.Apparatus element = (com.linkare.rec.impl.baseUI.config.Apparatus) element2;
			if (element != null) {
				element.addPropertyChangeListener(listener);
			}
		}
		for (final Object element2 : _WebResource) {
			final com.linkare.rec.impl.baseUI.config.WebResource element = (com.linkare.rec.impl.baseUI.config.WebResource) element2;
			if (element != null) {
				element.addPropertyChangeListener(listener);
			}
		}
		for (final Object element2 : _LocalizationBundle) {
			final com.linkare.rec.impl.baseUI.config.LocalizationBundle element = (com.linkare.rec.impl.baseUI.config.LocalizationBundle) element2;
			if (element != null) {
				element.addPropertyChangeListener(listener);
			}
		}
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (eventListeners == null) {
			return;
		}
		eventListeners.removePropertyChangeListener(listener);
		if (!eventListeners.hasListeners(null)) {
			eventListeners = null;
		}
	}

	@Override
	public void _setPropertyChangeSupport(final PropertyChangeSupport listeners) {
		eventListeners = listeners;
	}

	public void changePropertyByName(String name, final Object value) {
		if (name == null) {
			return;
		}
		name = name.intern();
		if (name.equals("order")) {
			setOrder(((Number) value).intValue());
		} else if (name.equals("iconLocationBundleKey")) {
			setIconLocationBundleKey((String) value);
		} else if (name.equals("desktopLocationBundleKey")) {
			setDesktopLocationBundleKey((String) value);
		} else if (name.equals("toolTipBundleKey")) {
			setToolTipBundleKey((String) value);
		} else if (name.equals("displayStringBundleKey")) {
			setDisplayStringBundleKey((String) value);
		} else if (name.equals("location")) {
			setLocation((String) value);
		} else if (name.equals("apparatus")) {
			addApparatus((Apparatus) value);
		} else if (name.equals("apparatus[]")) {
			setApparatus((Apparatus[]) value);
		} else if (name.equals("webResource")) {
			addWebResource((WebResource) value);
		} else if (name.equals("webResource[]")) {
			setWebResource((WebResource[]) value);
		} else if (name.equals("localizationBundle")) {
			addLocalizationBundle((LocalizationBundle) value);
		} else if (name.equals("localizationBundle[]")) {
			setLocalizationBundle((LocalizationBundle[]) value);
		} else {
			throw new IllegalArgumentException(name + " is not a valid property name for Lab");
		}
	}

	public Object fetchPropertyByName(final String name) {
		if (name.equals("order")) {
			return Integer.valueOf(getOrder());
		}
		if (name.equals("iconLocationBundleKey")) {
			return getIconLocationBundleKey();
		}
		if (name.equals("desktopLocationBundleKey")) {
			return getDesktopLocationBundleKey();
		}
		if (name.equals("toolTipBundleKey")) {
			return getToolTipBundleKey();
		}
		if (name.equals("displayStringBundleKey")) {
			return getDisplayStringBundleKey();
		}
		if (name.equals("location")) {
			return getLocation();
		}
		if (name.equals("apparatus[]")) {
			return getApparatus();
		}
		if (name.equals("webResource[]")) {
			return getWebResource();
		}
		if (name.equals("localizationBundle[]")) {
			return getLocalizationBundle();
		}
		throw new IllegalArgumentException(name + " is not a valid property name for Lab");
	}

	// Return an array of all of the properties that are beans and are set.
	@Override
	public com.linkare.rec.impl.baseUI.config.CommonBean[] childBeans(final boolean recursive) {
		final List<CommonBean> children = new LinkedList<CommonBean>();
		childBeans(recursive, children);
		final com.linkare.rec.impl.baseUI.config.CommonBean[] result = new com.linkare.rec.impl.baseUI.config.CommonBean[children
				.size()];
		return children.toArray(result);
	}

	// Put all child beans into the beans list.
	public void childBeans(final boolean recursive, final List<CommonBean> beans) {
		for (final Object element2 : _Apparatus) {
			final com.linkare.rec.impl.baseUI.config.Apparatus element = (com.linkare.rec.impl.baseUI.config.Apparatus) element2;
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
		for (final Object element2 : _WebResource) {
			final com.linkare.rec.impl.baseUI.config.WebResource element = (com.linkare.rec.impl.baseUI.config.WebResource) element2;
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
		for (final Object element2 : _LocalizationBundle) {
			final com.linkare.rec.impl.baseUI.config.LocalizationBundle element = (com.linkare.rec.impl.baseUI.config.LocalizationBundle) element2;
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
	}

	@Override
	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof com.linkare.rec.impl.baseUI.config.Lab)) {
			return false;
		}
		final com.linkare.rec.impl.baseUI.config.Lab inst = (com.linkare.rec.impl.baseUI.config.Lab) o;
		if (_Order != inst._Order) {
			return false;
		}
		if (!(_IconLocationBundleKey == null ? inst._IconLocationBundleKey == null : _IconLocationBundleKey
				.equals(inst._IconLocationBundleKey))) {
			return false;
		}
		if (!(_DesktopLocationBundleKey == null ? inst._DesktopLocationBundleKey == null : _DesktopLocationBundleKey
				.equals(inst._DesktopLocationBundleKey))) {
			return false;
		}
		if (!(_ToolTipBundleKey == null ? inst._ToolTipBundleKey == null : _ToolTipBundleKey
				.equals(inst._ToolTipBundleKey))) {
			return false;
		}
		if (!(_DisplayStringBundleKey == null ? inst._DisplayStringBundleKey == null : _DisplayStringBundleKey
				.equals(inst._DisplayStringBundleKey))) {
			return false;
		}
		if (!(_Location == null ? inst._Location == null : _Location.equals(inst._Location))) {
			return false;
		}
		if (sizeApparatus() != inst.sizeApparatus()) {
			return false;
		}
		// Compare every element.
		for (Iterator<Apparatus> it = _Apparatus.iterator(), it2 = inst._Apparatus.iterator(); it.hasNext() && it2.hasNext();) {
			final com.linkare.rec.impl.baseUI.config.Apparatus element = it
					.next();
			final com.linkare.rec.impl.baseUI.config.Apparatus element2 = it2
					.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		if (sizeWebResource() != inst.sizeWebResource()) {
			return false;
		}
		// Compare every element.
		for (Iterator<WebResource> it = _WebResource.iterator(), it2 = inst._WebResource.iterator(); it.hasNext() && it2.hasNext();) {
			final com.linkare.rec.impl.baseUI.config.WebResource element = it
					.next();
			final com.linkare.rec.impl.baseUI.config.WebResource element2 = it2
					.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		if (sizeLocalizationBundle() != inst.sizeLocalizationBundle()) {
			return false;
		}
		// Compare every element.
		for (Iterator<LocalizationBundle> it = _LocalizationBundle.iterator(), it2 = inst._LocalizationBundle.iterator(); it.hasNext()
				&& it2.hasNext();) {
			final com.linkare.rec.impl.baseUI.config.LocalizationBundle element = it
					.next();
			final com.linkare.rec.impl.baseUI.config.LocalizationBundle element2 = it2
					.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + _Order;
		result = 37 * result + (_IconLocationBundleKey == null ? 0 : _IconLocationBundleKey.hashCode());
		result = 37 * result + (_DesktopLocationBundleKey == null ? 0 : _DesktopLocationBundleKey.hashCode());
		result = 37 * result + (_ToolTipBundleKey == null ? 0 : _ToolTipBundleKey.hashCode());
		result = 37 * result + (_DisplayStringBundleKey == null ? 0 : _DisplayStringBundleKey.hashCode());
		result = 37 * result + (_Location == null ? 0 : _Location.hashCode());
		result = 37 * result + (_Apparatus == null ? 0 : _Apparatus.hashCode());
		result = 37 * result + (_WebResource == null ? 0 : _WebResource.hashCode());
		result = 37 * result + (_LocalizationBundle == null ? 0 : _LocalizationBundle.hashCode());
		return result;
	}

	public String toString() {
		final StringWriter sw = new StringWriter();
		try {
			writeNode(sw, "Lab", "");
		} catch (final IOException e) {
			// How can we actually get an IOException on a StringWriter?
			// We'll just ignore it.
		}
		return sw.toString();
	}

	public String getDisplayNameBundleKey() {
		return getDisplayStringBundleKey();
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
