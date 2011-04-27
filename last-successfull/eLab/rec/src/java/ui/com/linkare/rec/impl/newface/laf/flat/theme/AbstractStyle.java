/* 
 * AbstractStyle.java created on Mar 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.theme;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlTransient;

/**
 * The base class for FlatTheme Component Styles.
 * 
 * @author Henrique Fernandes
 * @author João Florindo
 */
@XmlTransient
public abstract class AbstractStyle {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(AbstractStyle.class.getName());

	// -------------------------------------------------------------------------
	// Property keys

	private static final String DOT = ".";

	public static final String FONT = "font";
	public static final String BORDER = "border";
	public static final String BACKGROUND = "background";
	public static final String FOREGROUND = "foreground";
	public static final String DISABLED_FOREGROUND = "disabledForeground";
	public static final String SELECTION_FOREGROUND = "selectionForeground";
	public static final String SELECTION_BACKGROUND = "selectionBackground";
	public static final String DEFAULT_WHITE = "defaultWhite";

	//COMBOBOX ARRROW BUTTON COLOR'S DEFINITION
	public static final String ARROWBUTTON_ENABLEDFOREGROUND = "arrowButton.enabledForeground";
	public static final String ARROWBUTTON_DISABLEDFOREGROUND = "arrowButton.disabledForeground";

	private String id;

	private Map<String, Object> propertyMap;

	/**
	 * Default constructor. Initializes the style id.
	 */
	public AbstractStyle() {
		this.id = defineStyleId();
		this.propertyMap = new HashMap<String, Object>();
		updatePropertyMap(propertyMap);
	}

	/**
	 * @return The property name, value pairs in a collection.
	 */
	public Collection<Object> getProperties() {
		Collection<Object> result = new ArrayList<Object>();
		for (Entry<String, Object> entry : propertyMap.entrySet()) {
			String propertyKey = getId() + DOT + entry.getKey();
			result.add(propertyKey);
			result.add(entry.getValue());
			if (log.isLoggable(Level.FINER)) {
				log.finer("UI Property: " + propertyKey + "=" + entry.getValue());
			}
		}
		assert (result.size() % 2 == 0); // Must be even
		return result;
	}

	/**
	 * Override to define the style identifier.
	 * 
	 * @return The style id.
	 */
	protected String defineStyleId() {
		return ""; // Maps the default prefix for ui properties
	}

	/**
	 * Override to update the property map.
	 * <p>
	 * In most cases you should call super.updatePropertyMap before update the child properties.
	 * 
	 * @param map
	 *            The style propertyMap.
	 */
	public abstract void updatePropertyMap(Map<String, Object> map);

	// -------------------------------------------------------------------------
	// Getters n Setters

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the propertyMap
	 */
	public Map<String, Object> getPropertyMap() {
		return propertyMap;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param propertyMap
	 *            the propertyMap to set
	 */
	public void setPropertyMap(Map<String, Object> propertyMap) {
		this.propertyMap = propertyMap;
	}

	/**
	 * @return Id + PropertyMap String description
	 */
	@Override
	public String toString() {
		return id + " " + propertyMap.toString();
	}

}
