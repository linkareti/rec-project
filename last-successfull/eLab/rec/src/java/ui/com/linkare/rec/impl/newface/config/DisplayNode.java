package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class DisplayNode extends AbstractConfigBean {

	private int order = 0;

	private boolean enabled = false;

	private boolean visible = true;

	private boolean selected = true;

	private boolean connected = false;

	/**
	 * Creates a new <code>DisplayNode</code>.
	 */
	public DisplayNode() {
		super();
	}

	/**
	 * @return the order
	 */
	@XmlAttribute
	public int getOrder() {
		return order;
	}

	/**
	 * @return the enabled
	 */
	@XmlAttribute
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the visible
	 */
	@XmlAttribute
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @return the selected
	 */
	@XmlAttribute
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @return the connected
	 */
	@XmlAttribute
	public boolean isConnected() {
		return connected;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(final int order) {
		changeSupport.firePropertyChange("order", this.order, this.order = order);
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(final boolean enabled) {
		changeSupport.firePropertyChange("enabled", this.enabled, this.enabled = enabled);
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(final boolean visible) {
		changeSupport.firePropertyChange("visible", this.visible, this.visible = visible);
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(final boolean selected) {
		changeSupport.firePropertyChange("selected", this.selected, this.selected = selected);
	}

	/**
	 * @param connected the connected to set
	 */
	public void setConnected(final boolean connected) {
		changeSupport.firePropertyChange("connected", this.connected, this.connected = connected);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DisplayNode other = (DisplayNode) obj;
		if (order != other.order) {
			return false;
		}
		if (enabled != other.enabled) {
			return false;
		}
		if (visible != other.visible) {
			return false;
		}
		if (selected != other.selected) {
			return false;
		}
		if (connected != other.connected) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + order;
		hash = 71 * hash + (enabled ? 1 : 0);
		hash = 71 * hash + (visible ? 1 : 0);
		hash = 71 * hash + (selected ? 1 : 0);
		hash = 71 * hash + (connected ? 1 : 0);
		return hash;
	}

}
