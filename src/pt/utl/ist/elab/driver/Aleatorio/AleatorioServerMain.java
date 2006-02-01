package pt.utl.ist.elab.driver.Aleatorio;

/*
 * AleatorioServerMain.java
 *
 * Created on 4 de Junho de 2003, 16:47
 */

import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;


/**
 *
 * @author  PC
 */
public class AleatorioServerMain{
    
    private static String ALEATORIO_HARDWARE_LOGGER="Aleatorio.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(ALEATORIO_HARDWARE_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(ALEATORIO_HARDWARE_LOGGER));
	}
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try
	{
            ORBBean.getORBBean(args);
            BaseHardware baseHardware=new BaseHardware(new AleatorioDriver());
            
            try {Thread.currentThread().join();}
            catch(Exception ignored){}
            
            ORBBean.getORBBean(args).killORB();
        }
        catch(Exception e)
	{
            LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(ALEATORIO_HARDWARE_LOGGER));
	}	
        /*AleatorioDriver aleatorioDriver = new AleatorioDriver();
        aleatorioDriver.init((HardwareInfo)aleatorioDriver.getHardwareInfo());
         */
    }
    
}//AleatorioServerMain.class
