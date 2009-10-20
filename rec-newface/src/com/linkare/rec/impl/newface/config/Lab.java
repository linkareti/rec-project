package com.linkare.rec.impl.newface.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Lab extends AbstractConfigBean {

	private String iconLocationBundleKey = "";

	private String toolTipBundleKey = "";

	private String displayStringBundleKey = "";

	private String location = "";

	private String desktopLocationBundleKey = "";

	private List<Apparatus> apparatus = new ArrayList<Apparatus>();

	private List<WebResource> webResource = new ArrayList<WebResource>();

	private List<LocalizationBundle> localizationBundle = new ArrayList<LocalizationBundle>();

	public Lab() {
	}

	/**
	 * @return the iconLocationBundleKey
	 */
	@XmlAttribute
	public String getIconLocationBundleKey() {
		return iconLocationBundleKey;
	}

	/**
	 * @return the toolTipBundleKey
	 */
	@XmlAttribute
	public String getToolTipBundleKey() {
		return toolTipBundleKey;
	}

	/**
	 * @return the displayStringBundleKey
	 */
	@XmlAttribute
	public String getDisplayStringBundleKey() {
		return displayStringBundleKey;
	}

	/**
	 * @return the location
	 */
	@XmlAttribute
	public String getLocation() {
		return location;
	}

	/**
	 * @return the desktopLocationBundleKey
	 */
	@XmlAttribute
	public String getDesktopLocationBundleKey() {
		return desktopLocationBundleKey;
	}

	/**
	 * @return the apparatus
	 */
	public List<Apparatus> getApparatus() {
		return apparatus;
	}

	/**
	 * @return the webResource
	 */
	public List<WebResource> getWebResource() {
		return webResource;
	}

	/**
	 * @return the localizationBundle
	 */
	public List<LocalizationBundle> getLocalizationBundle() {
		return localizationBundle;
	}

	/**
	 * @param iconLocationBundleKey
	 *            the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(String iconLocationBundleKey) {
		this.iconLocationBundleKey = iconLocationBundleKey;
	}

	/**
	 * @param toolTipBundleKey
	 *            the toolTipBundleKey to set
	 */
	public void setToolTipBundleKey(String toolTipBundleKey) {
		this.toolTipBundleKey = toolTipBundleKey;
	}

	/**
	 * @param displayStringBundleKey
	 *            the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(String displayStringBundleKey) {
		this.displayStringBundleKey = displayStringBundleKey;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param desktopLocationBundleKey
	 *            the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(String desktopLocationBundleKey) {
		this.desktopLocationBundleKey = desktopLocationBundleKey;
	}

	/**
	 * @param apparatus
	 *            the apparatus to set
	 */
	public void setApparatus(List<Apparatus> apparatus) {
		this.apparatus = apparatus;
	}

	/**
	 * @param webResource
	 *            the webResource to set
	 */
	public void setWebResource(List<WebResource> webResource) {
		this.webResource = webResource;
	}

	/**
	 * @param localizationBundle
	 *            the localizationBundle to set
	 */
	public void setLocalizationBundle(List<LocalizationBundle> localizationBundle) {
		this.localizationBundle = localizationBundle;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Lab other = (Lab) obj;
		if ((this.iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !this.iconLocationBundleKey
				.equals(other.iconLocationBundleKey)) {
			return false;
		}
		if ((this.toolTipBundleKey == null) ? (other.toolTipBundleKey != null) : !this.toolTipBundleKey.equals(other.toolTipBundleKey)) {
			return false;
		}
		if ((this.displayStringBundleKey == null) ? (other.displayStringBundleKey != null) : !this.displayStringBundleKey
				.equals(other.displayStringBundleKey)) {
			return false;
		}
		if ((this.location == null) ? (other.location != null) : !this.location.equals(other.location)) {
			return false;
		}
		if ((this.desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null) : !this.desktopLocationBundleKey
				.equals(other.desktopLocationBundleKey)) {
			return false;
		}
		if (this.apparatus != other.apparatus && (this.apparatus == null || !this.apparatus.equals(other.apparatus))) {
			return false;
		}
		if (this.webResource != other.webResource && (this.webResource == null || !this.webResource.equals(other.webResource))) {
			return false;
		}
		if (this.localizationBundle != other.localizationBundle
				&& (this.localizationBundle == null || !this.localizationBundle.equals(other.localizationBundle))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 19 * hash + (this.iconLocationBundleKey != null ? this.iconLocationBundleKey.hashCode() : 0);
		hash = 19 * hash + (this.toolTipBundleKey != null ? this.toolTipBundleKey.hashCode() : 0);
		hash = 19 * hash + (this.displayStringBundleKey != null ? this.displayStringBundleKey.hashCode() : 0);
		hash = 19 * hash + (this.location != null ? this.location.hashCode() : 0);
		hash = 19 * hash + (this.desktopLocationBundleKey != null ? this.desktopLocationBundleKey.hashCode() : 0);
		hash = 19 * hash + (this.apparatus != null ? this.apparatus.hashCode() : 0);
		hash = 19 * hash + (this.webResource != null ? this.webResource.hashCode() : 0);
		hash = 19 * hash + (this.localizationBundle != null ? this.localizationBundle.hashCode() : 0);
		return hash;
	}

}
