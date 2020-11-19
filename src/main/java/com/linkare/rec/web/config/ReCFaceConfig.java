package com.linkare.rec.web.config;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;
import javax.xml.transform.Source;

@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ReCFaceConfig extends AbstractConfigBean {

	private static final int DEFAULT_PREFERRED_HEIGHT = 478;

	private static final int DEFAULT_PREFERRED_WIDTH = 848;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(ReCFaceConfig.class
			.getName());

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

	private List<LocalizationBundle> localizationBundleList = new ArrayList<LocalizationBundle>();

	private int appPreferredWidth = DEFAULT_PREFERRED_WIDTH;

	private int appPreferredHeight = DEFAULT_PREFERRED_HEIGHT;

	/**
	 * Creates a new <code>ReCConfig</code>. Default Constructor.
	 */
	public ReCFaceConfig() {
		super();
	}

	/**
	 * Marshalls the current configuration to the OutputStream.
	 * 
	 * @param os
	 *            OutputStream
	 * @throws JAXBException
	 *             If a JAXB error occours.
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
	 * @param is
	 *            The source input stream
	 * @throws JAXBException
	 *             If a JAXB error occours.
	 */
	public static ReCFaceConfig unmarshall(final InputStream is)
			throws JAXBException {
		// Old version
		final JAXBContext jc = JAXBContext.newInstance(ReCFaceConfig.class);
		final Unmarshaller un = jc.createUnmarshaller();
		return (ReCFaceConfig) un.unmarshal(is);
		// Workaround of unmarshalling exception - Bug 5817
		//JAXBContext ctx = JAXBContext.newInstance(ReCFaceConfig.class);
	    //Unmarshaller unmarshaller = ctx.createUnmarshaller();
	    //SAXParserFactory sax = SAXParserFactory.newInstance();
	    //sax.setNamespaceAware(false); // This line is important!
	    //XMLReader reader = sax.newSAXParser().getXMLReader();
	    //Source source = new SAXSource(reader, is);
	    //return (ReCFaceConfig) unmarshaller.unmarshal(source);
	}

	// -------------------------------------------------------------------------
	// Getters

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
	public void setAppPreferredWidth(final int appPreferredWidth) {
		this.appPreferredWidth = appPreferredWidth;
	}

	/**
	 * @param appPreferredHeight
	 *            the appPreferredHeight to set
	 */
	public void setAppPreferredHeight(final int appPreferredHeight) {
		this.appPreferredHeight = appPreferredHeight;
	}

	/**
	 * @param showVideoFrame
	 *            the showVideoFrame to set
	 */
	public void setShowVideoFrame(final boolean showVideoFrame) {
		changeSupport.firePropertyChange("showVideoFrame", this.showVideoFrame,
				showVideoFrame);
		this.showVideoFrame = showVideoFrame;
	}

	/**
	 * @param enableVideoFrame
	 *            the enableVideoFrame to set
	 */
	public void setEnableVideoFrame(final boolean enableVideoFrame) {
		changeSupport.firePropertyChange("enableVideoFrame",
				this.enableVideoFrame, enableVideoFrame);
		this.enableVideoFrame = enableVideoFrame;
	}

	/**
	 * @param enableLoginPassword
	 *            the enableLoginPassword to set
	 */
	public void setEnableLoginPassword(final boolean enableLoginPassword) {
		changeSupport.firePropertyChange("enableLoginPassword",
				this.enableLoginPassword, enableLoginPassword);
		this.enableLoginPassword = enableLoginPassword;
	}

	/**
	 * @param showChatFrame
	 *            the showChatFrame to set
	 */
	public void setShowChatFrame(final boolean showChatFrame) {
		changeSupport.firePropertyChange("showChatFrame", this.showChatFrame,
				showChatFrame);
		this.showChatFrame = showChatFrame;
	}

	/**
	 * @param enableChatFrame
	 *            the enableChatFrame to set
	 */
	public void setEnableChatFrame(final boolean enableChatFrame) {
		changeSupport.firePropertyChange("enableChatFrame",
				this.enableChatFrame, enableChatFrame);
		this.enableChatFrame = enableChatFrame;
	}

	/**
	 * @param showUserList
	 *            the showUserList to set
	 */
	public void setShowUserList(final boolean showUserList) {
		changeSupport.firePropertyChange("showUserList", this.showUserList,
				showUserList);
		this.showUserList = showUserList;
	}

	/**
	 * @param enableUsersList
	 *            the enableUsersList to set
	 */
	public void setEnableUsersList(final boolean enableUsersList) {
		changeSupport.firePropertyChange("enableUsersList",
				this.enableUsersList, enableUsersList);
		this.enableUsersList = enableUsersList;
	}

	/**
	 * @param usersListRefreshRateMs
	 *            the usersListRefreshRateMs to set
	 */
	public void setUsersListRefreshRateMs(final long usersListRefreshRateMs) {
		changeSupport.firePropertyChange("usersListRefreshRateMs",
				this.usersListRefreshRateMs, usersListRefreshRateMs);
		this.usersListRefreshRateMs = usersListRefreshRateMs;
	}

	/**
	 * @param enterApparatusChatRoom
	 *            the enterApparatusChatRoom to set
	 */
	public void setEnterApparatusChatRoom(final boolean enterApparatusChatRoom) {
		changeSupport.firePropertyChange("enterApparatusChatRoom",
				this.enterApparatusChatRoom, enterApparatusChatRoom);
		this.enterApparatusChatRoom = enterApparatusChatRoom;
	}

	/**
	 * @param aboutPageLocationBundleKey
	 *            the aboutPageLocationBundleKey to set
	 */
	public void setAboutPageLocationBundleKey(
			final String aboutPageLocationBundleKey) {
		changeSupport.firePropertyChange("aboutPageLocationBundleKey",
				this.aboutPageLocationBundleKey, aboutPageLocationBundleKey);
		this.aboutPageLocationBundleKey = aboutPageLocationBundleKey;
	}

	/**
	 * @param splashIconLocationBundleKey
	 *            the splashIconLocationBundleKey to set
	 */
	public void setSplashIconLocationBundleKey(
			final String splashIconLocationBundleKey) {
		changeSupport.firePropertyChange("splashIconLocationBundleKey",
				this.splashIconLocationBundleKey, splashIconLocationBundleKey);
		this.splashIconLocationBundleKey = splashIconLocationBundleKey;
	}

	/**
	 * @param iconLocationBundleKey
	 *            the iconLocationBundleKey to set
	 */
	public void setIconLocationBundleKey(final String iconLocationBundleKey) {
		changeSupport.firePropertyChange("iconLocationBundleKey",
				this.iconLocationBundleKey, iconLocationBundleKey);
		this.iconLocationBundleKey = iconLocationBundleKey;
	}

	/**
	 * @param desktopLocationBundleKey
	 *            the desktopLocationBundleKey to set
	 */
	public void setDesktopLocationBundleKey(
			final String desktopLocationBundleKey) {
		changeSupport.firePropertyChange("desktopLocationBundleKey",
				this.desktopLocationBundleKey, desktopLocationBundleKey);
		this.desktopLocationBundleKey = desktopLocationBundleKey;
	}

	/**
	 * @param frameTitleBundleKey
	 *            the frameTitleBundleKey to set
	 */
	public void setFrameTitleBundleKey(final String frameTitleBundleKey) {
		changeSupport.firePropertyChange("frameTitleBundleKey",
				this.frameTitleBundleKey, frameTitleBundleKey);
		this.frameTitleBundleKey = frameTitleBundleKey;
	}

	/**
	 * @param iconSponsorLocationBundleKey
	 *            the iconSponsorLocationBundleKey to set
	 */
	public void setIconSponsorLocationBundleKey(
			final String iconSponsorLocationBundleKey) {
		changeSupport
				.firePropertyChange("iconSponsorLocationBundleKey",
						this.iconSponsorLocationBundleKey,
						iconSponsorLocationBundleKey);
		this.iconSponsorLocationBundleKey = iconSponsorLocationBundleKey;
	}

	/**
	 * @param helpPageLocationBundleKey
	 *            the helpPageLocationBundleKey to set
	 */
	public void setHelpPageLocationBundleKey(
			final String helpPageLocationBundleKey) {
		changeSupport.firePropertyChange("helpPageLocationBundleKey",
				this.helpPageLocationBundleKey, helpPageLocationBundleKey);
		this.helpPageLocationBundleKey = helpPageLocationBundleKey;
	}

	/**
	 * @param lab
	 *            the lab to set
	 */
	public void setLab(final List<Lab> lab) {
		labList = lab;
	}

	/**
	 * @param webResource
	 *            the webResource to set
	 */
	public void setWebResource(final List<WebResource> webResource) {
		webResourceList = webResource;
	}

	/**
	 * @param localizationBundle
	 *            the localizationBundle to set
	 */
	public void setLocalizationBundle(
			final List<LocalizationBundle> localizationBundle) {
		localizationBundleList = localizationBundle;
	}

	@Override
	public boolean equals(final Object obj) {
		boolean retVal = true;
		if (obj == null || getClass() != obj.getClass()) {
			retVal = false;
		} else {
			final ReCFaceConfig other = (ReCFaceConfig) obj;
			retVal = retVal
					&& nullSafeObjectEquals(showVideoFrame,
							other.showVideoFrame);
			retVal = retVal
					&& nullSafeObjectEquals(enableVideoFrame,
							other.enableVideoFrame);
			retVal = retVal
					&& nullSafeObjectEquals(enableLoginPassword,
							other.enableLoginPassword);
			retVal = retVal
					&& nullSafeObjectEquals(showChatFrame, other.showChatFrame);
			retVal = retVal
					&& nullSafeObjectEquals(enableChatFrame,
							other.enableChatFrame);
			retVal = retVal
					&& nullSafeObjectEquals(showUserList, other.showUserList);
			retVal = retVal
					&& nullSafeObjectEquals(enableUsersList,
							other.enableUsersList);
			retVal = retVal
					&& nullSafeObjectEquals(usersListRefreshRateMs,
							other.usersListRefreshRateMs);
			retVal = retVal
					&& nullSafeObjectEquals(enterApparatusChatRoom,
							other.enterApparatusChatRoom);
			retVal = retVal
					&& nullSafeObjectEquals(aboutPageLocationBundleKey,
							other.aboutPageLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(splashIconLocationBundleKey,
							other.splashIconLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(iconLocationBundleKey,
							other.iconLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(desktopLocationBundleKey,
							other.desktopLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(frameTitleBundleKey,
							other.frameTitleBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(iconSponsorLocationBundleKey,
							other.iconSponsorLocationBundleKey);
			retVal = retVal
					&& nullSafeObjectEquals(helpPageLocationBundleKey,
							other.helpPageLocationBundleKey);
			retVal = retVal && nullSafeObjectEquals(labList, other.labList);
			retVal = retVal
					&& nullSafeObjectEquals(webResourceList,
							other.webResourceList);
			retVal = retVal
					&& nullSafeObjectEquals(localizationBundleList,
							other.localizationBundleList);
			retVal = retVal
					&& nullSafeObjectEquals(appPreferredWidth,
							other.appPreferredWidth);
			retVal = retVal
					&& nullSafeObjectEquals(appPreferredHeight,
							other.appPreferredHeight);
		}
		return retVal;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 59 * hash + nullObjectSafeHash(showVideoFrame);
		hash = 59 * hash + nullObjectSafeHash(enableVideoFrame);
		hash = 59 * hash + nullObjectSafeHash(enableLoginPassword);
		hash = 59 * hash + nullObjectSafeHash(showChatFrame);
		hash = 59 * hash + nullObjectSafeHash(enableChatFrame);
		hash = 59 * hash + nullObjectSafeHash(showUserList);
		hash = 59 * hash + nullObjectSafeHash(enableUsersList);
		hash = 59 * hash + nullObjectSafeHash(usersListRefreshRateMs);
		hash = 59 * hash + nullObjectSafeHash(enterApparatusChatRoom);
		hash = 59 * hash + nullObjectSafeHash(aboutPageLocationBundleKey);
		hash = 59 * hash + nullObjectSafeHash(splashIconLocationBundleKey);
		hash = 59 * hash + nullObjectSafeHash(iconLocationBundleKey);
		hash = 59 * hash + nullObjectSafeHash(desktopLocationBundleKey);
		hash = 59 * hash + nullObjectSafeHash(frameTitleBundleKey);
		hash = 59 * hash + nullObjectSafeHash(iconSponsorLocationBundleKey);
		hash = 59 * hash + nullObjectSafeHash(helpPageLocationBundleKey);
		hash = 59 * hash + nullObjectSafeHash(labList != null);
		hash = 59 * hash + nullObjectSafeHash(webResourceList != null);
		hash = 59 * hash + nullObjectSafeHash(localizationBundleList);
		hash = 59 * hash + nullObjectSafeHash(appPreferredWidth);
		hash = 59 * hash + nullObjectSafeHash(appPreferredHeight);
		return hash;
	}

}
