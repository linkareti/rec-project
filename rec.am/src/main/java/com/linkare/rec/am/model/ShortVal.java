package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author artur
 */
@Entity
public class ShortVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "SHORT_VAL")
    private Short value;

    public Short getValue() {
	return value;
    }

    public void setValue(Short value) {
	this.value = value;
    }

}
