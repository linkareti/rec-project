/*
 * Handler.java
 *
 * Created on 22 de Maio de 2003, 16:45
 */

package com.linkare.rec.impl.protocols.recresource;

import java.net.URL;
import java.net.URLConnection;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class Handler extends java.net.URLStreamHandler {

	/**
	 * 
	 */
	public static final String RECRESOURCE_PROTOCOL_IDENTIFIER = "recresource://";

	/**
	 * 
	 */
	public static final String RECRESOURCE_PROTOCOL_IDENTIFIER_ABSOLUTE_URL = Handler.RECRESOURCE_PROTOCOL_IDENTIFIER
			+ "/";

	@Override
	public URLConnection openConnection(final URL url) {
		if (!url.toExternalForm().startsWith(Handler.RECRESOURCE_PROTOCOL_IDENTIFIER)) {
			return null;
		}

		final boolean rootClassLoader = url.toExternalForm().startsWith(
				Handler.RECRESOURCE_PROTOCOL_IDENTIFIER_ABSOLUTE_URL);

		try {
			URL resourceURL = null;

			if (rootClassLoader) {
				resourceURL = getClass().getClassLoader().getResource(
						url.toExternalForm().substring(Handler.RECRESOURCE_PROTOCOL_IDENTIFIER_ABSOLUTE_URL.length()));
			} else {
				resourceURL = getClass().getResource(
						url.toExternalForm().substring(Handler.RECRESOURCE_PROTOCOL_IDENTIFIER.length()));

				// Maybe the user forgot to define the three (3) slashes but
				// still wants an absolute URL... Try it!
				if (resourceURL == null) {
					resourceURL = getClass().getClassLoader().getResource(
							url.toExternalForm().substring(Handler.RECRESOURCE_PROTOCOL_IDENTIFIER.length()));
				}
			}

			if (resourceURL == null) {
				throw new NullPointerException("Unable to find object at URL " + toExternalForm(url));
			}
			return resourceURL.openConnection();

		} catch (final java.io.IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Converts a <code>URL</code> of a specific protocol to a
	 * <code>String</code>.
	 * 
	 * @param u the URL.
	 * @return a string representation of the <code>URL</code> argument.
	 */
	@Override
	protected String toExternalForm(final URL u) {
		return u.getProtocol() + "://" + u.getHost() + (u.getPort() != -1 ? ":" + u.getPort() : "") + u.getFile();
	}

}
