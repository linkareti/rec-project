package com.linkare.rec.am.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Table(name = "SAMPLES_PACKET")
public class SamplesPacket extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @Column(name = "PACKET_NUMBER")
    private int packetNumber;

    @Column(name = "TOTAL_PACKETS")
    private int totalPackets;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_SAMPLES_PACKET", nullable = false, updatable = false)
    private List<ColumnPhysicsValue> data;

    @Embedded
    private DateTime timeStart = null;

    public SamplesPacket() {
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

    public List<ColumnPhysicsValue> getData() {
	return data;
    }

    public void setData(List<ColumnPhysicsValue> data) {
	this.data = data;
    }

    public DateTime getTimeStart() {
	return timeStart;
    }

    public void setTimeStart(DateTime timeStart) {
	this.timeStart = timeStart;
    }

}
