package com.linkare.rec.impl.newface.newconfig;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class DefaultAcquisitionConfig extends DisplayNode {
  
  private String displayStringBundleKey = "";
  
  private String iconLocationBundleKey = "";
  
  private String toolTipBundleKey = "";
  
  private String classLocationBundleKey = "";
  
  //private PropertyChangeSupport eventListeners;
  
  public DefaultAcquisitionConfig() {
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

//  /**
//   * @return the eventListeners
//   */
//  public PropertyChangeSupport getEventListeners() {
//    return eventListeners;
//  }

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
   * @param toolTipBundleKey the toolTipBundleKey to set
   */
  public void setToolTipBundleKey(String toolTipBundleKey) {
    this.toolTipBundleKey = toolTipBundleKey;
  }

  /**
   * @param classLocationBundleKey the classLocationBundleKey to set
   */
  public void setClassLocationBundleKey(String classLocationBundleKey) {
    this.classLocationBundleKey = classLocationBundleKey;
  }

//  /**
//   * @param eventListeners the eventListeners to set
//   */
//  public void setEventListeners(PropertyChangeSupport eventListeners) {
//    this.eventListeners = eventListeners;
//  }
  
  
}
