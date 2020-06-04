package com.linkare.irn.nascimento.web.fasp;

import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public class SAMLBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(SAMLBootstrap.class);

    private static boolean BOOTSTRAP_DONE = false;

    protected FAProperties properties;

    static {
	if (!BOOTSTRAP_DONE) {
	    try {
		DefaultBootstrap.bootstrap();
	    } catch (ConfigurationException e) {
		LOGGER.error("SAML boostrap error: " + e.getMessage(), e);
		throw new RuntimeException(e);
	    }
	}
    }

    public SAMLBootstrap(FAProperties properties) {
	super();
	this.properties = properties;
    }

}
