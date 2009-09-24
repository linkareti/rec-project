package com.linkare.rec.impl.newface.config;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//TODO Check PropertyChangeListener add on indexed properties

@XmlRootElement
public class ReCFaceConfig extends AbstractConfigBean {
	
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(ReCFaceConfig.class.getName());

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

//Delete    private MediaConfig mediaConfig = null;

	private List<Lab> labList = new ArrayList<Lab>();

	private List<WebResource> webResourceList = new ArrayList<WebResource>();
	
	private List<LocalizationBundle> localizationBundleList = new ArrayList<LocalizationBundle>();

	private int appPreferredWidth = 848;

	private int appPreferredHeight = 478;

	/**
	 * Creates a new <code>ReCConfig</code>. Default Constructor.
	 */
	public ReCFaceConfig() {
	}
	
	/**
	 * Marshalls the current configuration to the OutputStream.
	 * @param os OutputStream
	 * @throws JAXBException If a JAXB error occours.
	 */
	public void marshall(OutputStream os) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);

		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(this, os);
	}
	
	/**
	 * Unmarshalls a new <code>ReCConfig</code> from the input stream. 
	 * @param is The source input stream
	 * @throws JAXBException If a JAXB error occours.
	 */
	public static ReCFaceConfig unmarshall(InputStream is) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);
		Unmarshaller un = jc.createUnmarshaller();
		return (ReCFaceConfig) un.unmarshal(is);
	}

	// -------------------------------------------------------------------------
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
	@XmlElement
	public List<Lab> getLab() {
		return labList;
	}

    //Bruno tem de estar como XmlElement ou assim????
    /**
     * @return the mediaConfig
     */
    //Delete
//    @XmlElement
//    public MediaConfig getMediaConfig() {
//        return mediaConfig;
//    }

	/**
	 * @return the webResource
	 */
	@XmlElement
	public List<WebResource> getWebResource() {
		return webResourceList;
	}
	
	/**
	 * @return the localizationBundle
	 */
	@XmlElement
	public List<LocalizationBundle> getLocalizationBundle() {
		return localizationBundleList;
	}

	/**
	 * @return the appPreferredWidth
	 */
	@XmlAttribute
	public int getAppPreferredWidth() {
		return appPreferredWidth;
	}

	/**
	 * @return the appPreferredHeight
	 */
	@XmlAttribute
	public int getAppPreferredHeight() {
		return appPreferredHeight;
	}

	// -------------------------------------------------------------------------
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
	 * @param autoConnectLab
	 *            the autoConnectLab to set
	 */
	public void setAutoConnectLab(boolean autoConnectLab) {
		changeSupport.firePropertyChange("autoConnectLab", this.autoConnectLab, this.autoConnectLab = autoConnectLab);
	}

	/**
	 * @param showVideoFrame
	 *            the showVideoFrame to set
	 */
	public void setShowVideoFrame(boolean showVideoFrame) {
		changeSupport.firePropertyChange("showVideoFrame", this.showVideoFrame, this.showVideoFrame = showVideoFrame);
	}

	/**
	 * @param enableVideoFrame
	 *            the enableVideoFrame to set
	 */
	public void setEnableVideoFrame(boolean enableVideoFrame) {
		changeSupport.firePropertyChange("enableVideoFrame", this.enableVideoFrame,
				this.enableVideoFrame = enableVideoFrame);
	}

	/**
	 * @param enableLoginPassword
	 *            the enableLoginPassword to set
	 */
	public void setEnableLoginPassword(boolean enableLoginPassword) {
		changeSupport.firePropertyChange("enableLoginPassword", this.enableLoginPassword,
				this.enableLoginPassword = enableLoginPassword);
	}

	/**
	 * @param showChatFrame
	 *            the showChatFrame to set
	 */
	public void setShowChatFrame(boolean showChatFrame) {
		changeSupport.firePropertyChange("showChatFrame", this.showChatFrame, this.showChatFrame = showChatFrame);
	}

	/**
	 * @param enableChatFrame
	 *            the enableChatFrame to set
	 */
	public void setEnableChatFrame(boolean enableChatFrame) {
		changeSupport.firePropertyChange("enableChatFrame", this.enableChatFrame,
				this.enableChatFrame = enableChatFrame);
	}

	/**
	 * @param showUserList
	 *            the showUserList to set
	 */
	public void setShowUserList(boolean showUserList) {
		changeSupport.firePropertyChange("showUserList", this.showUserList, this.showUserList = showUserList);
	}

	/**
	 * @param enableUsersList
	 *            the enableUsersList to set
	 */
	public void setEnableUsersList(boolean enableUsersList) {
		changeSupport.firePropertyChange("enableUsersList", this.enableUsersList,
				this.enableUsersList = enableUsersList);
	}

	/**
	 * @param usersListRefreshRateMs
	 *            the usersListRefreshRateMs to set
	 */
	public void setUsersListRefreshRateMs(long usersListRefreshRateMs) {
		changeSupport.firePropertyChange("usersListRefreshRateMs", this.usersListRefreshRateMs,
				this.usersListRefreshRateMs = usersListRefreshRateMs);
	}

	/**
	 * @param enterApparatusChatRoom
	 *            the enterApparatusChatRoom to set
	 */
	public void setEnterApparatusChatRoom(boolean enterApparatusChatRoom) {
		changeSupport.firePropertyChange("enterApparatusChatRoom", this.enterApparatusChatRoom,
				this.enterApparatusChatRoom = enterApparatusChatRoom);
	}

	/**
	 * @param aboutPageLocationBundleKey
	 *            the aboutPageLocationBundleKey to set
	 */
	public void setAboutPageLocationBundleKey(String aboutPageLocationBundleKey) {
		changeSupport.firePropertyChange("aboutPageLocationBundleKey", this.aboutPageLocationBundleKey,
				this.aboutPageLocationBundleKey = aboutPageLocationBundleKey);
	}

	/**
	 * @param splashIconLocationBundleKey
	 *            the splashIconLocationBundleKey to set
	 */
	public void setSplashIconLocationBundleKey(String splashIconLocationBundleKey) {
		changeSupport.firePropertyChange("splashIconLocationBundleKey", this.splashIconLocationBundleKey,
				this.splashIconLocationBundleKey = splashIconLocationBundleKey);
	}

	/**
	 * @param iconLocationBundleKey
	 *            the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(String iconLocationBundleKey) {
		changeSupport.firePropertyChange("iconLocationBundleKey", this.iconLocationBundleKey,
				this.iconLocationBundleKey = iconLocationBundleKey);
	}

	/**
	 * @param desktopLocationBundleKey
	 *            the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(String desktopLocationBundleKey) {
		changeSupport.firePropertyChange("desktopLocationBundleKey", this.desktopLocationBundleKey,
				this.desktopLocationBundleKey = desktopLocationBundleKey);
	}

	/**
	 * @param frameTitleBundleKey
	 *            the frameTitleBundleKey to set
	 */
	public void setFrameTitleBundleKey(String frameTitleBundleKey) {
		changeSupport.firePropertyChange("frameTitleBundleKey", this.frameTitleBundleKey,
				this.frameTitleBundleKey = frameTitleBundleKey);
	}

	/**
	 * @param iconSponsorLocationBundleKey
	 *            the iconSponsorLocationBundleKey to set
	 */
	public void setIconSponsorLocationBundleKey(String iconSponsorLocationBundleKey) {
		changeSupport.firePropertyChange("iconSponsorLocationBundleKey", this.iconSponsorLocationBundleKey,
				this.iconSponsorLocationBundleKey = iconSponsorLocationBundleKey);
	}

	/**
	 * @param helpPageLocationBundleKey
	 *            the helpPageLocationBundleKey to set
	 */
	public void setHelpPageLocationBundleKey(String helpPageLocationBundleKey) {
		changeSupport.firePropertyChange("helpPageLocationBundleKey", this.helpPageLocationBundleKey,
				this.helpPageLocationBundleKey = helpPageLocationBundleKey);
	}

    /**
     * @param mediaConfig the mediaConfig to set
     */
    //Delete
//    public void setMediaConfig(MediaConfig mediaConfig) {
//        this.mediaConfig = mediaConfig;
//    }

	/**
	 * @param lab
	 *            the lab to set
	 */
	public void setLab(List<Lab> lab) {
		this.labList = lab;
	}

	/**
	 * @param webResource
	 *            the webResource to set
	 */
	public void setWebResource(List<WebResource> webResource) {
		this.webResourceList = webResource;
	}
	
	/**
	 * @param localizationBundle
	 *            the localizationBundle to set
	 */
	public void setLocalizationBundle(List<LocalizationBundle> localizationBundle) {
		this.localizationBundleList = localizationBundle;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReCFaceConfig other = (ReCFaceConfig) obj;
        if (this.autoConnectLab != other.autoConnectLab) {
            return false;
        }
        if (this.showVideoFrame != other.showVideoFrame) {
            return false;
        }
        if (this.enableVideoFrame != other.enableVideoFrame) {
            return false;
        }
        if (this.enableLoginPassword != other.enableLoginPassword) {
            return false;
        }
        if (this.showChatFrame != other.showChatFrame) {
            return false;
        }
        if (this.enableChatFrame != other.enableChatFrame) {
            return false;
        }
        if (this.showUserList != other.showUserList) {
            return false;
        }
        if (this.enableUsersList != other.enableUsersList) {
            return false;
        }
        if (this.usersListRefreshRateMs != other.usersListRefreshRateMs) {
            return false;
        }
        if (this.enterApparatusChatRoom != other.enterApparatusChatRoom) {
            return false;
        }
        if ((this.aboutPageLocationBundleKey == null) ? (other.aboutPageLocationBundleKey != null) : !this.aboutPageLocationBundleKey.equals(other.aboutPageLocationBundleKey)) {
            return false;
        }
        if ((this.splashIconLocationBundleKey == null) ? (other.splashIconLocationBundleKey != null) : !this.splashIconLocationBundleKey.equals(other.splashIconLocationBundleKey)) {
            return false;
        }
        if ((this.iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !this.iconLocationBundleKey.equals(other.iconLocationBundleKey)) {
            return false;
        }
        if ((this.desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null) : !this.desktopLocationBundleKey.equals(other.desktopLocationBundleKey)) {
            return false;
        }
        if ((this.frameTitleBundleKey == null) ? (other.frameTitleBundleKey != null) : !this.frameTitleBundleKey.equals(other.frameTitleBundleKey)) {
            return false;
        }
        if ((this.iconSponsorLocationBundleKey == null) ? (other.iconSponsorLocationBundleKey != null) : !this.iconSponsorLocationBundleKey.equals(other.iconSponsorLocationBundleKey)) {
            return false;
        }
        if ((this.helpPageLocationBundleKey == null) ? (other.helpPageLocationBundleKey != null) : !this.helpPageLocationBundleKey.equals(other.helpPageLocationBundleKey)) {
            return false;
        }
        //Delete
//        if (this.mediaConfig != other.mediaConfig && (this.mediaConfig == null || !this.mediaConfig.equals(other.mediaConfig))) {
//            return false;
//        }
        if (this.labList != other.labList && (this.labList == null || !this.labList.equals(other.labList))) {
            return false;
        }
        if (this.webResourceList != other.webResourceList && (this.webResourceList == null || !this.webResourceList.equals(other.webResourceList))) {
            return false;
        }
        if (this.localizationBundleList != other.localizationBundleList && (this.localizationBundleList == null || !this.localizationBundleList.equals(other.localizationBundleList))) {
            return false;
        }
        if (this.appPreferredWidth != other.appPreferredWidth) {
            return false;
        }
        if (this.appPreferredHeight != other.appPreferredHeight) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.autoConnectLab ? 1 : 0);
        hash = 59 * hash + (this.showVideoFrame ? 1 : 0);
        hash = 59 * hash + (this.enableVideoFrame ? 1 : 0);
        hash = 59 * hash + (this.enableLoginPassword ? 1 : 0);
        hash = 59 * hash + (this.showChatFrame ? 1 : 0);
        hash = 59 * hash + (this.enableChatFrame ? 1 : 0);
        hash = 59 * hash + (this.showUserList ? 1 : 0);
        hash = 59 * hash + (this.enableUsersList ? 1 : 0);
        hash = 59 * hash + (int) (this.usersListRefreshRateMs ^ (this.usersListRefreshRateMs >>> 32));
        hash = 59 * hash + (this.enterApparatusChatRoom ? 1 : 0);
        hash = 59 * hash + (this.aboutPageLocationBundleKey != null ? this.aboutPageLocationBundleKey.hashCode() : 0);
        hash = 59 * hash + (this.splashIconLocationBundleKey != null ? this.splashIconLocationBundleKey.hashCode() : 0);
        hash = 59 * hash + (this.iconLocationBundleKey != null ? this.iconLocationBundleKey.hashCode() : 0);
        hash = 59 * hash + (this.desktopLocationBundleKey != null ? this.desktopLocationBundleKey.hashCode() : 0);
        hash = 59 * hash + (this.frameTitleBundleKey != null ? this.frameTitleBundleKey.hashCode() : 0);
        hash = 59 * hash + (this.iconSponsorLocationBundleKey != null ? this.iconSponsorLocationBundleKey.hashCode() : 0);
        hash = 59 * hash + (this.helpPageLocationBundleKey != null ? this.helpPageLocationBundleKey.hashCode() : 0);
//Delete        hash = 83 * hash + (this.mediaConfig != null ? this.mediaConfig.hashCode() : 0);
        hash = 59 * hash + (this.labList != null ? this.labList.hashCode() : 0);
        hash = 59 * hash + (this.webResourceList != null ? this.webResourceList.hashCode() : 0);
        hash = 59 * hash + (this.localizationBundleList != null ? this.localizationBundleList.hashCode() : 0);
        hash = 59 * hash + this.appPreferredWidth;
        hash = 59 * hash + this.appPreferredHeight;
        return hash;
    }
	
}
