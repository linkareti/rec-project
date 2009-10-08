package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class DefaultAcquisitionConfig extends DisplayNode {

    private String displayStringBundleKey = "";

    private String iconLocationBundleKey = "";

    private String toolTipBundleKey = "";

    private String classLocationBundleKey = "";

    public DefaultAcquisitionConfig() {
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
    public void setDisplayStringBundleKey(String displayStringBundleKey) {
	this.displayStringBundleKey = displayStringBundleKey;
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
     * @param classLocationBundleKey
     *            the classLocationBundleKey to set
     */
    public void setClassLocationBundleKey(String classLocationBundleKey) {
	this.classLocationBundleKey = classLocationBundleKey;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final DefaultAcquisitionConfig other = (DefaultAcquisitionConfig) obj;
	if ((this.displayStringBundleKey == null) ? (other.displayStringBundleKey != null)
		: !this.displayStringBundleKey.equals(other.displayStringBundleKey)) {
	    return false;
	}
	if ((this.iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !this.iconLocationBundleKey
		.equals(other.iconLocationBundleKey)) {
	    return false;
	}
	if ((this.toolTipBundleKey == null) ? (other.toolTipBundleKey != null) : !this.toolTipBundleKey
		.equals(other.toolTipBundleKey)) {
	    return false;
	}
	if ((this.classLocationBundleKey == null) ? (other.classLocationBundleKey != null)
		: !this.classLocationBundleKey.equals(other.classLocationBundleKey)) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 23 * hash + (this.displayStringBundleKey != null ? this.displayStringBundleKey.hashCode() : 0);
	hash = 23 * hash + (this.iconLocationBundleKey != null ? this.iconLocationBundleKey.hashCode() : 0);
	hash = 23 * hash + (this.toolTipBundleKey != null ? this.toolTipBundleKey.hashCode() : 0);
	hash = 23 * hash + (this.classLocationBundleKey != null ? this.classLocationBundleKey.hashCode() : 0);
	return hash;
    }

}
