/*
 * AboutDialogBeanInfo.java
 *
 * Created on August 10, 2004, 11:15 AM
 */

package com.linkare.rec.impl.baseUI;

import java.beans.BeanDescriptor;
import java.beans.EventSetDescriptor;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;

/**
 * @author andre
 */
public class AboutDialogBeanInfo extends SimpleBeanInfo {

	// Bean descriptor //GEN-FIRST:BeanDescriptor
	/* lazy BeanDescriptor */
	private static BeanDescriptor getBdescriptor() {
		final BeanDescriptor beanDescriptor = new BeanDescriptor(AboutDialog.class, null);// GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

		return beanDescriptor;
	}// GEN-LAST:BeanDescriptor

	// Property identifiers //GEN-FIRST:Properties
	private static final int PROPERTY_accessibleContext = 0;
	private static final int PROPERTY_active = 1;
	private static final int PROPERTY_alignmentX = 2;
	private static final int PROPERTY_alignmentY = 3;
	private static final int PROPERTY_background = 4;
	private static final int PROPERTY_backgroundSet = 5;
	private static final int PROPERTY_bounds = 6;
	private static final int PROPERTY_bufferStrategy = 7;
	private static final int PROPERTY_colorModel = 8;
	private static final int PROPERTY_component = 9;
	private static final int PROPERTY_componentCount = 10;
	private static final int PROPERTY_componentListeners = 11;
	private static final int PROPERTY_componentOrientation = 12;
	private static final int PROPERTY_components = 13;
	private static final int PROPERTY_containerListeners = 14;
	private static final int PROPERTY_contentPane = 15;
	private static final int PROPERTY_cursor = 16;
	private static final int PROPERTY_cursorSet = 17;
	private static final int PROPERTY_defaultCloseOperation = 18;
	private static final int PROPERTY_displayable = 19;
	private static final int PROPERTY_doubleBuffered = 20;
	private static final int PROPERTY_dropTarget = 21;
	private static final int PROPERTY_enabled = 22;
	private static final int PROPERTY_focusable = 23;
	private static final int PROPERTY_focusableWindow = 24;
	private static final int PROPERTY_focusableWindowState = 25;
	private static final int PROPERTY_focusCycleRoot = 26;
	private static final int PROPERTY_focusCycleRootAncestor = 27;
	private static final int PROPERTY_focused = 28;
	private static final int PROPERTY_focusListeners = 29;
	private static final int PROPERTY_focusOwner = 30;
	private static final int PROPERTY_focusTraversable = 31;
	private static final int PROPERTY_focusTraversalKeys = 32;
	private static final int PROPERTY_focusTraversalKeysEnabled = 33;
	private static final int PROPERTY_focusTraversalPolicy = 34;
	private static final int PROPERTY_focusTraversalPolicySet = 35;
	private static final int PROPERTY_font = 36;
	private static final int PROPERTY_fontSet = 37;
	private static final int PROPERTY_foreground = 38;
	private static final int PROPERTY_foregroundSet = 39;
	private static final int PROPERTY_glassPane = 40;
	private static final int PROPERTY_graphics = 41;
	private static final int PROPERTY_graphicsConfiguration = 42;
	private static final int PROPERTY_height = 43;
	private static final int PROPERTY_hierarchyBoundsListeners = 44;
	private static final int PROPERTY_hierarchyListeners = 45;
	private static final int PROPERTY_ignoreRepaint = 46;
	private static final int PROPERTY_inputContext = 47;
	private static final int PROPERTY_inputMethodListeners = 48;
	private static final int PROPERTY_inputMethodRequests = 49;
	private static final int PROPERTY_insets = 50;
	private static final int PROPERTY_JMenuBar = 51;
	private static final int PROPERTY_keyListeners = 52;
	private static final int PROPERTY_layeredPane = 53;
	private static final int PROPERTY_layout = 54;
	private static final int PROPERTY_lightweight = 55;
	private static final int PROPERTY_locale = 56;
	private static final int PROPERTY_locationOnScreen = 57;
	private static final int PROPERTY_locationRelativeTo = 58;
	private static final int PROPERTY_maximumSize = 59;
	private static final int PROPERTY_minimumSize = 60;
	private static final int PROPERTY_modal = 61;
	private static final int PROPERTY_mostRecentFocusOwner = 62;
	private static final int PROPERTY_mouseListeners = 63;
	private static final int PROPERTY_mouseMotionListeners = 64;
	private static final int PROPERTY_mouseWheelListeners = 65;
	private static final int PROPERTY_name = 66;
	private static final int PROPERTY_opaque = 67;
	private static final int PROPERTY_ownedWindows = 68;
	private static final int PROPERTY_owner = 69;
	private static final int PROPERTY_parent = 70;
	private static final int PROPERTY_peer = 71;
	private static final int PROPERTY_preferredSize = 72;
	private static final int PROPERTY_propertyChangeListeners = 73;
	private static final int PROPERTY_resizable = 74;
	private static final int PROPERTY_rootPane = 75;
	private static final int PROPERTY_showing = 76;
	private static final int PROPERTY_title = 77;
	private static final int PROPERTY_toolkit = 78;
	private static final int PROPERTY_treeLock = 79;
	private static final int PROPERTY_undecorated = 80;
	private static final int PROPERTY_valid = 81;
	private static final int PROPERTY_visible = 82;
	private static final int PROPERTY_warningString = 83;
	private static final int PROPERTY_width = 84;
	private static final int PROPERTY_windowFocusListeners = 85;
	private static final int PROPERTY_windowListeners = 86;
	private static final int PROPERTY_windowStateListeners = 87;
	private static final int PROPERTY_x = 88;
	private static final int PROPERTY_y = 89;

	// Property array
	/* lazy PropertyDescriptor */
	private static PropertyDescriptor[] getPdescriptor() {
		final PropertyDescriptor[] properties = new PropertyDescriptor[90];

		try {
			properties[AboutDialogBeanInfo.PROPERTY_accessibleContext] = new PropertyDescriptor("accessibleContext",
					AboutDialog.class, "getAccessibleContext", null);
			properties[AboutDialogBeanInfo.PROPERTY_active] = new PropertyDescriptor("active", AboutDialog.class,
					"isActive", null);
			properties[AboutDialogBeanInfo.PROPERTY_alignmentX] = new PropertyDescriptor("alignmentX",
					AboutDialog.class, "getAlignmentX", null);
			properties[AboutDialogBeanInfo.PROPERTY_alignmentY] = new PropertyDescriptor("alignmentY",
					AboutDialog.class, "getAlignmentY", null);
			properties[AboutDialogBeanInfo.PROPERTY_background] = new PropertyDescriptor("background",
					AboutDialog.class, "getBackground", "setBackground");
			properties[AboutDialogBeanInfo.PROPERTY_backgroundSet] = new PropertyDescriptor("backgroundSet",
					AboutDialog.class, "isBackgroundSet", null);
			properties[AboutDialogBeanInfo.PROPERTY_bounds] = new PropertyDescriptor("bounds", AboutDialog.class,
					"getBounds", "setBounds");
			properties[AboutDialogBeanInfo.PROPERTY_bufferStrategy] = new PropertyDescriptor("bufferStrategy",
					AboutDialog.class, "getBufferStrategy", null);
			properties[AboutDialogBeanInfo.PROPERTY_colorModel] = new PropertyDescriptor("colorModel",
					AboutDialog.class, "getColorModel", null);
			properties[AboutDialogBeanInfo.PROPERTY_component] = new IndexedPropertyDescriptor("component",
					AboutDialog.class, null, null, "getComponent", null);
			properties[AboutDialogBeanInfo.PROPERTY_componentCount] = new PropertyDescriptor("componentCount",
					AboutDialog.class, "getComponentCount", null);
			properties[AboutDialogBeanInfo.PROPERTY_componentListeners] = new PropertyDescriptor("componentListeners",
					AboutDialog.class, "getComponentListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_componentOrientation] = new PropertyDescriptor(
					"componentOrientation", AboutDialog.class, "getComponentOrientation", "setComponentOrientation");
			properties[AboutDialogBeanInfo.PROPERTY_components] = new PropertyDescriptor("components",
					AboutDialog.class, "getComponents", null);
			properties[AboutDialogBeanInfo.PROPERTY_containerListeners] = new PropertyDescriptor("containerListeners",
					AboutDialog.class, "getContainerListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_contentPane] = new PropertyDescriptor("contentPane",
					AboutDialog.class, "getContentPane", "setContentPane");
			properties[AboutDialogBeanInfo.PROPERTY_cursor] = new PropertyDescriptor("cursor", AboutDialog.class,
					"getCursor", "setCursor");
			properties[AboutDialogBeanInfo.PROPERTY_cursorSet] = new PropertyDescriptor("cursorSet", AboutDialog.class,
					"isCursorSet", null);
			properties[AboutDialogBeanInfo.PROPERTY_defaultCloseOperation] = new PropertyDescriptor(
					"defaultCloseOperation", AboutDialog.class, "getDefaultCloseOperation", "setDefaultCloseOperation");
			properties[AboutDialogBeanInfo.PROPERTY_displayable] = new PropertyDescriptor("displayable",
					AboutDialog.class, "isDisplayable", null);
			properties[AboutDialogBeanInfo.PROPERTY_doubleBuffered] = new PropertyDescriptor("doubleBuffered",
					AboutDialog.class, "isDoubleBuffered", null);
			properties[AboutDialogBeanInfo.PROPERTY_dropTarget] = new PropertyDescriptor("dropTarget",
					AboutDialog.class, "getDropTarget", "setDropTarget");
			properties[AboutDialogBeanInfo.PROPERTY_enabled] = new PropertyDescriptor("enabled", AboutDialog.class,
					"isEnabled", "setEnabled");
			properties[AboutDialogBeanInfo.PROPERTY_focusable] = new PropertyDescriptor("focusable", AboutDialog.class,
					"isFocusable", "setFocusable");
			properties[AboutDialogBeanInfo.PROPERTY_focusableWindow] = new PropertyDescriptor("focusableWindow",
					AboutDialog.class, "isFocusableWindow", null);
			properties[AboutDialogBeanInfo.PROPERTY_focusableWindowState] = new PropertyDescriptor(
					"focusableWindowState", AboutDialog.class, "getFocusableWindowState", "setFocusableWindowState");
			properties[AboutDialogBeanInfo.PROPERTY_focusCycleRoot] = new PropertyDescriptor("focusCycleRoot",
					AboutDialog.class, "isFocusCycleRoot", "setFocusCycleRoot");
			properties[AboutDialogBeanInfo.PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor(
					"focusCycleRootAncestor", AboutDialog.class, "getFocusCycleRootAncestor", null);
			properties[AboutDialogBeanInfo.PROPERTY_focused] = new PropertyDescriptor("focused", AboutDialog.class,
					"isFocused", null);
			properties[AboutDialogBeanInfo.PROPERTY_focusListeners] = new PropertyDescriptor("focusListeners",
					AboutDialog.class, "getFocusListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_focusOwner] = new PropertyDescriptor("focusOwner",
					AboutDialog.class, "isFocusOwner", null);
			properties[AboutDialogBeanInfo.PROPERTY_focusTraversable] = new PropertyDescriptor("focusTraversable",
					AboutDialog.class, "isFocusTraversable", null);
			properties[AboutDialogBeanInfo.PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor(
					"focusTraversalKeys", AboutDialog.class, null, null, "getFocusTraversalKeys",
					"setFocusTraversalKeys");
			properties[AboutDialogBeanInfo.PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor(
					"focusTraversalKeysEnabled", AboutDialog.class, "getFocusTraversalKeysEnabled",
					"setFocusTraversalKeysEnabled");
			properties[AboutDialogBeanInfo.PROPERTY_focusTraversalPolicy] = new PropertyDescriptor(
					"focusTraversalPolicy", AboutDialog.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy");
			properties[AboutDialogBeanInfo.PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor(
					"focusTraversalPolicySet", AboutDialog.class, "isFocusTraversalPolicySet", null);
			properties[AboutDialogBeanInfo.PROPERTY_font] = new PropertyDescriptor("font", AboutDialog.class,
					"getFont", "setFont");
			properties[AboutDialogBeanInfo.PROPERTY_fontSet] = new PropertyDescriptor("fontSet", AboutDialog.class,
					"isFontSet", null);
			properties[AboutDialogBeanInfo.PROPERTY_foreground] = new PropertyDescriptor("foreground",
					AboutDialog.class, "getForeground", "setForeground");
			properties[AboutDialogBeanInfo.PROPERTY_foregroundSet] = new PropertyDescriptor("foregroundSet",
					AboutDialog.class, "isForegroundSet", null);
			properties[AboutDialogBeanInfo.PROPERTY_glassPane] = new PropertyDescriptor("glassPane", AboutDialog.class,
					"getGlassPane", "setGlassPane");
			properties[AboutDialogBeanInfo.PROPERTY_graphics] = new PropertyDescriptor("graphics", AboutDialog.class,
					"getGraphics", null);
			properties[AboutDialogBeanInfo.PROPERTY_graphicsConfiguration] = new PropertyDescriptor(
					"graphicsConfiguration", AboutDialog.class, "getGraphicsConfiguration", null);
			properties[AboutDialogBeanInfo.PROPERTY_height] = new PropertyDescriptor("height", AboutDialog.class,
					"getHeight", null);
			properties[AboutDialogBeanInfo.PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor(
					"hierarchyBoundsListeners", AboutDialog.class, "getHierarchyBoundsListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_hierarchyListeners] = new PropertyDescriptor("hierarchyListeners",
					AboutDialog.class, "getHierarchyListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_ignoreRepaint] = new PropertyDescriptor("ignoreRepaint",
					AboutDialog.class, "getIgnoreRepaint", "setIgnoreRepaint");
			properties[AboutDialogBeanInfo.PROPERTY_inputContext] = new PropertyDescriptor("inputContext",
					AboutDialog.class, "getInputContext", null);
			properties[AboutDialogBeanInfo.PROPERTY_inputMethodListeners] = new PropertyDescriptor(
					"inputMethodListeners", AboutDialog.class, "getInputMethodListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_inputMethodRequests] = new PropertyDescriptor(
					"inputMethodRequests", AboutDialog.class, "getInputMethodRequests", null);
			properties[AboutDialogBeanInfo.PROPERTY_insets] = new PropertyDescriptor("insets", AboutDialog.class,
					"getInsets", null);
			properties[AboutDialogBeanInfo.PROPERTY_JMenuBar] = new PropertyDescriptor("JMenuBar", AboutDialog.class,
					"getJMenuBar", "setJMenuBar");
			properties[AboutDialogBeanInfo.PROPERTY_keyListeners] = new PropertyDescriptor("keyListeners",
					AboutDialog.class, "getKeyListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_layeredPane] = new PropertyDescriptor("layeredPane",
					AboutDialog.class, "getLayeredPane", "setLayeredPane");
			properties[AboutDialogBeanInfo.PROPERTY_layout] = new PropertyDescriptor("layout", AboutDialog.class,
					"getLayout", "setLayout");
			properties[AboutDialogBeanInfo.PROPERTY_lightweight] = new PropertyDescriptor("lightweight",
					AboutDialog.class, "isLightweight", null);
			properties[AboutDialogBeanInfo.PROPERTY_locale] = new PropertyDescriptor("locale", AboutDialog.class,
					"getLocale", "setLocale");
			properties[AboutDialogBeanInfo.PROPERTY_locationOnScreen] = new PropertyDescriptor("locationOnScreen",
					AboutDialog.class, "getLocationOnScreen", null);
			properties[AboutDialogBeanInfo.PROPERTY_locationRelativeTo] = new PropertyDescriptor("locationRelativeTo",
					AboutDialog.class, null, "setLocationRelativeTo");
			properties[AboutDialogBeanInfo.PROPERTY_maximumSize] = new PropertyDescriptor("maximumSize",
					AboutDialog.class, "getMaximumSize", null);
			properties[AboutDialogBeanInfo.PROPERTY_minimumSize] = new PropertyDescriptor("minimumSize",
					AboutDialog.class, "getMinimumSize", null);
			properties[AboutDialogBeanInfo.PROPERTY_modal] = new PropertyDescriptor("modal", AboutDialog.class,
					"isModal", "setModal");
			properties[AboutDialogBeanInfo.PROPERTY_mostRecentFocusOwner] = new PropertyDescriptor(
					"mostRecentFocusOwner", AboutDialog.class, "getMostRecentFocusOwner", null);
			properties[AboutDialogBeanInfo.PROPERTY_mouseListeners] = new PropertyDescriptor("mouseListeners",
					AboutDialog.class, "getMouseListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_mouseMotionListeners] = new PropertyDescriptor(
					"mouseMotionListeners", AboutDialog.class, "getMouseMotionListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_mouseWheelListeners] = new PropertyDescriptor(
					"mouseWheelListeners", AboutDialog.class, "getMouseWheelListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_name] = new PropertyDescriptor("name", AboutDialog.class,
					"getName", "setName");
			properties[AboutDialogBeanInfo.PROPERTY_opaque] = new PropertyDescriptor("opaque", AboutDialog.class,
					"isOpaque", null);
			properties[AboutDialogBeanInfo.PROPERTY_ownedWindows] = new PropertyDescriptor("ownedWindows",
					AboutDialog.class, "getOwnedWindows", null);
			properties[AboutDialogBeanInfo.PROPERTY_owner] = new PropertyDescriptor("owner", AboutDialog.class,
					"getOwner", null);
			properties[AboutDialogBeanInfo.PROPERTY_parent] = new PropertyDescriptor("parent", AboutDialog.class,
					"getParent", null);
			properties[AboutDialogBeanInfo.PROPERTY_peer] = new PropertyDescriptor("peer", AboutDialog.class,
					"getPeer", null);
			properties[AboutDialogBeanInfo.PROPERTY_preferredSize] = new PropertyDescriptor("preferredSize",
					AboutDialog.class, "getPreferredSize", null);
			properties[AboutDialogBeanInfo.PROPERTY_propertyChangeListeners] = new PropertyDescriptor(
					"propertyChangeListeners", AboutDialog.class, "getPropertyChangeListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_resizable] = new PropertyDescriptor("resizable", AboutDialog.class,
					"isResizable", "setResizable");
			properties[AboutDialogBeanInfo.PROPERTY_rootPane] = new PropertyDescriptor("rootPane", AboutDialog.class,
					"getRootPane", null);
			properties[AboutDialogBeanInfo.PROPERTY_showing] = new PropertyDescriptor("showing", AboutDialog.class,
					"isShowing", null);
			properties[AboutDialogBeanInfo.PROPERTY_title] = new PropertyDescriptor("title", AboutDialog.class,
					"getTitle", "setTitle");
			properties[AboutDialogBeanInfo.PROPERTY_toolkit] = new PropertyDescriptor("toolkit", AboutDialog.class,
					"getToolkit", null);
			properties[AboutDialogBeanInfo.PROPERTY_treeLock] = new PropertyDescriptor("treeLock", AboutDialog.class,
					"getTreeLock", null);
			properties[AboutDialogBeanInfo.PROPERTY_undecorated] = new PropertyDescriptor("undecorated",
					AboutDialog.class, "isUndecorated", "setUndecorated");
			properties[AboutDialogBeanInfo.PROPERTY_valid] = new PropertyDescriptor("valid", AboutDialog.class,
					"isValid", null);
			properties[AboutDialogBeanInfo.PROPERTY_visible] = new PropertyDescriptor("visible", AboutDialog.class,
					"isVisible", "setVisible");
			properties[AboutDialogBeanInfo.PROPERTY_warningString] = new PropertyDescriptor("warningString",
					AboutDialog.class, "getWarningString", null);
			properties[AboutDialogBeanInfo.PROPERTY_width] = new PropertyDescriptor("width", AboutDialog.class,
					"getWidth", null);
			properties[AboutDialogBeanInfo.PROPERTY_windowFocusListeners] = new PropertyDescriptor(
					"windowFocusListeners", AboutDialog.class, "getWindowFocusListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_windowListeners] = new PropertyDescriptor("windowListeners",
					AboutDialog.class, "getWindowListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_windowStateListeners] = new PropertyDescriptor(
					"windowStateListeners", AboutDialog.class, "getWindowStateListeners", null);
			properties[AboutDialogBeanInfo.PROPERTY_x] = new PropertyDescriptor("x", AboutDialog.class, "getX", null);
			properties[AboutDialogBeanInfo.PROPERTY_y] = new PropertyDescriptor("y", AboutDialog.class, "getY", null);
		} catch (final IntrospectionException e) {
		}// GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

		return properties;
	}// GEN-LAST:Properties

	// EventSet identifiers//GEN-FIRST:Events
	private static final int EVENT_componentListener = 0;
	private static final int EVENT_containerListener = 1;
	private static final int EVENT_focusListener = 2;
	private static final int EVENT_hierarchyBoundsListener = 3;
	private static final int EVENT_hierarchyListener = 4;
	private static final int EVENT_inputMethodListener = 5;
	private static final int EVENT_keyListener = 6;
	private static final int EVENT_mouseListener = 7;
	private static final int EVENT_mouseMotionListener = 8;
	private static final int EVENT_mouseWheelListener = 9;
	private static final int EVENT_propertyChangeListener = 10;
	private static final int EVENT_windowFocusListener = 11;
	private static final int EVENT_windowListener = 12;
	private static final int EVENT_windowStateListener = 13;

	// EventSet array
	/* lazy EventSetDescriptor */
	private static EventSetDescriptor[] getEdescriptor() {
		final EventSetDescriptor[] eventSets = new EventSetDescriptor[14];

		try {
			eventSets[AboutDialogBeanInfo.EVENT_componentListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "componentListener",
					java.awt.event.ComponentListener.class, new String[] { "componentHidden", "componentMoved",
							"componentResized", "componentShown" }, "addComponentListener", "removeComponentListener");
			eventSets[AboutDialogBeanInfo.EVENT_containerListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "containerListener",
					java.awt.event.ContainerListener.class, new String[] { "componentAdded", "componentRemoved" },
					"addContainerListener", "removeContainerListener");
			eventSets[AboutDialogBeanInfo.EVENT_focusListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "focusListener", java.awt.event.FocusListener.class,
					new String[] { "focusGained", "focusLost" }, "addFocusListener", "removeFocusListener");
			eventSets[AboutDialogBeanInfo.EVENT_hierarchyBoundsListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "hierarchyBoundsListener",
					java.awt.event.HierarchyBoundsListener.class, new String[] { "ancestorMoved", "ancestorResized" },
					"addHierarchyBoundsListener", "removeHierarchyBoundsListener");
			eventSets[AboutDialogBeanInfo.EVENT_hierarchyListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "hierarchyListener",
					java.awt.event.HierarchyListener.class, new String[] { "hierarchyChanged" },
					"addHierarchyListener", "removeHierarchyListener");
			eventSets[AboutDialogBeanInfo.EVENT_inputMethodListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "inputMethodListener",
					java.awt.event.InputMethodListener.class, new String[] { "caretPositionChanged",
							"inputMethodTextChanged" }, "addInputMethodListener", "removeInputMethodListener");
			eventSets[AboutDialogBeanInfo.EVENT_keyListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "keyListener", java.awt.event.KeyListener.class,
					new String[] { "keyPressed", "keyReleased", "keyTyped" }, "addKeyListener", "removeKeyListener");
			eventSets[AboutDialogBeanInfo.EVENT_mouseListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "mouseListener", java.awt.event.MouseListener.class,
					new String[] { "mouseClicked", "mouseEntered", "mouseExited", "mousePressed", "mouseReleased" },
					"addMouseListener", "removeMouseListener");
			eventSets[AboutDialogBeanInfo.EVENT_mouseMotionListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "mouseMotionListener",
					java.awt.event.MouseMotionListener.class, new String[] { "mouseDragged", "mouseMoved" },
					"addMouseMotionListener", "removeMouseMotionListener");
			eventSets[AboutDialogBeanInfo.EVENT_mouseWheelListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "mouseWheelListener",
					java.awt.event.MouseWheelListener.class, new String[] { "mouseWheelMoved" },
					"addMouseWheelListener", "removeMouseWheelListener");
			eventSets[AboutDialogBeanInfo.EVENT_propertyChangeListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "propertyChangeListener",
					java.beans.PropertyChangeListener.class, new String[] { "propertyChange" },
					"addPropertyChangeListener", "removePropertyChangeListener");
			eventSets[AboutDialogBeanInfo.EVENT_windowFocusListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "windowFocusListener",
					java.awt.event.WindowFocusListener.class, new String[] { "windowGainedFocus", "windowLostFocus" },
					"addWindowFocusListener", "removeWindowFocusListener");
			eventSets[AboutDialogBeanInfo.EVENT_windowListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "windowListener",
					java.awt.event.WindowListener.class, new String[] { "windowActivated", "windowClosed",
							"windowClosing", "windowDeactivated", "windowDeiconified", "windowIconified",
							"windowOpened" }, "addWindowListener", "removeWindowListener");
			eventSets[AboutDialogBeanInfo.EVENT_windowStateListener] = new EventSetDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class, "windowStateListener",
					java.awt.event.WindowStateListener.class, new String[] { "windowStateChanged" },
					"addWindowStateListener", "removeWindowStateListener");
		} catch (final IntrospectionException e) {
		}// GEN-HEADEREND:Events

		// Here you can add code for customizing the event sets array.

		return eventSets;
	}// GEN-LAST:Events

	// Method identifiers //GEN-FIRST:Methods
	private static final int METHOD_action0 = 0;
	private static final int METHOD_add1 = 1;
	private static final int METHOD_addNotify2 = 2;
	private static final int METHOD_addPropertyChangeListener3 = 3;
	private static final int METHOD_applyComponentOrientation4 = 4;
	private static final int METHOD_applyResourceBundle5 = 5;
	private static final int METHOD_areFocusTraversalKeysSet6 = 6;
	private static final int METHOD_bounds7 = 7;
	private static final int METHOD_checkImage8 = 8;
	private static final int METHOD_contains9 = 9;
	private static final int METHOD_countComponents10 = 10;
	private static final int METHOD_createBufferStrategy11 = 11;
	private static final int METHOD_createImage12 = 12;
	private static final int METHOD_createVolatileImage13 = 13;
	private static final int METHOD_deliverEvent14 = 14;
	private static final int METHOD_disable15 = 15;
	private static final int METHOD_dispatchEvent16 = 16;
	private static final int METHOD_dispose17 = 17;
	private static final int METHOD_doLayout18 = 18;
	private static final int METHOD_enable19 = 19;
	private static final int METHOD_enableInputMethods20 = 20;
	private static final int METHOD_findComponentAt21 = 21;
	private static final int METHOD_getBounds22 = 22;
	private static final int METHOD_getComponentAt23 = 23;
	private static final int METHOD_getFocusOwner24 = 24;
	private static final int METHOD_getFontMetrics25 = 25;
	private static final int METHOD_getListeners26 = 26;
	private static final int METHOD_getLocation27 = 27;
	private static final int METHOD_getPropertyChangeListeners28 = 28;
	private static final int METHOD_getSize29 = 29;
	private static final int METHOD_gotFocus30 = 30;
	private static final int METHOD_handleEvent31 = 31;
	private static final int METHOD_hasFocus32 = 32;
	private static final int METHOD_hide33 = 33;
	private static final int METHOD_imageUpdate34 = 34;
	private static final int METHOD_insets35 = 35;
	private static final int METHOD_inside36 = 36;
	private static final int METHOD_invalidate37 = 37;
	private static final int METHOD_isAncestorOf38 = 38;
	private static final int METHOD_isDefaultLookAndFeelDecorated39 = 39;
	private static final int METHOD_isFocusCycleRoot40 = 40;
	private static final int METHOD_keyDown41 = 41;
	private static final int METHOD_keyUp42 = 42;
	private static final int METHOD_layout43 = 43;
	private static final int METHOD_list44 = 44;
	private static final int METHOD_locate45 = 45;
	private static final int METHOD_location46 = 46;
	private static final int METHOD_lostFocus47 = 47;
	private static final int METHOD_minimumSize48 = 48;
	private static final int METHOD_mouseDown49 = 49;
	private static final int METHOD_mouseDrag50 = 50;
	private static final int METHOD_mouseEnter51 = 51;
	private static final int METHOD_mouseExit52 = 52;
	private static final int METHOD_mouseMove53 = 53;
	private static final int METHOD_mouseUp54 = 54;
	private static final int METHOD_move55 = 55;
	private static final int METHOD_nextFocus56 = 56;
	private static final int METHOD_pack57 = 57;
	private static final int METHOD_paint58 = 58;
	private static final int METHOD_paintAll59 = 59;
	private static final int METHOD_paintComponents60 = 60;
	private static final int METHOD_postEvent61 = 61;
	private static final int METHOD_preferredSize62 = 62;
	private static final int METHOD_prepareImage63 = 63;
	private static final int METHOD_print64 = 64;
	private static final int METHOD_printAll65 = 65;
	private static final int METHOD_printComponents66 = 66;
	private static final int METHOD_remove67 = 67;
	private static final int METHOD_removeAll68 = 68;
	private static final int METHOD_removeNotify69 = 69;
	private static final int METHOD_removePropertyChangeListener70 = 70;
	private static final int METHOD_repaint71 = 71;
	private static final int METHOD_requestFocus72 = 72;
	private static final int METHOD_requestFocusInWindow73 = 73;
	private static final int METHOD_reshape74 = 74;
	private static final int METHOD_resize75 = 75;
	private static final int METHOD_setBounds76 = 76;
	private static final int METHOD_setDefaultLookAndFeelDecorated77 = 77;
	private static final int METHOD_setLocation78 = 78;
	private static final int METHOD_setSize79 = 79;
	private static final int METHOD_show80 = 80;
	private static final int METHOD_size81 = 81;
	private static final int METHOD_toBack82 = 82;
	private static final int METHOD_toFront83 = 83;
	private static final int METHOD_toString84 = 84;
	private static final int METHOD_transferFocus85 = 85;
	private static final int METHOD_transferFocusBackward86 = 86;
	private static final int METHOD_transferFocusDownCycle87 = 87;
	private static final int METHOD_transferFocusUpCycle88 = 88;
	private static final int METHOD_update89 = 89;
	private static final int METHOD_validate90 = 90;

	// Method array
	/* lazy MethodDescriptor */
	private static MethodDescriptor[] getMdescriptor() {
		final MethodDescriptor[] methods = new MethodDescriptor[91];

		try {
			methods[AboutDialogBeanInfo.METHOD_action0] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("action", new Class[] {
							java.awt.Event.class, java.lang.Object.class }));
			methods[AboutDialogBeanInfo.METHOD_action0].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_add1] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("add",
							new Class[] { java.awt.Component.class }));
			methods[AboutDialogBeanInfo.METHOD_add1].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_addNotify2] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("addNotify", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_addNotify2].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_addPropertyChangeListener3] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("addPropertyChangeListener", new Class[] {
							java.lang.String.class, java.beans.PropertyChangeListener.class }));
			methods[AboutDialogBeanInfo.METHOD_addPropertyChangeListener3].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_applyComponentOrientation4] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("applyComponentOrientation",
							new Class[] { java.awt.ComponentOrientation.class }));
			methods[AboutDialogBeanInfo.METHOD_applyComponentOrientation4].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_applyResourceBundle5] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("applyResourceBundle",
							new Class[] { java.util.ResourceBundle.class }));
			methods[AboutDialogBeanInfo.METHOD_applyResourceBundle5].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_areFocusTraversalKeysSet6] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("areFocusTraversalKeysSet",
							new Class[] { Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_areFocusTraversalKeysSet6].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_bounds7] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("bounds", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_bounds7].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_checkImage8] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("checkImage", new Class[] {
							java.awt.Image.class, java.awt.image.ImageObserver.class }));
			methods[AboutDialogBeanInfo.METHOD_checkImage8].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_contains9] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("contains", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_contains9].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_countComponents10] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("countComponents", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_countComponents10].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_createBufferStrategy11] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("createBufferStrategy",
							new Class[] { Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_createBufferStrategy11].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_createImage12] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("createImage",
							new Class[] { java.awt.image.ImageProducer.class }));
			methods[AboutDialogBeanInfo.METHOD_createImage12].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_createVolatileImage13] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("createVolatileImage", new Class[] {
							Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_createVolatileImage13].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_deliverEvent14] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("deliverEvent",
							new Class[] { java.awt.Event.class }));
			methods[AboutDialogBeanInfo.METHOD_deliverEvent14].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_disable15] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("disable", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_disable15].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_dispatchEvent16] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("dispatchEvent",
							new Class[] { java.awt.AWTEvent.class }));
			methods[AboutDialogBeanInfo.METHOD_dispatchEvent16].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_dispose17] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("dispose", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_dispose17].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_doLayout18] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("doLayout", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_doLayout18].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_enable19] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("enable", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_enable19].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_enableInputMethods20] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("enableInputMethods",
							new Class[] { Boolean.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_enableInputMethods20].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_findComponentAt21] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("findComponentAt", new Class[] {
							Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_findComponentAt21].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getBounds22] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getBounds",
							new Class[] { java.awt.Rectangle.class }));
			methods[AboutDialogBeanInfo.METHOD_getBounds22].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getComponentAt23] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getComponentAt", new Class[] {
							Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_getComponentAt23].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getFocusOwner24] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getFocusOwner", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_getFocusOwner24].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getFontMetrics25] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getFontMetrics",
							new Class[] { java.awt.Font.class }));
			methods[AboutDialogBeanInfo.METHOD_getFontMetrics25].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getListeners26] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getListeners",
							new Class[] { java.lang.Class.class }));
			methods[AboutDialogBeanInfo.METHOD_getListeners26].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getLocation27] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getLocation", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_getLocation27].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getPropertyChangeListeners28] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getPropertyChangeListeners",
							new Class[] { java.lang.String.class }));
			methods[AboutDialogBeanInfo.METHOD_getPropertyChangeListeners28].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_getSize29] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("getSize", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_getSize29].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_gotFocus30] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("gotFocus", new Class[] {
							java.awt.Event.class, java.lang.Object.class }));
			methods[AboutDialogBeanInfo.METHOD_gotFocus30].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_handleEvent31] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("handleEvent",
							new Class[] { java.awt.Event.class }));
			methods[AboutDialogBeanInfo.METHOD_handleEvent31].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_hasFocus32] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("hasFocus", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_hasFocus32].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_hide33] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("hide", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_hide33].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_imageUpdate34] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("imageUpdate",
							new Class[] { java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE,
									Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_imageUpdate34].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_insets35] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("insets", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_insets35].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_inside36] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("inside", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_inside36].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_invalidate37] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("invalidate", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_invalidate37].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_isAncestorOf38] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("isAncestorOf",
							new Class[] { java.awt.Component.class }));
			methods[AboutDialogBeanInfo.METHOD_isAncestorOf38].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_isDefaultLookAndFeelDecorated39] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("isDefaultLookAndFeelDecorated",
							new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_isDefaultLookAndFeelDecorated39].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_isFocusCycleRoot40] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("isFocusCycleRoot",
							new Class[] { java.awt.Container.class }));
			methods[AboutDialogBeanInfo.METHOD_isFocusCycleRoot40].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_keyDown41] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("keyDown", new Class[] {
							java.awt.Event.class, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_keyDown41].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_keyUp42] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("keyUp", new Class[] {
							java.awt.Event.class, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_keyUp42].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_layout43] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("layout", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_layout43].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_list44] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("list", new Class[] {
							java.io.PrintStream.class, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_list44].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_locate45] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("locate", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_locate45].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_location46] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("location", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_location46].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_lostFocus47] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("lostFocus", new Class[] {
							java.awt.Event.class, java.lang.Object.class }));
			methods[AboutDialogBeanInfo.METHOD_lostFocus47].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_minimumSize48] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("minimumSize", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_minimumSize48].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_mouseDown49] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("mouseDown", new Class[] {
							java.awt.Event.class, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_mouseDown49].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_mouseDrag50] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("mouseDrag", new Class[] {
							java.awt.Event.class, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_mouseDrag50].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_mouseEnter51] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("mouseEnter", new Class[] {
							java.awt.Event.class, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_mouseEnter51].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_mouseExit52] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("mouseExit", new Class[] {
							java.awt.Event.class, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_mouseExit52].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_mouseMove53] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("mouseMove", new Class[] {
							java.awt.Event.class, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_mouseMove53].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_mouseUp54] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("mouseUp", new Class[] {
							java.awt.Event.class, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_mouseUp54].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_move55] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("move", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_move55].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_nextFocus56] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("nextFocus", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_nextFocus56].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_pack57] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("pack", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_pack57].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_paint58] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("paint",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_paint58].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_paintAll59] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("paintAll",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_paintAll59].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_paintComponents60] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("paintComponents",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_paintComponents60].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_postEvent61] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("postEvent",
							new Class[] { java.awt.Event.class }));
			methods[AboutDialogBeanInfo.METHOD_postEvent61].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_preferredSize62] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("preferredSize", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_preferredSize62].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_prepareImage63] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("prepareImage", new Class[] {
							java.awt.Image.class, java.awt.image.ImageObserver.class }));
			methods[AboutDialogBeanInfo.METHOD_prepareImage63].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_print64] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("print",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_print64].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_printAll65] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("printAll",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_printAll65].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_printComponents66] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("printComponents",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_printComponents66].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_remove67] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("remove",
							new Class[] { java.awt.Component.class }));
			methods[AboutDialogBeanInfo.METHOD_remove67].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_removeAll68] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("removeAll", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_removeAll68].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_removeNotify69] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("removeNotify", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_removeNotify69].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_removePropertyChangeListener70] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("removePropertyChangeListener",
							new Class[] { java.lang.String.class, java.beans.PropertyChangeListener.class }));
			methods[AboutDialogBeanInfo.METHOD_removePropertyChangeListener70].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_repaint71] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("repaint", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_repaint71].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_requestFocus72] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("requestFocus", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_requestFocus72].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_requestFocusInWindow73] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("requestFocusInWindow", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_requestFocusInWindow73].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_reshape74] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("reshape", new Class[] { Integer.TYPE,
							Integer.TYPE, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_reshape74].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_resize75] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("resize", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_resize75].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_setBounds76] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("setBounds", new Class[] { Integer.TYPE,
							Integer.TYPE, Integer.TYPE, Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_setBounds76].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_setDefaultLookAndFeelDecorated77] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("setDefaultLookAndFeelDecorated",
							new Class[] { Boolean.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_setDefaultLookAndFeelDecorated77].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_setLocation78] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("setLocation", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_setLocation78].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_setSize79] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("setSize", new Class[] { Integer.TYPE,
							Integer.TYPE }));
			methods[AboutDialogBeanInfo.METHOD_setSize79].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_show80] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("show", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_show80].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_size81] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("size", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_size81].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_toBack82] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("toBack", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_toBack82].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_toFront83] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("toFront", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_toFront83].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_toString84] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("toString", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_toString84].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_transferFocus85] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("transferFocus", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_transferFocus85].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_transferFocusBackward86] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("transferFocusBackward", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_transferFocusBackward86].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_transferFocusDownCycle87] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("transferFocusDownCycle", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_transferFocusDownCycle87].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_transferFocusUpCycle88] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("transferFocusUpCycle", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_transferFocusUpCycle88].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_update89] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("update",
							new Class[] { java.awt.Graphics.class }));
			methods[AboutDialogBeanInfo.METHOD_update89].setDisplayName("");
			methods[AboutDialogBeanInfo.METHOD_validate90] = new MethodDescriptor(
					com.linkare.rec.impl.baseUI.AboutDialog.class.getMethod("validate", new Class[] {}));
			methods[AboutDialogBeanInfo.METHOD_validate90].setDisplayName("");
		} catch (final Exception e) {
		}// GEN-HEADEREND:Methods

		// Here you can add code for customizing the methods array.

		return methods;
	}// GEN-LAST:Methods

	private static final int defaultPropertyIndex = -1;// GEN-BEGIN:Idx
	private static final int defaultEventIndex = -1;// GEN-END:Idx

	// GEN-FIRST:Superclass

	// Here you can add code for customizing the Superclass BeanInfo.

	// GEN-LAST:Superclass

	/**
	 * Gets the bean's <code>BeanDescriptor</code>s.
	 * 
	 * @return BeanDescriptor describing the editable properties of this bean.
	 *         May return null if the information should be obtained by
	 *         automatic analysis.
	 */
	public BeanDescriptor getBeanDescriptor() {
		return AboutDialogBeanInfo.getBdescriptor();
	}

	/**
	 * Gets the bean's <code>PropertyDescriptor</code>s.
	 * 
	 * @return An array of PropertyDescriptors describing the editable
	 *         properties supported by this bean. May return null if the
	 *         information should be obtained by automatic analysis.
	 *         <p>
	 *         If a property is indexed, then its entry in the result array will
	 *         belong to the IndexedPropertyDescriptor subclass of
	 *         PropertyDescriptor. A client of getPropertyDescriptors can use
	 *         "instanceof" to check if a given PropertyDescriptor is an
	 *         IndexedPropertyDescriptor.
	 */
	public PropertyDescriptor[] getPropertyDescriptors() {
		return AboutDialogBeanInfo.getPdescriptor();
	}

	/**
	 * Gets the bean's <code>EventSetDescriptor</code>s.
	 * 
	 * @return An array of EventSetDescriptors describing the kinds of events
	 *         fired by this bean. May return null if the information should be
	 *         obtained by automatic analysis.
	 */
	public EventSetDescriptor[] getEventSetDescriptors() {
		return AboutDialogBeanInfo.getEdescriptor();
	}

	/**
	 * Gets the bean's <code>MethodDescriptor</code>s.
	 * 
	 * @return An array of MethodDescriptors describing the methods implemented
	 *         by this bean. May return null if the information should be
	 *         obtained by automatic analysis.
	 */
	public MethodDescriptor[] getMethodDescriptors() {
		return AboutDialogBeanInfo.getMdescriptor();
	}

	/**
	 * A bean may have a "default" property that is the property that will
	 * mostly commonly be initially chosen for update by human's who are
	 * customizing the bean.
	 * 
	 * @return Index of default property in the PropertyDescriptor array
	 *         returned by getPropertyDescriptors.
	 *         <P>
	 *         Returns -1 if there is no default property.
	 */
	public int getDefaultPropertyIndex() {
		return AboutDialogBeanInfo.defaultPropertyIndex;
	}

	/**
	 * A bean may have a "default" event that is the event that will mostly
	 * commonly be used by human's when using the bean.
	 * 
	 * @return Index of default event in the EventSetDescriptor array returned
	 *         by getEventSetDescriptors.
	 *         <P>
	 *         Returns -1 if there is no default event.
	 */
	public int getDefaultEventIndex() {
		return AboutDialogBeanInfo.defaultEventIndex;
	}
}
