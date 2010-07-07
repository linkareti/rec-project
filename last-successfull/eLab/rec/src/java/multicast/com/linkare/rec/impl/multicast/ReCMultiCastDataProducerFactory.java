/*
 * ProxyDataProducerManager.java
 *
 * Created on 5 de Novembro de 2002, 18:05
 */

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

	/** Creates a new instance of ProxyDataProducerManager */
	private ReCMultiCastDataProducerFactory() {
	}

	public static final ReCMultiCastDataProducer createReCMultiCastDataProducer(IResource resource,
			ReCMultiCastDataProducerListener listener, String baseDir, int maximum_receivers) {
		String fileName = (new Date()).toString().replaceAll(":", "_").replaceAll(" ", "_");
		fileName = baseDir + File.separator + fileName;
		ReCMultiCastDataProducer dataProducer = new ReCMultiCastDataProducer(resource, maximum_receivers, fileName);
		dataProducer.setReCMultiCastDataProducerListener(listener);
		Deactivator deactivator = new Deactivator(dataProducer);
		return dataProducer;
	}

}
