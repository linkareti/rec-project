/*
 * testReCBaseUIConfig.java
 *
 * Created on July 19, 2004, 1:02 PM
 */

package test;

/**
 *
 * @author André Neto - LEFT - IST
 */
public class testReCBaseUIConfig {
    
    /** Creates a new instance of testReCBaseUIConfig */
    public testReCBaseUIConfig() 
    {
        String file = getClass().getResource("/com/linkare/rec/impl/baseUI/config/ReCBaseUIConfig.xml").getFile();
        com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig rec = com.linkare.rec.impl.baseUI.config.ReCBaseUIConfig.sharedInstance();
        try
        {
            rec.read(new java.io.FileInputStream(new java.io.File(file)));
            System.out.println(rec.getLab(0).getApparatus(0).getCustomizer());
        }
        catch(Exception e){e.printStackTrace();}

    }
    
    public static void main(String args[])
    {
        new testReCBaseUIConfig();
    }  
}
