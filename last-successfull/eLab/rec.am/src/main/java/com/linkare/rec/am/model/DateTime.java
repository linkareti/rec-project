/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.am.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
@Embeddable
public class DateTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private java.util.Date date;

    @Column(name = "PICOS")
    private short picos;

    @Column(name = "NANOS")
    private short nanos;

    @Column(name = "MICROS")
    private short micros;

    @Column(name = "MILIS")
    private short milis;

    @Column(name = "SECONDS")
    private byte seconds;

    @Column(name = "MINUTES")
    private byte minutes;

    @Column(name = "HOURS")
    private byte hours;

    public DateTime() {
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date embdate) {
	this.date = embdate;
    }

    public short getPicos() {
	return picos;
    }

    public void setPicos(short picos) {
	this.picos = picos;
    }

    public short getNanos() {
	return nanos;
    }

    public void setNanos(short nanos) {
	this.nanos = nanos;
    }

    public short getMicros() {
	return micros;
    }

    public void setMicros(short micros) {
	this.micros = micros;
    }

    public short getMilis() {
	return milis;
    }

    public void setMilis(short milis) {
	this.milis = milis;
    }

    public byte getHours() {
	return hours;
    }

    public void setHours(byte hours) {
	this.hours = hours;
    }

    public byte getMinutes() {
	return minutes;
    }

    public void setMinutes(byte minutes) {
	this.minutes = minutes;
    }

    public byte getSeconds() {
	return seconds;
    }

    public void setSeconds(byte seconds) {
	this.seconds = seconds;
    }

    @Override
    public String toString() {
	return "DateTime [date=" + date + ", picos=" + picos + ", nanos=" + nanos + ", micros=" + micros + ", milis=" + milis + ", seconds=" + seconds
		+ ", minutes=" + minutes + ", hours=" + hours + "]";
    }

    /*
     * @Override public int hashCode() { int hash = 0; hash += (id != null ? id.hashCode() : 0); return hash; }
     * 
     * @Override public boolean equals(Object object) { // TODO: Warning - this method won't work in the case the id fields are not set if (!(object instanceof
     * EmbDateTime)) { return false; } EmbDateTime other = (EmbDateTime) object; if ((this.id == null && other.id != null) || (this.id != null &&
     * !this.id.equals(other.id))) { return false; } return true; }
     * 
     * @Override public String toString() { return this.getClass().getName() "[id=" + id + "]"; }
     */
}
