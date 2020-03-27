package pt.utl.ist.elab.driver.serial.stamp.transproc;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.utils.ClassUtils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class StampTranslatorProcessorManager {

	private static final Logger LOGGER = Logger.getLogger(StampTranslatorProcessorManager.class.getName());

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
		return StampTranslatorProcessorManager.translators.get(command.getCommandIdentifier());
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
		return StampTranslatorProcessorManager.processors.get(command.getCommandIdentifier());
	}

	public static void initStampProcessorTranslator(final String className) {
		try {                    
                        final Class<?> c = ClassUtils.findClass(className, StampTranslatorProcessorManager.class.getClassLoader());
			c.getDeclaredConstructor().newInstance();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to load class:" + className, e);
		}
	}

	public static void initStampProcessorsTranslators(final String[] classNames) {
		for (final String className : classNames) {
			try {
                                final Class<?> c = ClassUtils.findClass(className, StampTranslatorProcessorManager.class.getClassLoader());
				c.getDeclaredConstructor().newInstance();
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to load class:" + className, e);
			}
		}
	}

}
