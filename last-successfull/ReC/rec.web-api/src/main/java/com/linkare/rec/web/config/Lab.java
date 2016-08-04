package com.linkare.rec.web.config;

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
	 * @param iconLocationBundleKey
	 *            the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		this.iconLocationBundleKey = iconLocationBundleKey;
	}

	/**
	 * @param toolTipBundleKey
	 *            the toolTipBundleKey to set
	 */
	public void setToolTipBundleKey(final String toolTipBundleKey) {
		this.toolTipBundleKey = toolTipBundleKey;
	}

	/**
	 * @param displayStringBundleKey
	 *            the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		this.displayStringBundleKey = displayStringBundleKey;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @param desktopLocationBundleKey
	 *            the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(
			final String desktopLocationBundleKey) {
		this.desktopLocationBundleKey = desktopLocationBundleKey;
	}

	/**
	 * @param apparatus
	 *            the apparatus to set
	 */
	public void setApparatus(final List<Apparatus> apparatus) {
		this.apparatus = apparatus;
	}

	/**
	 * @param webResource
	 *            the webResource to set
	 */
	public void setWebResource(final List<WebResource> webResource) {
		this.webResource = webResource;
	}

	/**
	 * @param localizationBundle
	 *            the localizationBundle to set
	 */
	public void setLocalizationBundle(
			final List<LocalizationBundle> localizationBundle) {
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
	 * @param labId
	 *            the labId to set
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
	 * @param labIdStringBundleKey
	 *            the labIdStringBundleKey to set
	 */
	public void setLabIdStringBundleKey(final String labIdStringBundleKey) {
		this.labIdStringBundleKey = labIdStringBundleKey;
	}

	@Override
	public boolean equals(final Object obj) {
		boolean retVal = true;
		if (obj == null || getClass() != obj.getClass()) {
			retVal = false;
		} else {
			final Lab other = (Lab) obj;
			retVal = retVal
					&& nullSafeObjectEquals(iconLocationBundleKey,
							other.iconLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(toolTipBundleKey,
							other.toolTipBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(displayStringBundleKey,
							other.displayStringBundleKey);
			retVal = retVal && nullSafeObjectEquals(location, other.location);
			retVal = retVal
					&& nullSafeObjectEquals(desktopLocationBundleKey,
							other.desktopLocationBundleKey);
			retVal = retVal && nullSafeObjectEquals(apparatus, other.apparatus);
			retVal = retVal
					&& nullSafeObjectEquals(webResource, other.webResource);
			retVal = retVal
					&& nullSafeObjectEquals(localizationBundle,
							other.localizationBundle);
			retVal = retVal
					&& nullSafeObjectEquals(labIdStringBundleKey,
							other.labIdStringBundleKey);
			retVal = retVal && nullSafeObjectEquals(labId, other.labId);
		}
		return retVal;
	}


	@Override
	public int hashCode() {
		int hash = 3;
		hash = 19 * hash + nullObjectSafeHash(iconLocationBundleKey);
		hash = 19 * hash + nullObjectSafeHash(toolTipBundleKey);
		hash = 19 * hash + nullObjectSafeHash(displayStringBundleKey);
		hash = 19 * hash + nullObjectSafeHash(location);
		hash = 19 * hash + nullObjectSafeHash(desktopLocationBundleKey);
		hash = 19 * hash + nullObjectSafeHash(apparatus);
		hash = 19 * hash + nullObjectSafeHash(webResource);
		hash = 19 * hash + nullObjectSafeHash(localizationBundle);
		hash = 19 * hash + nullObjectSafeHash(labId);
		hash = 19 * hash + nullObjectSafeHash(labIdStringBundleKey);
		return hash;
	}

	

}
