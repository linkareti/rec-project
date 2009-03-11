/* 
 * AbstractContentPane.java created on 2009/02/06
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.linkare.rec.impl.newface.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Represents the base for all ReC standart panels.
 * 
 * @author Henrique Fernandes
 */
public abstract class AbstractContentPane extends JPanel {

    private static final long serialVersionUID = -4706961491108859138L;

    /**
     * PropertyChangeSupport handler.
     */
    protected PropertyChangeSupport changeSupport;

    protected List<ContentPaneListener> contentPaneListeners;

    public AbstractContentPane() {
	super();
	changeSupport = new PropertyChangeSupport(this);
	contentPaneListeners = new ArrayList<ContentPaneListener>();
    }

    public void addContentPaneListener(ContentPaneListener listener) {
	contentPaneListeners.add(listener);
    }

    public void removeContentPaneListener(ContentPaneListener listener) {
	contentPaneListeners.remove(listener);
    }

    public List<ContentPaneListener> getContentPaneListeners() {
	return contentPaneListeners;
    }

    public void fireContentPaneCloseEvent(Object evt) {
	for (ContentPaneListener listener : contentPaneListeners) {
	    listener.contentPaneClose(evt);
	}
    }

    /**
     * @param listener
     *            The PropertyChangeListener to be added
     * 
     * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
	changeSupport.addPropertyChangeListener(listener);
    }

    /**
     * @param propertyName
     *            The name of the property to listen on.
     * @param listener
     *            The PropertyChangeListener to be added
     * 
     * @see PropertyChangeSupport#addPropertyChangeListener(String,
     *      PropertyChangeListener)
     */
    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * @param listener
     *            The PropertyChangeListener to be removed
     * 
     * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener) {
	changeSupport.removePropertyChangeListener(listener);
    }

    /**
     * @param propertyName
     *            The name of the property that was listened on.
     * @param listener
     *            The PropertyChangeListener to be removed
     * 
     * @see PropertyChangeSupport#removePropertyChangeListener(String,
     *      PropertyChangeListener)
     */
    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * @return all of the <code>PropertyChangeListeners</code> added or an empty
     *         array if no listeners have been added
     * 
     * @see PropertyChangeSupport#getPropertyChangeListeners()
     */
    @Override
    public PropertyChangeListener[] getPropertyChangeListeners() {
	return changeSupport.getPropertyChangeListeners();
    }

    /**
     * @param propertyName
     *            The name of the property being listened to
     * 
     * @return all of the <code>PropertyChangeListeners</code> associated with
     *         the named property. If no such listeners have been added, or if
     *         <code>propertyName</code> is null, an empty array is returned.
     * 
     * @see PropertyChangeSupport#getPropertyChangeListeners(String)
     */
    @Override
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
	return changeSupport.getPropertyChangeListeners(propertyName);
    }

}
