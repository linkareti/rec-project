/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.web.bean;

import com.linkare.rec.web.ClientInfoDTO;
import com.linkare.rec.web.RecChatMessageDTO;
import com.linkare.rec.web.model.DeployedExperiment;
import com.linkare.rec.web.moodle.SessionHelper;
import com.linkare.rec.web.util.LaboratoriesMonitor;
import com.linkare.rec.web.util.MultiThreadLaboratoryWrapper;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.CloseEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Gedsimon Pereira - Linkare TI
 */
@ManagedBean(name = "chatBean")
@ViewScoped
public class ChatBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1721201942653716666L;
	private List<ClientInfoDTO> usersLab;
    private List<String> usersExperiment;
    private MultiThreadLaboratoryWrapper selectedLab;
    private List<MultiThreadLaboratoryWrapper> labs;
    private String messageReceived;
    private String selectedUser;
    private String message;
    private boolean privateMessage;
    private StringBuilder strBuilder = new StringBuilder();
    private DeployedExperiment selectedExperimentLab;
    final String USER_NAME = SessionHelper.getUserView().getUsername();
    private List<RecChatMessageDTO> chatMessageDTO;

    public List<MultiThreadLaboratoryWrapper> getLabs() {
        if (labs == null) {
            labs = LaboratoriesMonitor.getInstance().getActiveLabs();
        }
        return labs;
    }

    public void updateLabStatus() {
        labs = LaboratoriesMonitor.getInstance().getActiveLabs();
        getUsers();
    }

    public void onTabChange(TabChangeEvent event) {
        selectedLab = (MultiThreadLaboratoryWrapper) event.getData();
        getUsers();
        messageReceived = null;
    }

    public List<ClientInfoDTO> getUsers() {

        if (selectedLab != null) {
            usersLab = new ArrayList<ClientInfoDTO>();
            List<String> connectedUsers = new ArrayList<String>(selectedLab.getConnectedUsers());
            for (String userName : connectedUsers) {
                usersLab.add(new ClientInfoDTO(userName));
            }
        }
        return usersLab;
    }

    public List<String> getUsersExperiment() {
        if (selectedExperimentLab != null) {
            usersExperiment = new ArrayList<String>();
            usersExperiment.addAll(selectedExperimentLab.getUsersConnected());
        }
        return usersExperiment;
    }

    public void send() {
        if (message != null) {
            //if (selectedUser == null || selectedUser.equals(ResourceBundleUtil.getValue(Locale.getDefault(), "label.everyone"))) {
            if(!usersExperiment.contains(selectedUser)){
                selectedLab.sendMessage(new ClientInfoDTO(USER_NAME), null, message);
            } else {
                selectedLab.sendMulticastMessage(selectedUser, message);
            }
            chatMessageDTO.add(new RecChatMessageDTO(USER_NAME +" : "+message, new Date().getTime(), null));
            strBuilder.append(USER_NAME).append(" : ").append(message).append(System.getProperty("line.separator"));
            //messageReceived = strBuilder.toString();
            message = null;
        }
    }

    public void refreshMessageReceived() {
        if (selectedLab != null && selectedLab.getRecChatMessageDTO() != null) {
            for(RecChatMessageDTO newMessage : selectedLab.getRecChatMessageDTO()){
                if(!chatMessageDTO.contains(newMessage)){
                   chatMessageDTO.add(newMessage); 
                }
            }
        }
    }

    public void kickUser() {
        Set<String> userNamesToKick = new HashSet<String>();
        userNamesToKick.add(selectedUser);
        selectedLab.kickUsers(userNamesToKick, selectedExperimentLab.getExperiment().getExternalId());
        refreshUserConnected();

    }

    public void refreshUserConnected() {
        getUsersExperiment();
    }
    
    public void listenerMethod(ClientInfoDTO clientInfo) {
        System.out.println("Event: "+clientInfo);
    }

    public void handleClose(CloseEvent event) {
        messageReceived = null;
        chatMessageDTO = null;
    }

    public MultiThreadLaboratoryWrapper getSelectedLab() {
        return selectedLab;
    }

    public void setSelectedLab(MultiThreadLaboratoryWrapper selectedLab) {
        this.selectedLab = selectedLab;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String userSelected) {
        this.selectedUser = userSelected;
    }

    public String getMessageReceived() {
        return messageReceived;
    }

    public void setMessageReceived(String messageReceived) {
        this.messageReceived = messageReceived;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isPrivateMessage() {
        return privateMessage;
    }

    public void setPrivateMessage(boolean privateMessage) {
        this.privateMessage = privateMessage;
    }

    public DeployedExperiment getSelectedExperimentLab() {
        return selectedExperimentLab;
    }

    public void setSelectedExperimentLab(DeployedExperiment selectedExperimentLab) {
        this.selectedExperimentLab = selectedExperimentLab;
    }

    public List<RecChatMessageDTO> getChatMessageDTO() {
        if (chatMessageDTO == null) {
            chatMessageDTO = new ArrayList<RecChatMessageDTO>();
        }
        return chatMessageDTO;
    }

    public void setChatMessageDTO(List<RecChatMessageDTO> chatMessageDTO) {
        this.chatMessageDTO = chatMessageDTO;
    }
}
