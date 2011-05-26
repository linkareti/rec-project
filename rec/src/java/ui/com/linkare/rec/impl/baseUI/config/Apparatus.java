/**
 *	This generated bean class Apparatus
 *	matches the schema element 'Apparatus'.
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

public class Apparatus extends DisplayNode implements com.linkare.rec.impl.baseUI.config.CommonBean, OrderedItem {
	private int _Order = Integer.MAX_VALUE;
	private String _DisplayStringBundleKey = "";
	private String _IconLocationBundleKey = "";
	private String _DesktopLocationBundleKey = "";
	private String _ToolTipBundleKey = "";
	private String _DataModelClassLocationBundleKey = "";
	private String _VideoLocation = "";
	private String _HeaderDisplayClassLocationBundleKey = "";
	private String _DisplayFactoryClassLocationBundleKey = "";
	private String _CustomizerClassLocationBundleKey = "";
	private String _Location = "";
	private final List<DefaultAcquisitionConfig> _DefaultAcquisitionConfig = new ArrayList<DefaultAcquisitionConfig>(); // List<DefaultAcquisitionConfig>
	private final List<Display> _Display = new ArrayList<Display>(); // List<Display>
	private final List<WebResource> _WebResource = new ArrayList<WebResource>(); // List<WebResource>
	private final List<LocalizationBundle> _LocalizationBundle = new ArrayList<LocalizationBundle>(); // List<LocalizationBundle>
	private PropertyChangeSupport eventListeners;

	public Apparatus() {
	}

	// Deep copy
	public Apparatus(final com.linkare.rec.impl.baseUI.config.Apparatus source) {
		_Order = source._Order;
		_DisplayStringBundleKey = source._DisplayStringBundleKey;
		_IconLocationBundleKey = source._IconLocationBundleKey;
		_DesktopLocationBundleKey = source._DesktopLocationBundleKey;
		_ToolTipBundleKey = source._ToolTipBundleKey;
		_DataModelClassLocationBundleKey = source._DataModelClassLocationBundleKey;
		_VideoLocation = source._VideoLocation;
		_HeaderDisplayClassLocationBundleKey = source._HeaderDisplayClassLocationBundleKey;
		_DisplayFactoryClassLocationBundleKey = source._DisplayFactoryClassLocationBundleKey;
		_Location = source._Location;
		_CustomizerClassLocationBundleKey = source._CustomizerClassLocationBundleKey;
		for (final Object element : source._DefaultAcquisitionConfig) {
			_DefaultAcquisitionConfig.add(new com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig(
					(com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig) element));
		}
		for (final Object element : source._Display) {
			_Display.add(new com.linkare.rec.impl.baseUI.config.Display(
					(com.linkare.rec.impl.baseUI.config.Display) element));
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
	public void setOrder(final int value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "order", new Integer(getOrder()), new Integer(value));
		}
		_Order = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public int getOrder() {
		return _Order;
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

	public String getToolTipBundleKey() {
		return _ToolTipBundleKey;
	}

	// This attribute is mandatory
	public void setDataModelClassLocationBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "dataModelClassLocationBundleKey",
					getDataModelClassLocationBundleKey(), value);
		}
		_DataModelClassLocationBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getDataModelClassLocationBundleKey() {
		return _DataModelClassLocationBundleKey;
	}

	// This attribute is mandatory
	public void setVideoLocation(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "videoLocation", getVideoLocation(), value);
		}
		_VideoLocation = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getVideoLocation() {
		return _VideoLocation;
	}

	// This attribute is mandatory
	public void setHeaderDisplayClassLocationBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "headerDisplayClassLocationBundleKey",
					getHeaderDisplayClassLocationBundleKey(), value);
		}
		_HeaderDisplayClassLocationBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getHeaderDisplayClassLocationBundleKey() {
		return _HeaderDisplayClassLocationBundleKey;
	}

	// This attribute is mandatory
	public void setDisplayFactoryClassLocationBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "displayFactoryClassLocationBundleKey",
					getDisplayFactoryClassLocationBundleKey(), value);
		}
		_DisplayFactoryClassLocationBundleKey = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getDisplayFactoryClassLocationBundleKey() {
		return _DisplayFactoryClassLocationBundleKey;
	}

	// This attribute is mandatory
	public void setCustomizerClassLocationBundleKey(final String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "customizerClassLocationBundleKey",
					getCustomizerClassLocationBundleKey(), value);
		}
		_Location = value;
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public String getCustomizerClassLocationBundleKey() {
		return _CustomizerClassLocationBundleKey;
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
	public void setDefaultAcquisitionConfig(com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig[] value) {
		if (value == null) {
			value = new DefaultAcquisitionConfig[0];
		}
		// Make the foreign beans take on our property change event listeners.
		for (int i = 0; i < value.length; ++i) {
			if (value[i] != null) {
				value[i]._setPropertyChangeSupport(eventListeners);
			}
		}
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "defaultAcquisitionConfig", getDefaultAcquisitionConfig(), value);
		}
		_DefaultAcquisitionConfig.clear();
		for (int i = 0; i < value.length; ++i) {
			_DefaultAcquisitionConfig.add(value[i]);
		}
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public void setDefaultAcquisitionConfig(final int index,
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "defaultAcquisitionConfig.i" + index,
					_DefaultAcquisitionConfig.get(index), value);
			eventListeners.firePropertyChange(event);
		}
		_DefaultAcquisitionConfig.set(index, value);
	}

	public com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig[] getDefaultAcquisitionConfig() {
		// Check this --- it should be probably 0 here
		final DefaultAcquisitionConfig[] arr = new DefaultAcquisitionConfig[_DefaultAcquisitionConfig.size()];
		return _DefaultAcquisitionConfig.toArray(arr);
	}

	public List<DefaultAcquisitionConfig> fetchDefaultAcquisitionConfigList() {
		return _DefaultAcquisitionConfig;
	}

	public com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig getDefaultAcquisitionConfig(final int index) {
		return _DefaultAcquisitionConfig.get(index);
	}

	// Return the number of defaultAcquisitionConfig
	public int sizeDefaultAcquisitionConfig() {
		return _DefaultAcquisitionConfig.size();
	}

	public int addDefaultAcquisitionConfig(final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_DefaultAcquisitionConfig.add(value);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "defaultAcquisitionConfig.i"
					+ (_DefaultAcquisitionConfig.size() - 1), null, value);
			eventListeners.firePropertyChange(event);
		}
		return _DefaultAcquisitionConfig.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeDefaultAcquisitionConfig(final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig value) {
		final int pos = _DefaultAcquisitionConfig.indexOf(value);
		if (pos >= 0) {
			_DefaultAcquisitionConfig.remove(pos);
			if (eventListeners != null) {
				final PropertyChangeEvent event = new PropertyChangeEvent(this, "defaultAcquisitionConfig.i" + pos,
						value, null);
				eventListeners.firePropertyChange(event);
			}
		}
		return pos;
	}

	// This attribute is an array, possibly empty
	public void setDisplay(com.linkare.rec.impl.baseUI.config.Display[] value) {
		if (value == null) {
			value = new Display[0];
		}
		// Make the foreign beans take on our property change event listeners.
		for (int i = 0; i < value.length; ++i) {
			if (value[i] != null) {
				value[i]._setPropertyChangeSupport(eventListeners);
			}
		}
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "display", getDisplay(), value);
		}
		_Display.clear();
		for (int i = 0; i < value.length; ++i) {
			_Display.add(value[i]);
		}
		if (eventListeners != null) {
			eventListeners.firePropertyChange(event);
		}
	}

	public void setDisplay(final int index, final com.linkare.rec.impl.baseUI.config.Display value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "display.i" + index, _Display.get(index),
					value);
			eventListeners.firePropertyChange(event);
		}
		_Display.set(index, value);
	}

	public com.linkare.rec.impl.baseUI.config.Display[] getDisplay() {
		return _Display.toArray(new Display[0]);
	}

	public List<Display> fetchDisplayList() {
		return _Display;
	}

	public com.linkare.rec.impl.baseUI.config.Display getDisplay(final int index) {
		return _Display.get(index);
	}

	// Return the number of display
	public int sizeDisplay() {
		return _Display.size();
	}

	public int addDisplay(final com.linkare.rec.impl.baseUI.config.Display value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_Display.add(value);
		if (eventListeners != null) {
			final PropertyChangeEvent event = new PropertyChangeEvent(this, "display.i" + (_Display.size() - 1), null,
					value);
			eventListeners.firePropertyChange(event);
		}
		return _Display.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeDisplay(final com.linkare.rec.impl.baseUI.config.Display value) {
		final int pos = _Display.indexOf(value);
		if (pos >= 0) {
			_Display.remove(pos);
			if (eventListeners != null) {
				final PropertyChangeEvent event = new PropertyChangeEvent(this, "display.i" + pos, value, null);
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

	public void writeNode(final Writer out, final String nodeName, final String indent) throws IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// order is an attribute
		out.write(" order"); // NOI18N
		out.write("='"); // NOI18N
		out.write(_Order); // NOI18N
		out.write("'"); // NOI18N

		// displayStringBundleKey is an attribute
		if (_DisplayStringBundleKey != null) {
			out.write(" displayStringBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _DisplayStringBundleKey, true);
			out.write("'"); // NOI18N
		}
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
		// dataModelClassLocationBundleKey is an attribute
		if (_DataModelClassLocationBundleKey != null) {
			out.write(" dataModelClassLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _DataModelClassLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// videoLocation is an attribute
		if (_VideoLocation != null) {
			out.write(" videoLocation"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _VideoLocation, true);
			out.write("'"); // NOI18N
		}
		// headerDisplayClassLocationBundleKey is an attribute
		if (_HeaderDisplayClassLocationBundleKey != null) {
			out.write(" headerDisplayClassLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig
					.writeXML(out, _HeaderDisplayClassLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// headerDisplayClassLocationBundleKey is an attribute
		if (_DisplayFactoryClassLocationBundleKey != null) {
			out.write(" displayFactoryClassLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _DisplayFactoryClassLocationBundleKey,
					true);
			out.write("'"); // NOI18N
		}
		// location is an attribute
		if (_Location != null) {
			out.write(" location"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _Location, true);
			out.write("'"); // NOI18N
		}
		// customizerClassLocationBundleKey is an attribute
		if (_CustomizerClassLocationBundleKey != null) {
			out.write(" customizerClassLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.writeXML(out, _CustomizerClassLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		out.write(">\n");
		final String nextIndent = indent + "	";
		for (final Object element2 : _DefaultAcquisitionConfig) {
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig element = (com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig) element2;
			if (element != null) {
				element.writeNode(out, "DefaultAcquisitionConfig", nextIndent);
			}
		}
		for (final Object element2 : _Display) {
			final com.linkare.rec.impl.baseUI.config.Display element = (com.linkare.rec.impl.baseUI.config.Display) element2;
			if (element != null) {
				element.writeNode(out, "Display", nextIndent);
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

	public void readNode(final Node node) {
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem("order");
			if (attr != null) {
				_Order = Integer.parseInt(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("displayStringBundleKey");
			if (attr != null) {
				_DisplayStringBundleKey = attr.getValue();
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
			attr = (Attr) attrs.getNamedItem("dataModelClassLocationBundleKey");
			if (attr != null) {
				_DataModelClassLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("videoLocation");
			if (attr != null) {
				_VideoLocation = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("headerDisplayClassLocationBundleKey");
			if (attr != null) {
				_HeaderDisplayClassLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("displayFactoryClassLocationBundleKey");
			if (attr != null) {
				_DisplayFactoryClassLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("location");
			if (attr != null) {
				_Location = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("customizerClassLocationBundleKey");
			if (attr != null) {
				_CustomizerClassLocationBundleKey = attr.getValue();
			}
		}
		final NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			final Node childNode = children.item(i);
			final String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern()
					: childNode.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if ("DefaultAcquisitionConfig".equals(childNodeName)) {
				final DefaultAcquisitionConfig aDefaultAcquisitionConfig = new com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig();
				aDefaultAcquisitionConfig._setPropertyChangeSupport(eventListeners);
				aDefaultAcquisitionConfig.readNode(childNode);
				_DefaultAcquisitionConfig.add(aDefaultAcquisitionConfig);
			} else if ("Display".equals(childNodeName)) {
				final Display aDisplay = new com.linkare.rec.impl.baseUI.config.Display();
				aDisplay._setPropertyChangeSupport(eventListeners);
				aDisplay.readNode(childNode);
				_Display.add(aDisplay);
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
				Logger.getLogger(Apparatus.class.getName()).log(Level.WARNING,"Found a child nome of type '"+childNodeName+"' that is not recognized!");
			}
		}
	}

	public ReCResourceBundle loadLocalizationBundle(final LocalizationBundle bundle) {
		return ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
	}

	public void validate() throws ValidateException {
		final boolean restrictionFailure = false;

		// Validating property apparatus
		if (getCustomizerClassLocationBundleKey() == null) {
			throw new ValidateException("getCustomizer() == null", "customizerClassLocationBundleKey", this); // NOI18N
		}
		// Validating property displayStringBundleKey
		if (getDisplayStringBundleKey() == null) {
			throw new ValidateException("getDisplayStringBundleKey() == null", "displayStringBundleKey", this); // NOI18N
		}
		// Validating property iconLocationBundleKey
		if (getIconLocationBundleKey() == null) {
			throw new ValidateException("getIconLocationBundleKey() == null", "iconLocationBundleKey", this); // NOI18N
		}
		// Validating property toolTipBundleKey
		// Check if this is not optional! -> Indeed it should be optional
		/*
		 * if (getToolTipBundleKey() == null) { throw new
		 * ValidateException("getToolTipBundleKey() == null",
		 * "toolTipBundleKey", this); // NOI18N }
		 */
		// Validating property dataModelClassLocationBundleKey
		// Check if this is not optional! -> Indeed it should be optional
		/*
		 * if (getDataModelClassLocationBundleKey() == null) { throw new
		 * ValidateException("getDataModelClassLocationBundleKey() == null",
		 * "dataModelClassLocationBundleKey", this); // NOI18N }
		 */
		// Validating property videoLocation
		// Check if this is not optional! -> Indeed it should be optional
		/*
		 * if (getVideoLocation() == null) { throw new
		 * ValidateException("getVideoLocation() == null", "videoLocation",
		 * this); // NOI18N }
		 */
		// Validating property headerDisplayClassLocationBundleKey
		// Check if this is not optional! -> Indeed it should be optional
		/*
		 * if (getHeaderDisplayClassLocationBundleKey() == null) { throw new
		 * ValidateException("getHeaderDisplayClassLocationBundleKey() == null",
		 * "headerDisplayClassLocationBundleKey", this); // NOI18N }
		 */
		// Validating property location
		if (getLocation() == null) {
			throw new ValidateException("getLocation() == null", "location", this); // NOI18N
		}
		// Validating property defaultAcquisitionConfig
		for (int _index = 0; _index < sizeDefaultAcquisitionConfig(); ++_index) {
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig element = getDefaultAcquisitionConfig(_index);
			if (element != null) {
				element.validate();
			}
		}
		// Validating property display
		for (int _index = 0; _index < sizeDisplay(); ++_index) {
			final com.linkare.rec.impl.baseUI.config.Display element = getDisplay(_index);
			if (element != null) {
				element.validate();
			}
		}
		if (sizeDisplay() < 1) {
			throw new ValidateException("At least one display must exist", "no display in apparatus", this); // NOI18N
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

	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		if (eventListeners == null) {
			eventListeners = new PropertyChangeSupport(this);
		}
		eventListeners.addPropertyChangeListener(listener);
		for (final Object element2 : _DefaultAcquisitionConfig) {
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig element = (com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig) element2;
			if (element != null) {
				element.addPropertyChangeListener(listener);
			}
		}
		for (final Object element2 : _Display) {
			final com.linkare.rec.impl.baseUI.config.Display element = (com.linkare.rec.impl.baseUI.config.Display) element2;
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

	public void removePropertyChangeListener(final PropertyChangeListener listener) {
		if (eventListeners == null) {
			return;
		}
		eventListeners.removePropertyChangeListener(listener);
		if (!eventListeners.hasListeners(null)) {
			eventListeners = null;
		}
	}

	public void _setPropertyChangeSupport(final PropertyChangeSupport listeners) {
		eventListeners = listeners;
	}

	public void changePropertyByName(String name, final Object value) {
		if (name == null) {
			return;
		}
		name = name.intern();
		if (name == "order") {
			setOrder(((Number) value).intValue());
		} else if (name == "displayStringBundleKey") {
			setDisplayStringBundleKey((String) value);
		} else if (name == "iconLocationBundleKey") {
			setIconLocationBundleKey((String) value);
		} else if (name == "desktopLocationBundleKey") {
			setDesktopLocationBundleKey((String) value);
		} else if (name == "toolTipBundleKey") {
			setToolTipBundleKey((String) value);
		} else if (name == "dataModelClassLocationBundleKey") {
			setDataModelClassLocationBundleKey((String) value);
		} else if (name == "videoLocation") {
			setVideoLocation((String) value);
		} else if (name == "displayFactoryClassLocationBundleKey") {
			setDisplayFactoryClassLocationBundleKey((String) value);
		} else if (name == "displayFactoryClassLocationBundleKey") {
			setHeaderDisplayClassLocationBundleKey((String) value);
		} else if (name == "location") {
			setLocation((String) value);
		} else if (name == "customizerClassLocationBundleKey") {
			setLocation((String) value);
		} else if (name == "defaultAcquisitionConfig") {
			addDefaultAcquisitionConfig((DefaultAcquisitionConfig) value);
		} else if (name == "defaultAcquisitionConfig[]") {
			setDefaultAcquisitionConfig((DefaultAcquisitionConfig[]) value);
		} else if (name == "display") {
			addDisplay((Display) value);
		} else if (name == "display[]") {
			setDisplay((Display[]) value);
		} else if (name == "webResource") {
			addWebResource((WebResource) value);
		} else if (name == "webResource[]") {
			setWebResource((WebResource[]) value);
		} else if (name == "localizationBundle") {
			addLocalizationBundle((LocalizationBundle) value);
		} else if (name == "localizationBundle[]") {
			setLocalizationBundle((LocalizationBundle[]) value);
		} else {
			throw new IllegalArgumentException(name + " is not a valid property name for Apparatus");
		}
	}

	public Object fetchPropertyByName(final String name) {
		if (name == "order") {
			return new Integer(getOrder());
		}
		if (name == "displayStringBundleKey") {
			return getDisplayStringBundleKey();
		}
		if (name == "iconLocationBundleKey") {
			return getIconLocationBundleKey();
		}
		if (name == "desktopLocationBundleKey") {
			return getDesktopLocationBundleKey();
		}
		if (name == "toolTipBundleKey") {
			return getToolTipBundleKey();
		}
		if (name == "dataModelClassLocationBundleKey") {
			return getDataModelClassLocationBundleKey();
		}
		if (name == "videoLocation") {
			return getVideoLocation();
		}
		if (name == "headerDisplayClassLocationBundleKey") {
			return getHeaderDisplayClassLocationBundleKey();
		}
		if (name == "displayFactoryClassLocationBundleKey") {
			return getDisplayFactoryClassLocationBundleKey();
		}
		if (name == "location") {
			return getLocation();
		}
		if (name == "customizerClassLocationBundleKey") {
			return getCustomizerClassLocationBundleKey();
		}
		if (name == "defaultAcquisitionConfig[]") {
			return getDefaultAcquisitionConfig();
		}
		if (name == "display[]") {
			return getDisplay();
		}
		if (name == "webResource[]") {
			return getWebResource();
		}
		if (name == "localizationBundle[]") {
			return getLocalizationBundle();
		}
		throw new IllegalArgumentException(name + " is not a valid property name for Apparatus");
	}

	// Return an array of all of the properties that are beans and are set.
	public com.linkare.rec.impl.baseUI.config.CommonBean[] childBeans(final boolean recursive) {
		final List<CommonBean> children = new LinkedList<CommonBean>();
		childBeans(recursive, children);
		final com.linkare.rec.impl.baseUI.config.CommonBean[] result = new com.linkare.rec.impl.baseUI.config.CommonBean[children
				.size()];
		return children.toArray(result);
	}

	// Put all child beans into the beans list.
	public void childBeans(final boolean recursive, final List<CommonBean> beans) {
		for (final Object element2 : _DefaultAcquisitionConfig) {
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig element = (com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig) element2;
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
		for (final Object element2 : _Display) {
			final com.linkare.rec.impl.baseUI.config.Display element = (com.linkare.rec.impl.baseUI.config.Display) element2;
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

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof com.linkare.rec.impl.baseUI.config.Apparatus)) {
			return false;
		}
		final com.linkare.rec.impl.baseUI.config.Apparatus inst = (com.linkare.rec.impl.baseUI.config.Apparatus) o;
		if (!(_Order == inst._Order)) {
			return false;
		}
		if (!(_DisplayStringBundleKey == null ? inst._DisplayStringBundleKey == null : _DisplayStringBundleKey
				.equals(inst._DisplayStringBundleKey))) {
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
		if (!(_DataModelClassLocationBundleKey == null ? inst._DataModelClassLocationBundleKey == null
				: _DataModelClassLocationBundleKey.equals(inst._DataModelClassLocationBundleKey))) {
			return false;
		}
		if (!(_VideoLocation == null ? inst._VideoLocation == null : _VideoLocation.equals(inst._VideoLocation))) {
			return false;
		}
		if (!(_HeaderDisplayClassLocationBundleKey == null ? inst._HeaderDisplayClassLocationBundleKey == null
				: _HeaderDisplayClassLocationBundleKey.equals(inst._HeaderDisplayClassLocationBundleKey))) {
			return false;
		}
		if (!(_DisplayFactoryClassLocationBundleKey == null ? inst._DisplayFactoryClassLocationBundleKey == null
				: _DisplayFactoryClassLocationBundleKey.equals(inst._DisplayFactoryClassLocationBundleKey))) {
			return false;
		}
		if (!(_Location == null ? inst._Location == null : _Location.equals(inst._Location))) {
			return false;
		}
		if (!(_CustomizerClassLocationBundleKey == null ? inst._CustomizerClassLocationBundleKey == null
				: _CustomizerClassLocationBundleKey.equals(inst._CustomizerClassLocationBundleKey))) {
			return false;
		}
		if (sizeDefaultAcquisitionConfig() != inst.sizeDefaultAcquisitionConfig()) {
			return false;
		}
		// Compare every element.
		for (Iterator<DefaultAcquisitionConfig> it = _DefaultAcquisitionConfig.iterator(), it2 = inst._DefaultAcquisitionConfig.iterator(); it
				.hasNext() && it2.hasNext();) {
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig element = it
					.next();
			final com.linkare.rec.impl.baseUI.config.DefaultAcquisitionConfig element2 = it2
					.next();
			if (!(element == null ? element2 == null : element.equals(element2))) {
				return false;
			}
		}
		if (sizeDisplay() != inst.sizeDisplay()) {
			return false;
		}
		// Compare every element.
		for (Iterator<Display> it = _Display.iterator(), it2 = inst._Display.iterator(); it.hasNext() && it2.hasNext();) {
			final com.linkare.rec.impl.baseUI.config.Display element = it
					.next();
			final com.linkare.rec.impl.baseUI.config.Display element2 = it2
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
		result = 37 * result + (_DisplayStringBundleKey == null ? 0 : _DisplayStringBundleKey.hashCode());
		result = 37 * result + (_IconLocationBundleKey == null ? 0 : _IconLocationBundleKey.hashCode());
		result = 37 * result + (_DesktopLocationBundleKey == null ? 0 : _DesktopLocationBundleKey.hashCode());
		result = 37 * result + (_ToolTipBundleKey == null ? 0 : _ToolTipBundleKey.hashCode());
		result = 37 * result
				+ (_DataModelClassLocationBundleKey == null ? 0 : _DataModelClassLocationBundleKey.hashCode());
		result = 37 * result + (_VideoLocation == null ? 0 : _VideoLocation.hashCode());
		result = 37 * result
				+ (_HeaderDisplayClassLocationBundleKey == null ? 0 : _HeaderDisplayClassLocationBundleKey.hashCode());
		result = 37
				* result
				+ (_DisplayFactoryClassLocationBundleKey == null ? 0 : _DisplayFactoryClassLocationBundleKey.hashCode());
		result = 37 * result + (_Location == null ? 0 : _Location.hashCode());
		result = 37 * result
				+ (_CustomizerClassLocationBundleKey == null ? 0 : _CustomizerClassLocationBundleKey.hashCode());
		result = 37 * result + (_DefaultAcquisitionConfig == null ? 0 : _DefaultAcquisitionConfig.hashCode());
		result = 37 * result + (_Display == null ? 0 : _Display.hashCode());
		result = 37 * result + (_WebResource == null ? 0 : _WebResource.hashCode());
		result = 37 * result + (_LocalizationBundle == null ? 0 : _LocalizationBundle.hashCode());
		return result;
	}

	public String getDisplayNameBundleKey() {
		return _DisplayStringBundleKey;
	}

	public String toString() {
		final StringWriter sw = new StringWriter();
		try {
			writeNode(sw, "Apparatus", "");
		} catch (final IOException e) {
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
