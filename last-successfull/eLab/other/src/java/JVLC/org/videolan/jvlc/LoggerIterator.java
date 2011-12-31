/*****************************************************************************
 * LoggerIterator.java: VLC Java Bindings
 *****************************************************************************
 * Copyright (C) 1998-2008 the VideoLAN team
 *
 * Authors: Filippo Carone <filippo@carone.org>
 *
 *
 * $Id $
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111, USA.
 *****************************************************************************/

package org.videolan.jvlc;

import java.util.Iterator;

import org.videolan.jvlc.internal.LibVlc.LibVlcLogIterator;
import org.videolan.jvlc.internal.LibVlc.libvlc_exception_t;
import org.videolan.jvlc.internal.LibVlc.libvlc_log_message_t;

public class LoggerIterator implements Iterator<LoggerMessage> {

	private final Logger logger;
	private final LibVlcLogIterator logIterator;

	/**
	 * @param logInstance
	 */
	LoggerIterator(final Logger logger) {
		this.logger = logger;
		final libvlc_exception_t exception = new libvlc_exception_t();
		logIterator = logger.libvlc.libvlc_log_get_iterator(logger.logInstance, exception);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasNext() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		return logger.libvlc.libvlc_log_iterator_has_next(logIterator, exception) != 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LoggerMessage next() {
		final libvlc_exception_t exception = new libvlc_exception_t();
		final libvlc_log_message_t message = new libvlc_log_message_t();
		logger.libvlc.libvlc_log_iterator_next(logIterator, message, exception);
		final LoggerMessage result = new LoggerMessage(message);
		return result;
	}

	/**
	 * {@inheritDoc} Does not remove the element.
	 */
	@Override
	public void remove() {
		//
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void finalize() throws Throwable {
		final libvlc_exception_t exception = new libvlc_exception_t();
		logger.libvlc.libvlc_log_iterator_free(logIterator, exception);
		super.finalize();
	}

}
