package com.linkare.rec.impl.newface.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class DisplayNode extends AbstractConfigBean {
  
  private int order=0;
  
  private boolean enabled=false;
  
  private boolean visible = true;
  
  private boolean selected = true;
  
  private boolean connected = false;

  /**
   * Creates a new <code>DisplayNode</code>.
   */
  public DisplayNode() {
  }

  /**
   * @return the order
   */
  @XmlAttribute
  public int getOrder() {
    return order;
  }

  /**
   * @return the enabled
   */
  @XmlAttribute
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * @return the visible
   */
  @XmlAttribute
  public boolean isVisible() {
    return visible;
  }

  /**
   * @return the selected
   */
  @XmlAttribute
  public boolean isSelected() {
    return selected;
  }

  /**
   * @return the connected
   */
  @XmlAttribute
  public boolean isConnected() {
    return connected;
  }

  /**
   * @param order the order to set
   */
  public void setOrder(int order) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      changeSupport.firePropertyChange("order", this.order, this.order = order);
    }
  }

  /**
   * @param enabled the enabled to set
   */
  public void setEnabled(boolean enabled) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      changeSupport.firePropertyChange("enabled", this.enabled,
          this.enabled = enabled);
    }
  }

  /**
   * @param visible the visible to set
   */
  public void setVisible(boolean visible) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      changeSupport.firePropertyChange("visible", this.visible,
          this.visible = visible);
    }
  }

  /**
   * @param selected the selected to set
   */
  public void setSelected(boolean selected) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      changeSupport.firePropertyChange("selected", this.selected,
          this.selected = selected);
    }
  }

  /**
   * @param connected the connected to set
   */
  public void setConnected(boolean connected) {
    if (changeSupport.getPropertyChangeListeners().length > 0) {
      changeSupport.firePropertyChange("connected", this.connected,
          this.connected = connected);
    }
  }
  
}

