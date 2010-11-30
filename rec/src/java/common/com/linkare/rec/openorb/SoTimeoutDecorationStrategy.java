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
import org.openorb.util.logger.LoggerTeam;
import org.openorb.orb.net.SocketStreamDecorationStrategy;

/**
 * 
 * @author npadriano
 */
public final class SoTimeoutDecorationStrategy implements SocketStreamDecorationStrategy {

	public final class Factory implements SocketStreamDecorationStrategy.Factory {

		public SocketStreamDecorationStrategy create(LoggerTeam logger, ORBLoader loader, String prefix) {
			return new SoTimeoutDecorationStrategy();
		}

	}

	public InputStream decorate(Socket socket, InputStream stream) throws IOException {
		socket.setSoTimeout(1000);
		return stream;
	}

	public OutputStream decorate(Socket socket, OutputStream stream) throws IOException {
		socket.setSoTimeout(1000);
		return stream;
	}
}
