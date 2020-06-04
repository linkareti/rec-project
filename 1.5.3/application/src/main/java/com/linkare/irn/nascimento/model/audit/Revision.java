package com.linkare.irn.nascimento.model.audit;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.linkare.irn.nascimento.model.listener.audit.ApplicationRevisionListener;
import com.linkare.irn.nascimento.model.listener.audit.AuditInfo;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "revision")
@RevisionEntity(ApplicationRevisionListener.class)
public class Revision implements Serializable {

    private static final long serialVersionUID = 7700588851534827404L;

    @Id
    @Column(insertable = false, updatable = false, name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table-hilo-generator")
    @TableGenerator(name = "table-hilo-generator", table = "table_hilo_generator", pkColumnName = "table_name", valueColumnName = "next_val", allocationSize = 100)
    @RevisionNumber
    private int id;

    @RevisionTimestamp
    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "username")
    private String username;

    @Column(name = "operation")
    private String operation;

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    @Transient
    public Date getRevisionDate() {
	return new Date(timestamp);
    }

    public long getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(long timestamp) {
	this.timestamp = timestamp;
    }

    /**
     * @return the username
     */
    public String getUsername() {
	return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
	return operation;
    }

    /**
     * @param operation
     *            the operation to set
     */
    public void setOperation(String operation) {
	this.operation = operation;
    }

    public void setAuditInfo(final AuditInfo auditInfo) {
	setTimestamp(auditInfo.getTimestamp());

	this.username = auditInfo.getUsername();
	this.operation = auditInfo.getOperation();
    }

    @Override
    public boolean equals(Object o) {
	if (this == o) {
	    return true;
	}
	if (!(o instanceof DefaultRevisionEntity)) {
	    return false;
	}

	final Revision that = (Revision) o;
	return id == that.id && timestamp == that.timestamp;
    }

    @Override
    public int hashCode() {
	int result;
	result = id;
	result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
	return result;
    }

    @Override
    public String toString() {
	return "Revision(id = " + id + ", revisionDate = " + DateFormat.getDateTimeInstance().format(getRevisionDate()) + ")";
    }
}