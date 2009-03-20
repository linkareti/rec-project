package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class LocalizationBundle extends AbstractConfigBean {
  
  private String location;
  
  private String name;
 
  public LocalizationBundle() { 
  }

  /**
   * @return the location
   */
  @XmlAttribute
  public java.lang.String getLocation() {
    return location;
  }

  /**
   * @return the name
   */
  @XmlAttribute
  public java.lang.String getName() {
    return name;
  }

  /**
   * @param location the location to set
   */
  public void setLocation(java.lang.String location) {
    this.location = location;
  }

  /**
   * @param name the name to set
   */
  public void setName(java.lang.String name) {
    this.name = name;
  }
  
  
}
