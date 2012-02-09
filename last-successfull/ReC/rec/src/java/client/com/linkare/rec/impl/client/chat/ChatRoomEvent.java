/*
 * ChatRoomEvent.java
 *
 * Created on July 29, 2004, 5:23 PM
 */

package com.linkare.rec.impl.client.chat;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ChatRoomEvent extends java.util.EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4273394836402179364L;
	private String newRoomName = "";

	public ChatRoomEvent(final Object source, final String newRoomName) {
		super(source);
		this.newRoomName = newRoomName;
	}

	public String getNewRoomName() {
		return newRoomName;
	}
}
