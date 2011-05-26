/*
 * ChatMessageEvent.java
 *
 * Created on 01 May 2003, 12:46
 */

package com.linkare.rec.impl.events;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.threading.util.EnumPriority;

/**
 * 
 * @author José Pedro Pereira
 */
public class ChatMessageEvent extends java.util.EventObject implements Prioritazible {

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
	public ChatMessageEvent(final Object source, final UserInfo userFrom, final UserInfo userTo, final String message) {
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
		return userTo;
	}

	/**
	 * Setter for property userTo.
	 * 
	 * @param userTo New value of property userTo.
	 */
	public void setUserTo(final UserInfo userTo) {
		this.userTo = userTo;
	}

	/**
	 * Getter for property userFrom.
	 * 
	 * @return Value of property userFrom.
	 */
	public UserInfo getUserFrom() {
		return userFrom;
	}

	/**
	 * Setter for property userFrom.
	 * 
	 * @param userFrom New value of property userFrom.
	 */
	public void setUserFrom(final UserInfo userFrom) {
		this.userFrom = userFrom;
	}

	/**
	 * Getter for property message.
	 * 
	 * @return Value of property message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for property message.
	 * 
	 * @param message New value of property message.
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EnumPriority getPriority() {
		return EnumPriority.MINIMUM;
	}

}
