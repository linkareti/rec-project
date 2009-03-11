package com.linkare.rec.impl.newface.newconfig;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//TODO Equals, Hashcode and Check PropertyChangeListener add on indexed properties

@XmlRootElement
public class ReCFaceConfig extends AbstractConfigBean {

  private boolean autoConnectLab = false;
  
  private boolean showVideoFrame = false;
  
  private boolean enableVideoFrame = true;
  
  private boolean enableLoginPassword = false;
  
  private boolean showChatFrame = false;
  
  private boolean enableChatFrame = true;
  
  private boolean showUserList = false;
  
  private boolean enableUsersList = true;
  
  private long usersListRefreshRateMs = 2000;
  
  private boolean enterApparatusChatRoom = false;
  
  private String aboutPageLocationBundleKey = "";
  
  private String splashIconLocationBundleKey = "";
  
  private String iconLocationBundleKey = "";
  
  private String desktopLocationBundleKey = null;
  
  private String frameTitleBundleKey;
  
  private String iconSponsorLocationBundleKey = "";
  
  private String helpPageLocationBundleKey = "";
  
  private List<Lab> labList = new ArrayList<Lab>();
  
  private List<WebResource> webResourceList = new ArrayList<WebResource>();

  private int appPreferredWidth = 848;

  private int appPreferredHeight = 478;

  /**
   * Creates a new <code>ReCConfig</code>.
   */
  public ReCFaceConfig() {  
  }
  
  //---------------------------------------------------------------------------
  // Getters

  /**
   * @return the autoConnectLab
   */
  @XmlAttribute
  public boolean isAutoConnectLab() {
    return autoConnectLab;
  }

  /**
   * @return the showVideoFrame
   */
  @XmlAttribute
  public boolean isShowVideoFrame() {
    return showVideoFrame;
  }

  /**
   * @return the enableVideoFrame
   */
  @XmlAttribute
  public boolean isEnableVideoFrame() {
    return enableVideoFrame;
  }

  /**
   * @return the enableLoginPassword
   */
  @XmlAttribute
  public boolean isEnableLoginPassword() {
    return enableLoginPassword;
  }

  /**
   * @return the showChatFrame
   */
  @XmlAttribute
  public boolean isShowChatFrame() {
    return showChatFrame;
  }

  /**
   * @return the enableChatFrame
   */
  @XmlAttribute
  public boolean isEnableChatFrame() {
    return enableChatFrame;
  }

  /**
   * @return the showUserList
   */
  @XmlAttribute
  public boolean isShowUserList() {
    return showUserList;
  }

  /**
   * @return the enableUsersList
   */
  @XmlAttribute
  public boolean isEnableUsersList() {
    return enableUsersList;
  }

  /**
   * @return the usersListRefreshRateMs
   */
  @XmlAttribute
  public long getUsersListRefreshRateMs() {
    return usersListRefreshRateMs;
  }

  /**
   * @return the enterApparatusChatRoom
   */
  @XmlAttribute
  public boolean isEnterApparatusChatRoom() {
    return enterApparatusChatRoom;
  }

  /**
   * @return the aboutPageLocationBundleKey
   */
  @XmlAttribute
  public String getAboutPageLocationBundleKey() {
    return aboutPageLocationBundleKey;
  }

  /**
   * @return the splashIconLocationBundleKey
   */
  @XmlAttribute
  public String getSplashIconLocationBundleKey() {
    return splashIconLocationBundleKey;
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
   * @return the frameTitleBundleKey
   */
  @XmlAttribute
  public String getFrameTitleBundleKey() {
    return frameTitleBundleKey;
  }

  /**
   * @return the iconSponsorLocationBundleKey
   */
  @XmlAttribute
  public String getIconSponsorLocationBundleKey() {
    return iconSponsorLocationBundleKey;
  }

  /**
   * @return the helpPageLocationBundleKey
   */
  @XmlAttribute
  public String getHelpPageLocationBundleKey() {
    return helpPageLocationBundleKey;
  }

  /**
   * @return the lab
   */
  public List<Lab> getLab() {
    return labList;
  }

    /**
     * @return the webResource
     */
    public List<WebResource> getWebResource() {
	return webResourceList;
    }

    /**
     * @return the appPreferredWidth
     */
    public int getAppPreferredWidth() {
	return appPreferredWidth;
    }

    /**
     * @return the appPreferredHeight
     */
    public int getAppPreferredHeight() {
	return appPreferredHeight;
    }

    // ---------------------------------------------------------------------------
    // Setters

    /**
     * @param appPreferredWidth
     *            the appPreferredWidth to set
     */
    public void setAppPreferredWidth(int appPreferredWidth) {
	this.appPreferredWidth = appPreferredWidth;
    }

    /**
     * @param appPreferredHeight
     *            the appPreferredHeight to set
     */
    public void setAppPreferredHeight(int appPreferredHeight) {
	this.appPreferredHeight = appPreferredHeight;
    }

/**
   * @param autoConnectLab the autoConnectLab to set
   */
  public void setAutoConnectLab(boolean autoConnectLab) {
    changeSupport.firePropertyChange("autoConnectLab", this.autoConnectLab,
        this.autoConnectLab = autoConnectLab);
  }

  /**
   * @param showVideoFrame the showVideoFrame to set
   */
  public void setShowVideoFrame(boolean showVideoFrame) {
    changeSupport.firePropertyChange("showVideoFrame", this.showVideoFrame,
        this.showVideoFrame = showVideoFrame);
  }

  /**
   * @param enableVideoFrame the enableVideoFrame to set
   */
  public void setEnableVideoFrame(boolean enableVideoFrame) {
    changeSupport.firePropertyChange("enableVideoFrame", this.enableVideoFrame,
        this.enableVideoFrame = enableVideoFrame);
  }

  /**
   * @param enableLoginPassword the enableLoginPassword to set
   */
  public void setEnableLoginPassword(boolean enableLoginPassword) {
    changeSupport.firePropertyChange("enableLoginPassword",
        this.enableLoginPassword,
        this.enableLoginPassword = enableLoginPassword);
  }

  /**
   * @param showChatFrame the showChatFrame to set
   */
  public void setShowChatFrame(boolean showChatFrame) {
    changeSupport.firePropertyChange("showChatFrame", this.showChatFrame,
        this.showChatFrame = showChatFrame);
  }

  /**
   * @param enableChatFrame the enableChatFrame to set
   */
  public void setEnableChatFrame(boolean enableChatFrame) {
    changeSupport.firePropertyChange("enableChatFrame", this.enableChatFrame,
        this.enableChatFrame = enableChatFrame);
  }

  /**
   * @param showUserList the showUserList to set
   */
  public void setShowUserList(boolean showUserList) {
    changeSupport.firePropertyChange("showUserList", this.showUserList,
        this.showUserList = showUserList);
  }

  /**
   * @param enableUsersList the enableUsersList to set
   */
  public void setEnableUsersList(boolean enableUsersList) {
    changeSupport.firePropertyChange("enableUsersList", this.enableUsersList,
        this.enableUsersList = enableUsersList);
  }

  /**
   * @param usersListRefreshRateMs the usersListRefreshRateMs to set
   */
  public void setUsersListRefreshRateMs(long usersListRefreshRateMs) {
    changeSupport.firePropertyChange("usersListRefreshRateMs",
        this.usersListRefreshRateMs,
        this.usersListRefreshRateMs = usersListRefreshRateMs);
  }

  /**
   * @param enterApparatusChatRoom the enterApparatusChatRoom to set
   */
  public void setEnterApparatusChatRoom(boolean enterApparatusChatRoom) {
    changeSupport.firePropertyChange("enterApparatusChatRoom",
        this.enterApparatusChatRoom,
        this.enterApparatusChatRoom = enterApparatusChatRoom);
  }

  /**
   * @param aboutPageLocationBundleKey the aboutPageLocationBundleKey to set
   */
  public void setAboutPageLocationBundleKey(String aboutPageLocationBundleKey) {
    if (this.aboutPageLocationBundleKey == null
        && aboutPageLocationBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("aboutPageLocationBundleKey",
        this.aboutPageLocationBundleKey,
        this.aboutPageLocationBundleKey = aboutPageLocationBundleKey);
  }

  /**
   * @param splashIconLocationBundleKey the splashIconLocationBundleKey to set
   */
  public void setSplashIconLocationBundleKey(String splashIconLocationBundleKey) {
    if (this.splashIconLocationBundleKey == null
        && splashIconLocationBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("splashIconLocationBundleKey",
        this.splashIconLocationBundleKey,
        this.splashIconLocationBundleKey = splashIconLocationBundleKey);
  }

  /**
   * @param iconLocationBundleKey the iconLocationBundleKey to set
   */
  public void setIconLocationBundleKey(String iconLocationBundleKey) {
    if (this.iconLocationBundleKey == null && iconLocationBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("iconLocationBundleKey",
        this.iconLocationBundleKey,
        this.iconLocationBundleKey = iconLocationBundleKey);
  }

  /**
   * @param desktopLocationBundleKey the desktopLocationBundleKey to set
   */
  public void setDesktopLocationBundleKey(String desktopLocationBundleKey) {
    if (this.desktopLocationBundleKey == null
        && desktopLocationBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("desktopLocationBundleKey",
        this.desktopLocationBundleKey,
        this.desktopLocationBundleKey = desktopLocationBundleKey);
  }

  /**
   * @param frameTitleBundleKey the frameTitleBundleKey to set
   */
  public void setFrameTitleBundleKey(String frameTitleBundleKey) {
    if (this.frameTitleBundleKey == null && frameTitleBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("frameTitleBundleKey",
        this.frameTitleBundleKey,
        this.frameTitleBundleKey = frameTitleBundleKey);
  }

  /**
   * @param iconSponsorLocationBundleKey the iconSponsorLocationBundleKey to set
   */
  public void setIconSponsorLocationBundleKey(String iconSponsorLocationBundleKey) {
    if (this.iconSponsorLocationBundleKey == null
        && iconSponsorLocationBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("iconSponsorLocationBundleKey",
        this.iconSponsorLocationBundleKey,
        this.iconSponsorLocationBundleKey = iconSponsorLocationBundleKey);
  }

  /**
   * @param helpPageLocationBundleKey the helpPageLocationBundleKey to set
   */
  public void setHelpPageLocationBundleKey(String helpPageLocationBundleKey) {
    if (this.helpPageLocationBundleKey == null
        && helpPageLocationBundleKey == null) {
      return; // to avoid unwanted events to trigger
    }
    changeSupport.firePropertyChange("helpPageLocationBundleKey",
        this.helpPageLocationBundleKey,
        this.helpPageLocationBundleKey = helpPageLocationBundleKey);
  }

  /**
   * @param lab the lab to set
   */
  public void setLab(List<Lab> lab) {
    this.labList = lab;
  }

  /**
   * @param webResource the webResource to set
   */
  public void setWebResource(List<WebResource> webResource) {
    this.webResourceList = webResource;
  }
  
}
