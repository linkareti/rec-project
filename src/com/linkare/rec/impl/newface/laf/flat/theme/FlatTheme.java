/* 
 * FlatTheme2.java created on Mar 26, 2009
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.impl.newface.laf.flat.theme;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIDefaults;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 
 * @author Henrique Fernandes
 */
@XmlTransient
public abstract class FlatTheme {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(FlatTheme.class.getName());
	
	private List<AbstractStyle> stylesList;

	/**
	 * Initializes the FlatTheme.
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public FlatTheme() {
		super();
		stylesList = new ArrayList<AbstractStyle>();
		Set<Class<? extends AbstractStyle>> styleClasses = registerStyles();
		for (Class<? extends AbstractStyle> clazz : styleClasses) {
			try {
				AbstractStyle newInstance = clazz.newInstance();
				stylesList.add(newInstance);
			} catch (Exception e) {
				log.log(Level.SEVERE, "Error creating style.", e);
			}
		}
		if (log.isLoggable(Level.FINE)) {
			log.fine("Registered Styles: " + styleClasses);
		}
	}

	/**
	 * Add this theme's custom entries to the defaults table.
	 * 
	 * @param table the defaults table, non-null
	 */
	public void addCustomEntriesToTable(UIDefaults table) {
		List<Object> defaults = new ArrayList<Object>();
		for (AbstractStyle style : getThemeStyles()) {
			defaults.addAll(style.getProperties());
		}
		table.putDefaults((Object[]) defaults.toArray(new Object[defaults.size()]));
	}

	/**
	 * @return The <code>FlatTheme</code> styles.
	 */
	@XmlElement
	public List<AbstractStyle> getThemeStyles() {
		return stylesList;
	}

	protected abstract Set<Class<? extends AbstractStyle>> registerStyles();
}
