package old;

import java.beans.*;

public class LaboratoryApparatusTreeBeanInfo extends SimpleBeanInfo
{
	

    // Bean descriptor //GEN-FIRST:BeanDescriptor
    private static BeanDescriptor beanDescriptor = new BeanDescriptor  ( LaboratoryApparatusTree.class , null );

    private static BeanDescriptor getBdescriptor(){
        return beanDescriptor;
    }

    static {//GEN-HEADEREND:BeanDescriptor
		
		// Here you can add code for customizing the BeanDescriptor.
		
}//GEN-LAST:BeanDescriptor
	
	
    // Property identifiers //GEN-FIRST:Properties
    private static final int PROPERTY_verifyInputWhenFocusTarget = 0;
    private static final int PROPERTY_componentOrientation = 1;
    private static final int PROPERTY_fontSet = 2;
    private static final int PROPERTY_locationOnScreen = 3;
    private static final int PROPERTY_mouseWheelListeners = 4;
    private static final int PROPERTY_colorModel = 5;
    private static final int PROPERTY_focusTraversalPolicy = 6;
    private static final int PROPERTY_registeredKeyStrokes = 7;
    private static final int PROPERTY_alignmentX = 8;
    private static final int PROPERTY_paintingTile = 9;
    private static final int PROPERTY_alignmentY = 10;
    private static final int PROPERTY_hierarchyListeners = 11;
    private static final int PROPERTY_accessibleContext = 12;
    private static final int PROPERTY_preferredSize = 13;
    private static final int PROPERTY_managingFocus = 14;
    private static final int PROPERTY_minimumSizeSet = 15;
    private static final int PROPERTY_focusTraversalPolicySet = 16;
    private static final int PROPERTY_y = 17;
    private static final int PROPERTY_x = 18;
    private static final int PROPERTY_cursorSet = 19;
    private static final int PROPERTY_inputMethodRequests = 20;
    private static final int PROPERTY_containerListeners = 21;
    private static final int PROPERTY_insets = 22;
    private static final int PROPERTY_componentCount = 23;
    private static final int PROPERTY_components = 24;
    private static final int PROPERTY_inputVerifier = 25;
    private static final int PROPERTY_hierarchyBoundsListeners = 26;
    private static final int PROPERTY_border = 27;
    private static final int PROPERTY_name = 28;
    private static final int PROPERTY_optimizedDrawingEnabled = 29;
    private static final int PROPERTY_graphics = 30;
    private static final int PROPERTY_toolTipText = 31;
    private static final int PROPERTY_minimumSize = 32;
    private static final int PROPERTY_focusTraversalKeysEnabled = 33;
    private static final int PROPERTY_foreground = 34;
    private static final int PROPERTY_ignoreRepaint = 35;
    private static final int PROPERTY_focusable = 36;
    private static final int PROPERTY_preferredSizeSet = 37;
    private static final int PROPERTY_visible = 38;
    private static final int PROPERTY_focusCycleRootAncestor = 39;
    private static final int PROPERTY_model = 40;
    private static final int PROPERTY_parent = 41;
    private static final int PROPERTY_rootPane = 42;
    private static final int PROPERTY_lightweight = 43;
    private static final int PROPERTY_width = 44;
    private static final int PROPERTY_keyListeners = 45;
    private static final int PROPERTY_toolkit = 46;
    private static final int PROPERTY_inputContext = 47;
    private static final int PROPERTY_layout = 48;
    private static final int PROPERTY_opaque = 49;
    private static final int PROPERTY_font = 50;
    private static final int PROPERTY_locale = 51;
    private static final int PROPERTY_cursor = 52;
    private static final int PROPERTY_inputMethodListeners = 53;
    private static final int PROPERTY_transferHandler = 54;
    private static final int PROPERTY_vetoableChangeListeners = 55;
    private static final int PROPERTY_doubleBuffered = 56;
    private static final int PROPERTY_visibleRect = 57;
    private static final int PROPERTY_maximumSizeSet = 58;
    private static final int PROPERTY_valid = 59;
    private static final int PROPERTY_focusCycleRoot = 60;
    private static final int PROPERTY_maximumSize = 61;
    private static final int PROPERTY_mouseMotionListeners = 62;
    private static final int PROPERTY_bounds = 63;
    private static final int PROPERTY_treeLock = 64;
    private static final int PROPERTY_focusTraversable = 65;
    private static final int PROPERTY_propertyChangeListeners = 66;
    private static final int PROPERTY_autoscrolls = 67;
    private static final int PROPERTY_componentListeners = 68;
    private static final int PROPERTY_showing = 69;
    private static final int PROPERTY_dropTarget = 70;
    private static final int PROPERTY_focusListeners = 71;
    private static final int PROPERTY_nextFocusableComponent = 72;
    private static final int PROPERTY_peer = 73;
    private static final int PROPERTY_height = 74;
    private static final int PROPERTY_topLevelAncestor = 75;
    private static final int PROPERTY_displayable = 76;
    private static final int PROPERTY_background = 77;
    private static final int PROPERTY_graphicsConfiguration = 78;
    private static final int PROPERTY_focusOwner = 79;
    private static final int PROPERTY_ancestorListeners = 80;
    private static final int PROPERTY_requestFocusEnabled = 81;
    private static final int PROPERTY_debugGraphicsOptions = 82;
    private static final int PROPERTY_backgroundSet = 83;
    private static final int PROPERTY_actionMap = 84;
    private static final int PROPERTY_mouseListeners = 85;
    private static final int PROPERTY_enabled = 86;
    private static final int PROPERTY_foregroundSet = 87;
    private static final int PROPERTY_validateRoot = 88;
    private static final int PROPERTY_UI = 89;
    private static final int PROPERTY_UIClassID = 90;
    private static final int PROPERTY_component = 91;
    private static final int PROPERTY_focusTraversalKeys = 92;

    // Property array 
    private static PropertyDescriptor[] properties = new PropertyDescriptor[93];

    private static PropertyDescriptor[] getPdescriptor(){
        return properties;
    }

    static {
        try {
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", LaboratoryApparatusTree.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" );
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", LaboratoryApparatusTree.class, "getComponentOrientation", "setComponentOrientation" );
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", LaboratoryApparatusTree.class, "isFontSet", null );
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", LaboratoryApparatusTree.class, "getLocationOnScreen", null );
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", LaboratoryApparatusTree.class, "getMouseWheelListeners", null );
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", LaboratoryApparatusTree.class, "getColorModel", null );
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", LaboratoryApparatusTree.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" );
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", LaboratoryApparatusTree.class, "getRegisteredKeyStrokes", null );
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", LaboratoryApparatusTree.class, "getAlignmentX", "setAlignmentX" );
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", LaboratoryApparatusTree.class, "isPaintingTile", null );
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", LaboratoryApparatusTree.class, "getAlignmentY", "setAlignmentY" );
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", LaboratoryApparatusTree.class, "getHierarchyListeners", null );
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", LaboratoryApparatusTree.class, "getAccessibleContext", null );
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", LaboratoryApparatusTree.class, "getPreferredSize", "setPreferredSize" );
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", LaboratoryApparatusTree.class, "isManagingFocus", null );
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", LaboratoryApparatusTree.class, "isMinimumSizeSet", null );
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", LaboratoryApparatusTree.class, "isFocusTraversalPolicySet", null );
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", LaboratoryApparatusTree.class, "getY", null );
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", LaboratoryApparatusTree.class, "getX", null );
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", LaboratoryApparatusTree.class, "isCursorSet", null );
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", LaboratoryApparatusTree.class, "getInputMethodRequests", null );
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", LaboratoryApparatusTree.class, "getContainerListeners", null );
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", LaboratoryApparatusTree.class, "getInsets", null );
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", LaboratoryApparatusTree.class, "getComponentCount", null );
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", LaboratoryApparatusTree.class, "getComponents", null );
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", LaboratoryApparatusTree.class, "getInputVerifier", "setInputVerifier" );
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", LaboratoryApparatusTree.class, "getHierarchyBoundsListeners", null );
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", LaboratoryApparatusTree.class, "getBorder", "setBorder" );
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", LaboratoryApparatusTree.class, "getName", "setName" );
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", LaboratoryApparatusTree.class, "isOptimizedDrawingEnabled", null );
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", LaboratoryApparatusTree.class, "getGraphics", null );
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", LaboratoryApparatusTree.class, "getToolTipText", "setToolTipText" );
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", LaboratoryApparatusTree.class, "getMinimumSize", "setMinimumSize" );
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", LaboratoryApparatusTree.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" );
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", LaboratoryApparatusTree.class, "getForeground", "setForeground" );
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", LaboratoryApparatusTree.class, "getIgnoreRepaint", "setIgnoreRepaint" );
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", LaboratoryApparatusTree.class, "isFocusable", "setFocusable" );
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", LaboratoryApparatusTree.class, "isPreferredSizeSet", null );
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", LaboratoryApparatusTree.class, "isVisible", "setVisible" );
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", LaboratoryApparatusTree.class, "getFocusCycleRootAncestor", null );
            properties[PROPERTY_model] = new PropertyDescriptor ( "model", LaboratoryApparatusTree.class, "getModel", "setModel" );
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", LaboratoryApparatusTree.class, "getParent", null );
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", LaboratoryApparatusTree.class, "getRootPane", null );
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", LaboratoryApparatusTree.class, "isLightweight", null );
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", LaboratoryApparatusTree.class, "getWidth", null );
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", LaboratoryApparatusTree.class, "getKeyListeners", null );
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", LaboratoryApparatusTree.class, "getToolkit", null );
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", LaboratoryApparatusTree.class, "getInputContext", null );
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", LaboratoryApparatusTree.class, "getLayout", "setLayout" );
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", LaboratoryApparatusTree.class, "isOpaque", "setOpaque" );
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", LaboratoryApparatusTree.class, "getFont", "setFont" );
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", LaboratoryApparatusTree.class, "getLocale", "setLocale" );
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", LaboratoryApparatusTree.class, "getCursor", "setCursor" );
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", LaboratoryApparatusTree.class, "getInputMethodListeners", null );
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", LaboratoryApparatusTree.class, "getTransferHandler", "setTransferHandler" );
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", LaboratoryApparatusTree.class, "getVetoableChangeListeners", null );
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", LaboratoryApparatusTree.class, "isDoubleBuffered", "setDoubleBuffered" );
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", LaboratoryApparatusTree.class, "getVisibleRect", null );
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", LaboratoryApparatusTree.class, "isMaximumSizeSet", null );
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", LaboratoryApparatusTree.class, "isValid", null );
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", LaboratoryApparatusTree.class, "isFocusCycleRoot", "setFocusCycleRoot" );
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", LaboratoryApparatusTree.class, "getMaximumSize", "setMaximumSize" );
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", LaboratoryApparatusTree.class, "getMouseMotionListeners", null );
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", LaboratoryApparatusTree.class, "getBounds", "setBounds" );
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", LaboratoryApparatusTree.class, "getTreeLock", null );
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", LaboratoryApparatusTree.class, "isFocusTraversable", null );
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", LaboratoryApparatusTree.class, "getPropertyChangeListeners", null );
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", LaboratoryApparatusTree.class, "getAutoscrolls", "setAutoscrolls" );
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", LaboratoryApparatusTree.class, "getComponentListeners", null );
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", LaboratoryApparatusTree.class, "isShowing", null );
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", LaboratoryApparatusTree.class, "getDropTarget", "setDropTarget" );
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", LaboratoryApparatusTree.class, "getFocusListeners", null );
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", LaboratoryApparatusTree.class, "getNextFocusableComponent", "setNextFocusableComponent" );
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", LaboratoryApparatusTree.class, "getPeer", null );
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", LaboratoryApparatusTree.class, "getHeight", null );
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", LaboratoryApparatusTree.class, "getTopLevelAncestor", null );
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", LaboratoryApparatusTree.class, "isDisplayable", null );
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", LaboratoryApparatusTree.class, "getBackground", "setBackground" );
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", LaboratoryApparatusTree.class, "getGraphicsConfiguration", null );
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", LaboratoryApparatusTree.class, "isFocusOwner", null );
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", LaboratoryApparatusTree.class, "getAncestorListeners", null );
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", LaboratoryApparatusTree.class, "isRequestFocusEnabled", "setRequestFocusEnabled" );
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", LaboratoryApparatusTree.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" );
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", LaboratoryApparatusTree.class, "isBackgroundSet", null );
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", LaboratoryApparatusTree.class, "getActionMap", "setActionMap" );
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", LaboratoryApparatusTree.class, "getMouseListeners", null );
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", LaboratoryApparatusTree.class, "isEnabled", "setEnabled" );
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", LaboratoryApparatusTree.class, "isForegroundSet", null );
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", LaboratoryApparatusTree.class, "isValidateRoot", null );
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", LaboratoryApparatusTree.class, "getUI", "setUI" );
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", LaboratoryApparatusTree.class, "getUIClassID", null );
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", LaboratoryApparatusTree.class, null, null, "getComponent", null );
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", LaboratoryApparatusTree.class, null, null, "getFocusTraversalKeys", "setFocusTraversalKeys" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Properties
		
		// Here you can add code for customizing the properties array.
		
}//GEN-LAST:Properties
	
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_inputMethodListener = 0;
    private static final int EVENT_containerListener = 1;
    private static final int EVENT_mouseMotionListener = 2;
    private static final int EVENT_mouseWheelListener = 3;
    private static final int EVENT_mouseListener = 4;
    private static final int EVENT_componentListener = 5;
    private static final int EVENT_hierarchyBoundsListener = 6;
    private static final int EVENT_apparatusSelectionChangeListener = 7;
    private static final int EVENT_ancestorListener = 8;
    private static final int EVENT_focusListener = 9;
    private static final int EVENT_keyListener = 10;
    private static final int EVENT_hierarchyListener = 11;
    private static final int EVENT_vetoableChangeListener = 12;
    private static final int EVENT_propertyChangeListener = 13;

    // EventSet array
    private static EventSetDescriptor[] eventSets = new EventSetDescriptor[14];

    private static EventSetDescriptor[] getEdescriptor(){
        return eventSets;
    }

    static {
        try {
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"inputMethodTextChanged", "caretPositionChanged"}, "addInputMethodListener", "removeInputMethodListener" );
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" );
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" );
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" );
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mousePressed", "mouseReleased", "mouseEntered", "mouseExited"}, "addMouseListener", "removeMouseListener" );
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentResized", "componentMoved", "componentShown", "componentHidden"}, "addComponentListener", "removeComponentListener" );
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" );
            eventSets[EVENT_apparatusSelectionChangeListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "apparatusSelectionChangeListener", com.linkare.rec.impl.client.apparatus.ApparatusSelectionChangeListener.class, new String[] {"apparatusSelectionChange"}, "addApparatusSelectionChangeListener", "removeApparatusSelectionChangeListener" );
            eventSets[EVENT_apparatusSelectionChangeListener].setPreferred ( true );
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorRemoved", "ancestorMoved"}, "addAncestorListener", "removeAncestorListener" );
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" );
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyTyped", "keyPressed", "keyReleased"}, "addKeyListener", "removeKeyListener" );
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" );
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" );
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" );
        }
        catch( IntrospectionException e) {}//GEN-HEADEREND:Events
		
		// Here you can add code for customizing the event sets array.
		
}//GEN-LAST:Events
	
    // Method identifiers //GEN-FIRST:Methods
    private static final int METHOD_updateUI0 = 0;
    private static final int METHOD_update1 = 1;
    private static final int METHOD_paint2 = 2;
    private static final int METHOD_printAll3 = 3;
    private static final int METHOD_print4 = 4;
    private static final int METHOD_requestFocus5 = 5;
    private static final int METHOD_requestFocus6 = 6;
    private static final int METHOD_requestFocusInWindow7 = 7;
    private static final int METHOD_grabFocus8 = 8;
    private static final int METHOD_contains9 = 9;
    private static final int METHOD_getInsets10 = 10;
    private static final int METHOD_registerKeyboardAction11 = 11;
    private static final int METHOD_registerKeyboardAction12 = 12;
    private static final int METHOD_unregisterKeyboardAction13 = 13;
    private static final int METHOD_getConditionForKeyStroke14 = 14;
    private static final int METHOD_getActionForKeyStroke15 = 15;
    private static final int METHOD_resetKeyboardActions16 = 16;
    private static final int METHOD_setInputMap17 = 17;
    private static final int METHOD_getInputMap18 = 18;
    private static final int METHOD_getInputMap19 = 19;
    private static final int METHOD_requestDefaultFocus20 = 20;
    private static final int METHOD_getDefaultLocale21 = 21;
    private static final int METHOD_setDefaultLocale22 = 22;
    private static final int METHOD_getToolTipText23 = 23;
    private static final int METHOD_getToolTipLocation24 = 24;
    private static final int METHOD_createToolTip25 = 25;
    private static final int METHOD_scrollRectToVisible26 = 26;
    private static final int METHOD_enable27 = 27;
    private static final int METHOD_disable28 = 28;
    private static final int METHOD_getClientProperty29 = 29;
    private static final int METHOD_putClientProperty30 = 30;
    private static final int METHOD_isLightweightComponent31 = 31;
    private static final int METHOD_reshape32 = 32;
    private static final int METHOD_getBounds33 = 33;
    private static final int METHOD_getSize34 = 34;
    private static final int METHOD_getLocation35 = 35;
    private static final int METHOD_computeVisibleRect36 = 36;
    private static final int METHOD_firePropertyChange37 = 37;
    private static final int METHOD_firePropertyChange38 = 38;
    private static final int METHOD_firePropertyChange39 = 39;
    private static final int METHOD_firePropertyChange40 = 40;
    private static final int METHOD_firePropertyChange41 = 41;
    private static final int METHOD_firePropertyChange42 = 42;
    private static final int METHOD_firePropertyChange43 = 43;
    private static final int METHOD_firePropertyChange44 = 44;
    private static final int METHOD_addPropertyChangeListener45 = 45;
    private static final int METHOD_removePropertyChangeListener46 = 46;
    private static final int METHOD_getPropertyChangeListeners47 = 47;
    private static final int METHOD_getListeners48 = 48;
    private static final int METHOD_addNotify49 = 49;
    private static final int METHOD_removeNotify50 = 50;
    private static final int METHOD_repaint51 = 51;
    private static final int METHOD_repaint52 = 52;
    private static final int METHOD_revalidate53 = 53;
    private static final int METHOD_paintImmediately54 = 54;
    private static final int METHOD_paintImmediately55 = 55;
    private static final int METHOD_countComponents56 = 56;
    private static final int METHOD_insets57 = 57;
    private static final int METHOD_add58 = 58;
    private static final int METHOD_add59 = 59;
    private static final int METHOD_add60 = 60;
    private static final int METHOD_add61 = 61;
    private static final int METHOD_add62 = 62;
    private static final int METHOD_remove63 = 63;
    private static final int METHOD_remove64 = 64;
    private static final int METHOD_removeAll65 = 65;
    private static final int METHOD_doLayout66 = 66;
    private static final int METHOD_layout67 = 67;
    private static final int METHOD_invalidate68 = 68;
    private static final int METHOD_validate69 = 69;
    private static final int METHOD_preferredSize70 = 70;
    private static final int METHOD_minimumSize71 = 71;
    private static final int METHOD_paintComponents72 = 72;
    private static final int METHOD_printComponents73 = 73;
    private static final int METHOD_deliverEvent74 = 74;
    private static final int METHOD_getComponentAt75 = 75;
    private static final int METHOD_locate76 = 76;
    private static final int METHOD_getComponentAt77 = 77;
    private static final int METHOD_findComponentAt78 = 78;
    private static final int METHOD_findComponentAt79 = 79;
    private static final int METHOD_isAncestorOf80 = 80;
    private static final int METHOD_list81 = 81;
    private static final int METHOD_list82 = 82;
    private static final int METHOD_areFocusTraversalKeysSet83 = 83;
    private static final int METHOD_isFocusCycleRoot84 = 84;
    private static final int METHOD_transferFocusBackward85 = 85;
    private static final int METHOD_transferFocusDownCycle86 = 86;
    private static final int METHOD_applyComponentOrientation87 = 87;
    private static final int METHOD_enable88 = 88;
    private static final int METHOD_enableInputMethods89 = 89;
    private static final int METHOD_show90 = 90;
    private static final int METHOD_show91 = 91;
    private static final int METHOD_hide92 = 92;
    private static final int METHOD_getLocation93 = 93;
    private static final int METHOD_location94 = 94;
    private static final int METHOD_setLocation95 = 95;
    private static final int METHOD_move96 = 96;
    private static final int METHOD_setLocation97 = 97;
    private static final int METHOD_getSize98 = 98;
    private static final int METHOD_size99 = 99;
    private static final int METHOD_setSize100 = 100;
    private static final int METHOD_resize101 = 101;
    private static final int METHOD_setSize102 = 102;
    private static final int METHOD_resize103 = 103;
    private static final int METHOD_bounds104 = 104;
    private static final int METHOD_setBounds105 = 105;
    private static final int METHOD_getFontMetrics106 = 106;
    private static final int METHOD_paintAll107 = 107;
    private static final int METHOD_repaint108 = 108;
    private static final int METHOD_repaint109 = 109;
    private static final int METHOD_repaint110 = 110;
    private static final int METHOD_imageUpdate111 = 111;
    private static final int METHOD_createImage112 = 112;
    private static final int METHOD_createImage113 = 113;
    private static final int METHOD_createVolatileImage114 = 114;
    private static final int METHOD_createVolatileImage115 = 115;
    private static final int METHOD_prepareImage116 = 116;
    private static final int METHOD_prepareImage117 = 117;
    private static final int METHOD_checkImage118 = 118;
    private static final int METHOD_checkImage119 = 119;
    private static final int METHOD_inside120 = 120;
    private static final int METHOD_contains121 = 121;
    private static final int METHOD_dispatchEvent122 = 122;
    private static final int METHOD_postEvent123 = 123;
    private static final int METHOD_handleEvent124 = 124;
    private static final int METHOD_mouseDown125 = 125;
    private static final int METHOD_mouseDrag126 = 126;
    private static final int METHOD_mouseUp127 = 127;
    private static final int METHOD_mouseMove128 = 128;
    private static final int METHOD_mouseEnter129 = 129;
    private static final int METHOD_mouseExit130 = 130;
    private static final int METHOD_keyDown131 = 131;
    private static final int METHOD_keyUp132 = 132;
    private static final int METHOD_action133 = 133;
    private static final int METHOD_gotFocus134 = 134;
    private static final int METHOD_lostFocus135 = 135;
    private static final int METHOD_transferFocus136 = 136;
    private static final int METHOD_nextFocus137 = 137;
    private static final int METHOD_transferFocusUpCycle138 = 138;
    private static final int METHOD_hasFocus139 = 139;
    private static final int METHOD_add140 = 140;
    private static final int METHOD_remove141 = 141;
    private static final int METHOD_toString142 = 142;
    private static final int METHOD_list143 = 143;
    private static final int METHOD_list144 = 144;
    private static final int METHOD_list145 = 145;

    // Method array 
    private static MethodDescriptor[] methods = new MethodDescriptor[146];

    private static MethodDescriptor[] getMdescriptor(){
        return methods;
    }

    static {
        try {
            methods[METHOD_updateUI0] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("updateUI", new Class[] {}));
            methods[METHOD_updateUI0].setDisplayName ( "" );
            methods[METHOD_update1] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("update", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_update1].setDisplayName ( "" );
            methods[METHOD_paint2] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("paint", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paint2].setDisplayName ( "" );
            methods[METHOD_printAll3] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("printAll", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_printAll3].setDisplayName ( "" );
            methods[METHOD_print4] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("print", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_print4].setDisplayName ( "" );
            methods[METHOD_requestFocus5] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("requestFocus", new Class[] {}));
            methods[METHOD_requestFocus5].setDisplayName ( "" );
            methods[METHOD_requestFocus6] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("requestFocus", new Class[] {Boolean.TYPE}));
            methods[METHOD_requestFocus6].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow7] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("requestFocusInWindow", new Class[] {}));
            methods[METHOD_requestFocusInWindow7].setDisplayName ( "" );
            methods[METHOD_grabFocus8] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("grabFocus", new Class[] {}));
            methods[METHOD_grabFocus8].setDisplayName ( "" );
            methods[METHOD_contains9] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("contains", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_contains9].setDisplayName ( "" );
            methods[METHOD_getInsets10] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getInsets", new Class[] {java.awt.Insets.class}));
            methods[METHOD_getInsets10].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction11] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, Integer.TYPE}));
            methods[METHOD_registerKeyboardAction11].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction12] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, javax.swing.KeyStroke.class, Integer.TYPE}));
            methods[METHOD_registerKeyboardAction12].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction13] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_unregisterKeyboardAction13].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke14] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_getConditionForKeyStroke14].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke15] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class}));
            methods[METHOD_getActionForKeyStroke15].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions16] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("resetKeyboardActions", new Class[] {}));
            methods[METHOD_resetKeyboardActions16].setDisplayName ( "" );
            methods[METHOD_setInputMap17] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setInputMap", new Class[] {Integer.TYPE, javax.swing.InputMap.class}));
            methods[METHOD_setInputMap17].setDisplayName ( "" );
            methods[METHOD_getInputMap18] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getInputMap", new Class[] {Integer.TYPE}));
            methods[METHOD_getInputMap18].setDisplayName ( "" );
            methods[METHOD_getInputMap19] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getInputMap", new Class[] {}));
            methods[METHOD_getInputMap19].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus20] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("requestDefaultFocus", new Class[] {}));
            methods[METHOD_requestDefaultFocus20].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale21] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getDefaultLocale", new Class[] {}));
            methods[METHOD_getDefaultLocale21].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale22] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class}));
            methods[METHOD_setDefaultLocale22].setDisplayName ( "" );
            methods[METHOD_getToolTipText23] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class}));
            methods[METHOD_getToolTipText23].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation24] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class}));
            methods[METHOD_getToolTipLocation24].setDisplayName ( "" );
            methods[METHOD_createToolTip25] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("createToolTip", new Class[] {}));
            methods[METHOD_createToolTip25].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible26] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_scrollRectToVisible26].setDisplayName ( "" );
            methods[METHOD_enable27] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("enable", new Class[] {}));
            methods[METHOD_enable27].setDisplayName ( "" );
            methods[METHOD_disable28] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("disable", new Class[] {}));
            methods[METHOD_disable28].setDisplayName ( "" );
            methods[METHOD_getClientProperty29] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class}));
            methods[METHOD_getClientProperty29].setDisplayName ( "" );
            methods[METHOD_putClientProperty30] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class}));
            methods[METHOD_putClientProperty30].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent31] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class}));
            methods[METHOD_isLightweightComponent31].setDisplayName ( "" );
            methods[METHOD_reshape32] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("reshape", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_reshape32].setDisplayName ( "" );
            methods[METHOD_getBounds33] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_getBounds33].setDisplayName ( "" );
            methods[METHOD_getSize34] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getSize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_getSize34].setDisplayName ( "" );
            methods[METHOD_getLocation35] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getLocation", new Class[] {java.awt.Point.class}));
            methods[METHOD_getLocation35].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect36] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_computeVisibleRect36].setDisplayName ( "" );
            methods[METHOD_firePropertyChange37] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Byte.TYPE, Byte.TYPE}));
            methods[METHOD_firePropertyChange37].setDisplayName ( "" );
            methods[METHOD_firePropertyChange38] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Character.TYPE, Character.TYPE}));
            methods[METHOD_firePropertyChange38].setDisplayName ( "" );
            methods[METHOD_firePropertyChange39] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Short.TYPE, Short.TYPE}));
            methods[METHOD_firePropertyChange39].setDisplayName ( "" );
            methods[METHOD_firePropertyChange40] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_firePropertyChange40].setDisplayName ( "" );
            methods[METHOD_firePropertyChange41] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Long.TYPE, Long.TYPE}));
            methods[METHOD_firePropertyChange41].setDisplayName ( "" );
            methods[METHOD_firePropertyChange42] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Float.TYPE, Float.TYPE}));
            methods[METHOD_firePropertyChange42].setDisplayName ( "" );
            methods[METHOD_firePropertyChange43] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Double.TYPE, Double.TYPE}));
            methods[METHOD_firePropertyChange43].setDisplayName ( "" );
            methods[METHOD_firePropertyChange44] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Boolean.TYPE, Boolean.TYPE}));
            methods[METHOD_firePropertyChange44].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener45] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class}));
            methods[METHOD_addPropertyChangeListener45].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener46] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class}));
            methods[METHOD_removePropertyChangeListener46].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners47] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class}));
            methods[METHOD_getPropertyChangeListeners47].setDisplayName ( "" );
            methods[METHOD_getListeners48] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getListeners", new Class[] {java.lang.Class.class}));
            methods[METHOD_getListeners48].setDisplayName ( "" );
            methods[METHOD_addNotify49] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("addNotify", new Class[] {}));
            methods[METHOD_addNotify49].setDisplayName ( "" );
            methods[METHOD_removeNotify50] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("removeNotify", new Class[] {}));
            methods[METHOD_removeNotify50].setDisplayName ( "" );
            methods[METHOD_repaint51] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("repaint", new Class[] {Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_repaint51].setDisplayName ( "" );
            methods[METHOD_repaint52] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("repaint", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_repaint52].setDisplayName ( "" );
            methods[METHOD_revalidate53] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("revalidate", new Class[] {}));
            methods[METHOD_revalidate53].setDisplayName ( "" );
            methods[METHOD_paintImmediately54] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("paintImmediately", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_paintImmediately54].setDisplayName ( "" );
            methods[METHOD_paintImmediately55] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("paintImmediately", new Class[] {java.awt.Rectangle.class}));
            methods[METHOD_paintImmediately55].setDisplayName ( "" );
            methods[METHOD_countComponents56] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("countComponents", new Class[] {}));
            methods[METHOD_countComponents56].setDisplayName ( "" );
            methods[METHOD_insets57] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("insets", new Class[] {}));
            methods[METHOD_insets57].setDisplayName ( "" );
            methods[METHOD_add58] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("add", new Class[] {java.awt.Component.class}));
            methods[METHOD_add58].setDisplayName ( "" );
            methods[METHOD_add59] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("add", new Class[] {java.lang.String.class, java.awt.Component.class}));
            methods[METHOD_add59].setDisplayName ( "" );
            methods[METHOD_add60] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("add", new Class[] {java.awt.Component.class, Integer.TYPE}));
            methods[METHOD_add60].setDisplayName ( "" );
            methods[METHOD_add61] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class}));
            methods[METHOD_add61].setDisplayName ( "" );
            methods[METHOD_add62] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("add", new Class[] {java.awt.Component.class, java.lang.Object.class, Integer.TYPE}));
            methods[METHOD_add62].setDisplayName ( "" );
            methods[METHOD_remove63] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("remove", new Class[] {Integer.TYPE}));
            methods[METHOD_remove63].setDisplayName ( "" );
            methods[METHOD_remove64] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("remove", new Class[] {java.awt.Component.class}));
            methods[METHOD_remove64].setDisplayName ( "" );
            methods[METHOD_removeAll65] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("removeAll", new Class[] {}));
            methods[METHOD_removeAll65].setDisplayName ( "" );
            methods[METHOD_doLayout66] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("doLayout", new Class[] {}));
            methods[METHOD_doLayout66].setDisplayName ( "" );
            methods[METHOD_layout67] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("layout", new Class[] {}));
            methods[METHOD_layout67].setDisplayName ( "" );
            methods[METHOD_invalidate68] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("invalidate", new Class[] {}));
            methods[METHOD_invalidate68].setDisplayName ( "" );
            methods[METHOD_validate69] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("validate", new Class[] {}));
            methods[METHOD_validate69].setDisplayName ( "" );
            methods[METHOD_preferredSize70] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("preferredSize", new Class[] {}));
            methods[METHOD_preferredSize70].setDisplayName ( "" );
            methods[METHOD_minimumSize71] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("minimumSize", new Class[] {}));
            methods[METHOD_minimumSize71].setDisplayName ( "" );
            methods[METHOD_paintComponents72] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paintComponents72].setDisplayName ( "" );
            methods[METHOD_printComponents73] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_printComponents73].setDisplayName ( "" );
            methods[METHOD_deliverEvent74] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_deliverEvent74].setDisplayName ( "" );
            methods[METHOD_getComponentAt75] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getComponentAt", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_getComponentAt75].setDisplayName ( "" );
            methods[METHOD_locate76] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("locate", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_locate76].setDisplayName ( "" );
            methods[METHOD_getComponentAt77] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getComponentAt", new Class[] {java.awt.Point.class}));
            methods[METHOD_getComponentAt77].setDisplayName ( "" );
            methods[METHOD_findComponentAt78] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("findComponentAt", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_findComponentAt78].setDisplayName ( "" );
            methods[METHOD_findComponentAt79] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("findComponentAt", new Class[] {java.awt.Point.class}));
            methods[METHOD_findComponentAt79].setDisplayName ( "" );
            methods[METHOD_isAncestorOf80] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class}));
            methods[METHOD_isAncestorOf80].setDisplayName ( "" );
            methods[METHOD_list81] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("list", new Class[] {java.io.PrintStream.class, Integer.TYPE}));
            methods[METHOD_list81].setDisplayName ( "" );
            methods[METHOD_list82] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("list", new Class[] {java.io.PrintWriter.class, Integer.TYPE}));
            methods[METHOD_list82].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet83] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("areFocusTraversalKeysSet", new Class[] {Integer.TYPE}));
            methods[METHOD_areFocusTraversalKeysSet83].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot84] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class}));
            methods[METHOD_isFocusCycleRoot84].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward85] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("transferFocusBackward", new Class[] {}));
            methods[METHOD_transferFocusBackward85].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle86] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("transferFocusDownCycle", new Class[] {}));
            methods[METHOD_transferFocusDownCycle86].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation87] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class}));
            methods[METHOD_applyComponentOrientation87].setDisplayName ( "" );
            methods[METHOD_enable88] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("enable", new Class[] {Boolean.TYPE}));
            methods[METHOD_enable88].setDisplayName ( "" );
            methods[METHOD_enableInputMethods89] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("enableInputMethods", new Class[] {Boolean.TYPE}));
            methods[METHOD_enableInputMethods89].setDisplayName ( "" );
            methods[METHOD_show90] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("show", new Class[] {}));
            methods[METHOD_show90].setDisplayName ( "" );
            methods[METHOD_show91] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("show", new Class[] {Boolean.TYPE}));
            methods[METHOD_show91].setDisplayName ( "" );
            methods[METHOD_hide92] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("hide", new Class[] {}));
            methods[METHOD_hide92].setDisplayName ( "" );
            methods[METHOD_getLocation93] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getLocation", new Class[] {}));
            methods[METHOD_getLocation93].setDisplayName ( "" );
            methods[METHOD_location94] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("location", new Class[] {}));
            methods[METHOD_location94].setDisplayName ( "" );
            methods[METHOD_setLocation95] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setLocation", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setLocation95].setDisplayName ( "" );
            methods[METHOD_move96] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("move", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_move96].setDisplayName ( "" );
            methods[METHOD_setLocation97] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setLocation", new Class[] {java.awt.Point.class}));
            methods[METHOD_setLocation97].setDisplayName ( "" );
            methods[METHOD_getSize98] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getSize", new Class[] {}));
            methods[METHOD_getSize98].setDisplayName ( "" );
            methods[METHOD_size99] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("size", new Class[] {}));
            methods[METHOD_size99].setDisplayName ( "" );
            methods[METHOD_setSize100] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setSize", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setSize100].setDisplayName ( "" );
            methods[METHOD_resize101] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("resize", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_resize101].setDisplayName ( "" );
            methods[METHOD_setSize102] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setSize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_setSize102].setDisplayName ( "" );
            methods[METHOD_resize103] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("resize", new Class[] {java.awt.Dimension.class}));
            methods[METHOD_resize103].setDisplayName ( "" );
            methods[METHOD_bounds104] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("bounds", new Class[] {}));
            methods[METHOD_bounds104].setDisplayName ( "" );
            methods[METHOD_setBounds105] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("setBounds", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_setBounds105].setDisplayName ( "" );
            methods[METHOD_getFontMetrics106] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class}));
            methods[METHOD_getFontMetrics106].setDisplayName ( "" );
            methods[METHOD_paintAll107] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class}));
            methods[METHOD_paintAll107].setDisplayName ( "" );
            methods[METHOD_repaint108] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("repaint", new Class[] {}));
            methods[METHOD_repaint108].setDisplayName ( "" );
            methods[METHOD_repaint109] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("repaint", new Class[] {Long.TYPE}));
            methods[METHOD_repaint109].setDisplayName ( "" );
            methods[METHOD_repaint110] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("repaint", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_repaint110].setDisplayName ( "" );
            methods[METHOD_imageUpdate111] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_imageUpdate111].setDisplayName ( "" );
            methods[METHOD_createImage112] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class}));
            methods[METHOD_createImage112].setDisplayName ( "" );
            methods[METHOD_createImage113] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("createImage", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_createImage113].setDisplayName ( "" );
            methods[METHOD_createVolatileImage114] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_createVolatileImage114].setDisplayName ( "" );
            methods[METHOD_createVolatileImage115] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE, java.awt.ImageCapabilities.class}));
            methods[METHOD_createVolatileImage115].setDisplayName ( "" );
            methods[METHOD_prepareImage116] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class}));
            methods[METHOD_prepareImage116].setDisplayName ( "" );
            methods[METHOD_prepareImage117] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, java.awt.image.ImageObserver.class}));
            methods[METHOD_prepareImage117].setDisplayName ( "" );
            methods[METHOD_checkImage118] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class}));
            methods[METHOD_checkImage118].setDisplayName ( "" );
            methods[METHOD_checkImage119] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("checkImage", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, java.awt.image.ImageObserver.class}));
            methods[METHOD_checkImage119].setDisplayName ( "" );
            methods[METHOD_inside120] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("inside", new Class[] {Integer.TYPE, Integer.TYPE}));
            methods[METHOD_inside120].setDisplayName ( "" );
            methods[METHOD_contains121] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("contains", new Class[] {java.awt.Point.class}));
            methods[METHOD_contains121].setDisplayName ( "" );
            methods[METHOD_dispatchEvent122] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class}));
            methods[METHOD_dispatchEvent122].setDisplayName ( "" );
            methods[METHOD_postEvent123] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("postEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_postEvent123].setDisplayName ( "" );
            methods[METHOD_handleEvent124] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("handleEvent", new Class[] {java.awt.Event.class}));
            methods[METHOD_handleEvent124].setDisplayName ( "" );
            methods[METHOD_mouseDown125] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseDown125].setDisplayName ( "" );
            methods[METHOD_mouseDrag126] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseDrag126].setDisplayName ( "" );
            methods[METHOD_mouseUp127] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseUp127].setDisplayName ( "" );
            methods[METHOD_mouseMove128] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseMove128].setDisplayName ( "" );
            methods[METHOD_mouseEnter129] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseEnter129].setDisplayName ( "" );
            methods[METHOD_mouseExit130] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE}));
            methods[METHOD_mouseExit130].setDisplayName ( "" );
            methods[METHOD_keyDown131] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("keyDown", new Class[] {java.awt.Event.class, Integer.TYPE}));
            methods[METHOD_keyDown131].setDisplayName ( "" );
            methods[METHOD_keyUp132] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("keyUp", new Class[] {java.awt.Event.class, Integer.TYPE}));
            methods[METHOD_keyUp132].setDisplayName ( "" );
            methods[METHOD_action133] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_action133].setDisplayName ( "" );
            methods[METHOD_gotFocus134] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_gotFocus134].setDisplayName ( "" );
            methods[METHOD_lostFocus135] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class}));
            methods[METHOD_lostFocus135].setDisplayName ( "" );
            methods[METHOD_transferFocus136] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("transferFocus", new Class[] {}));
            methods[METHOD_transferFocus136].setDisplayName ( "" );
            methods[METHOD_nextFocus137] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("nextFocus", new Class[] {}));
            methods[METHOD_nextFocus137].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle138] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("transferFocusUpCycle", new Class[] {}));
            methods[METHOD_transferFocusUpCycle138].setDisplayName ( "" );
            methods[METHOD_hasFocus139] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("hasFocus", new Class[] {}));
            methods[METHOD_hasFocus139].setDisplayName ( "" );
            methods[METHOD_add140] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("add", new Class[] {java.awt.PopupMenu.class}));
            methods[METHOD_add140].setDisplayName ( "" );
            methods[METHOD_remove141] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("remove", new Class[] {java.awt.MenuComponent.class}));
            methods[METHOD_remove141].setDisplayName ( "" );
            methods[METHOD_toString142] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("toString", new Class[] {}));
            methods[METHOD_toString142].setDisplayName ( "" );
            methods[METHOD_list143] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("list", new Class[] {}));
            methods[METHOD_list143].setDisplayName ( "" );
            methods[METHOD_list144] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("list", new Class[] {java.io.PrintStream.class}));
            methods[METHOD_list144].setDisplayName ( "" );
            methods[METHOD_list145] = new MethodDescriptor ( com.linkare.rec.impl.baseUI.LaboratoryApparatusTree.class.getMethod("list", new Class[] {java.io.PrintWriter.class}));
            methods[METHOD_list145].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods
		
		// Here you can add code for customizing the methods array.
		
}//GEN-LAST:Methods
	
	
    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx
	
	
 //GEN-FIRST:Superclass
	
	// Here you can add code for customizing the Superclass BeanInfo.
	
 //GEN-LAST:Superclass
	
	/**
	 * Gets the bean's <code>BeanDescriptor</code>s.
	 *
	 * @return BeanDescriptor describing the editable
	 * properties of this bean.  May return null if the
	 * information should be obtained by automatic analysis.
	 */
	public BeanDescriptor getBeanDescriptor()
	{
		return getBdescriptor();
	}
	
	/**
	 * Gets the bean's <code>PropertyDescriptor</code>s.
	 *
	 * @return An array of PropertyDescriptors describing the editable
	 * properties supported by this bean.  May return null if the
	 * information should be obtained by automatic analysis.
	 * <p>
	 * If a property is indexed, then its entry in the result array will
	 * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
	 * A client of getPropertyDescriptors can use "instanceof" to check
	 * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
	 */
	public PropertyDescriptor[] getPropertyDescriptors()
	{
		return getPdescriptor();
	}
	
	/**
	 * Gets the bean's <code>EventSetDescriptor</code>s.
	 *
	 * @return  An array of EventSetDescriptors describing the kinds of
	 * events fired by this bean.  May return null if the information
	 * should be obtained by automatic analysis.
	 */
	public EventSetDescriptor[] getEventSetDescriptors()
	{
		return getEdescriptor();
	}
	
	/**
	 * Gets the bean's <code>MethodDescriptor</code>s.
	 *
	 * @return  An array of MethodDescriptors describing the methods
	 * implemented by this bean.  May return null if the information
	 * should be obtained by automatic analysis.
	 */
	public MethodDescriptor[] getMethodDescriptors()
	{
		return getMdescriptor();
	}
	
	/**
	 * A bean may have a "default" property that is the property that will
	 * mostly commonly be initially chosen for update by human's who are
	 * customizing the bean.
	 * @return  Index of default property in the PropertyDescriptor array
	 * 		returned by getPropertyDescriptors.
	 * <P>	Returns -1 if there is no default property.
	 */
	public int getDefaultPropertyIndex()
	{
		return defaultPropertyIndex;
	}
	
	/**
	 * A bean may have a "default" event that is the event that will
	 * mostly commonly be used by human's when using the bean.
	 * @return Index of default event in the EventSetDescriptor array
	 *		returned by getEventSetDescriptors.
	 * <P>	Returns -1 if there is no default event.
	 */
	public int getDefaultEventIndex()
	{
		return defaultEventIndex;
	}
}

