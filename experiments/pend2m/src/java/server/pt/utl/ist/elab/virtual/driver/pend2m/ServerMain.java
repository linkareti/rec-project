/*
 * ServerMain.java
 *
 * Created on 27 de Fevereiro de 2005, 20:07
 */

package pt.utl.ist.elab.virtual.driver.pend2m;

import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;

/**
 *
 * @author  Antonio J. R. Figueiredo
 *          Last Review : 6/04/2005
 */
public class ServerMain {     

    private static String Pend2M_HARDWARE_LOGGER="Pend2M.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(Pend2M_HARDWARE_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(Pend2M_HARDWARE_LOGGER));
	}
    }
	

    /**
    * @param args the command line arguments
    */
    public static void main(String[] args)
    {
        try
	{
            ORBBean.getORBBean(args);
			
            BaseHardware baseHardware=new BaseHardware(new Pend2MDriver());
									
            Thread.currentThread().join();
	
        }
        catch(Exception e)
	{
            ORBBean.getORBBean(args).killORB();
            LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(Pend2M_HARDWARE_LOGGER));
	}	
    }
}
