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
    public void setInternalBrowser(boolean internalBrowser) {
        changeSupport.firePropertyChange("internalBrowser", this.internalBrowser,
                this.internalBrowser = internalBrowser);
    }

    /**
     * @param toolTipLocationBundleKey the toolTipLocationBundleKey to set
     */
    public void setToolTipLocationBundleKey(String toolTipLocationBundleKey) {
        changeSupport.firePropertyChange("toolTipLocationBundleKey",
                this.toolTipLocationBundleKey,
                this.toolTipLocationBundleKey = toolTipLocationBundleKey);
    }

    /**
     * @param displayStringBundleKey the displayStringBundleKey to set
     */
    public void setDisplayStringBundleKey(String displayStringBundleKey) {
        changeSupport.firePropertyChange("displayStringBundleKey",
                this.displayStringBundleKey,
                this.displayStringBundleKey = displayStringBundleKey);
    }

    /**
     * @param iconLocationBundleKey the iconLocationBundleKey to set
     */
    public void setIconLocationBundleKey(String iconLocationBundleKey) {
        changeSupport.firePropertyChange("iconLocationBundleKey",
                this.iconLocationBundleKey,
                this.iconLocationBundleKey = iconLocationBundleKey);
    }

    /**
     * @param locationBundleKey the locationBundleKey to set
     */
    public void setLocationBundleKey(String locationBundleKey) {
        changeSupport.firePropertyChange("locationBundleKey",
                this.locationBundleKey, this.locationBundleKey = locationBundleKey);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WebResource other = (WebResource) obj;
        if (this.internalBrowser != other.internalBrowser) {
            return false;
        }
        if ((this.toolTipLocationBundleKey == null) ? (other.toolTipLocationBundleKey != null) : !this.toolTipLocationBundleKey.equals(other.toolTipLocationBundleKey)) {
            return false;
        }
        if ((this.displayStringBundleKey == null) ? (other.displayStringBundleKey != null) : !this.displayStringBundleKey.equals(other.displayStringBundleKey)) {
            return false;
        }
        if ((this.iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !this.iconLocationBundleKey.equals(other.iconLocationBundleKey)) {
            return false;
        }
        if ((this.locationBundleKey == null) ? (other.locationBundleKey != null) : !this.locationBundleKey.equals(other.locationBundleKey)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + (this.internalBrowser ? 1 : 0);
        hash = 13 * hash + (this.toolTipLocationBundleKey != null ? this.toolTipLocationBundleKey.hashCode() : 0);
        hash = 13 * hash + (this.displayStringBundleKey != null ? this.displayStringBundleKey.hashCode() : 0);
        hash = 13 * hash + (this.iconLocationBundleKey != null ? this.iconLocationBundleKey.hashCode() : 0);
        hash = 13 * hash + (this.locationBundleKey != null ? this.locationBundleKey.hashCode() : 0);
        return hash;
    }
    
}
