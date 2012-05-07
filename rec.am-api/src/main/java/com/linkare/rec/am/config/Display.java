package com.linkare.rec.am.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Display extends DisplayNode {

	private boolean offlineCapable = false;
	private String displayStringBundleKey = "";
	private String iconLocationBundleKey = "";
	private String toolTipBundleKey = "";
	private String classLocationBundleKey = "";

	public Display() {
		super();
	}

	/**
	 * @return the offlineCapable
	 */
	@XmlAttribute
	public boolean isOfflineCapable() {
		return offlineCapable;
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
	 * @param offlineCapable the offlineCapable to set
	 */
	public void setOfflineCapable(final boolean offlineCapable) {
		this.offlineCapable = offlineCapable;
	}

	/**
	 * @param displayStringBundleKey the displayStringBundleKey to set
	 */
	public void setDisplayStringBundleKey(final String displayStringBundleKey) {
		this.displayStringBundleKey = displayStringBundleKey;
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
	 * @param classLocationBundleKey the classLocationBundleKey to set
	 */
	public void setClassLocationBundleKey(final String classLocationBundleKey) {
		this.classLocationBundleKey = classLocationBundleKey;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Display other = (Display) obj;
		if (offlineCapable != other.offlineCapable) {
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
		if ((toolTipBundleKey == null) ? (other.toolTipBundleKey != null) : !toolTipBundleKey
				.equals(other.toolTipBundleKey)) {
			return false;
		}
		if ((classLocationBundleKey == null) ? (other.classLocationBundleKey != null) : !classLocationBundleKey
				.equals(other.classLocationBundleKey)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 29 * hash + (offlineCapable ? 1 : 0);
		hash = 29 * hash + (displayStringBundleKey != null ? displayStringBundleKey.hashCode() : 0);
		hash = 29 * hash + (iconLocationBundleKey != null ? iconLocationBundleKey.hashCode() : 0);
		hash = 29 * hash + (toolTipBundleKey != null ? toolTipBundleKey.hashCode() : 0);
		hash = 29 * hash + (classLocationBundleKey != null ? classLocationBundleKey.hashCode() : 0);
		return hash;
	}

}
