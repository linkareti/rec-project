/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.linkare.rec.export.domain.embeddable;

import com.linkare.rec.data.synch.DateTime;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author artur
 */
@Embeddable
public class EmbDateTime implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date embdate = null;
    private Short picos = null;
    private Short nanos = null;
    private Short micros = null;
    private Short milis = null;

    public EmbDateTime() {
    }

    public EmbDateTime(DateTime dateTime) {
        if (dateTime != null) {
            Calendar c = Calendar.getInstance();
            c.set(dateTime.getDate().getYear(), dateTime.getDate().getMonth(), dateTime.getDate().getDay(),
                    dateTime.getTime().getHours(), dateTime.getTime().getMinutes(), dateTime.getTime().getSeconds());
            this.setEmbdate(c.getTime());
            this.setPicos(dateTime.getTime().getPicos());
            this.setNanos(dateTime.getTime().getNanos());
            this.setMicros(dateTime.getTime().getMicros());
            this.setMilis(dateTime.getTime().getMilis());
        }
    }

    @Temporal(TemporalType.TIMESTAMP)
    public Date getEmbdate() {
        return embdate;
    }

    public void setEmbdate(Date embdate) {
        this.embdate = embdate;
    }

    public Short getPicos() {
        return picos;
    }

    public void setPicos(Short picos) {
        this.picos = picos;
    }

    public Short getNanos() {
        return nanos;
    }

    public void setNanos(Short nanos) {
        this.nanos = nanos;
    }

    public Short getMicros() {
        return micros;
    }

    public void setMicros(Short micros) {
        this.micros = micros;
    }

    public Short getMilis() {
        return milis;
    }

    public void setMilis(Short milis) {
        this.milis = milis;
    }
    /*@Override
    public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
    }
    @Override
    public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof EmbDateTime)) {
    return false;
    }
    EmbDateTime other = (EmbDateTime) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
    return false;
    }
    return true;
    }
    @Override
    public String toString() {
    return this.getClass().getName() "[id=" + id + "]";
    }*/
}
