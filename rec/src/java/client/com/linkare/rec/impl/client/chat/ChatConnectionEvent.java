/*
 * ChatConnectionEvent.java
 *
 * Created on July 29, 2004, 4:22 PM
 */

package com.linkare.rec.impl.client.chat;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ChatConnectionEvent extends java.util.EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -553175236398899773L;
	private boolean connect = false;
	private final String newRoomName = "";

	public ChatConnectionEvent(final Object source, final boolean connect) {
		super(source);
		this.connect = connect;
	}

	public boolean isConnected() {
		return connect;
	}
}
