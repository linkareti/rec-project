package com.linkare.rec.am.config;

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

	// Delete private MediaConfig mediaConfig = null;

	private List<Lab> labList = new ArrayList<Lab>();

	private List<WebResource> webResourceList = new ArrayList<WebResource>();

	private List<LocalizationBundle> localizationBundleList = new ArrayList<LocalizationBundle>();

	private int appPreferredWidth = 848;

	private int appPreferredHeight = 478;

	/**
	 * Creates a new <code>ReCConfig</code>. Default Constructor.
	 */
	public ReCFaceConfig() {
		super();
	}

	/**
	 * Marshalls the current configuration to the OutputStream.
	 * 
	 * @param os OutputStream
	 * @throws JAXBException If a JAXB error occours.
	 */
	public void marshall(final OutputStream os) throws JAXBException {
		final JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);

		final Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(this, os);
	}

	/**
	 * Unmarshalls a new <code>ReCConfig</code> from the input stream.
	 * 
	 * @param is The source input stream
	 * @throws JAXBException If a JAXB error occours.
	 */
	public static ReCFaceConfig unmarshall(final InputStream is) throws JAXBException {
		final JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);
		final Unmarshaller un = jc.createUnmarshaller();
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

	// Bruno tem de estar como XmlElement ou assim????
	/**
	 * @return the mediaConfig
	 */
	// Delete
	// @XmlElement
	// public MediaConfig getMediaConfig() {
	// return mediaConfig;
	// }
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
	 * @param appPreferredWidth the appPreferredWidth to set
	 */
	public void setAppPreferredWidth(final int appPreferredWidth) {
		this.appPreferredWidth = appPreferredWidth;
	}

	/**
	 * @param appPreferredHeight the appPreferredHeight to set
	 */
	public void setAppPreferredHeight(final int appPreferredHeight) {
		this.appPreferredHeight = appPreferredHeight;
	}

	/**
	 * @param autoConnectLab the autoConnectLab to set
	 */
	public void setAutoConnectLab(final boolean autoConnectLab) {
		changeSupport.firePropertyChange("autoConnectLab", this.autoConnectLab, this.autoConnectLab = autoConnectLab);
	}

	/**
	 * @param showVideoFrame the showVideoFrame to set
	 */
	public void setShowVideoFrame(final boolean showVideoFrame) {
		changeSupport.firePropertyChange("showVideoFrame", this.showVideoFrame, this.showVideoFrame = showVideoFrame);
	}

	/**
	 * @param enableVideoFrame the enableVideoFrame to set
	 */
	public void setEnableVideoFrame(final boolean enableVideoFrame) {
		changeSupport.firePropertyChange("enableVideoFrame", this.enableVideoFrame,
				this.enableVideoFrame = enableVideoFrame);
	}

	/**
	 * @param enableLoginPassword the enableLoginPassword to set
	 */
	public void setEnableLoginPassword(final boolean enableLoginPassword) {
		changeSupport.firePropertyChange("enableLoginPassword", this.enableLoginPassword,
				this.enableLoginPassword = enableLoginPassword);
	}

	/**
	 * @param showChatFrame the showChatFrame to set
	 */
	public void setShowChatFrame(final boolean showChatFrame) {
		changeSupport.firePropertyChange("showChatFrame", this.showChatFrame, this.showChatFrame = showChatFrame);
	}

	/**
	 * @param enableChatFrame the enableChatFrame to set
	 */
	public void setEnableChatFrame(final boolean enableChatFrame) {
		changeSupport.firePropertyChange("enableChatFrame", this.enableChatFrame,
				this.enableChatFrame = enableChatFrame);
	}

	/**
	 * @param showUserList the showUserList to set
	 */
	public void setShowUserList(final boolean showUserList) {
		changeSupport.firePropertyChange("showUserList", this.showUserList, this.showUserList = showUserList);
	}

	/**
	 * @param enableUsersList the enableUsersList to set
	 */
	public void setEnableUsersList(final boolean enableUsersList) {
		changeSupport.firePropertyChange("enableUsersList", this.enableUsersList,
				this.enableUsersList = enableUsersList);
	}

	/**
	 * @param usersListRefreshRateMs the usersListRefreshRateMs to set
	 */
	public void setUsersListRefreshRateMs(final long usersListRefreshRateMs) {
		changeSupport.firePropertyChange("usersListRefreshRateMs", this.usersListRefreshRateMs,
				this.usersListRefreshRateMs = usersListRefreshRateMs);
	}

	/**
	 * @param enterApparatusChatRoom the enterApparatusChatRoom to set
	 */
	public void setEnterApparatusChatRoom(final boolean enterApparatusChatRoom) {
		changeSupport.firePropertyChange("enterApparatusChatRoom", this.enterApparatusChatRoom,
				this.enterApparatusChatRoom = enterApparatusChatRoom);
	}

	/**
	 * @param aboutPageLocationBundleKey the aboutPageLocationBundleKey to set
	 */
	public void setAboutPageLocationBundleKey(final String aboutPageLocationBundleKey) {
		changeSupport.firePropertyChange("aboutPageLocationBundleKey", this.aboutPageLocationBundleKey,
				this.aboutPageLocationBundleKey = aboutPageLocationBundleKey);
	}

	/**
	 * @param splashIconLocationBundleKey the splashIconLocationBundleKey to set
	 */
	public void setSplashIconLocationBundleKey(final String splashIconLocationBundleKey) {
		changeSupport.firePropertyChange("splashIconLocationBundleKey", this.splashIconLocationBundleKey,
				this.splashIconLocationBundleKey = splashIconLocationBundleKey);
	}

	/**
	 * @param iconLocationBundleKey the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		changeSupport.firePropertyChange("iconLocationBundleKey", this.iconLocationBundleKey,
				this.iconLocationBundleKey = iconLocationBundleKey);
	}

	/**
	 * @param desktopLocationBundleKey the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(final String desktopLocationBundleKey) {
		changeSupport.firePropertyChange("desktopLocationBundleKey", this.desktopLocationBundleKey,
				this.desktopLocationBundleKey = desktopLocationBundleKey);
	}

	/**
	 * @param frameTitleBundleKey the frameTitleBundleKey to set
	 */
	public void setFrameTitleBundleKey(final String frameTitleBundleKey) {
		changeSupport.firePropertyChange("frameTitleBundleKey", this.frameTitleBundleKey,
				this.frameTitleBundleKey = frameTitleBundleKey);
	}

	/**
	 * @param iconSponsorLocationBundleKey the iconSponsorLocationBundleKey to
	 *            set
	 */
	public void setIconSponsorLocationBundleKey(final String iconSponsorLocationBundleKey) {
		changeSupport.firePropertyChange("iconSponsorLocationBundleKey", this.iconSponsorLocationBundleKey,
				this.iconSponsorLocationBundleKey = iconSponsorLocationBundleKey);
	}

	/**
	 * @param helpPageLocationBundleKey the helpPageLocationBundleKey to set
	 */
	public void setHelpPageLocationBundleKey(final String helpPageLocationBundleKey) {
		changeSupport.firePropertyChange("helpPageLocationBundleKey", this.helpPageLocationBundleKey,
				this.helpPageLocationBundleKey = helpPageLocationBundleKey);
	}

	/**
	 * @param mediaConfig the mediaConfig to set
	 */
	// Delete
	// public void setMediaConfig(MediaConfig mediaConfig) {
	// this.mediaConfig = mediaConfig;
	// }
	/**
	 * @param lab the lab to set
	 */
	public void setLab(final List<Lab> lab) {
		labList = lab;
	}

	/**
	 * @param webResource the webResource to set
	 */
	public void setWebResource(final List<WebResource> webResource) {
		webResourceList = webResource;
	}

	/**
	 * @param localizationBundle the localizationBundle to set
	 */
	public void setLocalizationBundle(final List<LocalizationBundle> localizationBundle) {
		localizationBundleList = localizationBundle;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ReCFaceConfig other = (ReCFaceConfig) obj;
		if (autoConnectLab != other.autoConnectLab) {
			return false;
		}
		if (showVideoFrame != other.showVideoFrame) {
			return false;
		}
		if (enableVideoFrame != other.enableVideoFrame) {
			return false;
		}
		if (enableLoginPassword != other.enableLoginPassword) {
			return false;
		}
		if (showChatFrame != other.showChatFrame) {
			return false;
		}
		if (enableChatFrame != other.enableChatFrame) {
			return false;
		}
		if (showUserList != other.showUserList) {
			return false;
		}
		if (enableUsersList != other.enableUsersList) {
			return false;
		}
		if (usersListRefreshRateMs != other.usersListRefreshRateMs) {
			return false;
		}
		if (enterApparatusChatRoom != other.enterApparatusChatRoom) {
			return false;
		}
		if ((aboutPageLocationBundleKey == null) ? (other.aboutPageLocationBundleKey != null)
				: !aboutPageLocationBundleKey.equals(other.aboutPageLocationBundleKey)) {
			return false;
		}
		if ((splashIconLocationBundleKey == null) ? (other.splashIconLocationBundleKey != null)
				: !splashIconLocationBundleKey.equals(other.splashIconLocationBundleKey)) {
			return false;
		}
		if ((iconLocationBundleKey == null) ? (other.iconLocationBundleKey != null) : !iconLocationBundleKey
				.equals(other.iconLocationBundleKey)) {
			return false;
		}
		if ((desktopLocationBundleKey == null) ? (other.desktopLocationBundleKey != null) : !desktopLocationBundleKey
				.equals(other.desktopLocationBundleKey)) {
			return false;
		}
		if ((frameTitleBundleKey == null) ? (other.frameTitleBundleKey != null) : !frameTitleBundleKey
				.equals(other.frameTitleBundleKey)) {
			return false;
		}
		if ((iconSponsorLocationBundleKey == null) ? (other.iconSponsorLocationBundleKey != null)
				: !iconSponsorLocationBundleKey.equals(other.iconSponsorLocationBundleKey)) {
			return false;
		}
		if ((helpPageLocationBundleKey == null) ? (other.helpPageLocationBundleKey != null)
				: !helpPageLocationBundleKey.equals(other.helpPageLocationBundleKey)) {
			return false;
		}
		// Delete
		// if (this.mediaConfig != other.mediaConfig && (this.mediaConfig ==
		// null || !this.mediaConfig.equals(other.mediaConfig))) {
		// return false;
		// }
		if (labList != other.labList && (labList == null || !labList.equals(other.labList))) {
			return false;
		}
		if (webResourceList != other.webResourceList
				&& (webResourceList == null || !webResourceList.equals(other.webResourceList))) {
			return false;
		}
		if (localizationBundleList != other.localizationBundleList
				&& (localizationBundleList == null || !localizationBundleList.equals(other.localizationBundleList))) {
			return false;
		}
		if (appPreferredWidth != other.appPreferredWidth) {
			return false;
		}
		if (appPreferredHeight != other.appPreferredHeight) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + (autoConnectLab ? 1 : 0);
		hash = 59 * hash + (showVideoFrame ? 1 : 0);
		hash = 59 * hash + (enableVideoFrame ? 1 : 0);
		hash = 59 * hash + (enableLoginPassword ? 1 : 0);
		hash = 59 * hash + (showChatFrame ? 1 : 0);
		hash = 59 * hash + (enableChatFrame ? 1 : 0);
		hash = 59 * hash + (showUserList ? 1 : 0);
		hash = 59 * hash + (enableUsersList ? 1 : 0);
		hash = 59 * hash + (int) (usersListRefreshRateMs ^ (usersListRefreshRateMs >>> 32));
		hash = 59 * hash + (enterApparatusChatRoom ? 1 : 0);
		hash = 59 * hash + (aboutPageLocationBundleKey != null ? aboutPageLocationBundleKey.hashCode() : 0);
		hash = 59 * hash + (splashIconLocationBundleKey != null ? splashIconLocationBundleKey.hashCode() : 0);
		hash = 59 * hash + (iconLocationBundleKey != null ? iconLocationBundleKey.hashCode() : 0);
		hash = 59 * hash + (desktopLocationBundleKey != null ? desktopLocationBundleKey.hashCode() : 0);
		hash = 59 * hash + (frameTitleBundleKey != null ? frameTitleBundleKey.hashCode() : 0);
		hash = 59 * hash + (iconSponsorLocationBundleKey != null ? iconSponsorLocationBundleKey.hashCode() : 0);
		hash = 59 * hash + (helpPageLocationBundleKey != null ? helpPageLocationBundleKey.hashCode() : 0);
		// Delete hash = 83 * hash + (this.mediaConfig != null ?
		// this.mediaConfig.hashCode() : 0);
		hash = 59 * hash + (labList != null ? labList.hashCode() : 0);
		hash = 59 * hash + (webResourceList != null ? webResourceList.hashCode() : 0);
		hash = 59 * hash + (localizationBundleList != null ? localizationBundleList.hashCode() : 0);
		hash = 59 * hash + appPreferredWidth;
		hash = 59 * hash + appPreferredHeight;
		return hash;
	}

}
