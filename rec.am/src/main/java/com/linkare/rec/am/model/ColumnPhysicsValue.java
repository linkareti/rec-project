package com.linkare.rec.am.model;

import java.util.List;

import javax.persistence.CascadeType;
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
@Table(name = "COLUMN_PHYSICS_VALUE")
public class ColumnPhysicsValue extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn(name = "KEY_COLUMN_VALUES", nullable = false, updatable = false)
    private List<PhysicsValue> columnValues;

    public ColumnPhysicsValue() {
    }

    public List<PhysicsValue> getColumnValues() {
	return columnValues;
    }

    public void setColumnValues(List<PhysicsValue> column) {
	this.columnValues = column;
    }

}
