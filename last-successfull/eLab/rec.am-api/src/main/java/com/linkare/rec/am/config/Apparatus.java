package com.linkare.rec.am.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Apparatus extends DisplayNode {

    // FIXME - HFernandes - Please set it to null or document why we need this
    // anyhow!
    private String displayStringBundleKey = "";

    private String descriptionStringBundleKey = "";

    private String iconLocationBundleKey = "";

    private String desktopLocationBundleKey = "";

    private String toolTipBundleKey = "";

    private String dataModelClassLocationBundleKey = "";

    // private String videoLocation = "";

    private String headerDisplayClassLocationBundleKey = "";

    private String displayFactoryClassLocationBundleKey = "";

    private String customizerClassLocationBundleKey = "";

    private String location = "";

    private MediaConfig mediaConfig = null;

    private List<DefaultAcquisitionConfig> defaultAcquisitionConfig = new ArrayList<DefaultAcquisitionConfig>();

    private List<Display> display = new ArrayList<Display>();

    private List<WebResource> webResource = new ArrayList<WebResource>();

    private List<LocalizationBundle> localizationBundle = new ArrayList<LocalizationBundle>();

    public Apparatus() {
	super();
    }

    /**
     * @return the displayStringBundleKey
     */
    @XmlAttribute
    public String getDisplayStringBundleKey() {
	return displayStringBundleKey;
    }

    @XmlAttribute
    public String getDescriptionStringBundleKey() {
	return descriptionStringBundleKey;
    }

    /**
     * @return the iconLocationBundleKey
     */
    @XmlAttribute
    public String getIconLocationBundleKey() {
	return iconLocationBundleKey;
    }

    /**
     * @return the desktopLocationBundleKey
     */
    @XmlAttribute
    public String getDesktopLocationBundleKey() {
	return desktopLocationBundleKey;
    }

    /**
     * @return the toolTipBundleKey
     */
    @XmlAttribute
    public String getToolTipBundleKey() {
	return toolTipBundleKey;
    }

    /**
     * @return the dataModelClassLocationBundleKey
     */
    @XmlAttribute
    public String getDataModelClassLocationBundleKey() {
	return dataModelClassLocationBundleKey;
    }

    /**
     * @return the videoLocation
     */
    // Delete
    // @XmlAttribute
    // public String getVideoLocation() {
    // return videoLocation;
    // }
    /**
     * @return the headerDisplayClassLocationBundleKey
     */
    @XmlAttribute
    public String getHeaderDisplayClassLocationBundleKey() {
	return headerDisplayClassLocationBundleKey;
    }

    /**
     * @return the displayFactoryClassLocationBundleKey
     */
    @XmlAttribute
    public String getDisplayFactoryClassLocationBundleKey() {
	return displayFactoryClassLocationBundleKey;
    }

    /**
     * @return the customizerClassLocationBundleKey
     */
    @XmlAttribute
    public String getCustomizerClassLocationBundleKey() {
	return customizerClassLocationBundleKey;
    }

    /**
     * @return the location
     */
    @XmlAttribute
    public String getLocation() {
	return location;
    }

    // Bruno tem de estar como XmlElement ou assim????
    /**
     * @return the mediaConfig
     */
    @XmlElement
    public MediaConfig getMediaConfig() {
	return mediaConfig;
    }

    /**
     * @return the defaultAcquisitionConfig
     */
    public List<DefaultAcquisitionConfig> getDefaultAcquisitionConfig() {
	return defaultAcquisitionConfig;
    }

    /**
     * @return the display
     */
    public List<Display> getDisplay() {
	return display;
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
     * @param displayStringBundleKey
     *            the displayStringBundleKey to set
     */
    public void setDisplayStringBundleKey(final String displayStringBundleKey) {
	this.displayStringBundleKey = displayStringBundleKey;
    }

    /**
     * @param descriptionStringBundleKey
     *            the descriptionStringBundleKey to set
     */
    public void setDescriptionStringBundleKey(final String descriptionStringBundleKey) {
	this.descriptionStringBundleKey = descriptionStringBundleKey;
    }

    /**
     * @param iconLocationBundleKey
     *            the iconLocationBundleKey to set
     */
    public void setIconLocationBundleKey(final String iconLocationBundleKey) {
	this.iconLocationBundleKey = iconLocationBundleKey;
    }

    /**
     * @param desktopLocationBundleKey
     *            the desktopLocationBundleKey to set
     */
    public void setDesktopLocationBundleKey(final String desktopLocationBundleKey) {
	this.desktopLocationBundleKey = desktopLocationBundleKey;
    }

    /**
     * @param toolTipBundleKey
     *            the toolTipBundleKey to set
     */
    public void setToolTipBundleKey(final String toolTipBundleKey) {
	this.toolTipBundleKey = toolTipBundleKey;
    }

    /**
     * @param dataModelClassLocationBundleKey
     *            the dataModelClassLocationBundleKey to set
     */
    public void setDataModelClassLocationBundleKey(final String dataModelClassLocationBundleKey) {
	this.dataModelClassLocationBundleKey = dataModelClassLocationBundleKey;
    }

    /**
     * @param videoLocation
     *            the videoLocation to set
     */
    // Delete
    // public void setVideoLocation(String videoLocation) {
    // this.videoLocation = videoLocation;
    // }
    /**
     * @param headerDisplayClassLocationBundleKey
     *            the headerDisplayClassLocationBundleKey to set
     */
    public void setHeaderDisplayClassLocationBundleKey(final String headerDisplayClassLocationBundleKey) {
	this.headerDisplayClassLocationBundleKey = headerDisplayClassLocationBundleKey;
    }

    /**
     * @param displayFactoryClassLocationBundleKey
     *            the displayFactoryClassLocationBundleKey to set
     */
    public void setDisplayFactoryClassLocationBundleKey(final String displayFactoryClassLocationBundleKey) {
	this.displayFactoryClassLocationBundleKey = displayFactoryClassLocationBundleKey;
    }

    /**
     * @param customizerClassLocationBundleKey
     *            the customizerClassLocationBundleKey to set
     */
    public void setCustomizerClassLocationBundleKey(final String customizerClassLocationBundleKey) {
	this.customizerClassLocationBundleKey = customizerClassLocationBundleKey;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(final String location) {
	this.location = location;
    }

    /**
     * @param mediaConfig
     *            the mediaConfig to set
     */
    public void setMediaConfig(final MediaConfig mediaConfig) {
	this.mediaConfig = mediaConfig;
    }

    /**
     * @param defaultAcquisitionConfig
     *            the defaultAcquisitionConfig to set
     */
    public void setDefaultAcquisitionConfig(final List<DefaultAcquisitionConfig> defaultAcquisitionConfig) {
	this.defaultAcquisitionConfig = defaultAcquisitionConfig;
    }

    /**
     * @param display
     *            the display to set
     */
    public void setDisplay(final List<Display> display) {
	this.display = display;
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
    public void setLocalizationBundle(final List<LocalizationBundle> localizationBundle) {
	this.localizationBundle = localizationBundle;
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	final Apparatus other = (Apparatus) obj;
	if ((displayStringBundleKey == null) ? (other.displayStringBundleKey != null) : !displayStringBundleKey.equals(other.displayStringBundleKey)) {
	    return false;
	}
	if ((iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !iconLocationBundleKey.equals(other.iconLocationBundleKey)) {
	    return false;
	}
	if ((desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null) : !desktopLocationBundleKey.equals(other.desktopLocationBundleKey)) {
	    return false;
	}
	if ((toolTipBundleKey == null) ? (other.toolTipBundleKey != null) : !toolTipBundleKey.equals(other.toolTipBundleKey)) {
	    return false;
	}
	if ((dataModelClassLocationBundleKey == null) ? (other.dataModelClassLocationBundleKey != null)
		: !dataModelClassLocationBundleKey.equals(other.dataModelClassLocationBundleKey)) {
	    return false;
	}
	// Delete
	// if ((this.videoLocation == null) ? (other.videoLocation != null) :
	// !this.videoLocation.equals(other.videoLocation)) {
	// return false;
	// }
	if ((headerDisplayClassLocationBundleKey == null) ? (other.headerDisplayClassLocationBundleKey != null)
		: !headerDisplayClassLocationBundleKey.equals(other.headerDisplayClassLocationBundleKey)) {
	    return false;
	}
	if ((displayFactoryClassLocationBundleKey == null) ? (other.displayFactoryClassLocationBundleKey != null)
		: !displayFactoryClassLocationBundleKey.equals(other.displayFactoryClassLocationBundleKey)) {
	    return false;
	}
	if ((customizerClassLocationBundleKey == null) ? (other.customizerClassLocationBundleKey != null)
		: !customizerClassLocationBundleKey.equals(other.customizerClassLocationBundleKey)) {
	    return false;
	}
	if ((location == null) ? (other.location != null) : !location.equals(other.location)) {
	    return false;
	}
	if (mediaConfig != other.mediaConfig && (mediaConfig == null || !mediaConfig.equals(other.mediaConfig))) {
	    return false;
	}
	if (defaultAcquisitionConfig != other.defaultAcquisitionConfig
		&& (defaultAcquisitionConfig == null || !defaultAcquisitionConfig.equals(other.defaultAcquisitionConfig))) {
	    return false;
	}
	if (display != other.display && (display == null || !display.equals(other.display))) {
	    return false;
	}
	if (webResource != other.webResource && (webResource == null || !webResource.equals(other.webResource))) {
	    return false;
	}
	if (localizationBundle != other.localizationBundle && (localizationBundle == null || !localizationBundle.equals(other.localizationBundle))) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	int hash = 7;
	hash = 83 * hash + (displayStringBundleKey != null ? displayStringBundleKey.hashCode() : 0);
	hash = 83 * hash + (iconLocationBundleKey != null ? iconLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (desktopLocationBundleKey != null ? desktopLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (toolTipBundleKey != null ? toolTipBundleKey.hashCode() : 0);
	hash = 83 * hash + (dataModelClassLocationBundleKey != null ? dataModelClassLocationBundleKey.hashCode() : 0);
	// Delete
	// hash = 83 * hash + (this.videoLocation != null ?
	// this.videoLocation.hashCode() : 0);
	hash = 83 * hash + (headerDisplayClassLocationBundleKey != null ? headerDisplayClassLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (displayFactoryClassLocationBundleKey != null ? displayFactoryClassLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (customizerClassLocationBundleKey != null ? customizerClassLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (location != null ? location.hashCode() : 0);
	hash = 83 * hash + (mediaConfig != null ? mediaConfig.hashCode() : 0);
	hash = 83 * hash + (defaultAcquisitionConfig != null ? defaultAcquisitionConfig.hashCode() : 0);
	hash = 83 * hash + (display != null ? display.hashCode() : 0);
	hash = 83 * hash + (webResource != null ? webResource.hashCode() : 0);
	hash = 83 * hash + (localizationBundle != null ? localizationBundle.hashCode() : 0);
	return hash;
    }

}
