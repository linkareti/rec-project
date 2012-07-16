package com.linkare.rec.web;

import java.io.Serializable;

public class MultiCastControllerNotifInfoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private long uptime;
    private String labID;
    private String notificationType;
    private RegisteredHardwareDTO registeredHardwareDTO;
    private HardwareStateChangeDTO hardwareStateChangeDTO;
    private ClientInfoDTO clientInfoDTO;
    private RecChatMessageDTO recChatMessageDTO;

    public MultiCastControllerNotifInfoDTO(final String labID, final String notificationType) {
        this.labID = labID;
        this.notificationType = notificationType;
    }

    public String getLabID() {
        return labID;
    }

    public void setLabID(String labID) {
        this.labID = labID;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public RegisteredHardwareDTO getRegisteredHardwareDTO() {
        return registeredHardwareDTO;
    }

    public void setRegisteredHardwareDTO(RegisteredHardwareDTO registeredHardwareDTO) {
        this.registeredHardwareDTO = registeredHardwareDTO;
    }

    public HardwareStateChangeDTO getHardwareStateChangeDTO() {
        return hardwareStateChangeDTO;
    }

    public void setHardwareStateChangeDTO(HardwareStateChangeDTO hardwareStateDTO) {
        this.hardwareStateChangeDTO = hardwareStateDTO;
    }

    public ClientInfoDTO getClientInfoDTO() {
        return clientInfoDTO;
    }

    public void setClientInfoDTO(ClientInfoDTO clientInfoDTO) {
        this.clientInfoDTO = clientInfoDTO;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }

    public RecChatMessageDTO getRecChatMessageDTO() {
        return recChatMessageDTO;
    }

    public void setRecChatMessageDTO(RecChatMessageDTO recChatMessageDTO) {
        this.recChatMessageDTO = recChatMessageDTO;
    }
}
