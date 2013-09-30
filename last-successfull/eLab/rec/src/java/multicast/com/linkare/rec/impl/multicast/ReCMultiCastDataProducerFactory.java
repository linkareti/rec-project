package com.linkare.rec.impl.multicast;

import java.io.File;
import java.util.Date;

import com.linkare.rec.impl.multicast.security.IResource;
import com.linkare.rec.impl.utils.Deactivator;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public final class ReCMultiCastDataProducerFactory {

	// private static final Logger LOGGER =
	// Logger.getLogger(ReCMultiCastDataProducerFactory.class.getName());

	/** Creates a new instance of ProxyDataProducerManager */
	private ReCMultiCastDataProducerFactory() {
	}

	public static final ReCMultiCastDataProducer createReCMultiCastDataProducer(final IResource resource,
			final ReCMultiCastDataProducerListener listener, final String baseDir, final int maximum_receivers,
			final String user) {
		String fileName = (new Date()).toString().replaceAll(":", "_").replaceAll(" ", "_");
		fileName = baseDir + File.separator + fileName;
		final ReCMultiCastDataProducer dataProducer = new ReCMultiCastDataProducer(resource, maximum_receivers,
				fileName, user);
		dataProducer.setReCMultiCastDataProducerListener(listener);
		new Deactivator(dataProducer);
		return dataProducer;
	}

}
