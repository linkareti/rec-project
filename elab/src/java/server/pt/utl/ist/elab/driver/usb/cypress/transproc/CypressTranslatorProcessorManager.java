/*
 * SerialPortTranslatorManager.java
 *
 * Created on 11 de Novembro de 2002, 15:35
 */

package pt.utl.ist.elab.driver.usb.cypress.transproc;

import java.util.Hashtable;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class CypressTranslatorProcessorManager
{
	private static String Cypress_TPMANAGER_LOGGER="CypressTranslatorProcessorManager.Logger";
	
	static
	{
		Logger l=LogManager.getLogManager().getLogger(Cypress_TPMANAGER_LOGGER);
		if(l==null)
		{
			LogManager.getLogManager().addLogger(Logger.getLogger(Cypress_TPMANAGER_LOGGER));
		}
	}
	
	
	private static Hashtable translators=new Hashtable(5);
	private static Hashtable processors=new Hashtable(5);
	/** Creates a new instance of SerialPortTranslatorManager */
	private CypressTranslatorProcessorManager()
	{
	}
	
	
	public static void registerTranslator(CypressTranslator translator)
	{
		if(!translators.containsKey(translator.getCommandIdentifier()))
			translators.put(translator.getCommandIdentifier(),translator);
	}

	public static void deregisterTranslator(CypressTranslator translator)
	{
		if(translators.containsKey(translator.getCommandIdentifier()))
			translators.remove(translator.getCommandIdentifier());
	}
	
	public static CypressTranslator getTranslator(CypressCommand command)
	{
		return (CypressTranslator)translators.get(command.getCommandIdentifier());
	}
	
	
	public static void registerProcessor(CypressProcessor processor)
	{
		if(!processors.containsKey(processor.getCommandIdentifier()))
			processors.put(processor.getCommandIdentifier(),processor);
	}

	public static void deregisterProcessor(CypressProcessor processor)
	{
		if(processors.containsKey(processor.getCommandIdentifier()))
			processors.remove(processor.getCommandIdentifier());
	}
	
	public static CypressProcessor getProcessor(CypressCommand command)
	{
		return (CypressProcessor)processors.get(command.getCommandIdentifier());
	}
	
	public static void initCypressProcessorTranslator(String className)
	{
		try
		{
			Class c=Class.forName(className);
			c.newInstance();
		}catch(Exception e)
		{
			LoggerUtil.logThrowable("Unable to load class:" + className,e,Logger.getLogger(Cypress_TPMANAGER_LOGGER));
		}
	}
	
	public static void initCypressProcessorsTranslators(String[] classNames)
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
				LoggerUtil.logThrowable("Unable to load class:" + className,e,Logger.getLogger(Cypress_TPMANAGER_LOGGER));
			}
		}
	}
	
}
