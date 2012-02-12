package com.linkare.rec.export.domain;

import com.linkare.rec.data.acquisition.PhysicsValue;
import com.linkare.rec.export.exceptions.InvalidMultiplierException;
import com.linkare.rec.export.exceptions.InvalidTypeValException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author artur
 */
@Entity
@Table(name = "COLUMN_PHYSICS_VALUE")
public class EColumnPhysicsValue implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private List<EPhysicsValue> columnValues;
    private ESamplesPacket samplesPacket;

    public EColumnPhysicsValue() {
    }

    public EColumnPhysicsValue(PhysicsValue[] physicsValues) throws InvalidTypeValException, InvalidMultiplierException {
        if (physicsValues != null) {
            for (PhysicsValue physicsValue : physicsValues) {
                if (physicsValue != null) {
                    addBidirectEPhysicsValue(new EPhysicsValue(physicsValue));
                }
            }
        }

    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @OneToMany(mappedBy = "column", cascade = CascadeType.PERSIST)
    public List<EPhysicsValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<EPhysicsValue> column) {
        this.columnValues = column;
    }

    @ManyToOne
    @JoinColumn(name = "SAMPLEPACKET_ID", nullable = false)
    public ESamplesPacket getSamplesPacket() {
        return samplesPacket;
    }

    public void setSamplesPacket(ESamplesPacket samplesPacket) {
        this.samplesPacket = samplesPacket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EColumnPhysicsValue)) {
            return false;
        }
        EColumnPhysicsValue other = (EColumnPhysicsValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "[id=" + id + "]";
    }

    public void addBidirectEPhysicsValue(EPhysicsValue ePhysicsValue) {
        if (ePhysicsValue != null) {
            if (ePhysicsValue.getColumn() != null) {
                ePhysicsValue.getColumn().getColumnValues().remove(ePhysicsValue);
            }
            ePhysicsValue.setColumn(this);
            if (this.getColumnValues() == null) {
                this.setColumnValues(new ArrayList<EPhysicsValue>());
            }
            this.getColumnValues().add(ePhysicsValue);
        }

    }
}
