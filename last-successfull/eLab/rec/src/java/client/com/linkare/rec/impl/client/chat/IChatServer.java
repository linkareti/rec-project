/*
 * IChatServer.java
 *
 * Created on 01 May 2003, 12:35
 */

package com.linkare.rec.impl.client.chat;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.events.ChatMessageEvent;

/**
 * 
 * @author Jos� Pedro Pereira
 */
public interface IChatServer {
	public void sendMessage(ChatMessageEvent evt);

	UserInfo[] getUsers();

	void addChatMessageListener(IChatMessageListener cml);

	void removeChatMessageListener(IChatMessageListener cml);

	String getRoomName();
}
