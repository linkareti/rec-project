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
	private boolean connect = false;
	private String newRoomName = "";

	public ChatConnectionEvent(Object source, boolean connect) {
		super(source);
		this.connect = connect;
	}

	public boolean isConnected() {
		return connect;
	}
}
