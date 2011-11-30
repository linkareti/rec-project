package com.linkare.rec.am.model;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
public class ByteVal extends PhysicsVal {

    private static final long serialVersionUID = 1L;

    @Column(name = "BYTE_VALUE")
    private Byte value;

    public Byte getValue() {
	return value;
    }

    public void setValue(Byte value) {
	this.value = value;
    }

}
