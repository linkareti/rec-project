/*****************************************************************************
 * LoggerMessage.java: VLC Java Bindings
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

import org.videolan.jvlc.internal.LibVlc.libvlc_log_message_t;

public class LoggerMessage {

	private final LoggerVerbosityLevel severity;
	private final String header;
	private final String message;
	private final String name;
	private final String type;

	/**
	 * @param message
	 */
	LoggerMessage(final libvlc_log_message_t message) {
		severity = LoggerVerbosityLevel.getSeverity(message.i_severity);
		header = message.psz_header;
		this.message = message.psz_message;
		name = message.psz_name;
		type = message.psz_type;
	}

	/**
	 * Returns the header.
	 * 
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}

	/**
	 * Returns the message.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Returns the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the type.
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the severity.
	 * 
	 * @return the severity
	 */
	public LoggerVerbosityLevel getSeverity() {
		return severity;
	}

}
