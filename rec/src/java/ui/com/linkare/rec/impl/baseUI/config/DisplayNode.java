/*
 * DisplayNode.java
 *
 * Created on 27 de Janeiro de 2004, 2:10
 */

package com.linkare.rec.impl.baseUI.config;

import javax.swing.Icon;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public abstract class DisplayNode {
	private int order = 0;
	private boolean enabled = false;
	private boolean visible = true;
	private boolean selected = true;
	private boolean connected = false;

	/**
	 * Utility field used by event firing mechanism.
	 */
	private javax.swing.event.EventListenerList listenerList = null;

	/**
	 * Getter for property order.
	 * 
	 * @return Value of property order.
	 * 
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * Setter for property order.
	 * 
	 * @param order New value of property order.
	 * 
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	public Icon getIcon() {
		return ReCResourceBundle.findImageIconOrDefault(getIconLocationBundleKey(), null);
	}

	public String getText() {
		return ReCResourceBundle.findStringOrDefault(getDisplayNameBundleKey(), "<DisplayNameNotFoundInBundles="
				+ getDisplayNameBundleKey() + ">");
	}

	public String getToolTipText() {
		return ReCResourceBundle.findStringOrDefault(getToolTipBundleKey(), ReCResourceBundle.findStringOrDefault(
				getDisplayNameBundleKey(), ""));
	}

	/**
	 * Getter for property enabled.
	 * 
	 * @return Value of property enabled.
	 * 
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Setter for property enabled.
	 * 
	 * @param enabled New value of property enabled.
	 * 
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		firePropertyChangeListenerPropertyChange(new java.beans.PropertyChangeEvent(this, "enable", new Boolean(
				!enabled), new Boolean(enabled)));
	}

	/**
	 * Getter for property connected.
	 * 
	 * @return Value of property connected.
	 * 
	 */
	public boolean isConnected() {
		return connected;
	}

	/**
	 * Setter for property connected.
	 * 
	 * @param connected New value of property connected.
	 * 
	 */
	public void setConnected(boolean connected) {
		this.connected = connected;
		firePropertyChangeListenerPropertyChange(new java.beans.PropertyChangeEvent(this, "connected", new Boolean(
				!connected), new Boolean(connected)));
	}

	/**
	 * Getter for property visible.
	 * 
	 * @return Value of property visible.
	 * 
	 */
	public boolean isVisible() {
		return this.visible;
	}

	/**
	 * Setter for property visible.
	 * 
	 * @param visible New value of property visible.
	 * 
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public abstract String getIconLocationBundleKey();

	public abstract String getDisplayNameBundleKey();

	public abstract String getToolTipBundleKey();

	/**
	 * Registers PropertyChangeListener to receive events.
	 * 
	 * @param listener The listener to register.
	 */
	public synchronized void addDisplayNodePropertyChangeListener(java.beans.PropertyChangeListener listener) {
		if (listenerList == null) {
			listenerList = new javax.swing.event.EventListenerList();
		}
		listenerList.add(java.beans.PropertyChangeListener.class, listener);
	}

	/**
	 * Removes PropertyChangeListener from the list of listeners.
	 * 
	 * @param listener The listener to remove.
	 */
	public synchronized void removeDisplayNodePropertyChangeListener(java.beans.PropertyChangeListener listener) {
		listenerList.remove(java.beans.PropertyChangeListener.class, listener);
	}

	/**
	 * Notifies all registered listeners about the event.
	 * 
	 * @param event The event to be fired
	 */
	private void firePropertyChangeListenerPropertyChange(java.beans.PropertyChangeEvent event) {
		if (listenerList == null)
			return;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == java.beans.PropertyChangeListener.class) {
				((java.beans.PropertyChangeListener) listeners[i + 1]).propertyChange(event);
			}
		}
	}

	/**
	 * Getter for property selected.
	 * 
	 * @return Value of property selected.
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * Setter for property selected.
	 * 
	 * @param selected New value of property selected.
	 */
	public void setSelected(boolean selected) {
		if (selected == this.selected)
			return;
		this.selected = selected;
		firePropertyChangeListenerPropertyChange(new java.beans.PropertyChangeEvent(this, "selected", new Boolean(
				!selected), new Boolean(selected)));
	}

}
