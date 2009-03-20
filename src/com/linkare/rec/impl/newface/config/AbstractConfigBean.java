package com.linkare.rec.impl.newface.config;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.xml.bind.annotation.XmlTransient;

/**
 * The base Configuration Bean. It handles the common functionality.
 * 
 * @author Henrique Fernandes
 */
@XmlTransient
public abstract class AbstractConfigBean {

    /**
     * PropertyChangeSupport handler.
     */
    protected PropertyChangeSupport changeSupport;

    /**
     * Default constructor
     */
    public AbstractConfigBean() {
	changeSupport = new PropertyChangeSupport(this);
    }

    /**
     * @param listener
     *            The PropertyChangeListener to be added
     * 
     * @see PropertyChangeSupport#addPropertyChangeListener(PropertyChangeListener)
     */
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
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * @param listener
     *            The PropertyChangeListener to be removed
     * 
     * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
     */
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
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
	changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * @return all of the <code>PropertyChangeListeners</code> added or an empty
     *         array if no listeners have been added
     * 
     * @see PropertyChangeSupport#getPropertyChangeListeners()
     */
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
    public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
	return changeSupport.getPropertyChangeListeners(propertyName);
    }
}
