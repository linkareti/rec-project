package com.linkare.rec.web.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.PROPERTY)
public class DisplayNode extends AbstractConfigBean {

	private int order = 0;

	private boolean enabled = false;

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
	@XmlTransient
	public boolean isEnabled() {
		return enabled;
	}


	/**
	 * @param order the order to set
	 */
	public void setOrder(final int order) {
		int oldOrder = this.order;
		this.order = order;
		changeSupport.firePropertyChange("order", oldOrder, this.order);
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(final boolean enabled) {
		boolean oldEnabled = this.enabled;
		this.enabled = enabled;
		changeSupport.firePropertyChange("enabled", oldEnabled, enabled);
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
	
		
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 71 * hash + order;
		hash = 71 * hash + (enabled ? 1 : 0);
		return hash;
	}

}
