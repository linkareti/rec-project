/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.linkare.rec.web.repository;

import java.util.Date;

/**
 * 
 * @author Artur Correia - Linkare TI
 */
public class DateTimeDTO extends AbstractBaseDTO {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private java.util.Date date;

    private short picos;

    private short nanos;

    private short micros;

    private short milis;

    private byte seconds;

    private byte minutes;

    private byte hours;

    public DateTimeDTO() {
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
	StringBuilder builder = new StringBuilder();
	builder.append("DateTimeDTO [date=");
	builder.append(date);
	builder.append(", picos=");
	builder.append(picos);
	builder.append(", nanos=");
	builder.append(nanos);
	builder.append(", micros=");
	builder.append(micros);
	builder.append(", milis=");
	builder.append(milis);
	builder.append(", seconds=");
	builder.append(seconds);
	builder.append(", minutes=");
	builder.append(minutes);
	builder.append(", hours=");
	builder.append(hours);
	builder.append("]");
	return builder.toString();
    }

}
