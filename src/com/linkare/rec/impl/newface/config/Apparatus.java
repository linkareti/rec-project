package com.linkare.rec.impl.newface.config;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Apparatus extends DisplayNode {

  private String displayStringBundleKey = "";
  
  private String iconLocationBundleKey = "";
  
  private String desktopLocationBundleKey = "";
  
  private String toolTipBundleKey = "";
  
  private String dataModelClassLocationBundleKey = "";
  
  private String videoLocation = "";
  
  private String headerDisplayClassLocationBundleKey = "";
  
  private String displayFactoryClassLocationBundleKey = "";         
  
  private String customizerClassLocationBundleKey = "";
  
  private String location = "";
  
  private List<DefaultAcquisitionConfig> defaultAcquisitionConfig = new ArrayList<DefaultAcquisitionConfig>(); // List<DefaultAcquisitionConfig>
  
  private List<Display> display = new ArrayList<Display>();  // List<Display>
  
  private List<WebResource> webResource = new ArrayList<WebResource>();  // List<WebResource>
  
  private List<LocalizationBundle> localizationBundle = new ArrayList<LocalizationBundle>(); // List<LocalizationBundle>
  
  public Apparatus() {
    // TODO Auto-generated constructor stub
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
  @XmlAttribute
  public String getVideoLocation() {
    return videoLocation;
  }

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
   * @param displayStringBundleKey the displayStringBundleKey to set
   */
  public void setDisplayStringBundleKey(String displayStringBundleKey) {
    this.displayStringBundleKey = displayStringBundleKey;
  }

  /**
   * @param iconLocationBundleKey the iconLocationBundleKey to set
   */
  public void setIconLocationBundleKey(String iconLocationBundleKey) {
    this.iconLocationBundleKey = iconLocationBundleKey;
  }

  /**
   * @param desktopLocationBundleKey the desktopLocationBundleKey to set
   */
  public void setDesktopLocationBundleKey(String desktopLocationBundleKey) {
    this.desktopLocationBundleKey = desktopLocationBundleKey;
  }

  /**
   * @param toolTipBundleKey the toolTipBundleKey to set
   */
  public void setToolTipBundleKey(String toolTipBundleKey) {
    this.toolTipBundleKey = toolTipBundleKey;
  }

  /**
   * @param dataModelClassLocationBundleKey the dataModelClassLocationBundleKey to set
   */
  public void setDataModelClassLocationBundleKey(
      String dataModelClassLocationBundleKey) {
    this.dataModelClassLocationBundleKey = dataModelClassLocationBundleKey;
  }

  /**
   * @param videoLocation the videoLocation to set
   */
  public void setVideoLocation(String videoLocation) {
    this.videoLocation = videoLocation;
  }

  /**
   * @param headerDisplayClassLocationBundleKey the headerDisplayClassLocationBundleKey to set
   */
  public void setHeaderDisplayClassLocationBundleKey(
      String headerDisplayClassLocationBundleKey) {
    this.headerDisplayClassLocationBundleKey = headerDisplayClassLocationBundleKey;
  }

  /**
   * @param displayFactoryClassLocationBundleKey the displayFactoryClassLocationBundleKey to set
   */
  public void setDisplayFactoryClassLocationBundleKey(
      String displayFactoryClassLocationBundleKey) {
    this.displayFactoryClassLocationBundleKey = displayFactoryClassLocationBundleKey;
  }

  /**
   * @param customizerClassLocationBundleKey the customizerClassLocationBundleKey to set
   */
  public void setCustomizerClassLocationBundleKey(
      String customizerClassLocationBundleKey) {
    this.customizerClassLocationBundleKey = customizerClassLocationBundleKey;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * @param defaultAcquisitionConfig the defaultAcquisitionConfig to set
   */
  public void setDefaultAcquisitionConfig(
      List<DefaultAcquisitionConfig> defaultAcquisitionConfig) {
    this.defaultAcquisitionConfig = defaultAcquisitionConfig;
  }

  /**
   * @param display the display to set
   */
  public void setDisplay(List<Display> display) {
    this.display = display;
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
