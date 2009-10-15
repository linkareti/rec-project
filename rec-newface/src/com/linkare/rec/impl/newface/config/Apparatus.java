package com.linkare.rec.impl.newface.config;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.linkare.rec.impl.i18n.ReCResourceBundle;

@XmlType
public class Apparatus extends DisplayNode {

    private String displayStringBundleKey = "";
    
    private String descriptionStringBundleKey = "";

    private String iconLocationBundleKey = "";

    private String desktopLocationBundleKey = "";

    private String toolTipBundleKey = "";

    private String dataModelClassLocationBundleKey = "";

    //private String videoLocation = "";

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

    public Icon getIcon() {
	return ReCResourceBundle.findImageIconOrDefault(getIconLocationBundleKey(), null);
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
    //Delete
    //    @XmlAttribute
    //    public String getVideoLocation() {
    //        return videoLocation;
    //    }
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

    //Bruno tem de estar como XmlElement ou assim????
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
    public void setDisplayStringBundleKey(String displayStringBundleKey) {
	this.displayStringBundleKey = displayStringBundleKey;
    }

    /**
     * @param descriptionStringBundleKey
     *            the descriptionStringBundleKey to set
     */
    public void setDescriptionStringBundleKey(String descriptionStringBundleKey) {
	this.descriptionStringBundleKey = descriptionStringBundleKey;
    }

    /**
     * @param iconLocationBundleKey
     *            the iconLocationBundleKey to set
     */
    public void setIconLocationBundleKey(String iconLocationBundleKey) {
	this.iconLocationBundleKey = iconLocationBundleKey;
    }

    /**
     * @param desktopLocationBundleKey
     *            the desktopLocationBundleKey to set
     */
    public void setDesktopLocationBundleKey(String desktopLocationBundleKey) {
	this.desktopLocationBundleKey = desktopLocationBundleKey;
    }

    /**
     * @param toolTipBundleKey
     *            the toolTipBundleKey to set
     */
    public void setToolTipBundleKey(String toolTipBundleKey) {
	this.toolTipBundleKey = toolTipBundleKey;
    }

    /**
     * @param dataModelClassLocationBundleKey
     *            the dataModelClassLocationBundleKey to set
     */
    public void setDataModelClassLocationBundleKey(String dataModelClassLocationBundleKey) {
	this.dataModelClassLocationBundleKey = dataModelClassLocationBundleKey;
    }

    /**
     * @param videoLocation
     *            the videoLocation to set
     */
    //Delete
    //    public void setVideoLocation(String videoLocation) {
    //        this.videoLocation = videoLocation;
    //    }
    /**
     * @param headerDisplayClassLocationBundleKey
     *            the headerDisplayClassLocationBundleKey to set
     */
    public void setHeaderDisplayClassLocationBundleKey(String headerDisplayClassLocationBundleKey) {
	this.headerDisplayClassLocationBundleKey = headerDisplayClassLocationBundleKey;
    }

    /**
     * @param displayFactoryClassLocationBundleKey
     *            the displayFactoryClassLocationBundleKey to set
     */
    public void setDisplayFactoryClassLocationBundleKey(String displayFactoryClassLocationBundleKey) {
	this.displayFactoryClassLocationBundleKey = displayFactoryClassLocationBundleKey;
    }

    /**
     * @param customizerClassLocationBundleKey
     *            the customizerClassLocationBundleKey to set
     */
    public void setCustomizerClassLocationBundleKey(String customizerClassLocationBundleKey) {
	this.customizerClassLocationBundleKey = customizerClassLocationBundleKey;
    }

    /**
     * @param location
     *            the location to set
     */
    public void setLocation(String location) {
	this.location = location;
    }

    /**
     * @param mediaConfig
     *            the mediaConfig to set
     */
    public void setMediaConfig(MediaConfig mediaConfig) {
	this.mediaConfig = mediaConfig;
    }

    /**
     * @param defaultAcquisitionConfig
     *            the defaultAcquisitionConfig to set
     */
    public void setDefaultAcquisitionConfig(List<DefaultAcquisitionConfig> defaultAcquisitionConfig) {
	this.defaultAcquisitionConfig = defaultAcquisitionConfig;
    }

    /**
     * @param display
     *            the display to set
     */
    public void setDisplay(List<Display> display) {
	this.display = display;
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
	final Apparatus other = (Apparatus) obj;
	if ((this.displayStringBundleKey == null) ? (other.displayStringBundleKey != null)
		: !this.displayStringBundleKey.equals(other.displayStringBundleKey)) {
	    return false;
	}
	if ((this.iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !this.iconLocationBundleKey
		.equals(other.iconLocationBundleKey)) {
	    return false;
	}
	if ((this.desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null)
		: !this.desktopLocationBundleKey.equals(other.desktopLocationBundleKey)) {
	    return false;
	}
	if ((this.toolTipBundleKey == null) ? (other.toolTipBundleKey != null) : !this.toolTipBundleKey
		.equals(other.toolTipBundleKey)) {
	    return false;
	}
	if ((this.dataModelClassLocationBundleKey == null) ? (other.dataModelClassLocationBundleKey != null)
		: !this.dataModelClassLocationBundleKey.equals(other.dataModelClassLocationBundleKey)) {
	    return false;
	}
	//Delete
	//        if ((this.videoLocation == null) ? (other.videoLocation != null) : !this.videoLocation.equals(other.videoLocation)) {
	//            return false;
	//        }
	if ((this.headerDisplayClassLocationBundleKey == null) ? (other.headerDisplayClassLocationBundleKey != null)
		: !this.headerDisplayClassLocationBundleKey.equals(other.headerDisplayClassLocationBundleKey)) {
	    return false;
	}
	if ((this.displayFactoryClassLocationBundleKey == null) ? (other.displayFactoryClassLocationBundleKey != null)
		: !this.displayFactoryClassLocationBundleKey.equals(other.displayFactoryClassLocationBundleKey)) {
	    return false;
	}
	if ((this.customizerClassLocationBundleKey == null) ? (other.customizerClassLocationBundleKey != null)
		: !this.customizerClassLocationBundleKey.equals(other.customizerClassLocationBundleKey)) {
	    return false;
	}
	if ((this.location == null) ? (other.location != null) : !this.location.equals(other.location)) {
	    return false;
	}
	if (this.mediaConfig != other.mediaConfig
		&& (this.mediaConfig == null || !this.mediaConfig.equals(other.mediaConfig))) {
	    return false;
	}
	if (this.defaultAcquisitionConfig != other.defaultAcquisitionConfig
		&& (this.defaultAcquisitionConfig == null || !this.defaultAcquisitionConfig
			.equals(other.defaultAcquisitionConfig))) {
	    return false;
	}
	if (this.display != other.display && (this.display == null || !this.display.equals(other.display))) {
	    return false;
	}
	if (this.webResource != other.webResource
		&& (this.webResource == null || !this.webResource.equals(other.webResource))) {
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
	int hash = 7;
	hash = 83 * hash + (this.displayStringBundleKey != null ? this.displayStringBundleKey.hashCode() : 0);
	hash = 83 * hash + (this.iconLocationBundleKey != null ? this.iconLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (this.desktopLocationBundleKey != null ? this.desktopLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (this.toolTipBundleKey != null ? this.toolTipBundleKey.hashCode() : 0);
	hash = 83 * hash
		+ (this.dataModelClassLocationBundleKey != null ? this.dataModelClassLocationBundleKey.hashCode() : 0);
	//Delete
	//        hash = 83 * hash + (this.videoLocation != null ? this.videoLocation.hashCode() : 0);
	hash = 83
		* hash
		+ (this.headerDisplayClassLocationBundleKey != null ? this.headerDisplayClassLocationBundleKey
			.hashCode() : 0);
	hash = 83
		* hash
		+ (this.displayFactoryClassLocationBundleKey != null ? this.displayFactoryClassLocationBundleKey
			.hashCode() : 0);
	hash = 83
		* hash
		+ (this.customizerClassLocationBundleKey != null ? this.customizerClassLocationBundleKey.hashCode() : 0);
	hash = 83 * hash + (this.location != null ? this.location.hashCode() : 0);
	hash = 83 * hash + (this.mediaConfig != null ? this.mediaConfig.hashCode() : 0);
	hash = 83 * hash + (this.defaultAcquisitionConfig != null ? this.defaultAcquisitionConfig.hashCode() : 0);
	hash = 83 * hash + (this.display != null ? this.display.hashCode() : 0);
	hash = 83 * hash + (this.webResource != null ? this.webResource.hashCode() : 0);
	hash = 83 * hash + (this.localizationBundle != null ? this.localizationBundle.hashCode() : 0);
	return hash;
    }

}
