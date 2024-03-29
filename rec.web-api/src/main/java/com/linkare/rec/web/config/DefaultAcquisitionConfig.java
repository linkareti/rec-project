package com.linkare.rec.web.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class DefaultAcquisitionConfig extends DisplayNode {

	private String displayStringBundleKey = "";

	private String iconLocationBundleKey = "";

	private String toolTipBundleKey = "";

	private String classLocationBundleKey = "";

	public DefaultAcquisitionConfig() {
		super();
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
	 * @return the toolTipBundleKey
	 */
	@XmlAttribute
	public String getToolTipBundleKey() {
		return toolTipBundleKey;
	}

	/**
	 * @return the classLocationBundleKey
	 */
	@XmlAttribute
	public String getClassLocationBundleKey() {
		return classLocationBundleKey;
	}

	/**
	 * @param displayStringBundleKey
	 *            the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		this.displayStringBundleKey = displayStringBundleKey;
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
	 * @param classLocationBundleKey
	 *            the classLocationBundleKey to set
	 */
	public void setClassLocationBundleKey(final String classLocationBundleKey) {
		this.classLocationBundleKey = classLocationBundleKey;
	}

	@Override
	public boolean equals(final Object obj) {
		boolean retVal = true;
		if (obj == null || getClass() != obj.getClass()) {
			retVal = false;
		} else {
			final DefaultAcquisitionConfig other = (DefaultAcquisitionConfig) obj;
			retVal = retVal
					&& nullSafeObjectEquals(displayStringBundleKey,
							other.displayStringBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(iconLocationBundleKey,
							other.iconLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(toolTipBundleKey,
							other.toolTipBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(classLocationBundleKey,
							other.classLocationBundleKey);
		}
		return retVal;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 23 * hash + nullObjectSafeHash(displayStringBundleKey);
		hash = 23 * hash + nullObjectSafeHash(iconLocationBundleKey);
		hash = 23 * hash + nullObjectSafeHash(toolTipBundleKey);
		hash = 23 * hash + nullObjectSafeHash(classLocationBundleKey);
		return hash;
	}

}
