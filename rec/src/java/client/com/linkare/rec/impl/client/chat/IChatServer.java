/*
 * IChatServer.java
 *
 * Created on 01 May 2003, 12:35
 */

package com.linkare.rec.impl.client.chat;

import com.linkare.rec.acquisition.UserInfo;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public interface IChatServer
{
    public String EVERYONE_USER = null;
    public String EVERYONE_USER_ALIAS = "Everyone";
    
    public void sendMessage(ChatMessageEvent evt);
    
    UserInfo[] getUsers();
    
    void addChatMessageListener(IChatMessageListener cml);
    
    void removeChatMessageListener(IChatMessageListener cml);
    
    String getRoomName();
}
