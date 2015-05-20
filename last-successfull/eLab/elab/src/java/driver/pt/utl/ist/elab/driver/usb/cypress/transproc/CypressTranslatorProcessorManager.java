package pt.utl.ist.elab.driver.usb.cypress.transproc;

import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.linkare.rec.utils.ClassUtils;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class CypressTranslatorProcessorManager {

	private static final Logger LOGGER = Logger.getLogger(CypressTranslatorProcessorManager.class.getName());

	private static Hashtable<String, CypressTranslator> translators = new Hashtable<String, CypressTranslator>(5);
	private static Hashtable<String, CypressProcessor> processors = new Hashtable<String, CypressProcessor>(5);

	/** Creates a new instance of SerialPortTranslatorManager */
	private CypressTranslatorProcessorManager() {
	}

	public static void registerTranslator(final CypressTranslator translator) {
		if (!CypressTranslatorProcessorManager.translators.containsKey(translator.getCommandIdentifier())) {
			CypressTranslatorProcessorManager.translators.put(translator.getCommandIdentifier(), translator);
		}
	}

	public static void deregisterTranslator(final CypressTranslator translator) {
		if (CypressTranslatorProcessorManager.translators.containsKey(translator.getCommandIdentifier())) {
			CypressTranslatorProcessorManager.translators.remove(translator.getCommandIdentifier());
		}
	}

	public static CypressTranslator getTranslator(final CypressCommand command) {
		return CypressTranslatorProcessorManager.translators.get(command.getCommandIdentifier());
	}

	public static void registerProcessor(final CypressProcessor processor) {
		if (!CypressTranslatorProcessorManager.processors.containsKey(processor.getCommandIdentifier())) {
			CypressTranslatorProcessorManager.processors.put(processor.getCommandIdentifier(), processor);
		}
	}

	public static void deregisterProcessor(final CypressProcessor processor) {
		if (CypressTranslatorProcessorManager.processors.containsKey(processor.getCommandIdentifier())) {
			CypressTranslatorProcessorManager.processors.remove(processor.getCommandIdentifier());
		}
	}

	public static CypressProcessor getProcessor(final CypressCommand command) {
		return CypressTranslatorProcessorManager.processors.get(command.getCommandIdentifier());
	}

	public static void initCypressProcessorTranslator(final String className) {
		try {
			final Class<?> c = ClassUtils.findClass(className, ClassLoader.getSystemClassLoader());
			c.newInstance();
		} catch (final Exception e) {
			LOGGER.log(Level.SEVERE, "Unable to load class:" + className, e);
		}
	}

	public static void initCypressProcessorsTranslators(final String[] classNames) {
		for (final String className : classNames) {
			try {
				final Class<?> c = ClassUtils.findClass(className, ClassLoader.getSystemClassLoader());
				c.newInstance();
			} catch (final Exception e) {
				LOGGER.log(Level.SEVERE, "Unable to load class:" + className, e);
			}
		}
	}

}
