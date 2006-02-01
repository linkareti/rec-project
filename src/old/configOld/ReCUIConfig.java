/*
 * ReCUIConfig.java
 *
 * Created on 11 de Junho de 2003, 16:00
 */

package old.configOld;

import java.net.URL;
import java.awt.Image;
import javax.swing.Icon;
import javax.jnlp.ServiceManager;
import javax.jnlp.BasicService;
import com.linkare.rec.impl.utils.Defaults;


/**
 *
 * @author  jp
 */
public final class ReCUIConfig
{
    public final static String UI_CONFIG_URL_PROP="ReCBaseUI.Config";
    private static ReCUIConfig thisConfig=null;
    
    public LabConfigCollection labConfiguration = new LabConfigCollection();
    public WebResourceCollection recWebResources = new WebResourceCollection("General","recresource:///com/linkare/rec/impl/baseUI/resources/ReCIconHand16.gif","General ReC WebResources");
    public ApparatusConfigCollection apparatusConfig= new ApparatusConfigCollection();
    
    
    /** Holds value of property autoConectLab. */
    private boolean autoConectLab;
    
    /** Holds value of property username. */
    private String username;
    
    /** Holds value of property password. */
    private String password;
    
    /** Holds value of property showVideoFrame. */
    private boolean showVideoFrame;
    
    /** Holds value of property enableVideoFrame. */
    private boolean enableVideoFrame;
    
    /** Holds value of property showChatFrame. */
    private boolean showChatFrame;
    
    /** Holds value of property enableChatFrame. */
    private boolean enableChatFrame;
    
    /** Holds value of property showUsersList. */
    private boolean showUsersList;
    
    /** Holds value of property enableUsersList. */
    private boolean enableUsersList;
    
    /** Holds value of property usersListRefreshRate. */
    private long usersListRefreshRate;
    
    /** Holds value of property enterApparatusChatRoom. */
    private boolean enterApparatusChatRoom;
    
    /** Holds value of property icon. */
    private Image icon=(new javax.swing.ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/ReCIconHand16.gif"))).getImage();
    
    /** Holds value of property frameTitle. */
    private String frameTitle;
    
    /** Holds value of property logoSponsor. */
    private Icon logoSponsor=new javax.swing.ImageIcon(getClass().getResource("/pt/utl/ist/elab/resources/ElabLogoGray.gif"));
    
    /** Holds value of property helpURL. */
    private URL helpURL;
    
    /** Holds value of property aboutURL. */
    private URL aboutURL;
    
    /** Creates a new instance of ReCUIConfig */
    private ReCUIConfig()
    {
    }
    
    public static ReCUIConfig getReCUIConfig()
    {
	if(thisConfig!=null) return thisConfig;
		
	thisConfig=new ReCUIConfig();
	
	String codeBase=null;
	
	try
	{
	    BasicService bs=(BasicService)ServiceManager.lookup("javax.jnlp.BasicService");
	    codeBase=bs.getCodeBase().toString();
	}catch(Exception e)
	{
	    e.printStackTrace();
	    return thisConfig;
	}
	
	String configLocation=Defaults.defaultIfEmpty(System.getProperty(UI_CONFIG_URL_PROP),codeBase+"/"+UI_CONFIG_URL_PROP+".xml");
    
	try
	{
	    ReCBaseUIConfigXMLReader.readReCUIConfig(thisConfig,configLocation);
	}catch(Exception e)
	{
	    e.printStackTrace();
	    return thisConfig;
	}
    
	
	return thisConfig;
    }
    
    /** Getter for property autoConectLab.
     * @return Value of property autoConectLab.
     */
    public boolean isAutoConectLab()
    {
	return this.autoConectLab;
    }    
    
    /** Setter for property autoConectLab.
     * @param autoConectLab New value of property autoConectLab.
     */
    public void setAutoConectLab(boolean autoConectLab)
    {
	this.autoConectLab = autoConectLab;
    }    
    
    /** Getter for property username.
     * @return Value of property username.
     */
    public String getUsername()
    {
	return this.username;
    }    
    
    /** Setter for property username.
     * @param username New value of property username.
     */
    public void setUsername(String username)
    {
	this.username = username;
    }    
    
    /** Getter for property password.
     * @return Value of property password.
     */
    public String getPassword()
    {
	return this.password;
    }
    
    /** Setter for property password.
     * @param password New value of property password.
     */
    public void setPassword(String password)
    {
	this.password = password;
    }
    
    /** Getter for property showVideoFrame.
     * @return Value of property showVideoFrame.
     */
    public boolean isShowVideoFrame()
    {
	return this.showVideoFrame;
    }
    
    /** Setter for property showVideoFrame.
     * @param showVideoFrame New value of property showVideoFrame.
     */
    public void setShowVideoFrame(boolean showVideoFrame)
    {
	this.showVideoFrame = showVideoFrame;
    }
    
    /** Getter for property enableVideoFrame.
     * @return Value of property enableVideoFrame.
     */
    public boolean isEnableVideoFrame()
    {
	return this.enableVideoFrame;
    }
    
    /** Setter for property enableVideoFrame.
     * @param enableVideoFrame New value of property enableVideoFrame.
     */
    public void setEnableVideoFrame(boolean enableVideoFrame)
    {
	this.enableVideoFrame = enableVideoFrame;
    }
    
    /** Getter for property showChatFrame.
     * @return Value of property showChatFrame.
     */
    public boolean isShowChatFrame()
    {
	return this.showChatFrame;
    }
    
    /** Setter for property showChatFrame.
     * @param showChatFrame New value of property showChatFrame.
     */
    public void setShowChatFrame(boolean showChatFrame)
    {
	this.showChatFrame = showChatFrame;
    }
    
    /** Getter for property enableChatFrame.
     * @return Value of property enableChatFrame.
     */
    public boolean isEnableChatFrame()
    {
	return this.enableChatFrame;
    }
    
    /** Setter for property enableChatFrame.
     * @param enableChatFrame New value of property enableChatFrame.
     */
    public void setEnableChatFrame(boolean enableChatFrame)
    {
	this.enableChatFrame = enableChatFrame;
    }
    
    /** Getter for property showUsersList.
     * @return Value of property showUsersList.
     */
    public boolean isShowUsersList()
    {
	return this.showUsersList;
    }
    
    /** Setter for property showUsersList.
     * @param showUsersList New value of property showUsersList.
     */
    public void setShowUsersList(boolean showUsersList)
    {
	this.showUsersList = showUsersList;
    }
    
    /** Getter for property enableUsersList.
     * @return Value of property enableUsersList.
     */
    public boolean isEnableUsersList()
    {
	return this.enableUsersList;
    }
    
    /** Setter for property enableUsersList.
     * @param enableUsersList New value of property enableUsersList.
     */
    public void setEnableUsersList(boolean enableUsersList)
    {
	this.enableUsersList = enableUsersList;
    }
    
    /** Getter for property usersListRefreshRate.
     * @return Value of property usersListRefreshRate.
     */
    public long getUsersListRefreshRate()
    {
	return this.usersListRefreshRate;
    }
    
    /** Setter for property usersListRefreshRate.
     * @param usersListRefreshRate New value of property usersListRefreshRate.
     */
    public void setUsersListRefreshRate(long usersListRefreshRate)
    {
	this.usersListRefreshRate = usersListRefreshRate;
    }
    
    /** Getter for property enterApparatusChatRoom.
     * @return Value of property enterApparatusChatRoom.
     */
    public boolean isEnterApparatusChatRoom()
    {
	return this.enterApparatusChatRoom;
    }
    
    /** Setter for property enterApparatusChatRoom.
     * @param enterApparatusChatRoom New value of property enterApparatusChatRoom.
     */
    public void setEnterApparatusChatRoom(boolean enterApparatusChatRoom)
    {
	this.enterApparatusChatRoom = enterApparatusChatRoom;
    }
    
    /** Getter for property icon.
     * @return Value of property icon.
     */
    public Image getIcon()
    {
	return this.icon;
    }
    
    /** Setter for property icon.
     * @param icon New value of property icon.
     */
    public void setIcon(Image icon)
    {
	if(icon!=null)
	    this.icon = icon;
    }
    
    /** Getter for property frameTitle.
     * @return Value of property frameTitle.
     */
    public String getFrameTitle()
    {
	return this.frameTitle;
    }
    
    /** Setter for property frameTitle.
     * @param frameTitle New value of property frameTitle.
     */
    public void setFrameTitle(String frameTitle)
    {
	this.frameTitle = frameTitle;
    }
    
    /** Getter for property logoSponsor.
     * @return Value of property logoSponsor.
     */
    public Icon getLogoSponsor()
    {
	return this.logoSponsor;
    }
    
    /** Setter for property logoSponsor.
     * @param logoSponsor New value of property logoSponsor.
     */
    public void setLogoSponsor(Icon logoSponsor)
    {
	this.logoSponsor = logoSponsor;
    }
    
    /** Getter for property helpURL.
     * @return Value of property helpURL.
     */
    public URL getHelpURL()
    {
	return this.helpURL;
    }
    
    /** Setter for property helpURL.
     * @param helpURL New value of property helpURL.
     */
    public void setHelpURL(URL helpURL)
    {
	this.helpURL = helpURL;
    }
    
    /** Getter for property aboutURL.
     * @return Value of property aboutURL.
     */
    public URL getAboutURL()
    {
	return this.aboutURL;
    }
    
    /** Setter for property aboutURL.
     * @param aboutURL New value of property aboutURL.
     */
    public void setAboutURL(URL aboutURL)
    {
	this.aboutURL = aboutURL;
    }
    
}
