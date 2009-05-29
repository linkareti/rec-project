/* 
 * AbstractContentPane.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Component;
import java.awt.Window;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.jdesktop.application.ApplicationContext;
import org.jdesktop.application.ResourceMap;

import com.linkare.rec.impl.newface.ReCApplication;

/**
 * Represents the base for all ReC standart panels.
 * 
 * @author Henrique Fernandes
 */
public abstract class AbstractContentPane extends JPanel {
	
	private ReCApplication recApplication = ReCApplication.getApplication();
	
	private static final Logger log = Logger
			.getLogger(AbstractContentPane.class.getName());

	private static final long serialVersionUID = -4706961491108859138L;

    protected Window container;

    public AbstractContentPane() {
        this(null);
    }
    
    public AbstractContentPane(Window container) {
        this.container = container;
    }

    public Window getContainer() {
        return container;
    }

    public void setContainer(Window container) {
        this.container = container;
    }

    public void closeContainer() {
        if (container != null) {
            container.setVisible(false);
        }
    }
    
    /**
     * Sets all child components enabled/disabled. (One level only)
     * @param enabled True to set enabled, false to disabled.
     */
    public void setChildComponentsEnabled(boolean enabled) {
    	for (Component childComponent : getComponents()) {
    		childComponent.setEnabled(enabled);
    		if (log.isLoggable(Level.FINE)) {
				log.fine("component " + childComponent.getName() + (enabled ? " enabled" : " disabled"));
			}
    	}
	}

	/**
	 * @return the recApplication
	 */
	public ReCApplication getRecApplication() {
		return recApplication;
	}

	public ApplicationContext getContext() {
		return recApplication.getContext();
	}
	
	public ResourceMap getResourceMap() {
		return getContext().getResourceMap();
	}
}
