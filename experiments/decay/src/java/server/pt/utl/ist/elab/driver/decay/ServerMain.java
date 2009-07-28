/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.decay;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import pt.utl.ist.elab.driver.radioactividade.RadioactividadeStampDriver;

import com.linkare.rec.impl.driver.BaseHardware;
import com.linkare.rec.impl.logging.LoggerUtil;
import com.linkare.rec.impl.utils.ORBBean;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ServerMain
{

	private static String RADIOACTIVIDADE_HARDWARE_LOGGER="RadioactividadeHardware.Logger";
	
	static
	{
		Logger l=LogManager.getLogManager().getLogger(RADIOACTIVIDADE_HARDWARE_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(RADIOACTIVIDADE_HARDWARE_LOGGER));
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
			
			BaseHardware baseHardware=new BaseHardware(new RadioactividadeStampDriver());
									
			
			Thread.currentThread().join();
			
		}catch(Exception e)
		{
		    ORBBean.getORBBean(args).killORB();
		    LoggerUtil.logThrowable("Error on Main...",e,Logger.getLogger(RADIOACTIVIDADE_HARDWARE_LOGGER));
		}
	}

}
