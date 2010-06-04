/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Chat.java
 *
 * Created on 20/Abr/2009, 11:47:20
 */

package com.linkare.rec.impl.newface.component;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;

import org.apache.commons.lang.StringEscapeUtils;
import org.jdesktop.application.Action;

import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.client.chat.ChatConnectionEvent;
import com.linkare.rec.impl.client.chat.ChatRoomEvent;
import com.linkare.rec.impl.client.chat.IChatMessageListener;
import com.linkare.rec.impl.client.chat.IChatServer;
import com.linkare.rec.impl.events.ChatMessageEvent;
import com.linkare.rec.impl.utils.EventQueue;
import com.linkare.rec.impl.utils.EventQueueDispatcher;

/**
 * 
 * @author Henrique Fernandes
 */
public class Chat extends javax.swing.JPanel implements IChatMessageListener {

	private static final Logger log = Logger.getLogger(Chat.class.getName());

	private static final String CHAT_TEMPLATE_RESOURCE = "resources/chatTemplate.htm";

	private static final String USERMESSAGE__TEMPLATE__ID = "userMessage__TEMPLATE__";

	private static final String __USER__ = "__USER__";

	private static final String __MESSAGE__ = "__MESSAGE__";

	private static final InputStream CHAT_TEMPLATE_IS = Chat.class.getResourceAsStream(CHAT_TEMPLATE_RESOURCE);

	private static final String CHAT_TEMPLATE;

	private static final String USERMESSAGE_TEMPLATE;

	private static final String MESSAGE_LIST = "messageList";
	
	//BORDER
	private static final Color COLOR_BORDER_SOLID_THIN_BLUE = new Color(0x517DA8);

	public static final Border THIN_BLUE_BORDER = BorderFactory.createCompoundBorder(javax.swing.BorderFactory
			.createLineBorder(COLOR_BORDER_SOLID_THIN_BLUE), javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

	static {
		boolean parsingUserMessageTemplate = false;
		StringBuilder userMessageTemplate = new StringBuilder();

		StringBuilder chatTemplateResult = new StringBuilder();
		Scanner scan = new Scanner(CHAT_TEMPLATE_IS);
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			if (line.contains(USERMESSAGE__TEMPLATE__ID)) {
				parsingUserMessageTemplate = true;
				userMessageTemplate.append(line);

			} else if (parsingUserMessageTemplate) {
				if (line.contains("</div>")) {
					parsingUserMessageTemplate = false;
				}
				userMessageTemplate.append(line);

			} else {
				chatTemplateResult.append(line);
			}
		}
		USERMESSAGE_TEMPLATE = userMessageTemplate.toString();
		CHAT_TEMPLATE = chatTemplateResult.toString();

		if (log.isLoggable(Level.INFO)) {
			log.info(USERMESSAGE_TEMPLATE);
			log.info(CHAT_TEMPLATE);
		}
	}

	private static class UserMessage {

		private final String user;
		private final String message;
		private final String result;

		public UserMessage(String user, String message) {
			super();
			this.user = user;
			this.message = message;
			this.result = USERMESSAGE_TEMPLATE.replaceFirst(__USER__, user)
					.replaceFirst(__MESSAGE__, StringEscapeUtils.escapeJava(message));
		}

		@Override
		public String toString() {
			return result;
		}

	}

	private class MessageQueueDispatcher implements EventQueueDispatcher {

		public void dispatchEvent(Object evt) {
			if (evt instanceof ChatMessageEvent && chatServer != null)
				chatServer.sendMessage((ChatMessageEvent) evt);
		}

		public int getPriority() {
			return Thread.NORM_PRIORITY - 1;
		}

	}

	private EventQueue messageQueue = null;

	private UserInfo[] usersList;

	/** Creates new form Chat */
	public Chat() {
		initComponents();

		// Set HTML document
		msgPane.setText(CHAT_TEMPLATE);

	}

	private HTMLDocument getHTMLDocument() {
		return (HTMLDocument) msgPane.getDocument();
	}

	@Override
	public void roomChanged(ChatRoomEvent evt) {
		if (log.isLoggable(Level.FINE)) {
			log.fine(evt.toString());
		}
	}

	@Override
	public void connectionChanged(ChatConnectionEvent evt) {
		if (log.isLoggable(Level.FINE)) {
			log.fine(evt.toString());
		}

		if (evt.isConnected()) {
			msgPane.setText(CHAT_TEMPLATE);
			txtInputMsg.setText("");
		}
	}

	@Override
	public void userListChanged(UserInfo[] usersList) {
		if (log.isLoggable(Level.FINEST)) {
			log.finest(Arrays.deepToString(usersList));
		}
		this.usersList = usersList;

		boolean foundUserEveryone = false;

		for (UserInfo user : usersList) {
			if (ChatMessageEvent.EVERYONE_USER_ALIAS.equals(user.getUserName())) {
				everyone = user;
				foundUserEveryone = true;
			}
		}

		if (!foundUserEveryone) {
			everyone = new UserInfo(ChatMessageEvent.EVERYONE_USER);
		}
	}

	@Override
	public void newChatMessage(ChatMessageEvent evt) {
		if (log.isLoggable(Level.FINE)) {
			log.fine(evt != null ? evt.getMessage() : "null event");
		}

		String userFrom = evt.getUserFrom().getUserName();
		String msg = evt.getMessage();

		if (msg.length() > 0) {
			String escapedMsg = StringEscapeUtils.escapeHtml(msg);

			Element msgList = getHTMLDocument().getElement(MESSAGE_LIST);

			try {
				getHTMLDocument().insertBeforeEnd(msgList, new UserMessage(userFrom, escapedMsg).toString());

			} catch (BadLocationException e) {
				log.log(Level.SEVERE, "Trying to insert html element", e);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Trying to insert html element", e);
			}
			//TODO improve the method to scroll to the end of the jEditorPane
			int editorPaneHeight = (int) msgPane.getBounds().getHeight();
			msgPane.scrollRectToVisible(new Rectangle(new Point(0, editorPaneHeight + 15)));

		}
	}

	@Action
	public void sendMessage() {

		String msg = txtInputMsg.getText();

		if (userInfo != null && chatServer != null && msg.length() > 0) {

			ChatMessageEvent newMessage = new ChatMessageEvent(this, userInfo, everyone, msg);

			log.info("mesage event: " + newMessage);
			log.info("mesage: " + msg);

			messageQueue.addEvent(newMessage);
			//			if (!((UserInfo) comboUsersChat.getSelectedItem()).getUserName().equals(IChatServer.EVERYONE_USER_ALIAS))
			//				newChatMessage(newMessage);

			clearInputMessage();
		}
	}

	private void clearInputMessage() {
		txtInputMsg.setText("");
	}

	/**
	 * @return the chatServer
	 */
	public IChatServer getChatServer() {
		return chatServer;
	}

	/**
	 * @return the userInfo
	 */
	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * @param chatServer
	 *            the chatServer to set
	 */
	public void setChatServer(IChatServer chatServer) {
		if (this.chatServer != null)
			this.chatServer.removeChatMessageListener(this);
		this.chatServer = chatServer;
		if (this.chatServer != null)
			this.chatServer.addChatMessageListener(this);
		if (messageQueue == null)
			messageQueue = new EventQueue(new MessageQueueDispatcher());
	}

	/**
	 * @param userInfo
	 *            the userInfo to set
	 */
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	/*
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		txtInputMsg.setEnabled(enabled);
		msgPane.setEnabled(enabled);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
	 * content of this method is always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtInputMsg = new javax.swing.JTextField();
        pChatContainer = new javax.swing.JPanel();
        msgScrollPane = new javax.swing.JScrollPane();
        msgPane = new javax.swing.JEditorPane();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(Chat.class);
        txtInputMsg.setText(resourceMap.getString("txtInputMsg.text")); // NOI18N
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(Chat.class, this);
        txtInputMsg.setAction(actionMap.get("sendMessage")); // NOI18N
        txtInputMsg.setName("txtInputMsg"); // NOI18N
        txtInputMsg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtInputMsgActionPerformed(evt);
            }
        });

        pChatContainer.setName("pChatContainer"); // NOI18N
        pChatContainer.setLayout(new javax.swing.BoxLayout(pChatContainer, javax.swing.BoxLayout.LINE_AXIS));
		pChatContainer.setBorder(THIN_BLUE_BORDER);

        msgScrollPane.setName("msgScrollPane"); // NOI18N

        msgPane.setContentType(resourceMap.getString("msgPane.contentType")); // NOI18N
        msgPane.setEditable(false);
        msgPane.setText(resourceMap.getString("msgPane.text")); // NOI18N
        msgPane.setName("msgPane"); // NOI18N
        msgScrollPane.setViewportView(msgPane);

        pChatContainer.add(msgScrollPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtInputMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(pChatContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(pChatContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInputMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

        private void txtInputMsgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtInputMsgActionPerformed
            log.info("action was performed");
        }//GEN-LAST:event_txtInputMsgActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane msgPane;
    private javax.swing.JScrollPane msgScrollPane;
    private javax.swing.JPanel pChatContainer;
    private javax.swing.JTextField txtInputMsg;
    // End of variables declaration//GEN-END:variables

	/** Holds value of property setChatServer. */
	private IChatServer chatServer;

	/** Holds value of property user. */
	private UserInfo userInfo;

	private UserInfo everyone;

}
