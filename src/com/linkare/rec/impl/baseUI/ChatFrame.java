/*
 * ChatFrame.java
 *
 * Created on 01 May 2003, 19:15
 */

package com.linkare.rec.impl.baseUI;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.text.html.*;
import javax.swing.text.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.awt.event.*;
import java.util.logging.*;
import com.linkare.rec.impl.logging.*;
import com.linkare.rec.impl.utils.*;
import com.linkare.rec.impl.baseUI.labsTree.*;
import com.linkare.rec.impl.baseUI.config.*;
import com.linkare.rec.impl.client.chat.*;
import com.linkare.rec.impl.client.experiment.*;
import com.linkare.rec.acquisition.UserInfo;
import com.linkare.rec.impl.utils.Defaults;
import com.linkare.rec.impl.i18n.ReCResourceBundle;
/**
 *
 * @author  Jos� Pedro Pereira
 */
public class ChatFrame extends javax.swing.JInternalFrame implements IChatMessageListener
{
    private static String UI_CLIENT_LOGGER="ReC.baseUI";
    
    static
    {
        Logger l=LogManager.getLogManager().getLogger(UI_CLIENT_LOGGER);
        if(l==null)
        {
            LogManager.getLogManager().addLogger(Logger.getLogger(UI_CLIENT_LOGGER));
        }
    }
    private boolean isConnected=false;
    private String roomName=null;
    
    private static final String CHAT_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.chat", "Chat");
    private static final String UNAVAILABLE_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.unavailable", "Unavailable");
    private static final String CONNECTED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.connected", "Connected");
    private static final String DISCONNECTED_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.status.disconnected", "Disconnected");
    private static final String DEFAULT_ROOM_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.defaultRoom", "Default Room");
    private static final String EVERYONE_STR = ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.lbl.everyone", "Everyone");
    
    /** Creates new form ChatFrame */
    public ChatFrame()
    {
        initComponents();
        
        comboUsersChat.setRenderer(new ChatCellRenderer());
        
        this.setTitle(CHAT_STR + "[" + DISCONNECTED_STR + "]");
        
        editPaneChat.setContentType("text/html");
        editPaneChat.setText("<html><head><style>body{font: Verdana 8pt;background-color: #E5E6FF;padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px;margin: 0px 0px 0px 0px;}</style></head><body><div id=\"messages\"></div><a name=\"messagesEnd\"></a></body></html>");
    }
    
    private class ChatCellRenderer implements ListCellRenderer
    {
        
        public java.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new java.awt.BorderLayout());
            
            if(value instanceof UserInfo)
            {
                JPanel panelinfo = new JPanel();
                UserInfo user = (UserInfo)value;
                
                String userName = user.getUserName();
                if(userName.equals(IChatServer.EVERYONE_USER_ALIAS))
                    userName = EVERYONE_STR;
                JLabel jLabelName = new JLabel(userName);                
     
                JLabel jLabelIcon = new JLabel();                                
                Apparatus app = getApparatus(user.getHardwaresConnectedTo());
                if(app != null)
                {
                    jLabelIcon.setIcon(app.getIcon());
                }
                else
                    jLabelIcon.setIcon(null);
                
                panelinfo.add(jLabelName);
                panelinfo.add(jLabelIcon);
                
                panel.add(panelinfo, java.awt.BorderLayout.WEST);
            }
            return panel;
        }
        
    }
    
    private Apparatus getApparatus(String uniqueID)
    {        
        DefaultMutableTreeNode root = LabsTreeModel.treeRoot;
        if(root == null || uniqueID == null)
            return null;
        java.util.Enumeration allChild = ((DefaultMutableTreeNode)root).breadthFirstEnumeration();
        while(allChild.hasMoreElements())
        {
            Object currentNode = ((DefaultMutableTreeNode)allChild.nextElement()).getUserObject();
            if(currentNode instanceof Apparatus)
            {
                if(((Apparatus)currentNode).getLocation().equals(uniqueID))
                {
                    return (Apparatus)currentNode;
                }
            }
        }
        
        return null;
    }
    
    public void newChatMessage(ChatMessageEvent evt)
    {
        String userFrom = evt.getUserFrom().getUserName();
        String userTo = evt.getUserTo().getUserName();
        if(userFrom.equals(IChatServer.EVERYONE_USER_ALIAS))
            userFrom = EVERYONE_STR;
        if(userTo.equals(IChatServer.EVERYONE_USER_ALIAS))
            userTo = EVERYONE_STR;
        
        String htmlMessage = "<p style=\"padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px; margin: 0px 0px 0px 0px;\">";
        htmlMessage += "<font style=\"color: red;\">[ </font>";
        htmlMessage += "<font style=\"color: navy;font-weight: bold;\">";
        htmlMessage += userFrom;
        htmlMessage += " => ";
        htmlMessage += userTo;
        htmlMessage += "</font>";
        htmlMessage += "<font style=\"color: red;\"> ]</font>&nbsp;:&nbsp;";
        htmlMessage += "<font style=\"color: blue;\">";
        htmlMessage += evt.getMessage().replaceAll("&","&amp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll(System.getProperty("line.separator"),"<br>");
        htmlMessage += "</font></p>";
        synchronized(editPaneChat)
        {
            final HTMLDocument docHTML=(HTMLDocument)editPaneChat.getDocument();
            final String message=htmlMessage;
            SwingUtilities.invokeLater(
            new Runnable()
            {
                public void run()
                {
                    try
                    {
                        docHTML.insertBeforeEnd(docHTML.getElement("messages"),message);
                        editPaneChat.scrollToReference("messagesEnd");
                    }catch(Exception e)
                    {
                        LoggerUtil.logThrowable(null,e,Logger.getLogger(UI_CLIENT_LOGGER));
                    }
                }
            }
            );
        }
    }
    
    public void connectionChanged(com.linkare.rec.impl.client.chat.ChatConnectionEvent event)
    {
        this.isConnected = event.isConnected();
        String title = CHAT_STR;
        title = title + " " + (isConnected ? CONNECTED_STR: DISCONNECTED_STR);
        if(!isConnected)
        {
            roomName=null;
        }
        else
        {
            title+=(roomName==null?" [" + DEFAULT_ROOM_STR + "]":" [" + roomName + "]");
        }
        
        this.setTitle(title);
        
    }
    
    public void roomChanged(com.linkare.rec.impl.client.chat.ChatRoomEvent event)
    {
        roomName = event.getNewRoomName();
        String title = CHAT_STR;
        title = title + " " + (isConnected? CONNECTED_STR: DISCONNECTED_STR);
        if(!isConnected)
            roomName=null;
        else
        {
            title+=roomName==null?" [" + DEFAULT_ROOM_STR + "]":" ["+roomName+"]";
        }
        
        final String titleF=title;
        SwingUtilities.invokeLater(
        new Runnable()
        {
            public void run()
            {
                setTitle(titleF);
            }
        }
        );
    }
    
    public void userListChanged(UserInfo[] newUsersList)
    {
        final Object selected = comboUsersChat.getSelectedItem().toString();
        
        if(newUsersList==null)
        {
            cleanUsersList();
            //System.out.println("New users list is null...");
            return;
        }
        
        Arrays.sort(newUsersList,new Comparator()
        {
            public int compare(Object o1,Object o2)
            {
                if(o1==null && o2==null) return 0;
                
                if(o1==null || !(o1 instanceof UserInfo)) return -1;
                if(o2==null || !(o2 instanceof UserInfo)) return +1;
                
                UserInfo u1=(UserInfo)o1;
                UserInfo u2=(UserInfo)o2;
                
                if(u1.getUserName()==null && u2.getUserName()==null) return 0;
                if(u1.getUserName()==null || u1.getUserName().equals(IChatServer.EVERYONE_USER_ALIAS)) return -1;
                if(u2.getUserName()==null) return +1;
                
                return Collator.getInstance().compare(u1.getUserName(),u2.getUserName());
            }
            
            public boolean equals(Object other)
            {
                if(other==null || !(other.getClass()==this.getClass()))
                    return false;
                
                return true;
            }
        });
        
        cleanUsersList();
        
        //int displace=0;
        boolean foundHimSelf = false;
        
        final UserInfo[] finalNewUsersList=new UserInfo[newUsersList.length - 1];
        for(int i=0;i<newUsersList.length;i++)
        {
            if(newUsersList[i].getUserName().equals(getUser().getUserName()))
                foundHimSelf = true;
            else if(foundHimSelf)
                finalNewUsersList[i - 1] = newUsersList[i];
            else
                finalNewUsersList[i] = newUsersList[i];
        }
        
        SwingUtilities.invokeLater(
        new Runnable()
        {
            public void run()
            {
                comboUsersChat.setModel(new DefaultComboBoxModel(finalNewUsersList));
                for(int i=0; i<comboUsersChat.getItemCount(); i++)
                {
                    if(comboUsersChat.getItemAt(i).toString().equals(selected))
                    {
                        comboUsersChat.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        );
        
        
    }
    
    
    private void cleanUsersList()
    {
        ((DefaultComboBoxModel)comboUsersChat.getModel()).removeAllElements();
        ((DefaultComboBoxModel)comboUsersChat.getModel()).addElement(UNAVAILABLE_STR);
        SwingUtilities.invokeLater(
        new Runnable()
        {
            public void run()
            {
                comboUsersChat.setSelectedItem(UNAVAILABLE_STR);
            }
        }
        );
        
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents()//GEN-BEGIN:initComponents
    {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        editPaneChat = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        comboUsersChat = new javax.swing.JComboBox();
        clearMessagesBtn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        taChatMessage = new javax.swing.JTextArea();
        btnSendChatMessage = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.title.chat", "Chat"));
        setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.chat", "Chat with other users here!"));
        setFrameIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.chat", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Chat16.gif"))));
        setMinimumSize(new java.awt.Dimension(10, 10));
        setPreferredSize(new java.awt.Dimension(250, 100));
        editPaneChat.setBorder(null);
        editPaneChat.setEditable(false);
        editPaneChat.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jScrollPane1.setViewportView(editPaneChat);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridBagLayout());

        comboUsersChat.setModel(    new javax.swing.DefaultComboBoxModel(new String[] { UNAVAILABLE_STR }));
        comboUsersChat.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.selectUsers", "Select users to send message to"));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 100.0;
        jPanel3.add(comboUsersChat, gridBagConstraints);

        clearMessagesBtn.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.delete", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/Delete16.gif"))));
        clearMessagesBtn.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.clearChatMessages", "Clear Chat Messages"));
        clearMessagesBtn.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                clearMessagesBtnActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        jPanel3.add(clearMessagesBtn, gridBagConstraints);

        jPanel1.add(jPanel3, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setMinimumSize(new java.awt.Dimension(10, 10));
        taChatMessage.setColumns(30);
        taChatMessage.setLineWrap(true);
        taChatMessage.setRows(2);
        taChatMessage.setTabSize(4);
        taChatMessage.setToolTipText("Type text here");
        taChatMessage.setWrapStyleWord(true);
        taChatMessage.setMinimumSize(new java.awt.Dimension(200, 16));
        taChatMessage.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyPressed(java.awt.event.KeyEvent evt)
            {
                taChatMessageKeyPressed(evt);
            }
        });

        jScrollPane2.setViewportView(taChatMessage);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 100.0;
        jPanel2.add(jScrollPane2, gridBagConstraints);

        btnSendChatMessage.setIcon(ReCResourceBundle.findImageIconOrDefault("ReCBaseUI$rec.bui.icon.send", new ImageIcon(getClass().getResource("/com/linkare/rec/impl/baseUI/resources/SendMail16.gif"))));
        btnSendChatMessage.setToolTipText(ReCResourceBundle.findStringOrDefault("ReCBaseUI$rec.bui.tip.sendMessage", "Click to Send Message"));
        btnSendChatMessage.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                btnSendChatMessageActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.1;
        jPanel2.add(btnSendChatMessage, gridBagConstraints);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }//GEN-END:initComponents
    
    private void clearMessagesBtnActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_clearMessagesBtnActionPerformed
    {//GEN-HEADEREND:event_clearMessagesBtnActionPerformed
        synchronized(editPaneChat)
        {
            editPaneChat.setText("<html><head><style>body{font: Verdana 8pt;background-color: #E5E6FF;padding-top: 0px;padding-left: 0px;padding-right: 0px;padding-bottom: 0px;margin: 0px 0px 0px 0px;}</style></head><body><div id=\"messages\"></div><a name=\"messagesEnd\"></a></body></html>");
        }
    }//GEN-LAST:event_clearMessagesBtnActionPerformed
    
    
	private void taChatMessageKeyPressed(java.awt.event.KeyEvent evt)//GEN-FIRST:event_taChatMessageKeyPressed
	{//GEN-HEADEREND:event_taChatMessageKeyPressed
            if((evt.getModifiers() & KeyEvent.SHIFT_MASK)==0 && evt.getKeyCode()==KeyEvent.VK_ENTER)
                btnSendChatMessageActionPerformed(new ActionEvent(btnSendChatMessage,1,"Send ChatMessage"));
            else if((evt.getModifiers() & KeyEvent.SHIFT_MASK)!=0 && evt.getKeyCode()==KeyEvent.VK_ENTER && !taChatMessage.getText().trim().equals(""))
                taChatMessage.setText(taChatMessage.getText()+System.getProperty("line.separator"));
            
            evt.consume();
	}//GEN-LAST:event_taChatMessageKeyPressed
        
    private void btnSendChatMessageActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSendChatMessageActionPerformed
    {//GEN-HEADEREND:event_btnSendChatMessageActionPerformed
        if(taChatMessage.getText().trim().equals("")) return;
        
        if(user==null || chatServer==null || !(comboUsersChat.getSelectedItem() instanceof UserInfo))
        {
            /*System.out.println("Returning because:");
            System.out.println("user==null ? " + (user==null));
            System.out.println("user==null ? " + (user==null));
            System.out.println("!(comboUsersChat.getSelectedItem() instanceof UserInfo ? " + (!(comboUsersChat.getSelectedItem() instanceof UserInfo)));*/
            return;
        }
        else
        {
            ChatMessageEvent newMessage=new ChatMessageEvent(this, user, (UserInfo)comboUsersChat.getSelectedItem(),taChatMessage.getText());
            messageQueue.addEvent(newMessage);
            
            if( !((UserInfo)comboUsersChat.getSelectedItem()).getUserName().equals(IChatServer.EVERYONE_USER_ALIAS) )
                newChatMessage(newMessage);
        }
        
        taChatMessage.setText("");
        taChatMessage.requestFocus();
    }//GEN-LAST:event_btnSendChatMessageActionPerformed
    
    
    private class MessageQueueDispatcher implements EventQueueDispatcher
    {
        
        public void dispatchEvent(Object evt)
        {
            if(evt instanceof ChatMessageEvent && chatServer!=null)
                chatServer.sendMessage((ChatMessageEvent)evt);
        }
        
        public int getPriority()
        {
            return Thread.NORM_PRIORITY-1;
        }
        
    }
    
    //I've changed this to only intantiate with setChatServer... I was getting outofboundsexceptions
    private EventQueue messageQueue = null;
    
    
    /** Getter for property setChatServer.
     * @return Value of property setChatServer.
     */
    public IChatServer getChatServer()
    {
        return this.chatServer;
    }
    
    /** Setter for property setChatServer.
     * @param setChatServer New value of property setChatServer.
     */
    public void setChatServer(IChatServer chatServer)
    {
        if(chatServer!=null)
            chatServer.removeChatMessageListener(this);
        this.chatServer = chatServer;
        if(chatServer!=null)
            chatServer.addChatMessageListener(this);
        if(messageQueue == null)
            messageQueue = new EventQueue(new MessageQueueDispatcher());
    }
    
    /** Getter for property user.
     * @return Value of property user.
     */
    public UserInfo getUser()
    {
        return this.user;
    }
    
    /** Setter for property user.
     * @param user New value of property user.
     */
    public void setUser(UserInfo user)
    {
        this.user = user;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSendChatMessage;
    private javax.swing.JButton clearMessagesBtn;
    private javax.swing.JComboBox comboUsersChat;
    private javax.swing.JEditorPane editPaneChat;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea taChatMessage;
    // End of variables declaration//GEN-END:variables
    
    /** Holds value of property setChatServer. */
    private IChatServer chatServer=null;
    
    /** Holds value of property user. */
    private UserInfo user;
    
    
}
