/*
 * TestResources.java
 *
 * Created on July 20, 2004, 1:26 PM
 */

package test;

import com.linkare.rec.impl.i18n.ReCResourceBundle;
import com.linkare.rec.impl.utils.Defaults;

/**
 *
 * @author  andre
 */
public class TestResources {
    
    /** Creates a new instance of TestResources */
    public TestResources() 
    {
	com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig baseUIConf = com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.sharedInstance();
	
	
	String noUsersList = Defaults.defaultIfEmpty(ReCResourceBundle.findString("ReC_Base_UI$no_users_list"), "No Users List Available");

	System.out.println(noUsersList);
	/*try
	{
	    com.linkare.rec.impl.i18n.ReCResourceBundle.findObject("none");
	}
	catch(java.io.IOException ioe)
	{
	    ioe.printStackTrace();
	}
	catch(ClassNotFoundException cnfe)
	{
	    cnfe.printStackTrace();
	}*/
	
	/*try
	{
	    //baseUIConf.read(new java.io.FileInputStream(getClass().getResource("/com/linkare/rec/impl/baseUI/config/ReCBaseUIConfig.xml").getFile()));
	    /*java.net.URL url = com.linkare.rec.impl.protocols.ReCProtocols.getURL("recresource:///com/linkare/rec/impl/baseUI/config/ReCBaseUIConfigExample.xml");	    
	    baseUIConf.read(url.openConnection().getInputStream());*/
	/*}
	catch(Exception e)
	{
	    e.printStackTrace();
	}
	
	System.out.println("->" + baseUIConf.getIconLocationBundleKey());
        /*try
        {
            java.net.URL url = getClass().getResource("/com/linkare/rec/impl/baseUI/resources/ReC_Base_UI.properties");
            System.out.println("->" + url.getFile());
            com.linkare.rec.impl.i18n.ReCPropertyResourceBundle rec = new com.linkare.rec.impl.i18n.ReCPropertyResourceBundle(new java.io.FileInputStream(url.getFile()));
            System.out.println(rec.getBundle("Default").getString("lbl_Cancel"));
        }
        catch(java.io.FileNotFoundException fnf)
        {
            fnf.printStackTrace();
        }
        catch(java.net.MalformedURLException me)
        {
            me.printStackTrace();
        }
        catch(java.io.IOException e)
        {            
            e.printStackTrace();
        }*/
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new TestResources();
    }
    
}
