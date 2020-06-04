package com.linkare.irn.nascimento.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.linkare.irn.nascimento.util.BooleanResult;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class DomainObject implements Serializable {

    private static final long serialVersionUID = 5452496747967322411L;

    public static final String QUERY_PARAM_CREATION_DATE = "creationDate";

    @Id
    @Column(insertable = false, updatable = false, name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table-hilo-generator")
    @TableGenerator(name = "table-hilo-generator", table = "table_hilo_generator", pkColumnName = "table_name", valueColumnName = "next_val", allocationSize = 100)
    @XmlAttribute(name = "id")
    @XmlID
    private Long id;

    @Column(name = "uuid", length = 75, insertable = true, updatable = false)
    private String uuid;

    @Column(name = "creation_date", insertable = true, updatable = false)
    @JsonIgnore
    private Date creationDate;

    /**
     * @return the id
     */
    public Long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
	this.id = id;
    }

    /**
     * @return the creationDate
     */
    public Date getCreationDate() {
	return creationDate;
    }

    /**
     * @return the uuid
     */
    public String getUuid() {
	return uuid;
    }

    public BooleanResult getIsDeletable() {
	return BooleanResult.TRUE;
    }

    public void delete() {
	// do nothing by default
    }

    public boolean isPersistent() {
	return id != null;
    }

    @Override
    public String toString() {
	return id == null ? super.toString() : MoreObjects.toStringHelper(this).omitNullValues().add("id", getId()).toString();
    }

    @PrePersist
    public void prePersist() {
	this.creationDate = new Date();
	this.uuid = UUID.randomUUID().toString();
    }

    @PostPersist
    public void postPersist() {
	// do nothing
    }

    @PreUpdate
    public void preUpdate() {
	// do nothing
    }

    @PostUpdate
    public void postUpdate() {
	// do nothing
    }

    @PreRemove
    public void preRemove() {
	// do nothing
    }

    @PostRemove
    public void postRemove() {
	// do nothing
    }

    @PostLoad
    public void postLoad() {
	// do nothing
    }

    @Override
    public boolean equals(final Object other) {
	if (this == other) {
	    return true;
	}
	if (!(other instanceof DomainObject)) {
	    return false;
	}
	return equalsTo((DomainObject) other);
    }

    @Override
    public int hashCode() {
	int result = 13;
	result = 29 * result + (getId() != null ? getId().hashCode() : super.hashCode());
	return result;
    }

    private boolean equalsTo(final DomainObject other) {
	if (getId() == null && other.getId() == null) {
	    return false;
	}
	return Objects.equals(getId(), other.getId());
    }
}