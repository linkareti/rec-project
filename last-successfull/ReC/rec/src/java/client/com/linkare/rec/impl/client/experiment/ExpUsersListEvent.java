/*
 * ExpUsersListChangeEvent.java
 *
 * Created on July 29, 2004, 4:09 PM
 */

package com.linkare.rec.impl.client.experiment;

/**
 * 
 * @author André Neto - LEFT - IST
 */
public class ExpUsersListEvent extends java.util.EventObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5596745922812557782L;
	private final com.linkare.rec.acquisition.UserInfo[] userInfo;

	@SuppressWarnings("PMD.ArrayIsStoredDirectly")
	public ExpUsersListEvent(final Object source, final com.linkare.rec.acquisition.UserInfo[] userInfo) {
		super(source);
		this.userInfo = userInfo;
	}

	public com.linkare.rec.acquisition.UserInfo[] getUserInfo() {
		return userInfo;
	}
}
