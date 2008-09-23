/*
 * HardwareServerMain.java
 *
 * Created on 26 de Junho de 2002, 16:44
 */

package pt.utl.ist.elab.driver.decay;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.driver.*;
import com.linkare.rec.impl.logging.*;
import java.util.logging.*;
import pt.utl.ist.elab.driver.serial.stamp.radioactividade.RadioactividadeStampDriver;

/**
 *
 * @author  jp
 */
public class DecayHardwareServerMain
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
