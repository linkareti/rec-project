package com.linkare.rec.am.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author artur
 */
@Entity
@Table(name = "COLUMN_PHYSICS_VALUE")
public class ColumnPhysicsValue extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @OneToMany
    @JoinColumn(name = "KEY_COLUMN_VALUES")
    private List<PhysicsValue> columnValues;

    @ManyToOne
    @JoinColumn(name = "KEY_SAMPLES_PACKET")
    private SamplesPacket samplesPacket;

    public ColumnPhysicsValue() {
    }

    public List<PhysicsValue> getColumnValues() {
	return columnValues;
    }

    public void setColumnValues(List<PhysicsValue> column) {
	this.columnValues = column;
    }

    public SamplesPacket getSamplesPacket() {
	return samplesPacket;
    }

    public void setSamplesPacket(SamplesPacket samplesPacket) {
	this.samplesPacket = samplesPacket;
    }

}
