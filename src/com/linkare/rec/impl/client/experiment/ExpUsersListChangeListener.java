/*
 * ExpUsersListChangeListener.java
 *
 * Created on 08 May 2003, 21:46
 */

package com.linkare.rec.impl.client.experiment;

import java.util.EventListener;
import com.linkare.rec.acquisition.UserInfo;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public interface ExpUsersListChangeListener extends java.util.EventListener
{
    public void usersListChanged(ExpUsersListEvent evt);
}
