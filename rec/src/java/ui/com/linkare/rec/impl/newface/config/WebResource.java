package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class WebResource extends DisplayNode {

	private boolean internalBrowser = false;

	private String toolTipLocationBundleKey = "";

	private String displayStringBundleKey;

	private String iconLocationBundleKey = "";

	private String locationBundleKey;

	public WebResource() {
		super();
	}

	/**
	 * @return the internalBrowser
	 */
	@XmlAttribute
	public boolean isInternalBrowser() {
		return internalBrowser;
	}

	/**
	 * @return the toolTipLocationBundleKey
	 */
	@XmlAttribute
	public String getToolTipLocationBundleKey() {
		return toolTipLocationBundleKey;
	}

	/**
	 * @return the displayStringBundleKey
	 */
	@XmlAttribute
	public String getDisplayStringBundleKey() {
		return displayStringBundleKey;
	}

	/**
	 * @return the iconLocationBundleKey
	 */
	@XmlAttribute
	public String getIconLocationBundleKey() {
		return iconLocationBundleKey;
	}

	/**
	 * @return the locationBundleKey
	 */
	@XmlAttribute
	public String getLocationBundleKey() {
		return locationBundleKey;
	}

	/**
	 * @param internalBrowser the internalBrowser to set
	 */
	public void setInternalBrowser(final boolean internalBrowser) {
		changeSupport.firePropertyChange("internalBrowser", this.internalBrowser,
				this.internalBrowser = internalBrowser);
	}

	/**
	 * @param toolTipLocationBundleKey the toolTipLocationBundleKey to set
	 */
	public void setToolTipLocationBundleKey(final String toolTipLocationBundleKey) {
		changeSupport.firePropertyChange("toolTipLocationBundleKey", this.toolTipLocationBundleKey,
				this.toolTipLocationBundleKey = toolTipLocationBundleKey);
	}

	/**
	 * @param displayStringBundleKey the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		changeSupport.firePropertyChange("displayStringBundleKey", this.displayStringBundleKey,
				this.displayStringBundleKey = displayStringBundleKey);
	}

	/**
	 * @param iconLocationBundleKey the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		changeSupport.firePropertyChange("iconLocationBundleKey", this.iconLocationBundleKey,
				this.iconLocationBundleKey = iconLocationBundleKey);
	}

	/**
	 * @param locationBundleKey the locationBundleKey to set
	 */
	public void setLocationBundleKey(final String locationBundleKey) {
		changeSupport.firePropertyChange("locationBundleKey", this.locationBundleKey,
				this.locationBundleKey = locationBundleKey);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final WebResource other = (WebResource) obj;
		if (internalBrowser != other.internalBrowser) {
			return false;
		}
		if ((toolTipLocationBundleKey == null) ? (other.toolTipLocationBundleKey != null) : !toolTipLocationBundleKey
				.equals(other.toolTipLocationBundleKey)) {
			return false;
		}
		if ((displayStringBundleKey == null) ? (other.displayStringBundleKey != null) : !displayStringBundleKey
				.equals(other.displayStringBundleKey)) {
			return false;
		}
		if ((iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !iconLocationBundleKey
				.equals(other.iconLocationBundleKey)) {
			return false;
		}
		if ((locationBundleKey == null) ? (other.locationBundleKey != null) : !locationBundleKey
				.equals(other.locationBundleKey)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + (internalBrowser ? 1 : 0);
		hash = 13 * hash + (toolTipLocationBundleKey != null ? toolTipLocationBundleKey.hashCode() : 0);
		hash = 13 * hash + (displayStringBundleKey != null ? displayStringBundleKey.hashCode() : 0);
		hash = 13 * hash + (iconLocationBundleKey != null ? iconLocationBundleKey.hashCode() : 0);
		hash = 13 * hash + (locationBundleKey != null ? locationBundleKey.hashCode() : 0);
		return hash;
	}

}
