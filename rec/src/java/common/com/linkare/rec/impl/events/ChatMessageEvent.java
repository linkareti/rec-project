/*
 * ChatMessageEvent.java
 *
 * Created on 01 May 2003, 12:46
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.acquisition.UserInfo;

/**
 * 
 * @author José Pedro Pereira
 */
public class ChatMessageEvent extends java.util.EventObject {

	/** Generated UID */
	private static final long serialVersionUID = 5094517997811244867L;

	public static final String EVERYONE_USER_ALIAS = "Everyone";

	public static final String EVERYONE_USER = null;

	public static final String MULTICAST_USERNAME = "SysAdmin";
	public static final String SECURITY_COMMUNICATION_MSG_BEFORE_KICK_KEY = "before.kick";
	public static final String SECURITY_COMMUNICATION_MSG_ON_KICK_KEY = "on.kick";
	public static final String SECURITY_COMMUNICATION_MSG_SEPARATOR = "###";

	/** Holds value of property userTo. */
	private UserInfo userTo;

	/** Holds value of property userFrom. */
	private UserInfo userFrom;

	/** Holds value of property message. */
	private String message;

	/** Creates a new instance of ChatMessageEvent */
	public ChatMessageEvent(Object source, UserInfo userFrom, UserInfo userTo, String message) {
		super(source);
		this.message = message;
		this.userFrom = userFrom;
		this.userTo = userTo;
	}

	/**
	 * Getter for property userTo.
	 * 
	 * @return Value of property userTo.
	 */
	public UserInfo getUserTo() {
		return this.userTo;
	}

	/**
	 * Setter for property userTo.
	 * 
	 * @param userTo New value of property userTo.
	 */
	public void setUserTo(UserInfo userTo) {
		this.userTo = userTo;
	}

	/**
	 * Getter for property userFrom.
	 * 
	 * @return Value of property userFrom.
	 */
	public UserInfo getUserFrom() {
		return this.userFrom;
	}

	/**
	 * Setter for property userFrom.
	 * 
	 * @param userFrom New value of property userFrom.
	 */
	public void setUserFrom(UserInfo userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * Getter for property message.
	 * 
	 * @return Value of property message.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Setter for property message.
	 * 
	 * @param message New value of property message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
