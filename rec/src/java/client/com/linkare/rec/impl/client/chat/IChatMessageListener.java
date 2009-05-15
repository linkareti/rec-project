/*
 * IChatMessageListener.java
 *
 * Created on 01 May 2003, 12:51
 */

package com.linkare.rec.impl.client.chat;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.events.ChatMessageEvent;

/**
 *
 * @author  Jos� Pedro Pereira
 */
public interface IChatMessageListener extends java.util.EventListener
{
        
    /*void roomChanged(String param0);
    void isConnected();*/
    void roomChanged(com.linkare.rec.impl.client.chat.ChatRoomEvent event);
    void connectionChanged(com.linkare.rec.impl.client.chat.ChatConnectionEvent event);
    
    void userListChanged(UserInfo[] newUsersList);
    
    void newChatMessage(ChatMessageEvent evt);
    
}
