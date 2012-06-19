package com.linkare.rec.am;

import java.io.Serializable;

public class MultiCastControllerNotifInfoDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String labID;

    private String notificationType;

    private HardwareInfoDTO hardwareInfoDTO;

    private HardwareStateChangeDTO hardwareStateChangeDTO;

    private ClientInfoDTO clientInfoDTO;

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

    public HardwareInfoDTO getHardwareInfoDTO() {
	return hardwareInfoDTO;
    }

    public void setHardwareInfoDTO(HardwareInfoDTO hardwareInfoDTO) {
	this.hardwareInfoDTO = hardwareInfoDTO;
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
}
