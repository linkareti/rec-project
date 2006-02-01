/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.serial.stamp.planck;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;

/**
 *
 * @author  jp
 */
public class PlanckHardwareServerMain
{

	private static String PLANCK_HARDWARE_LOGGER="PlanckHardware.Logger";
	
	static
	{
		Logger l=LogManager.getLogManager().getLogger(PLANCK_HARDWARE_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(PLANCK_HARDWARE_LOGGER));
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
			
			BaseHardware baseHardware=new BaseHardware(new PlanckStampDriver());
									
			try
			{
			    Thread.currentThread().join();
			}catch(Exception ignored){}
			
			ORBBean.getORBBean(args).killORB();
		}catch(Exception e)
		{
			LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(PLANCK_HARDWARE_LOGGER));
		}
	}

}
