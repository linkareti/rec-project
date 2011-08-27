/*
 * SerialPortTranslatorManager.java
 *
 * Created on 11 de Novembro de 2002, 15:35
 */

package pt.utl.ist.elab.driver.serial.stamp.transproc;

import java.util.Hashtable;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.logging.LoggerUtil;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class StampTranslatorProcessorManager {
	private static String STAMP_TPMANAGER_LOGGER = "StampTranslatorProcessorManager.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(StampTranslatorProcessorManager.STAMP_TPMANAGER_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(
					Logger.getLogger(StampTranslatorProcessorManager.STAMP_TPMANAGER_LOGGER));
		}
	}

	private static Hashtable<String, StampTranslator> translators = new Hashtable<String, StampTranslator>(5);
	private static Hashtable<String, StampProcessor> processors = new Hashtable<String, StampProcessor>(5);

	/** Creates a new instance of SerialPortTranslatorManager */
	private StampTranslatorProcessorManager() {
	}

	public static void registerTranslator(final StampTranslator translator) {
		if (!StampTranslatorProcessorManager.translators.containsKey(translator.getCommandIdentifier())) {
			StampTranslatorProcessorManager.translators.put(translator.getCommandIdentifier(), translator);
		}
	}

	public static void deregisterTranslator(final StampTranslator translator) {
		if (StampTranslatorProcessorManager.translators.containsKey(translator.getCommandIdentifier())) {
			StampTranslatorProcessorManager.translators.remove(translator.getCommandIdentifier());
		}
	}

	public static StampTranslator getTranslator(final StampCommand command) {
		return (StampTranslator) StampTranslatorProcessorManager.translators.get(command.getCommandIdentifier());
	}

	public static void registerProcessor(final StampProcessor processor) {
		if (!StampTranslatorProcessorManager.processors.containsKey(processor.getCommandIdentifier())) {
			StampTranslatorProcessorManager.processors.put(processor.getCommandIdentifier(), processor);
		}
	}

	public static void deregisterProcessor(final StampProcessor processor) {
		if (StampTranslatorProcessorManager.processors.containsKey(processor.getCommandIdentifier())) {
			StampTranslatorProcessorManager.processors.remove(processor.getCommandIdentifier());
		}
	}

	public static StampProcessor getProcessor(final StampCommand command) {
		return (StampProcessor) StampTranslatorProcessorManager.processors.get(command.getCommandIdentifier());
	}

	public static void initStampProcessorTranslator(final String className) {
		try {
			final Class<?> c = Class.forName(className);
			c.newInstance();
		} catch (final Exception e) {
			LoggerUtil.logThrowable("Unable to load class:" + className, e,
					Logger.getLogger(StampTranslatorProcessorManager.STAMP_TPMANAGER_LOGGER));
		}
	}

	public static void initStampProcessorsTranslators(final String[] classNames) {
		for (final String className : classNames) {
			try {
				final Class<?> c = Class.forName(className);
				c.newInstance();
			} catch (final Exception e) {
				LoggerUtil.logThrowable("Unable to load class:" + className, e,
						Logger.getLogger(StampTranslatorProcessorManager.STAMP_TPMANAGER_LOGGER));
			}
		}
	}

}
