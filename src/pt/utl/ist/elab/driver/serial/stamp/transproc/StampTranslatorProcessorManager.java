/*
 * SerialPortTranslatorManager.java
 *
 * Created on 11 de Novembro de 2002, 15:35
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

import java.util.*;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;

/**
 *
 * @author  jp
 */
public class StampTranslatorProcessorManager
{
	private static String STAMP_TPMANAGER_LOGGER="StampTranslatorProcessorManager.Logger";
	
	static
	{
		Logger l=LogManager.getLogManager().getLogger(STAMP_TPMANAGER_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(STAMP_TPMANAGER_LOGGER));
		}
	}
	
	
	private static Hashtable translators=new Hashtable(5);
	private static Hashtable processors=new Hashtable(5);
	/** Creates a new instance of SerialPortTranslatorManager */
	private StampTranslatorProcessorManager()
	{
	}
	
	
	public static void registerTranslator(StampTranslator translator)
	{
		if(!translators.containsKey(translator.getCommandIdentifier()))
			translators.put(translator.getCommandIdentifier(),translator);
	}

	public static void deregisterTranslator(StampTranslator translator)
	{
		if(translators.containsKey(translator.getCommandIdentifier()))
			translators.remove(translator.getCommandIdentifier());
	}
	
	public static StampTranslator getTranslator(StampCommand command)
	{
		return (StampTranslator)translators.get(command.getCommandIdentifier());
	}
	
	
	public static void registerProcessor(StampProcessor processor)
	{
		if(!processors.containsKey(processor.getCommandIdentifier()))
			processors.put(processor.getCommandIdentifier(),processor);
	}

	public static void deregisterProcessor(StampProcessor processor)
	{
		if(processors.containsKey(processor.getCommandIdentifier()))
			processors.remove(processor.getCommandIdentifier());
	}
	
	public static StampProcessor getProcessor(StampCommand command)
	{
		return (StampProcessor)processors.get(command.getCommandIdentifier());
	}
	
	public static void initStampProcessorTranslator(String className)
	{
		try
		{
			Class c=Class.forName(className);
			c.newInstance();
		}catch(Exception e)
		{
			LoggerUtil.logThrowable("Unable to load class:" + className,e,Logger.getLogger(STAMP_TPMANAGER_LOGGER));
		}
	}
	
	public static void initStampProcessorsTranslators(String[] classNames)
	{
		for(int i=0;i<classNames.length;i++)
		{
			String className=classNames[i];
			try
			{
				Class c=Class.forName(className);
				c.newInstance();
			}catch(Exception e)
			{
				LoggerUtil.logThrowable("Unable to load class:" + className,e,Logger.getLogger(STAMP_TPMANAGER_LOGGER));
			}
		}
	}
	
}
