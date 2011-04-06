package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author artur
 */
@Entity
public class IntVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "INT_VALUE")
    private Integer value;

    public Integer getValue() {
	return value;
    }

    public void setValue(Integer value) {
	this.value = value;
    }

}
