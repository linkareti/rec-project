/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatBox.java
 *
 * Created on 20/Abr/2009, 11:44:28
 */

package com.linkare.rec.impl.newface.component;

/**
 * 
 * @author Henrique Fernandes
 */
public class ChatBox extends AbstractContentPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2937212386156434551L;

	/** Creates new form ChatBox */
	public ChatBox() {
		initComponents();
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		lblChatRoom = new javax.swing.JLabel();
		chat = new com.linkare.rec.impl.newface.component.Chat();

		setName("Form"); // NOI18N

		lblChatRoom
				.setFont(lblChatRoom.getFont().deriveFont(lblChatRoom.getFont().getStyle() | java.awt.Font.BOLD, 11));
		final org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application
				.getInstance(com.linkare.rec.impl.newface.ReCApplication.class).getContext()
				.getResourceMap(ChatBox.class);
		lblChatRoom.setText(resourceMap.getString("lblChatRoom.text")); // NOI18N
		lblChatRoom.setName("lblChatRoom"); // NOI18N

		chat.setName("chat"); // NOI18N

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(chat, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
										.addComponent(lblChatRoom)).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(lblChatRoom)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(chat, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
						.addContainerGap()));
	}// </editor-fold>//GEN-END:initComponents

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.linkare.rec.impl.newface.component.Chat chat;
	private javax.swing.JLabel lblChatRoom;
	// End of variables declaration//GEN-END:variables

}
