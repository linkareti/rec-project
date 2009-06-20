/*
 * QuantumServerMain.java
 *
 * Created on 10 de Abril de 2003, 20:07
 */

package pt.utl.ist.elab.virtual.driver.quantum;

import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;

/**
 *
 * @author  nomead
 */
public class QuantumServerMain {     

    private static String Quantum_HARDWARE_LOGGER="Quantum.Logger";
    static
    {
    	Logger l=LogManager.getLogManager().getLogger(Quantum_HARDWARE_LOGGER);
    	if(l==null)
    	{
            LogManager.getLogManager().addLogger(Logger.getLogger(Quantum_HARDWARE_LOGGER));
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
			
            BaseHardware baseHardware=new BaseHardware(new QuantumDriver());
									
            Thread.currentThread().join();
	
        }
        catch(Exception e)
	{
            ORBBean.getORBBean(args).killORB();
            LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(Quantum_HARDWARE_LOGGER));
	}	
    }
}
