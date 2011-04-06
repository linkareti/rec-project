package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author artur
 */
@Entity
public class DoubleVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "DOUBLE_VALUE")
    private Double value;

    public Double getValue() {
	return value;
    }

    public void setValue(Double doubleValue) {
	this.value = doubleValue;
    }

}
