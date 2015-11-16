/* 
 * SoTimeoutDecoratorStrategy.java created on 30 Nov 2010
 *
 * Copyright 2009 Linkare TI. All rights reserved.
 * Linkare TI PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.linkare.rec.openorb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.openorb.orb.config.ORBLoader;
import org.openorb.orb.net.SocketStreamDecorationStrategy;
import org.openorb.util.logger.LoggerTeam;

/**
 * 
 * @author npadriano
 */
public final class SoTimeoutDecorationStrategy implements SocketStreamDecorationStrategy {

	public static final class Factory implements SocketStreamDecorationStrategy.Factory {

		@Override
		public SocketStreamDecorationStrategy create(final LoggerTeam logger, final ORBLoader loader,
				final String prefix) {
			return new SoTimeoutDecorationStrategy();
		}

	}

	@Override
	public InputStream decorate(final Socket socket, final InputStream stream) throws IOException {
		socket.setSoTimeout(1000);
		return stream;
	}

	@Override
	public OutputStream decorate(final Socket socket, final OutputStream stream) throws IOException {
		socket.setSoTimeout(1000);
		return stream;
	}
}
