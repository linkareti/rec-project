package com.linkare.rec.am.config;

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

	private String labId = "";

	private String labIdStringBundleKey = "";

	public Lab() {
		super();
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
	 * @param iconLocationBundleKey the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		this.iconLocationBundleKey = iconLocationBundleKey;
	}

	/**
	 * @param toolTipBundleKey the toolTipBundleKey to set
	 */
	public void setToolTipBundleKey(final String toolTipBundleKey) {
		this.toolTipBundleKey = toolTipBundleKey;
	}

	/**
	 * @param displayStringBundleKey the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		this.displayStringBundleKey = displayStringBundleKey;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @param desktopLocationBundleKey the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(final String desktopLocationBundleKey) {
		this.desktopLocationBundleKey = desktopLocationBundleKey;
	}

	/**
	 * @param apparatus the apparatus to set
	 */
	public void setApparatus(final List<Apparatus> apparatus) {
		this.apparatus = apparatus;
	}

	/**
	 * @param webResource the webResource to set
	 */
	public void setWebResource(final List<WebResource> webResource) {
		this.webResource = webResource;
	}

	/**
	 * @param localizationBundle the localizationBundle to set
	 */
	public void setLocalizationBundle(final List<LocalizationBundle> localizationBundle) {
		this.localizationBundle = localizationBundle;
	}

	/**
	 * @return the labId
	 */
	@XmlAttribute
	public String getLabId() {
		return labId;
	}

	/**
	 * @param labId the labId to set
	 */
	public void setLabId(final String labId) {
		this.labId = labId;
	}

	/**
	 * @return the labIdStringBundleKey
	 */
	@XmlAttribute
	public String getLabIdStringBundleKey() {
		return labIdStringBundleKey;
	}

	/**
	 * @param labIdStringBundleKey the labIdStringBundleKey to set
	 */
	public void setLabIdStringBundleKey(final String labIdStringBundleKey) {
		this.labIdStringBundleKey = labIdStringBundleKey;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Lab other = (Lab) obj;
		if ((iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !iconLocationBundleKey
				.equals(other.iconLocationBundleKey)) {
			return false;
		}
		if ((toolTipBundleKey == null) ? (other.toolTipBundleKey != null) : !toolTipBundleKey
				.equals(other.toolTipBundleKey)) {
			return false;
		}
		if ((displayStringBundleKey == null) ? (other.displayStringBundleKey != null) : !displayStringBundleKey
				.equals(other.displayStringBundleKey)) {
			return false;
		}
		if ((location == null) ? (other.location != null) : !location.equals(other.location)) {
			return false;
		}
		if ((desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null) : !desktopLocationBundleKey
				.equals(other.desktopLocationBundleKey)) {
			return false;
		}
		if (apparatus != other.apparatus && (apparatus == null || !apparatus.equals(other.apparatus))) {
			return false;
		}
		if (webResource != other.webResource && (webResource == null || !webResource.equals(other.webResource))) {
			return false;
		}
		if (localizationBundle != other.localizationBundle
				&& (localizationBundle == null || !localizationBundle.equals(other.localizationBundle))) {
			return false;
		}
		if ((labId == null) ? (other.labId != null) : !labId.equals(other.labId)) {
			return false;
		}
		if ((labIdStringBundleKey == null) ? (other.labIdStringBundleKey != null) : !labIdStringBundleKey
				.equals(other.labIdStringBundleKey)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 19 * hash + (iconLocationBundleKey != null ? iconLocationBundleKey.hashCode() : 0);
		hash = 19 * hash + (toolTipBundleKey != null ? toolTipBundleKey.hashCode() : 0);
		hash = 19 * hash + (displayStringBundleKey != null ? displayStringBundleKey.hashCode() : 0);
		hash = 19 * hash + (location != null ? location.hashCode() : 0);
		hash = 19 * hash + (desktopLocationBundleKey != null ? desktopLocationBundleKey.hashCode() : 0);
		hash = 19 * hash + (apparatus != null ? apparatus.hashCode() : 0);
		hash = 19 * hash + (webResource != null ? webResource.hashCode() : 0);
		hash = 19 * hash + (localizationBundle != null ? localizationBundle.hashCode() : 0);
		hash = 19 * hash + (labId != null ? labId.hashCode() : 0);
		hash = 19 * hash + (labIdStringBundleKey != null ? labIdStringBundleKey.hashCode() : 0);
		return hash;
	}

}
