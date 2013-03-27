package com.linkare.rec.web.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class WebResource extends DisplayNode {

	private boolean internalBrowser = false;

	private String toolTipLocationBundleKey;

	private String displayStringBundleKey;

	private String iconLocationBundleKey;

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
	 * @param internalBrowser
	 *            the internalBrowser to set
	 */
	public void setInternalBrowser(final boolean internalBrowser) {
		changeSupport.firePropertyChange("internalBrowser",
				this.internalBrowser, internalBrowser);
		this.internalBrowser = internalBrowser;
	}

	/**
	 * @param toolTipLocationBundleKey
	 *            the toolTipLocationBundleKey to set
	 */
	public void setToolTipLocationBundleKey(
			final String toolTipLocationBundleKey) {
		changeSupport.firePropertyChange("toolTipLocationBundleKey",
				this.toolTipLocationBundleKey, toolTipLocationBundleKey);
		this.toolTipLocationBundleKey = toolTipLocationBundleKey;
	}

	/**
	 * @param displayStringBundleKey
	 *            the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		changeSupport.firePropertyChange("displayStringBundleKey",
				this.displayStringBundleKey,
				displayStringBundleKey);
		this.displayStringBundleKey = displayStringBundleKey;
	}

	/**
	 * @param iconLocationBundleKey
	 *            the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		changeSupport.firePropertyChange("iconLocationBundleKey",
				this.iconLocationBundleKey, iconLocationBundleKey);
		this.iconLocationBundleKey = iconLocationBundleKey;
	}

	/**
	 * @param locationBundleKey
	 *            the locationBundleKey to set
	 */
	public void setLocationBundleKey(final String locationBundleKey) {
		changeSupport.firePropertyChange("locationBundleKey",
				this.locationBundleKey, locationBundleKey);
		this.locationBundleKey = locationBundleKey;
	}

	@Override
	public boolean equals(final Object obj) {
		boolean retVal = true;
		if (obj == null || getClass() != obj.getClass()) {
			retVal = false;
		} else {
			final WebResource other = (WebResource) obj;
			retVal = retVal
					&& nullSafeObjectEquals(internalBrowser,
							other.internalBrowser);
			retVal = retVal
					&& nullSafeObjectEquals(toolTipLocationBundleKey,
							other.toolTipLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(displayStringBundleKey,
							other.displayStringBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(iconLocationBundleKey,
							other.iconLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(locationBundleKey,
							other.locationBundleKey);
		}
		return retVal;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 13 * hash + nullObjectSafeHash(internalBrowser);
		hash = 13 * hash + nullObjectSafeHash(toolTipLocationBundleKey);
		hash = 13 * hash + nullObjectSafeHash(displayStringBundleKey);
		hash = 13 * hash + nullObjectSafeHash(iconLocationBundleKey);
		hash = 13 * hash + nullObjectSafeHash(locationBundleKey);
		return hash;
	}

}
