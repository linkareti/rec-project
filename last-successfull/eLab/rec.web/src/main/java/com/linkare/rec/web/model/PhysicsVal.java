package com.linkare.rec.web.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.linkare.commons.jpa.DefaultDomainObject;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "PHYSICS_VAL")
@DiscriminatorColumn(name = "VALUE_TYPE")
public abstract class PhysicsVal extends DefaultDomainObject {

    private static final long serialVersionUID = 1L;

}
