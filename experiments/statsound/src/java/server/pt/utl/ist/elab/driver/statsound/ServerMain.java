/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.statsound;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ServerMain
{

	private static String STATSOUND_HARDWARE_LOGGER="StatSoundHardware.Logger";
	
	static
	{
		Logger l=LogManager.getLogManager().getLogger(STATSOUND_HARDWARE_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(STATSOUND_HARDWARE_LOGGER));
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
			
			BaseHardware baseHardware=new BaseHardware(new StatSoundStampDriver());
									
			try
			{
			    Thread.currentThread().join();
			}catch(Exception ignored){}
			
			ORBBean.getORBBean(args).killORB();
		}catch(Exception e)
		{
			LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(STATSOUND_HARDWARE_LOGGER));
		}
	}

}
