package old.configOld;
/*
 * ApparatusConfig.java
 *
 * Created on 22 de Maio de 2003, 16:17
 */
import java.util.*;
import com.linkare.rec.impl.baseUI.display.*;

/**
 *
 * @author  bruno
 */
public class ApparatusConfig
{
    
    /** Holds value of property id. */
    private String id;    

    
    /** Holds value of property videoURL. */
    private String videoURL;
    
    /** Holds value of property display. */
    private DisplayCollection displays=new DisplayCollection();
    
    /** Holds value of property webResources. */
    private WebResourceCollection webResources=new WebResourceCollection("Apparatus","recresource:///com/linkare/rec/impl/baseUI/resources/hcontrol16.gif","Apparatus WebResources");
    
    /** Creates new ApparatusConfig */
    public ApparatusConfig()
    {}
    
    /** Getter for property id.
     * @return Value of property id.
     */
    public String getId()
    {
	return this.id;
    }    
    
    /** Setter for property id.
     * @param id New value of property id.
     */
    public void setId(String id)
    {
	this.id = id;
    }
    

    /** Getter for property videoURL.
     * @return Value of property videoURL.
     */
    public String getVideoURL()
    {
	return this.videoURL;
    }
    
    /** Setter for property videoURL.
     * @param videoURL New value of property videoURL.
     */
    public void setVideoURL(String videoURL)
    {
	this.videoURL = videoURL;
    }
    
    /** Indexed getter for property display.
     * @param index Index of the property.
     * @return Value of the property at <CODE>index</CODE>.
     */
    public String[] getDisplays()
    {
	return displays.getDisplays();
    }
    
    public void addDisplay(String bean,int order)
    {
	Display d=new Display();
	d.setBean(bean);
	d.setOrder(order);
	displays.add(d);
    }
    
    public javax.swing.JMenu getWebResourcesMenu()
    {
	return webResources.getJMenu();
    }
    
    
    public void addWebResource(String displayString,String url,int order,String tooltip,String iconURL)
    {
	
	WebResource resource=new WebResource();
	resource.setDisplayString(displayString);
	resource.setURL(url);
	resource.setOrder(order);
	resource.setTooltip(tooltip);
	resource.setIconURL(iconURL);
	webResources.add(resource);
    }
    
    public void setWebResources(WebResourceCollection webResources)
    {
        this.webResources = webResources;
    }
    
    public WebResourceCollection getWebResources()
    {
        return this.webResources;
    }
    
    public void setDisplays(DisplayCollection displays)
    {
        this.displays = displays;
    }
}
