package com.linkare.rec.am.model;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Inheritance (strategy=InheritanceType.JOINED)
public abstract class Resource {

}