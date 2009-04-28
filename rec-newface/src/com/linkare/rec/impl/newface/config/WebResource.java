package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class WebResource extends DisplayNode {
  
  /* OLD
   * public class WebResource extends DisplayNode  implements CommonBean, OrderedItem
   * 
   * private int _Order = Integer.MAX_VALUE;
    private boolean _InternalBrowser = false;
    private String _ToolTipLocationBundleKey = "";
    private String _DisplayStringBundleKey;
    private String _IconLocationBundleKey = "";
    private String _LocationBundleKey;
    private PropertyChangeSupport eventListeners;
   */
  
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
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      changeSupport.firePropertyChange("internalBrowser", this.internalBrowser,
          this.internalBrowser = internalBrowser);
    }
  }

  /**
   * @param toolTipLocationBundleKey the toolTipLocationBundleKey to set
   */
  public void setToolTipLocationBundleKey(String toolTipLocationBundleKey) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      if (this.toolTipLocationBundleKey == null
          && toolTipLocationBundleKey == null) {
        return; // to avoid unwanted events to trigger
      }
      changeSupport.firePropertyChange("toolTipLocationBundleKey",
          this.toolTipLocationBundleKey,
          this.toolTipLocationBundleKey = toolTipLocationBundleKey);
    }
  }

  /**
   * @param displayStringBundleKey the displayStringBundleKey to set
   */
  public void setDisplayStringBundleKey(String displayStringBundleKey) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      if (this.displayStringBundleKey == null && displayStringBundleKey == null) {
        return; // to avoid unwanted events to trigger
      }
      changeSupport.firePropertyChange("displayStringBundleKey",
          this.displayStringBundleKey,
          this.displayStringBundleKey = displayStringBundleKey);
    }
  }

  /**
   * @param iconLocationBundleKey the iconLocationBundleKey to set
   */
  public void setIconLocationBundleKey(String iconLocationBundleKey) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      if (this.iconLocationBundleKey == null && iconLocationBundleKey == null) {
        return; // to avoid unwanted events to trigger
      }
      changeSupport.firePropertyChange("iconLocationBundleKey",
          this.iconLocationBundleKey,
          this.iconLocationBundleKey = iconLocationBundleKey);
    }
  }

  /**
   * @param locationBundleKey the locationBundleKey to set
   */
  public void setLocationBundleKey(String locationBundleKey) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      if (this.locationBundleKey == null && locationBundleKey == null) {
        return; // to avoid unwanted events to trigger
      }
      changeSupport.firePropertyChange("locationBundleKey",
          this.locationBundleKey, this.locationBundleKey = locationBundleKey);
    }
  }
  
}
