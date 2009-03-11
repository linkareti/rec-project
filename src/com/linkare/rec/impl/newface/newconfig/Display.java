package com.linkare.rec.impl.newface.newconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Display extends DisplayNode {

    private boolean offlineCapable = false;

    private boolean selected = true;

    private String displayStringBundleKey = "";

    private String iconLocationBundleKey = "";

    private String toolTipBundleKey = "";

    private String classLocationBundleKey = "";

    public Display() {
    }

    /**
     * @return the offlineCapable
     */
    @XmlAttribute
    public boolean isOfflineCapable() {
	return offlineCapable;
    }

    /**
     * @return the selected
     */
    @Override
    @XmlAttribute
    public boolean isSelected() {
	return selected;
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
     * @param offlineCapable
     *            the offlineCapable to set
     */
    public void setOfflineCapable(boolean offlineCapable) {
	this.offlineCapable = offlineCapable;
    }

    /**
     * @param selected
     *            the selected to set
     */
    @Override
    public void setSelected(boolean selected) {
	this.selected = selected;
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

}
