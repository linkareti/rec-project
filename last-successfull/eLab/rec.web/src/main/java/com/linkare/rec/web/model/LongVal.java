package com.linkare.rec.web.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
public class LongVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "LONG_VALUE")
    private Long value;

    public Long getValue() {
	return value;
    }

    public void setValue(Long value) {
	this.value = value;
    }

}
