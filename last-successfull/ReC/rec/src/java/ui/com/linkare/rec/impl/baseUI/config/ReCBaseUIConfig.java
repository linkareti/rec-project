/**
 *	This generated bean class ReCBaseUIConfig
 *	matches the schema element 'ReCBaseUIConfig'.
 *
 *	Generated on Mon Jan 26 00:03:11 GMT 2004
 *
 *	This class matches the root element of the DTD,
 *	and is the root of the bean graph.
 *
 * 	ReCBaseUIConfig : ReCBaseUIConfig
 * 		[attr: aboutPageLocationBundleKey CDATA ]
 * 		[attr: autoConnectLab ENUM ( true false 0 1 yes no ) false]
 * 		[attr: showVideoFrame ENUM ( true false 0 1 yes no ) false]
 * 		[attr: enableVideoFrame ENUM ( true false 0 1 yes no ) true]
 * 		[attr: showChatFrame ENUM ( true false 0 1 yes no ) false]
 * 		[attr: enableChatFrame ENUM ( true false 0 1 yes no ) true]
 * 		[attr: showUserList ENUM ( true false 0 1 yes no ) false]
 * 		[attr: enableUsersList ENUM ( true false 0 1 yes no ) true]
 * 		[attr: usersListRefreshRateMs CDATA 2000]
 * 		[attr: enterApparatusChatRoom ENUM ( true false 0 1 yes no ) false]
 * 		[attr: iconLocationBundleKey CDATA ]
 * 		[attr: frameTitleBundleKey CDATA #REQUIRED ]
 * 		[attr: iconSponsorLocationBundleKey CDATA ]
 * 		[attr: helpPageLocationBundleKey CDATA ]
 * 		(
 * 		  | Lab : Lab
 * 		  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	[attr: toolTipBundleKey CDATA ]
 * 		  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	[attr: location CDATA #REQUIRED ]
 * 		  | 	(
 * 		  | 	  | Apparatus : Apparatus
 * 		  | 	  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	  | 	[attr: toolTipBundleKey CDATA ]
 * 		  | 	  | 	[attr: dataModelClassLocationBundleKey CDATA ]
 * 		  | 	  | 	[attr: videoLocation CDATA ]
 * 		  | 	  | 	[attr: headerDisplayClassLocationBundleKey CDATA ]
 * 		  | 	  | 	[attr: location CDATA #REQUIRED ]
 * 		  | 	  | 	(
 * 		  | 	  | 	  | DefaultAcquisitionConfig : DefaultAcquisitionConfig
 * 		  | 	  | 	  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	  | 	  | 	[attr: toolTipBundleKey CDATA ]
 * 		  | 	  | 	  | 	[attr: classLocationBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	  | Display : Display
 * 		  | 	  | 	  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: offlineCapable ENUM ( true false 0 1 yes no ) false]
 * 		  | 	  | 	  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	  | 	  | 	[attr: toolTipBundleKey CDATA ]
 * 		  | 	  | 	  | 	[attr: classLocationBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	  | WebResource : WebResource
 * 		  | 	  | 	  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: internalBrowser ENUM ( true false 0 1 yes no ) false]
 * 		  | 	  | 	  | 	[attr: toolTipLocationBundleKey CDATA ]
 * 		  | 	  | 	  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	  | 	  | 	[attr: locationBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	  | LocalizationBundle : LocalizationBundle
 * 		  | 	  | 	  | 	[attr: location CDATA #REQUIRED ]
 * 		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 * 		  | 	  | 	)[0,n]
 * 		  | 	  | WebResource : WebResource
 * 		  | 	  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	  | 	[attr: internalBrowser ENUM ( true false 0 1 yes no ) false]
 * 		  | 	  | 	[attr: toolTipLocationBundleKey CDATA ]
 * 		  | 	  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	  | 	[attr: locationBundleKey CDATA #REQUIRED ]
 * 		  | 	  | LocalizationBundle : LocalizationBundle
 * 		  | 	  | 	[attr: location CDATA #REQUIRED ]
 * 		  | 	  | 	[attr: name CDATA #REQUIRED ]
 * 		  | 	)[0,n]
 * 		  | WebResource : WebResource
 * 		  | 	[attr: order CDATA #REQUIRED ]
 * 		  | 	[attr: internalBrowser ENUM ( true false 0 1 yes no ) false]
 * 		  | 	[attr: toolTipLocationBundleKey CDATA ]
 * 		  | 	[attr: displayStringBundleKey CDATA #REQUIRED ]
 * 		  | 	[attr: iconLocationBundleKey CDATA ]
 * 		  | 	[attr: locationBundleKey CDATA #REQUIRED ]
 * 		  | LocalizationBundle : LocalizationBundle
 * 		  | 	[attr: location CDATA #REQUIRED ]
 * 		  | 	[attr: name CDATA #REQUIRED ]
 * 		)[0,n]
 *
 */

package com.linkare.rec.impl.baseUI.config;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.protocols.ReCProtocols;
import com.linkare.rec.impl.utils.Defaults;

public class ReCBaseUIConfig implements CommonBean {
	public final static String UI_CONFIG_URL_PROP = "ReCBaseUIConfig";

	public static String UI_CONFIG_LOGGER = "ReCBaseUIConfig.Logger";

	static {
		Logger l = LogManager.getLogManager().getLogger(UI_CONFIG_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(Logger.getLogger(UI_CONFIG_LOGGER));
		}
	}

	private void log(Level debugLevel, String message) {
		Logger.getLogger(UI_CONFIG_LOGGER).log(debugLevel, "ReCBaseUIConfig - " + message);
	}

	private void logThrowable(String message, Throwable t) {
		LoggerUtil.logThrowable("ReCBaseUIConfig - " + message, t, Logger.getLogger(UI_CONFIG_LOGGER));
	}

	private boolean _AutoConnectLab = false;
	private boolean _ShowVideoFrame = false;
	private boolean _EnableVideoFrame = true;
	private boolean _EnableLoginPassword = false;
	private boolean _ShowChatFrame = false;
	private boolean _EnableChatFrame = true;
	private boolean _ShowUserList = false;
	private boolean _EnableUsersList = true;
	private long _UsersListRefreshRateMs = 2000;
	private boolean _EnterApparatusChatRoom = false;
	private String _AboutPageLocationBundleKey = "";
	private String _SplashIconLocationBundleKey = "";
	private String _IconLocationBundleKey = "";
	private String _DesktopLocationBundleKey = null;
	private String _FrameTitleBundleKey;
	private String _IconSponsorLocationBundleKey = "";
	private String _HelpPageLocationBundleKey = "";
	private List<Lab> _Lab = new ArrayList<Lab>(); // List<Lab>
	private List<WebResource> _WebResource = new ArrayList<WebResource>(); // List<WebResource>
	private PropertyChangeSupport eventListeners;

	private ReCBaseUIConfig() {
		_FrameTitleBundleKey = "";

		String codeBase = "";

		try {
			BasicService bs = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
			codeBase = bs.getCodeBase().toString();
		} catch (Exception e) {
			logThrowable(
					"Couldn't get codebase - Basic Service is probably not defined... Not running as a jnlp application!",
					e);
		}

		if (codeBase == null || "".equals(codeBase))
			codeBase = ".";

		String configLocation = Defaults.defaultIfEmpty(System.getProperty(UI_CONFIG_URL_PROP), codeBase + "/"
				+ UI_CONFIG_URL_PROP + ".xml");

		InputStream is = null;

		try {
			is = ReCProtocols.getURL(configLocation).openConnection().getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
			logThrowable("Couldn't openConnection to Config file, trying to load directly from the jar file!", e);
		}
		// Only to test...
		/*
		 * finally { try {is=ReCProtocols.getURL(
		 * "recresource:///com/linkare/rec/impl/baseUI/config/ReCBaseUIConfigExample.xml"
		 * ).openConnection().getInputStream(); } catch(Exception e) {
		 * logThrowable("Couldn't openConnection to Config file!", e); } }
		 */

		try {
			read(is);
		} catch (Exception e) {
			logThrowable("Couldn't read config file!", e);
		}
		try {
			validate();
		} catch (Exception e) {
			logThrowable("Couldn't validate config file!", e);
			e.printStackTrace();
		}

	}

	public static ReCBaseUIConfig instance = null;

	public static ReCBaseUIConfig sharedInstance() {
		if (instance == null)
			instance = new ReCBaseUIConfig();

		return instance;
	}

	// This attribute is mandatory
	public void setAboutPageLocationBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "aboutPageLocationBundleKey", getAboutPageLocationBundleKey(), value);
		}
		_AboutPageLocationBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	// This attribute is mandatory
	public void setSplashIconLocationBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "splashIconLocationBundleKey", getSplashIconLocationBundleKey(),
					value);
		}
		_SplashIconLocationBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public String getAboutPageLocationBundleKey() {
		return _AboutPageLocationBundleKey;
	}

	public String getSplashIconLocationBundleKey() {
		return _SplashIconLocationBundleKey;
	}

	public javax.swing.ImageIcon getSplashIcon() {
		return ReCResourceBundle.findImageIconOrDefault(getSplashIconLocationBundleKey(), new javax.swing.ImageIcon(
				getClass().getResource("/com/linkare/rec/impl/baseUI/resources/about_box.png")));
	}

	public URL getAboutPageURL() {
		try {
			return ReCProtocols.getURL(ReCResourceBundle.findString(getAboutPageLocationBundleKey()));
		} catch (Exception e) {
		}

		return null;
	}

	public String fetchDefaultAboutPageLocationBundleKey() {
		return "";
	}

	// This attribute is mandatory
	public void setAutoConnectLab(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "autoConnectLab", new Boolean(isAutoConnectLab()), new Boolean(value));
		}
		_AutoConnectLab = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isAutoConnectLab() {
		return _AutoConnectLab;
	}

	// This attribute is mandatory
	public void setShowVideoFrame(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "showVideoFrame", new Boolean(isShowVideoFrame()), new Boolean(value));
		}
		_ShowVideoFrame = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isShowVideoFrame() {
		return _ShowVideoFrame;
	}

	// This attribute is mandatory
	public void setEnableVideoFrame(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "enableVideoFrame", new Boolean(isEnableVideoFrame()), new Boolean(
					value));
		}
		_EnableVideoFrame = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isEnableVideoFrame() {
		return _EnableVideoFrame;
	}

	// This attribute is NOT mandatory
	public void setEnableLoginPassword(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "enableLoginPassword", new Boolean(isEnableLoginPassword()),
					new Boolean(value));
		}
		_EnableLoginPassword = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isEnableLoginPassword() {
		return _EnableLoginPassword;
	}

	// This attribute is mandatory
	public void setShowChatFrame(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "showChatFrame", new Boolean(isShowChatFrame()), new Boolean(value));
		}
		_ShowChatFrame = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isShowChatFrame() {
		return _ShowChatFrame;
	}

	// This attribute is mandatory
	public void setEnableChatFrame(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "enableChatFrame", new Boolean(isEnableChatFrame()), new Boolean(
					value));
		}
		_EnableChatFrame = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isEnableChatFrame() {
		return _EnableChatFrame;
	}

	// This attribute is mandatory
	public void setShowUserList(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "showUserList", new Boolean(isShowUserList()), new Boolean(value));
		}
		_ShowUserList = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isShowUserList() {
		return _ShowUserList;
	}

	// This attribute is mandatory
	public void setEnableUsersList(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "enableUsersList", new Boolean(isEnableUsersList()), new Boolean(
					value));
		}
		_EnableUsersList = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isEnableUsersList() {
		return _EnableUsersList;
	}

	// This attribute is mandatory
	public void setUsersListRefreshRateMs(long value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "usersListRefreshRateMs", new Long(getUsersListRefreshRateMs()),
					new Long(value));
		}
		_UsersListRefreshRateMs = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public long getUsersListRefreshRateMs() {
		return _UsersListRefreshRateMs;
	}

	// This attribute is mandatory
	public void setEnterApparatusChatRoom(boolean value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "enterApparatusChatRoom", new Boolean(isEnterApparatusChatRoom()),
					new Boolean(value));
		}
		_EnterApparatusChatRoom = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public boolean isEnterApparatusChatRoom() {
		return _EnterApparatusChatRoom;
	}

	// This attribute is mandatory
	public void setIconLocationBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "iconLocationBundleKey", getIconLocationBundleKey(), value);
		}
		_IconLocationBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public String getIconLocationBundleKey() {
		return _IconLocationBundleKey;
	}

	public void setDesktopLocationBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "desktopLocationBundleKey", getIconLocationBundleKey(), value);
		}
		_DesktopLocationBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public String getDesktopLocationBundleKey() {
		return _DesktopLocationBundleKey;
	}

	// This attribute is mandatory
	public void setFrameTitleBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "frameTitleBundleKey", getFrameTitleBundleKey(), value);
		}
		_FrameTitleBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public String getFrameTitleBundleKey() {
		return _FrameTitleBundleKey;
	}

	public String getFrameTitle() {
		return ReCResourceBundle.findString(getFrameTitleBundleKey());
	}

	// This attribute is mandatory
	public void setIconSponsorLocationBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "iconSponsorLocationBundleKey", getIconSponsorLocationBundleKey(),
					value);
		}
		_IconSponsorLocationBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public String getIconSponsorLocationBundleKey() {
		return _IconSponsorLocationBundleKey;
	}

	public javax.swing.Icon getIconSponsor() {
		try {
			return ReCResourceBundle.findImageIcon(getIconSponsorLocationBundleKey());
		} catch (Exception e) {
		}
		return null;
	}

	// This attribute is mandatory
	public void setHelpPageLocationBundleKey(String value) {
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "helpPageLocationBundleKey", getHelpPageLocationBundleKey(), value);
		}
		_HelpPageLocationBundleKey = value;
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public String getHelpPageLocationBundleKey() {
		return _HelpPageLocationBundleKey;
	}

	public URL getHelpPageURL() {
		try {
			return ReCProtocols.getURL(getHelpPageLocationBundleKey());
		} catch (Exception e) {
		}

		return null;
	}

	// This attribute is an array, possibly empty
	public void setLab(Lab[] value) {
		if (value == null)
			value = new Lab[0];
		// Make the foreign beans take on our property change event listeners.
		for (int i = 0; i < value.length; ++i) {
			if (value[i] != null) {
				value[i]._setPropertyChangeSupport(eventListeners);
			}
		}
		PropertyChangeEvent event = null;
		if (eventListeners != null) {
			event = new PropertyChangeEvent(this, "lab", getLab(), value);
		}
		_Lab.clear();
		for (int i = 0; i < value.length; ++i) {
			_Lab.add(value[i]);
		}
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public void setLab(int index, Lab value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			PropertyChangeEvent event = new PropertyChangeEvent(this, "lab.i" + index, _Lab.get(index), value);
			eventListeners.firePropertyChange(event);
		}
		_Lab.set(index, value);
	}

	public Lab[] getLab() {
		return (Lab[]) _Lab.toArray(new Lab[0]);
	}

	public Lab[] getOrderedLab() {
		Lab[] arr = new Lab[_Lab.size()];
		arr = (Lab[]) _Lab.toArray(arr);
		Arrays.sort(arr, new OrderedItemComparator());
		return arr;
	}

	public List fetchLabList() {
		return _Lab;
	}

	public Lab getLab(int index) {
		return (Lab) _Lab.get(index);
	}

	// Return the number of lab
	public int sizeLab() {
		return _Lab.size();
	}

	public int addLab(Lab value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_Lab.add(value);
		if (eventListeners != null) {
			PropertyChangeEvent event = new PropertyChangeEvent(this, "lab.i" + (_Lab.size() - 1), null, value);
			eventListeners.firePropertyChange(event);
		}
		return _Lab.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeLab(Lab value) {
		int pos = _Lab.indexOf(value);
		if (pos >= 0) {
			_Lab.remove(pos);
			if (eventListeners != null) {
				PropertyChangeEvent event = new PropertyChangeEvent(this, "lab.i" + pos, value, null);
				eventListeners.firePropertyChange(event);
			}
		}
		return pos;
	}

	// This attribute is an array, possibly empty
	public void setWebResource(WebResource[] value) {
		if (value == null)
			value = new WebResource[0];
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
		if (eventListeners != null)
			eventListeners.firePropertyChange(event);
	}

	public void setWebResource(int index, WebResource value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		if (eventListeners != null) {
			PropertyChangeEvent event = new PropertyChangeEvent(this, "webResource.i" + index, _WebResource.get(index),
					value);
			eventListeners.firePropertyChange(event);
		}
		_WebResource.set(index, value);
	}

	public WebResource[] getWebResource() {
		WebResource[] arr = new WebResource[_WebResource.size()];
		return (WebResource[]) _WebResource.toArray(arr);
	}

	public WebResource[] getOrderedWebResource() {
		WebResource[] arr = new WebResource[_WebResource.size()];
		arr = (WebResource[]) _WebResource.toArray(arr);
		Arrays.sort(arr, new OrderedItemComparator());
		return arr;
	}

	public List fetchWebResourceList() {
		return _WebResource;
	}

	public WebResource getWebResource(int index) {
		return (WebResource) _WebResource.get(index);
	}

	// Return the number of webResource
	public int sizeWebResource() {
		return _WebResource.size();
	}

	public int addWebResource(WebResource value) {
		// Make the foreign beans take on our property change event listeners.
		value._setPropertyChangeSupport(eventListeners);
		_WebResource.add(value);
		if (eventListeners != null) {
			PropertyChangeEvent event = new PropertyChangeEvent(this, "webResource.i" + (_WebResource.size() - 1),
					null, value);
			eventListeners.firePropertyChange(event);
		}
		return _WebResource.size() - 1;
	}

	// Search from the end looking for @param value, and then remove it.
	public int removeWebResource(WebResource value) {
		int pos = _WebResource.indexOf(value);
		if (pos >= 0) {
			_WebResource.remove(pos);
			if (eventListeners != null) {
				PropertyChangeEvent event = new PropertyChangeEvent(this, "webResource.i" + pos, value, null);
				eventListeners.firePropertyChange(event);
			}
		}
		return pos;
	}

	public ReCResourceBundle addLocalizationBundle(LocalizationBundle bundle) {
		return ReCResourceBundle.loadResourceBundle(bundle.getName(), bundle.getLocation());
	}

	public void write(OutputStream out) throws IOException {
		write(out, null);
	}

	public void write(OutputStream out, String encoding) throws IOException {
		Writer w;
		if (encoding == null) {
			encoding = "UTF-8"; // NOI18N
		}
		w = new java.io.BufferedWriter(new java.io.OutputStreamWriter(out, encoding));
		write(w, encoding);
		w.flush();
	}

	// Print this Java Bean to @param out including an XML header.
	// @param encoding is the encoding style that @param out was opened with.
	public void write(Writer out, String encoding) throws IOException {
		out.write("<?xml version='1.0'"); // NOI18N
		if (encoding != null)
			out.write(" encoding='" + encoding + "'"); // NOI18N
		out.write(" ?>\n"); // NOI18N
		writeNode(out, "ReCBaseUIConfig", ""); // NOI18N
	}

	public void writeNode(Writer out, String nodeName, String indent) throws IOException {
		out.write(indent);
		out.write("<");
		out.write(nodeName);
		// aboutPageLocationBundleKey is an attribute
		if (_AboutPageLocationBundleKey != null) {
			out.write(" aboutPageLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _AboutPageLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		if (_SplashIconLocationBundleKey != null) {
			out.write(" splashIconLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _SplashIconLocationBundleKey, true);
			out.write("'"); // NOI18N
		}

		out.write(" autoConnectLab"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_AutoConnectLab));
		out.write("'"); // NOI18N

		out.write(" showVideoFrame"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_ShowVideoFrame));
		out.write("'"); // NOI18N

		out.write(" enableVideoFrame"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_EnableVideoFrame));
		out.write("'"); // NOI18N

		out.write(" enableLoginPassword"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_EnableLoginPassword));
		out.write("'"); // NOI18N

		out.write(" showChatFrame"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_ShowChatFrame));
		out.write("'"); // NOI18N

		out.write(" enableChatFrame"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_EnableChatFrame));
		out.write("'"); // NOI18N

		out.write(" showUserList"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_ShowUserList));
		out.write("'"); // NOI18N

		out.write(" enableUsersList"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_EnableUsersList));
		out.write("'"); // NOI18N

		out.write(" usersListRefreshRateMs"); // NOI18N
		out.write("='"); // NOI18N
		out.write("" + _UsersListRefreshRateMs); // NOI18N
		out.write("'"); // NOI18N

		out.write(" enterApparatusChatRoom"); // NOI18N
		out.write("='"); // NOI18N
		out.write(BooleanXMLParser.toString(_EnterApparatusChatRoom));
		out.write("'"); // NOI18N

		// iconLocationBundleKey is an attribute
		if (_IconLocationBundleKey != null) {
			out.write(" iconLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _IconLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// desktopLocationBundleKey is an attribute
		if (_DesktopLocationBundleKey != null) {
			out.write(" desktopLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _DesktopLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// frameTitleBundleKey is an attribute
		if (_FrameTitleBundleKey != null) {
			out.write(" frameTitleBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _FrameTitleBundleKey, true);
			out.write("'"); // NOI18N
		}
		// iconSponsorLocationBundleKey is an attribute
		if (_IconSponsorLocationBundleKey != null) {
			out.write(" iconSponsorLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _IconSponsorLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		// helpPageLocationBundleKey is an attribute
		if (_HelpPageLocationBundleKey != null) {
			out.write(" helpPageLocationBundleKey"); // NOI18N
			out.write("='"); // NOI18N
			ReCBaseUIConfig.writeXML(out, _HelpPageLocationBundleKey, true);
			out.write("'"); // NOI18N
		}
		out.write(">\n");
		String nextIndent = indent + "	";
		for (Iterator it = _Lab.iterator(); it.hasNext();) {
			Lab element = (Lab) it.next();
			if (element != null) {
				element.writeNode(out, "Lab", nextIndent);
			}
		}
		for (Iterator it = _WebResource.iterator(); it.hasNext();) {
			WebResource element = (WebResource) it.next();
			if (element != null) {
				element.writeNode(out, "WebResource", nextIndent);
			}
		}
		out.write(indent);
		out.write("</" + nodeName + ">\n");
	}

	public void read(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		read(new InputSource(in), false, null, null);
	}

	// Warning: in readNoEntityResolver character and entity references will
	// not be read from any DTD in the XML source.
	// However, this way is faster since no DTDs are looked up
	// (possibly skipping network access) or parsed.
	public void readNoEntityResolver(InputStream in) throws ParserConfigurationException, SAXException, IOException {
		read(new InputSource(in), false, new EntityResolver() {
			public InputSource resolveEntity(String publicId, String systemId) {
				ByteArrayInputStream bin = new ByteArrayInputStream(new byte[0]);
				return new InputSource(bin);
			}
		}, null);
	}

	public void read(InputSource in, boolean validate, EntityResolver er, org.xml.sax.ErrorHandler eh)
			throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(validate);
		javax.xml.parsers.DocumentBuilder db = dbf.newDocumentBuilder();
		if (er != null)
			db.setEntityResolver(er);
		if (eh != null)
			db.setErrorHandler(eh);
		Document doc = db.parse(in);
		read(doc);
	}

	public void read(Document document) {
		readNode(document.getDocumentElement());
	}

	public void readNode(Node node) {
		if (node.hasAttributes()) {
			org.w3c.dom.NamedNodeMap attrs = node.getAttributes();
			Attr attr;
			attr = (Attr) attrs.getNamedItem("aboutPageLocationBundleKey");
			if (attr != null) {
				_AboutPageLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("splashIconLocationBundleKey");
			if (attr != null) {
				_SplashIconLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("autoConnectLab");
			if (attr != null) {
				_AutoConnectLab = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("showVideoFrame");
			if (attr != null) {
				_ShowVideoFrame = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("enableVideoFrame");
			if (attr != null) {
				_EnableVideoFrame = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("enableLoginPassword");
			if (attr != null) {
				_EnableLoginPassword = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("showChatFrame");
			if (attr != null) {
				_ShowChatFrame = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("enableChatFrame");
			if (attr != null) {
				_EnableChatFrame = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("showUserList");
			if (attr != null) {
				_ShowUserList = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("enableUsersList");
			if (attr != null) {
				_EnableUsersList = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("usersListRefreshRateMs");
			if (attr != null) {
				_UsersListRefreshRateMs = Long.parseLong(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("enterApparatusChatRoom");
			if (attr != null) {
				_EnterApparatusChatRoom = BooleanXMLParser.parseBoolean(attr.getValue());
			}
			attr = (Attr) attrs.getNamedItem("iconLocationBundleKey");
			if (attr != null) {
				_IconLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("desktopLocationBundleKey");
			if (attr != null) {
				_DesktopLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("frameTitleBundleKey");
			if (attr != null) {
				_FrameTitleBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("iconSponsorLocationBundleKey");
			if (attr != null) {
				_IconSponsorLocationBundleKey = attr.getValue();
			}
			attr = (Attr) attrs.getNamedItem("helpPageLocationBundleKey");
			if (attr != null) {
				_HelpPageLocationBundleKey = attr.getValue();
			}
		}
		org.w3c.dom.NodeList children = node.getChildNodes();
		for (int i = 0, size = children.getLength(); i < size; ++i) {
			Node childNode = children.item(i);
			String childNodeName = (childNode.getLocalName() == null ? childNode.getNodeName().intern() : childNode
					.getLocalName().intern());
			String childNodeValue = "";
			if (childNode.getFirstChild() != null) {
				childNodeValue = childNode.getFirstChild().getNodeValue();
			}
			if (childNodeName == "Lab") {
				Lab aLab = new Lab();
				aLab._setPropertyChangeSupport(eventListeners);
				aLab.readNode(childNode);
				_Lab.add(aLab);
			} else if (childNodeName == "WebResource") {
				WebResource aWebResource = new WebResource();
				aWebResource._setPropertyChangeSupport(eventListeners);
				aWebResource.readNode(childNode);
				_WebResource.add(aWebResource);
			} else if (childNodeName == "LocalizationBundle") {
				LocalizationBundle aLocalizationBundle = new LocalizationBundle();
				aLocalizationBundle._setPropertyChangeSupport(eventListeners);
				aLocalizationBundle.readNode(childNode);
				addLocalizationBundle(aLocalizationBundle);
			} else {
				// Found extra unrecognized childNode
			}
		}
	}

	// Takes some text to be printed into an XML stream and escapes any
	// characters that might make it invalid XML (like '<').
	public static void writeXML(Writer out, String msg) throws IOException {
		writeXML(out, msg, true);
	}

	public static void writeXML(Writer out, String msg, boolean attribute) throws IOException {
		if (msg == null)
			return;
		int msgLength = msg.length();
		for (int i = 0; i < msgLength; ++i) {
			char c = msg.charAt(i);
			writeXML(out, c, attribute);
		}
	}

	public static void writeXML(Writer out, char msg, boolean attribute) throws IOException {
		if (msg == '&')
			out.write("&amp;");
		else if (msg == '<')
			out.write("&lt;");
		else if (msg == '>')
			out.write("&gt;");
		else if (attribute && msg == '"')
			out.write("&quot;");
		else if (attribute && msg == '\'')
			out.write("&apos;");
		else if (attribute && msg == '\n')
			out.write("&#xA;");
		else if (attribute && msg == '\t')
			out.write("&#x9;");
		else
			out.write(msg);
	}

	public static class ValidateException extends Exception {
		private CommonBean failedBean;
		private String failedPropertyName;

		public ValidateException(String msg, String failedPropertyName, CommonBean failedBean) {
			super(msg);
			this.failedBean = failedBean;
			this.failedPropertyName = failedPropertyName;
		}

		public String getFailedPropertyName() {
			return failedPropertyName;
		}

		public CommonBean getFailedBean() {
			return failedBean;
		}
	}

	public void validate() throws ValidateException {
		boolean restrictionFailure = false;
		// Validating property aboutPageLocationBundleKey
		/*
		 * if (getAboutPageLocationBundleKey() == null) { throw new
		 * ReCBaseUIConfig
		 * .ValidateException("getAboutPageLocationBundleKey() == null",
		 * "aboutPageLocationBundleKey", this); // NOI18N }
		 */

		// Validating property usersListRefreshRateMs
		if (getUsersListRefreshRateMs() < 0) {
			throw new ReCBaseUIConfig.ValidateException("getUsersListRefreshRateMs() < 0", "usersListRefreshRateMs",
					this); // NOI18N
		}

		// Validating property iconLocationBundleKey
		if (getIconLocationBundleKey() == null) {
			throw new ReCBaseUIConfig.ValidateException("getIconLocationBundleKey() == null", "iconLocationBundleKey",
					this); // NOI18N
		}
		// Validating property frameTitleBundleKey
		if (getFrameTitleBundleKey() == null) {
			throw new ReCBaseUIConfig.ValidateException("getFrameTitleBundleKey() == null", "frameTitleBundleKey", this); // NOI18N
		}
		// Validating property iconSponsorLocationBundleKey
		/*
		 * if (getIconSponsorLocationBundleKey() == null) { throw new
		 * ReCBaseUIConfig
		 * .ValidateException("getIconSponsorLocationBundleKey() == null",
		 * "iconSponsorLocationBundleKey", this); // NOI18N }
		 */
		// Validating property helpPageLocationBundleKey
		/*
		 * if (getHelpPageLocationBundleKey() == null) { throw new
		 * ReCBaseUIConfig
		 * .ValidateException("getHelpPageLocationBundleKey() == null",
		 * "helpPageLocationBundleKey", this); // NOI18N }
		 */
		// Validating property lab
		for (int _index = 0; _index < sizeLab(); ++_index) {
			Lab element = getLab(_index);
			if (element != null) {
				element.validate();
			}
		}
		if (sizeLab() < 1) {
			throw new ReCBaseUIConfig.ValidateException("At least one lab must exist!", "sizelab", this); // NOI18N
		}
		// Validating property webResource
		for (int _index = 0; _index < sizeWebResource(); ++_index) {
			WebResource element = getWebResource(_index);
			if (element != null) {
				element.validate();
			}
		}
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (eventListeners == null) {
			eventListeners = new PropertyChangeSupport(this);
		}
		eventListeners.addPropertyChangeListener(listener);
		for (Iterator it = _Lab.iterator(); it.hasNext();) {
			Lab element = (Lab) it.next();
			if (element != null) {
				element.addPropertyChangeListener(listener);
			}
		}
		for (Iterator it = _WebResource.iterator(); it.hasNext();) {
			WebResource element = (WebResource) it.next();
			if (element != null) {
				element.addPropertyChangeListener(listener);
			}
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (eventListeners == null) {
			return;
		}
		eventListeners.removePropertyChangeListener(listener);
		if (!eventListeners.hasListeners(null)) {
			eventListeners = null;
		}
	}

	public void _setPropertyChangeSupport(PropertyChangeSupport listeners) {
		eventListeners = listeners;
	}

	// Return an array of all of the properties that are beans and are set.
	public CommonBean[] childBeans(boolean recursive) {
		List<CommonBean> children = new LinkedList<CommonBean>();
		childBeans(recursive, children);
		CommonBean[] result = new CommonBean[children.size()];
		return (CommonBean[]) children.toArray(result);
	}

	// Put all child beans into the beans list.
	public void childBeans(boolean recursive, List<CommonBean> beans) {
		for (Iterator it = _Lab.iterator(); it.hasNext();) {
			Lab element = (Lab) it.next();
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
		for (Iterator it = _WebResource.iterator(); it.hasNext();) {
			WebResource element = (WebResource) it.next();
			if (element != null) {
				if (recursive) {
					element.childBeans(true, beans);
				}
				beans.add(element);
			}
		}
	}

	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ReCBaseUIConfig))
			return false;
		ReCBaseUIConfig inst = (ReCBaseUIConfig) o;
		if (!(_AboutPageLocationBundleKey == null ? inst._AboutPageLocationBundleKey == null
				: _AboutPageLocationBundleKey.equals(inst._AboutPageLocationBundleKey)))
			return false;
		if (!(_SplashIconLocationBundleKey == null ? inst._SplashIconLocationBundleKey == null
				: _SplashIconLocationBundleKey.equals(inst._SplashIconLocationBundleKey)))
			return false;
		if (!(_AutoConnectLab == inst._AutoConnectLab))
			return false;
		if (!(_ShowVideoFrame == inst._ShowVideoFrame))
			return false;
		if (!(_EnableVideoFrame == inst._EnableVideoFrame))
			return false;
		if (!(_EnableLoginPassword == inst._EnableLoginPassword))
			return false;
		if (!(_ShowChatFrame == inst._ShowChatFrame))
			return false;
		if (!(_EnableChatFrame == inst._EnableChatFrame))
			return false;
		if (!(_ShowUserList == inst._ShowUserList))
			return false;
		if (!(_EnableUsersList == inst._EnableUsersList))
			return false;
		if (!(_UsersListRefreshRateMs == inst._UsersListRefreshRateMs))
			return false;
		if (!(_EnterApparatusChatRoom == inst._EnterApparatusChatRoom))
			return false;
		if (!(_IconLocationBundleKey == null ? inst._IconLocationBundleKey == null : _IconLocationBundleKey
				.equals(inst._IconLocationBundleKey)))
			return false;
		if (!(_DesktopLocationBundleKey == null ? inst._DesktopLocationBundleKey == null : _DesktopLocationBundleKey
				.equals(inst._DesktopLocationBundleKey)))
			return false;
		if (!(_FrameTitleBundleKey == null ? inst._FrameTitleBundleKey == null : _FrameTitleBundleKey
				.equals(inst._FrameTitleBundleKey)))
			return false;
		if (!(_IconSponsorLocationBundleKey == null ? inst._IconSponsorLocationBundleKey == null
				: _IconSponsorLocationBundleKey.equals(inst._IconSponsorLocationBundleKey)))
			return false;
		if (!(_HelpPageLocationBundleKey == null ? inst._HelpPageLocationBundleKey == null : _HelpPageLocationBundleKey
				.equals(inst._HelpPageLocationBundleKey)))
			return false;
		if (sizeLab() != inst.sizeLab())
			return false;
		// Compare every element.
		for (Iterator it = _Lab.iterator(), it2 = inst._Lab.iterator(); it.hasNext() && it2.hasNext();) {
			Lab element = (Lab) it.next();
			Lab element2 = (Lab) it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}
		if (sizeWebResource() != inst.sizeWebResource())
			return false;
		// Compare every element.
		for (Iterator it = _WebResource.iterator(), it2 = inst._WebResource.iterator(); it.hasNext() && it2.hasNext();) {
			WebResource element = (WebResource) it.next();
			WebResource element2 = (WebResource) it2.next();
			if (!(element == null ? element2 == null : element.equals(element2)))
				return false;
		}

		return true;
	}

	public int hashCode() {
		long result = 17;
		result = 37 * result + (_AboutPageLocationBundleKey == null ? 0 : _AboutPageLocationBundleKey.hashCode());
		result = 37 * result + (_SplashIconLocationBundleKey == null ? 0 : _SplashIconLocationBundleKey.hashCode());
		result = 37 * result + (_AutoConnectLab ? 0 : 1);
		result = 37 * result + (_ShowVideoFrame ? 0 : 1);
		result = 37 * result + (_EnableVideoFrame ? 0 : 1);
		result = 37 * result + (_EnableLoginPassword ? 0 : 1);
		result = 37 * result + (_ShowChatFrame ? 0 : 1);
		result = 37 * result + (_EnableChatFrame ? 0 : 1);
		result = 37 * result + (_ShowUserList ? 0 : 1);
		result = 37 * result + (_EnableUsersList ? 0 : 1);
		result = 37 * result + (_UsersListRefreshRateMs < 0 ? 0 : _UsersListRefreshRateMs);
		result = 37 * result + (_EnterApparatusChatRoom ? 0 : 1);
		result = 37 * result + (_IconLocationBundleKey == null ? 0 : _IconLocationBundleKey.hashCode());
		result = 37 * result + (_DesktopLocationBundleKey == null ? 0 : _DesktopLocationBundleKey.hashCode());
		result = 37 * result + (_FrameTitleBundleKey == null ? 0 : _FrameTitleBundleKey.hashCode());
		result = 37 * result + (_IconSponsorLocationBundleKey == null ? 0 : _IconSponsorLocationBundleKey.hashCode());
		result = 37 * result + (_HelpPageLocationBundleKey == null ? 0 : _HelpPageLocationBundleKey.hashCode());
		result = 37 * result + (_Lab == null ? 0 : _Lab.hashCode());
		result = 37 * result + (_WebResource == null ? 0 : _WebResource.hashCode());
		return (int) result;
	}

	public String toString() {
		StringWriter sw = new StringWriter();
		try {
			writeNode(sw, "ReCBaseUIConfig", "");
		} catch (IOException e) {
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
