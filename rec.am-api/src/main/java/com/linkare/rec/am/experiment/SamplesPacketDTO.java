package com.linkare.rec.am.experiment;

import java.util.List;

public class SamplesPacketDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private int packetNumber;

    private int totalPackets;

    private List<ColumnPhysicsValueDTO> data;

    private DateTimeDTO timeStart = null;

    public SamplesPacketDTO() {
    }

    public int getPacketNumber() {
	return packetNumber;
    }

    public void setPacketNumber(int packetNumber) {
	this.packetNumber = packetNumber;
    }

    public int getTotalPackets() {
	return totalPackets;
    }

    public void setTotalPackets(int totalPackets) {
	this.totalPackets = totalPackets;
    }

    public List<ColumnPhysicsValueDTO> getData() {
	return data;
    }

    public void setData(List<ColumnPhysicsValueDTO> data) {
	this.data = data;
    }

    public DateTimeDTO getTimeStart() {
	return timeStart;
    }

    public void setTimeStart(DateTimeDTO timeStart) {
	this.timeStart = timeStart;
    }
}
