/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.serial.stamp.scuba;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;

/**
 *
 * @author  jp
 */
public class ScubaHardwareServerMain
{

	private static String SCUBA_HARDWARE_LOGGER="ScubaHardware.Logger";
	
	static
	{
		Logger l=LogManager.getLogManager().getLogger(SCUBA_HARDWARE_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(SCUBA_HARDWARE_LOGGER));
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
			
			BaseHardware baseHardware=new BaseHardware(new ScubaStampDriver());
									
			try
			{
			    Thread.currentThread().join();
			}catch(Exception ignored){}
			
			ORBBean.getORBBean(args).killORB();
		}catch(Exception e)
		{
			LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(SCUBA_HARDWARE_LOGGER));
		}
	}

}
