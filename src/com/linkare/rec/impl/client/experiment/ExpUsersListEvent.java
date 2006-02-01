/*
 * ExpUsersListChangeEvent.java
 *
 * Created on July 29, 2004, 4:09 PM
 */

package com.linkare.rec.impl.client.experiment;

/**
 *
 * @author  andre
 */
public class ExpUsersListEvent extends java.util.EventObject
{
    private com.linkare.rec.acquisition.UserInfo[] userInfo;
    
    public ExpUsersListEvent(Object source, com.linkare.rec.acquisition.UserInfo[] userInfo)
    {
	super(source);
	this.userInfo = userInfo;
    }
    
    public com.linkare.rec.acquisition.UserInfo[] getUserInfo()
    {
	return userInfo;
    }
}
