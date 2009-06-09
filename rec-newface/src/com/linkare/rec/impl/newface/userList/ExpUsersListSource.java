/*
 * ExpUsersListSource.java
 *
 * Created on 08 May 2003, 21:48
 */

package com.linkare.rec.impl.newface.userList;

import com.linkare.rec.acquisition.UserInfo;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public interface ExpUsersListSource
{
    
    /** Registers ExpUsersListChangeListener to receive events.
     * @param listener The listener to register.
     */
    public void addExpUsersListChangeListener(ExpUsersListChangeListener listener);
    
    /** Removes ExpUsersListChangeListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public void removeExpUsersListChangeListener(ExpUsersListChangeListener listener);
    
    public UserInfo[] getUsers();
    
    public void startAutoRefresh(long delayMillis);
    
    public void stopAutoRefresh();
    
}
