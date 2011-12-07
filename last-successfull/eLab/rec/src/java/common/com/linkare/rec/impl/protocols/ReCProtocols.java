/*
 * ReCProtocols.java
 *
 * Created on 22 de Maio de 2003, 16:56
 */

package com.linkare.rec.impl.protocols;

import java.net.MalformedURLException;
import java.net.URL;

import com.linkare.rec.impl.protocols.recresource.Handler;

/**
 * 
 * @author José Pedro Pereira - Linkare TI
 */
public class ReCProtocols {

	public static URL getURL(final String spec) throws MalformedURLException {
		if (spec == null || spec.length() == 0) {
			throw new MalformedURLException("spec is null or with length 0 - returning Exception from ReCProtocols...");
		}

		if (spec.startsWith(Handler.RECRESOURCE_PROTOCOL_IDENTIFIER)) {
			final String protocol = "recresource";
			final int protocolLength = Handler.RECRESOURCE_PROTOCOL_IDENTIFIER.length();
			String file = "";
			String host = "localhost";
			if (spec.indexOf("/", protocolLength) != -1) {
				host = spec.substring(protocolLength, spec.indexOf("/", protocolLength));

				file += spec.substring(spec.indexOf("/", protocolLength));
			} else {
				host = spec.substring(protocolLength);
			}

			int port = -1;

			if (host.indexOf(":") != -1) {
				port = Integer.parseInt(host.substring(host.indexOf(":")));
				host = host.substring(0, host.indexOf(":") - 1);
			}

			final URL url = new URL(protocol, host, port, file,
					new com.linkare.rec.impl.protocols.recresource.Handler());
			return url;
		}

		return new URL(spec);
	}

}
