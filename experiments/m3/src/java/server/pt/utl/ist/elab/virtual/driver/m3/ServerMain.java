/*
 * ServerMain.java
 *
 * Created on 17 de Fevereiro de 2005, 11:18
 */

package pt.utl.ist.elab.virtual.driver.m3;

import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;

/**
 *
 * @author n0dP2
 */
public class ServerMain {
    
    private static String M3_HARDWARE_LOGGER="M3.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(M3_HARDWARE_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(M3_HARDWARE_LOGGER));
	}
    }
    
    
    /** Creates a new instance of ServerMain */
    public ServerMain() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try
	{
            ORBBean.getORBBean(args);
			
            BaseHardware baseHardware=new BaseHardware(new M3Driver());
									
            Thread.currentThread().join();
	
        }
        catch(Exception e)
	{
            ORBBean.getORBBean(args).killORB();
            LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(M3_HARDWARE_LOGGER));
	}	
        // TODO code application logic here
    }
}
