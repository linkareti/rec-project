/*
 * ProxyDataProducerManager.java
 *
 * Created on 5 de Novembro de 2002, 18:05
 */

package com.linkare.rec.impl.multicast;

import java.io.File;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.utils.Deactivator;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public final class ReCMultiCastDataProducerFactory {

	public static String MC_DATA_PRODUCER_FACTORY_LOGGER = "ReCMultiCastDataProducerFactory.Logger";

	static {
		final Logger l = LogManager.getLogManager().getLogger(
				ReCMultiCastDataProducerFactory.MC_DATA_PRODUCER_FACTORY_LOGGER);
		if (l == null) {
			LogManager.getLogManager().addLogger(
					Logger.getLogger(ReCMultiCastDataProducerFactory.MC_DATA_PRODUCER_FACTORY_LOGGER));
		}
	}

	/** Creates a new instance of ProxyDataProducerManager */
	private ReCMultiCastDataProducerFactory() {
	}

	public static final ReCMultiCastDataProducer createReCMultiCastDataProducer(final IResource resource,
			final ReCMultiCastDataProducerListener listener, final String baseDir, final int maximum_receivers, final String user) {
		String fileName = (new Date()).toString().replaceAll(":", "_").replaceAll(" ", "_");
		fileName = baseDir + File.separator + fileName;
		final ReCMultiCastDataProducer dataProducer = new ReCMultiCastDataProducer(resource, maximum_receivers,
				fileName,user);
		dataProducer.setReCMultiCastDataProducerListener(listener);
		final Deactivator deactivator = new Deactivator(dataProducer,
				Logger.getLogger(ReCMultiCastDataProducerFactory.MC_DATA_PRODUCER_FACTORY_LOGGER));
		return dataProducer;
	}

}
