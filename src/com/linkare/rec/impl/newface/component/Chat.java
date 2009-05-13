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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;


import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.client.chat.ChatConnectionEvent;
import com.linkare.rec.impl.client.chat.ChatMessageEvent;
import com.linkare.rec.impl.client.chat.ChatRoomEvent;
import com.linkare.rec.impl.client.chat.IChatMessageListener;
import com.sun.xml.internal.ws.util.StringUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.jdesktop.application.Action;

import pt.utl.ist.elab.client.webrobot.customizer.Comps.Esquerda;

/**
 *
 * @author Henrique Fernandes
 */
public class Chat extends javax.swing.JPanel implements IChatMessageListener {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(Chat.class.getName());
	
	private static final String CHAT_TEMPLATE_RESOURCE = "chatTemplate.html";

    private static final String USERMESSAGE__TEMPLATE__ID = "userMessage__TEMPLATE__";
    
    private static final String __USER__ = "__USER__";
    
    private static final String __MESSAGE__ = "__MESSAGE__";
	
	private static final InputStream CHAT_TEMPLATE_IS = Chat.class.getResourceAsStream(CHAT_TEMPLATE_RESOURCE);

    private static final String CHAT_TEMPLATE;
    
    private static final String USERMESSAGE_TEMPLATE;

	private static final String MESSAGE_LIST_END = "messageListEnd";

	private static final String MESSAGE_LIST = "messageList";
    
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
        
        if (log.isLoggable(Level.FINE)) {
			log.fine(USERMESSAGE_TEMPLATE);
			log.fine(CHAT_TEMPLATE);
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
					.replaceFirst(__MESSAGE__, message);
		}
		
		@Override
		public String toString() {
			return result;
		}

	}
   
	
    private HTMLDocument getHTMLDocument() {
    	return (HTMLDocument)msgPane.getDocument();
    }
    
	/** Creates new form Chat */
    public Chat() {
        initComponents();
        
        // Set HTML document
        msgPane.setText(CHAT_TEMPLATE);
        
    }

    @Override
    public void roomChanged(ChatRoomEvent evt) {
        log.fine(evt.toString());
    }

    @Override
    public void connectionChanged(ChatConnectionEvent evt) {
    	log.fine(evt.toString());
    }

    @Override
    public void userListChanged(UserInfo[] usrInfo) {
    	log.fine(Arrays.deepToString(usrInfo));
    }

    @Override
    public void newChatMessage(ChatMessageEvent evt) {
    	log.fine(evt.toString());
    }
    
    @Action
    public void sendMessage() {
    	String msg = StringEscapeUtils.escapeJava(txtInputMsg.getText());
    	if (msg.length() > 0) {
    		String escapedMsg = StringEscapeUtils.escapeHtml(msg);
    		log.fine(escapedMsg);
	    	Element msgList = getHTMLDocument().getElement(MESSAGE_LIST);
	        try {
				getHTMLDocument().insertBeforeEnd(msgList, 
						new UserMessage("Henrique", escapedMsg).toString());
				msgPane.scrollToReference(MESSAGE_LIST_END);
				
			} catch (BadLocationException e) {
				log.log(Level.SEVERE, "Trying to insert html element", e);
			} catch (IOException e) {
				log.log(Level.SEVERE, "Trying to insert html element", e);
			}
			clearInputMessage();
    	}
    }
    
    private void clearInputMessage() {
    	txtInputMsg.setText("");
    }

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtInputMsg = new javax.swing.JTextField();
        msgScrollPane = new javax.swing.JScrollPane();
        msgPane = new javax.swing.JEditorPane();

        setName("Form"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getResourceMap(Chat.class);
        txtInputMsg.setText(resourceMap.getString("txtInputMsg.text")); // NOI18N
        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext().getActionMap(Chat.class, this);
        txtInputMsg.setAction(actionMap.get("sendMessage")); // NOI18N
        txtInputMsg.setName("txtInputMsg"); // NOI18N

        msgScrollPane.setName("msgScrollPane"); // NOI18N

        msgPane.setContentType(resourceMap.getString("msgPane.contentType")); // NOI18N
        msgPane.setEditable(false);
        msgPane.setText(resourceMap.getString("msgPane.text")); // NOI18N
        msgPane.setName("msgPane"); // NOI18N
        msgScrollPane.setViewportView(msgPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtInputMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
            .addComponent(msgScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(msgScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtInputMsg, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane msgPane;
    private javax.swing.JScrollPane msgScrollPane;
    private javax.swing.JTextField txtInputMsg;
    // End of variables declaration//GEN-END:variables

}
