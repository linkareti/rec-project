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
   * @param iconLocationBundleKey the iconLocationBundleKey to set
   */
  public void setIconLocationBundleKey(String iconLocationBundleKey) {
    this.iconLocationBundleKey = iconLocationBundleKey;
  }

  /**
   * @param toolTipBundleKey the toolTipBundleKey to set
   */
  public void setToolTipBundleKey(String toolTipBundleKey) {
    this.toolTipBundleKey = toolTipBundleKey;
  }

  /**
   * @param displayStringBundleKey the displayStringBundleKey to set
   */
  public void setDisplayStringBundleKey(String displayStringBundleKey) {
    this.displayStringBundleKey = displayStringBundleKey;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * @param desktopLocationBundleKey the desktopLocationBundleKey to set
   */
  public void setDesktopLocationBundleKey(String desktopLocationBundleKey) {
    this.desktopLocationBundleKey = desktopLocationBundleKey;
  }

  /**
   * @param apparatus the apparatus to set
   */
  public void setApparatus(List<Apparatus> apparatus) {
    this.apparatus = apparatus;
  }

  /**
   * @param webResource the webResource to set
   */
  public void setWebResource(List<WebResource> webResource) {
    this.webResource = webResource;
  }

  /**
   * @param localizationBundle the localizationBundle to set
   */
  public void setLocalizationBundle(List<LocalizationBundle> localizationBundle) {
    this.localizationBundle = localizationBundle;
  }
}
